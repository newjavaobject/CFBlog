package com.cf.blog.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 * 服务器启动入口
 */
public class WebApplicationStarter implements WebApplicationInitializer {

    private static final String SERVLET_NAME = "spring-mvc";
    private static final String ENCODING_FILTER_NAME = "encodingFilter";
    private static final long MAX_FILE_UPLOAD_SIZE = 1024 * 1024 * 5; // 5 Mb
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024; // After 1Mb
    private static final long MAX_REQUEST_SIZE = -1L;

    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringMVC.class, ViewConfiguration.class, MyBatisConfig.class);
        DispatcherServlet servlet = new DispatcherServlet(context);

        ServletRegistration.Dynamic dynamic = servletContext.addServlet(SERVLET_NAME, servlet);
        dynamic.addMapping("*.do");
        dynamic.addMapping("*.html");
        dynamic.setLoadOnStartup(1);

        //filter
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        servletContext.addFilter(ENCODING_FILTER_NAME, encodingFilter);

        //log4j listener
        Log4jConfigListener log4jConfigListener = new Log4jConfigListener();
        servletContext.setInitParameter(Log4jWebConfigurer.CONFIG_LOCATION_PARAM, "/WEB-INF/classes/config/log4j.properties");
        servletContext.setInitParameter(Log4jWebConfigurer.REFRESH_INTERVAL_PARAM, "3000");
        servletContext.addListener(log4jConfigListener);
    }
}
