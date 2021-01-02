package actions;
import dao.*;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

import dati.*;

import com.opensymphony.xwork2.ActionSupport;

/** Contiene le action di reindirizzamento**/
public class Avanti extends ActionSupport implements ServletContextAware {
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = ServletActionContext.getRequest();
	public static ArrayList<MagazzinoIn> articoli=new ArrayList<MagazzinoIn>();
	public static String flag="falso";
	private String cerca="";
	public static ArrayList<MagazzinoIn> carrelloTemp= new ArrayList <MagazzinoIn>();
	public static ArrayList<MagazzinoOut> carrelloSalvato= new ArrayList<MagazzinoOut>();
	private int codiceprodotto;
	private Prodotto product;
	private Utente user;
	private float tot =0;
	private String alert;
	public ArrayList<Integer> number= new ArrayList<Integer>();
	private String orderBy;
	private int pagina;
	private String ascDesc;

	/** recupera una lista degli articoli in magazzino **/
	public String execute(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao list=new MagazzinoInDao(sf);
//		articoli=list.getAllMagazzinoIn();
//		articoli=list.getMagazzinoInLimit(1);
		articoli=list.getMagazzinoInOrder(null,"asc",1);
		//taglia la descrizione a 50 caratteri
		
		/***
		for (int i =0; i<articoli.size(); i++){
			if(articoli.get(i).getProdotto().getDescrizione().length() > 50)
			    articoli.get(i).getProdotto().setDescrizione(articoli.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
		}
		for (int i =0; i<articoli.size();){//nasconde i prodotti con 0 unità presenti in magazzino
			if(articoli.get(i).getQuantita()<1){
				articoli.remove(i);
			}
			else{i++;}
		}
		***/
		articoli=fixIt(articoli);
		//numero di elementi presenti in magazzino
		number=list.getNumberMagazzinoIn();
		//aggiorna in sessione le quantità in base ai prodotti salvati in carrello
		
		HttpSession sessione= request.getSession();
//		System.out.println(sessione.getAttribute("cart"));
		if(sessione.getAttribute("cart").equals("notempty")){
			carrelloTemp=(ArrayList<MagazzinoIn>) sessione.getAttribute("carrello");
//			for(int i=0; i<carrelloTemp.size();i++){
//				System.out.println(carrelloTemp.get(i).getProdotto().getIdProdotto());
//			}
			for(int i=0;i<articoli.size();i++){
				for(int j=0; j<carrelloTemp.size();j++){
//					System.out.println(articoli.get(i).getProdotto().getIdProdotto());
//					System.out.println(carrelloTemp.get(j).getProdotto().getIdProdotto());
					if(articoli.get(i).getProdotto().getIdProdotto()==carrelloTemp.get(j).getProdotto().getIdProdotto()){
						articoli.get(i).setQuantita(articoli.get(i).getQuantita()-carrelloTemp.get(j).getQuantita());
					}
				}
			}
		}
			return SUCCESS;

	}
	/** ricerca articoli in magazzino**/
	public String ricerca(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao list=new MagazzinoInDao(sf);
		if(!cerca.equals("")){
			articoli=list.like(cerca);
//			System.out.println(articoli.size());
			if(articoli.size()==0){
				articoli=list.getAllMagazzinoInLim(1);
				number=list.getNumberMagazzinoIn();
				setAlert("noItem");
			}
			articoli=fixIt(articoli);
			/***
			for (int i =0; i<articoli.size(); i++){//taglia la descrizione a 50 caratteri
				if(articoli.get(i).getProdotto().getDescrizione().length() > 50)
				    articoli.get(i).getProdotto().setDescrizione(articoli.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
			}
			for (int i =0; i<articoli.size(); ){//nasconde prodotti
				if(articoli.get(i).getQuantita()<1){
					articoli.remove(i);
				}
				else{i++;}
			}
			***/
			return SUCCESS;
		}
		else 
			articoli=list.getAllMagazzinoInLim(1);
		/***
		for (int i =0; i<articoli.size(); i++){//taglia la descrizione a 50 caratteri
			if(articoli.get(i).getProdotto().getDescrizione().length() > 50)
			    articoli.get(i).getProdotto().setDescrizione(articoli.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
		}
		for (int i =0; i<articoli.size(); ){//nasconde prodotti
			if(articoli.get(i).getQuantita()<1){
				articoli.remove(i);
			}
			else{i++;}
		}
		***/
		articoli=fixIt(articoli);
		number=list.getNumberMagazzinoIn();
			return ERROR;
	}
	
//	public String pagination(){
//		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
//		MagazzinoInDao list=new MagazzinoInDao(sf);
//		articoli=list.getAllMagazzinoIn();
//		list.getNumberMagazzinoIn();
//		return SUCCESS;
//	}
	
	public String goToPage(){
//		System.out.println(pagina);
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao list=new MagazzinoInDao(sf);
		HttpSession sessione=request.getSession(false);
		orderBy=(String) sessione.getAttribute("orderby");
		String upDown=ascDesc;
		articoli=list.getMagazzinoInLimit(pagina,orderBy,ascDesc);
		articoli=fixIt(articoli);
		number=list.getNumberMagazzinoIn();
	return SUCCESS;	
	}
	
