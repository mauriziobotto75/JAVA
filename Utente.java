package dati;

public class Utente {
	private String codice;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	
	public Utente(String codice, String username, String password){
		setCodice(codice);
		setUsername(username);
		setPassword(password);
	}

	public Utente (String codice, String nome, String cognome, String username, String password){
		setCodice(codice);
		setNome(nome);
		setCognome(cognome);
		setUsername(username);
		setPassword(password);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

}
