package com.cf.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 * 视图配置
 */
@Configuration
public class ViewConfiguration {
    @Bean
    public ViewResolver contentNegotiatingViewResolver(){
        List defaultViews = new ArrayList(1);
        defaultViews.add(new MappingJackson2JsonView());
        List viewResolvers = new ArrayList(1);
        viewResolvers.add(freeMarkerViewResolver());

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setDefaultViews(defaultViews);
        resolver.setViewResolvers(viewResolvers);

        resolver.setContentNegotiationManager(contentNegotiationManagerFactoryBean().getObject());

        return resolver;
    }

    @Bean
    public ContentNegotiationManagerFactoryBean contentNegotiationManagerFactoryBean(){
        ContentNegotiationManagerFactoryBean bean = new ContentNegotiationManagerFactoryBean();
        bean.setIgnoreAcceptHeader(true);
        bean.setDefaultContentType(MediaType.TEXT_HTML);

        Properties properties = new Properties();
        properties.setProperty("do","application/json");
        properties.setProperty("html","text/html");
        bean.setMediaTypes(properties);

        return bean;
    }
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver(){
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");/**@see this.freeMarkerConfigurer() ,已配置了路径*/
        resolver.setSuffix(".html");
        resolver.setContentType("text/html;charset=utf-8");
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setAllowRequestOverride(true);
        resolver.setAllowSessionOverride(true);
        resolver.setExposeSpringMacroHelpers(false);
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        resolver.setRequestContextAttribute("request");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("utf-8");
        configurer.setTemplateLoaderPath("/template/");

        Properties properties = new Properties();
        properties.setProperty("template_update_delay","10");
        properties.setProperty("locale","zh_CN");
        properties.setProperty("datetime_format","yyyy-MM-dd");
        properties.setProperty("date_format","yyyy-MM-dd");
        properties.setProperty("number_format","#.##");
        configurer.setFreemarkerSettings(properties);
        return configurer;
    }
}
