package com.game.jxj.ui.user.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.MainActivity;
import com.game.jxj.utils.RxUtil;
import com.game.jxj.utils.threadShare.ShareResponseEntity;
import com.game.jxj.utils.threadShare.ThirdLogin;
import com.game.jxj.utils.threadShare.ThreadLogin;
import com.gm.utils.ActivityUtil;
import com.gm.utils.CacheUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends SimpleActivity implements ThreadLogin {


    @BindView(R.id.ibtn_wechat)
    ImageView ibtnWechat;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    private ThirdLogin thirdLogin;
    //第三方登录
    private ThreadLogin threadLogin = this;

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, LoginActivity.class);
    }

    @BindView(R.id.video_view)
    VideoView videoView;


    @Override
    protected int getLayout() {
        return R.layout.activity_login2;
    }


    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initEventAndData() {
        String videoURI = "android.resource://" + getPackageName() + "/" + R.raw.login;
        videoView.setVideoURI(Uri.parse(videoURI));
        videoView.start();
        videoView.setOnPreparedListener(mp -> {
            mp.start();
            mp.setLooping(true);
        });

        SpannableStringBuilder span = new SpannableStringBuilder(tvAgreement.getText().toString());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(
                R.color.color_blue_49AED8)),
                10,
                14,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAgreement.setText(span);

    }


    @OnClick(R.id.ibtn_wechat)
    public void onIbtnWechatClicked() {
//        MainActivity.launch(mContext);
//        finish();
        thirdLogin = new ThirdLogin(mContext, threadLogin);
        thirdLogin.weixinLogoin();


    }

    @OnClick(R.id.tv_agreement)
    public void onTvAgreementClicked() {

    }

    @Override
    public void AuthorizedSuccess() {

    }

    @Override
    public void AuthorizedFailure() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void ThirdLoginSucces(ShareResponseEntity shareResponseEntity) {
        CacheUtils.getInstance().put("weChatUserInfo", shareResponseEntity);
        getHttpHelper().getToken(shareResponseEntity.getUserID(),
                shareResponseEntity.getUserName(),
                shareResponseEntity.getUserSex(),
                shareResponseEntity.getUserIcon())
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<LoginResp>>(mContext) {
                                   @Override
                                   public void onNext(Optional<LoginResp> LoginResp) {
                                       LoginResp loginResp = LoginResp.get();
                                       Toast.makeText(mContext, loginResp.nickName, Toast.LENGTH_LONG).show();
                                       CacheUtils.getInstance().put("loginResp", loginResp);
                                       UserInfo.getInstance().token = loginResp.token;
                                       UserInfo.getInstance().userId = loginResp.userId;
                                       MainActivity.launch(mContext);
                                       finish();


                                   }
                               }
                );
    }

    @Override
    public void ThirdLoginFailure() {

    }

    @Override
    public void ShareItSuccess() {

    }

    @Override
    public void ShareItFailure() {

    }

    @Override
    public void ShareSDkError(String messageException) {

    }
}
