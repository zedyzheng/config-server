package com.ms.platform.server.config.web.controller;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.AppService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.model.App;
import com.ms.platform.server.config.model.LongId;
import com.ms.platform.server.config.request.AppPageRequest;
import com.ms.platform.server.config.request.AppRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Joey on 2017/4/12 0012.
 */
@RestController
@CrossOrigin
@RequestMapping(path="app")
public class AppController {

    @Autowired
    private AppService appService;

    @ApiOperation("查询详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Authority
    public App getDetail(@PathVariable Long id,@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return appService.getDetail(id);
    }

    @ApiOperation("分页查询")
    @RequestMapping(path = "/pages",method = RequestMethod.POST)
    @Authority
    public PageCustom<App> pages(@Valid @RequestBody AppPageRequest pageRequest,
                                 @RequestHeader(name = Constants.AUTHORIZATION) String token,
                                 HttpServletRequest servletRequest) throws BusinessException {
        return appService.pages(pageRequest,servletRequest);
    }

    @ApiOperation("添加app")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @Authority
    public LongId add(@Valid @RequestBody AppRequest app,@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return new LongId(appService.add(app));
    }

    @ApiOperation("修改app")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Authority
    public LongId modify(@PathVariable Long id,@Valid @RequestBody AppRequest app,@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        appService.modify(id,app);
        return new LongId(id);
    }
}
