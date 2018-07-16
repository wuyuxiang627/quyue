package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class WalletDetailedListReq {

    /**
     * endDate : 2018-07-03T02:41:52.789Z
     * length : 0
     * page : 0
     * startDate : 2018-07-03T02:41:52.789Z
     */

    @SerializedName("endDate")
    public String endDate;
    @SerializedName("length")
    public int length;
    @SerializedName("page")
    public int page;
    @SerializedName("startDate")
    public String startDate;
}
