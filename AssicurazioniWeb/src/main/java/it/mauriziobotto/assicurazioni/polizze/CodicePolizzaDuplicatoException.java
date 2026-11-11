package it.mauriziobotto.assicurazioni.polizze;

public class CodicePolizzaDuplicatoException extends RuntimeException {

    public CodicePolizzaDuplicatoException(String codice) {
        super("Il codice polizza " + codice + " è già presente.");
    }
}
