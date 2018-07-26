package com.ms.platform.server.config.web.interceptor;

import com.ms.common.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by Joey on 2017/4/12 0012.
 */
public class MonitorInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME_KEY="__START_TIME";
    private static final Logger LOG = LoggerFactory.getLogger(MonitorInterceptor.class);
    private static final Logger TIMEOUT_LOG = LoggerFactory.getLogger("monitor_timeout");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(START_TIME_KEY,System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long startTime = (Long)request.getAttribute(START_TIME_KEY);
        long executionTime = System.currentTimeMillis() - startTime;
        if( handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String name = method.getName();
            long standardTime = 6000L;
            if( executionTime > standardTime ){
                TIMEOUT_LOG.warn("方法 {} 执行时间 {}/{}",method,executionTime,standardTime);
            }else{
                TIMEOUT_LOG.info("方法 {} 执行时间 {}/{}",method,executionTime,standardTime);
            }

            if( ex != null ){
                //String body = requestBody(request);
//                EXCEPTION_LOG.warn("\n请求{}出错 参数{} 用时{} ",
//                        request.getRequestURI(),
//                        JSONUtils.toJSON(request.getParameterMap()),String.valueOf(executionTime)
//                );
            }else{
                try{
                    //String body = requestBody(request);
                    LOG.debug("\n请求{} 成功 参数{} 用时{}", request.getRequestURI(),
                            JSONUtils.toJSON(request.getParameterMap()), String.valueOf(executionTime)
                    );
                }catch (Exception e){
                }
            }
        }
        super.afterCompletion(request, response, handler, ex);
    }

}
