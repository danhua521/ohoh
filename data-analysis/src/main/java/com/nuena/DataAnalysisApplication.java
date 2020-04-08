package com.nuena;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableEurekaClient
@MapperScan({
        "com.nuena.changx.mapper",
        "com.nuena.orglib.mapper"
})
public class DataAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataAnalysisApplication.class, args);
    }

}