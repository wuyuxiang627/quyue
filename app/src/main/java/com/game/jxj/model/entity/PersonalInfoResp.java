package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jxj
 * @date 2018/4/25
 */

public class PersonalInfoResp implements Serializable {

    /**
     * id : 100001
     * status : 1
     * realName : 昵称111
     * avatarPath : null
     * token : null
     */

    @SerializedName("id")
    public int id;
    @SerializedName("status")
    public int status;
    @SerializedName("realName")
    public String realName;
    @SerializedName("avatarPath")
    public String avatarPath;
    @SerializedName("token")
    public String token;
}
