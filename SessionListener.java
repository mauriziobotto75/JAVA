package listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		HttpSession sessione=e.getSession();
		sessione.setAttribute("flag", "falso");
		sessione.setAttribute("cart", "vuoto");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		
		
	}

}
