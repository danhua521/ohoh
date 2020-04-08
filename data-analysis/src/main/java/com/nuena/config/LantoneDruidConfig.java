package com.nuena.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/8 21:41
 */
@Configuration
@MapperScan(basePackages = "com.nuena.orglib.mapper", sqlSessionFactoryRef = "sqlSessionFactory1")
public class LantoneDruidConfig {

    @Bean("db1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.lantone")
    public DataSource druidDataSource1() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean("sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("db1") DataSource db1) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db1);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/orglib/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}