package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class DeleteFavorReq extends FavorReq {


    /**
     * receivePerson : string
     */

    /**
     * {
     receivePerson (string, optional): 接收人 ,
     videoId (string, optional): 视频ID
     }
     */

    @SerializedName("receivePerson")
    public String receivePerson;

}
