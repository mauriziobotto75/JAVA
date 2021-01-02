package dati;

public class Prodotto {
	private int idProdotto;
	private String nomeProdotto;
	private String descrizione;
	private String immagine;
	private float prezzo;
	
	private MagazzinoIn in;
	private MagazzinoOut out;
	
	public Prodotto(){
		
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public MagazzinoIn getIn() {
		return in;
	}

	public void setIn(MagazzinoIn in) {
		this.in = in;
	}

	public MagazzinoOut getOut() {
		return out;
	}

	public void setOut(MagazzinoOut out) {
		this.out = out;
	}
	
}
