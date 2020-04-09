package com.nuena.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/8 21:41
 */
@Configuration
@MapperScan(basePackages = "com.nuena.lantone.mapper", sqlSessionFactoryRef = "sqlSessionFactory1")
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
                .getResources("classpath*:mapper/lantone/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "db1TransactionManager")
    @Primary//不加这个注解会报错,必须告诉springboot优先使用哪一个，不然 2个的话 它无从选择
    public DataSourceTransactionManager testTransactionManager(@Qualifier("db1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate1")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}