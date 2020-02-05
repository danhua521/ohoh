package com.nuena;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableEurekaClient
@MapperScan({"com.nuena.bkmy.mapper","com.nuena.xywy.mapper"})
public class PainsectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PainsectApplication.class, args);
    }

}
