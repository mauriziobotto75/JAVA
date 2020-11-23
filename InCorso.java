package biblioteca;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;



import javax.swing.JOptionPane;

public class InCorso {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
	Connection db_conn=connessione.connessione1();	
	Statement statement=db_conn.createStatement();
	Prestito user=new Prestito();
	String risultato="";
	

		
		ResultSet rs=statement.executeQuery(
				"select prestito.*,Nome,Cognome,Titolo "+
				"from prestito,persona,libri "+
				"where Cf_p = Cf_p_fk "+
				"and CodLibri = CodLibri_fk "+
				"and stato=1"
				);
		
		
		
		while(rs.next()){
			user.setCf_p_fk(rs.getString("Cf_p_fk"));
			user.setCodLibri_fk(rs.getString("CodLibri_fk"));
			user.setInizio(rs.getDate("Inizio"));
			user.setRestituzione(rs.getDate("Restituzione"));
			user.setNome(rs.getString("Nome"));
			user.setCognome(rs.getString("Cognome"));
			user.setTitolo(rs.getString("Titolo"));
			
			risultato=risultato+user.toString()+"\n";
			
		}
		
		JOptionPane.showMessageDialog(null, ""+risultato);
		
		rs.close();
		db_conn.close();

	}
	
}
	



