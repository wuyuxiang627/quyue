package com.gm.utils;

import android.app.Activity;
import android.view.View;

/**
 * @author jxj
 * @date 2018/4/18
 */

public class ViewUtils {
    public ViewUtils() {
    }

    public static <V extends View> V find(Activity ac, int id) {
        return ac.findViewById(id);
    }

    public static <V extends View> V find(View view, int id) {
        return view.findViewById(id);
    }

}
