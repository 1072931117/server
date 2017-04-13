package com.lhx.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * Created by lhx on 2017/3/24.
 */

@Configurable
@Import(value = {RedisConfig.class,DataBaseConfig.class})
@PropertySource("classpath:/${package.environment}/properties/jdbc-base.properties")
@PropertySource("classpath:/${package.environment}/properties/redis-base.properties")
public class CoreConfig {

    @PostConstruct
    public void init(){
        System.out.println("-----------CoreConfig-----------");
    }


}
