package com.game.jxj.model.http.subscriber;

import android.text.TextUtils;
import android.util.Log;

import com.game.jxj.base.BaseView;
import com.game.jxj.model.http.exception.ApiException;
import com.gm.utils.LogUtils;
import com.gm.utils.ToastUtils;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber() {

    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }


    @Override
    public void onError(Throwable e) {


//        if (((ApiException) e).getCode()==104) {
//
//
//        }

        if (mView == null) {


            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ApiException) {

            mView.showErrorMsg(e.getMessage());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg("网络错误");
        } else {
            mView.showErrorMsg("网络错误");
            LogUtils.e(e.toString());
        }
        if (isShowErrorState) {
            mView.stateError();
        }
    }

    protected  void  onFailure(){


    }
}
