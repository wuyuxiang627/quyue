package com.game.jxj.di.component;

import android.app.Activity;

import com.game.jxj.di.module.ActivityModule;
import com.game.jxj.di.scope.ActivityScope;
import com.game.jxj.ui.home.MainActivity;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity MainActivity);

}
