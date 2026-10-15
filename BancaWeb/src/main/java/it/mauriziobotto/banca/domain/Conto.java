package it.mauriziobotto.banca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "conti")
public class Conto {

    public static final String OPERATIVO = "A";
    public static final String CHIUSO = "C";

    @Id
    @Column(name = "numero", length = 12, nullable = false)
    @NotBlank
    @Size(max = 12)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codice_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "abi", length = 5)
    private String abi;

    @Column(name = "cab", length = 5)
    private String cab;

    @Column(name = "filiale", length = 4)
    private String filiale;

    @Column(name = "data_apertura", nullable = false)
    private LocalDate dataApertura;

    @Column(name = "saldo", precision = 13, scale = 2, nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "fido", precision = 13, scale = 2, nullable = false)
    private BigDecimal fido = BigDecimal.ZERO;

    @Column(name = "stato", length = 1, nullable = false)
    private String stato = OPERATIVO;

    @Version
    private long versione;

    protected Conto() {
    }

    public Conto(String numero, Cliente cliente, String abi, String cab, String filiale,
                 LocalDate dataApertura, BigDecimal fido) {
        this.numero = numero;
        this.cliente = cliente;
        this.abi = abi;
        this.cab = cab;
        this.filiale = filiale;
        this.dataApertura = dataApertura;
        this.fido = fido;
    }

    public void aggiornaFido(BigDecimal nuovoFido) {
        this.fido = nuovoFido;
    }

    public void chiudi() {
        this.stato = CHIUSO;
    }

    public void applicaSaldo(BigDecimal variazione) {
        this.saldo = this.saldo.add(variazione);
    }

    public boolean isOperativo() {
        return OPERATIVO.equals(stato);
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getAbi() {
        return abi;
    }

    public String getCab() {
        return cab;
    }

    public String getFiliale() {
        return filiale;
    }

    public LocalDate getDataApertura() {
        return dataApertura;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getFido() {
        return fido;
    }

    public String getStato() {
        return stato;
    }
}
