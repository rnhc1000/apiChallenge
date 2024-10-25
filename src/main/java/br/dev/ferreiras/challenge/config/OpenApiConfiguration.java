package br.dev.ferreiras.challenge.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Support to OpenAPI 3.0
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */

@Configuration
public class OpenApiConfiguration {
    /**
     * @return API UI
     */
    @Bean
    public OpenAPI defineOpenApi() {
        final Server server = new Server();
        server.setUrl("""
                https://challenge.ferreiras.dev.br
                """);
        server.setDescription("Challenge");

        final Contact myContact = new Contact();
        myContact.setName(":Ricardo Ferreira");
        myContact.setEmail("ricardo@ferreiras.dev.br");

        final Info information = new Info()
                .title("Jobsity Challenge")
                .version("1.0")
                .description("Application to consume https://k-messages-api.herokuapp.com/api/v1/contacts")
                .contact(myContact);

        return new OpenAPI()
                .info(information)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                )
                )
                .servers(List.of(server));
    }
}
