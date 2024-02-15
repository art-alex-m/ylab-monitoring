package io.ylab.monitoring.app.springmvc.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Инициация Spring MVC
 *
 * <p>
 *     <a href="https://docs.spring.io/spring-security/reference/servlet/configuration/java.html#abstractsecuritywebapplicationinitializer-with-spring-mvc">AbstractSecurityWebApplicationInitializer with Spring MVC</a><br>
 *     <a href="https://docs.spring.io/spring-security/reference/servlet/integrations/mvc.html#mvc-requestmatcher">MvcRequestMatcher</a><br>
 *     <a href="https://stackoverflow.com/questions/76809698/spring-security-method-cannot-decide-pattern-is-mvc-or-not-spring-boot-applicati">Spring security method cannot decide pattern is MVC or not Spring Boot application exception</a><br>
 * </p>
 */
public class AppMonitoringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final String PACKAGE_MAIN = "io.ylab.monitoring.app.springmvc.*";

    public static final String SERVLET_NAME = "monitoring-app-springmvc";

    public static final String POSTGRESQL_DRIVER_CLASS = "org.postgresql.Driver";

    public static final String SERVLET_MAPPING = "/api/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        try {
            Class.forName(POSTGRESQL_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.scan(PACKAGE_MAIN);

        return applicationContext;
    }

    @Override
    protected String getServletName() {
        return SERVLET_NAME;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{SERVLET_MAPPING};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppSecurityConfiguration.class};
    }
}
