package com.game.jxj.model.entity;

import com.gm.utils.StringUtils;

import java.io.Serializable;

/**
 * @author jxj
 * @date 2018/4/24
 */

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -6933225801623155046L;

    private UserInfo() {
    }

    private static UserInfo instance;

    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }


    public String userId;

    public String token = "CPQY9HDUDVN3M7U1KI8BMFQ4HZS2TB6C";


    public boolean isLogined() {

        return !StringUtils.isEmpty(userId) && !token.equals("1");
    }
}




