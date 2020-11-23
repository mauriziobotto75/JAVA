package biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class VisLibri {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
	Connection db_conn=connessione.connessione1();	
	Statement statement=db_conn.createStatement();
	AggLibro user=new AggLibro();
	String risultato="";
		
		ResultSet rs=statement.executeQuery(
				"select * "+
				"from libri "
				);
		
		while(rs.next()){
			user.setTitolo((rs.getString("Titolo")));
			user.setAutore((rs.getString("Autore")));
			user.setGenere((rs.getString("Genere")));
			risultato=risultato+user.toString()+"\n";
			
		}
		
		JOptionPane.showMessageDialog(null, ""+risultato);
		
		rs.close();
		db_conn.close();

	}
	
}
	



