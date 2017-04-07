package com.lhx.manager.config;

import com.lhx.config.SessionConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

/**
 * Created by lhx on 2017/3/24.
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @PostConstruct
    public void init(){
        System.out.println("-----------ApplicationInitializer------------");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MVCConfig.class,SessionConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 配置过滤器
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("utf-8");
        encodingFilter.setForceEncoding(true);
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("springSessionRepositoryFilter");
        return new Filter[]{encodingFilter,delegatingFilterProxy };
    }

}
