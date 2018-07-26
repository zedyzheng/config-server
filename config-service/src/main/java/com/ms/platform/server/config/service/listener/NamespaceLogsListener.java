package com.ms.platform.server.config.service.listener;


import com.ms.common.jpa.EntityCrudEvent;
import com.ms.common.utils.JSONUtils;
import com.ms.platform.server.config.common.enums.OperationType;
import com.ms.platform.server.config.dal.entity.AppNamespaceEntity;
import com.ms.platform.server.config.dal.entity.OperatorLogsEntity;
import com.ms.platform.server.config.dal.repository.OperatorLogsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by fankenie on 2016/12/20.
 */
@Component
public class NamespaceLogsListener implements ApplicationListener<EntityCrudEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private OperatorLogsDao operatorLogsDao;

	@Override
    public void onApplicationEvent(EntityCrudEvent entityCrudEvent) {

        switch (entityCrudEvent.getEventType()){
            case AFTER_CREATE:
				touchForUpdate(entityCrudEvent.getSource(),1);
                break;
            case AFTER_UPDATE:
            	touchForUpdate(entityCrudEvent.getSource(),2);
                break;
        }
    }

	private void touchForUpdate(Object target,int flag){
    	if(null==target){
			return;
		}
    	if(!(target instanceof AppNamespaceEntity)){
    		return;
    	}
		AppNamespaceEntity appNamespaceEntity = (AppNamespaceEntity)target;
    	if(null == appNamespaceEntity){
    		return;
		}
		try {
			OperatorLogsEntity logsEntity = new OperatorLogsEntity();
			logsEntity.setEntityName(AppNamespaceEntity.class.getSimpleName());
			if(flag == 3){
				logsEntity.setOperatorName(OperationType.DELETE.name());
			}else if(flag == 1){
				logsEntity.setOperatorName(OperationType.ADD.name());
			}else{
				logsEntity.setOperatorName(OperationType.UPDATE.name());
			}
			logsEntity.setDescription(JSONUtils.toJSON(appNamespaceEntity));
			operatorLogsDao.save(logsEntity);
		} catch (Exception e) {
			logger.error("日志异常.",e);
		}
	}

}
