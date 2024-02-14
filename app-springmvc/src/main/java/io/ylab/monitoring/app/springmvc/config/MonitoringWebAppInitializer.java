package io.ylab.monitoring.app.springmvc.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Инициация Spring MVC
 */
public class MonitoringWebAppInitializer implements WebApplicationInitializer {

    public static final String PACKAGE_MAIN = "io.ylab.monitoring.app.springmvc.*";

    public static final String SERVLET_NAME = "monitoring-app-springmvc";

    public static final String POSTGRESQL_DRIVER_CLASS = "org.postgresql.Driver";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.scan(PACKAGE_MAIN);

        servletContext.addListener(new ContextLoaderListener(applicationContext));
        ServletRegistration.Dynamic dispatcherServlet = servletContext
                .addServlet(SERVLET_NAME, new DispatcherServlet(applicationContext));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");

        try {
            Class.forName(POSTGRESQL_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }
}
