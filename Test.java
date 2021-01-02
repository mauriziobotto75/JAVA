package test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import actions.Avanti;
import dao.*;
import dati.*;
import hibernate.*;
public class Test {
	public static void main(String[] args) {
//		ArrayList<Prodotto> prodotti = new ArrayList <Prodotto>();
//		ArrayList<MagazzinoIn> articoli = new ArrayList <MagazzinoIn>();
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session = HibernateUtil.getSessionFactory().openSession();
//		
//		MagazzinoInDao neu = new MagazzinoInDao(sf);
//		prodotti=neu.getNotMagazzinoIn();
//		for (int i=0; i< prodotti.size(); i++){
//			System.out.println("qualcosa");
//			System.out.println(prodotti.get(i).getNomeProdotto());
//		}
//		
//		articoli=neu.like("nome");
//		for (int i=0; i< articoli.size(); i++){
//			System.out.println("altro");
//			System.out.println(articoli.get(i).getProdotto().getNomeProdotto());
//		}
	
	
//	Set<MagazzinoOut> carrello= new HashSet<MagazzinoOut>();
//	Transaction tx = null;	
//	try{
//		tx = session.beginTransaction();
//		Utente utente= (Utente) session.get(Utente.class, 1);
//		Ordine ordine= new Ordine();
//		ProdottoDao prodotto= new ProdottoDao(sf);
//		MagazzinoOut out1= new MagazzinoOut();
//		MagazzinoOut out2= new MagazzinoOut();
//		out1.setProdotto(prodotto.getProdottoById(1));
//		out1.setQuantita(10);
//		out2.setProdotto(prodotto.getProdottoById(2));
//		out2.setQuantita(20);
//		ordine.setUtente(utente);
//		ordine.setAcquisto(carrello);
//		session.save(ordine);
//		session.save(out1);
//		session.save(out2);
//		System.out.println("prova");
//		session.getTransaction().commit();
//		}catch (HibernateException e) {
//			if (tx!=null) 
//				tx.rollback();
//			e.printStackTrace();
//	}
	

	}
}
