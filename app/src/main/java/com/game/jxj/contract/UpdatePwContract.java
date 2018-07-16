package com.game.jxj.contract;

import com.game.jxj.base.BasePresenter;
import com.game.jxj.base.BaseView;

/**
 * @author jxj
 * @date 2018/4/18
 */

public interface UpdatePwContract {


    interface View extends BaseView {

        void upDatePWSuccess();

    }

    interface Presenter extends BasePresenter<View> {

        void upDatePW(String phone, String newPw, String code);

    }
}