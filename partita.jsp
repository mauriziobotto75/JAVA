<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
function myFunction() {
	document.getElementById("myTable").style.visibility = "hidden";
	document.getElementById("avversario").style.visibility = "visible";
	document.getElementById("adversary").style.visibility = "visible";
}

function myFunction2() {
	document.getElementById("avversario").style.visibility = "hidden";
	document.getElementById("adversary").style.visibility = "hidden";
	document.getElementById("myTable").style.visibility = "visible";
}


</script>
<style>
#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;

}
#section1 {
    width:400px;
    height:700px;
    float:left;
    padding:10px;
 	 
}
#section2 {
    width:400px;
    height:700px;
    float:left;
    padding:10px;
 	 
}
#section3 {
    width:350px;
    height:700px;
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
<title>Nuova Partita</title>
</head>
<body>
<div id="header">
<h1>Benvenuto</h1>
</div>

<div id="section1">
<h3>Stato</h3>
<table>
<tr>
<td></td><td><s:property value="io.bandiera" /></td>

</tr>
<tr><td></td></tr>
<tr>
<td>Popolazione</td>
<td><s:property value="io.popolazione" />
</td>

</tr>
<tr><td></td></tr>
<tr>
<td>Soldati</td>
<td><s:property value="io.soldati" /></td>

</tr>

<tr><td></td></tr>
<tr>
<td>Denaro</td>
<td><s:property value="io.denaro" /></td>

</tr>
<tr><td></td></tr>
<tr>
<td>Cibo</td>
<td><s:property value="io.cibo" /></td>

</tr>
</table>
<div id="città">
<br>
<h3>ELENCO CITTA'</h3>
<h2>Soldati disponibili: <s:property value="io.soldatiDisp"/></h2>

<s:form action="setsoldati" >
<table>
 <tr>
    <th>Nome</th>
    <th>Soldati</th>
    <th>Popolazione</th>
  </tr>
<s:iterator value="current" status="listaStatus"> 
<tr>
<td height="15" valign="middle"><s:property value="nome"/></td>
<td><s:property value="nsoldati"/></td>
<td><s:property value="popolazione"/></td>
<td><s:textfield name="prova" value="0" size="5"/></td>
</tr>	
</s:iterator>
</table>
<s:submit value="Posiziona soldati"/>
</s:form>
</div>



</div>
<div id="section2">
<h3>Avversari</h3>
<table id= "avversario" style="visibility:hidden;">
<tr>
<td></td><td><s:property value="opponent.bandiera" /></td>

</tr>
<tr><td></td></tr>
<tr>
<td>Popolazione</td>
<td><s:property value="opponent.popolazione" /></td>

</tr>
<tr><td></td></tr>
<tr>
<td>Soldati</td>
<td><s:property value="opponent.soldati" /></td>

</tr>
<tr><td></td></tr>

<tr><td></td></tr>
<tr>
<td>Denaro</td>
<td><s:property value="opponent.denaro" /></td>

</tr>
<tr><td></td></tr>
<tr>
<td>Cibo</td>
<td><s:property value="opponent.cibo" /></td>
</tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
</table>
<br>
<br>
<table id= "adversary" style="visibility:hidden;">
  <tr>
    <th>Nome</th>
    <th>Soldati</th>
    <th>Popolazione</th>
  </tr>
<s:iterator value="opp"> 
<tr>
<td height="15" valign="middle"><s:property value="nome"/></td>
<td><s:property value="nsoldati"/></td>
<td><s:property value="popolazione"/></td>

</tr>	
</s:iterator>
<tr><td>
<s:form action="avanti">
            <s:submit value="Avanti"/>
	</s:form>
</td></tr>

</table>

</div>
<div id="section3">
<h3>Azioni</h3>
<table id="myTable">
<tr><td>
<s:form action="attacca">
            <s:submit value="Attacca"/>
	</s:form>
</td></tr>
<tr><td></td></tr>
<tr><td>
	<s:form action="attendi">
            <s:submit value="Attendi"/>
	</s:form>
</td></tr>
<tr><td></td></tr>
<tr><td>
	<s:form action="addestra">
            <s:submit value="Addestra"/>
	</s:form>
</td></tr>
<tr><td></td></tr>
<tr><td>
	<s:form action="incrementa">
            <s:submit value="Incrementa la popolazione"/>
	</s:form>
</td></tr>
<tr><td></td></tr>
<tr><td><button id="visible" type="button" onclick="myFunction()" >Spia</button></td></tr>
<tr><td></td></tr>
</table>
</div>
<div id="other">
se non hai capito le regole (che cazzo stai a fa???)<br>
clicca <a href="regole.jsp">qui</a> per leggere il regolamento
</div>
<div id="footer">
Copyright © <a href="email.jsp">Enrico Puglia</a>
</div>
</body>
</html>