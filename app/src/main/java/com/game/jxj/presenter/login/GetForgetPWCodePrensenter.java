package com.game.jxj.presenter.login;

import com.game.jxj.base.RxPresenter;
import com.game.jxj.contract.GetCodeContract;
import com.game.jxj.model.http.HttpHelper;

import javax.inject.Inject;

/**
 * @author jxj
 * @date 2018/4/18
 */

public class GetForgetPWCodePrensenter extends RxPresenter<GetCodeContract.View> implements GetCodeContract.Presenter {


    HttpHelper httpHelper;

    @Inject
    public GetForgetPWCodePrensenter(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public void sendCode(String phone, String mark) {
//        addSubscribe(mDataManager.fetchSendCode(phone, mark)
//                .compose(RxUtil.rxSchedulerHelper())
//                .compose(RxUtil.handleResult())
//                .subscribeWith(new CommonSubscriber<SendCodeResp>(mView, false) {
//                    @Override
//                    public void onNext(SendCodeResp sendCodeResp) {
//                        mView.sendCodeSuccess(sendCodeResp);
//                    }
//                }));

        mView.sendCodeSuccess(null);

    }
}
