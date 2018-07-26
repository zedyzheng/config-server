package com.ms.platform.server.config.service.auditor;

import com.ms.platform.server.config.common.contexts.EventContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by Joey on 2017/8/7 0007.
 */
@Component
public class UserAuditorAware implements AuditorAware<Long> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Long getCurrentAuditor() {
        try {
            String userId = EventContextUtil.getValue();
            if(null!=userId){
                return Long.valueOf(userId);
            }
        }catch (Exception e){
            logger.error("{}",e);
        }
        return null;
    }

}
