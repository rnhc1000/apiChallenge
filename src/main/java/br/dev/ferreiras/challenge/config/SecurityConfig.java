package br.dev.ferreiras.challenge.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.ForceEagerSessionCreationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Explore Spring Security & OAUTH2
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Value("${jwt.public.key}")
    private RSAPublicKey rsaPublicKey;

    @Value ("${jwt.private.key}")
    private RSAPrivateKey rsaPrivateKey;

    private static final String[] WHITELIST = {

            "/swagger-ui/**", "/api-docs/**", "/swagger-docs/**",
            "/swagger-resources/**", "/actuator/**", "/info/**",
            "/api/v1/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {

//    CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        httpSecurity
                .securityContext(contextConfig -> contextConfig.requireExplicitSave(false) )
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ForwardedHeaderFilter(), ForceEagerSessionCreationFilter.class)
//                .csrf(csrfConfig -> csrfConfig
//                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .ignoringRequestMatchers("/api/v1/login", "/api/v1/csrf")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SecurityConfig.WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)

                .oauth2ResourceServer((oauth2 -> oauth2.jwt(Customizer.withDefaults())));

        return httpSecurity.build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {

        final JWK jwk = new RSAKey.Builder(this.rsaPublicKey).privateKey(this.rsaPrivateKey).build();
        final var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    /**
     * @return JwtDecoder object given public key
     */
    @Bean
    public JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder.withPublicKey(this.rsaPublicKey).build();
    }

    /**
     * @return encoded clear-text password
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
