package com.ms.platform.server.config.dal.repository;

import com.ms.platform.server.config.dal.entity.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends JpaRepository<AppEntity, Long>,JpaSpecificationExecutor<AppEntity> {

    AppEntity findByName(String name);

    AppEntity findByAppId(String appId);

}
