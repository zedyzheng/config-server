package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.api.AppNamespaceService;
import com.ms.platform.server.config.api.ServerConfigService;
import com.ms.platform.server.config.api.ServerConfigSynService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.enums.ServiceConfigStatus;
import com.ms.platform.server.config.dal.entity.ServerConfigEntity;
import com.ms.platform.server.config.dal.repository.ServerConfigDao;
import com.ms.platform.server.config.model.AppNamespace;
import com.ms.platform.server.config.model.ServerConfig;
import com.ms.platform.server.config.request.CommonServerConfigRequest;
import com.ms.platform.server.config.request.ServerConfigPageRequest;
import com.ms.platform.server.config.request.ServerConfigRequest;
import com.ms.platform.server.config.service.convert.ServerConfigConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.Column;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Joey on 2017/8/3 0003.
 */
@Service
public class ServiceConfigServiceImpl implements ServerConfigService {

    @Autowired
    private ServerConfigDao serverConfigDao;
    @Autowired
    private AppNamespaceService appNamespaceService;

    @Override
    public ServerConfig getDetail(Long id) {
        ServerConfigEntity serverConfigEntity = serverConfigDao.findOne(id);
        if(null == serverConfigEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录不存在");
        }
        ServerConfig serverConfig = new ServerConfig();
        BeanUtils.copyProperties(serverConfigEntity,serverConfig);

        return serverConfig;
    }

    @Override
    public List<ServerConfig> findByAppNamespaceId(Long appNamespaceId) {

        List<ServerConfigEntity> serverConfigEntityList = serverConfigDao.findByAppNamespaceId(appNamespaceId);
        if(null == serverConfigEntityList){
            return new ArrayList<>();
        }

        List<ServerConfig> serverConfigList = new ArrayList<>();
        serverConfigEntityList.forEach(serverConfigEntity -> {
            ServerConfig serverConfig = new ServerConfig();
            BeanUtils.copyProperties(serverConfigEntity,serverConfig);
            serverConfigList.add(serverConfig);
        });

        return serverConfigList;
    }

