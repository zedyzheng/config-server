package com.ms.platform.server.config.service.process;

import com.ms.common.web.processor.ThreadParamProcess;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.contexts.EventContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joey on 2017/8/14 0014.
 */
@Component("threadParamProcess")
public class ThreadParamProcessor implements ThreadParamProcess {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void preHandle(HttpServletRequest servletRequest) {
        try {
            Object userIdObj = servletRequest.getAttribute(Constants.CLAIM_KEY_USERID);
            if(null == userIdObj){
                return;
            }
            String userId = (String)userIdObj;
            EventContextUtil.setEventContext(userId);
        }catch (Exception e){
            logger.error("{}",e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest servletRequest) {
        try {
            EventContextUtil.cleanContext();
        }catch (Exception e){
            logger.error("{}",e);
        }
    }
}
