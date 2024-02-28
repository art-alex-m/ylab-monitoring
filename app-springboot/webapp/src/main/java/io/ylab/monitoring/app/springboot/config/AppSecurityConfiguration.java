package io.ylab.monitoring.app.springboot.config;

import io.ylab.monitoring.app.springboot.service.AppAuthHeaderAuthorizationProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Profile("security-enabled")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class AppSecurityConfiguration {

    @Bean
    @Order(100)
    public SecurityFilterChain baseSecurityFilter(HttpSecurity httpSecurity,
            RequestHeaderAuthenticationFilter authTokenAuthenticationFilter,
            AuthenticationManager authenticationManager)
            throws Exception {

        return httpSecurity
                .securityMatcher(antMatcher("/api/**"))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(authTokenAuthenticationFilter)
                .authenticationManager(authenticationManager)
                .build();
    }

    @Bean
    @Order
    public SecurityFilterChain denySecurityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(customizer -> customizer.anyRequest().denyAll()).build();
    }

    @Bean
    public RequestHeaderAuthenticationFilter authTokenAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setPrincipalRequestHeader("Authorization");
        filter.setExceptionIfHeaderMissing(false);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    public FilterRegistrationBean<RequestHeaderAuthenticationFilter> excludeFromServletAuthTokenAuthenticationFilter(
            RequestHeaderAuthenticationFilter authTokenAuthenticationFilter) {
        FilterRegistrationBean<RequestHeaderAuthenticationFilter> registration =
                new FilterRegistrationBean<>(authTokenAuthenticationFilter);
        registration.setEnabled(false);

        return registration;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
            AppAuthHeaderAuthorizationProvider appAuthHeaderAuthorizationProvider) throws Exception {

        AuthenticationManagerBuilder builder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(appAuthHeaderAuthorizationProvider);

        return builder.build();
    }
}
