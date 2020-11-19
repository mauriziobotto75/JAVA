<%@ page import="java.util.Date" import="java.util.Locale" import="java.text.NumberFormat" import="java.util.Vector" import="myShop.*" %>
<%@ include file="sql.jsp" %>

<%
  //if(session.getValue("myShop.backofficeUser")==null)
   // response.sendRedirect("backoffice.jsp");

  String idOrdine=request.getParameter("id");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cancella</title>
</head>
<body>
<% Connection c = DriverManager.getConnection(stringaConnessione, utenteSQL, passwordSQL);
Statement s = c.createStatement();
int r = s.executeUpdate("DELETE FROM intestazioni_ordini where id = " + idOrdine);

response.sendRedirect("evasi.jsp");
%>
</body>
</html>