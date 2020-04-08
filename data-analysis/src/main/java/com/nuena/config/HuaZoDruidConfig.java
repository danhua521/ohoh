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
 * @time: 2020/4/8 21:46
 */
@Configuration
@MapperScan(basePackages = "com.nuena.huazo.mapper", sqlSessionFactoryRef = "sqlSessionFactory2")
public class HuaZoDruidConfig {

    @Bean("db2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.huazo")
    public DataSource druidDataSource1() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean("sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("db2") DataSource db2) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db2);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/huazo/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}