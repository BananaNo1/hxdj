package com.leis.hxdscst;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leis.hxdscst.db.dao")
@ComponentScan("com.leis.*")
@EnableDistributedTransaction
public class HxdsCstApplication {

    public static void main(String[] args) {
        SpringApplication.run(HxdsCstApplication.class, args);
    }

}
