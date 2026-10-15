package it.mauriziobotto.banca.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteForm {

    @NotBlank(message = "Il codice cliente è obbligatorio.")
    @Size(max = 6, message = "Il codice cliente può contenere al massimo 6 caratteri.")
    private String codice;

    @NotBlank(message = "Il nominativo è obbligatorio.")
    @Size(max = 40, message = "Il nominativo può contenere al massimo 40 caratteri.")
    private String nome;

    @NotBlank(message = "L'indirizzo è obbligatorio.")
    @Size(max = 40, message = "L'indirizzo può contenere al massimo 40 caratteri.")
    private String indirizzo;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
