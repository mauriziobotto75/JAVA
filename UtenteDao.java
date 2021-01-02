package dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dati.*;

public class UtenteDao {
	
	private SessionFactory sf;
    
    public UtenteDao(SessionFactory sf){
        this.sf = sf;
    }
	
	public void createUtente(String nome, String cognome, String username, String password){

		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Utente nuovo = new Utente();
			nuovo.setNome(nome);
			nuovo.setCognome(cognome);
			nuovo.setUser(username);
			nuovo.setPass(password);
			nuovo.setStato("utente");
			session.save(nuovo);
			session.getTransaction().commit();
			}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
	}		 
	
		public void createAdmin(String nome, String cognome, String username, String password){
		
		//controlli su nome cognome e numero
		//
		//
		//
		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Utente nuovo = new Utente();
			nuovo.setNome(nome);
			nuovo.setCognome(cognome);
			nuovo.setUser(username);
			nuovo.setPass(password);
			nuovo.setStato("admin");
			session.save(nuovo);
			session.getTransaction().commit();
			}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
	}
		/***	***		***		***/
	public void updateUtente(int id, String stato){
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		String hql = "UPDATE Utente set stato = :stato "  + 
	             "WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("stato", stato);
		query.setParameter("id", id);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
	}
	
	public void updateUtenteAll(int id, String nome, String cognome,
		String username, String pass, String email, String stato){
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		Utente utente= (Utente)session.get(Utente.class, id);
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setUser(username);
		utente.setPass(pass);
		utente.setEmail(email);
		utente.setStato(stato);
		session.update(utente); 
        tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
	}
	
	public List<Utente> getAllUtente(){
		List<Utente> users= new ArrayList<Utente>();
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		List<?> utenti = session.createQuery("from Utente").list();
		
		for (Iterator<?> iterator =
		utenti.iterator(); iterator.hasNext();){
			System.out.println(iterator.next().toString());
			Utente corrente = (Utente) iterator.next();
			users.add(corrente);
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) 
			tx.rollback();
		e.printStackTrace();
		}
		
		return users;
	}
	public Utente getUtenteById(int id){
		Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Utente user= (Utente) session.get(Utente.class, id);
//        if(user != null){
//            System.out.println("Utente da DB::"+user);
//        }
        tx.commit();
        session.close();
        return user;
	}
	public Utente getUtenteByCredentials(String user, String pass){
		boolean flag=false;
		Utente utente= new Utente();
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		List users = session.createQuery("from Utente").list();
		for (Iterator iterator =
		users.iterator(); iterator.hasNext()&&!flag;){
			Utente corrente = (Utente) iterator.next();
			if(corrente.getUser().equals(user)&&corrente.getPass().equals(pass))
			{
			flag = true;
			utente=corrente;
			}
			//arrayList da creare o già presente???
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return utente;
	}
	/***	***		***		***/
	public void deleteUtente(int id){
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "delete from Utente where id= :id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			System.out.println(query.executeUpdate());
		  
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
		}

	public boolean controlla(String nome, String cognome){
		boolean flag=true;
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
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return flag;
		}
	public boolean controllaUser( String user){
		boolean flag=true;

		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		List users = session.createQuery("from Utente").list();
		for (Iterator iterator =
		users.iterator(); iterator.hasNext()&&flag;){
			Utente corrente = (Utente) iterator.next();
			if(corrente.getUser().equals(user))
			{flag = false;}
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return flag;
		}

	public boolean ricerca(String user, String pass) {
		boolean flag=false;

		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		List users = session.createQuery("from Utente").list();
		for (Iterator iterator =
		users.iterator(); iterator.hasNext()&&!flag;){
			Utente corrente = (Utente) iterator.next();
			if(corrente.getUser().equals(user)&&corrente.getPass().equals(pass))
			{flag = true;}
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return flag;
	}
}
