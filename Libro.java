package biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Libro {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		Connection db_conn=connessione.connessione1();
		AggLibro user=new AggLibro();
		boolean ctrl=false;
		
		do{
			try{
				ctrl=false;
				
				user.setTitolo((JOptionPane.showInputDialog("Inserisci Titolo")));
				if(user.getTitolo().trim().isEmpty()){
					ctrl=true;
				}
				
				
				user.setAutore((JOptionPane.showInputDialog("Inserisci Autore")));
				if(user.getAutore().trim().isEmpty()){
					ctrl=true;
				}
				
				user.setGenere((JOptionPane.showInputDialog("Inserisci Genere")));
				if(user.getGenere().trim().isEmpty()){
					ctrl=true;
				}
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				ctrl=true;
			}
			
		}while(ctrl);
		
		String sql="Insert into libri(Titolo,Autore,Genere) "
				+ "	values("
				+"'"+user.getTitolo()+"', "
				+"'"+user.getAutore()+"', "
				+"'"+user.getGenere()+" ')";
				
		Statement statement=db_conn.createStatement();
		
		int ris=0;
		
			//per tutto cio che e un inserimento modifica o eliminazione si usa executeUpdate
			ris=statement.executeUpdate(sql);
			if(ris==1)
				System.out.println("Inserimento andato a buon fine!");
			
			db_conn.close();
}
}
