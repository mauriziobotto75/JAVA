package dati;

public class MagazzinoIn {
	private int idIn;

	private int quantita;
	
	private Prodotto prodotto;

	public MagazzinoIn(){
		
	}
	
	public int getIdIn() {
		return idIn;
	}

	public void setIdIn(int idIn) {
		this.idIn = idIn;
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

}
