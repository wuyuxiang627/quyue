package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveOpinioReq {
    /**
     * content : string
     * name : string
     * telephone : string
     */

    @SerializedName("content")
    public String content;
    @SerializedName("name")
    public String name;
    @SerializedName("telephone")
    public String telephone;
}
