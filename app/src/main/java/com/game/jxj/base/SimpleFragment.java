package com.game.jxj.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tu.loadingdialog.LoadingDailog;
import com.game.jxj.model.http.HttpHelper;
import com.gm.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by codeest on 16/8/11.
 * 无MVP的Fragment基类
 */

public abstract class SimpleFragment extends SupportFragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInited = false;
    protected LoadingDailog dialog;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
        initEventAndData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }


    protected LoadingDailog showLoading(@Nullable String str) {

        if (dialog == null) {
            String msg = StringUtils.isEmpty(str) ? "加载中..." : str;

            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setCancelOutside(true);
            return dialog = loadBuilder.create();
        }


        return dialog;
    }

    protected LoadingDailog showLoading() {

        return showLoading(null);

    }


    protected void hideLoading() {

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    protected HttpHelper getHttpHelper() {
        return App.getDaggerAppComponent().getHttpHelper();
    }


    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}
