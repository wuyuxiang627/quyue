package com.game.jxj.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.game.jxj.R;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.di.component.AppComponent;
import com.game.jxj.di.component.DaggerAppComponent;
import com.game.jxj.di.module.AppModule;
import com.game.jxj.di.module.HttpModule;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.MainActivity;
import com.game.jxj.utils.DynamicTimeFormat;
import com.game.jxj.utils.PicassoImageLoader;
import com.game.jxj.utils.RxUtil;
import com.game.jxj.utils.threadShare.ShareResponseEntity;
import com.gm.base.SimpleApp;
import com.gm.utils.CacheUtils;
import com.gm.utils.LogUtils;
import com.gm.utils.StringUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobSDK;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.ugc.TXUGCBase;

import org.xutils.x;


/**
 * Created by PC005 on 2017/11/27.
 */

public class App extends SimpleApp {

    public static AppComponent daggerAppComponent;
    private static App instance;


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {

            layout.setPrimaryColorsId(R.color.white);
            return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            layout.setEnableAutoLoadmore(false);
            return new ClassicsFooter(context);
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //shareSDK init
        MobSDK.init(this);

        x.Ext.init(this);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素



        Stetho.initializeWithDefaults(this);
        instance = this;

        TXUGCBase.getInstance().setLicence(this, Constants.TENCENT_LICENSE_URL, Constants.TENCENT_KEY);
        String str = TXUGCBase.getInstance().getLicenceInfo(this);

    }



    public static AppComponent getDaggerAppComponent() {

        if (daggerAppComponent == null) {
            daggerAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }

        return daggerAppComponent;

    }

    public static synchronized App getInstance() {
        return instance;
    }



}
