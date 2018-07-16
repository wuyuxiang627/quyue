package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveuserVideoReq {
    /**
     * coverPath : string
     * describes : string
     * filePath : string
     * isOriginal : 0
     * musicId : string
     * userId : string
     */

    @SerializedName("coverPath")
    public String coverPath;
    @SerializedName("describes")
    public String describes;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("isOriginal")
    public int isOriginal;
    @SerializedName("musicId")
    public String musicId;
    @SerializedName("userId")
    public String userId;
}
