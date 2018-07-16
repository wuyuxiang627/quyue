package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jxj
 * @date 2017/12/13
 */

public class RegisterResp implements Serializable {


    /**
     * id : 100001
     * role_id : 0
     * role_name : null
     * userName : 13641168496
     * password : 9030aa320c86d85e8c7475ae36f98c4c
     * randomcode : 220599
     * status : 0
     * realName : null
     * create_person : null
     * create_date : 2018-04-23 18:28
     * token : JY9LAA92G709QVSV2HQ3DFZF4IMBV5TH
     */

    @SerializedName("id")
    public int id;
    @SerializedName("role_id")
    public int roleId;
    @SerializedName("role_name")
    public Object roleName;
    @SerializedName("userName")
    public String userName;
    @SerializedName("password")
    public String password;
    @SerializedName("randomcode")
    public String randomcode;
    @SerializedName("status")
    public int status;
    @SerializedName("realName")
    public Object realName;
    @SerializedName("create_person")
    public Object createPerson;
    @SerializedName("create_date")
    public String createDate;
    @SerializedName("token")
    public String token;
}
