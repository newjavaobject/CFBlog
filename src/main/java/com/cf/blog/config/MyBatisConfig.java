package com.cf.blog.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 * mybatis配置 ----> 改为使用spring jdbc，不用mybatis了.
 */
@Configuration
public class MyBatisConfig {

//    @Bean
    public SqlSessionTemplate sqlSessionTemplate(){
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }

//    @Bean
    public SqlSessionFactory sqlSessionFactory(){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("config/MyBatis-Configuration.xml"));
        factoryBean.setDataSource(comboPooledDataSource());
//        factoryBean.setMapperLocations();
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.cf.blog.dao");
        configurer.setSqlSessionFactory(sqlSessionFactory());
        return configurer;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(comboPooledDataSource());

        return template;
    }

    @Bean(destroyMethod = "close")
    public ComboPooledDataSource comboPooledDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/inst?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setInitialPoolSize(5);
        dataSource.setMaxIdleTime(60);
        dataSource.setAcquireIncrement(2);
        dataSource.setIdleConnectionTestPeriod(60);
        dataSource.setAcquireRetryAttempts(5);

        return dataSource;
    }
}
