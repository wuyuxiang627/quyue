package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveCertifiedEnterpriseReq {
    /**
     * companyName : string
     * idCard : string
     * legalPersonName : string
     * legalPersonPhone : string
     * taxpayer : string
     */

    @SerializedName("companyName")
    public String companyName;
    @SerializedName("idCard")
    public String idCard;
    @SerializedName("legalPersonName")
    public String legalPersonName;
    @SerializedName("legalPersonPhone")
    public String legalPersonPhone;
    @SerializedName("taxpayer")
    public String taxpayer;
}
