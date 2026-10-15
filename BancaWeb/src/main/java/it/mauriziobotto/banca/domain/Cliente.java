package it.mauriziobotto.banca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clienti")
public class Cliente {

    @Id
    @Column(name = "codice", length = 6, nullable = false)
    @NotBlank
    @Size(max = 6)
    private String codice;

    @Column(name = "nome", length = 40, nullable = false)
    @NotBlank
    @Size(max = 40)
    private String nome;

    @Column(name = "indirizzo", length = 40, nullable = false)
    @NotBlank
    @Size(max = 40)
    private String indirizzo;

    protected Cliente() {
    }

    public Cliente(String codice, String nome, String indirizzo) {
        this.codice = codice;
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public String getCodice() {
        return codice;
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
