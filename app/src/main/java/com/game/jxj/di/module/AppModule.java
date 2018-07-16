package com.game.jxj.di.module;

import com.game.jxj.base.App;
import com.game.jxj.model.http.HttpHelper;
import com.game.jxj.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PC005 on 2017/11/28.
 */

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }


    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }


    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }


}
