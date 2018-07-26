package com.ms.platform.server.config.dal.repository;


import com.ms.platform.server.config.dal.entity.ServerConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerConfigDao extends JpaRepository<ServerConfigEntity, Long>,JpaSpecificationExecutor<ServerConfigEntity> {

    List<ServerConfigEntity> findByAppNamespaceId(Long appNamespaceId);

    ServerConfigEntity findByAppNamespaceIdAndItemKey(Long appNamespaceId, String itemKey);

}
