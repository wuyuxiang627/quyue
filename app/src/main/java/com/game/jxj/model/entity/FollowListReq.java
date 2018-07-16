package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class FollowListReq {


    /**
     * length : 0
     * page : 0
     * type : 0
     * userId : string
     */

    @SerializedName("length")
    public int length;
    @SerializedName("page")
    public int page;
    @SerializedName("type")
    public int type;
    @SerializedName("userId")
    public String userId;


}
