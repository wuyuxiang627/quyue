package com.game.jxj.utils;

import okhttp3.RequestBody;

public class OkHttpJsonRequest {

    public static RequestBody getJsonRequestBody(String json) {

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }
}
