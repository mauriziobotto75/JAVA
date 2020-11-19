<%@ page import="myShop.*" %>

<%
  Carrello carrello=null;
  
  if(session.getValue("myShop.carrello")==null)
    carrello=new Carrello();
  else
    carrello=(Carrello)session.getValue("myShop.carrello");

  carrello.Svuota();  
  response.sendRedirect("inserisci.jsp?stampa=1");
%>
