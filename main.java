package biblioteca;

import java.sql.SQLException;

import javax.swing.JOptionPane;


public class main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String scelta1;
		int scelta;
		
		//richiamo il metodo per stabile la connessione al db
		connessione.connessione1();
		
		//inserisco la scelta per effettuare il login o la registrazione
		//bisogna effettuare il controllo sul dato inserito
		
		do{
		scelta1=JOptionPane.showInputDialog(null,"Inserire la scelta:\n"+
		"1)Login\n"+"2)Registrazione");
		}while(!Metodi.IsNumber(scelta1));
		
		//trasformo in numero
		scelta=Integer.parseInt(scelta1);
		
		//richiamo a seconda della scelta
		if(scelta==1){
			accesso.main(args);
		}else{
			registrazione.main(args);
			accesso.main(args);
		}
		
		do{
		//scelta numero 2: 1)visualizza,2)libri,3)prestito
			//effettuo il controllo
			do{
		scelta1=JOptionPane.showInputDialog(null,"Inserire la scelta:\n"+
				"0)Esci\n"+"1)Riepilogo\n"+"2)Libri\n"+"3)Prestito");
			}while(!Metodi.IsNumber(scelta1));
			
			//trasformo in numero
			scelta=Integer.parseInt(scelta1);
		
		
		//richiamo a seconda della scelta
		switch(scelta){
		case 1:
			//Riepilogo: scelta dell'opzione
			//effettuo il controllo
			do{
			scelta1=JOptionPane.showInputDialog("Riepilogo:\n"+"1)Prestiti in corso\n"+"2)Storico");
			}while(!Metodi.IsNumber(scelta1));
			
			//trasformo in numero
			scelta=Integer.parseInt(scelta1);
			
			if(scelta==1){
				InCorso.main(args); //prestiti in corso  <---aggiungere avviso giorni massimi
			}else{
				if(scelta==2){
					Storico.main(args); //Storico dei prestiti effettuati
				}
			}
			
			break;
		
			
			
			
		case 2:
			//Libri: scelta dell'opzione:
			//effettuo il controllo
			do{
			scelta1=JOptionPane.showInputDialog("Libri:\n"+"1)Aggiungi\n"+"2)Elimina\n"+"3)Visualizza");
			}while(!Metodi.IsNumber(scelta1));
			
			
			//trasformo in numero
			scelta=Integer.parseInt(scelta1);
			
			switch(scelta){
			case 1:
				//aggiungi un nuovo libro
				Libro.main(args);
				break;
			case 2:
				//elimina un libro
				EliminaLibro.main(args);
				break;
			case 3:
				//visualizza i libri
				VisLibri.main(args);
				break;
			}
			
			break;
			
			
			
			
		case 3:
			//Prestito: scelta dell'opzione:
			//effettuo il controllo
			do{
			scelta1=JOptionPane.showInputDialog("Gestione Prestiti:\n"+"1)Aggiungi\n"+"2)Segnala Restituito");
			}while(!Metodi.IsNumber(scelta1));
			
			//trasformo in numero
			scelta=Integer.parseInt(scelta1);
			
			
			if(scelta==1){
				//aggiungi un nuovo prestito
				//effettuo il controllo
				do{
				scelta1=JOptionPane.showInputDialog("Aggiungi:\n"+"1)A esistente\n"+"2)Aggiungi");
				}while(!Metodi.IsNumber(scelta1));
				
				//traformo in numero
				scelta=Integer.parseInt(scelta1);
				
				
				if(scelta==1){
					AggPrestito.main(args);//aggiungi a un utente esistente
				}else{
					AggPersona.main(args);//aggiungi un utente
					AggPrestito.main(args);//aggiungi a un utente esistente
				}
			}else{
				Restituzione.main(args);//segnala come restituito
				
			}
			
			break;
			
		}
		}while(scelta!=0);

	}

}
