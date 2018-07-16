package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveActivityReq {

    /**
     * bannerPath : string
     * content : string
     * endDate : 2018-07-02T02:21:59.683Z
     * linkPath : string
     * money : 0
     * slogan : string
     * startDate : 2018-07-02T02:21:59.683Z
     * videoExamplePath : string
     */

    @SerializedName("bannerPath")
    public String bannerPath;
    @SerializedName("content")
    public String content;
    @SerializedName("endDate")
    public String endDate;
    @SerializedName("linkPath")
    public String linkPath;
    @SerializedName("money")
    public int money;
    @SerializedName("slogan")
    public String slogan;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("videoExamplePath")
    public String videoExamplePath;
}
