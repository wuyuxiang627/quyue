package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class VideoListReq {
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
    public Integer favorStatus;
    @SerializedName("length")
    public int length;
    @SerializedName("musicId")
    public String musicId;
    @SerializedName("page")
    public int page;
    @SerializedName("recommend")
    public Integer recommend;
    @SerializedName("userId")
    public String userId;
}
