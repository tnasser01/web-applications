package com.yanya.springmvc.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.CacheControl;




@EnableWebMvc
@Configuration
@ComponentScan({"com.yanya.springmvc"})

public class AppConfig extends WebMvcConfigurerAdapter {

	// Bean name must be "multipartResolver", by default Spring uses method name as bean name.
    @Bean(name = "multipartResolver")
    public StandardServletMultipartResolver resolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/", "/", "classpath:/META-INF/web-resources/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/static/svg/**").addResourceLocations("/static/svg/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/static/png/**").addResourceLocations("/static/png/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));

        //        registry.addResourceHandler("/static/ico/**").addResourceLocations("/static/ico/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));     
        //      registry.addResourceHandler("/favicon.ico").addResourceLocations("/");
    }
    
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }


    
}