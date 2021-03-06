package com.ms.platform.server.config.dal.repository;


import com.ms.platform.server.config.dal.entity.AppNamespaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppNamespaceDao extends JpaRepository<AppNamespaceEntity, Long>,JpaSpecificationExecutor<AppNamespaceEntity> {

    AppNamespaceEntity findByAppIdAndName(String appId,String name);
}
