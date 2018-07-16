package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class UserWalletResp {
    /**
     * incomeMoney : 0
     * money : 0
     * status : 0
     */

    @SerializedName("incomeMoney")
    public int incomeMoney;
    @SerializedName("money")
    public int money;
    @SerializedName("status")
    public int status;
}
