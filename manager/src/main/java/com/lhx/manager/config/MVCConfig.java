package com.lhx.manager.config;

import com.lhx.config.CoreConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhx on 2017/3/24.
 */
@Configurable
@EnableWebMvc
@Import({MVCConfig.MVCConfigLoader.class,CoreConfig.class,ManagerConfig.class})
public class MVCConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private VelocityViewResolver htmlViewResolver;

    @PostConstruct
    public void init(){
        System.out.println("-----------MVCConfig------------");
    }

    private ViewResolver redirectViewResolver() {
        VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver();
        resolver.setViewNames("redirect:*");
        return resolver;
    }

    private ViewResolver forwardViewResolver() {
        VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver();
        resolver.setViewNames("redirect:*");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/resources/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/resources/image/**").addResourceLocations("/image/");
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(htmlViewResolver);
        registry.viewResolver(redirectViewResolver());
        registry.viewResolver(forwardViewResolver());
    }

    @Bean
    public VelocityConfigurer velocityConfigurer(){
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoaderPath("/velocity");
        Map<String, Object> map = new HashMap<>();
        map.put("input.encoding","UTF-8");
        map.put("output.encoding","UTF-8");
        velocityConfigurer.setVelocityPropertiesMap(map);
        return velocityConfigurer;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(10485760000L);
        multipartResolver.setMaxInMemorySize(40960);
        return multipartResolver;
    }

    static class MVCConfigLoader {
        @Autowired
        private ApplicationContext context;
        @Bean
        public VelocityViewResolver htmlViewResolver() {
            VelocityViewResolver resolver = new VelocityViewResolver();
            resolver.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
            resolver.setCache(true);
            resolver.setPrefix("/");
            resolver.setSuffix(".vm");
            resolver.setApplicationContext(context);
            return resolver;
        }

    }
}
