package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.service.exceptions.ContactProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
    private static final String ENDPOINT = "https://k-messages-api.herokuapp.com/api/v1/contacts";
    private static final String APIKEY = "J7ybt6jv6pdJ4gyQP9gNonsY";

    public WebClient.ResponseSpec makeApiRequest() throws ContactProcessingException {

        final WebClient webClient = WebClient
                .builder()
                .baseUrl(ENDPOINT)
                .defaultHeader(APIKEY)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(APIKEY))
                .build();

        logger.info("webClient content {}", webClient);

        try {

            return webClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve();

        } catch (DecodingException exception) {
            throw new ContactProcessingException(exception.getMessage());
        }
    }
}

