/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maurizio
 */
 
    import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class GestioneDB {

	private final String userName = "root";

	private final String password = "root";

	/** indirizzo del computer dove c'è il db MySQL */
	private final String serverName = "localhost"; 

	/** default  3306 */
	private final int portNumber = 1527;

	private final String dbName = "APP";
	
	private final String tableName = "CUSTOMER";
	
	Connection conn = null;

	
	//crea  connessione
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);	//inserisco le credenziali nel connectionProps
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"				//crea connessione col DB tramite il drivermanager
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);	//passandogli le credenziali

		return conn;
	}

	
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
		
	    Statement stmt = null; 	//Statement sono i costrutti che ci permettono di eseguire interrogazioni, modifiche e inserimenti verso il database.
	    
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); //esegue il comando
	        return true;
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	


	// apre connessione al db MySQL
	
	public void run() {

		try {
			conn = this.getConnection();
			System.out.println("Connesso al database");
		} catch (SQLException e) {
			System.out.println("ERROR: non riesco a connettermi al db");
			e.printStackTrace();
			return;
		}
	}
	
	
	public void popolaTable(){
		
		try{
			conn = this.getConnection();
			String popolaString=("insert into " + this.tableName + " values " 
				+ "(27856,'The Mentalist','Rete4', 'Serie TV'),"
				+ "(98125, 'Cucine da incubo', 'Real Time','Reality'),"
				+ "(54321, 'Detto Fatto', 'Rai2','Intrattenimeto'),"
				+ "(90761, 'SuperQuark', 'Rai1','Documentario'),"
				+ "(78077,'Mila & Shiro','Italia1','Cartoon');");
			this.executeUpdate(conn , popolaString);
			System.out.println("Tabella popolata");

		}catch (SQLException e) {
			System.out.println("ERROR: non riesco a popolare la tabella");
			e.printStackTrace();
			return;	
		}
	}
	


	public void createTable(){	
		try {
			conn = this.getConnection();

		    String createString =
			        "CREATE TABLE " + this.tableName + " ( " + "codice int primary key,	"
			        		+ "nomeProgramma varchar(30), "
			        		+ "rete varchar(20), "
			        		+ "tipologia varchar(20) )";
			this.executeUpdate(conn , createString);
			System.out.println("Tabella creata");
	    } catch (SQLException e) {
			System.out.println("ERROR: non posso creare la tabella");
			e.printStackTrace();
			return;
		}
	}
	

	public void deleteTable(){
		try {
			conn = this.getConnection();
		    String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(conn, dropString);
			System.out.println("Tabella eliminata");
	    } catch (SQLException e) {
			System.out.println("ERROR: non posso eliminare la tabella");
			e.printStackTrace();
			return;
		}
	}
	
	public void readTable(){
		try {
			conn = this.getConnection();
			String query = ("select * from " + tableName + ";" );

			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);	//rs è una struttura che contiene tutti i dati richiesti
			
			while (rs.next()) {								//stampa le colonne, identificate attraverso il nome del campo!
		        System.out.println(rs.getString("codice"));
		        System.out.println(rs.getString("rete"));
		        System.out.println(rs.getString("tipologia"));
		        System.out.println(rs.getString("nomeProgramma"));	//notare che non sono stampate in ordine

		      }
			rs.close();
		}catch (SQLException e) {
			System.out.println("ERROR: non riesco a leggere la tabella");
			e.printStackTrace();
			return;
		}
	}
	

	public static void main(String[] args) {
		GestioneDB app = new GestioneDB();
		app.run();
		
		app.createTable();
		
		app.popolaTable();
		
		app.readTable();

		//app.deleteTable();
		
	}
}