	public String orderBy(){
//		System.out.println(orderBy);
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao list=new MagazzinoInDao(sf);

		HttpSession sessione=request.getSession(false);
		String ord=(String) sessione.getAttribute("orderby");
//		System.out.println(ord);
//		System.out.println(orderBy);
		try{
		if(ord.equals(orderBy)){
			System.out.println("uguali");
			if (ascDesc.equals("asc"))
			{ascDesc="desc";
			System.out.println("cambio");}
			else 
			{ascDesc="asc";
			System.out.println("cambio");}}
		else ascDesc="asc";
		}catch(NullPointerException e){ascDesc="asc";
		System.out.println("eccezione");}
		sessione.setAttribute("orderby",orderBy);
		articoli=list.getMagazzinoInOrder(orderBy,ascDesc, pagina);
		articoli=fixIt(articoli);
		number=list.getNumberMagazzinoIn();
		return SUCCESS;
	}
	
	/** indirizza alla pagina prodottoSingolo**/
	public String goTo(){
		System.out.println(codiceprodotto);
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		ProdottoDao prodotto=new ProdottoDao(sf);
		setProduct(prodotto.getProdottoById(codiceprodotto));
		return SUCCESS;
	}
	/** indirizza alla pagina ilMioAccount **/
	public String goToAccount(){ 
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		HttpSession sessione=request.getSession(false);
        int codice= (int)sessione.getAttribute("codice");
        MagazzinoOutDao mag= new MagazzinoOutDao(sf);
		Login.txnUser=mag.getUserMagazzino(codice,1);
		number=mag.getNumberMagazzinoOutUser(codice);
		UtenteDao utente=new UtenteDao(sf);
		user= utente.getUtenteById(codice);
		//recupero eventuali ordini salvati
		carrelloSalvato=mag.getUserCarrelloSalvato(codice);
		/**System.out.println(carrelloSalvato.size());*/
		for (int i=0; i<carrelloSalvato.size(); i++){
			tot=tot+(carrelloSalvato.get(i).getProdotto().getPrezzo()*carrelloSalvato.get(i).getQuantita());
			}
				//taglia la descrizione a 50 caratteri
				for (int i =0; i<carrelloSalvato.size(); i++){
					if(carrelloSalvato.get(i).getProdotto().getDescrizione().length() > 50)
						carrelloSalvato.get(i).getProdotto().setDescrizione(carrelloSalvato.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
					}
		return SUCCESS;
	}
	public String goToAccountPage(){
//		System.out.println(pagina);
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		HttpSession sessione=request.getSession(false);
        int codice= (int)sessione.getAttribute("codice");
		MagazzinoOutDao mag=new MagazzinoOutDao(sf);
		Login.txnUser=mag.getUserMagazzino(codice,pagina);

		number=mag.getNumberMagazzinoOutUser(codice);
	return SUCCESS;	
	}
	
	/**reindirizza all'indice e fa il logout**/
	/**utilizzato nella pagina ilMioAccount**/
	public String goToIndex(){
		HttpSession sessione=request.getSession(false);
		sessione.removeAttribute("nome");
        sessione.removeAttribute("cognome");
        sessione.removeAttribute("codice");
        sessione.removeAttribute("stato");
		sessione.setAttribute("flag", "falso");
        //setServletRequest(request);
        setFlag("falso");
		return SUCCESS;
	}
	
	
	public ArrayList<MagazzinoIn> fixIt(ArrayList<MagazzinoIn> arr){
		for (int i =0; i<arr.size(); i++){//taglia la descrizione a 50 caratteri
			if(arr.get(i).getProdotto().getDescrizione().length() > 50)
				arr.get(i).getProdotto().setDescrizione(arr.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
		}
//		for (int i =0; i<arr.size(); ){//nasconde prodotti
//			if(arr.get(i).getQuantita()<1){
//				arr.remove(i);
//			}
//			else{i++;}
//		}
		return arr;
	}
	
	//GETTERS AND SETTERS
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		Avanti.flag = flag;
	}
	private ServletContext ctx;
	
	public void setServletContext(ServletContext sc) {
		this.ctx = sc;
	}
	public String getCerca() {
		return cerca;
	}
	public void setCerca(String cerca) {
		this.cerca = cerca;
	}
	public ArrayList<MagazzinoIn> getCarrelloTemp() {
		return carrelloTemp;
	}
	public void setCarrelloTemp(ArrayList<MagazzinoIn> carrelloTemp) {
		Avanti.carrelloTemp = carrelloTemp;
	}
	public ArrayList<MagazzinoIn> getArticoli() {
		return articoli;
	}
	public void setArticoli(ArrayList<MagazzinoIn> articoli) {
		Avanti.articoli = articoli;
	}
	
	public Prodotto getProduct() {
		return product;
	}
	
	public void setProduct(Prodotto product) {
		this.product = product;
	}
	
	public int getCodiceprodotto() {
		return codiceprodotto;
	}
	
	public void setCodiceprodotto(int codiceprodotto) {
		this.codiceprodotto = codiceprodotto;
	}
	public ArrayList<MagazzinoOut> getTxnUser() {
		return Login.txnUser;
	}
	public void setTxnUser(ArrayList<MagazzinoOut> txnUser) {
		Login.txnUser = txnUser;
	}

	public Utente getUser() {
		return user;
	}

	public void setUser(Utente user) {
		this.user = user;
	}

	public ArrayList<MagazzinoOut> getCarrelloSalvato() {
		return carrelloSalvato;
	}
	
	public void setCarrelloSalvato(ArrayList<MagazzinoOut> carrelloSalvato) {
		Avanti.carrelloSalvato = carrelloSalvato;
	}

	public float getTot() {
		return tot;
	}

	public void setTot(float tot) {
		this.tot = tot;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}

	public ArrayList<Integer> getNumber() {
		return number;
	}
	public void setNumber(ArrayList<Integer> number) {
		this.number = number;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public String getAscDesc() {
		return ascDesc;
	}
	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
}
