package it.mauriziobotto.assicurazioni.polizze;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "POLIZZE")
public class Polizza {

    public static final String NON_PAGATA = "N";
    public static final String PAGATA = "P";

    @Id
    @Column(name = "COD_POLIZZA", nullable = false, length = 10, columnDefinition = "CHAR(10)")
    @NotBlank(message = "Il codice polizza è obbligatorio.")
    @Size(max = 10, message = "Il codice polizza può contenere al massimo 10 caratteri.")
    private String codicePolizza;

    @Column(name = "NOME_CLIENTE", length = 40)
    @Size(max = 40, message = "Il nome cliente può contenere al massimo 40 caratteri.")
    private String nomeCliente;

    @Column(name = "INDIRIZZO", length = 60)
    @Size(max = 60, message = "L'indirizzo può contenere al massimo 60 caratteri.")
    private String indirizzo;

    @Column(name = "IMPORTO", precision = 9, scale = 2)
    @DecimalMin(value = "0.00", message = "L'importo non può essere negativo.")
    @Digits(integer = 7, fraction = 2, message = "L'importo ammette fino a 7 cifre intere e 2 decimali.")
    private BigDecimal importo;

    @Column(name = "SCADENZA")
    private LocalDate scadenza;

    @Column(name = "SIGLA_AUTO", length = 10, columnDefinition = "CHAR(10)")
    @Size(max = 10, message = "La sigla auto può contenere al massimo 10 caratteri.")
    private String siglaAuto;

    @Column(name = "NUM_CAVALLI")
    @Min(value = 1, message = "Il numero di cavalli deve essere maggiore di zero.")
    @Max(value = 999, message = "Il numero di cavalli non può superare 999.")
    private Short numeroCavalli;

    @Column(name = "STATO_PAGAMENTO", length = 1, columnDefinition = "CHAR(1)")
    @Pattern(regexp = "[NP]", message = "Lo stato pagamento deve essere N (non pagata) o P (pagata).")
    private String statoPagamento = NON_PAGATA;

    @Column(name = "DATA_PAGAMENTO")
    @PastOrPresent(message = "La data di pagamento non può essere futura.")
    private LocalDate dataPagamento;

    public Polizza() {
    }

    public Polizza(String codicePolizza) {
        setCodicePolizza(codicePolizza);
    }

    public String getCodicePolizza() {
        return codicePolizza;
    }

    public void setCodicePolizza(String codicePolizza) {
        this.codicePolizza = codicePolizza == null ? null : codicePolizza.strip();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = normalize(nomeCliente);
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = normalize(indirizzo);
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }

    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }

    public String getSiglaAuto() {
        return siglaAuto;
    }

    public void setSiglaAuto(String siglaAuto) {
        this.siglaAuto = normalize(siglaAuto);
    }

    public Short getNumeroCavalli() {
        return numeroCavalli;
    }

    public void setNumeroCavalli(Short numeroCavalli) {
        this.numeroCavalli = numeroCavalli;
    }

    public String getStatoPagamento() {
        return statoPagamento;
    }

    public void setStatoPagamento(String statoPagamento) {
        this.statoPagamento = statoPagamento == null ? null : statoPagamento.strip().toUpperCase();
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public boolean isPagata() {
        return PAGATA.equals(statoPagamento);
    }

    public void registraPagamento(LocalDate data) {
        statoPagamento = PAGATA;
        dataPagamento = Objects.requireNonNull(data);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.strip();
    }
}
