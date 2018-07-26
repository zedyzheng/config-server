package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.Md5Utils;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.api.AppNamespaceService;
import com.ms.platform.server.config.api.ServerConfigSynService;
import com.ms.platform.server.config.common.enums.ServiceConfigStatus;
import com.ms.platform.server.config.common.utils.ConfigSupport;
import com.ms.platform.server.config.dal.entity.AppNamespaceEntity;
import com.ms.platform.server.config.dal.repository.AppNamespaceDao;
import com.ms.platform.server.config.model.AppNamespace;
import com.ms.platform.server.config.request.AppNamespacePageRequest;
import com.ms.platform.server.config.request.AppNamespaceRequest;
import com.ms.platform.server.config.service.convert.AppNamespaceConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/8/3 0003.
 */
@Service
public class AppNamespaceServiceImpl implements AppNamespaceService {

    @Autowired
    private AppNamespaceDao appNamespaceDao;

    @Autowired
    private ServerConfigSynService serverConfigSynService;

    @Override
    public AppNamespace getDetail(Long id) {

        AppNamespaceEntity appNamespaceEntity = appNamespaceDao.findOne(id);
        if(null == appNamespaceEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录不存在");
        }
        AppNamespace appNamespace = new AppNamespace();
        BeanUtils.copyProperties(appNamespaceEntity,appNamespace);
        return appNamespace;
    }

    @Override
    public AppNamespace findByAppIdAndName(String appId, String name) {
        AppNamespaceEntity appNamespaceEntity = appNamespaceDao.findByAppIdAndName(appId,name);
        if(null == appNamespaceEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录不存在");
        }
        AppNamespace appNamespace = new AppNamespace();
        BeanUtils.copyProperties(appNamespaceEntity,appNamespace);
        return appNamespace;
    }

    @Override
    public PageCustom<AppNamespace> pages(AppNamespacePageRequest pageRequest) throws BusinessException {
        if(pageRequest.getPageSize()<=0 || pageRequest.getPageSize()>200){
            throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "pageSize最大值为200");
        }

        Specification<AppNamespaceEntity> specification = (root, query, cb) -> {
            List<Predicate> all = new ArrayList<>();
            // 获取未删除的属性
            if (null!=pageRequest.getId() && pageRequest.getId()>0) {
                all.add(cb.equal(root.get("id").as(Long.class), pageRequest.getId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getAppId())) {
                all.add(cb.equal(root.get("appId").as(String.class), pageRequest.getAppId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getName())) {
                all.add(cb.equal(root.get("name").as(String.class),pageRequest.getName()) );
            }
            if (!StringUtils.isEmpty(pageRequest.getStatus())) {
                all.add(cb.equal(root.get("status").as(String.class), pageRequest.getStatus()));
            }
            Predicate[] predicates = new Predicate[all.size()];
            return cb.and(all.toArray(predicates));
        };

        Pageable pageable = new PageRequest(pageRequest.getPageIndex() - 1, pageRequest.getPageSize(),
                new Sort(Sort.Direction.DESC, "lastModifiedDate"));

        List<AppNamespace> excelTaskList = new ArrayList<>();
        Page<AppNamespaceEntity> pages = appNamespaceDao.findAll(specification, pageable);

        if (pages != null && pages.getSize() > 0) {
            pages.forEach(appEntity -> {
                AppNamespace app = new AppNamespace();
                BeanUtils.copyProperties(appEntity,app);
                excelTaskList.add(app);
            });
        }

        return new PageCustom<>(pages != null ? pages.getTotalElements() : 0, pageRequest, excelTaskList);
    }

    @Transactional
    @Override
    public Long add(@NotNull AppNamespaceRequest appNamespace) throws BusinessException {
        Assert.notNull(appNamespace, "appNamespace不能为空");
        if(StringUtils.isEmpty(appNamespace.getPassword())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"password不能为空");
        }

        if(!ConfigSupport.isNamespaceName(appNamespace.getName())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"namespace的名称只能是字母");
        }

        if(null!=appNamespaceDao.findByAppIdAndName(appNamespace.getAppId(),appNamespace.getName())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录已经存在");
        }

        AppNamespaceEntity appEntity = AppNamespaceConverter.toAppEntity(appNamespace);
        //AuditorProcess.fillAuditEntityAuditor(appEntity,true);
        appNamespaceDao.save(appEntity);
        return appEntity.getId();
    }

    @Transactional
    @Override
    public void modify(Long id, AppNamespaceRequest appNamespace) throws BusinessException {
        Assert.notNull(appNamespace, "appNamespace不能为空");

        AppNamespaceEntity appEntity = appNamespaceDao.findOne(id);
        if (null == appEntity) {
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "记录未找到");
        }
        appEntity.setComment(appNamespace.getComment());
        if(!StringUtils.isEmpty(appNamespace.getPassword())){
            appEntity.setPassword(Md5Utils.getMD5(appNamespace.getPassword()));
            appEntity.setStatus(ServiceConfigStatus.MODIFY.ordinal());
        }
        //AuditorProcess.fillAuditEntityAuditor(appEntity,false);
        appNamespaceDao.save(appEntity);
    }

    @Transactional
    @Override
    public void modifyStatus(Long id, ServiceConfigStatus status) throws BusinessException {
        AppNamespaceEntity appEntity = appNamespaceDao.findOne(id);
        if (null == appEntity) {
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "记录未找到");
        }
//        if(appEntity.getStatus() == ServiceConfigStatus.UN_SYNC.ordinal()){
//            return;
//        }
        //AuditorProcess.fillAuditEntityAuditor(appEntity,false);
        appEntity.setStatus(status.ordinal());
        appNamespaceDao.save(appEntity);
    }

    @Override
    public void configSync(Long appNamespaceId) throws Exception {
        serverConfigSynService.configSync(appNamespaceId);
    }

    @Override
    public void configSyncMulti(String ids) throws Exception {

        String[] namespaceIds = ids.split(",");
        for (String namespaceId:namespaceIds) {
            if(null!=namespaceId){
                serverConfigSynService.configSync(Long.valueOf(namespaceId));
            }
        }

    }
}
