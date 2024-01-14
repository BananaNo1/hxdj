package com.leis.hxds.nebula;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
//@MapperScan("com.leis.hxds.nebula.db.dao")
@ComponentScan("com.leis.*")
@EnableAsync
public class HxdsNebulaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HxdsNebulaApplication.class, args);
    }

}
