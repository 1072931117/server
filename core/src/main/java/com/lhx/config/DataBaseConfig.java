package com.lhx.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * 数据库配置
 * Created by lhx on 2017/4/7.
 */
@Configurable
@EnableTransactionManagement
@Import(DataBaseConfig.DataSourceLoader.class)
public class DataBaseConfig {

    @PostConstruct
    public void init(){
        System.out.println("-------------------DataBaseConfig----------------------");
    }

    @Bean
    public DataSourceTransactionManager transactionManager(BasicDataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(BasicDataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        return sqlSessionFactory;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        mapperScannerConfigurer.setBasePackage("com.lhx.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }


    static class DataSourceLoader {
        @Autowired
        private Environment env;
        @Bean
        public BasicDataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
            dataSource.setUsername(env.getProperty("jdbc.username"));
            dataSource.setPassword(env.getProperty("jdbc.password"));
            dataSource.setUrl(env.getProperty("jdbc.url"));
            dataSource.setInitialSize(Integer.valueOf(env.getProperty("jdbc.pool.init")));
            dataSource.setMaxActive(Integer.valueOf(env.getProperty("jdbc.pool.minIdle")));
            dataSource.setMinIdle(Integer.valueOf(env.getProperty("jdbc.pool.maxActive")));
            dataSource.setMaxWait(6000);
            //<!-- 检查一次连接池中空闲的连接 把空闲时间超过minEvictableIdleTimeMillis毫秒的连接断开,
            // 直到连接池中的连接数到minIdle为止 -->
            dataSource.setTimeBetweenEvictionRunsMillis(600000);
            //<!-- 连接池中连接可空闲的时间 毫秒 -->
            dataSource.setMinEvictableIdleTimeMillis(3600000);
            //<!-- 开启池的prepared -->
            dataSource.setPoolPreparedStatements(true);
            dataSource.setValidationQuery("/* ping */SELECT 1");
            return dataSource;
        }
    }

}
