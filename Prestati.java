package biblioteca;

import java.sql.Date;

public class Prestati {

	private String CodLibri_fk;
	private String Cf_p_fk;
	private Date Inizio;
	private Date Restituzione;
	private String Nome;
	private String Cognome;
	private String Titolo;
	
	Prestati(){}
	
	public Prestati(String codlibri_fk, String cf_p_fk, Date inizio, Date restituzione, String nome, String cognome, String titolo) {
		super();
		this.CodLibri_fk=codlibri_fk;
		this.Cf_p_fk=cf_p_fk;
		this.Inizio=inizio;
		this.Restituzione=restituzione;
		this.Nome=nome;
		this.Cognome=cognome;
		this.Titolo=titolo;
		
		
	}
	

	public String getCodLibri_fk() {
		return CodLibri_fk;
	}

	public void setCodLibri_fk(String codLibri_fk) {
		CodLibri_fk = codLibri_fk;
	}

	public String getCf_p_fk() {
		return Cf_p_fk;
	}

	public void setCf_p_fk(String cf_p_fk) {
		Cf_p_fk = cf_p_fk;
	}

	public Date getInizio() {
		return Inizio;
	}

	public void setInizio(Date inizio) {
		Inizio = inizio;
	}

	public Date getRestituzione() {
		return Restituzione;
	}

	public void setRestituzione(Date restituzione) {
		Restituzione = restituzione;
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

	public String getTitolo() {
		return Titolo;
	}

	public void setTitolo(String titolo) {
		Titolo = titolo;
	}

	@Override
	public String toString() {
		return "Prestito [Codice Libro = " + CodLibri_fk + ", Titolo=" + Titolo 
				+ ", Codice Fiscale = " + Cf_p_fk + ", Nome= " + Nome + ", Cognome=" + Cognome
				+ ", Inizio Prestito =" + Inizio  + ", Restituzione " + Restituzione + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
