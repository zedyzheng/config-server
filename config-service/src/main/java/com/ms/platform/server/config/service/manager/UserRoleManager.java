package com.ms.platform.server.config.service.manager;

import com.ms.common.bo.exception.BusinessException;
import com.ms.platform.server.config.request.UserModifyRequest;
import com.ms.platform.server.config.request.UserRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public interface UserRoleManager {

    Long add(@NotNull UserRequest userRequest) throws BusinessException;

    void modify(long id,@NotNull UserModifyRequest userRequest) throws BusinessException;

    List<String> findRolesByUserId(@NotNull String roleIds) throws BusinessException;
}
