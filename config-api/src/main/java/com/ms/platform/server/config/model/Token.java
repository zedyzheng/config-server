package com.ms.platform.server.config.model;

import javax.validation.constraints.NotNull;

/**
 * Token实体，包括token值和过期时间戳
 * Created by Joey on 2017/8/11 0011.
 */
public class Token {

    @NotNull
    private String token;

    private int seconds;

    private Long userId;

    private Token(){

    }

    public Token(String token, int seconds, Long userId) {
        this.token = token;
        this.seconds = seconds;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
