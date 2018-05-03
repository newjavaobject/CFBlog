package com.cf.blog.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Configuration
public class MyBatisConfig {

//    @Bean
    public SqlSessionTemplate sqlSessionTemplate(){
        SqlSessionTemplate template = null;
        try {
            template = new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }

//    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("config/MyBatis-Configuration.xml"));
        factoryBean.setDataSource(comboPooledDataSource());

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            factoryBean.setMapperLocations(resolver.getResources("classpath*:com/cf/blog/mapper/*/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factoryBean;
    }

//    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.cf.blog.mapper");
        try {
            configurer.setSqlSessionFactory(sqlSessionFactoryBean().getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configurer;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(comboPooledDataSource());

        return template;
    }

    @Bean(destroyMethod = "close")
    public ComboPooledDataSource comboPooledDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        try {
//            dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//        dataSource.setJdbcUrl("jdbc:oracle:thin:@//111.9.4.88:1521/orcl");
//        dataSource.setUser("ztapp");
//        dataSource.setPassword("ztapp");
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/cfblog?useUnicode=true&characterEncoding=utf-8");
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
