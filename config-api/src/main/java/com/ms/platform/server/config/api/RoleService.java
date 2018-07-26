package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.platform.server.config.model.Role;
import com.ms.platform.server.config.request.RoleRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Auth Joey
 * @date 16:27 2017/11/10 0010
 *
 */
public interface RoleService {

    List<Role> findAll() throws BusinessException;

    Long add(RoleRequest roleRequest) throws BusinessException;

    void modify(Long id,RoleRequest roleRequest) throws BusinessException;

    List<Role> findRolesByIds(@NotNull String roleIds) throws BusinessException;
}
