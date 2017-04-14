package com.lhx.manager.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * Created by lhx on 2017/3/24.
 */
@Configurable
@ComponentScan({"com.lhx.manager.controller","com.lhx.manager.service","com.lhx.manager.provider","com.lhx.mapper"})
@Import(DubboConfig.class)
public class ManagerConfig {

    @PostConstruct
    public void init(){
        System.out.println("-----------ManagerConfig-----------");
    }
}
