package br.dev.ferreiras.challenge.service.exceptions;

public class ContactProcessingException extends RuntimeException{
   public ContactProcessingException(String message) {
       super(message);
   }
}
