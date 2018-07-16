package com.game.jxj.model.entity;

import com.game.jxj.config.preference.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * @author jxj
 * @date 2018/6/22
 */
public class MusicListReq  {

    @Override
    public String toString() {
        return "MusicListReq{" +
                "isHot=" + isHot +
                ", length=" + length +
                ", musicStatus=" + musicStatus +
                ", musicType=" + musicType +
                ", page=" + page +
                ", searchName='" + searchName + '\'' +
                '}';
    }

    /**
     * isHot : 0
     * length : 0
     * musicStatus : 0
     * musicType : 0
     * page : 0
     * searchName : string
     */

    @SerializedName("isHot")
    public int isHot;
    @SerializedName("length")
    public int length = Constants.DATA_LIST_SIZE;
    @SerializedName("musicStatus")
    public int musicStatus;
    @SerializedName("musicType")
    public Integer musicType ;
    @SerializedName("page")
    public int page;
    @SerializedName("searchName")
    public String searchName;
}
