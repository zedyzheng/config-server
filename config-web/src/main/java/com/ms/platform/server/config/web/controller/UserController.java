package com.ms.platform.server.config.web.controller;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.UserService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.constants.Permissions;
import com.ms.platform.server.config.model.LongId;
import com.ms.platform.server.config.model.SysUser;
import com.ms.platform.server.config.request.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Joey on 2017/4/12 0012.
 */
@RestController
@CrossOrigin
@RequestMapping(path="user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="管理员添加用户")
    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    @ResponseBody
    @Authority(permission = Permissions.ROLE_ADMIN)
    public LongId addUserAndSelectRole(@Valid @RequestBody UserRequest user,
                                       @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return new LongId(userService.addUserAndSelectRole(user));
    }

    /**
     * 管理员重置密码
     *
     * @param passwords
     * @throws Exception
     */
    @ApiOperation(value="管理员重置密码")
    @RequestMapping(path = "/password/reset", method = RequestMethod.POST)
    @Authority(permission = Permissions.ROLE_ADMIN)
    public LongId resetPassword(@Valid @RequestBody PasswordsAdminRequest passwords,
                              @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception{
        userService.resetPassword(passwords);
        return new LongId(passwords.getUserId());
    }

    @ApiOperation("管理员修改用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Authority(permission = Permissions.ROLE_ADMIN)
    public LongId modify(@PathVariable Long id,@Valid @RequestBody UserModifyRequest userRequest,
                       @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        userService.modify(id,userRequest);
        return new LongId(id);
    }

    @ApiOperation("用户详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SysUser detail(@PathVariable Long id) throws Exception {
        return userService.findByUserId(id);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Authority(permission = Permissions.ROLE_ADMIN)
    public LongId delete(@PathVariable Long id,
                       @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        userService.delete(id);
        return new LongId(id);
    }


    @ApiOperation("分页查询")
    @RequestMapping(path = "/pages", method = RequestMethod.POST)
    @Authority(permission = Permissions.ROLE_ADMIN)
    public PageCustom<SysUser> pages(@Valid @RequestBody UserPageRequest pageRequest,
                                     @RequestHeader(name = Constants.AUTHORIZATION) String token) throws BusinessException {
        return userService.pages(pageRequest);
    }

}
