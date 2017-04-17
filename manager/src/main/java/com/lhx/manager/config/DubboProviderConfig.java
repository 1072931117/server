/*
 * 地址：杭州市萧山区风情大道与建设一路交叉口中栋国际北干科创园1403
 * 联系电话：0571-82757223
 * 邮箱：zhuwenli@readyidu.comvv
 * 版权所有：杭州益读科技有限公司
 */

package com.lhx.manager.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
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
public class DubboProviderConfig {
    @Autowired
    private Environment env;

    @Bean
    public ServiceConfig<DemoService> demoService(DemoService demoService,ApplicationConfig applicationConfig
            ,RegistryConfig registryConfig,ProtocolConfig protocolConfig){
        ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRef(demoService);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
        serviceConfig.setProtocol(protocolConfig); // 多个协议可以用setProtocols()
        // 暴露及注册服务
        serviceConfig.export();
        return serviceConfig;
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
    /**
     * 与<dubbo:protocol/>相当
     */
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig=new ProtocolConfig(env.getProperty("dubbo.protocol.name"));
        //默认为hessian2,但不支持无参构造函数类,而这种方式的效率很低
        protocolConfig.setSerialization("java");
        return protocolConfig;
    }

}
