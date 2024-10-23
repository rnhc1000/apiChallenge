package br.dev.ferreiras.challenge.controller;

import br.dev.ferreiras.challenge.dto.ContactsElementsDto;
import br.dev.ferreiras.challenge.dto.ResponseContactsDto;
import br.dev.ferreiras.challenge.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Return List of Contacts
 * consuming endpoint informed.
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */

@RestController
@RequestMapping("api/v1")
public class ContactController {

private final ContactService contactService;

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Fetch 20 contacts per page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get up to 20 contacts per page.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactController.class))}),
            @ApiResponse (responseCode = "400", description = "Bad Request",
                    content = {@Content (mediaType = "application/json",
                            schema = @Schema (implementation = ContactController.class))}),
            @ApiResponse (responseCode = "403", description = "FORBIDDEN",
                    content = {@Content (mediaType = "application/json",
                            schema = @Schema (implementation = ContactController.class))}),
            @ApiResponse (responseCode = "404", description = "Resource not found!",
            content = {@Content (mediaType = "application/json",
                    schema = @Schema (implementation = ContactController.class))})
    })
    @ResponseStatus
    @GetMapping("/contacts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public Mono<ResponseContactsDto> getContacts(
            @RequestParam (defaultValue = "1") int page,
            @RequestParam (defaultValue = "20") int size
    ) {
//        final requestContacts = this.contactService.prepareRequestBody();

        //List<ResponseContactsDto> contacts =  contactService.makeApiRequest());

        return contactService.makeApiRequest()
                .bodyToMono(ResponseContactsDto.class);

    }
}