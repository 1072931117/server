/*
 * 地址：杭州市萧山区风情大道与建设一路交叉口中栋国际北干科创园1403
 * 联系电话：0571-82757223
 * 邮箱：zhuwenli@readyidu.comvv
 * 版权所有：杭州益读科技有限公司
 */

package com.lhx.web.config;

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
