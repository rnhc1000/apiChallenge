package br.dev.ferreiras.challenge.dto;

import java.time.Instant;

public record ErrorResponseDto(int HttpCode, String message, Instant timeStamp) {
}
