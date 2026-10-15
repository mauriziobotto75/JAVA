<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<style>
#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;

}
#nav {
    line-height:30px;
    background-color:#eeeeee;
    height:500px;
    width:150px;
    float:left;
    padding:5px;
      
}
#section {
    width:350px;
    float:left;
    padding:10px;
 	 
}
#other {
    clear:both;
    text-align:center;
   padding:5px;	
	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
   	padding:5px;	 

}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Errore</title>
</head>

<body>
<div id="header">
<h2>ERRORE</h2>

</div>
<div id="nav">

</div>
<div id="section">

il sistema ha riscontrato un errore...<br>
controlla nome, cognome username e password<br>
se il problema persiste<br>
contatta l'<a href="<s:url action='contatta'/>">amministratore</a>
</div>
<div id="other">
<a href="index.jsp">Indietro</a>
</div>
<div id="footer">
Copyright © <a href="email.jsp">Enrico Puglia</a>
</div>
</body>
</html>