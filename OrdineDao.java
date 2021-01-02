package dao;

//import java.util.ArrayList;
import java.util.Date;
//import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dati.*;

public class OrdineDao {

	private SessionFactory sf;
    
    public OrdineDao(SessionFactory sf){
        this.sf = sf;
    }
    
	public void createOrdine(int idUtente, Set<MagazzinoOut> carrello){
		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Utente utente= (Utente) session.get(Utente.class, idUtente);
//			Set<MagazzinoOut> articoli= new HashSet<MagazzinoOut>();
			Ordine ordine= new Ordine();
			ordine.setUtente(utente);
			ordine.setAcquisto(carrello);
			Date data= new Date();
			ordine.setData(data);
			ordine.setStato("ORDINE");
			System.out.println("ordine");
			session.save(ordine);
			session.getTransaction().commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
		}
	} 
	
	public void saveOrdine(int idUtente, Set<MagazzinoOut> carrello){
		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Utente utente= (Utente) session.get(Utente.class, idUtente);
//			Set<MagazzinoOut> articoli= new HashSet<MagazzinoOut>();
			Ordine ordine= new Ordine();
			ordine.setUtente(utente);
			ordine.setAcquisto(carrello);
			Date data= new Date();
			ordine.setData(data);
			ordine.setStato("CARRELLO");
			System.out.println("carrello salvato");
			session.save(ordine);
			session.getTransaction().commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
		}
	} 
	
	public void updateOrdine(int idOrdine){
		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Ordine ordine= (Ordine) session.get(Ordine.class, idOrdine);

			Date data= new Date();
			ordine.setData(data);
			ordine.setStato("ORDINE");
			session.update(ordine);
			session.getTransaction().commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
		}
	}
	public List<Ordine> getAllOrdine(){
		return null;
	}
	public Ordine getOrdineById(){
		return null;
	}
	public void deleteOrdine(int idOrdine){
		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Ordine ordine= (Ordine) session.get(Ordine.class, idOrdine);
			session.delete(ordine);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
	}
	}
}
