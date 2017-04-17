/*
 * 地址：杭州市萧山区风情大道与建设一路交叉口中栋国际北干科创园1403
 * 联系电话：0571-82757223
 * 邮箱：zhuwenli@readyidu.comvv
 * 版权所有：杭州益读科技有限公司
 */

package com.lhx.web.config;

import com.alibaba.dubbo.config.*;
import com.lhx.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by lhx on 2017/4/14.
 */
@Configurable
@PropertySource("classpath:/properties/dubbo-base.properties")
public class DubboConsumerConfig {
    @Autowired
    private Environment env;

    @Bean
    public DemoService demoService (ReferenceConfig<DemoService> demoServiceReference){
        return demoServiceReference.get();
    }

    @Bean
    public ReferenceConfig<DemoService> demoServiceReference(ApplicationConfig applicationConfig, RegistryConfig registryConfig){
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setInterface(DemoService.class);
        return referenceConfig;
    }


    /**
     * 与<dubbo:application/>相当.
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setLogger("log4j");
        applicationConfig.setName(env.getProperty("dubbo.application.name"));
        return applicationConfig;
    }
    /**
     * 与<dubbo:registry/>相当
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(env.getProperty("dubbo.registry.address"));
        return registryConfig;
    }


}
