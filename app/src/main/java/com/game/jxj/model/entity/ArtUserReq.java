package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ArtUserReq implements Serializable{
    /**
     * cooperationStatus : 0
     * favorNum : 0
     * favorVideoNum : 0
     * filePath : string
     * followHeNum : 0
     * followMyNum : 0
     * followStatus : 0
     * hometown : string
     * idCard : string
     * isCertified : 0
     * meToId : 0
     * money : 0
     * myVideoNum : 0
     * nickName : string
     * realName : string
     * roleType : 0
     * sex : 0
     * signature : string
     * userId : string
     */

    @SerializedName("cooperationStatus")
    public int cooperationStatus;
    @SerializedName("favorNum")
    public int favorNum;
    @SerializedName("favorVideoNum")
    public int favorVideoNum;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("followHeNum")
    public int followHeNum;
    @SerializedName("followMyNum")
    public int followMyNum;
    @SerializedName("followStatus")
    public int followStatus;
    @SerializedName("hometown")
    public String hometown;
    @SerializedName("idCard")
    public String idCard;
    @SerializedName("isCertified")
    public int isCertified;
    @SerializedName("meToId")
    public int meToId;
    @SerializedName("money")
    public int money;
    @SerializedName("myVideoNum")
    public int myVideoNum;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("realName")
    public String realName;
    @SerializedName("roleType")
    public int roleType;
    @SerializedName("sex")
    public int sex;
    @SerializedName("signature")
    public String signature;
    @SerializedName("userId")
    public String userId;
}
