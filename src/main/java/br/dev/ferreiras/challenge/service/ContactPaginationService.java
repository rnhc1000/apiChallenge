package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.dto.ContactsElementsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContactPaginationService {

    private static final String apiKey = "J7ybt6jv6pdJ4gyQP9gNonsY";

    public WebClient.ResponseSpec fetchPaginatedContacts() {
        final WebClient webClient = WebClient
                .builder()
                .baseUrl("https://k-messages-api.herokuapp.com/api/v1/contacts")
                .defaultHeader(apiKey)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(apiKey))
                .build();

       return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }

//    public boolean hasNextPage(Response response) {
//        // Extract pagination info from response headers or body
//        return response.getHeaders().containsKey("X-Next-Page");
//        // Example header check
//    }
//
//    public int getNextPage(Response response) {
//        // Return the next page number from response
//        return Integer.parseInt(response.getHeaders().getFirst("X-Next-Page"));
//    }
//
//    private Flux<ContactsElementsDto> fetchPage(int page, int size) {
//        return fetchPaginatedContacts(page, size).expand(response -> {
//            if (response.this.hasNextPage()) { // Assuming response includes nextPage info
//                return fetchPage(response.this.getNextPage(), size);
//            } else {
//                return Flux.empty();
//            }
//        });
//    }
}
