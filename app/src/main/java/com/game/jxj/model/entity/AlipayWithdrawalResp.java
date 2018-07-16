package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class AlipayWithdrawalResp {


    /**
     * code : 200
     * data : {}
     * message : 请求成功
     */

    @SerializedName("code")
    public String code;
    @SerializedName("data")
    public String data;
    @SerializedName("message")
    public String message;
}
