package com.game.jxj.presenter.login;

import android.content.Context;

import com.game.jxj.base.RxPresenter;
import com.game.jxj.contract.RegisterContract;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.RegisterResp;
import com.game.jxj.model.entity.SendCodeResp;
import com.game.jxj.model.http.HttpHelper;
import com.game.jxj.model.http.subscriber.CommonSubscriber;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * @author jxj
 * @date 2017/12/12
 */

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {


    HttpHelper httpHelper;

    @Inject
    public RegisterPresenter(HttpHelper helper) {
        this.httpHelper = helper;
    }


    @Override
    public void sendCode(String phone, String mark) {

        addSubscribe(httpHelper.sendCode(phone, mark)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<SendCodeResp>>>((Context) mView) {
                    @Override
                    public void onNext(Optional<List<SendCodeResp>> listOptional) {
                        mView.sendCodeSuccess();
                    }
                }));

    }

    @Override
    public void register(String phone, String pw, String code, String refereeUser) {

        addSubscribe(httpHelper.register(phone, pw, code, refereeUser)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                .subscribeWith(new CommonSubscriber<RegisterResp>(mView, false) {
                    @Override
                    public void onNext(RegisterResp registerResp) {
                        mView.onRegisterSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onRegisterFailed();
                    }
                }));

    }
}
