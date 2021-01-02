package hibernate ;

import dati.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import actions.Avanti;
import dvdrental.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
	
	public class HibernateGestione {
//
		private SessionFactory sf;
		public HibernateGestione(SessionFactory sf){
			this.sf = sf;
		}
		public HibernateGestione(){
			
		}
		

	/* Method to CREATE an employee in the database */
	public void addUtente(String nome, String cognome, String username, String password){
	//controlli su nome cognome e numero
		//
		//
		//
	Session session = HibernateUtil.getSessionFactory().openSession();
	Session session = sf.openSession();
	Transaction tx = null;
//	
	try{
	tx = session.beginTransaction();
	Utente nuovo = new Utente();
	nuovo.setNome(nome);
	nuovo.setCognome(cognome);
	nuovo.setUsername(username);
	nuovo.setPassword(password);
	nuovo.setStato("utente");
	session.save(nuovo);
	session.getTransaction().commit();
	}catch (HibernateException e) {
	if (tx!=null) tx.rollback();
	e.printStackTrace();
	}
//	
	}
//		
//	/* lita globale dei prodotti */
	public void listaProdotti( ){
	Avanti.prodotti.clear();
	Session session = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("LocalVariableHidesMemberVariable")
	Session session = sf.openSession();
	Transaction tx = null;
	try{
	tx = session.beginTransaction();
	List<?> prodotti = session.createQuery("from Prodotto").list();
//	
	for (Iterator<?> iterator =
	prodotti.iterator(); iterator.hasNext();){
		System.out.println(iterator.next().toString());
		Prodotto corrente = (Prodotto) iterator.next();
		Avanti.prodotti.add(corrente);
	}
	tx.commit();
	}catch (HibernateException e) {
	if (tx!=null) tx.rollback();
	e.printStackTrace();
	}
	
	}
//	/*verifica le credenziali di accesso*/
	public boolean ricerca( String user, String password){
		boolean flag=false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		List users = session.createQuery("from Utente").list();
		for (Iterator iterator =
		users.iterator(); iterator.hasNext()&&!flag;){
		Utente corrente = (Utente) iterator.next();
			if(corrente.getUsername().equals(user)&&corrente.getPassword().equals(password))
			{flag = true;}
			//arrayList da creare o gi� presente???
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return flag;
		}
//	
	/*Controlla che non ci si sia gi� registrati*/
	public boolean controlla( String nome, String cognome){
		boolean flag=true;
 		Session session = HibernateUtil.getSessionFactory().openSession();
 		Session session = sf.openSession();
 		Transaction tx = null;
 	try{
 		tx = session.beginTransaction();
 		List users = session.createQuery("from Utente").list();
 		for (Iterator iterator =
 		users.iterator(); iterator.hasNext()&&flag;){
 		Utente corrente = (Utente) iterator.next();
 		if(corrente.getNome().equals(nome)&&corrente.getCognome().equals(cognome))
 			{flag = false;}
 		//arrayList da creare o gi� presente???
 		}
 	tx.commit();
 		}catch (HibernateException e) {
 	if (tx!=null) tx.rollback();
 		e.printStackTrace();
 		}
 		return flag;
 		}
//	/*Controlla che non ci siano utenti con username uguali*/
 		Session session = sf.openSession();
 		Transaction tx = null;
 		try{
 		tx = session.beginTransaction();
 		List users = session.createQuery("from Utente").list();
 		for (Iterator iterator =
 		users.iterator(); iterator.hasNext()&&flag;){
 		Utente corrente = (Utente) iterator.next();
 			if(corrente.getUsername().equals(user))
 			{flag = false;}
 			//arrayList da creare o gi� presente???
 	}
 		tx.commit();
 		}catch (HibernateException e) {
 		if (tx!=null) tx.rollback();
 		e.printStackTrace();
 		}
 		return flag;
 		}
 public Utente corrUtente( String user, String password){
 		boolean flag=false;
 	Utente utente=null;
 		Session session = HibernateUtil.getSessionFactory().openSession();
 	Session session = sf.openSession();
 	Transaction tx = null;
 	try{
 	tx = session.beginTransaction();
 	List users = session.createQuery("from Utente").list();
 		for (Iterator iterator =
 	users.iterator(); iterator.hasNext()&&!flag;){
 		Utente corrente = (Utente) iterator.next();
 			if(corrente.getUsername().equals(user)&&corrente.getPassword().equals(password))
 		{	utente=corrente;
 			flag = true;}
 			//arrayList da creare o gi� presente???
 		}
 	tx.commit();
 	}catch (HibernateException e) {
 	if (tx!=null) tx.rollback();
 		e.printStackTrace();
 	}
 		return utente;
 		}
 		public void like(String cerca){
 		Avanti.prodotti.clear();
 		Session session = HibernateUtil.getSessionFactory().openSession();
 	Session session = sf.openSession();
 	Transaction tx = null;
 	try{
 		tx = session.beginTransaction();
 		List<?> prodotti = session.createQuery("from Prodotto where nomeProdotto like '" + cerca + "' order by nomeProdotto").list();
 			
 	for (Iterator<?> iterator =
 	prodotti.iterator(); iterator.hasNext();){
 			System.out.println(iterator.next().toString());
 			Prodotto corrente = (Prodotto) iterator.next();
 		Avanti.prodotti.add(corrente);
 		}
 		tx.commit();
 	}catch (HibernateException e) {
 		if (tx!=null) tx.rollback();
 		e.printStackTrace();
 		}
 	}
 	public void aggProdotto(int codice){
 		boolean flag=true;
 		Session session = sf.openSession();
 		Transaction tx = null;
 		try{
 			tx = session.beginTransaction();
 		List<?> prodotti = session.createQuery("from Prodotto").list();
 		
 			for (Iterator<?> iterator =
 		prodotti.iterator(); iterator.hasNext()&&flag;){
 			Prodotto corrente = (Prodotto) iterator.next();
 			if(corrente.getCodice()==codice)
 			{Avanti.carrelloTemp.add(corrente);
 			flag=false;
 			}
 		}
 		tx.commit();
 		}catch (HibernateException e) {
 		if (tx!=null) tx.rollback();
 		e.printStackTrace();
 		}
 	}
}
