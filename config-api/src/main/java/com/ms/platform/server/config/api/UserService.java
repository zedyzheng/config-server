package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.model.SysUser;
import com.ms.platform.server.config.request.PasswordsAdminRequest;
import com.ms.platform.server.config.request.UserModifyRequest;
import com.ms.platform.server.config.request.UserPageRequest;
import com.ms.platform.server.config.request.UserRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public interface UserService {

    SysUser findByUserId(long id);

    //SysUser findByUserName(String userName);

    SysUser findByUserNameAndPassword(String userName, String password);

    Long addUserAndSelectRole(@NotNull UserRequest userRequest) throws BusinessException;

    void resetPassword(@NotNull PasswordsAdminRequest passwords);

    void modify(long id,@NotNull UserModifyRequest userRequest) throws BusinessException;

    void delete(Long id) throws BusinessException;

    PageCustom<SysUser> pages(UserPageRequest pageRequest) throws BusinessException;

}
