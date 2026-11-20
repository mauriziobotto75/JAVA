<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contatta</title>
<style>
#header {

	background-color:black;
	height: 100px;
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
    height: 40px;
    color:white;
    clear:both;
    text-align:center;
   	padding:5px;	 

}

</style>
</head>
<body>
<div id="header">
Contattami
</div>

<div id="nav">

</div>
<div id= section>

<s:form action="invia" >
<h3>Inserisci i tuoi dati</h3>
			 <s:textfield name="name" label="Nome" />
			 <s:textfield name="surname" label="Cognome" />
			 <s:textfield name="email_add" label="Indirizzo Email" />
			<s:textarea label="Testo Email" name="email" cols="50" rows="5"/>
<s:submit value="Invia Email"/>
</s:form>
</div>
<div id="other"></div>
<div id="footer">
<a href="index.jsp">Indietro</a></div>
</body>
</html>