package com.game.jxj.di.component;

import com.game.jxj.base.App;
import com.game.jxj.di.module.AppModule;
import com.game.jxj.di.module.HttpModule;
import com.game.jxj.model.http.HttpHelper;
import com.game.jxj.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by PC005 on 2017/11/28.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();

    RetrofitHelper retrofitHelper();

    OkHttpClient getOkHttpClien();

    HttpHelper getHttpHelper();

}
