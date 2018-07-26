package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.api.AppService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.utils.ConfigSupport;
import com.ms.platform.server.config.dal.entity.AppEntity;
import com.ms.platform.server.config.dal.entity.UserEntity;
import com.ms.platform.server.config.dal.repository.AppDao;
import com.ms.platform.server.config.dal.repository.UserDao;
import com.ms.platform.server.config.model.App;
import com.ms.platform.server.config.request.AppPageRequest;
import com.ms.platform.server.config.request.AppRequest;
import com.ms.platform.server.config.service.convert.AppConverter;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/8/3 0003.
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppDao appDao;
    @Autowired
    private UserDao userDao;

    @Override
    public App getDetail(Long id) {

        AppEntity appEntity = appDao.findOne(id);
        if(null == appEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录不存在");
        }
        App app = new App();
        BeanUtils.copyProperties(appEntity,app);
        return app;
    }

    @Override
    public App findByAppId(String appId) {
        AppEntity appEntity = appDao.findByAppId(appId);
        if(null == appEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录不存在");
        }
        App app = new App();
        BeanUtils.copyProperties(appEntity,app);
        return app;
    }

    @Override
    public PageCustom<App> pages(AppPageRequest pageRequest,HttpServletRequest servletRequest) throws BusinessException {
        if(pageRequest.getPageSize()<=0 || pageRequest.getPageSize()>200){
            throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "pageSize最大值为200");
        }

        Specification<AppEntity> specification = (root, query, cb) -> {
            List<Predicate> all = new ArrayList<>();
            // 获取未删除的属性
            if (null!=pageRequest.getId() && pageRequest.getId()>0) {
                all.add(cb.equal(root.get("id").as(Long.class), pageRequest.getId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getAppId())) {
                all.add(cb.equal(root.get("appId").as(String.class), pageRequest.getAppId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getName())) {
                all.add(cb.like(root.get("name").as(String.class),"%" +  pageRequest.getName()+ "%") );
            }

//            Object queryFlagObj = servletRequest.getAttribute(Constants.QUERY_FLAG);
//            Object userIdObj = servletRequest.getAttribute(Constants.CLAIM_KEY_USERID);
//            if(null!=queryFlagObj && null!=userIdObj){
//                String queryFlag = (String)queryFlagObj;
//                Long userId = (Long)userIdObj;
//                if(!queryFlag.trim().equals("false") && null!=userId && userId>0){
//
//                    UserEntity userEntity = userDao.findOne(userId);
//                    if(null!=userEntity){
//                        //all.add(cb.equal(root.get("ownerName").as(String.class),userEntity.getUserName()));
//                    }
//                }
//            }
            Predicate[] predicates = new Predicate[all.size()];
            return cb.and(all.toArray(predicates));
        };

        Pageable pageable = new PageRequest(pageRequest.getPageIndex() - 1, pageRequest.getPageSize(),
                new Sort(Sort.Direction.DESC, "lastModifiedDate"));

        List<App> excelTaskList = new ArrayList<>();
        Page<AppEntity> pages = appDao.findAll(specification, pageable);

        if (pages != null && pages.getSize() > 0) {
            pages.forEach(appEntity -> {
                App app = new App();
                BeanUtils.copyProperties(appEntity,app);
                excelTaskList.add(app);
            });
        }

        return new PageCustom<>(pages != null ? pages.getTotalElements() : 0, pageRequest, excelTaskList);
    }

    @Transactional
    @Override
    public Long add(@NotNull AppRequest app) throws BusinessException {

        if(!ConfigSupport.isAppId(app.getAppId())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"appId只能包含字母和点号");
        }

        if(null!=appDao.findByAppId(app.getAppId())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"appId已经存在");
        }

        AppEntity appEntity = AppConverter.toAppEntity(app);
        //AuditorProcess.fillAuditEntityAuditor(appEntity,true);
        appDao.save(appEntity);
        return appEntity.getId();
    }

    @Transactional
    @Override
    public void modify(Long id, AppRequest app) throws BusinessException {
        Assert.notNull(app, "app不能为空");

        AppEntity appEntity = appDao.findOne(id);
        if (null == appEntity) {
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "记录未找到");
        }
        appEntity.setName(app.getName());
        if(app.getAppId()!=null && !app.getAppId().trim().equals("")){
            appEntity.setAppId(app.getAppId());
        }
        appEntity.setOrgName(app.getOrgName());
        appEntity.setOwnerName(app.getOwnerName());
        //AuditorProcess.fillAuditEntityAuditor(appEntity,false);
        appDao.save(appEntity);
    }
}
