package com.game.jxj.utils;

import android.util.Log;

import com.game.jxj.model.entity.ImageDNS;
import com.game.jxj.model.entity.ObjectTokenResp;

import org.xutils.common.Callback;

import java.io.IOException;

import okhttp3.Call;

public interface IUploadImgListener {


    /**
     * 获取上传凭证
     *
     * @param objectTokenResp 图片地址/图片权限
     */
    void getObjectTokenOnNext(ObjectTokenResp objectTokenResp);

    /**
     * 获取网易云DNS服务器失败
     *
     * @param call
     * @param e
     */
    void getWangyiDNSOnFailure(Call call, IOException e);

    /**
     * 获取网易云DNS服务器成功
     *
     * @param imageDNS 服务器列表
     */
    void getWangyiDNSOnResponse(ImageDNS imageDNS);


    /**
     * 上传成功
     * @param iamgeurl 图片地址
     * @param s
     */
    void onSuccess(String iamgeurl,String s);

    /**
     * 上传失败
     * @param throwable
     * @param b
     */
    void onError(Throwable throwable, boolean b);



    void onCancelled(Callback.CancelledException e);


    void onFinished();

    void onWaiting();

    void onStarted();

    void onLoading(long l, long l1, boolean b);


}
