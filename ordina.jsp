<%@ page import="java.util.Date" import="java.util.Locale" import="java.text.NumberFormat" import="myShop.*" %>
<%@ include file="parametri.jsp" %>

<%
  Carrello carrello=null;
  
  if(session.getValue("myShop.carrello")==null) {
    carrello=new Carrello();
    session.putValue("myShop.carrello",carrello);
  }
  else
    carrello=(Carrello)session.getValue("myShop.carrello");

  boolean carrelloVuoto=carrello.Vuoto();
%>

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
    <td width="90%" style="text-align: center">
      <%
        if(carrelloVuoto) {
      %>
        <div class="errore">&nbsp;</div>
        <div class="errore">Il tuo carrello &egrave; vuoto:<br />devi inserirvi dei prodotti prima di effettuare un ordine.</div>
        <div class="errore">&nbsp;</div>
      <%
        }
        else {
      %>
        <div class="intestazione">&nbsp;</div>
        <div class="intestazione">Modulo d'ordine:</div>
        <div class="intestazione">&nbsp;</div>
        <form method="post" action="invia.jsp">
        <table cellpadding="5" align="center">
          <tr>
            <td class="intestazione_form">
              Nome:&nbsp;
            </td>
            <td>
              <input type="text" name="nome" size="20" />
            </td>
          </tr>
          <tr>
            <td class="intestazione_form">
              Cognome:&nbsp;
            </td>
            <td>
              <input type="text" name="cognome" size="20" />
            </td>
          </tr>
          <tr>
            <td class="intestazione_form">
              Indirizzo:&nbsp;
            </td>
            <td>
              <input type="text" name="indirizzo" size="20" />
            </td>
          </tr>
          <tr>
            <td class="intestazione_form">
              Modalit&agrave; di pagamento:&nbsp;
            </td>
            <td>
              <select name="pagamento">
                <option value="-">Seleziona un'opzione...</option>
                <option value="bonifico bancario">Bonifico bancario</option>
                <option value="contrassegno">Pagamento in contrassegno</option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="intestazione_form">
              Modalit&agrave; di consegna:&nbsp;
            </td>
            <td>
              <select name="consegna">
                <option value="-">Seleziona un'opzione...</option>
                <option value="corriere espresso">Corriere espresso</option>
                <option value="posta celere">Posta celere</option>
              </select>
            </td>
          </tr>
        </table>
        <div class="intestazione">&nbsp;</div>
        <div class="intestazione">Prodotti:</div>
        <div class="intestazione">&nbsp;</div>
        <table cellpadding="5" align="center">
          <tr>
            <td class="intestazione_tabella">
              Prodotto
            </td>
            <td class="intestazione_tabella">
              Descrizione
            </td>
            <td class="intestazione_tabella">
              Prezzo unitario (&euro;)
            </td>
            <td class="intestazione_tabella">
              Quantit&agrave;
            </td>
            <td class="intestazione_tabella">
              Prezzo totale (&euro;)
            </td>
          </tr>
              <%
                  NumberFormat fmt=NumberFormat.getInstance(Locale.ITALIAN);
                  fmt.setMinimumFractionDigits(2);
                  fmt.setMaximumFractionDigits(2);
                  fmt.setParseIntegerOnly(false);
                  double totale=0.0;
                  int i=0;
  
                  try {

                    for(; i<carrello.NumeroProdotti(); i++) {
                      String stile=(i+1)%2==0 ? "riga2_tabella" : "riga1_tabella";
                      ProdottoSelezionato ps=carrello.TrovaProdotto(i);
                      totale+=ps.prezzo*ps.quantitaSelezionata;
              %>
                          <tr>
                            <td class='<%= stile%>'><%= ps.nome%></td>
                            <td class='<%= stile%>'><%= ps.descrizione%></td>
                            <td class='<%= stile%>'><%= fmt.format(ps.prezzo)%></td>
                            <td class='<%= stile%>'>
                              <%= ps.quantitaSelezionata%>
                            </td>
                            <td class='<%= stile%>'><%= fmt.format(ps.prezzo*ps.quantitaSelezionata)%></td>
                          </tr>
              <%
                    }

                  } catch (Exception e) {
                    response.sendRedirect("errore.jsp");
                  }
              %>
          <tr>
            <td colspan="5"  style="text-align: right">
              Contributo fisso spese di spedizione: <%= fmt.format(contributoSpeseSpedizione)%> &euro;
            </td>
          </tr>
          <tr>
            <td colspan="5"  style="text-align: right">
              Totale: <%= fmt.format(totale+contributoSpeseSpedizione)%> &euro;
            </td>
          </tr>
        </table>
        <div>
          <input type="submit" value="Invia &gt;&gt;" />
        </div>
        </form>
      <%
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