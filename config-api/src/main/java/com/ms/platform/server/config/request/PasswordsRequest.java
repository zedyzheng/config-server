package com.ms.platform.server.config.request;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public class PasswordsRequest implements Serializable {

    @NotEmpty
    private String userName;

    @NotBlank(message ="密码不能为空")
    private String oldPassword;

    @NotBlank(message ="密码不能为空")
    private String newPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
