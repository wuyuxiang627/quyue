package com.gm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/8/26.
 */

public class ActivityUtil {
    public ActivityUtil() {
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity act, Class cls, int Code) {
        Intent intent = new Intent(act, cls);
        act.startActivityForResult(intent, Code);
    }

    public static void startActivityForResult(Activity act, Class cls, Bundle bundle, int Code) {
        Intent intent = new Intent(act, cls);
        intent.putExtras(bundle);
        act.startActivityForResult(intent, Code);
    }
}
