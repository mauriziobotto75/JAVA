package biblioteca;

public class AggLibro {

	private String Titolo;
	private String Genere;
	private String Autore;
	private int codLibri;

	
	AggLibro(){}
	
	public AggLibro(String titolo, String genere, String autore, int codlibri) {
		super();
		this.Titolo=titolo;
		this.Genere=genere;
		this.Autore=autore;
		this.codLibri=codlibri;
	}
	
	
	

	

	public String getTitolo() {
		return Titolo;
	}

	public void setTitolo(String titolo) {
		Titolo = titolo;
	}

	public String getGenere() {
		return Genere;
	}

	public void setGenere(String genere) {
		Genere = genere;
	}

	public String getAutore() {
		return Autore;
	}

	public void setAutore(String autore) {
		Autore = autore;
	}
	

	public int getcodLibri() {
		return codLibri;
	}

	public void setcodLibri(int codlibri) {
		codLibri = codlibri;
	}

	@Override
	public String toString() {
		return " Titolo=" + Titolo + ", Genere=" + Genere
				+ ", Autore=" + Autore ;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
