package br.dev.ferreiras.challenge.dto;

import java.time.*;

public record ContactsDto(
        Long id, String name, String email,
        String company, Instant createdAt, Instant updateAt,
        Boolean deleted) {
}
