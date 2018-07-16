package com.game.jxj.model.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author jxj
 * @date 2018/6/22
 */
public class Test {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        T t = new T();
        t.a = 1;
        t.b = 2;
        String json = gson.toJson(t);
        System.out.println(json);
    }

    static class T {

        public int a;
        public int b;
    }
}
