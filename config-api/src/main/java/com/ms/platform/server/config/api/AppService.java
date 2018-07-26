package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.model.App;
import com.ms.platform.server.config.request.AppPageRequest;
import com.ms.platform.server.config.request.AppRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public interface AppService {

    App getDetail(Long id);

    App findByAppId(String appId);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    PageCustom<App> pages(AppPageRequest pageRequest,HttpServletRequest servletRequest) throws BusinessException;

    Long add(@NotNull AppRequest app) throws BusinessException;

    void modify(Long id,@NotNull AppRequest app) throws BusinessException;

}
