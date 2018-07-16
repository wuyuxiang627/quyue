package com.game.jxj.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * It.Boss 红叶-QiYe
 */
public class MGson {
    public static final  Gson mGson = new Gson();
    public static <T> T fromJson(String str,Class<T> tClass){
        return mGson.fromJson(str,tClass);
    }
    public static <T> String toJson(T bean){
       return mGson.toJson(bean);
    }
    public static String get(String str,String key){
        try {
            JSONObject jsonObject = new JSONObject(str);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
