package com.game.jxj.di.component;

import android.app.Activity;

import com.game.jxj.di.module.FragmentModule;
import com.game.jxj.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


}
