package br.dev.ferreiras.challenge.controller;

import br.dev.ferreiras.challenge.dto.*;
import br.dev.ferreiras.challenge.entity.Contact;
import br.dev.ferreiras.challenge.service.ContactPaginatedService;
import br.dev.ferreiras.challenge.service.ContactService;
import br.dev.ferreiras.challenge.service.ContactsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Return List of Contacts
 * consuming endpoint informed.
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    private final ContactsService contactsService;
    private final ContactService contactService;
    private final ContactPaginatedService contactPaginationService;

    public ContactController(ContactsService contactsService, ContactService contactService, ContactPaginatedService contactPaginationService) {
        this.contactsService = contactsService;
        this.contactService = contactService;
        this.contactPaginationService = contactPaginationService;
    }

    @Operation(summary = "Fetch 20 contacts per page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get up to 20 contacts per page.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseContactsDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Access Denied",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Resource not found!",
                    content = {@Content(mediaType = "application/json")})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/evaluating-contacts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public Mono<ResponseContactsDto> getContacts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        return contactService.makeApiRequest()
                .bodyToMono(ResponseContactsDto.class);
    }

    @Operation(summary = "Fetch paginated contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get up to 20 contacts per page.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactsElementsDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Access Denied",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Resource not found!",
                    content = {@Content(mediaType = "application/json")})
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/contacts")
    public Flux<ResponseContactsDto> getPaginatedContacts() {

        return contactPaginationService.fetchPaginatedContacts();
    }

    @Operation(summary = "Return All Contacts from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned All Contacts",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseContactsDto.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found!",
                    content = {@Content(mediaType = "application/json")})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("contact")
    public ResponseEntity<List<Contact>> fetchContacts() {

        return ResponseEntity.ok(contactsService.getContacts());
    }

    @Operation(summary = "Fetch paginated contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get up to 20 contacts per page.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactsElementsDto.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found!",
                    content = {@Content(mediaType = "application/json")})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("paged")
    public ResponseEntity<PagedContactsDto> getPagedContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(contactsService.getPagedContacts(page, size));
    }
    @Operation(summary = "Fetch paginated contacts by company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get up to 20 contacts per company per page.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactsElementsDto.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found!",
                    content = {@Content(mediaType = "application/json")})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/paged/{company}")
    public ResponseEntity<PagedContactsDto> getPagedContactsByCompany(
            @Parameter(description = "return list of contacts given company's name!") @PathVariable String company,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(contactsService.getPagedContactsByCompany(page, size, company));

    }

    @Operation (summary = "Add a contact")
    @ApiResponses ({
            @ApiResponse (responseCode = "201", description = "Contacts added!", content = @Content (
                    mediaType = "application/json", schema = @Schema (implementation = Contact.class))),
            @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
            @ApiResponse (responseCode = "403", description = "Access Denied!", content = @Content),
            @ApiResponse (responseCode = "422", description = "Not Processable!", content = @Content)})
    @ResponseStatus
    @PreAuthorize ("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/newContact")
    public ResponseEntity<ResponsePagedContactsDto> saveContact(@RequestBody RequestPagedContactsDto contactsDto) {

        return ResponseEntity.ok(contactsService.saveNewContact(contactsDto));
    }

    @Operation (summary = "Add several contacts - bulk mode")
    @ApiResponses ({
            @ApiResponse (responseCode = "201", description = "Contacts added!", content = @Content (
                    mediaType = "application/json", schema = @Schema (
                    implementation = Contact.class))),
            @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
            @ApiResponse (responseCode = "403", description = "Access Denied!", content = @Content),
            @ApiResponse (responseCode = "422", description = "Not Processable!", content = @Content)})
    @ResponseStatus
    @PreAuthorize ("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<BulkContactsResponseDto> saveBulkContact(@RequestBody List<Contact> contacts) {

        return  ResponseEntity.ok(contactsService.insertBulkContacts(contacts));
    }
}
