package com.gm.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gm.utils.ILogLifeCycle;
import com.gm.utils.LogUtils;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by PC005 on 2017/11/28.
 */

public class LifeCycleActivity extends SupportActivity implements ILogLifeCycle {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.logLifeCycle("onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.logLifeCycle("onNewIntent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.logLifeCycle("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.logLifeCycle("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.logLifeCycle("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.logLifeCycle("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.logLifeCycle("onDestroy");
    }


    @Override
    public boolean getLogEnabled() {
        return false;
    }

    @Override
    public void logLifeCycle(String var2) {

        if (getLogEnabled()) {
            LogUtils.iTag(this.getClass().getSimpleName(), var2);
        }

    }
}
