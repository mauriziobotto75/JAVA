package util;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;


public class Javamail {
	
		public static void sendMail (String dest, final String mitt, String oggetto, String testoEmail)
	      throws MessagingException
	  {
		 
		final String pass="spammingaround";		//password dell'account con il quale si vuole mandare messaggio!!!
	    // Creazione di una mail session
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host dell'account di posta elettronica che si vuole usare per inviare es. smtp.gmail.com per account gmail
	    props.put("mail.smtp.port", "587"); //TLS Port
	    props.put("mail.smtp.auth", "true"); //abilita autenticazione
	    props.put("mail.smtp.starttls.enable", "true"); //abilita STARTTLS

	    
	    Authenticator auth = new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(mitt, pass);
	        }
	    };						// definisco i parametri di autenticazione
	    
	    
	    Session session = Session.getInstance(props, auth);
	    // Creazione del messaggio da inviare
	    MimeMessage message = new MimeMessage(session);
	    message.setSubject(oggetto);
	    message.setText(testoEmail);

	    // Aggiunta degli indirizzi del mittente e del destinatario
	    InternetAddress fromAddress = new InternetAddress(mitt);			
	    InternetAddress toAddress = new InternetAddress(dest);	
	    message.setFrom(fromAddress);
	    message.setRecipient(Message.RecipientType.TO, toAddress);

	    // Invio del messaggio
	    Transport.send(message);
	  }
	}


