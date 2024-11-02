package br.dev.ferreiras.challenge.controller;
import br.dev.ferreiras.challenge.config.SecurityConfig;
import br.dev.ferreiras.challenge.dto.ContactsElementsDto;
import br.dev.ferreiras.challenge.dto.ResponseContactsDto;
import br.dev.ferreiras.challenge.service.ContactService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;

import java.time.Instant;
import java.util.List;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder.*;

@WebFluxTest(ContactController.class)
@Import({SecurityConfig.class})
class ContactControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ContactService contactService;

    private MockMvc mvc;

    @Before("")
    public void setup() {

        // Assuming ResponseContactsDto contains a list of contacts
        ResponseContactsDto responseDto = new ResponseContactsDto(List.of(new ContactsElementsDto(1L, Instant.now(), Instant.now(),"jmadsen", "jmadsen@kenect.com",  "KENECT_LABS")));
        Mockito.when(contactService.makeApiRequest()).thenReturn((WebClient.ResponseSpec) Mono.just(responseDto));
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_ADMIN")
    void getContacts_ReturnsContacts_WhenAuthorized() {
        webTestClient.get().uri("/api/v1/contacts?page=1&size=20")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.contacts").isNotEmpty()
                .jsonPath("$.contacts[0].name").isEqualTo("jmadsen");
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_USER")
    void getContacts_ReturnsForbidden_WhenNotAuthorized() {
        webTestClient.get().uri("/api/v1/contacts?page=1&size=20")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isForbidden();
    }
}