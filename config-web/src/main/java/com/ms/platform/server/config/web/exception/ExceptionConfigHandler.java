package com.ms.platform.server.config.web.exception;

import com.ms.common.bo.exception.AppResponse;
import com.ms.common.bo.exception.BusinessException;
import com.ms.common.web.exception.NoAuthorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * 统一异常处理
 * @author joeyzheng
 * @date 2017年2月14日
 */
@ControllerAdvice(basePackages = {"com.ms.platform","com.ms.common"})
public class ExceptionConfigHandler {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Object handleBusinessException(BusinessException icEx,HttpServletResponse response) {
    	response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return AppResponse.error(icEx);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object handleIllegalArgumentException(IllegalArgumentException icEx,HttpServletResponse response) {
    	response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return AppResponse.error(new BusinessException(BusinessException.Errors.UN_PROCESSABLE, icEx.getMessage()));
    }
    
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Object handleValidationException(ValidationException icEx,HttpServletResponse response) {
    	response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return AppResponse.error(new BusinessException(BusinessException.Errors.UN_PROCESSABLE, icEx.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException icEx,HttpServletResponse response) {
    	response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return AppResponse.error(new BusinessException(BusinessException.Errors.UN_PROCESSABLE, icEx.getMessage()));
    }

    @ExceptionHandler(NoAuthorityException.class)
    @ResponseBody
    public Object handleMethodNoAuthorityException(NoAuthorityException icEx,HttpServletResponse response) {
        if(icEx.getCode() == HttpStatus.UNAUTHORIZED.value()){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return AppResponse.error(new BusinessException(icEx.getCode(), icEx.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleException(Exception ucEx) {
    	logger.error("["+ucEx.getMessage()+"]:{}",ucEx);
        return AppResponse.error(ucEx);
    }

}

