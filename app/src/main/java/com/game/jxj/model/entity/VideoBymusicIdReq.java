package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class VideoBymusicIdReq {
    /**
     * activityId : string
     * favorStatus : 0
     * length : 0
     * musicId : string
     * page : 0
     * recommend : 0
     * userId : string
     */

    @SerializedName("activityId")
    public String activityId;
    @SerializedName("favorStatus")
    public int favorStatus;
    @SerializedName("length")
    public int length;
    @SerializedName("musicId")
    public String musicId;
    @SerializedName("page")
    public int page;
    @SerializedName("recommend")
    public int recommend;
    @SerializedName("userId")
    public String userId;
}
