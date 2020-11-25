import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class FindRecords implements ActionListener{

	 
	private ScrollingPanel fields;
	private JTextArea output;
	private Connection connection;
	public  FindRecords(Connection c, ScrollingPanel f, JTextArea o)	{	
		// TODO Auto-generated method stub
		 connection = c;
		 fields = f;
		 output = o;
		  
	}
	public void actionPerformed(ActionEvent e) {
	 	// TODO Auto-generated method stub
		try
		   {
			if (!fields.last.getText().equals(""))
	  		{   
				Statement statement = connection.createStatement();
				String query = "SELECT * FROM address " +
				"WHERE lastname = '" +
				fields.last.getText() + "'";
			    output.append("\nSending query: " +
				                connection.nativeSQL(query) + "\n");
			    ResultSet rs = statement.executeQuery(query);
			    display(rs);
			    output.append("\nQuery successful\n");
			    statement.close();
				
	  		}
			else
				fields.last.setText("Enter last name here then press Find");
			} catch (SQLException sqlex)
		 
	{ sqlex.printStackTrace();
	  output.append(sqlex.toString());
	  }
	}

// Mostra i risultati di una query
public void display(ResultSet rs)
{

try {
	rs.next();
	int recordNumber = rs.getInt(1);

    if (recordNumber != 0) {
    	fields.id.setText(rs.getString(2));
    	fields.last.setText(rs.getString(3));
    	fields.address.setText(rs.getString(4));
    	fields.city.setText(rs.getString(5));
    	fields.state.setText(rs.getString(6));
    	fields.zip.setText(rs.getString(7));
    	fields.country.setText(rs.getString(8));
    	fields.email.setText(rs.getString(9));
    	fields.home.setText(rs.getString(10));
    	fields.fax.setText(rs.getString(11));
    }
    else 
    	output.append("\nNo record found");
    }
       catch (SQLException sqlex) 
        {
    	   sqlex.printStackTrace();
    	   output.append(sqlex.toString());
        	}
        }
}