package com.ms.platform.server.config.web.controller;

import com.ms.common.web.annotation.Authority;
import com.ms.platform.server.config.api.AuthService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.model.LongId;
import com.ms.platform.server.config.model.Token;
import com.ms.platform.server.config.request.LoginRequest;
import com.ms.platform.server.config.request.PasswordsRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 注册、登录相关的API
 */
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	/**
	 * 用户名、密码登录
	 * @param loginRequest
	 * @return
	 */
    @ApiOperation(value="用户名、密码登录 ")
	@RequestMapping(method = RequestMethod.POST)
	public Token login(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
		return authService.login(loginRequest);
	}
	
	/**
	 * 刷新token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="刷新token ")
	@RequestMapping(path = "token", method = RequestMethod.GET)
	//@Authority
	public Token refreshToken(
			@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception{
		return authService.refreshToken(token);
	}

	@ApiOperation(value="检查token及role:ADD,UPDATE,DELETE,READ ")
	@RequestMapping(path = "checkToken", method = RequestMethod.GET)
	public LongId checkToken(
			@RequestHeader(name = Constants.AUTHORIZATION) String token,@RequestParam String operationType,@RequestParam(required = false) String appId) throws Exception{
		authService.checkToken(token,operationType,appId);
		return new LongId(0L);
	}

	/**
	 * 修改密码
	 *
	 * @param passwords
	 * @throws Exception
	 */
	@ApiOperation(value="修改密码")
	@RequestMapping(path = "password", method = RequestMethod.POST)
	@Authority
	public LongId modifyPassword(@Valid @RequestBody PasswordsRequest passwords,@RequestHeader(name = Constants.AUTHORIZATION) String token) throws Exception{
		authService.modifyPassword(passwords);
		return new LongId(0L);
	}
}
