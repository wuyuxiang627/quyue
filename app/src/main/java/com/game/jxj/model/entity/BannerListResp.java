package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class BannerListResp {
    /**
     * content : string
     * filePath : string
     * status : 0
     */

    @SerializedName("content")
    public String content;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("status")
    public int status;
}
