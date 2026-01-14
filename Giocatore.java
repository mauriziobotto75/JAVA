package com.guerra.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nazione;
    private double popolazione;
    private double soldati;
    private double soldatiDisp;
    private double denaro;
    private double cibo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Citta> arrayL;

    public Giocatore() {}

	public void setPopolazione(int i) {
		// TODO Auto-generated method stub
		popolazione = this.popolazione;
	}

	public double getPopolazione() {
		// TODO Auto-generated method stub
		popolazione = getPopolazione();
		return 0;
	}

	public double getSoldati() {
		// TODO Auto-generated method stub
		soldati = getSoldati();
		return 0;
	}
	public void setSoldati(int i) {
		// TODO Auto-generated method stub
		soldati = this.soldati;
	}
     public void setSoldatiDisp(int i) {
	// TODO Auto-generated method stub
	soldatiDisp = this.soldatiDisp;
}

public double getSoldatiDisp() {
	// TODO Auto-generated method stub
	soldatiDisp = getSoldatiDisp();
	return 0;
}
public void setDenaro(int i) {
// TODO Auto-generated method stub
denaro = this.denaro;
}

public double getDenaro() {
// TODO Auto-generated method stub
denaro = getDenaro();
return 0;
} 
public void setCibo(int i) {
	// TODO Auto-generated method stub
	cibo = this.cibo;
	}

	public double getCibo() {
	// TODO Auto-generated method stub
	cibo = getCibo();
	return 0;
	}
}
