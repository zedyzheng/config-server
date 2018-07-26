package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.platform.server.config.common.enums.OperationType;
import com.ms.platform.server.config.model.Token;
import com.ms.platform.server.config.request.LoginRequest;
import com.ms.platform.server.config.request.PasswordsRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public interface AuthService {

    Token login(@NotNull LoginRequest loginRequest) throws BusinessException;

    long getTokenUserId(@NotNull String strToken) throws BusinessException;

    Token refreshToken(@NotNull String strToken) throws BusinessException;

    //void logout(@NotNull String strToken);

    void modifyPassword(@NotNull PasswordsRequest passwords) throws BusinessException;

    void checkToken(String strToken,String strOperationType,String appId) throws BusinessException;
}
