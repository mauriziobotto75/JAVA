<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
function myFunction() {
	document.getElementById("myP").style.visibility = "hidden";
	document.getElementById("myP2").style.visibility = "visible";
}

function myFunction2() {
	document.getElementById("myP2").style.visibility = "hidden";
	document.getElementById("myP").style.visibility = "visible";
}


</script>
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
#section1 {
    width:450px;
    height:500px;
    float:left;
    padding:10px;
 	 
}
#section2 {
    width:450px;
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
<title>Scelta nazione</title>
</head>
<body>
<div id="header">
<h3>Scegli tra Inghilterra e Francia</h3>
</div>

<div id="nav">
<div align="center">
<br>
<br>
<button id="hidden" type="button" onclick="myFunction2()" >Inghilterra</button>
<br>
<br>
<button id="visible" type="button" onclick="myFunction()" >Francia</button>

</div>
</div>
		
<div id="section1">
<div id="myP" style="visibility:hidden;">
	<h3>INGHILTERRA</h3>
	<p>Zotici e ignoranti, contro un esercito sguarnito ed impreparato,<br> 
	hanno fatto della quantità la loro arma vincente... <br>
	sentono più la necessità di copulare tra di loro, piuttosto <br>
	che organizzarsi militarmente per poter resistere ad una offensiva<br> 
	militare guidata da una	ragazzina vestita da cavaliere<br>
	quello che non possono vincere con le armi, lo comprano col denaro<br> 
	</p>	
	
	<s:form action="sceltaI">
            <s:submit value="god save the queen"/>
	</s:form>
	
	</div>
</div>

<div id="section1">
<div id="myP2" style="visibility:hidden;">
	<h3>FRANCIA</h3>	
	<p>Popoli di avvinazzati e feticisti del formaggio,<br>
	 vive un periodo di decadenza culturale e militare...<br>
	persa la propria capitale, i reali francesi si nascondono a Orleans, <br>
	nella speranza di un miracolo che possa salvarli e riportarli al <br>
	fasto di un secolo prima... <br>
	traditori e opportunisti, sanno trarre il meglio da ogni occasione...<br>
	sono capaci di vendere la madre per pochi spicci,<br>
	una pulzella d'Orlean per poco più...<br>
	
	<s:form action="sceltaF">
            <s:submit value="vive la France"/>
	</s:form>
	
	</p>
	</div>
</div>
		
<div id="other">

</div>

<div id="footer">
Copyright © <a href="email.jsp">Enrico Puglia</a>
</div>
</body>
</html>