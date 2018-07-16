package com.game.jxj.ui.home.me.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.model.entity.ArtUserReq;
import com.game.jxj.model.entity.ImageDNS;
import com.game.jxj.model.entity.JsonBean;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.ObjectTokenResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.UpdateArtUserReq;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.MainActivity;
import com.game.jxj.utils.GetJsonDataUtil;
import com.game.jxj.utils.IUploadImgListener;
import com.game.jxj.utils.MessageEvent;
import com.game.jxj.utils.RxUtil;
import com.game.jxj.utils.UploadImgWangyiUtil;
import com.game.jxj.view.ActionSheetDialog;
import com.game.jxj.widget.TCInputTextMsgDialog;
import com.gm.utils.ActivityUtil;
import com.gm.utils.CacheUtils;
import com.gm.utils.ToastUtils;
import com.gm.views.AbTitleBar;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.xutils.common.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class EditInfoActivity extends SimpleActivity implements TCInputTextMsgDialog.OnTextSendListener {
    ArtUserReq artUserReq;
    @BindView(R.id.tv_edit_nickname)
    TextView tvEditNickname;
    @BindView(R.id.rl_edit_nickname)
    RelativeLayout rlEditNickname;
    @BindView(R.id.tv_edit_gender)
    TextView tvEditGender;
    @BindView(R.id.rl_edit_gender)
    RelativeLayout rlEditGender;
    @BindView(R.id.tv_edit_address)
    TextView tvEditAddress;
    @BindView(R.id.rl_edit_address)
    RelativeLayout rlEditAddress;
    @BindView(R.id.tv_edit_text)
    TextView tvEditText;
    @BindView(R.id.rl_edit_text)
    RelativeLayout rlEditText;
    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    private TCInputTextMsgDialog mInputTextMsgDialog;
    private int IMAGE_PICKER = 1002;
    private ActionSheetDialog mDialog;
    private String iMageUrl = "";

    public static void launch(Context context, ArtUserReq artUserReq) {
        Intent intent = new Intent(context, EditInfoActivity.class);
        intent.putExtra("artUserReq", artUserReq);
        ActivityUtil.startActivity(context, intent);
    }

    @BindView(R.id.circle_iv_edit_avatar)
    CircleImageView circleIvEditAvatar;
    @BindView(R.id.btn_edit_photo)
    Button btnEditPhoto;

    boolean blType = false;

    private boolean isLoaded = false;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    private String province;

    private String city;

    private String district;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    UploadImgWangyiUtil uploadImgWangyiUtil;

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_userinfo;
    }

    @Override
    protected void initEventAndData() {

        titleBar.setTitleText("我的");
        titleBar.setBackgroundColor(Color.parseColor("#010B1E"));


        artUserReq = (com.game.jxj.model.entity.ArtUserReq) getIntent().getSerializableExtra("artUserReq");
        mInputTextMsgDialog = new TCInputTextMsgDialog(this, R.style.InputDialog);
        mInputTextMsgDialog.setmOnTextSendListener(this);
        UserData();

        titleBar.setRightButton("保存",R.color.white).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                UpdateArtUserReq updateArtUserReq = new UpdateArtUserReq();
                updateArtUserReq.filePath = iMageUrl;
                updateArtUserReq.nickName = tvEditNickname.getText().toString();
                updateArtUserReq.sex = tvEditGender.getText().toString().equals("男")?1:2;
                updateArtUserReq.hometown = tvEditAddress.getText().toString();
                updateArtUserReq.signature = tvEditText.getText().toString();
                getHttpHelper().updateArtUser(updateArtUserReq)
                        .compose(RxUtil.rxSchedulerHelper())
                        .compose(RxUtil.handleResult2())
                        .subscribeWith(new SimpleCommonSubscriber<Optional<String>>(mContext) {

                            @Override
                            public void onNext(Optional<String> loginRespOptional) {
                                ToastUtils.showLong("保存成功");
                                EventBus.getDefault().post(new MessageEvent(1));
                                finish();

                            }
                        });

            }
        });


        mHandler.sendEmptyMessage(MSG_LOAD_DATA);


    }

    public void UserData() {

        Glide.with(this).load(artUserReq.filePath).into(circleIvEditAvatar);
        tvEditNickname.setText(artUserReq.nickName);
        if (artUserReq.sex == 0) {
        } else if (artUserReq.sex == 1) {
            tvEditGender.setText("男");
        } else {
            tvEditGender.setText("女");
        }
        tvEditAddress.setText(artUserReq.hometown);
        tvEditText.setText(artUserReq.signature);



    }

    @OnClick({R.id.rl_edit_nickname, R.id.rl_edit_gender, R.id.rl_edit_address, R.id.rl_edit_text, R.id.btn_edit_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_edit_nickname: //设置用户名
                blType = true;
                showInputMsgDialog(blType);
                break;
            case R.id.rl_edit_gender:
                setSex();
                break;
            case R.id.rl_edit_address:

                setAddress();

                break;
            case R.id.rl_edit_text: //设置个性签名
                blType = false;
                showInputMsgDialog(blType);
                break;
            case R.id.btn_edit_photo: //更换头像
                updataIcon();


                break;
        }
    }

    private void updataIcon() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);

    }


    private void setAddress() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) ->
        {
            //返回的分别是三个级别的选中位置

            province = options1Items.get(options1).getPickerViewText();
            city = options2Items.get(options1).get(options2);
            district = options3Items.get(options1).get(options2).get(options3);
            tvEditAddress.setText(city);

        })
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(getResources().getColor(R.color.colorPrimary))
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))
                .setDividerColor(getResources().getColor(R.color.gray))
                .setTextColorCenter(getResources().getColor(R.color.gray)) //设置选中项文字颜色
                .setContentTextSize(16)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//三级选择器
        pvOptions.show();

    }

    /**
     * 发消息弹出框
     */
    private void showInputMsgDialog(boolean blType) {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mInputTextMsgDialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); //设置宽度
        mInputTextMsgDialog.getWindow().setAttributes(lp);
        mInputTextMsgDialog.setCancelable(true);
        mInputTextMsgDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mInputTextMsgDialog.show();
        mInputTextMsgDialog.setEditType(blType);
    }


    //设置性别
    public void setSex() {
        View views = LayoutInflater.from(mContext).inflate(R.layout.view_actionsheet, null);

        PopupWindow popupWindow = new PopupWindow(views);

        // 设置外部可点击
        popupWindow.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        popupWindow.setContentView(views);
        // 设置弹出窗体的宽和高
        popupWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.GiftPopupAnimation);
        // 设置弹出窗体可点击
        popupWindow.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(views, Gravity.BOTTOM, 0, 0); // 设置layout在PopupWindow中显示的位置

        TextView tvFinish = views.findViewById(R.id.txt_cancel);
        tvFinish.setOnClickListener(v -> popupWindow.dismiss());
        TextView tvMan = views.findViewById(R.id.tv_sex_man);
        tvMan.setOnClickListener(v -> {
            tvEditGender.setText("男");
            popupWindow.dismiss();
        });
        TextView tvWuMan = views.findViewById(R.id.tv_sex_wuman);
        tvWuMan.setOnClickListener(v -> {
            tvEditGender.setText("女");
            popupWindow.dismiss();
        });

    }


    @Override
    public void onTextSend(String msg, boolean tanmuOpen) {
        if (blType) {
            tvEditNickname.setText(msg);
        } else {
            tvEditText.setText(msg);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String imagePath = images.get(0).path;
                Log.e("用户头像", imagePath);
                //本地文件
                File file = new File(imagePath);
                //加载图片
                Glide.with(this).load(file).into(circleIvEditAvatar);

                HashMap<String, String> map = new HashMap<>();
                map.put("objectname", file.getName());
                map.put("time", new Date().getTime() + "");

                //获取sign值
                uploadImgWangyiUtil = new UploadImgWangyiUtil(mContext, getHttpHelper(), file, new IUploadImgListener() {
                    @Override
                    public void getObjectTokenOnNext(ObjectTokenResp objectTokenResp) {

                    }

                    @Override
                    public void getWangyiDNSOnFailure(Call call, IOException e) {

                    }

                    @Override
                    public void getWangyiDNSOnResponse(ImageDNS imageDNS) {

                    }

                    @Override
                    public void onSuccess(String iamgeurl, String s) {
                        iMageUrl =iamgeurl;

                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {

                    }

                    @Override
                    public void onCancelled(Callback.CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onLoading(long l, long l1, boolean b) {

                    }
                });
                uploadImgWangyiUtil.setSign(map);

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        thread = new Thread(() -> initJsonData());
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:

                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:

                    break;
                default:
                    break;
            }
        }
    };


    private void initJsonData() {//解析数据

        //获取assets目录下的json文件数据
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
