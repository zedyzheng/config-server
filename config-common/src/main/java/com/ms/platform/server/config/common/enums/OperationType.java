package com.ms.platform.server.config.common.enums;

/**
 */
public enum OperationType {

    ADD("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    LOAD("加载"),
    READ("读取");


    private String desc;

    OperationType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 通过name获取结果码
     */
    public static OperationType getResultEnum(String code) {
        for (OperationType result : values()) {
            if (result.name().equals(code)) {
                return result;
            }
        }
        return null;
    }
}
