package com.ms.platform.server.config.web;

import com.ms.common.bo.prop.PlatformProp;
import com.ms.platform.server.config.PackageInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@EnableEurekaClient
@SpringBootApplication(scanBasePackageClasses = {PackageInfo.class},scanBasePackages = {"com.ms.common.config","com.ms.platform.config"})
public class ConfigConsoleApplication {

	public static void main(String[] args) {
		try {
			PlatformProp.init("platform.properties");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		SpringApplication.run(ConfigConsoleApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Sampler sampler(){
		return new AlwaysSampler();//输出所有数据
	}

}
