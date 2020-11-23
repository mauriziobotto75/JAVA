package biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class EliminaLibro {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		Connection db_conn=connessione.connessione1();
		AggLibro user=new AggLibro();
		int Cod;
		boolean ctrl=false;
		
		
				
		Cod=Integer.parseInt(JOptionPane.showInputDialog("Inserisci il codice del libro"));
				
		
		String sql="Delete from Libri "
				+ "	where codLibri= " + Cod;
				
		Statement statement=db_conn.createStatement();
		
		int ris=0;
		
			//per tutto cio che e un inserimento modifica o eliminazione si usa executeUpdate
			ris=statement.executeUpdate(sql);
			if(ris==1)
				System.out.println("Libro eliminato!");
			
			db_conn.close();
}
}
