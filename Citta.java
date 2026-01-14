package dati;

public class Citta {
	private String naz;
	private String nome;
	private int nsoldati;
	private int popolazione;
	
	public Citta( String naz, String nome, int nsoldati, int popolazione){
		setNaz(naz);
		setNome(nome);
		setNsoldati(nsoldati);
		setPopolazione(popolazione);
	}
	

	public String getNaz() {
		return naz;
	}
	public void setNaz(String naz) {
		this.naz = naz;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getNsoldati() {
		return nsoldati;
	}
	public void setNsoldati(int nsoldati) {
		this.nsoldati = nsoldati;
	}
	@Override
	public String toString() {
		return nome +",\n" + nsoldati;
	}


	public int getPopolazione() {
		return popolazione;
	}


	public void setPopolazione(int popolazione) {
		this.popolazione = popolazione;
	}

}
