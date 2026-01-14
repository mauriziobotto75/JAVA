<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<script>

function myFunction() {
	document.getElementById("myForm").style.visibility = "hidden";
}

function myFunction2() {
	document.getElementById("myForm").style.visibility = "visible";
//	 var x = document.getElementById("mySelect").value; 
//   document.getElementById("myForm").innerHTML= "Function2()";
	
}
function functionHide() {
	document.getElementById("elenco").style.visibility = "hidden";
		
}
function functionShow() {
	document.getElementById("elenco").style.visibility = "visible";
	
}
function functionHide2() {
	document.getElementById("tabella").style.visibility = "hidden";
		
}
function functionShow2() {
	document.getElementById("tabella").style.visibility = "visible";
	
}
	
</script>
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

h1.visible {
    visibility: visible
}

h1.hidden {
    visibility: hidden
}

</style>
<title>Saluta</title>
</head>
<body>

<div id="header">
<h2>Menu</h2>

</div>

<div id="nav">

<button id="visible" type="button" onclick="functionShow(),myFunction(), functionShow2()" >Elenco</button>

<button id="hidden" type="button" onclick="myFunction2(), functionHide(),functionHide2()" >Registra</button>
</div>
		
<div id="section" style="visibility:hidden;">
		
		<h3 style="visibility:visible;">benvenuto <s:property value="person.nome"/> <s:property value="person.cognome"/></h3>		
		
		<p style="visibility:visible;"><s:property value="stringa1"/><s:property value="stringa2"/></p>

		<p style="visibility:visible;"><s:property value="errPass"/> </p>
		
		<s:form action="aggiungi" id="myForm">
		<h3>Inserisci nome e cognome</h3>
			 <s:textfield name="nom" label="Nome" />		
			 <s:textfield name="cognom" label="Cognome" />	
			 <s:textfield name="pass1" type="password" label="Password" />
			 <s:textfield name="pass2" type="password" label="Conferma Password" />		
            <s:submit value="aggiungi"/>
		</s:form>
		
		

<ol id="elenco">
<s:iterator value="temp">
 <li><s:property/></li> <p>
</s:iterator>
</ol>
		
<table id="tabella" style="visibility:hidden;">
<s:iterator value="temp" status="listaStatus">
 <tr>
  	<s:if test="#listaStatus.index == 1">     				<%--  --%>
      <td style="background: #CCCCCC"><s:property value="nome"/></td>
      <td> <s:property value="cognome"/> </td>
    </s:if>
 
    <s:else>
      <td><s:property/></td>
    </s:else>
</tr>
</s:iterator>
</table>		
		

		</div>
<div id="other">
<s:property value="ciao.saluto"/>
</div>
<div id="footer">
Copyright © <a href="email.jsp">Enrico Puglia</a>
</div>


</body>
</html>