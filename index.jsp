<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
    height:500px;
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
<title>Indice</title>
</head>
<body>
<div id="header">
<h1>Benvenuto</h1>
</div>
<div id="nav">

</div>
<div id="section">
<h3>Inserisci dati accesso</h3>
<s:form action="login" >
			 <s:textfield name="username1" label="username" />
			<s:textfield name="password1" label="password" type= "password"/>
            <s:submit value="login"/>
</s:form>

</div>
<div id="other">
se non sei ancora iscritto, puoi registrarti <a href="registrazione.jsp">qui</a>
</div>
<div id="footer">
Copyright © <a href="email.jsp">Enrico Puglia</a>
</div>

</body>
</html>