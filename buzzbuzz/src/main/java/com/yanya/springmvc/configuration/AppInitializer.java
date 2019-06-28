package com.yanya.springmvc.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;

import java.io.File;
import java.util.EnumSet;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer{

    private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfiguration.class };
    }
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);
 
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
                new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");

         
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
 
        servletContext.addListener(contextLoaderListener);
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
         
         
        // Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
 
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        // upload temp file will put here
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);


    }

}