
package actions;
import javax.servlet.ServletContext;
import org. apache.struts.util.*;
import org.hibernate.SessionFactory;

import com.oracle.util.*;

/**
 *
 * @author Hp
 */
public class Registrazione extends ActionSupport implements ServletContextAware{

	private static final long serialVersionUID = 1L;
	private String nom="";
	private String cognom="";
	private String usernam="";
	private String pass1="";
	private String pass2="";
	private String stringa1="";
	private String stringa2="";
	private String errPass="";
	private String erroreUser="";
	private String errCampi="";
	
	public String execute(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		UtenteDao utentedao= new UtenteDao(sf);
		System.out.println("n:"+nom+"\n"+cognom+"\n"+pass1+"\n"+pass2);
		if(!nom.equals("")&&!cognom.equals("")&&!usernam.equals("")&&!pass1.equals("")&&!pass1.equals("")){
		if(pass1.equals(pass2)){
			if(utentedao.controlla(nom, cognom)){
				if(utentedao.controllaUser(usernam)){
					utentedao.createUtente(nom, cognom, usernam, pass1);
					return SUCCESS;
				}
				else{
					setErroreUser("nome utente non disponibile");
					addFieldError("usernam",erroreUser);
					return ERROR;
					}
			}
				else
					{
						//nome utente presente
						stringa1 = "nome e cognome presenti ";
						stringa2="nel database..";
						addFieldError("nom",stringa1);
						addFieldError("cognom",stringa2);
						System.out.println(stringa1+stringa2);
						return ERROR;
					}
			}
		else{
//		password non corretta
		errPass="controlla la password";
		System.out.println(errPass);
		addFieldError("pass2",errPass);
		return ERROR;
	}
		}
		else{
			errCampi="tutti i campi devono essere compilati";
			System.out.println(errCampi);
			return ERROR;
		}
	}
	
//	 public void validate()
//	   {
//	      if (nom == null || nom.trim().equals(""))
//	      {
//	         addFieldError("nom","The name is required");
//	      }
//	      if (cognom == null || cognom.trim().equals(""))
//	      {
//	         addFieldError("cognom","cognom is required");
//	      }
//	      if (usernam == null || usernam.trim().equals(""))
//	      {
//	         addFieldError("usernam","usernam is required");
//	      }
//	      if (pass1 == null || pass1.trim().equals(""))
//	      {
//	         addFieldError("pass1","pass1 is required");
//	      }
//	      if (pass2 == null || pass2.trim().equals(""))
//	      {
//	         addFieldError("pass2","pass2 is required");
//	      }
//	  }
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCognom() {
		return cognom;
	}
	public void setCognom(String cognom) {
		this.cognom = cognom;
	}
	public String getUsernam() {
		return usernam;
	}
	public void setUsernam(String usernam) {
		this.usernam = usernam;
	}
	public String getPass1() {
		return pass1;
	}
	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}
	public String getPass2() {
		return pass2;
	}
	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}
	public String getStringa1() {
		return stringa1;
	}
	public void setStringa1(String stringa1) {
		this.stringa1 = stringa1;
	}
	public String getStringa2() {
		return stringa2;
	}
	public void setStringa2(String stringa2) {
		this.stringa2 = stringa2;
	}
	public String getErrPass() {
		return errPass;
	}
	public void setErrPass(String errPass) {
		this.errPass = errPass;
	}
	public String getErroreUser() {
		return erroreUser;
	}
	public void setErroreUser(String erroreUser) {
		this.erroreUser = erroreUser;
	}
	public String getErrCampi() {
		return errCampi;
	}
	public void setErrCampi(String errCampi) {
		this.errCampi = errCampi;
	}
	private ServletContext ctx;

	public void setServletContext(ServletContext sc) {
		this.ctx = sc;
	}
}