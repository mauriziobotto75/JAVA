package com.siu.component;
import com.siu.*;
import java.util.*;
import java.sql.*;

public class Root {
  public static Studente lookupStudente(String matricola) {
         Studente s = null;
         try {
              Statement st = siu.getConnection().createStatement();
              ResultSet rs = st.executeQuery("SELECT matricola, cognome, nome FROM studente " +
                                             "WHERE matricola = '" + matricola + "'");
                      if (rs.next()) {
                          s = new Studente();
                          s.Matricola = rs.getInt("matricola");
                          s.Cognome  = rs.getString("cognome");
                          s.Nome     = rs.getString("nome");

                              }
                            }
         catch (SQLException e) {
              e.printStackTrace();
              }
            return s;
         }

  public static Vector getStudenti(String cognome) {
         Vector v = new Vector();
         try {
              Statement st = siu.getConnection().createStatement();
              ResultSet rs = st.executeQuery("SELECT matricola, cognome, nome FROM studente WHERE cognome like " + cognome + "%'");
              while (rs.next()) {
                     Studente s = new Studente();
                     s.Matricola = rs.getInt("matricola");
                     s.Cognome =  rs.getString("cognome");
                     s.Nome  =  rs.getString("nome");
                     v.addElement(s);
                     }
                   }
         catch (SQLException e) {
                e.printStackTrace();
                }
                return v;
              }
    }
