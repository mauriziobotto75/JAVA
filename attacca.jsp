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
    height:300px;
    width:100px;
    float:left;
    padding:5px;
      
}
#section1 {
    width:800px;
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
<title>Attacca!!!</title>
</head>
<body>

<div id="header">
<h3>ATTACCA!!!</h3>
</div>

<div id="nav">

</div>
		
<div id="section1">
	
	<s:form action="attack">
	<s:radio name="cittamia" list="current"/>
	<s:textfield name="num" label="numero soldati" size="5" value="0"/>
	<s:radio name="cittaavv" list="opp"/>
    <s:submit value="ok"/>
	</s:form>

	
</div>
<div id="other">

</div>

<div id="footer">
Copyright © <a href="email.jsp">Enrico Puglia</a>
</div>


</body>
</html>