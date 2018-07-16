package com.game.jxj.ui.home.me.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.SaveCooperationReq;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.ActivityUtil;
import com.gm.utils.RegexUtils;
import com.gm.utils.ToastUtils;
import com.gm.views.AbTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CopperationActivity extends SimpleActivity {

    public static void launch(Context context) {

        ActivityUtil.startActivity(context, CopperationActivity.class);
    }


    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.tv_cooperation_name)
    TextView tvCooperationName;
    @BindView(R.id.et_cooperation_name)
    EditText etCooperationName;
    @BindView(R.id.tv_cooperation_phone)
    TextView tvCooperationPhone;
    @BindView(R.id.et_cooperation_phone)
    EditText etCooperationPhone;
    @BindView(R.id.et_cooperation_text)
    EditText etCooperationText;
    @BindView(R.id.ib_cooperation_send)
    ImageButton ibCooperationSend;

    @Override
    protected int getLayout() {
        return R.layout.activity_cooperation;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("合作申请");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);

    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.ib_cooperation_send)
    public void onViewClicked() {
        String name = etCooperationName.getText().toString();
        String phone = etCooperationPhone.getText().toString();
        String content = etCooperationText.getText().toString();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(content)){
            ToastUtils.showLong("请填写必要参数");
            return;
        }

        if(!RegexUtils.isMobileSimple(phone)){
            ToastUtils.showLong("请输入正确的手机号");
            return;
        }

        SaveCooperationReq saveCooperationReq = new SaveCooperationReq();
        saveCooperationReq.name = etCooperationName.getText().toString();
        saveCooperationReq.telephone = etCooperationPhone.getText().toString();
        saveCooperationReq.content = etCooperationText.getText().toString();
        getHttpHelper().saveCooperation(saveCooperationReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<String>>(mContext) {
                    @Override
                    public void onNext(Optional<String> stringOptional) {
                        ToastUtils.showLong("提交成功");
                        finish();
                    }
                });
    }
}
