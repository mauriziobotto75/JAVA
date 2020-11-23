package biblioteca;

public class Login {
	
	private String Cf_Add;
	private String User;
	private String Password;
	private String Nome;
	private String Cognome;
	
	Login(){}
	
	public Login(String cf_add, String user, String password, String nome, String cognome) {
		super();
		this.Cf_Add=cf_add;
		this.User=user;
		this.Password=password;
		this.Nome=nome;
		this.Cognome=cognome;
	}
	
	
	

	public String getCf_Add() {
		return Cf_Add;
	}

	public void setCf_Add(String cf_add) {
		Cf_Add = cf_add;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
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


	@Override
	public String toString() {
		return "Benvenuto " + Nome +" "+ Cognome + "!";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
