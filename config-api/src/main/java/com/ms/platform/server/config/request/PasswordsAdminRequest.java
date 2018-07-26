package com.ms.platform.server.config.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public class PasswordsAdminRequest implements Serializable {

    @NotNull
    private Long userId;

    @NotBlank(message ="密码不能为空")
    private String newPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
