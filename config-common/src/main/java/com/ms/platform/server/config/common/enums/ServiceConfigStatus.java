package com.ms.platform.server.config.common.enums;

/**
 * 同步状态 0:未同步,1:已同步,2:有修改
 *
 * Created by Joey on 2017/8/7 0007.
 */
public enum ServiceConfigStatus {

    UN_SYNC("未同步"),
    SYNC("已同步"),
    MODIFY("有修改");

    private String desc;

    ServiceConfigStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
