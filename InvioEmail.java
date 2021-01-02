package actions;
import javax.mail.MessagingException;

import util.*;

import com.opensymphony.xwork2.ActionSupport;

public class InvioEmail extends ActionSupport{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String dest="thimble@hotmail.it";
String mitt="provaperjava@gmail.com";
String oggetto="Contattaci";
String testoEmail="";
String name="";
String surname="";
String email_add="";
String email="";

	public String getTestoEmail() {
	return testoEmail;
}
public void setTestoEmail(String testoEmail) {
	this.testoEmail = testoEmail;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSurname() {
	return surname;
}
public void setSurname(String surname) {
	this.surname = surname;
}
public String getEmail_add() {
	return email_add;
}
public void setEmail_add(String email_add) {
	this.email_add = email_add;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
	
	public String execute(){
		testoEmail="sei stato contattato da \n nome: "+name+"\n"+"cognome: "+surname+"\n"+
	"indirizzo email: "+email_add+"\n"+email;
		try {
			Javamail.sendMail (dest, mitt, oggetto, testoEmail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(name+" \n"+surname+" \n"+email_add+" \n"+email);
		
		return "invia";
	}
}
