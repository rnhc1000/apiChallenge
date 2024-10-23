package br.dev.ferreiras.challenge.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Support to CORS
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CorsSecurityConfiguration implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsMessageConfiguration() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull final CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost",
                                "http://localhost:3000",
                                "http://localhost:4200",
                                "http://127.0.0.1",
                                "http://127.0.0.1:3000",
                                "http://127.0.0.1:4200",
                                "http://127.0.0.1:8097"
                        )
                        .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600L);
            }
        };
    }
}
