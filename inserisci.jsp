<%@ page import="java.util.Date" import="java.util.Locale" import="java.text.NumberFormat" import="myShop.*" %>
<%@ include file="sql.jsp" %>

<%
  String stampaFlag=request.getParameter("stampa");
  
  if(stampaFlag==null)
        stampaFlag="2";

  Carrello carrello=null;
  
  if(session.getValue("myShop.carrello")==null) {
    carrello=new Carrello();
    session.putValue("myShop.carrello",carrello);
  }
  else
    carrello=(Carrello)session.getValue("myShop.carrello");

  int qtProdotto=0;

  if(!stampaFlag.equals("1")) {
        String idProdotto=request.getParameter("pid");
  
        try {
                qtProdotto = Integer.parseInt(request.getParameter("qt"));
        } catch (NullPointerException e) {
                qtProdotto=0;
        } catch (NumberFormatException e) {
                qtProdotto=0;
        }
  
        if(qtProdotto>0) {

                try {
                  Connection c = DriverManager.getConnection(stringaConnessione, utenteSQL, passwordSQL);
                  Statement s = c.createStatement();
                  ResultSet r = s.executeQuery("SELECT Prodotto, Descrizione, Prezzo FROM prodotti WHERE ID="+idProdotto);
                  
                  if(r.next()) {
                    Prodotto p=new Prodotto(Integer.parseInt(idProdotto), r.getString(1), r.getString(2), r.getFloat(3));

                    if(carrello.TrovaIndiceProdotto(Integer.parseInt(idProdotto))==-1)
                      carrello.AggiungiProdotto(p, qtProdotto);
                    else
                      carrello.ModificaQuantita(Integer.parseInt(idProdotto), qtProdotto);

                  }

                  r.close();
                  s.close();
                  c.close();
                } catch (SQLException e) {
                  response.sendRedirect("errore.jsp");
                }
        
        }

  }

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
        if(qtProdotto<=0 && !stampaFlag.equals("1")) {
      %>
          <div class="errore">&nbsp;</div>
          <div class="errore">Errore:<br />La quantit&agrave; del prodotto deve essere un numero intero maggiore o uguale a 1.<br /><br /><a href="javascript:window.history.go(-1)">Riprova.</a></div>
          <div class="errore">&nbsp;</div>
      <%
        }
        else {
        
          if(carrelloVuoto) {
      %>
            <div class="intestazione">&nbsp;</div>
            <div class="intestazione">Il tuo carrello &egrave; vuoto!</div>
            <div class="intestazione">&nbsp;</div>
      <%
          }
          else {
      %>
            <div class="intestazione">&nbsp;</div>
            <div class="intestazione">Il contenuto del tuo carrello &egrave;:</div>
            <div class="intestazione">&nbsp;</div>
            <form method="post" action="aggiorna.jsp">
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
                <td class="intestazione_tabella">
                </td>
              </tr>
              <%
                  NumberFormat fmt=NumberFormat.getInstance(Locale.ITALIAN);
                  fmt.setMinimumFractionDigits(2);
                  fmt.setMaximumFractionDigits(2);
                  fmt.setParseIntegerOnly(false);
                  int i=0;
  
                  for(; i<carrello.NumeroProdotti(); i++) {
                    String stile=(i+1)%2==0 ? "riga2_tabella" : "riga1_tabella";
                    ProdottoSelezionato ps=carrello.TrovaProdotto(i);
              %>
                        <tr>
                          <td class='<%= stile%>'><%= ps.nome%></td>
                          <td class='<%= stile%>'><%= ps.descrizione%></td>
                          <td class='<%= stile%>'><%= fmt.format(ps.prezzo)%></td>
                          <td class='<%= stile%>'>
                            <input type="hidden" name="id<%= i%>" value="<%= ps.id%>" />
                            <input type="text" name="qt<%= i%>" value="<%= ps.quantitaSelezionata%>" size="2" />
                          </td>
                          <td class='<%= stile%>'><%= fmt.format(ps.prezzo*ps.quantitaSelezionata)%></td>
                          <td class='<%= stile%>'><a href="elimina.jsp?id=<%= ps.id%>"><img src="images/cestino.gif" border="0" /></a></td>
                        </tr>
              <%
                  }
              %>
              <tr>
                <td colspan="6" style="text-align: right">
                  <input type="hidden" name="righe" value="<%= i%>" />
                  <input type="submit" value="Aggiorna le quantit&agrave; &gt;&gt;" />
                </td>
              </tr>
            </table>
            </form>
            <form method="post" action="svuota.jsp">
            <table align="center">
              <tr>
                <td>
                  <input type="submit" value="Svuota il carrello &gt;&gt;" />
                </td>
                
              </tr>
            </table>
            </form>
            </form>
            <form method="post" action="prodotti.jsp">
            <table align="center">
              <tr>
                <td>
                  <input type="submit" value="Continua a comprare &gt;&gt;" />
                </td>
                
              </tr>
            </table>
            </form>
      <%
          }

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