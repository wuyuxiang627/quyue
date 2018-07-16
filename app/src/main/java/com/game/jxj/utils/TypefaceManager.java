package com.game.jxj.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.game.jxj.base.App;

/**
 * @author jxj
 * @date 2018/6/11
 */
public class TypefaceManager {

    private static Typeface typeface3 = null;// 导航字体

    static {
        getTypeface3(App.getInstance());
    }
    public static Typeface getTypeface3(Context context) {
        try {
            if (typeface3 == null) {
                typeface3 = Typeface.createFromAsset(context.getAssets(), "fonts/msyh.TTF");
            }
        }catch (Exception e){
        }
        return typeface3;
    }
}
