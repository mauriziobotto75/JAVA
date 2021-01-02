package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dati.*;;

public class ProdottoDao {
	
	private SessionFactory sf;
    
    public ProdottoDao(SessionFactory sf){
        this.sf = sf;
    }
	
	public void createProdotto(String nome, String descrizione, String immagine, float prezzo){
		Session session = sf.openSession();
		Transaction tx = null;	
		try{
			tx = session.beginTransaction();
			Prodotto nuovo = new Prodotto();
			nuovo.setNomeProdotto(nome);
			nuovo.setDescrizione(descrizione);
			/***/nuovo.setImmagine(immagine);/***/
			nuovo.setPrezzo(prezzo);
			
			session.save(nuovo);
			session.getTransaction().commit();
			}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
		
	} 
	public void updateProdotto(int id, String nome, String descrizione, String immagine, float prezzo){
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		Prodotto prodotto= (Prodotto)session.get(Prodotto.class, id);
		prodotto.setNomeProdotto(nome);
		prodotto.setDescrizione(descrizione);
		prodotto.setImmagine(immagine);
		prodotto.setPrezzo(prezzo);
		session.update(prodotto); 
        tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
	
	}
	public ArrayList<Prodotto> getAllProdotto(int page){
		ArrayList<Prodotto> prodotti= new ArrayList<Prodotto>();
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		Query query=session.createQuery("from Prodotto");
		query.setFirstResult(5*(page-1));
		query.setMaxResults(5);
//		List<?> products = session.createQuery("from Prodotto").list();
		List<?> products = query.list();
		
		for (Iterator<?> iterator =
		products.iterator(); iterator.hasNext();){
			Prodotto corrente = (Prodotto) iterator.next();
			prodotti.add(corrente);
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return prodotti;
	}
	public Prodotto getProdottoById(int id){
		Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Prodotto prodotto= (Prodotto) session.get(Prodotto.class, id);

        tx.commit();
        session.close();
        return prodotto;
	}
	public void deleteProdotto(int id){
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Prodotto prodotto= (Prodotto) session.get(Prodotto.class, id);
			session.delete(prodotto);
		  
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
		}
	public ArrayList<Prodotto> like(String cerca){
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
//		Session session = HibernateUtil.getSessionFactory().openSession();
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		List<?> products = session.createQuery("from Prodotto where nomeProdotto like '" + cerca + "' order by nomeProdotto").list();
		for (Iterator<?> iterator =
		products.iterator(); iterator.hasNext();){
//			System.out.println(iterator.next().toString());
			Prodotto corrente = (Prodotto) iterator.next();
			prodotti.add(corrente);
			}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return prodotti;
	}
	
	public ArrayList<Integer> getNumberProdotto(){
		ArrayList<Integer> number= new ArrayList<Integer>();
		Session session = sf.openSession();
		Transaction tx = null;
		try{
		tx = session.beginTransaction();
		long size = (long)session.createQuery("select count(*) from Prodotto").uniqueResult();
		float siz= (float)size;
		
		for (int i=1; i<=(Math.ceil(siz/5)); i++){
			number.add(i);
		}
		tx.commit();
		}catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();
		}
		return number;
	}
}
