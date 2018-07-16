package com.game.jxj.presenter.login;

import com.game.jxj.base.RxPresenter;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.contract.LoginContract;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.HttpHelper;
import com.game.jxj.model.http.subscriber.CommonSubscriber;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.CacheUtils;

import javax.inject.Inject;

/**
 * Created by win7 on 2017/12/10.
 */

public class LoginPrensenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    private HttpHelper httpHelper;


    @Inject
    public LoginPrensenter(HttpHelper helper) {
        httpHelper = helper;
    }

    @Override
    public void login(String account, String password) {

        addSubscribe(httpHelper.login(account, password)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                .subscribeWith(new CommonSubscriber<LoginResp>(mView, false) {
                    @Override
                    public void onNext(LoginResp loginResp) {


                        CacheUtils.getInstance(Constants.PATH_USER_INFO)
                                .put(Constants.PATH_USER_INFO_KEY, loginResp.token);

                        CacheUtils.getInstance()
                                .put(Constants.PATH_USER_ACCOUNT, account);

//                        UserInfo.getInstance().userId = loginResp.id;
//                        UserInfo.getInstance().token = loginResp.token;
                        mView.next();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }
                })
        );

    }
}
