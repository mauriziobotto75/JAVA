package it.mauriziobotto.biblioteca.service;

public class PrestitoNonDisponibileException extends RuntimeException {

    public PrestitoNonDisponibileException(String messaggio) {
        super(messaggio);
    }
}
