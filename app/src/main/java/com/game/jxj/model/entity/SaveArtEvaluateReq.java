package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveArtEvaluateReq {
    /**
     * content : string
     * reply : string
     * videoId : string
     */

    @SerializedName("content")
    public String content;
    @SerializedName("reply")
    public String reply;
    @SerializedName("videoId")
    public String videoId;
}
