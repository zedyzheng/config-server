package com.ms.platform.server.config.web.controller;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.OperatorLogsService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.constants.Permissions;
import com.ms.platform.server.config.model.OperatorLogs;
import com.ms.platform.server.config.request.OperatorLogsPageRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Joey on 2017/8/8 0008.
 */
@RestController
@CrossOrigin
@RequestMapping("logs")
public class OperatorLogsController {

    @Autowired
    private OperatorLogsService operatorLogsService;

    @ApiOperation("查询详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Authority(permission = Permissions.ROLE_LOGS)
    public OperatorLogs getDetail(@PathVariable Long id, @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return operatorLogsService.getDetail(id);
    }

    @ApiOperation("分页查询")
    @RequestMapping(path = "/pages", method = RequestMethod.POST)
    @Authority(permission = Permissions.ROLE_LOGS)
    public PageCustom<OperatorLogs> pages(@Valid @RequestBody OperatorLogsPageRequest pageRequest,
                                          @RequestHeader(name = Constants.AUTHORIZATION) String token) throws BusinessException {
        return operatorLogsService.pages(pageRequest);
    }

}