    @Override
    public ServerConfig findByAppNamespaceIdAndItemKey(Long appNamespaceId, String itemKey) {
        ServerConfigEntity serverConfigEntity = serverConfigDao.findByAppNamespaceIdAndItemKey(appNamespaceId,itemKey);
        if(null == serverConfigEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"记录不存在");
        }
        ServerConfig serverConfig = new ServerConfig();
        BeanUtils.copyProperties(serverConfigEntity,serverConfig);
        return serverConfig;
    }

    @Override
    public PageCustom<ServerConfig> pages(ServerConfigPageRequest pageRequest) throws BusinessException {
        if(pageRequest.getPageSize()<=0 || pageRequest.getPageSize()>200){
            throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "pageSize最大值为200");
        }

        Specification<ServerConfigEntity> specification = (root, query, cb) -> {
            List<Predicate> all = new ArrayList<>();
            // 获取未删除的属性
            if (null!=pageRequest.getId() && pageRequest.getId()>0) {
                all.add(cb.equal(root.get("id").as(Long.class), pageRequest.getId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getKey())) {
                all.add(cb.equal(root.get("key").as(String.class), pageRequest.getKey()));
            }
            if (!StringUtils.isEmpty(pageRequest.getAppNamespaceId())) {
                all.add(cb.equal(root.get("appNamespaceId").as(String.class), pageRequest.getAppNamespaceId()));
            }
            Predicate[] predicates = new Predicate[all.size()];
            return cb.and(all.toArray(predicates));
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"itemKey"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "ordered"));
        //orders.add(new Sort.Order(Sort.Direction.ASC, "createdDate"));

        Pageable pageable = new PageRequest(pageRequest.getPageIndex() - 1, pageRequest.getPageSize(),
                new Sort(orders));

        List<ServerConfig> excelTaskList = new ArrayList<>();
        Page<ServerConfigEntity> pages = serverConfigDao.findAll(specification, pageable);

        if (pages != null && pages.getSize() > 0) {
            pages.forEach(appEntity -> {
                ServerConfig serverConfig = new ServerConfig();
                BeanUtils.copyProperties(appEntity,serverConfig);
                excelTaskList.add(serverConfig);
            });
        }

        return new PageCustom<>(pages != null ? pages.getTotalElements() : 0, pageRequest, excelTaskList);
    }

    @Transactional
    @Override
    public Long add(ServerConfigRequest serverConfig) throws BusinessException {
        Assert.notNull(serverConfig, "serverConfig不能为空");
        if(null == serverConfig.getItemKey() || serverConfig.getItemKey().trim().length()<=0){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"key不能为空");
        }
        serverConfig.setItemKey(serverConfig.getItemKey().trim());
        String itemValue = serverConfig.getItemValue();
        if(null!=itemValue){
            itemValue = itemValue.trim();
            serverConfig.setItemValue(itemValue);
            if(itemValue.length()> Constants.ITEM_VALUE_MAX){
                throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"value值长度超出");
            }
        }

        if(null!=serverConfigDao.findByAppNamespaceIdAndItemKey(serverConfig.getAppNamespaceId(),serverConfig.getItemKey())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"配置已经存在");
        }

        AppNamespace appNamespace = appNamespaceService.getDetail(serverConfig.getAppNamespaceId());
        if(null==appNamespace){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"应用空间不存在");
        }

        ServerConfigEntity appEntity = ServerConfigConverter.toAppEntity(serverConfig);
        if(null==appEntity.getOrdered()){
            appEntity.setOrdered(1);
        }
        appEntity.setAppId(appNamespace.getAppId());

        serverConfigDao.save(appEntity);

        appNamespaceService.modifyStatus(appNamespace.getId(),ServiceConfigStatus.MODIFY);
        return appEntity.getId();
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public void modify(Long id, ServerConfigRequest serverConfig) throws BusinessException {
        Assert.notNull(serverConfig, "serverConfig不能为空");

        ServerConfigEntity appEntity = serverConfigDao.findOne(id);
        if (null == appEntity) {
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "记录未找到");
        }
        String itemValue = serverConfig.getItemValue();
        if(null!=itemValue){
            itemValue = itemValue.trim();
        }
        appEntity.setItemValue(itemValue);
        appEntity.setComment(serverConfig.getComment());

        serverConfigDao.save(appEntity);

        appNamespaceService.modifyStatus(appEntity.getAppNamespaceId(),ServiceConfigStatus.MODIFY);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        serverConfigDao.delete(id);
    }

    @Override
    public void uploadConfigFile(Long appNamespaceId, MultipartFile configFile) throws BusinessException {
        if(null == appNamespaceId){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"namespace不能为空");
        }
        if (configFile == null) {
            return;
        }
        Properties properties = new Properties();
        try {
            InputStream ins = configFile.getInputStream();
            properties.load(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppNamespace appNamespace = appNamespaceService.getDetail(appNamespaceId);
        if(null==appNamespace){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"应用空间不存在 appNamespaceId="+appNamespaceId);
        }

        //List<ServerConfigEntity> serverConfigEntityList = new ArrayList<>();
        properties.forEach((key,value)->{

            ServerConfigEntity existServerConfig = serverConfigDao.findByAppNamespaceIdAndItemKey(appNamespaceId,(String)key);
            if(null==existServerConfig){
                existServerConfig = new ServerConfigEntity();
                existServerConfig.setItemKey(((String)key).trim());
                existServerConfig.setAppNamespaceId(appNamespaceId);
                existServerConfig.setAppId(appNamespace.getAppId());
            }
            if(null!=value){
                existServerConfig.setItemValue(((String)value).trim());
                if(existServerConfig.getItemValue().length()>Constants.ITEM_VALUE_MAX){
                    throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"value值长度超出");
                }
            }

            serverConfigDao.save(existServerConfig);
        });

        //serverConfigDao.save(serverConfigEntityList);
        appNamespaceService.modifyStatus(appNamespace.getId(),ServiceConfigStatus.MODIFY);
    }

    @Override
    public void uploadCommonConfigFile(String appNamespaceName, CommonServerConfigRequest serverConfig, MultipartFile configFile) throws BusinessException {
        if(null == appNamespaceName){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"namespace不能为空");
        }
        if (configFile == null) {
            return;
        }

        String[] appIds = StringUtils.split(serverConfig.getAppIds(),",");
        for(int i =0;i<appIds.length;i++){
            String appId = appIds[i];
            AppNamespace appNamespace = appNamespaceService.findByAppIdAndName(appId,appNamespaceName);
            if(null==appNamespace){
                throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"应用空间不存在 appId="+appId+",appNamespaceName="+appNamespaceName);
            }

            this.uploadConfigFile(appNamespace.getId(),configFile);
        }
    }

    @Override
    public void downloadConfigFile(Long appNamespaceId,HttpServletResponse response) throws Exception{
        List<ServerConfigEntity> serverConfigEntityList = serverConfigDao.findByAppNamespaceId(appNamespaceId);


        Properties properties = readListToProperties(serverConfigEntityList);
        //response.reset();
        try {
            response.setHeader("Content-Disposition", "attachment; filename="+ (appNamespaceId+".properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setContentType("application/octet-stream");
        //response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        try {
            properties.store(response.getOutputStream(), appNamespaceId+"");
            //response.getOutputStream().write(fileBytes);
            //response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            throw e;
        }
    }

    @Transactional
    @Override
    public void importConfigFromSpace(Long sourceNamespaceId, Long targetNamespaceId) throws Exception {
        if(null == sourceNamespaceId || null == targetNamespaceId){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"namespace不能为空");
        }

        AppNamespace sourceNamespace = appNamespaceService.getDetail(sourceNamespaceId);
        if(null==sourceNamespace){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"应用空间不存在 sourceNamespaceId="+sourceNamespaceId);
        }
        AppNamespace targetNamespace = appNamespaceService.getDetail(targetNamespaceId);
        if(null==targetNamespace){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"应用空间不存在 targetNamespaceId="+targetNamespaceId);
        }

        List<ServerConfigEntity> serverConfigEntities = serverConfigDao.findByAppNamespaceId(sourceNamespaceId);
        serverConfigEntities.forEach(serverConfigEntity -> {
            ServerConfigEntity existServerConfig = serverConfigDao.findByAppNamespaceIdAndItemKey(targetNamespaceId,serverConfigEntity.getItemKey());
            if(null==existServerConfig){
                existServerConfig = new ServerConfigEntity();
                existServerConfig.setItemKey(serverConfigEntity.getItemKey());
                existServerConfig.setAppId(targetNamespace.getAppId());
                existServerConfig.setAppNamespaceId(targetNamespace.getId());
                existServerConfig.setOrdered(serverConfigEntity.getOrdered());
            }
            existServerConfig.setItemValue(serverConfigEntity.getItemValue());
            existServerConfig.setComment(serverConfigEntity.getComment());

            serverConfigDao.save(existServerConfig);
        });

    }

    private static Properties readListToProperties(List<ServerConfigEntity> serverConfigEntityList) throws IOException {
        if(null==serverConfigEntityList || serverConfigEntityList.isEmpty()){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"任务记录为空");
        }

        Properties properties = new Properties();
        serverConfigEntityList.forEach(key->{
            properties.setProperty(key.getItemKey(),key.getItemValue());
        });

        return properties;
    }

}
