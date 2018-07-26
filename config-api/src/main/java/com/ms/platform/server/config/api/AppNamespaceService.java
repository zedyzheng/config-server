package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.common.enums.ServiceConfigStatus;
import com.ms.platform.server.config.model.AppNamespace;
import com.ms.platform.server.config.request.AppNamespacePageRequest;
import com.ms.platform.server.config.request.AppNamespaceRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public interface AppNamespaceService {

    AppNamespace getDetail(Long id);

    AppNamespace findByAppIdAndName(String appId,String name);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    PageCustom<AppNamespace> pages(AppNamespacePageRequest pageRequest) throws BusinessException;

    Long add(@NotNull AppNamespaceRequest appNamespace) throws BusinessException;

    void modify(Long id,@NotNull AppNamespaceRequest appNamespace) throws BusinessException;

    void modifyStatus(Long id, ServiceConfigStatus status) throws BusinessException;

    void configSync(Long appNamespaceId) throws Exception;

    void configSyncMulti(String ids) throws Exception;

}
