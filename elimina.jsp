<%@ page import="myShop.*" %>

<%
  String idProdotto=request.getParameter("id");
  Carrello carrello=null;
  
  if(session.getValue("myShop.carrello")==null)
    carrello=new Carrello();
  else
    carrello=(Carrello)session.getValue("myShop.carrello");

  try {
    carrello.EliminaProdotto(Integer.parseInt(idProdotto));
  } catch (Exception e) {
        response.sendRedirect("errore.jsp");
  }

  response.sendRedirect("inserisci.jsp?stampa=1");
%>
