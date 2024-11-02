package br.dev.ferreiras.challenge.controller.handler;

import br.dev.ferreiras.challenge.dto.ErrorResponseDto;
import br.dev.ferreiras.challenge.service.exceptions.ContactProcessingException;
import br.dev.ferreiras.challenge.service.exceptions.MediaNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

/**
 * Customize Error Messages
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */

@ControllerAdvice(annotations = RestController.class)
public class ContactControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String JSON_DECODING_ERROR = "Processing error when decoding Json";
    private static final String ACCESS_DENIED = "Access denied";

    @ExceptionHandler(value={ContactProcessingException.class})
    public ResponseEntity<ErrorResponseDto> contactProcessingJson(final ContactProcessingException exception,
                                                                  final WebRequest webRequest
    ) {
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                ContactControllerExceptionHandler.JSON_DECODING_ERROR,
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponseDto);
    }

    @ExceptionHandler(value={BadCredentialsException.class})
    public ResponseEntity<ErrorResponseDto> badCredentials(final BadCredentialsException exception,
                                                           final WebRequest request) {
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.FORBIDDEN.value(),
                ContactControllerExceptionHandler.ACCESS_DENIED,
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(errorResponseDto);
    }

    @ExceptionHandler(value={UnsupportedMediaTypeException.class})
    public ResponseEntity<ErrorResponseDto> mediaException(final UnsupportedMediaTypeException exception) {

        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                ContactControllerExceptionHandler.JSON_DECODING_ERROR,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).body(errorResponseDto);
    }
}
