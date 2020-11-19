<%@ page import="java.util.Date" import="java.util.Locale" import="java.text.NumberFormat" %>
<%@ include file="sql.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3c.org/TR/xhtml1/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/XHTML" xml:lang="it" lang="it">
<%@ include file="heading.jsp" %>
<body>
<table width="100%">
  <tr>
    <td colspan="2" class="blubg">
      <%@ include file="banner.jsp" %>
    </td>
  </tr>
  <tr>
    <td width="10%" class="blubg" style="vertical-align: top">
      <%@ include file="menu.jsp" %>
    </td>
    <td width="90%" style="align: center">
      <%
        try {
          Connection c = DriverManager.getConnection(stringaConnessione, utenteSQL, passwordSQL);
          Statement s = c.createStatement();
          ResultSet r = s.executeQuery("SELECT * FROM prodotti");
      %>
          <div class="intestazione">&nbsp;</div>
          <div class="intestazione">Catalogo dei prodotti disponibili:</div>
          <div class="intestazione">&nbsp;</div>
          
          <table align="center">
            <tr>
              <td class="intestazione_tabella">
                Prodotto
              </td>
              <td class="intestazione_tabella">
                Descrizione
              </td>
              <td class="intestazione_tabella">
                Prezzo (&euro;)
              </td>
              <td class="intestazione_tabella">
                Q.t&agrave; da ordinare
              </td>
              <td class="intestazione_tabella">
                Inserisci nel carrello
              </td>
            </tr>
        <%
            NumberFormat fmt=NumberFormat.getInstance(Locale.ITALIAN);
            fmt.setMinimumFractionDigits(2);
            fmt.setMaximumFractionDigits(2);
            int riga=0;
            
            while(r.next()) {
              riga++;
              String stile=riga%2==0 ? "riga2_tabella" : "riga1_tabella";
        %>
              <tr>
                <form method="post" action="inserisci.jsp">
                <td class='<%= stile%>'><%= r.getString(2)%></td>
                <td class='<%= stile%>'><%= r.getString(3)%></td>
                <td class='<%= stile%>'><%= fmt.format(r.getFloat(4))%></td>
                <td class='<%= stile%>'>
                  <input type="text" name="qt" value="1" size="2" />
                </td>
                <td class='<%= stile%>'>
                  <input type="submit" name="ordina" value="&gt;&gt;" />
                  <input type="hidden" name="pid" value="<%= r.getInt(1)%>" />
                </td>
                </form>
              </tr>
        <%
            }
        %>
          </table>
        <%
            r.close(); s.close(); c.close();
          } catch (SQLException e) {
              response.sendRedirect("errore.jsp");
          }
        %>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <%@ include file="footer.jsp" %>
    </td>
  </tr>
</table>

</body>
</html>