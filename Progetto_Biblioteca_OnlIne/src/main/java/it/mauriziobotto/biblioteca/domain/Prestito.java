package it.mauriziobotto.biblioteca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "PRESTITI")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LIBRO_ID", nullable = false)
    private Libro libro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LETTORE_ID", nullable = false)
    private Lettore lettore;

    @Column(name = "DATA_PRESTITO", nullable = false)
    private LocalDate dataPrestito;

    @Column(name = "DATA_RESTITUZIONE")
    private LocalDate dataRestituzione;

    protected Prestito() {
    }

    public Prestito(Libro libro, Lettore lettore, LocalDate dataPrestito) {
        this.libro = libro;
        this.lettore = lettore;
        this.dataPrestito = dataPrestito;
    }

    public Long getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Lettore getLettore() {
        return lettore;
    }

    public LocalDate getDataPrestito() {
        return dataPrestito;
    }

    public LocalDate getDataRestituzione() {
        return dataRestituzione;
    }

    public boolean isAttivo() {
        return dataRestituzione == null;
    }

    public void registraRestituzione(LocalDate data) {
        dataRestituzione = data;
    }
}
