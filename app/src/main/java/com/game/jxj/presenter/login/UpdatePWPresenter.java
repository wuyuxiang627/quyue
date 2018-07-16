package com.game.jxj.presenter.login;

import com.game.jxj.base.RxPresenter;
import com.game.jxj.contract.UpdatePwContract;
import com.game.jxj.model.http.HttpHelper;

import javax.inject.Inject;

/**
 * @author jxj
 * @date 2018/4/18
 */

public class UpdatePWPresenter extends RxPresenter<UpdatePwContract.View> implements
        UpdatePwContract.Presenter {


    HttpHelper httpHelper;


    @Inject
    public UpdatePWPresenter(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public void upDatePW(String phone, String newPw, String code) {

        mView.upDatePWSuccess();
    }
}
