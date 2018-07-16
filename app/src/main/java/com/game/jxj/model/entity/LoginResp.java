package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jxj
 * @date 2017/12/13
 */

public class LoginResp implements Serializable {


    private static final long serialVersionUID = 1408855499980657887L;


    /**
     * agentNo : string
     * filePath : string
     * hometown : string
     * isCertified : 0
     * meToId : 0
     * money : 0
     * nickName : string
     * openID : string
     * position : string
     * roleType : 0
     * sex : 0
     * signature : string
     * status : 0
     * sumPartitionMoney : 0
     * token : string
     * userId : string
     */

    @SerializedName("agentNo")
    public String agentNo;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("hometown")
    public String hometown;
    @SerializedName("isCertified")
    public int isCertified;
    @SerializedName("meToId")
    public int meToId;
    @SerializedName("money")
    public int money;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("openID")
    public String openID;
    @SerializedName("position")
    public String position;
    @SerializedName("roleType")
    public int roleType;
    @SerializedName("sex")
    public int sex;
    @SerializedName("signature")
    public String signature;
    @SerializedName("status")
    public int status;
    @SerializedName("sumPartitionMoney")
    public int sumPartitionMoney;
    @SerializedName("token")
    public String token;
    @SerializedName("userId")
    public String userId;
}
