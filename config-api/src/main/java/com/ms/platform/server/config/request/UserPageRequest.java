package com.ms.platform.server.config.request;

import com.ms.common.utils.page.PageRequestCustom;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class UserPageRequest extends PageRequestCustom {

    private Long id;

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
