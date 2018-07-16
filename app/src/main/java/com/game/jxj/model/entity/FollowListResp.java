package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class FollowListResp {

    /**
     * createPerson : string
     * filePath : string
     * followId : string
     * followStatus : 0
     * meToId : string
     * nickName : string
     * receivePerson : string
     */

    @SerializedName("createPerson")
    public String createPerson;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("followId")
    public String followId;
    @SerializedName("followStatus")
    public int followStatus;
    @SerializedName("meToId")
    public String meToId;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("receivePerson")
    public String receivePerson;
}
