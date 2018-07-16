package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jxj
 * @date 2018/7/4
 */
public class VideoListResp {


    /**
     * total : 22
     * list : []
     */

    @SerializedName("total")
    public int total;
    @SerializedName("list")
    public List<VideoResp> list;
}
