package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActivityInfoResp implements Serializable {

    /**
     * LOGO : string
     * activityId : string
     * bannerPath : string
     * browseSum : 0
     * content : string
     * couponReceiveStatus : 0
     * couponStatus : 0
     * couponUseNum : 0
     * couponsEndDate : 2018-07-04T06:32:22.160Z
     * couponsName : string
     * couponsNum : 0
     * couponsStartDate : 2018-07-04T06:32:22.160Z
     * createPerson : string
     * endDate : 2018-07-04T06:32:22.160Z
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
    @SerializedName("couponReceiveStatus")
    public int couponReceiveStatus;
    @SerializedName("couponStatus")
    public int couponStatus;
    @SerializedName("couponUseNum")
    public int couponUseNum;
    @SerializedName("couponsEndDate")
    public String couponsEndDate;
    @SerializedName("couponsName")
    public String couponsName;
    @SerializedName("couponsNum")
    public int couponsNum;
    @SerializedName("couponsStartDate")
    public String couponsStartDate;
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
