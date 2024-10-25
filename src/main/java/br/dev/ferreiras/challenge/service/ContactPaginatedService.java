package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.dto.ContactsElementsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ContactPaginatedService {

    private static final String APIKEY = "J7ybt6jv6pdJ4gyQP9gNonsY";

    public Flux<ContactsElementsDto> fetchPaginatedContacts() {

    return  webClient()
                .get()
                .uri("/api/v1/contacts?")
                .exchange()
                .expand(clientResponse -> {
                    List<String> links = clientResponse.headers().asHttpHeaders().getValuesAsList("LINK");
                    System.out.println(links);
                    if (links.stream().anyMatch(link -> link.contains("rel=\"next\""))) {
                        for (String link : links) {
                            if (link.contains("rel=\"next\"")) {
                                return webClient().get()
                                        .uri("/api/v1/contacts?" + link.substring(link.indexOf("?"), link.indexOf(">")))
                                        .exchange();
                            }
                        }
                    }
                    return Flux.empty();
                })
                .flatMap(clientResponse ->
                       clientResponse.bodyToFlux(ContactsElementsDto.class)
                );
    }

    private WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("https://k-messages-api.herokuapp.com/")
                .defaultHeader(APIKEY)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(APIKEY))
                .build();
    }



}
