package it.mauriziobotto.banca.domain;

import java.math.BigDecimal;

public enum TipoMovimento {
    VE("Versamento", true),
    AC("Accredito", true),
    PR("Prelievo", false),
    BO("Bollettino", false),
    PA("Pagamento", false);

    private final String descrizione;
    private final boolean accredito;

    TipoMovimento(String descrizione, boolean accredito) {
        this.descrizione = descrizione;
        this.accredito = accredito;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public BigDecimal variazione(BigDecimal importo) {
        return accredito ? importo : importo.negate();
    }

    public boolean richiedeDisponibilita() {
        return !accredito;
    }
}
