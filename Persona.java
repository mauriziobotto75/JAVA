package biblioteca;

public class Persona {

	private String Cf_p;
	private String Nome;
	private String Cognome;
	private String Indirizzo;
	private String Cap;

	
	Persona(){}
	
	public Persona(String cf_p, String nome, String cognome, String indirizzo, String cap) {
		super();
		this.Cf_p=cf_p;
		this.Nome=nome;
		this.Cognome=cognome;
		this.Indirizzo=indirizzo;
		this.Cap=cap;
	}
	

	public String getCf_p() {
		return Cf_p;
	}

	public void setCf_p(String cf_p) {
		Cf_p = cf_p;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getIndirizzo() {
		return Indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		Indirizzo = indirizzo;
	}

	public String getCap() {
		return Cap;
	}

	public void setCap(String cap) {
		Cap = cap;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
