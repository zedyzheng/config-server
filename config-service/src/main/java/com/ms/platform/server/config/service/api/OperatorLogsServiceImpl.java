package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.api.OperatorLogsService;
import com.ms.platform.server.config.dal.entity.AppEntity;
import com.ms.platform.server.config.dal.entity.OperatorLogsEntity;
import com.ms.platform.server.config.dal.repository.OperatorLogsDao;
import com.ms.platform.server.config.model.App;
import com.ms.platform.server.config.model.OperatorLogs;
import com.ms.platform.server.config.request.OperatorLogsPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/8/3 0003.
 */
@Service
public class OperatorLogsServiceImpl implements OperatorLogsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OperatorLogsDao operatorLogsDao;

    @Override
    public OperatorLogs getDetail(Long id) {
        OperatorLogsEntity operatorLogsEntity = operatorLogsDao.findOne(id);
        OperatorLogs operatorLogs = new OperatorLogs();
        BeanUtils.copyProperties(operatorLogsEntity,operatorLogs);
        return operatorLogs;
    }

    @Override
    public PageCustom<OperatorLogs> pages(OperatorLogsPageRequest pageRequest) throws BusinessException {
        if(pageRequest.getPageSize()<=0 || pageRequest.getPageSize()>200){
            throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "pageSize最大值为200");
        }

        Specification<OperatorLogsEntity> specification = (root, query, cb) -> {
            List<Predicate> all = new ArrayList<>();
            // 获取未删除的属性
            if (null!=pageRequest.getId() && pageRequest.getId()>0) {
                all.add(cb.equal(root.get("id").as(Long.class), pageRequest.getId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getEntityName())) {
                all.add(cb.equal(root.get("entityName").as(String.class), pageRequest.getEntityName()));
            }
            if (!StringUtils.isEmpty(pageRequest.getOperatorName())) {
                all.add(cb.equal(root.get("operatorName").as(String.class), pageRequest.getOperatorName()));
            }
            if (!StringUtils.isEmpty(pageRequest.getDescription())) {
                all.add(cb.like(root.get("description").as(String.class),"%" +  pageRequest.getDescription()+ "%") );
            }
            Predicate[] predicates = new Predicate[all.size()];
            return cb.and(all.toArray(predicates));
        };

        Pageable pageable = new PageRequest(pageRequest.getPageIndex() - 1, pageRequest.getPageSize(),
                new Sort(Sort.Direction.DESC, "lastModifiedDate"));

        List<OperatorLogs> excelTaskList = new ArrayList<>();
        Page<OperatorLogsEntity> pages = operatorLogsDao.findAll(specification, pageable);

        if (pages != null && pages.getSize() > 0) {
            pages.forEach(appEntity -> {
                OperatorLogs operatorLogs = new OperatorLogs();
                BeanUtils.copyProperties(appEntity,operatorLogs);
                excelTaskList.add(operatorLogs);
            });
        }

        return new PageCustom<>(pages != null ? pages.getTotalElements() : 0, pageRequest, excelTaskList);
    }

    @Override
    public void addLogs(OperatorLogs operatorLogs){
        if(null==operatorLogs){
            return;
        }
        OperatorLogsEntity operatorLogsEntity = new OperatorLogsEntity();
        operatorLogsEntity.setEntityName(operatorLogs.getEntityName());
        operatorLogsEntity.setOperatorName(operatorLogs.getOperatorName());
        operatorLogsEntity.setDescription(operatorLogs.getDescription());
        try{
            operatorLogsDao.save(operatorLogsEntity);
        }catch (Exception e){
            logger.error("{}",e);
        }
    }
}
