package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class AlipayWithdrawalReq {

    /**
     * aliNumber : string
     * money : 0
     * password : string
     * realName : string
     * telephone : string
     */

    @SerializedName("aliNumber")
    public String aliNumber;
    @SerializedName("money")
    public int money;
    @SerializedName("password")
    public String password;
    @SerializedName("realName")
    public String realName;
    @SerializedName("telephone")
    public String telephone;
}
