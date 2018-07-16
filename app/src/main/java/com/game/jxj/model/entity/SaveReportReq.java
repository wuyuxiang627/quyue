package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveReportReq {
    /**
     * content : string
     * filePath : string
     * receivePerson : string
     */

    @SerializedName("content")
    public String content;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("receivePerson")
    public String receivePerson;
}
