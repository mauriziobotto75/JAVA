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
@Table(name = "LETTORI")
public class Lettore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMINATIVO", nullable = false, length = 120)
    @NotBlank
    @Size(max = 120)
    private String nominativo;

    protected Lettore() {
    }

    public Lettore(String nominativo) {
        this.nominativo = nominativo.strip();
    }

    public Long getId() {
        return id;
    }

    public String getNominativo() {
        return nominativo;
    }
}
