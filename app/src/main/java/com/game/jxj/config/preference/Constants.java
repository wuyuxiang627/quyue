package com.game.jxj.config.preference;

import android.os.Environment;

import com.game.jxj.base.App;
import com.game.jxj.model.http.api.Apis;

import java.io.File;

/**
 *
 */
public class Constants {


    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wodian";

    public static final String PATH_MUSIC = PATH_SDCARD + File.separator + "music";


    public static final String IMAGE = Apis.HOST + "upload";

    public static final String PATH_AVATAR = "avatarPath";
    public static final String PATH_BUSINESS_LICENSE = "businessLicense";
    public static final String PATH_GOODS_COMMENT = "comments";


    //缓存用户信息目录
    public static final String PATH_USER_INFO = "loginResp";

    public static final String PATH_USER_INFO_KEY = "info";

    public static final String PATH_USER_ACCOUNT = "account";

    public static final int DATA_LIST_SIZE = 20;


    //====================腾讯云====================

    public static final String TENCENT_LICENSE_URL = "http://license.vod2.myqcloud.com/license/v1/4a812f08bac70b66ade97721117e34fb/TXUgcSDK.licence";
    public static final String TENCENT_KEY = "3123fccd3b74eed74a9b289821a43d0d";


}