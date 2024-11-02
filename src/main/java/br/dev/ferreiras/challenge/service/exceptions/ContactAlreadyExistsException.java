package br.dev.ferreiras.challenge.service.exceptions;

public class ContactAlreadyExistsException extends RuntimeException{
    public ContactAlreadyExistsException(String message) {
        super(message);
    }
}
