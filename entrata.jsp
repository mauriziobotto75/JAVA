<%-- 
    Document   : entrata
    Created on : 24-giu-2015, 17.18.56
    Author     : maurizio
--%>
 

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Gestione Utente</h1>
        <form name="Entro" method="post" action="utente.jsp">
        <table name="tabella">
            <tr>
                <td>Nome Utente</td><td><input type="text" name="Utente" size=20></td>
                
            </tr>   
            <tr>
                <td>Password</td><td><input type="text" name="password" size=20></td>
                
            </tr>
            <tr>
                <td><input type="submit" name="invia" value="Invia"></td>
            </tr>
        </table>
        </form>    
    </body>
</html>
