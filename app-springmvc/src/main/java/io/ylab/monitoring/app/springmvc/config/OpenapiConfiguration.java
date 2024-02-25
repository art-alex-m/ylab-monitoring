package io.ylab.monitoring.app.springmvc.config;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@OpenAPI31
@OpenAPIDefinition(
        info = @Info(title = "Ylab Monitoring API", version = "1.0.0"),
        security = @SecurityRequirement(name = "Bearer-Token"),
        servers = @Server(url = "http://localhost:9090/monitoring-app-springmvc-1.0.0/api", description = "Local"),
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

    @Value("${ylab.monitoring.swagger-ui.version}")
    private String swaggerUiVersion;

    @Bean
    @Order(2000)
    public SecurityFilterChain openApiSecurityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(antMatcher("/swagger-ui/**"), antMatcher("/v3/api-docs/**"))
                        .permitAll())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/swagger-initializer.js")
                .addResourceLocations("/v3/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations(String.join("", "/webjars/swagger-ui/", swaggerUiVersion, "/"));
        registry.addResourceHandler("/v3/api-docs/**")
                .addResourceLocations("/v3/api-docs/");
    }
}
