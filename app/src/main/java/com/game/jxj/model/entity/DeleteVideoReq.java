package com.game.jxj.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteVideoReq {


    @SerializedName("videoIdList")
    public List<String> videoIdList;
}
