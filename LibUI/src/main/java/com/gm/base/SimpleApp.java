package com.gm.base;

import android.app.Application;
import android.content.Context;

import com.gm.R;
import com.gm.utils.LogUtils;
import com.gm.utils.ToastUtils;
import com.gm.utils.Utils;


/**
 * Created by PC005 on 2017/12/3.
 */

public class SimpleApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        initLog();

    }

    // init it in ur application
    public void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                // 设置log总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(true)
                .setConsoleSwitch(true)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1);// log栈深度，默认为1
        LogUtils.d(config.toString());

        ToastUtils.setBgResource(R.drawable.bg_toast);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
