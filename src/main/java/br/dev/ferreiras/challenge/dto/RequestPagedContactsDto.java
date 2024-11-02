package br.dev.ferreiras.challenge.dto;

import java.time.Instant;

public record RequestPagedContactsDto(String name, String email, String company,
                                      Instant createdAt, Instant updatedAt, Boolean deleted, String password) {
}
