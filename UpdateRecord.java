import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class UpdateRecord implements ActionListener{
    private ScrollingPanel fields;
    private JTextArea output;
    private Connection connection;
    
    public UpdateRecord(Connection c, ScrollingPanel f, JTextArea o)
	 
	{
		connection = c;
		fields = f;
		output = o;
	}
		public void actionPerformed(ActionEvent e) 
		{

		 try	{	
			  Statement statement = connection.createStatement();
		      if (! fields.id.getText().equals(""))
		     {	
		    	  String query = "UPDATE addresses SET " +
		          "firstname = '" + fields.first.getText() + "'," +
		    	  "lastname = '"  + fields.last.getText() + "',"  +
		          "address = '" + fields.address.getText() + "'," +
		    	  "city = '" + fields.city.getText() + "'," +
		    	  "stateorprovince = '" + fields.state.getText() + "'," + 
		    	  "postalcode = '" + fields.zip.getText() + "'," +
		    	  "country = '" + fields.country.getText() + "'," +
		    	  "emailaddresses = '" + fields.email.getText() + "'," +
		    	  "homephone = '" + fields.home.getText() + "'," +
		    	  "faxnumber = '" + fields.fax.getText() + "'" +
		    	  "WHERE id = " + fields.id.getText();
		    	  output.append("\nSending query: " +
		    	                  connection.nativeSQL(query) + "\n");
		    	  int result = statement.executeUpdate(query);
		    	  if (result == 1)
		    		  output.append("\nUpdate successful");
		    		  
		    	  else  {
		    		  output.append("\nUpdate failed");
		    		  fields.first.setText("");
		    		  fields.last.setText("");
		    		  fields.address.setText("");
		    		  fields.city.setText("");
		    		  fields.state.setText("");
		    		  fields.zip.setText("");
		    		  fields.country.setText("");
		    		  fields.email.setText("");
		    		  fields.home.setText("");
		    		  fields.fax.setText("");
		    		  
		    	  }
		    	  statement.close();
			     }		          
		      else
		    	  output.append("\nYou may only update an existing record. Use find to locate the record then modify the information qand press Update.\n" );
		     }
             catch (SQLException sqlex) {
            	 sqlex.printStackTrace();
            	 output.append(sqlex.toString());
             }
		}
}