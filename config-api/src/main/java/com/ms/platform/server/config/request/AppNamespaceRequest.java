package com.ms.platform.server.config.request;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Joey on 2017/8/8 0008.
 */
public class AppNamespaceRequest implements Serializable {

    //环境变量 dev,test,pre,prod
    @ApiModelProperty("环境变量")
    @NotEmpty
    private String name;

    //注释
    private String comment;

    @ApiModelProperty("访问密码")
    //@NotEmpty
    private String password;

    //appId
    @NotEmpty
    private String appId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
