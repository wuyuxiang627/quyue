package com.game.jxj.contract;

import com.game.jxj.base.BasePresenter;
import com.game.jxj.base.BaseView;

/**
 * @author jxj
 * @date 2017/12/12
 */

public interface RegisterContract {


    interface View extends BaseView {


        /**
         * 发送验证码成功
         */
        void sendCodeSuccess();


        /**
         * 注册成功
         */
        void onRegisterSuccess();

        /**
         * 注册失败
         */
        void onRegisterFailed();

    }

    interface Presenter extends BasePresenter<View>{

        /**
         * @param phone 手机号
         * @param mark login 登录signUp 注册
         */
        void sendCode(String phone,String mark);

        /**
         * @param phone 手机号
         * @param pw    密码
         * @param code  验证码
         */
        void register(String phone, String pw, String code,String refereeUser);
    }
}
