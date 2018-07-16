package com.game.jxj.model.http.api;

import com.game.jxj.model.entity.HttpResp;
import com.game.jxj.model.entity.ObjectTokenResp;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author jxj
 * @date 2018/7/3
 */
public interface WangYiApis {

    String HOST = "http://cx.connxun.com/cx-api/";



    @FormUrlEncoded
    @POST("user/objectToken")
    Flowable<HttpResp<ObjectTokenResp>> alipayRecharge(@HeaderMap Map<String, String> headers ,@Field("objectname") String objectname);


}
