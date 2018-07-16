package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class EvaluateListReq {
    /**
     * length : 0
     * page : 0
     * videoId : string
     */

    @SerializedName("length")
    public int length;
    @SerializedName("page")
    public int page;
    @SerializedName("videoId")
    public String videoId;
}

