package com.game.jxj.ui.home.me.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.SaveOpinioReq;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.ActivityUtil;
import com.gm.utils.RegexUtils;
import com.gm.utils.ToastUtils;
import com.gm.utils.Utils;
import com.gm.views.AbTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends SimpleActivity {


    public static void launch(Context context) {

        ActivityUtil.startActivity(context, FeedbackActivity.class);
    }

    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.tv_feed_name)
    TextView tvFeedName;
    @BindView(R.id.et_feedback_name)
    EditText etFeedbackName;
    @BindView(R.id.tv_feed_phone)
    TextView tvFeedPhone;
    @BindView(R.id.et_feedback_phone)
    EditText etFeedbackPhone;
    @BindView(R.id.et_feedback_text)
    EditText etFeedbackText;
    @BindView(R.id.ib_feedback_send)
    ImageButton ibFeedbackSend;

    @Override
    protected int getLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("意见反馈");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);
        setTitle();


    }

    private void setTitle() {
        String nameStr = "您的姓名<font color='#999999'><small>（选填）</small></font>";
        tvFeedName.setText(nameStr);
        tvFeedName.setText(Html.fromHtml(nameStr));
        String phoneStr = "联系人电话<font color='#999999'><small>（选填）</small></font>";
        tvFeedPhone.setText(phoneStr);
        tvFeedPhone.setText(Html.fromHtml(phoneStr));

    }


    @SuppressLint("CheckResult")
    @OnClick(R.id.ib_feedback_send)
    public void onViewClicked() {
        String name = etFeedbackName.getText().toString();
        String phone = etFeedbackPhone.getText().toString();
        String content = etFeedbackText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(content)) {
            ToastUtils.showLong("请填写详细信息");
            return;
        }
        if(!RegexUtils.isMobileSimple(phone)){
            ToastUtils.showLong("请输入正确的手机号");
            return;
        }

        SaveOpinioReq saveOpinioReq = new SaveOpinioReq();
        saveOpinioReq.name = name;
        saveOpinioReq.telephone = phone;
        saveOpinioReq.content = content;

        getHttpHelper().saveOpinion(saveOpinioReq)
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
