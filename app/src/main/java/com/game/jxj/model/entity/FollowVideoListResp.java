package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class FollowVideoListResp {

    /**
     * activityId : string
     * artActivity : {"LOGO":"string","activityId":"string","bannerPath":"string","browseSum":0,"content":"string","createPerson":"string","endDate":"2018-07-02T02:22:00.020Z","enterpriseId":"string","enterpriseName":"string","forwardSum":0,"linkPath":"string","linkPathSum":0,"linkPathTo":0,"logo":"string","manBrowseSum":0,"money":0,"partitionMoney":0,"payStatus":0,"slogan":"string","status":0,"videoCount":0,"videoExamplePath":"string","videoWatchSum":0,"woManBrowseSum":0}
     * coverPath : string
     * coverPathTwo : string
     * describes : string
     * enterpriseFilePath : string
     * enterpriseId : string
     * enterpriseName : string
     * evaluateNum : 0
     * favorNum : 0
     * favorStatus : 0
     * filePath : string
     * filePathId : string
     * followStatus : 0
     * forwardNum : 0
     * isOriginal : 0
     * musicAuthor : string
     * musicCover : string
     * musicDescription : string
     * musicFilePath : string
     * musicType : 0
     * musicUseNum : string
     * partitionMoney : 0
     * recommend : 0
     * status : 0
     * userFilePath : string
     * userId : string
     * userNickName : string
     * videoId : string
     * videoSize : string
     * videoWide : string
     * watchNum : 0
     */

    @SerializedName("activityId")
    public String activityId;
    @SerializedName("artActivity")
    public ArtActivityBean artActivity;
    @SerializedName("coverPath")
    public String coverPath;
    @SerializedName("coverPathTwo")
    public String coverPathTwo;
    @SerializedName("describes")
    public String describes;
    @SerializedName("enterpriseFilePath")
    public String enterpriseFilePath;
    @SerializedName("enterpriseId")
    public String enterpriseId;
    @SerializedName("enterpriseName")
    public String enterpriseName;
    @SerializedName("evaluateNum")
    public int evaluateNum;
    @SerializedName("favorNum")
    public int favorNum;
    @SerializedName("favorStatus")
    public int favorStatus;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("filePathId")
    public String filePathId;
    @SerializedName("followStatus")
    public int followStatus;
    @SerializedName("forwardNum")
    public int forwardNum;
    @SerializedName("isOriginal")
    public int isOriginal;
    @SerializedName("musicAuthor")
    public String musicAuthor;
    @SerializedName("musicCover")
    public String musicCover;
    @SerializedName("musicDescription")
    public String musicDescription;
    @SerializedName("musicFilePath")
    public String musicFilePath;
    @SerializedName("musicType")
    public int musicType;
    @SerializedName("musicUseNum")
    public String musicUseNum;
    @SerializedName("partitionMoney")
    public int partitionMoney;
    @SerializedName("recommend")
    public int recommend;
    @SerializedName("status")
    public int status;
    @SerializedName("userFilePath")
    public String userFilePath;
    @SerializedName("userId")
    public String userId;
    @SerializedName("userNickName")
    public String userNickName;
    @SerializedName("videoId")
    public String videoId;
    @SerializedName("videoSize")
    public String videoSize;
    @SerializedName("videoWide")
    public String videoWide;
    @SerializedName("watchNum")
    public int watchNum;

    public static class ArtActivityBean {
        /**
         * LOGO : string
         * activityId : string
         * bannerPath : string
         * browseSum : 0
         * content : string
         * createPerson : string
         * endDate : 2018-07-02T02:22:00.020Z
         * enterpriseId : string
         * enterpriseName : string
         * forwardSum : 0
         * linkPath : string
         * linkPathSum : 0
         * linkPathTo : 0
         * logo : string
         * manBrowseSum : 0
         * money : 0
         * partitionMoney : 0
         * payStatus : 0
         * slogan : string
         * status : 0
         * videoCount : 0
         * videoExamplePath : string
         * videoWatchSum : 0
         * woManBrowseSum : 0
         */

        @SerializedName("LOGO")
        public String LOGO;
        @SerializedName("activityId")
        public String activityId;
        @SerializedName("bannerPath")
        public String bannerPath;
        @SerializedName("browseSum")
        public int browseSum;
        @SerializedName("content")
        public String content;
        @SerializedName("createPerson")
        public String createPerson;
        @SerializedName("endDate")
        public String endDate;
        @SerializedName("enterpriseId")
        public String enterpriseId;
        @SerializedName("enterpriseName")
        public String enterpriseName;
        @SerializedName("forwardSum")
        public int forwardSum;
        @SerializedName("linkPath")
        public String linkPath;
        @SerializedName("linkPathSum")
        public int linkPathSum;
        @SerializedName("linkPathTo")
        public int linkPathTo;
        @SerializedName("logo")
        public String logo;
        @SerializedName("manBrowseSum")
        public int manBrowseSum;
        @SerializedName("money")
        public int money;
        @SerializedName("partitionMoney")
        public int partitionMoney;
        @SerializedName("payStatus")
        public int payStatus;
        @SerializedName("slogan")
        public String slogan;
        @SerializedName("status")
        public int status;
        @SerializedName("videoCount")
        public int videoCount;
        @SerializedName("videoExamplePath")
        public String videoExamplePath;
        @SerializedName("videoWatchSum")
        public int videoWatchSum;
        @SerializedName("woManBrowseSum")
        public int woManBrowseSum;
    }
}
