package actions;

import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;





import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
//import org.apache.commons.io.FileUtils;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.ActionSupport;

import dao.*;
import dati.*;
//import util.*;

public class Admin extends ActionSupport implements ServletContextAware,ServletRequestAware {

	private static final long serialVersionUID = 1L;

	public String flag1="";
	public String flag2="";
	private int cod;
	private int id;
	private int qty;
	private String nomeProd;
	private String descrizione;
	private String immaginePath;
	private File image;
	private float prezzo;
	private File immagine;
    private String immagineContentType;
    private String immagineFileName;
    private String info;
    private int pagina;

    
	HttpServletRequest request = ServletActionContext.getRequest();

    private HttpServletRequest servletRequest;
	
    public ArrayList<Prodotto>prodotti=new ArrayList<Prodotto>();
	public ArrayList<MagazzinoIn>articoli=new ArrayList <MagazzinoIn>();
	public ArrayList <Prodotto> magazzino = new ArrayList<Prodotto>();  
	public ArrayList<Integer> number= new ArrayList<Integer>();

	public String execute(){
		return SUCCESS;
	}
	/** restutuisce una lista delle transazioni effettuate dall'amministratore **/
	public String lista(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		HttpSession sessione=request.getSession(false);
        int codice= (int)sessione.getAttribute("codice");
        MagazzinoOutDao mag= new MagazzinoOutDao(sf);
        //elenco pagine
        number=mag.getNumberMagazzinoOutUser(codice);
		Login.txnAdmin=mag.getUserMagazzino(codice,1);
		setFlag1("lista");
		return SUCCESS;
	}
	/** restituisce una lista di tutte le transazioni effettuate sul sit
     * @return o**/
	public String listaTransazioni(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoOutDao mag= new MagazzinoOutDao(sf);
		//elenco Pagine
		number=mag.getNumberMagazzinoOut();
		Login.transazioni=mag.getAllMagazzinoOut(1);
		setFlag1("alltxn");
		return SUCCESS;
	}
	
	//per navigare tra le pagine delle transazioni dell'amministratore
	public String goToAdminPage(){
//		System.out.println(pagina);
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		HttpSession sessione=request.getSession(false);
        int codice= (int)sessione.getAttribute("codice");
		MagazzinoOutDao mag=new MagazzinoOutDao(sf);
		Login.txnAdmin=mag.getUserMagazzino(codice,pagina);

		number=mag.getNumberMagazzinoOutUser(codice);
		setFlag1("lista");
	return SUCCESS;	
	}
	
	//per navigare tra le pagine delle transazioni
	public String goToTxnPage(){
//		System.out.println(pagina);
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoOutDao mag=new MagazzinoOutDao(sf);
		Login.transazioni=mag.getAllMagazzinoOut(pagina);

		number=mag.getNumberMagazzinoOut();
		setFlag1("alltxn");
	return SUCCESS;	
	}
	
