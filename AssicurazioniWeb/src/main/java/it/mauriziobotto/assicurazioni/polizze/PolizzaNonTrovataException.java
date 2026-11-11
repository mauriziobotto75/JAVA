package it.mauriziobotto.assicurazioni.polizze;

public class PolizzaNonTrovataException extends RuntimeException {

    public PolizzaNonTrovataException(String codice) {
        super("La polizza " + codice + " non è presente in archivio.");
    }
}
