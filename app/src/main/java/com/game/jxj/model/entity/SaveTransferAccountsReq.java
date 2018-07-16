package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveTransferAccountsReq {
    /**
     * createAccounts : string
     * createName : string
     * filePath : string
     * money : 0
     * receiveAccounts : string
     * receiveBank : string
     * receiveName : string
     */

    @SerializedName("createAccounts")
    public String createAccounts;
    @SerializedName("createName")
    public String createName;
    @SerializedName("filePath")
    public String filePath;
    @SerializedName("money")
    public int money;
    @SerializedName("receiveAccounts")
    public String receiveAccounts;
    @SerializedName("receiveBank")
    public String receiveBank;
    @SerializedName("receiveName")
    public String receiveName;
}
