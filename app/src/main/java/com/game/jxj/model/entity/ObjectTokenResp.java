package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class ObjectTokenResp {

    /**
     * token : string,权限
     * url : string,图片访问位置
     */

    @SerializedName("token")
    public String token;
    @SerializedName("url")
    public String url;
}
