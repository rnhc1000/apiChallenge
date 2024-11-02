package br.dev.ferreiras.challenge.service.exceptions;

public class MediaNotSupportedException extends RuntimeException{
    public MediaNotSupportedException(String message) {

        super(message);
    }
}
