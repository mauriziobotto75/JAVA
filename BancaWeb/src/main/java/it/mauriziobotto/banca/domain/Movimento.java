package it.mauriziobotto.banca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimenti")
public class Movimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numero_conto", nullable = false)
    private Conto conto;

    @Column(name = "data_operazione", nullable = false)
    private LocalDate dataOperazione;

    @Column(name = "data_valuta", nullable = false)
    private LocalDate dataValuta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 2, nullable = false)
    private TipoMovimento tipo;

    @Column(name = "causale", length = 40, nullable = false)
    private String causale;

    @Column(name = "importo", precision = 13, scale = 2, nullable = false)
    private BigDecimal importo;

    protected Movimento() {
    }

    public Movimento(Conto conto, LocalDate dataOperazione, LocalDate dataValuta,
                     TipoMovimento tipo, String causale, BigDecimal importo) {
        this.conto = conto;
        this.dataOperazione = dataOperazione;
        this.dataValuta = dataValuta;
        this.tipo = tipo;
        this.causale = causale;
        this.importo = importo;
    }

    public Long getNumero() {
        return numero;
    }

    public Conto getConto() {
        return conto;
    }

    public LocalDate getDataOperazione() {
        return dataOperazione;
    }

    public LocalDate getDataValuta() {
        return dataValuta;
    }

    public TipoMovimento getTipo() {
        return tipo;
    }

    public String getCausale() {
        return causale;
    }

    public BigDecimal getImporto() {
        return importo;
    }
}