	/** setta il flag per poter visualizzare il form di creazione di nuovo prodotto**/
	public String nuovoProdotto(){
		setFlag1("nuovoProdotto");
		return SUCCESS;
	}
	/** recupera la lista dei prodotti e setta il flag per poter modificare i prodotti **/
	public String modificaProdotto(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		ProdottoDao prodottoDao=new ProdottoDao(sf);
		number=prodottoDao.getNumberProdotto();
		try{
		prodotti=prodottoDao.getAllProdotto(pagina);
		} catch(NullPointerException e){
			prodotti=prodottoDao.getAllProdotto(1);
		}
		//taglia la descrizione a 50 caratteri
		for (int i =0; i<prodotti.size(); i++){
			if(prodotti.get(i).getDescrizione().length() > 50)
				prodotti.get(i).setDescrizione(prodotti.get(i).getDescrizione().substring(0,50) + "...");
			}
		setFlag1("modificaProdotto");
		return SUCCESS;
	}
	
	
	/** recupera la lista di prodotti e setta il flag per poter aggiungere gli articoli in magazzino**/
	public String aggiungiArticolo(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao magazzinodao= new MagazzinoInDao(sf);
		setFlag1("aggiungiArticolo");
		try{
		magazzino=magazzinodao.getNotMagazzinoIn(pagina);
		}catch(NullPointerException e){
			magazzino=magazzinodao.getNotMagazzinoIn(1);
		}
		number=magazzinodao.getNumberNotMagazzinoIn();
		//taglia la descrizione a 50 caratteri
		for (int i =0; i<magazzino.size(); i++){
			if(magazzino.get(i).getDescrizione().length() > 50)
				magazzino.get(i).setDescrizione(magazzino.get(i).getDescrizione().substring(0,50) + "...");
			}
		return SUCCESS;
	}
	/** permette di aggiungere quantit� al magazzino**/
	public String modificaArticolo(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao magazzinodao= new MagazzinoInDao(sf);
		setFlag1("modificaArticolo");
		try{
		articoli=magazzinodao.getAllMagazzinoInLim(pagina);
		}catch(NullPointerException e){
			articoli=magazzinodao.getAllMagazzinoInLim(1);
		}
		number=magazzinodao.getNumberAllMagazzino();
		//taglia la descrizione a 50 caratteri
		for (int i =0; i<articoli.size(); i++){
			if(articoli.get(i).getProdotto().getDescrizione().length() > 50)
			    articoli.get(i).getProdotto().setDescrizione(articoli.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
			}
		return SUCCESS;
	}
	/** aggiunge un nuovo prodotto**/
	public String aggiungi(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		ProdottoDao prodottoDao=new ProdottoDao(sf);
		try {
		String nome=nomeProd.replace(" ", "");
		immagineFileName=nome+".jpeg";
        String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
        System.out.println("Server path:" + filePath);
        filePath=filePath+"img\\";
        File fileToCreate = new File(filePath, this.immagineFileName);

        FileUtils.copyFile(this.immagine, fileToCreate);
        immaginePath=filePath+immagineFileName;
    } catch (Exception e) {
        e.printStackTrace();
        addActionError(e.getMessage());
        addFieldError("immagine","controlla l'immagine!");
        return ERROR;
    }
		prodottoDao.createProdotto(nomeProd, descrizione, immagineFileName, prezzo);
		nuovoProdotto();
		nomeProd="";
		descrizione="";
		prezzo=0;
		return SUCCESS;
	}
	/** permette di scegliere un prodotto dalla lista prodotti**/
	public String scegliProdotto(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		ProdottoDao prodottoDao=new ProdottoDao(sf);
		number=prodottoDao.getNumberProdotto();
		try{
		prodotti=prodottoDao.getAllProdotto(pagina);
		} catch(NullPointerException e){
			prodotti=prodottoDao.getAllProdotto(1);
		}
		System.out.println(cod);
		setFlag1("modificaProdotto");
		setFlag2("on");
		Prodotto prodotto=prodottoDao.getProdottoById(cod);
		nomeProd=prodotto.getNomeProdotto();
		prezzo=prodotto.getPrezzo();
		descrizione=prodotto.getDescrizione();
		for (int i =0; i<prodotti.size(); i++){//resize descrizione
			if(prodotti.get(i).getDescrizione().length() > 50)
				prodotti.get(i).setDescrizione(prodotti.get(i).getDescrizione().substring(0,50) + "...");
			}
		return SUCCESS;
	}
	
	public String modifica(){
		//modifico un prodotto della tabella PRODOTTO
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		ProdottoDao prodottoDao=new ProdottoDao(sf);
		try {
//			String nome=nomeProd.replace(" ", "");
//			immagineFileName=nome+".jpeg";
			immagineFileName=immagineFileName+".jpeg";
	        String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
//	        System.out.println("Server path:" + filePath);
	        filePath=filePath+"img\\";
	        File fileToCreate = new File(filePath, this.immagineFileName);

	        FileUtils.copyFile(this.immagine, fileToCreate);
	        immaginePath=filePath+immagineFileName;
//	        System.out.println(immagineFileName);
	    } catch (Exception e) {
	        e.printStackTrace();
	        addActionError(e.getMessage());
	        addFieldError("immagine","controlla l'immagine!");
	        return ERROR;
	    }
		if(prezzo<0){
			addFieldError("prezzo","il prezzo non pu� essere negativo");
			return ERROR;
			}
		else{
		prodottoDao.updateProdotto(cod, nomeProd, descrizione, immagineFileName, prezzo);
		modificaProdotto();
		return SUCCESS;
		}
	}
	public String addMagazzino(){
		//aggiungo prodotto in magazzino (senza quantit�)
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao magazzinoDao=new MagazzinoInDao(sf);
		magazzinoDao.createMagazzinoIn(cod,0);
		aggiungiArticolo();
		return SUCCESS;
	}
	public String addQuantita(){
		//aggiungo una determinata quantit� di articoli in magazzino
		if(qty<=0)
		{
			addFieldError("quantita","la quantit� non pu� essere negativa");
			return ERROR;	
		}
		else{
		Set<MagazzinoOut> carrello= new HashSet<MagazzinoOut>();
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao magazzinoDao=new MagazzinoInDao(sf);
		MagazzinoIn magazzino=magazzinoDao.getMagazzinoInById(id);
		MagazzinoOutDao transazione= new MagazzinoOutDao(sf);
		OrdineDao ordine= new OrdineDao(sf);
		HttpSession sessione=request.getSession(false);
		int codice= (int)sessione.getAttribute("codice"); //codice utente
		magazzinoDao.updateMagazzinoIn(id,magazzino.getQuantita()+qty);
		int txn=transazione.createMagazzinoOut(cod, -qty);
		MagazzinoOut temp=transazione.getMagazzinoOutById(txn);
		carrello.add(temp);
		ordine.createOrdine(codice, carrello);
		modificaArticolo();
		return SUCCESS;
		}
	}
	
	public String scegliMag(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao articolo=new MagazzinoInDao(sf);
//		prodotti=prodottoDao.getAllProdotto();
		System.out.println(id);
		setFlag1("modificaArticolo");
		setFlag2("on");
		MagazzinoIn magazzino=articolo.getMagazzinoInById(id);

		nomeProd=magazzino.getProdotto().getNomeProdotto();
		
		articoli=articolo.getAllMagazzinoIn();
		//taglia la descrizione a 50 caratteri
		for (int i =0; i<articoli.size(); i++){
			if(articoli.get(i).getProdotto().getDescrizione().length() > 50)
			    articoli.get(i).getProdotto().setDescrizione(articoli.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
			}
		return SUCCESS;
	}
	/** rimuove un articolo dal magazzino**/
	public String rimuoviItem(){
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		MagazzinoInDao magazzinoDao=new MagazzinoInDao(sf);
		System.out.println(id);
		magazzinoDao.deleteMagazzinoIn(id);
		modificaArticolo();
		return SUCCESS;
	}
	
	public String goToAdmin(){
		setInfo("on");
		return SUCCESS;}

	
	private ServletContext ctx;

	public void setServletContext(ServletContext sc) {
		this.ctx = sc;
	}
	/** GETTERS AND SETTERS **/
	public String getFlag1() {
		return flag1;
	}
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	public ArrayList<MagazzinoIn> getArticoli() {
		return articoli;
	}

	public void setArticoli(ArrayList<MagazzinoIn> articoli) {
		this.articoli = articoli;
	}

	public ArrayList<Prodotto> getMagazzino() {
		return magazzino;
	}

	public void setMagazzino(ArrayList<Prodotto> magazzino) {
		this.magazzino = magazzino;
	}

	public String getNomeProd() {
		return nomeProd;
	}

	public void setNomeProd(String nomeProd) {
		this.nomeProd = nomeProd;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public String getImmaginePath() {
		return immaginePath;
	}

	public void setImmaginePath(String immaginePath) {
		this.immaginePath = immaginePath;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

	public File getImmagine() {
		return immagine;
	}

	public void setImmagine(File immagine) {
		this.immagine = immagine;
	}

	public String getImmagineContentType() {
		return immagineContentType;
	}

	public void setImmagineContentType(String immagineContentType) {
		this.immagineContentType = immagineContentType;
	}

	public String getImmagineFileName() {
		return immagineFileName;
	}

	public void setImmagineFileName(String immagineFileName) {
		this.immagineFileName = immagineFileName;
	}

	public ArrayList<MagazzinoOut> getTransazioni() {
		return Login.transazioni;
	}
	public void setTransazioni(ArrayList<MagazzinoOut> transazioni) {
		Login.transazioni = transazioni;
	}
	public ArrayList<MagazzinoOut> getTxnAdmin() {
		return Login.txnAdmin;
	}
	public void setTxnAdmin(ArrayList<MagazzinoOut> txnAdmin) {
		Login.txnAdmin = txnAdmin;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public ArrayList<Integer> getNumber() {
		return number;
	}
	public void setNumber(ArrayList<Integer> number) {
		this.number = number;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}


}
