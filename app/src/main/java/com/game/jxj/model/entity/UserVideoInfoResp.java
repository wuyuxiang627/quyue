package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserVideoInfoResp {
    /**
     * total : 0
     * list : []
     */

    @SerializedName("total")
    public int total;
    @SerializedName("list")
    public List<VideoResp> list;
}
