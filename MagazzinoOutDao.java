package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dati.*;


public class MagazzinoOutDao {
	
		private SessionFactory sf;
    
		public MagazzinoOutDao(SessionFactory sf){
			this.sf = sf;
		}
    
		public int createMagazzinoOut(int idProdotto, int quantita){
			Session session = sf.openSession();
			Transaction tx = null;
			int idTransazione=0;
			try{
				tx = session.beginTransaction();
			Prodotto p1= (Prodotto) session.get(Prodotto.class, idProdotto);
			MagazzinoOut out1= new MagazzinoOut();
			out1.setProdotto(p1);
			out1.setQuantita(quantita);
			Date data=new Date();
			out1.setData(data);
//			System.out.println("\n");
//			System.out.println(out1.getIdOut());
			session.save(out1);
			session.getTransaction().commit();
//			System.out.println("\n");
//			System.out.println(out1.getIdOut());
			idTransazione=out1.getIdOut();
			}catch (HibernateException e) {
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
			}
			return idTransazione;
		} 
		public void updateMagazzinoOut(int id, int idProdotto, int quantita){
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			MagazzinoOut articolo= (MagazzinoOut)session.get(MagazzinoOut.class, id);
			ProdottoDao prodottodao= new ProdottoDao(sf);
			Prodotto prodotto= prodottodao.getProdottoById(idProdotto);
			articolo.setQuantita(quantita);
			articolo.setProdotto(prodotto);
			
			session.update(articolo); 
	        tx.commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
				}
		}
		//overload
		public void updateMagazzinoOut(int id){
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			MagazzinoOut articolo= (MagazzinoOut)session.get(MagazzinoOut.class, id);
			Date data=new Date();
			articolo.setData(data);
			session.update(articolo); 
	        tx.commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
				}
		}
		public ArrayList<MagazzinoOut> getAllMagazzinoOut(int page){
			ArrayList<MagazzinoOut> txn= new ArrayList<MagazzinoOut>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			Query query=session.createQuery("from MagazzinoOut");
			query.setFirstResult(15*(page-1));
			query.setMaxResults(15);
			
			List<?> lista = query.list();

			for (Iterator<?> iterator =
					lista.iterator(); iterator.hasNext();){
					MagazzinoOut corrente = (MagazzinoOut)iterator.next();
//					if(corrente.getOrdine().getUtente())
					
					txn.add(corrente);
				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return txn;
		}
		public MagazzinoOut getMagazzinoOutById(int id){
			Session session = sf.openSession();
	        Transaction tx = session.beginTransaction();
	        MagazzinoOut articolo= (MagazzinoOut) session.get(MagazzinoOut.class, id);
//	        if(prodotto != null){
//	            System.out.println("Utente da DB::"+prodotto);
//	        }
	        tx.commit();
	        session.close();
	        return articolo;
		}
		public void deleteMagazzinoOut(int idMagazzino){
			Session session = sf.openSession();
			Transaction tx = null;	
			try{
				tx = session.beginTransaction();
				MagazzinoOut articolo= (MagazzinoOut) session.get(MagazzinoOut.class, idMagazzino);
				session.delete(articolo);
				tx.commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
		}
			
		}

		public ArrayList<MagazzinoOut> getUserMagazzinoOld(int id) {
			ArrayList<MagazzinoOut> txn= new ArrayList<MagazzinoOut>();
//			UtenteDao utente= new UtenteDao(sf);
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			List<?> lista = session.createQuery("from MagazzinoOut").list();
			for (Iterator<?> iterator =
					lista.iterator(); iterator.hasNext();){
					MagazzinoOut corrente = (MagazzinoOut)iterator.next();
					try{
					if(corrente.getOrdine().getUtente().getId()==(id)){
						if(corrente.getOrdine().getStato().equals("ORDINE")){
							txn.add(corrente);}}
					}catch(NullPointerException e){
					System.out.println("utente nullo, verifica!!!");
					e.printStackTrace();
					}
					
				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return txn;

		}
		
		public ArrayList<MagazzinoOut> getUserMagazzino(int id, int page) {
			ArrayList<MagazzinoOut> txn= new ArrayList<MagazzinoOut>();
//			UtenteDao utente= new UtenteDao(sf);
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			Query query=session.createQuery("from MagazzinoOut where ordine.stato='ORDINE' and ordine.utente.id="+id);
			query.setFirstResult(15*(page-1));
			query.setMaxResults(15);
			
			List<?> lista = query.list();
			for (Iterator<?> iterator =
					lista.iterator(); iterator.hasNext();){
					MagazzinoOut corrente = (MagazzinoOut)iterator.next();
//					try{
//					if(corrente.getOrdine().getUtente().getId()==(id)){
//						if(corrente.getOrdine().getStato().equals("ORDINE")){
							txn.add(corrente);
//							}
//			}
//					}catch(NullPointerException e){
//					System.out.println("utente nullo, verifica!!!");
//					e.printStackTrace();
//					}
//					
				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return txn;

		}

		public ArrayList<MagazzinoOut> getUserCarrelloSalvato(int id) {
			ArrayList<MagazzinoOut> txn= new ArrayList<MagazzinoOut>();
//			UtenteDao utente= new UtenteDao(sf);
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			List<?> lista = session.createQuery("from MagazzinoOut where ordine.stato='CARRELLO' and ordine.utente.id="+id).list();
			for (Iterator<?> iterator =
					lista.iterator(); iterator.hasNext();){
					MagazzinoOut corrente = (MagazzinoOut)iterator.next();
//					try{
//					if(corrente.getOrdine().getUtente().getId()==(id)){
//						if(corrente.getOrdine().getStato().equals("CARRELLO")){
							txn.add(corrente);}
//			}
//					}catch(NullPointerException e){
//					System.out.println("utente nullo, verifica!!!");
//					e.printStackTrace();
//					}
					
//				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return txn;

		}
		
		public ArrayList<MagazzinoOut> getUserCarrelloSalvatoOld(int id) {
			ArrayList<MagazzinoOut> txn= new ArrayList<MagazzinoOut>();
//			UtenteDao utente= new UtenteDao(sf);
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			List<?> lista = session.createQuery("from MagazzinoOut").list();
			for (Iterator<?> iterator =
					lista.iterator(); iterator.hasNext();){
					MagazzinoOut corrente = (MagazzinoOut)iterator.next();
					try{
					if(corrente.getOrdine().getUtente().getId()==(id)){
						if(corrente.getOrdine().getStato().equals("CARRELLO")){
							txn.add(corrente);}}
					}catch(NullPointerException e){
					System.out.println("utente nullo, verifica!!!");
					e.printStackTrace();
					}
					
				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return txn;

		}
		
		public ArrayList<Integer> getNumberMagazzinoOut(){
			ArrayList<Integer> number= new ArrayList<Integer>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			long size = (long)session.createQuery("select count(*) from MagazzinoOut").uniqueResult();
			float siz= (float)size;
			
			for (int i=1; i<=(Math.ceil(siz/15)); i++){
				number.add(i);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return number;
			}
		public ArrayList<Integer> getNumberMagazzinoOutUser(int id){
			ArrayList<Integer> number= new ArrayList<Integer>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			long size = (long)session.createQuery
					("select count(*) from MagazzinoOut where ordine.stato='ORDINE' and ordine.utente.id="+id).uniqueResult();
			float siz= (float)size;
			System.out.println(siz);
			for (int i=1; i<=(Math.ceil(siz/15)); i++){
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
