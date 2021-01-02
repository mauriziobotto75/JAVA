package dati;

import java.util.Date;
import java.util.Set;

public class Ordine {
	private int idOrdine;
	private String stato;
	private Date data;
	
	private Set<MagazzinoOut> acquisto;
	private Utente utente;

	public Ordine(){
		
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public Set<MagazzinoOut> getAcquisto() {
		return acquisto;
	}

	public void setAcquisto(Set<MagazzinoOut> acquisto) {
		this.acquisto = acquisto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
}
