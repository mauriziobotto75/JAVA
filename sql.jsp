<%@ page import="java.sql.*" %>

<%!
  String stringaConnessione="jdbc:mysql://localhost/myshop";
  String utenteSQL="root";
  String passwordSQL="password";
  int lunghezzaMassima=100;

  String CodificaApici(String s) {
    String codifica="";

    for(int i=0; i<s.length(); i++)
      if(s.charAt(i)=='\'')
        codifica+="''";
      else
        codifica+=s.charAt(i);

    return codifica;
  }
%>

<%
  try {
    Class.forName("com.mysql.jdbc.Driver"); 
  } catch (ClassNotFoundException e) {
    response.sendRedirect("errore.jsp");
  }
%>