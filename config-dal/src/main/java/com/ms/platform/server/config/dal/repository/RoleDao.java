package com.ms.platform.server.config.dal.repository;

import com.ms.platform.server.config.dal.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleEntity, Long>,JpaSpecificationExecutor<RoleEntity> {

    RoleEntity findByName(String name);

}
