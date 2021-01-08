package com.siu.component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

import com.siu.*;

public class RicercaStudente  extends JDialog {
      JTextField txtMatricola = new JTextField(12);
      JTextField txtCognome = new JTextField(12);
      JTable tblStudenti = null;
      JButton btnOk = null;
      JButton btnCancel = null;

      String _matricola = null;
      Vector _studente = new Vector();

      public RicercaStudente() {
        super((Frame)null, true);
        setVisible(true);
        setSize(300, 300);
        costruisciMaschera();
        registraEventi();
        }

      private void costruisciMaschera() {
       
        JLabel lblMatricola = new JLabel("Matricola");
        JLabel lblCognome = new JLabel("Cognome");
        JPanel pnlRicerca = new JPanel();
        pnlRicerca.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 5, 10 );
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = c.WEST;
        pnlRicerca.add(lblMatricola, c);

        c.gridx = 0;
        c.gridy = 1;
        pnlRicerca.add(lblCognome, c);

        c.insets = new Insets(5, 5, 5, 10);
        c.gridx = 1;
        c.gridy = 0;
        pnlRicerca.add(txtMatricola, c);

        c.gridx = 1;
        c.gridy = 1;
        pnlRicerca.add(txtCognome, c);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlRicerca, BorderLayout.NORTH);

// Bottoni OK E cancel

        JPanel pnlButton = new JPanel(new GridLayout(1,2));
        JPanel pnlSouth = new JPanel(new BorderLayout());
        btnOk = new JButton("OK");
        btnCancel = new JButton("Cancel");
        pnlButton.add(btnOk);
        pnlButton.add(btnCancel);
        pnlSouth.add(pnlButton, BorderLayout.EAST);
        getContentPane().add(pnlSouth, BorderLayout.SOUTH);

// Tabella studenti
        Vector header = new Vector();
        header.addElement("Matricola");
        header.addElement("Cognome");
        header.addElement("Nome");

        tblStudenti = new JTable(_studente, header);
        JScrollPane scrStudenti = new JScrollPane(tblStudenti);
        getContentPane().add(scrStudenti, BorderLayout.CENTER);
                
       }

      private void registraEventi() {
// Tasto invio su matricola

       txtMatricola.addKeyListener ( new KeyAdapter() {
          public void KeyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_ENTER) {
                cercaStudente(txtMatricola.getText());
                tblStudenti.updateUI();
                };
            }
          });

// Tasto invio su cognome
       txtCognome.addKeyListener ( new KeyAdapter() {
         public void KeyPressed(KeyEvent e) {
           if (e.getKeyCode() == e.VK_ENTER) {
               cercaStudente2(txtCognome.getText());
               tblStudenti.updateUI();
               };
             }
           });

// Tasto OK
      btnOk.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
               if (tblStudenti.getSelectedRowCount() > 0) {
                 int i = tblStudenti.getSelectedRow();
                _matricola = (String)((Vector)_studente.elementAt(i)).elementAt(0);
                 setVisible(false);
                 }
                 else
                 _matricola = null;
                  }
                });

// Tasto Cancel
     btnCancel.addActionListener( new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             _matricola = null;
             setVisible(false);
             }
           });
         }

     private void cercaStudente(String matricola) {
         try {
              Statement st = siu.getConnection().createStatement();
              ResultSet rs = st.executeQuery("SELECT matricola, cognome, nome FROM studente WHERE matricola = '" +
                                              txtMatricola.getText() + "'");
              if (rs.next()) {
                 _studente.removeAllElements();
       
          Vector row = new Vector();
                 row.addElement(rs.getString("matricola"));
                 row.addElement(rs.getString("cognome"));
                 row.addElement(rs.getString("nome"));
                 _studente.addElement(row);
                 }
              }
             catch (SQLException e) {
                   e.printStackTrace();
                   }
                 }
     private void cercaStudente2(String cognome) {
       try {
           Statement st = siu.getConnection().createStatement();
           ResultSet rs = st.executeQuery("SELECT matricola, cognome, nome FROM studente WHERE cognome = '" +
                                           txtCognome.getText()  + "'");
           _studente.removeAllElements();

           while (rs.next()) {
                 Vector row = new Vector();
                 row.addElement(rs.getString("matricola"));
                 row.addElement(rs.getString("cognome"));
                 row.addElement(rs.getString("nome"));

                _studente.addElement(row);
                 }
              }
        catch (SQLException e) {
            e.printStackTrace();
            }
          }
        public String getStudente() {
           return _matricola;
           }
         }
     

