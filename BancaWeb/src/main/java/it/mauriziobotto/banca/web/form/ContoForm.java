package it.mauriziobotto.banca.web.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContoForm {

    @NotBlank(message = "Il numero conto è obbligatorio.")
    @Size(max = 12, message = "Il numero conto può contenere al massimo 12 caratteri.")
    private String numero;

    @NotBlank(message = "Selezionare un cliente.")
    private String codiceCliente;

    @Size(max = 5)
    private String abi;

    @Size(max = 5)
    private String cab;

    @Size(max = 4)
    private String filiale;

    @NotNull(message = "La data di apertura è obbligatoria.")
    private LocalDate dataApertura;

    @NotNull(message = "Il fido è obbligatorio.")
    @DecimalMin(value = "0.00", message = "Il fido non può essere negativo.")
    @Digits(integer = 11, fraction = 2, message = "Il fido deve avere al massimo 11 cifre intere e 2 decimali.")
    private BigDecimal fido = BigDecimal.ZERO;

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getCodiceCliente() { return codiceCliente; }
    public void setCodiceCliente(String codiceCliente) { this.codiceCliente = codiceCliente; }
    public String getAbi() { return abi; }
    public void setAbi(String abi) { this.abi = abi; }
    public String getCab() { return cab; }
    public void setCab(String cab) { this.cab = cab; }
    public String getFiliale() { return filiale; }
    public void setFiliale(String filiale) { this.filiale = filiale; }
    public LocalDate getDataApertura() { return dataApertura; }
    public void setDataApertura(LocalDate dataApertura) { this.dataApertura = dataApertura; }
    public BigDecimal getFido() { return fido; }
    public void setFido(BigDecimal fido) { this.fido = fido; }
}
