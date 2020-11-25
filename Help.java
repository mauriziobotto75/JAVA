import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.event.ActionListener;
public class Help implements ActionListener{
    private JTextArea output;
    public Help(JTextArea o)
	 
	{ 
    	output = o;
	}	
    	public void actionPerformed(ActionEvent e) 
    	{
    		output.append("\nClick find to locate  a record.\n" + 
    	                  "Click add to insert a new record.\n" + 
    				      "Click update to update the information in a record.\n" +
    	                  "Click clear to empty the textfields.\n");
    		
    	}	 
		 
	}

 
