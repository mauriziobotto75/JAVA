package biblioteca;

import java.sql.Connection;  // classe implementazione Connessione
import java.sql.DriverManager;  // classe caricamento Driver di connessione
import java.sql.ResultSet;   
import java.sql.SQLException;
import java.sql.Statement;
	
	
	public class connessione {
		
		private final static String DB_NAME =  "biblioteca";
		private final static String SERVER ="localhost"; //nome virtuale o indirizzo ip
		private final static String PORT ="1530";
		private final static String CONNECTION="jdbc:derby://" +SERVER+ ":" +PORT+ "/" +DB_NAME;
		private final static String USERNAME="root";
		private final static String PASSWORD="root";	
		
		
		//metodo connessione
		
		public static Connection connessione1(){
			Connection con=null;
			
			//controllo caricamento driver connessione DB
			try{
				//controllo se nel mio project è presente questa classe
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver caricati correttamente");
			}catch(ClassNotFoundException e){
				System.out.println("Driver non caricati");
				e.printStackTrace();
			}
			
		
		//connessione al db
		
		try{
			// per connetterci ad un db o server differente modificare parametri
			con=DriverManager.getConnection(CONNECTION,USERNAME,PASSWORD);
			System.out.println("connessione eseguita");
		}catch(SQLException e){
			System.out.println("connessione fallita!");
			e.printStackTrace();
		}
		
		
		return con;
	}
		
}

