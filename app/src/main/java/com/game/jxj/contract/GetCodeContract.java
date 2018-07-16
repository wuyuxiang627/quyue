package com.game.jxj.contract;

import com.game.jxj.base.BasePresenter;
import com.game.jxj.base.BaseView;
import com.game.jxj.model.entity.SendCodeResp;

/**
 * @author jxj
 * @date 2018/4/18
 */

public interface GetCodeContract {

    interface View extends BaseView {

        /**
         * 发送验证码成功
         */
        void sendCodeSuccess(SendCodeResp resp);

    }


    interface Presenter extends BasePresenter<View> {
        /**
         * @param phone 手机号
         * @param mark  login 登录signUp 注册
         */
        void sendCode(String phone, String mark);

    }


}
