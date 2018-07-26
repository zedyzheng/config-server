package com.ms.platform.server.config.web.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Joey on 2017/8/3 0003.
 */

@Configuration
@EnableTransactionManagement
//@ComponentScan({"com.ms.platform.server.config.dal"})
@EntityScan(basePackages={"com.ms.platform.server.config.dal"})
@EnableJpaRepositories(basePackages={"com.ms.platform.server.config.dal"})
@EnableJpaAuditing
public class DataSourceConfig {

}
