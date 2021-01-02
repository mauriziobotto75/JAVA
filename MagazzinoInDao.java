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

public class MagazzinoInDao {

		private SessionFactory sf;
    
		public MagazzinoInDao(SessionFactory sf){
			this.sf = sf;
		}
	//creare nuovo oggetto MagazzinoIn ... forse non serve
		public void createMagazzinoIn(int idProdotto, int quantita){
			Session session = sf.openSession();
			Transaction tx = null;	
			try{
				tx = session.beginTransaction();
				MagazzinoIn nuovo = new MagazzinoIn();
				nuovo.setQuantita(quantita);
				
				ProdottoDao prodottodao= new ProdottoDao(sf);
				Prodotto prodotto=prodottodao.getProdottoById(idProdotto);
				nuovo.setProdotto(prodotto);
				
				session.save(nuovo);
				session.getTransaction().commit();
				}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
				}
		} 
		//aggiornare un articolo presente in magazzino, 
		//principalmente permette di modificare la quantità
		public void updateMagazzinoIn(int id, int quantita){
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			MagazzinoIn articolo= (MagazzinoIn)session.get(MagazzinoIn.class, id);
			/**questa è la parte che mi serve*/
			articolo.setQuantita(quantita);
			/***/
			session.update(articolo); 
	        tx.commit();
			}catch (HibernateException e) {
				if (tx!=null) 
					tx.rollback();
				e.printStackTrace();
				}
		}
		//lista di tutti i prodotti presenti in magazzino
		public ArrayList<MagazzinoIn> getAllMagazzinoIn(){
			ArrayList<MagazzinoIn> magazzino= new ArrayList<MagazzinoIn>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			List<?> storage = session.createQuery("from MagazzinoIn").list();
			
			for (Iterator<?> iterator =
					storage.iterator(); iterator.hasNext();){
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				magazzino.add(corrente);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return magazzino;
		}
		//prodotti non presenti in magazzino
		//da inserire nella pagina admin per poter aggiungere nuovi prodotti
		public ArrayList<Prodotto> getNotMagazzinoIn(int page){
			ArrayList<Prodotto> magazzino= new ArrayList<Prodotto>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			Query query= session.createQuery("from Prodotto p where p.idProdotto not in("+
			" select m.prodotto.idProdotto from MagazzinoIn m)");
			query.setFirstResult(5*(page-1));
			query.setMaxResults(5);
			List<?> storage = query.list();
			for (Iterator<?> iterator =
					storage.iterator(); iterator.hasNext();){
				Prodotto corrente = (Prodotto) iterator.next();
				magazzino.add(corrente);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return magazzino;
		}
		public MagazzinoIn getMagazzinoInById(int id){
			Session session = sf.openSession();
	        Transaction tx = session.beginTransaction();
	        MagazzinoIn articolo= (MagazzinoIn) session.get(MagazzinoIn.class, id);
	        tx.commit();
//	        session.close();
	        return articolo;
		}
		
		public MagazzinoIn getMagazzinoInByIdProd(int id){
				MagazzinoIn prodotti = new MagazzinoIn();
				Session session = sf.openSession();
				Transaction tx = null;
				try{
				tx = session.beginTransaction();
				Query query=session.createQuery("from MagazzinoIn where prodotto.idProdotto = '" + id + "'");
				prodotti = (MagazzinoIn) query.uniqueResult();
				tx.commit();
				}catch (HibernateException e) {
				if (tx!=null) tx.rollback();
				e.printStackTrace();
				}
				return prodotti;
			}
		
		
		public void deleteMagazzinoIn(int id){
			Session session = sf.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				MagazzinoIn articolo= (MagazzinoIn) session.get(MagazzinoIn.class, id);
				session.delete(articolo);
				tx.commit();
			} catch (HibernateException e) {
				if (tx!=null) tx.rollback();
				e.printStackTrace();
				}
		
		}
		public ArrayList<MagazzinoIn> likeOld(String cerca){
			ArrayList<MagazzinoIn> prodotti = new ArrayList<MagazzinoIn>();
//			Session session = HibernateUtil.getSessionFactory().openSession();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			List<?> products = session.createQuery("from MagazzinoIn where prodotto.nomeProdotto like '" + cerca + "%' order by nomeProdotto").list();
			for (Iterator<?> iterator =
			products.iterator(); iterator.hasNext();){
//				System.out.println(iterator.next().toString());
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				prodotti.add(corrente);
				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return prodotti;
		}
		public ArrayList<MagazzinoIn> like(String cerca){
			ArrayList<MagazzinoIn> prodotti = new ArrayList<MagazzinoIn>();
//			Session session = HibernateUtil.getSessionFactory().openSession();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			Query q= session.createQuery("from MagazzinoIn where prodotto.nomeProdotto like '" + cerca + "%' order by nomeProdotto");
			q.setFirstResult(0);
//			System.out.println(5*(page-1));
			q.setMaxResults(5);
			List<?> products =q.list();
			for (Iterator<?> iterator =
			products.iterator(); iterator.hasNext();){
//				System.out.println(iterator.next().toString());
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				prodotti.add(corrente);
				}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return prodotti;
		}

		public ArrayList<Integer> getNumberMagazzinoIn(){
			ArrayList<Integer> number= new ArrayList<Integer>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			long size = (long)session.createQuery("select count(*) from MagazzinoIn where quantita!=0").uniqueResult();
			float siz= (float)size;
//			System.out.println(size);
//			System.out.println(Math.ceil(siz/5));
			for (int i=1; i<=(Math.ceil(siz/5)); i++){
				number.add(i);
			}
//			for (int i=0; i<number.size(); i++){
//				System.out.println(number.get(i));
//			}
//			System.out.println(size);
//			System.out.println(number.size());
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return number;
		}
		
		public ArrayList<Integer> getNumberAllMagazzino(){
			ArrayList<Integer> number= new ArrayList<Integer>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			long size = (long)session.createQuery("select count(*) from MagazzinoIn").uniqueResult();
			float siz= (float)size;
//			System.out.println(size);
//			System.out.println(Math.ceil(siz/5));
			for (int i=1; i<=(Math.ceil(siz/5)); i++){
				number.add(i);
			}
//			for (int i=0; i<number.size(); i++){
//				System.out.println(number.get(i));
//			}
//			System.out.println(size);
//			System.out.println(number.size());
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return number;
		}
		
		public ArrayList<MagazzinoIn> getMagazzinoInLimit(int page, String orderBy, String ascDesc){
			ArrayList<MagazzinoIn> magazzino= new ArrayList<MagazzinoIn>();
			Session session = sf.openSession();
			Transaction tx = null;
			String order="";
			try{
		        switch (orderBy) {
		            case "id":  order = "prodotto.idProdotto";
		                     break;
		            case "nome":  order = "prodotto.nomeProdotto";
		                     break;
		            case "quantita":  order = "quantita";
		                     break;
		            case "prezzo":  order = "prodotto.prezzo";
		                     break;
		                     default:order="prodotto.idProdotto";
		        }
		        }catch(NullPointerException e){
		        	order="prodotto.idProdotto";
		        }

			try{
			tx = session.beginTransaction();
			Query q=session.createQuery("from MagazzinoIn where quantita !=0 order by "+ order+" "+ ascDesc);
			q.setFirstResult(5*(page-1));
//			System.out.println(5*(page-1));
			q.setMaxResults(5);
			List<?> storage = q.list();
			
			for (Iterator<?> iterator =
					storage.iterator(); iterator.hasNext();){
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				magazzino.add(corrente);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return magazzino;
		}
		
		public ArrayList<MagazzinoIn> getMagazzinoInOrder(String orderBy,String ascDesc, int page){
			ArrayList<MagazzinoIn> magazzino= new ArrayList<MagazzinoIn>();
			Session session = sf.openSession();
			Transaction tx = null;
	        String order="";
	        try{
	        switch (orderBy) {
	            case "id":  order = "prodotto.idProdotto";
	                     break;
	            case "nome":  order = "prodotto.nomeProdotto";
	                     break;
	            case "quantita":  order = "quantita";
	                     break;
	            case "prezzo":  order = "prodotto.prezzo";
	                     break;
	                     default:order="prodotto.idProdotto";
	        }
	        }catch(NullPointerException e){
	        	order="prodotto.idProdotto";
	        }

			try{
			tx = session.beginTransaction();
			Query q=session.createQuery("from MagazzinoIn where quantita !=0 order by "+order+" "+ascDesc);
//			q.setFirstResult(0);
//			q.setMaxResults(5);
			q.setFirstResult(5*(page-1));
//			System.out.println(5*(page-1));
			q.setMaxResults(5);
//			System.out.println(q);
			List<?> storage = q.list();
			
			for (Iterator<?> iterator =
					storage.iterator(); iterator.hasNext();){
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				magazzino.add(corrente);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return magazzino;
		}
		
		public ArrayList<MagazzinoIn> getMagazzinoInOrderLimit(){
			ArrayList<MagazzinoIn> magazzino= new ArrayList<MagazzinoIn>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			Query q=session.createQuery("from MagazzinoIn order by quantita desc");
			q.setFirstResult(0);
			q.setMaxResults(6);
			List<?> storage = q.list();
			
			for (Iterator<?> iterator =
					storage.iterator(); iterator.hasNext();){
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				magazzino.add(corrente);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return magazzino;
		}
		public ArrayList<Integer> getNumberNotMagazzinoIn() {
			ArrayList<Integer> number= new ArrayList<Integer>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			long size = (long)session.createQuery("select count(*) from Prodotto p where p.idProdotto not in("+
			" select m.prodotto.idProdotto from MagazzinoIn m)").uniqueResult();
			float siz= (float)size;
			
			for (int i=1; i<=(Math.ceil(siz/5)); i++){
				number.add(i);
			}
//			for (int i=0; i<number.size(); i++){
//				System.out.println(number.get(i));
//			}
//			System.out.println(size);
//			System.out.println(number.size());
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return number;
		}
		
		public ArrayList<MagazzinoIn> getAllMagazzinoInLim(int page){
			ArrayList<MagazzinoIn> magazzino= new ArrayList<MagazzinoIn>();
			Session session = sf.openSession();
			Transaction tx = null;
			try{
			tx = session.beginTransaction();
			Query query=session.createQuery("from MagazzinoIn");
//			List<?> storage = session.createQuery("from MagazzinoIn").list();
			query.setFirstResult(5*(page-1));
			query.setMaxResults(5);
			List<?> storage=query.list();
			for (Iterator<?> iterator =
					storage.iterator(); iterator.hasNext();){
				MagazzinoIn corrente = (MagazzinoIn) iterator.next();
				magazzino.add(corrente);
			}
			tx.commit();
			}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			}
			return magazzino;
		}
}
