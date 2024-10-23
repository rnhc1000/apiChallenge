package br.dev.ferreiras.challenge.dto;

import java.util.List;

public record ResponseContactsDto(List<ContactsElementsDto> contacts) {
}
