<%@ page import="java.util.Date" import="java.util.Locale" import="java.text.NumberFormat" import="java.util.Vector" import="myShop.*" %>
<%@ include file="sql.jsp" %>

<%
  if(session.getValue("myShop.backofficeUser")==null)
    response.sendRedirect("backoffice.jsp");

  String idOrdine=request.getParameter("id");

  try {
    Integer.parseInt(idOrdine);
  } catch (NullPointerException e) {
    idOrdine="0";
  } catch (NumberFormatException e) {
    idOrdine="0";
  }

  Vector ordini=new Vector();
  boolean nessunOrdine=true;

  try {
    Connection c = DriverManager.getConnection(stringaConnessione, utenteSQL, passwordSQL);
    Statement s = c.createStatement();
    ResultSet r = s.executeQuery("SELECT ID, Data, Nome, Cognome, Indirizzo, Pagamento, Consegna, SpeseSpedizione FROM intestazioni_ordini where evaso = 0 ORDER BY Data DESC");

    while(r.next()) {
      Ordine o=new Ordine(r.getInt(1), r.getDate(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getFloat(8));
      Statement s2 = c.createStatement();
      ResultSet r2=s2.executeQuery("SELECT P.ID, P.Prodotto, P.Descrizione, R.Prezzo, R.Quantita FROM prodotti AS P, righe_ordini AS R WHERE P.ID=R.IDProdotto AND R.IDOrdine="+o.numero);

      while(r2.next()) {
        Prodotto p=new Prodotto(r2.getInt(1), r2.getString(2), r2.getString(3), r2.getFloat(4));
        o.AggiungiRiga(p, r2.getInt(5));
      }

      r2.close();
      s2.close();
      ordini.add(o);
    }

    r.close();
    nessunOrdine=ordini.size()==0;

    s.close();
    c.close();
  } catch (SQLException e) {
    out.println(e.toString());
    response.sendRedirect("errore.jsp");
  }
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
            if(nessunOrdine) {
          %>
              <div class="intestazione">&nbsp;</div>
              <div class="intestazione">Non ci sono ordini!</div>
              <div class="intestazione">&nbsp;</div>
          <%
            }
            else {
          %>
              <div class="intestazione">&nbsp;</div>
              <div class="intestazione">Ordini registrati:</div>
              <div class="intestazione">&nbsp;</div>
              <table cellpadding="5" align="center">
                <tr>
                  <td class="intestazione_tabella">
                    # ordine
                  </td>
                  <td class="intestazione_tabella">
                    Data
                  </td>
                  <td class="intestazione_tabella">
                    Nome
                  </td>
                  <td class="intestazione_tabella">
                    Cognome
                  </td>
                  <td class="intestazione_tabella">
                    Indirizzo
                  </td>
                  <td class="intestazione_tabella">
                    Mod. pagamento
                  </td>
                  <td class="intestazione_tabella">
                    Mod. consegna
                  </td>
                  <td class="intestazione_tabella">
                    Spese spedizione (&euro;)
                  </td>
                  <td class="intestazione_tabella">
                  </td>
                  <td class="intestazione_tabella">
                  </td>
                </tr>
          <%
                NumberFormat fmt=NumberFormat.getInstance(Locale.ITALIAN);
                fmt.setMinimumFractionDigits(2);
                fmt.setMaximumFractionDigits(2);
                fmt.setParseIntegerOnly(false);

                for(int i=0; i<ordini.size(); i++) {
                  String stile=(i+1)%2==0 ? "riga2_tabella" : "riga1_tabella";
                  Ordine o=(Ordine)ordini.elementAt(i);
                  boolean testId=Integer.parseInt(idOrdine)==o.numero;
          %>
                  <tr>
                    <td class='<%= stile%>'><%= o.numero%></td>
                    <td class='<%= stile%>'><%= o.data%></td>
                    <td class='<%= stile%>'><%= o.nome%></td>
                    <td class='<%= stile%>'><%= o.cognome%></td>
                    <td class='<%= stile%>'><%= o.indirizzo%></td>
                    <td class='<%= stile%>'><%= o.pagamento%></td>
                    <td class='<%= stile%>'><%= o.consegna%></td>
                    <td class='<%= stile%>'><%= fmt.format(o.speseSpedizione)%></td>
                    <td class='<%= stile%>'><a href="ordini.jsp?id=<%= !testId ? o.numero : 0%>"><%= !testId ? "dettagli" : "nascondi dettagli"%></a></td>
                    <td class='<%= stile%>'><a href="evadi.jsp?id=<%= !testId ? o.numero : 0%>"><%= !testId ? "evaso" : "annulla"%></a></td>
                  </tr>
          <%
                  if(testId) {
          %>
                    <tr>
                      <td colspan="9">
                        <span class="piccolo">&nbsp;Dettaglio righe dell'ordine n. <%= o.numero%>:</span>
                        <table width="100%" align="center">
                          <tr>
                            <td class="intestazione_sotto_tabella">
                              Prodotto
                            </td>
                            <td class="intestazione_sotto_tabella">
                              Prezzo unitario (&euro;)
                            </td>
                            <td class="intestazione_sotto_tabella">
                              Quantit&agrave;
                            </td>
                            <td class="intestazione_sotto_tabella">
                              Prezzo totale (&euro;)
                            </td>
                          </tr>
          <%
                          for(int j=0; j<o.NumeroRighe(); j++) {
                            String stile2=(j+1)%2==0 ? "riga2_sotto_tabella" : "riga1_sotto_tabella";
                            ProdottoSelezionato ps=o.TrovaRiga(j);
          %>
                            <tr>
                              <td class='<%= stile2%>'><%= ps.nome%></td>
                              <td class='<%= stile2%>'><%= fmt.format(ps.prezzo)%></td>
                              <td class='<%= stile2%>'><%= ps.quantitaSelezionata%></td>
                              <td class='<%= stile2%>'><%= fmt.format(ps.prezzo*ps.quantitaSelezionata)%></td>
                            </tr>
          <%
                          }
          %>
                        </table>
                      </td>
                    </tr>
          <%
                  }

                };
          %>
              </table>
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