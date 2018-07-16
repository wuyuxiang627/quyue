package com.game.jxj.model.http.subscriber;

import android.content.Context;

import com.game.jxj.model.http.exception.ApiException;
import com.gm.utils.LogUtils;
import com.gm.utils.ToastUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author jxj
 * @date 2018/4/24
 */

public abstract class SimpleCommonSubscriber<T> extends ResourceSubscriber<T> {


    private static final int TOKEN_FAILURE = 403;


    private Context mContext;

    public SimpleCommonSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof ApiException) {

            int code = ((ApiException) e).getCode();
            if (code == TOKEN_FAILURE) {
                onTokenFailure();
            } else {
                onFailure((ApiException) e);
            }

        } else {
            onNetFailure(e.getMessage());
        }


    }


    @Override
    public void onComplete() {

    }


    public void onTokenFailure() {
        ToastUtils.showShort("尚未登录");

    }

    public void onFailure(ApiException e) {

        ToastUtils.showShort(e.getMessage());
        LogUtils.e(e.getMessage());

    }

    public void onNetFailure(String message) {

        ToastUtils.showShort("网络错误");
        LogUtils.e(message);
    }
}
