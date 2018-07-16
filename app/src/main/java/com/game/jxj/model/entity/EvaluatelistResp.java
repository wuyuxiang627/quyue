package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class EvaluatelistResp {
    /**
     * content : string
     * createDate : 2018-07-02T02:21:59.751Z
     * createPerson : string
     * evaluateId : string
     * favorNum : 0
     * favorStatus : 0
     * filePath : string
     * nickName : string
     * replyContent : string
     * replyNickName : string
     */

    @SerializedName("content")
    public String content;
    @SerializedName("createDate")
    public String createDate;
    @SerializedName("createPerson")
    public String createPerson;
    @SerializedName("evaluateId")
    public String evaluateId;
    @SerializedName("favorNum")
    public int favorNum;
    @SerializedName("favorStatus")
    public int favorStatus;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("replyContent")
    public String replyContent;
    @SerializedName("replyNickName")
    public String replyNickName;
}
