package com.ms.platform.server.config.web.controller;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.ServerConfigService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.model.LongId;
import com.ms.platform.server.config.model.ServerConfig;
import com.ms.platform.server.config.request.CommonServerConfigRequest;
import com.ms.platform.server.config.request.ServerConfigPageRequest;
import com.ms.platform.server.config.request.ServerConfigRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2017/4/12 0012.
 */
@RestController
@CrossOrigin
@RequestMapping(path="serviceconfig")
public class ServiceConfigController {

    @Autowired
    private ServerConfigService serverConfigService;

    @ApiOperation("查询详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Authority
    public ServerConfig getDetail(@PathVariable Long id,
                                  @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return serverConfigService.getDetail(id);
    }

    @ApiOperation("分页查询")
    @RequestMapping(path = "/pages",method = RequestMethod.POST)
    @Authority
    public PageCustom<ServerConfig> pages(@Valid @RequestBody ServerConfigPageRequest pageRequest,
                                          @RequestHeader(name = Constants.AUTHORIZATION) String token) throws BusinessException {
        return serverConfigService.pages(pageRequest);
    }

    @ApiOperation("添加")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @Authority
    public LongId add(@Valid @RequestBody ServerConfigRequest serverConfig,
                      @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        return new LongId(serverConfigService.add(serverConfig));
    }

    @ApiOperation(value="配置文件上传")
    @RequestMapping(path = "space/{appNamespaceId}", method = RequestMethod.POST)
    @Authority
    public Map<String,Long> uploadConfigFile(@PathVariable Long appNamespaceId,
                                             @RequestParam MultipartFile configFile,
                                             @RequestHeader(name = Constants.AUTHORIZATION) String token) {
        serverConfigService.uploadConfigFile(appNamespaceId,configFile);
        Map<String,Long> result = new HashMap<>();
        result.put("id",0L);
        return result;
    }

    @ApiOperation(value="公共配置文件上传")
    @RequestMapping(path = "spaceCommon/{appNamespaceName}", method = RequestMethod.POST)
    @Authority
    public Map<String,Long> uploadCommonConfigFile(@PathVariable String appNamespaceName,
                                             @RequestParam MultipartFile configFile,@Valid @RequestBody CommonServerConfigRequest serverConfig,
                                             @RequestHeader(name = Constants.AUTHORIZATION) String token) {
        serverConfigService.uploadCommonConfigFile(appNamespaceName,serverConfig,configFile);
        Map<String,Long> result = new HashMap<>();
        result.put("id",0L);
        return result;
    }

    @ApiOperation(value="配置文件下载")
    @RequestMapping(path = "download/{appNamespaceId}", method = RequestMethod.GET)
    @Authority
    public void downloadConfigFile(@PathVariable Long appNamespaceId,
                                   HttpServletResponse response,@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        serverConfigService.downloadConfigFile(appNamespaceId,response);
    }

    @ApiOperation("从其他空间导入配置")
    @RequestMapping(value = "import/{targetNamespaceId}/{sourceNamespaceId}", method = RequestMethod.GET)
    @ResponseBody
    @Authority
    public LongId importConfigFromSpace(@PathVariable Long sourceNamespaceId,@PathVariable Long targetNamespaceId,
                         @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        serverConfigService.importConfigFromSpace(sourceNamespaceId,targetNamespaceId);
        return new LongId(0L);
    }

    @ApiOperation("修改")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Authority
    public LongId modify(@PathVariable Long id,@Valid @RequestBody ServerConfigRequest serverConfig,
                       @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        serverConfigService.modify(id,serverConfig);
        return new LongId(id);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Authority
    public LongId delete(@PathVariable Long id,
                       @RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception {
        serverConfigService.delete(id);
        return new LongId(id);
    }

}
