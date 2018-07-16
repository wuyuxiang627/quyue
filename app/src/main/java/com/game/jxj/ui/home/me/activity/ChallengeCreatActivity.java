package com.game.jxj.ui.home.me.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.gm.utils.ActivityUtil;
import com.gm.utils.ToastUtils;
import com.gm.views.AbTitleBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ChallengeCreatActivity extends SimpleActivity {

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, ChallengeCreatActivity.class);
    }

    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.iv_release_challenge_logo)
    CircleImageView ivReleaseChallengeLogo;
    @BindView(R.id.tv_release_challeng_text)
    TextView tvReleaseChallengText;
    @BindView(R.id.et_release_challenge_text)
    EditText etReleaseChallengeText;
    @BindView(R.id.tv_release_challeng_money)
    TextView tvReleaseChallengMoney;
    @BindView(R.id.et_release_challenge_money)
    EditText etReleaseChallengeMoney;
    @BindView(R.id.tv_release_challenge_start_time)
    TextView tvReleaseChallengeStartTime;
    @BindView(R.id.view_challenge)
    View viewChallenge;
    @BindView(R.id.tv_release_challenge_end_time)
    TextView tvReleaseChallengeEndTime;
    @BindView(R.id.et_release_challenge_share)
    EditText etReleaseChallengeShare;
    @BindView(R.id.ib_release_challenge_preview)
    ImageButton ibReleaseChallengePreview;
    @BindView(R.id.ib_realse_challenge_start)
    ImageButton ibRealseChallengeStart;

    private TimePickerView pvTime;
    private long startTime;

    private long endTime;

    private int IMAGE_PICKER = 1002;

    @Override
    protected int getLayout() {
        return R.layout.activity_release_challenge;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("发起挑战赛");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setRightButton("重填",R.color.black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etReleaseChallengeText.setText("");
                etReleaseChallengeMoney.setText("");
                etReleaseChallengeShare.setText("");
                setTime(1);
            }
        });
    }

    @OnClick({R.id.ib_release_challenge_preview,
            R.id.ib_realse_challenge_start,
            R.id.iv_release_challenge_logo,
            R.id.tv_release_challenge_start_time,
            R.id.tv_release_challenge_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_release_challenge_logo:
                updataIcon();
                break;
            case R.id.ib_release_challenge_preview:
                PreviewActivity.launch(mContext);
                break;
            case R.id.ib_realse_challenge_start:
                break;
            case R.id.tv_release_challenge_start_time:
                //开始时间
                setTime(1);
                break;
            case R.id.tv_release_challenge_end_time:
                //结束时间
                setTime(2);
                break;
        }
    }


    private void updataIcon() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String imagePath = images.get(0).path;
                //本地文件
                File file = new File(imagePath);
                //加载图片
                Glide.with(this).load(file).into(ivReleaseChallengeLogo);
            }
        }
    }

    public void setTime(int type){
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(2013,0,1);
        endDate.set(2020,11,31);

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if(type ==2){
                    getTime(date,type);
                    if(endTime < startTime){
                        ToastUtils.showLong("结束时间不能小于开始时间");
                        return;
                    }
                    tvReleaseChallengeEndTime.setText(getTime(date,type));
                }else{
                    tvReleaseChallengeStartTime.setText(getTime(date,type));
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(getResources().getColor(R.color.gray))
                .setSubmitColor(getResources().getColor(R.color.gray))
                .setDividerColor(getResources().getColor(R.color.lightgray))
                .setTextColorCenter(getResources().getColor(R.color.colorPrimary)) //设置选中项文字颜色
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("","","","","","")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
        

    }

    private String getTime(Date date,int type) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        if(type ==1){
            startTime = date.getTime();
        }else {
            endTime = date.getTime();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }






}
