package actions;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

import dati.*;
import dao.*;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport implements ServletContextAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	public ArrayList<Prodotto>prodotti=new ArrayList<Prodotto>();
	public ArrayList <Prodotto> magazzino = new ArrayList<Prodotto>();
	public static ArrayList <MagazzinoOut> transazioni = new ArrayList<MagazzinoOut>();
	public static ArrayList <MagazzinoOut> txnAdmin = new ArrayList<MagazzinoOut>();
	public static ArrayList <MagazzinoOut> txnUser = new ArrayList<MagazzinoOut>();
	private String username1="";
	private String password1="";
	private String errore="";
	private String stato="";
	private Utente user;
	private String benvenuto;
	String redirectUrl;
	private String info;
	HttpServletRequest request = ServletActionContext.getRequest();

	public String execute(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		UtenteDao utenti=new UtenteDao(sf);
		setServletRequest(request);
		if(!username1.equals("")&&!password1.equals("")){
		user=utenti.getUtenteByCredentials(username1, password1);
		if(utenti.ricerca(username1, password1))
		{
			setFlag("vero");
//			System.out.println(Avanti.flag);
			if (user.getStato().equalsIgnoreCase("admin")){
				HttpSession sessione = request.getSession();
				sessione.setAttribute("codice", user.getId());
				sessione.setAttribute("stato", user.getStato());
				setInfo("on");
				return "admin";
				}
			else{
				HttpSession sessione = request.getSession();
				sessione.setAttribute("nome", user.getNome());
				sessione.setAttribute("cognome", user.getCognome());
				sessione.setAttribute("stato", user.getStato());
				sessione.setAttribute("codice", user.getId());
				sessione.setAttribute("flag", "vero");
				return "redirectUrl";
				}
		}
			else
				{System.out.println("error:\n"+username1+"\n"+password1);
				setFlag("falso");
				System.out.println(Avanti.flag);
				setErrore("errore");

				return ERROR;
				}
		}else 
			{System.out.println("error: username o pass vuoti");
			setFlag("falso");
			System.out.println(Avanti.flag);
			setErrore("errore");
			return ERROR;
			}
	}
	public String logout() {
        
		HttpSession sessione=request.getSession(false);
		String stato = (String) sessione.getAttribute("stato");
		if(stato.equalsIgnoreCase("admin")){
			sessione.removeAttribute("nome");
	        sessione.removeAttribute("cognome");
	        sessione.removeAttribute("codice");
	        sessione.removeAttribute("stato");
			setFlag("falso");
			sessione.setAttribute("flag", "falso");
			return SUCCESS;
			}
		else{
        sessione.removeAttribute("nome");
        sessione.removeAttribute("cognome");
        sessione.removeAttribute("codice");
        sessione.removeAttribute("stato");
		sessione.setAttribute("flag", "falso");
//        setServletRequest(request);
        setFlag("falso");
        return "redirectUrl";
		}
	}
	
	@Override
	public void setServletRequest(HttpServletRequest hsr) {
		String url=hsr.getHeader("Referer");
		redirectUrl=url;
	}
	
	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	public ArrayList<Prodotto> getMagazzino() {
		return magazzino;
	}

	public void setMagazzino(ArrayList<Prodotto> magazzino) {
		this.magazzino = magazzino;
	}

	public Utente getUser() {
		return user;
	}

	public void setUser(Utente user) {
		this.user = user;
	}

	public String getUsername1() {
		return username1;
	}

	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	
	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getErrore() {
		return errore;
	}

	public void setErrore(String errore) {
		this.errore = errore;
	}

	public String getFlag() {
		return Avanti.flag;
	}

	public void setFlag(String flag) {
		Avanti.flag = flag;
	}
	private ServletContext ctx;

	public void setServletContext(ServletContext sc) {
		this.ctx = sc;
	}
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public ArrayList<MagazzinoIn> getArticoli() {
		return Avanti.articoli;
	}

	public void setArticoli(ArrayList<MagazzinoIn> articoli) {
		Avanti.articoli = articoli;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getBenvenuto() {
		return benvenuto;
	}
	public void setBenvenuto(String benvenuto) {
		this.benvenuto = benvenuto;
	}
	public ArrayList<MagazzinoOut> getTransazioni() {
		return transazioni;
	}
	public void setTransazioni(ArrayList<MagazzinoOut> transazioni) {
		Login.transazioni = transazioni;
	}
	public ArrayList<MagazzinoOut> getTxnAdmin() {
		return txnAdmin;
	}
	public void setTxnAdmin(ArrayList<MagazzinoOut> txnAdmin) {
		Login.txnAdmin = txnAdmin;
	}
	public ArrayList<MagazzinoOut> getTxnUser() {
		return txnUser;
	}
	public void setTxnUser(ArrayList<MagazzinoOut> txnUser) {
		Login.txnUser = txnUser;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
