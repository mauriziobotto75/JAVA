package com.siu.component;
import java.sql.*;
public class siu {
       static Connection _con = null;
       public siu() throws SQLException, ClassNotFoundException {
         
           
              String MY_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
              String MY_URL = "jdbc:odbc:DSNTEST";
              Class.forName(MY_DRIVER);
              _con = DriverManager.getConnection(MY_URL);
              }
            public static Connection getConnection() {
              return _con;
              }
            }

// La connessione si otterrà includendo il package com.siu e invocando il metodo
// getConnection():
// Connection con = siu.getConnection();



