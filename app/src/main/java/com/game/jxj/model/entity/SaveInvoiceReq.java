package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

public class SaveInvoiceReq {
    /**
     * content : string
     * head : string
     * mailbox : string
     * taxNum : string
     * type : string
     */

    @SerializedName("content")
    public String content;
    @SerializedName("head")
    public String head;
    @SerializedName("mailbox")
    public String mailbox;
    @SerializedName("taxNum")
    public String taxNum;
    @SerializedName("type")
    public String type;
}
