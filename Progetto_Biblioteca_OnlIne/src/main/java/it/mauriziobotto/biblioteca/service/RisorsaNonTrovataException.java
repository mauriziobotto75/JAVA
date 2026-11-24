package it.mauriziobotto.biblioteca.service;

public class RisorsaNonTrovataException extends RuntimeException {

    public RisorsaNonTrovataException(String risorsa, Long id) {
        super(risorsa + " con id " + id + " non trovato.");
    }
}
