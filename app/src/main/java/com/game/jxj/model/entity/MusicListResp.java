package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jxj
 * @date 2018/6/21
 */
public class MusicListResp implements Serializable {


    /**
     * author : string
     * collectionStatus : 0
     * coverPath : string
     * description : string
     * filePath : string
     * filesize : string
     * isHot : 0
     * length : 0
     * musicId : string
     * musicStatus : 0
     * musicType : 0
     */

    @SerializedName("author")
    public String author;
    @SerializedName("collectionStatus")
    public int collectionStatus;
    @SerializedName("coverPath")
    public String coverPath;
    @SerializedName("description")
    public String description;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("filesize")
    public String filesize;
    @SerializedName("isHot")
    public int isHot;
    @SerializedName("length")
    public int length;
    @SerializedName("musicId")
    public String musicId;
    @SerializedName("musicStatus")
    public int musicStatus;
    @SerializedName("musicType")
    public int musicType;
}
