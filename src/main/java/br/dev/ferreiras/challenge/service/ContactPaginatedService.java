package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.dto.ContactsElementsDto;
import br.dev.ferreiras.challenge.dto.ResponseContactsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactPaginatedService {

    private static final String APIKEY = "J7ybt6jv6pdJ4gyQP9gNonsY";
    private static final String ENDPOINT = "/api/v1/contacts?";

    public Flux<ResponseContactsDto> fetchPaginatedContacts() {
        return webClient()
                .get()
                .uri(ENDPOINT)
                .exchange()
                .expand(clientResponse -> {
                    List<String> links = clientResponse.headers().asHttpHeaders().getValuesAsList("LINK");
                    System.out.println(links);
                    if (links.stream().anyMatch(link -> link.contains("rel=\"next\""))) {
                        for (String link : links) {
                            if (link.contains("rel=\"next\"")) {
                                return webClient().get()
                                        .uri(ENDPOINT + link.substring(link.indexOf("page="), link.indexOf(">")))
                                        .exchange();
                            }
                        }
                    }
                    return Flux.empty();
                })
                .flatMap(clientResponse ->
                        clientResponse.bodyToFlux(ResponseContactsDto.class)
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
