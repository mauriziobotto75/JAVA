package it.mauriziobotto.banca.web.form;

import it.mauriziobotto.banca.domain.TipoMovimento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimentoForm {

    @NotBlank(message = "Il numero conto è obbligatorio.")
    @Size(max = 12)
    private String numeroConto;

    @NotNull(message = "Selezionare il tipo di movimento.")
    private TipoMovimento tipo;

    @NotNull(message = "L'importo è obbligatorio.")
    @DecimalMin(value = "0.01", message = "L'importo deve essere maggiore di zero.")
    @Digits(integer = 11, fraction = 2, message = "L'importo deve avere al massimo 11 cifre intere e 2 decimali.")
    private BigDecimal importo;

    @NotBlank(message = "La causale è obbligatoria.")
    @Size(max = 40, message = "La causale può contenere al massimo 40 caratteri.")
    private String causale;

    @NotNull(message = "La data operazione è obbligatoria.")
    private LocalDate dataOperazione;

    @NotNull(message = "La data valuta è obbligatoria.")
    private LocalDate dataValuta;

    public String getNumeroConto() { return numeroConto; }
    public void setNumeroConto(String numeroConto) { this.numeroConto = numeroConto; }
    public TipoMovimento getTipo() { return tipo; }
    public void setTipo(TipoMovimento tipo) { this.tipo = tipo; }
    public BigDecimal getImporto() { return importo; }
    public void setImporto(BigDecimal importo) { this.importo = importo; }
    public String getCausale() { return causale; }
    public void setCausale(String causale) { this.causale = causale; }
    public LocalDate getDataOperazione() { return dataOperazione; }
    public void setDataOperazione(LocalDate dataOperazione) { this.dataOperazione = dataOperazione; }
    public LocalDate getDataValuta() { return dataValuta; }
    public void setDataValuta(LocalDate dataValuta) { this.dataValuta = dataValuta; }
}
