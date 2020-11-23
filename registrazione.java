package biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class registrazione {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		Connection db_conn=connessione.connessione1();
		Login user=new Login();
		boolean ctrl=false;
		
		do{
			try{
				ctrl=false;
				
				user.setCf_Add(JOptionPane.showInputDialog("Inserisci codice fiscale"));
				if(user.getCf_Add().trim().isEmpty()){
					ctrl=true;
				}
				
				
				user.setNome(JOptionPane.showInputDialog("Inserisci nome"));
				if(user.getNome().trim().isEmpty()){
					ctrl=true;
				}
				
				user.setCognome(JOptionPane.showInputDialog("Inserisci cognome"));
				if(user.getCognome().trim().isEmpty()){
					ctrl=true;
				}
				
				
				user.setUser(JOptionPane.showInputDialog("Inserisci username"));
				if(user.getUser().trim().isEmpty()){
					ctrl=true;
				}
							
				user.setPassword(JOptionPane.showInputDialog("Inserisci la password"));
				if(user.getPassword().trim().isEmpty()){
					ctrl=true;
				}
				
			}catch(Exception e){
				e.printStackTrace();
				ctrl=true;
			}
			
		}while(ctrl);
		
		String sql="Insert into addetti(Cf_Add,Nome,Cognome,User,Password) "
				+ "	values("
				+"'"+user.getCf_Add()+"', "
				+"'"+user.getNome()+"', "
				+"'"+user.getCognome()+"', "
				+"'"+user.getUser()+"', "
				+"'"+user.getPassword()+"')";
				
		Statement statement=db_conn.createStatement();
		
		int ris=0;
		
			//per tutto cio che e un inserimento modifica o eliminazione si usa executeUpdate
			ris=statement.executeUpdate(sql);
			if(ris==1)
				System.out.println("Inserimento andato a buon fine!");
			
			db_conn.close();
}
}
