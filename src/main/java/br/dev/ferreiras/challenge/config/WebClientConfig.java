package br.dev.ferreiras.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * override webclient Bean
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        final String apiKey = "J7ybt6jv6pdJ4gyQP9gNonsY";

        return (WebClient) builder
                .baseUrl("https://k-messages-api.herokuapp.com")
                .defaultHeader(apiKey)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(apiKey));
    }
}
