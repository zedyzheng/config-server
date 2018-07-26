package com.ms.platform.server.config.web.controller;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.RoleService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.model.LongId;
import com.ms.platform.server.config.model.Role;
import com.ms.platform.server.config.request.AppRequest;
import com.ms.platform.server.config.request.RoleRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Joey on 2017/11/10 0010.
 */
@RestController
@CrossOrigin
@RequestMapping(path="role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("添加role")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @Authority
    public LongId add(@Valid @RequestBody RoleRequest roleRequest, @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return new LongId(roleService.add(roleRequest));
    }

    @ApiOperation("修改role")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Authority
    public LongId modify(@PathVariable Long id,@Valid @RequestBody RoleRequest roleRequest,@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        roleService.modify(id,roleRequest);
        return new LongId(id);
    }

    @ApiOperation("权限列表")
    @RequestMapping(path = "/all", method = RequestMethod.POST)
    public List<Role> findAll() throws BusinessException {
        return roleService.findAll();
    }

}
