package it.mauriziobotto.biblioteca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "LIBRI")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITOLO", nullable = false, length = 200)
    @NotBlank
    @Size(max = 200)
    private String titolo;

    @Column(name = "AUTORE", nullable = false, length = 120)
    @NotBlank
    @Size(max = 120)
    private String autore;

    protected Libro() {
    }

    public Libro(String titolo, String autore) {
        this.titolo = titolo.strip();
        this.autore = autore.strip();
    }

    public Long getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }
}
