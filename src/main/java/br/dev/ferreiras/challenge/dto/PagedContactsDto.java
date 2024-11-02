package br.dev.ferreiras.challenge.dto;

import java.util.List;

public record PagedContactsDto(
        List<ContactsDto> contacts,
        int page,
        int size, int totalPages, long totalRecords) {
}
