package com.game.jxj.model.entity;

import java.io.Serializable;


/**
 * Created by PC005 on 2017/11/29.
 */

public class HttpResp<T> implements Serializable {

    public String code;
    public String message;
    public T data;

    public Optional<T> transform(){
        return new Optional<>(data);
    }

}
