package dati;

import java.util.Date;

public class MagazzinoOut {
	private int idOut;
	private int quantita;
	private Date data;
	
	private Prodotto prodotto;
	private Ordine ordine;
	
	public MagazzinoOut(){
		
	}

	public int getIdOut() {
		return idOut;
	}

	public void setIdOut(int idOut) {
		this.idOut = idOut;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
}
