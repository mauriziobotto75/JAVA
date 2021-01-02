package actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.ActionSupport;

import dati.MagazzinoIn;
//import dati.MagazzinoOut;
import dati.Prodotto;
import dao.*;

public class Carrello extends ActionSupport implements ServletContextAware{
	private static final long serialVersionUID = 1L;

	private int cod;
	private int idMag;
	private int qta;
	private int num;
	public String cart="vuoto";
	private String qtaerr="";
	private float totale =0;
	HttpServletRequest request = ServletActionContext.getRequest();

	/** Aggiunge prodotto al carrello **/
	public String execute() {
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		ProdottoDao prodottodao= new ProdottoDao(sf);
		Prodotto prodotto= prodottodao.getProdottoById(cod);
		int index=ricerca(idMag);
		if(Avanti.articoli.get(index).getQuantita()>=qta && qta>0){
			if(controlla(prodotto)>=0)//controlla che il prodotto non sia già in carrello
			{
			Avanti.carrelloTemp.get(controlla(prodotto)).setQuantita((Avanti.carrelloTemp.get(controlla(prodotto)).getQuantita())+qta);	
			}
			else{
				MagazzinoIn articolo= new MagazzinoIn();
				articolo.setIdIn(idMag);
				articolo.setProdotto(prodotto);
				articolo.setQuantita(qta);
				System.out.println(cod);
				Avanti.carrelloTemp.add(articolo);
				}
			/***/
			Avanti.articoli.get(index).setQuantita(Avanti.articoli.get(index).getQuantita()-qta);
			/***/
			cart="notempty";
			//inserire il carrello e la stringa cart in session
			HttpSession sessione = request.getSession();
			sessione.setAttribute("carrello", Avanti.carrelloTemp);
			sessione.setAttribute("cart", cart);
			return SUCCESS;
			}
		else{
			setQtaerr("controlla le quantità");
			return ERROR;
		}
	}
	
	//va al carrello
	public String vaiAlCarrello(){
		HttpSession sessione = request.getSession();
//		cart=(String) sessione.getAttribute("cart");
//		System.out.println("carrello:");
//		System.out.println(cart);
//		if (cart.equals("vuoto"))
//		{// carrello vuoto!!!
//			
//		}
//		else{
		Avanti.carrelloTemp=(ArrayList<MagazzinoIn>) sessione.getAttribute("carrello");
		for (int i=0; i<Avanti.carrelloTemp.size(); i++){
			totale=totale+(Avanti.carrelloTemp.get(i).getProdotto().getPrezzo()*Avanti.carrelloTemp.get(i).getQuantita());
			}
				//taglia la descrizione a 50 caratteri
				for (int i =0; i<Avanti.carrelloTemp.size(); i++){
					if(Avanti.carrelloTemp.get(i).getProdotto().getDescrizione().length() > 50)
						Avanti.carrelloTemp.get(i).getProdotto().setDescrizione(Avanti.carrelloTemp.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
					}
//		}
		return SUCCESS;
	}

	//rimuovi da carrello
	public String rimuovi(){
		int temp=0;
		for(int i=0; i< Avanti.carrelloTemp.size(); i++){
		if(Avanti.carrelloTemp.get(i).getProdotto().getIdProdotto()==cod){
			temp=Avanti.carrelloTemp.get(i).getQuantita();
			Avanti.carrelloTemp.remove(i);
			}
		}
		for (int i=0; i<Avanti.articoli.size(); i++){
			if(Avanti.articoli.get(i).getIdIn()==idMag){
				Avanti.articoli.get(i).setQuantita(Avanti.articoli.get(i).getQuantita()+temp);
			}
		}
		for (int i=0; i<Avanti.carrelloTemp.size(); i++){
			totale=totale+(Avanti.carrelloTemp.get(i).getProdotto().getPrezzo()*Avanti.carrelloTemp.get(i).getQuantita());
			}
		if(Avanti.carrelloTemp.size()==0)
		{
		HttpSession sessione = request.getSession();
		sessione.setAttribute("cart", "vuoto");
		}
		return SUCCESS;
	}
	//torna alla lista prodotti	
	public String indietro(){
		return SUCCESS;
	}

	//controlla se il prodotto è già presente nel carrello
	public int controlla(Prodotto prodotto){
		boolean flag=false;
		int index=-1;
		for (int i=0; i<Avanti.carrelloTemp.size()&&!flag; i++){
			if(Avanti.carrelloTemp.get(i).getProdotto().getIdProdotto()==prodotto.getIdProdotto()){
				flag=true;
				index=i;
			}
		}
			
		return index;
	}
	
	//cerca il "MAGAZZINOIN" corrispondente al codice
	public int ricerca(int cod){
		int index=0;
		boolean flag=true;
		for(int i=0; i<Avanti.articoli.size() && flag; i++){
			if(Avanti.articoli.get(i).getIdIn()==(cod)){
				index=i;
				flag=false;
			}
		}
		return index;
	}
	private ServletContext ctx;

	public void setServletContext(ServletContext sc) {
		this.ctx = sc;
	}
	public ArrayList<MagazzinoIn> getCarrelloTemp() {
		return Avanti.carrelloTemp;
	}

	public void setCarrelloTemp(ArrayList<MagazzinoIn> carrelloTemp) {
		Avanti.carrelloTemp = carrelloTemp;
	}
	public ArrayList<MagazzinoIn> getArticoli() {
		return Avanti.articoli;
	}

	public void setArticoli(ArrayList<MagazzinoIn> articoli) {
		Avanti.articoli = articoli;
	}
	
	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getFlag() {
		return Avanti.flag;
	}

	public void setFlag(String flag) {
		Avanti.flag = flag;
	}

	public int getQta() {
		return qta;
	}

	public void setQta(int qta) {
		this.qta = qta;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getQtaerr() {
		return qtaerr;
	}

	public void setQtaerr(String qtaerr) {
		this.qtaerr = qtaerr;
	}

	public int getIdMag() {
		return idMag;
	}

	public void setIdMag(int idMag) {
		this.idMag = idMag;
	}

	public float getTotale() {
		return totale;
	}

	public void setTotale(float totale) {
		this.totale = totale;
	}

}
