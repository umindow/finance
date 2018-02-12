package com.supervise.config.web;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.supervise.interceptor.RoleCheckInterceptor;
import com.supervise.interceptor.SessionCheckInterceptor;
import jetbrick.template.web.springmvc.JetTemplateViewResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

/**
 * @description
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/index").excludePathPatterns("/login");
        registry.addInterceptor(roleCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/index").excludePathPatterns("/login");
    }

    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor() {
        SessionCheckInterceptor interceptor = new SessionCheckInterceptor();
        interceptor.addNonFilter("/error");
        interceptor.addNonFilter("/resources/**");
        interceptor.addNonFilter("/static/**");
        interceptor.addNonFilter("/logout");
        interceptor.addNonFilter("/user/init");
        return interceptor;
    }

    public RoleCheckInterceptor roleCheckInterceptor() {
        RoleCheckInterceptor interceptor = new RoleCheckInterceptor();
        interceptor.addNonFilter("/error");
        interceptor.addNonFilter("/resources/**");
        interceptor.addNonFilter("/static/**");
        interceptor.addNonFilter("/logout");
        interceptor.addNonFilter("/user/init");
        return interceptor;
    }

    @Bean
    public FilterRegistrationBean druidFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.setName("druidStatFilter");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean druidViewSevletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.setName("druidStatViewSevlet");
        servletRegistrationBean.addUrlMappings("/druid/*");
        servletRegistrationBean.addInitParameter("allow", "");// IP白名单 (没有配置或者为空，则允许所有访问)
        servletRegistrationBean.addInitParameter("loginUsername", "admin");// 用户名
        servletRegistrationBean.addInitParameter("loginPassword", "root");// 密码
        servletRegistrationBean.addInitParameter("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }

    @Bean
    public JetTemplateViewResolver viewResolver() {
        JetTemplateViewResolver jetTemplateViewResolver = new JetTemplateViewResolver();
        jetTemplateViewResolver.setOrder(1);
        jetTemplateViewResolver.setContentType("text/html; charset=utf-8");
        jetTemplateViewResolver.setSuffix(".html");
        jetTemplateViewResolver.setConfigLocation("classpath:/jetbrick-template.properties");
        Properties configProperties = new Properties();
        configProperties.setProperty("jetx.input.encoding", "UTF-8");
        configProperties.setProperty("jetx.output.encoding", "UTF-8");
        jetTemplateViewResolver.setConfigProperties(configProperties);
        return jetTemplateViewResolver;
    }

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new VisitCountListener());
        return servletListenerRegistrationBean;
    }
}
