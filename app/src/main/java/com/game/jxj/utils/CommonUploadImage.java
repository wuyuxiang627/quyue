package com.game.jxj.utils;

import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.gm.utils.LogUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author jxj
 * @date 2018/4/25
 */

public class CommonUploadImage {


    public static void uploadPic(File file, SimpleCommonSubscriber<String> subscriber, String type) {

        if (file == null || subscriber == null) {
            LogUtils.d("图片文件 和观察者不能为空");
            return;
        }

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

//        Flowable<HttpResp<String>> flowable = App.getDaggerAppComponent()
//                .getHttpHelper()
//                .uploadPic(body, type);

//        flowable.compose(RxUtil.rxSchedulerHelper())
//                .compose(RxUtil.handleResult())
//                .subscribeWith(subscriber);

    }
}
