package com.ms.platform.server.config.web.controller;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.AppNamespaceService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.model.AppNamespace;
import com.ms.platform.server.config.model.LongId;
import com.ms.platform.server.config.request.AppNamespacePageRequest;
import com.ms.platform.server.config.request.AppNamespaceRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Joey on 2017/4/12 0012.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "namespace")
public class AppNamespaceController {

    @Autowired
    private AppNamespaceService appNamespaceService;

    @ApiOperation("查询详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Authority
    public AppNamespace getDetail(@PathVariable Long id
            , @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return appNamespaceService.getDetail(id);
    }

    @ApiOperation("分页查询")
    @RequestMapping(path = "/pages", method = RequestMethod.POST)
    @Authority
    public PageCustom<AppNamespace> pages(@Valid @RequestBody AppNamespacePageRequest pageRequest
            , @RequestHeader(name = Constants.AUTHORIZATION) String token) throws BusinessException {
        return appNamespaceService.pages(pageRequest);
    }

    @ApiOperation("添加")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @Authority
    public LongId add(@Valid @RequestBody AppNamespaceRequest appNamespace
            , @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return new LongId(appNamespaceService.add(appNamespace));
    }

    @ApiOperation("修改")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Authority
    public LongId modify(@PathVariable Long id,@Valid @RequestBody AppNamespaceRequest appNamespace
            , @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        appNamespaceService.modify(id, appNamespace);
        return new LongId(id);
    }

    @ApiOperation("同步配置")
    @RequestMapping(value = "/syncConfig/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Authority
    public LongId configSync(@PathVariable Long id,
                           @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        appNamespaceService.configSync(id);
        return new LongId(id);
    }

    @ApiOperation("批量同步配置")
    @RequestMapping(value = "/syncConfig/multi/{ids}", method = RequestMethod.GET)
    @ResponseBody
    //@Authority
    public LongId configSyncMulti(@PathVariable String ids,
                             @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        appNamespaceService.configSyncMulti(ids);
        return new LongId(0L);
    }
}
