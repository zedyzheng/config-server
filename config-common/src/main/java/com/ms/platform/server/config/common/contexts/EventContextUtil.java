package com.ms.platform.server.config.common.contexts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件上下文工具类
 * Created by Joey on 2017/8/14 0014.
 */
public class EventContextUtil {

    private static final Logger logger = LoggerFactory.getLogger(EventContextUtil.class);

    /**
     * 在调用请求前，创建上下文信息
     */
    public static void setEventContext(String userId) {
        if(null == userId){
            return;
        }
        try {
            ThreadLocalContext threadLocalContext = ThreadLocalContext.get();
            if(null == threadLocalContext){
                ThreadLocalContext.set(new ThreadLocalContext());
                threadLocalContext = ThreadLocalContext.get();
            }
            threadLocalContext.setUserId(userId);
        } catch (Exception e) {
            logger.error("创建时间上下文失败:{}",e);
        }
    }

    public static void cleanContext(){
        try {
            ThreadLocalContext threadLocalContext = ThreadLocalContext.get();
            if (threadLocalContext == null) {
                return;
            }
            ThreadLocalContext.set(null);
        } catch (Exception e) {
            logger.error("创建时间上下文失败:{}",e);
        }
    }

    public static String getValue(){
        ThreadLocalContext threadLocalContext = ThreadLocalContext.get();
        if (threadLocalContext == null) {
            return null;
        }
        return threadLocalContext.getUserId();
    }

}
