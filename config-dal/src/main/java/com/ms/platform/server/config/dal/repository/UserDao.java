package com.ms.platform.server.config.dal.repository;

import com.ms.platform.server.config.dal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long>,JpaSpecificationExecutor<UserEntity> {

    UserEntity findByUserName(String userName);

    UserEntity findByUserNameAndPassword(String userName,String password);

}
