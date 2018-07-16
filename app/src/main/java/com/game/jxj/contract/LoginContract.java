package com.game.jxj.contract;

import com.game.jxj.base.BasePresenter;
import com.game.jxj.base.BaseView;

/**
 * Created by win7 on 2017/12/10.
 */

public interface LoginContract {

    interface View extends BaseView {



        void showError();

        void next();

    }

    interface Presenter extends BasePresenter<View> {

        void login(String account, String password);
    }
}
