package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class UpdateArtUserReq {
    /**
     * filePath : string
     * hometown : string
     * idCard : string
     * isCertified : 0
     * nickName : string
     * position : string
     * realName : string
     * sex : 0
     * signature : string
     */

//    {
//        filePath (string, optional): 头像 ,
//            hometown (string, optional): 家乡 ,
//            idCard (string, optional): 身份证号 ,
//            isCertified (integer, optional): 是否认证 ,
//            nickName (string, optional): 昵称 ,
//            position (string, optional): 地理位置 ,
//            realName (string, optional): 真实姓名 ,
//            sex (integer, optional): 性别 ,
//            signature (string, optional): 个性签名
//    }

    @SerializedName("filePath")
    public String filePath;
    @SerializedName("hometown")
    public String hometown;
    @SerializedName("idCard")
    public String idCard;
    @SerializedName("isCertified")
    public int isCertified;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("position")
    public String position;
    @SerializedName("realName")
    public String realName;
    @SerializedName("sex")
    public int sex;
    @SerializedName("signature")
    public String signature;
}
