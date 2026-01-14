package com.guerra.model;

import jakarta.persistence.*;

@Entity
public class Citta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naz;
    private String nome;
    private int nsoldati;
    private int popolazione;

    public Citta() {}

    public Citta(String naz, String nome, int nsoldati, int popolazione) {
        this.naz = naz;
        this.nome = nome;
        this.nsoldati = nsoldati;
        this.popolazione = popolazione;
    }

    // Getter e Setter
}
