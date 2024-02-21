package io.ylab.monitoring.app.springboot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
        info = @Info(title = "Ylab Monitoring API", version = "1.0.0"),
        security = @SecurityRequirement(name = "Bearer-Token"),
        servers = @Server(url = "http://localhost:9090/monitoring-app-springboot-1.0.0/api", description = "Local"),
        tags = {
                @Tag(name = OpenapiTag.READINGS),
                @Tag(name = OpenapiTag.AUDIT),
                @Tag(name = OpenapiTag.AUTH),
                @Tag(name = OpenapiTag.USER),
                @Tag(name = OpenapiTag.ADMIN),
        }
)
@SecuritySchemes(value = {
        @SecurityScheme(
                name = "Bearer-Token",
                paramName = "Authorization",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                scheme = "Bearer"
        )
})
@Configuration
@EnableWebMvc
public class OpenapiConfiguration implements WebMvcConfigurer {

    @Bean
    @Order(2000)
    public SecurityFilterChain openApiSecurityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/swagger-ui/**", "/v3/api-docs/**")
                .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
                .build();
    }
}
