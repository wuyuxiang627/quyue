package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class ArtUserListReq {

    /**
     * length : 0
     * page : 0
     * roleType : 0
     *  {
     length (integer, optional): 每页条目数 ,
     page (integer, optional): 页码 ,
     roleType (integer, optional): 身份 0个人 1企业
     }
     */

    @SerializedName("length")
    public int length;
    @SerializedName("page")
    public int page;
    @SerializedName("roleType")
    public int roleType;
}
