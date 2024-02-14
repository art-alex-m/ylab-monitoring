package io.ylab.monitoring.app.springmvc.config;

import io.ylab.monitoring.app.springmvc.service.AppAuthHeaderAuthorizationProvider;
import io.ylab.monitoring.auth.boundary.AuthUserLoginInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserRegistrationInteractor;
import io.ylab.monitoring.auth.service.AuthPasswordEncoder;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuthUserDbRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.sql.Connection;

/**
 * Конфигурация сценариев регистрации, входа-выхода
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class AuthConfiguration {

    @Bean
    public UserLoginInput userLoginInteractor(PasswordEncoder passwordEncoder,
            MonitoringEventPublisher monitoringEventPublisher,
            UserLoginInputDbRepository inputDbRepository) {

        return AuthUserLoginInteractor.builder()
                .passwordEncoder(passwordEncoder)
                .eventPublisher(monitoringEventPublisher)
                .inputDbRepository(inputDbRepository)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${ylab.monitoring.auth.password.salt}") String passwordSalt) {
        return new AuthPasswordEncoder(passwordSalt.getBytes());
    }


    @Bean
    public JdbcAuthUserDbRepository userLoginInputDbRepository(Connection connection,
            SqlQueryRepository sqlQueryRepository) {
        return new JdbcAuthUserDbRepository(sqlQueryRepository, connection);
    }

    @Bean
    public UserRegistrationInput userRegistrationInteractor(PasswordEncoder passwordEncoder,
            MonitoringEventPublisher monitoringEventPublisher,
            UserRegistrationInputDbRepository inputDbRepository) {
        return AuthUserRegistrationInteractor.builder()
                .eventPublisher(monitoringEventPublisher)
                .inputDbRepository(inputDbRepository)
                .passwordEncoder(passwordEncoder)
                .build();
    }

    @Bean
    public SecurityFilterChain baseSecurityFilter(HttpSecurity httpSecurity,
            AuthenticationManager authenticationManager,
            RequestHeaderAuthenticationFilter authTokenAuthenticationFilter) throws Exception {

        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager)
                .addFilter(authTokenAuthenticationFilter)
                .authorizeHttpRequests(c -> c.requestMatchers("/login/**", "/logout/**").permitAll())
                .authorizeHttpRequests(c -> c.anyRequest().authenticated())
                .build();
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
