import java.awt.*;
 
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
// Definizione della classe ControlPanel
public class ControlPanel extends JPanel 
{
	private JButton findName, addName, updateName, clear, help;
	public ControlPanel(Connection c, ScrollingPanel s, JTextArea t)
	
	{
		setLayout(new GridLayout(1,5));
		findName = new JButton("Find");
		findName.addActionListener(new FindRecords(c, s, t));
		add(findName);
		addName = new JButton("Add");
		addName.addActionListener(new AddRecord(c, s, t));
		add(addName);
		updateName = new JButton("Update");
		updateName.addActionListener(new UpdateRecord(c, s, t));
		add(updateName);
		clear = new JButton("Clear");
		clear.addActionListener(new ClearFields(s));
		add(clear);
		help = new JButton("Help");
		help.addActionListener(new Help(t));
		add(help);
	}
} 
