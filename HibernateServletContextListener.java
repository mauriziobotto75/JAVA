package listener;

import java.net.URL;

//import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import actions.Avanti;
import dao.MagazzinoInDao;

public class HibernateServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SessionFactory sf = (SessionFactory) sce.getServletContext().getAttribute("SessionFactory");
		sf.close();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		URL url = HibernateServletContextListener.class.getResource("/hibernate.cfg.xml");
		Configuration config = new Configuration();
		config.configure(url);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(config.getProperties()).build();
		SessionFactory sf = config.buildSessionFactory(serviceRegistry);
		sce.getServletContext().setAttribute("SessionFactory", sf);
//		sce.getServletContext().setAttribute("flag", "falso");
//		sce.getServletContext().setAttribute("cart", "vuoto");
		
		
		MagazzinoInDao list=new MagazzinoInDao(sf);
//		Avanti.articoli=list.getAllMagazzinoIn();
		Avanti.articoli=list.getMagazzinoInOrderLimit();
		for (int i =0; i<Avanti.articoli.size(); i++){
		if(Avanti.articoli.get(i).getProdotto().getDescrizione().length() > 50)
			Avanti.articoli.get(i).getProdotto().setDescrizione(Avanti.articoli.get(i).getProdotto().getDescrizione().substring(0,50) + "...");
		}
		for (int i =0; i<Avanti.articoli.size();){//nasconde i prodotti con 0 unità presenti in magazzino
			if(Avanti.articoli.get(i).getQuantita()<1){
				Avanti.articoli.remove(i);
			}
			else{i++;}
		}
		
		sce.getServletContext().setAttribute("listaProdotti", Avanti.articoli);

	}

}
