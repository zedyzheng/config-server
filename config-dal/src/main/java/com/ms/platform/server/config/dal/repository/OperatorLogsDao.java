package com.ms.platform.server.config.dal.repository;

import com.ms.platform.server.config.dal.entity.OperatorLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorLogsDao extends JpaRepository<OperatorLogsEntity, Long>,JpaSpecificationExecutor<OperatorLogsEntity> {


}
