package com.game.jxj.ui.home;

import android.widget.Toast;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.user.activity.LoginActivity;
import com.game.jxj.utils.RxUtil;
import com.game.jxj.utils.threadShare.ShareResponseEntity;
import com.gm.utils.CacheUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends SimpleActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_wecome;
    }

    @Override
    protected void initEventAndData() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                ShareResponseEntity weChatUserInfo = (ShareResponseEntity) CacheUtils.getInstance().getSerializable("weChatUserInfo", null);
                if (weChatUserInfo != null) {
                    getHttpHelper().getToken(weChatUserInfo.getUserID(),
                            weChatUserInfo.getUserName(),
                            weChatUserInfo.getUserSex(),
                            weChatUserInfo.getUserIcon())
                            .compose(RxUtil.rxSchedulerHelper())
                            .compose(RxUtil.handleResult2())
                            .subscribeWith(new SimpleCommonSubscriber<Optional<LoginResp>>(mContext) {
                                               @Override
                                               public void onNext(Optional<LoginResp> LoginResp) {
                                                   LoginResp loginResp = LoginResp.get();
                                                   Toast.makeText(mContext, loginResp.nickName, Toast.LENGTH_LONG).show();
                                                   CacheUtils.getInstance().put("loginResp", loginResp);
                                                   UserInfo.getInstance().token = loginResp.token;
                                                   MainActivity.launch(mContext);
                                                   finish();
                                               }

                                           }
                            );

                } else {
                    weChatUserInfo = new ShareResponseEntity();
                    weChatUserInfo.setUserID("omB8y1qV7hC_ps9HnUzs-zAqehGo");
                    weChatUserInfo.setUserIcon("http://thirdwx.qlogo.cn/mmopen/vi_32/9cny6ArQeqZgDwT3ibN2hibCBGCThW4XvVxcVfQ9fufwltZroQB2YIiaViaDc2g2s0KqnvVlbXXuEBZz6PxySJP8dQ/132");
                    weChatUserInfo.setUserName("鲲鱼");
                    weChatUserInfo.setUserSex(1);
                    CacheUtils.getInstance().put("weChatUserInfo", weChatUserInfo);
                    getHttpHelper().getToken(weChatUserInfo.getUserID(),
                            weChatUserInfo.getUserName(),
                            weChatUserInfo.getUserSex(),
                            weChatUserInfo.getUserIcon())
                            .compose(RxUtil.rxSchedulerHelper())
                            .compose(RxUtil.handleResult2())
                            .subscribeWith(new SimpleCommonSubscriber<Optional<LoginResp>>(mContext) {
                                               @Override
                                               public void onNext(Optional<LoginResp> LoginResp) {
                                                   LoginResp loginResp = LoginResp.get();
                                                   Toast.makeText(mContext, loginResp.nickName, Toast.LENGTH_LONG).show();
                                                   CacheUtils.getInstance().put("loginResp", loginResp);
                                                   MainActivity.launch(mContext);
                                                   finish();
                                               }

                                           }
                            );

//                    LoginActivity.launch(mContext);
                    finish();
                }

//
            }
        }, 1000);

    }
}
