package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class WalletDetailedListResp {
    /**
     * createDate : 2018-07-03T02:41:52.785Z
     * describes : string
     * money : 0
     * type : 0
     */

    @SerializedName("createDate")
    public String createDate;
    @SerializedName("describes")
    public String describes;
    @SerializedName("money")
    public int money;
    @SerializedName("type")
    public int type;
}
