package io.ylab.monitoring.app.springmvc.config;

import io.ylab.monitoring.app.springmvc.service.AppAuthHeaderAuthorizationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
            AppAuthHeaderAuthorizationProvider authHeaderAuthenticationProvider) throws Exception {

        AuthenticationManagerBuilder builder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authHeaderAuthenticationProvider);

        return builder.build();
    }
}
