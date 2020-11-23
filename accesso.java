package biblioteca;

import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class accesso {

	public static void main(String[] args) throws SQLException {
		Connection db_conn = connessione.connessione1();
		Login user = new Login();
		boolean ctrl = false;

		do {
			try {
				ctrl = false;
				user.setUser(JOptionPane.showInputDialog("Inserisci username"));
				if (user.getUser().trim().isEmpty()) {
					ctrl = true;
				}

				user.setPassword(JOptionPane
						.showInputDialog("Inserisci la password"));
				if (user.getPassword().trim().isEmpty()) {
					ctrl = true;
				} else {
					if (!user.getPassword().equals(
							JOptionPane.showInputDialog(null,
									"Inserisci nuovamente la password"))) {
						JOptionPane.showMessageDialog(null,
								"Le password non coincidono");
						ctrl = true;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				ctrl = true;
			}

		} while (ctrl);

		Statement statement = db_conn.createStatement();

		ResultSet rs = statement.executeQuery("select * " + "from addetti "
				+ "where User = '" + user.getUser() + "' " + "and Password = '"
				+ user.getPassword() + "' ");

		if (rs.next()) {
			user.setNome(rs.getString("Nome"));
			user.setCognome(rs.getString("Cognome"));
			JOptionPane.showMessageDialog(null, user.toString());
		} else {
			JOptionPane.showMessageDialog(null, "User o Password errata");
		}

		rs.close();
		db_conn.close();

	}

}
