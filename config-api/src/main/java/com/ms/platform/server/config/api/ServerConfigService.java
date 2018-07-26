package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.model.ServerConfig;
import com.ms.platform.server.config.request.CommonServerConfigRequest;
import com.ms.platform.server.config.request.ServerConfigPageRequest;
import com.ms.platform.server.config.request.ServerConfigRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public interface ServerConfigService {

    ServerConfig getDetail(Long id);

    List<ServerConfig> findByAppNamespaceId(Long appNamespaceId);

    ServerConfig findByAppNamespaceIdAndItemKey(Long appNamespaceId, String itemKey);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    PageCustom<ServerConfig> pages(ServerConfigPageRequest pageRequest) throws BusinessException;

    Long add(@NotNull ServerConfigRequest serverConfig) throws BusinessException;

    void modify(Long id,@NotNull ServerConfigRequest serverConfig) throws BusinessException;

    void delete(Long id) throws BusinessException;

    void uploadConfigFile(Long appNamespaceId,MultipartFile configFile) throws BusinessException;

    void uploadCommonConfigFile(String appNamespaceName, CommonServerConfigRequest serverConfig, MultipartFile configFile) throws BusinessException;

    void downloadConfigFile(Long appNamespaceId,HttpServletResponse response) throws Exception;

    void importConfigFromSpace(Long sourceNamespaceId,Long targetNamespaceId) throws Exception;

}
