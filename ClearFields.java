import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
public class ClearFields implements ActionListener{
// Definizione della classe ClearFields
	 
	private ScrollingPanel fields;
	public ClearFields(ScrollingPanel f)
		{		// TODO Auto-generated method stub
		  fields = f;
		  
	}
	public void actionPerformed(ActionEvent e) {
	 	// TODO Auto-generated method stub
		fields.id.setText("");
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

}
