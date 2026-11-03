package com.siu.esami;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.util.Vector;

import com.siu.*;
import com.siu.business.*;

public class StudenteApplet extends Applet {
       TextField txtMatricola = new TextField(12);
       TextField txtEsame = new TextField(12);
       TextField txtData = new TextField(8);
       TextField txtVoto = new TextField(6);
       List tblEsami = null;
       Button btnTrova = null;
       Button btnAdd = null;

       // Dati
       com.siu.business.Studente _studente= null;

       public void init() {
         try {
             new siu();
             System.out.println("Connessione ok!");
             }
         catch(Exception e)   {
             e.printStackTrace(); 
             System.out.println("Connessione fallita");
             }
             costruisciMaschera();
             registraEventi();
             }
       private void costruisciMaschera() {
           setSize(500,300);
           Label lblMatricola = new Label("Matricola");
           Panel pnlStudente = new Panel();
           pnlStudente.setLayout(new GridBagLayout());

           GridBagConstraints c = new GridBagConstraints();
           c.insets = new Insets(5, 0, 5, 10);

           c.gridx = 0;
           c.gridy = 0;
           c.anchor = c.WEST;
           pnlStudente.add(lblMatricola, c);

           c.gridx = 1;
           c.gridy = 0;
           pnlStudente.add(txtMatricola, c);

           // Bottone Trova
           c.gridx = 2;
           c.gridy = 0;
           btnTrova = new Button("Trova");
           pnlStudente.add(btnTrova, c);

           setLayout = new BorderLayout();
           add(pnlStudente, BorderLayout.NORTH);

           // TABELLA esami
           tblEsami = new List();
           ScrollPane scrEsami = new ScrollPane();
           Panel pnlEsami = new Panel(new BorderLayout());
           pnlEsami.add(pnlEsami, BorderLayout.CENTER);

           // Esame, Data, Voto
           Panel pnlCampiEsame = new Panel(new FlowLayout());
           pnlCampiEsame.add(new Label("Esame"));
           pnlCampiEsame.add(txtEsame);
           pnlCampiEsame.add(new Label("Data"));
           pnlCampiEsame.add(txtData);
           pnlCampiEsame.add(new Label("Voto"));
           pnlCampiEsame.add(txtVoto);

           btnAdd = new Button("Aggiungi");
           pnlCampiEsame.add(btnAdd);
           pnlEsami.add(pnlCampiEsame, BorderLayout.SOUTH);
           add(pnlEsami, BorderLayout.CENTER);
       }

       private void registraEventi() {
          btnTrova.addActionListener( new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  caricaDati();
                  }
               });

          btnAdd.addActionListener( new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  aggiungiEsame();
                  }
                });
              }

       private void caricaDati() {
          _studente = Root.lookupStudente(txtMatricola.getText());
          tblEsami.removeAll();
          if (_studente !=null) {
             Vector v = _studente.getEsami();
             for (int i =0; i < v.size(); i++) {
                 Esame es = (Esame) v.elementAt(i);
                 String row = es.materia + " " + es.data + " " + es.voto;
                 tblEsami.addItem(row);
                 }
               }
                else
                  tblEsami.addItem("Studente non trovato");
             }
       private void aggiungiEsame() {
             if (_studente != null) {
                  Esame es = new Esame();
                  es.materia = txtEsame.getText();
                  es.data = txtData.getText();
                  es.voto = txtVoto.getText();
                  _studente.addEsame(es);
                  String row = es.materia + " " + es.data + " " + es.voto;
                  tblEsami.addItem(row);
                  }
                }
             }
