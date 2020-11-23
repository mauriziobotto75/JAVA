package biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class AggPrestito {
	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		Connection db_conn=connessione.connessione1();
		Prestito user=new Prestito();
		boolean ctrl=false;
		
		do{
			try{
				ctrl=false;
				
				// <----- verificare se il codice fiscale e esistente
				user.setCf_p_fk((JOptionPane.showInputDialog("Inserisci Codice Fiscale")));
				if(user.getCf_p_fk().trim().isEmpty()){
					ctrl=true;
				}
				
				//<----- verificare se il codice del libro e esistente 
				//<----- verificare che il codice non appartenga ad un libro gia in prestito
				user.setCodLibri_fk((JOptionPane.showInputDialog("Inserisci Codice Libro")));
				if(user.getCodLibri_fk().trim().isEmpty()){
					ctrl=true;
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
				ctrl=true;
			}
			
		}while(ctrl);
		

		
		String sql="Insert into prestito(Cf_p_fk,CodLibri_fk,Restituzione,Stato) "
				+ "	values("
				+"'"+user.getCf_p_fk()+"', "
				+"'"+user.getCodLibri_fk()+"', "
				+"date_add(current_timestamp, interval 10 day ), "
				+ 1 + " )";
				
		Statement statement=db_conn.createStatement();
		
		int ris=0;
		
			//per tutto cio che e un inserimento modifica o eliminazione si usa executeUpdate
			ris=statement.executeUpdate(sql);
			if(ris==1)
				System.out.println("Inserimento andato a buon fine!");
			
			db_conn.close();
}
}

