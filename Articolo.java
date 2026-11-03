
package com.example.magazzino.model;

public class Articolo {
    private int id;
    private String nome;
    private double prezzo;

    public Articolo() {}

    public Articolo(int id, String nome, double prezzo) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public Articolo(String nome, double prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPrezzo() { return prezzo; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    @Override
    public String toString() {
        return "Articolo{" +
                "id=" + id +
                ", nome='" + nome + ''' +
                ", prezzo=" + prezzo +
                '}';
    }
}
