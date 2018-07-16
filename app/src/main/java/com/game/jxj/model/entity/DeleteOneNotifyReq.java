package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class DeleteOneNotifyReq {
    /**
     * content : string
     * notifyId : string
     * readStatus : 0
     */

    @SerializedName("content")
    public String content;
    @SerializedName("notifyId")
    public String notifyId;
    @SerializedName("readStatus")
    public int readStatus;
}
