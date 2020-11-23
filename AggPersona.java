package biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class AggPersona {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		Connection db_conn=connessione.connessione1();
		Persona user=new Persona();
		boolean ctrl=false;
		
		do{
			try{
				ctrl=false;
				
				user.setCf_p((JOptionPane.showInputDialog("Inserisci Codice Fiscale")));
				if(user.getCf_p().trim().isEmpty()){
					ctrl=true;
				}
				
				
				user.setNome((JOptionPane.showInputDialog("Inserisci Nome")));
				if(user.getNome().trim().isEmpty()){
					ctrl=true;
				}
				
				user.setCognome((JOptionPane.showInputDialog("Inserisci Cognome")));
				if(user.getCognome().trim().isEmpty()){
					ctrl=true;
				}
				
				user.setIndirizzo((JOptionPane.showInputDialog("Inserisci Indirizzo")));
				if(user.getIndirizzo().trim().isEmpty()){
					ctrl=true;
				}
				
				user.setCap((JOptionPane.showInputDialog("Inserisci Cap")));
				if(user.getCap().trim().isEmpty()){
					ctrl=true;
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
				ctrl=true;
			}
			
		}while(ctrl);
		
		String sql="Insert into persona(Cf_p,Nome,Cognome,Indirizzo,Cap) "
				+ "	values("
				+"'"+user.getCf_p()+"', "
				+"'"+user.getNome()+"', "
				+"'"+user.getCognome()+"', "
				+"'"+user.getIndirizzo()+"', "
				+"'"+user.getCap()+" ')";
				
		Statement statement=db_conn.createStatement();
		
		int ris=0;
		
			//per tutto cio che e un inserimento modifica o eliminazione si usa executeUpdate
			ris=statement.executeUpdate(sql);
			if(ris==1)
				System.out.println("Inserimento andato a buon fine!");
			
			db_conn.close();
}
}
