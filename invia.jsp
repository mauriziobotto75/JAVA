<%@ page isThreadSafe="false" %>
<%@ page import="java.util.Date" import="myShop.*" %>
<%@ include file="parametri.jsp" %>
<%@ include file="sql.jsp" %>

<%
  String nome = request.getParameter("nome");
  String cognome = request.getParameter("cognome");
  String indirizzo = request.getParameter("indirizzo");
  String pagamento = request.getParameter("pagamento");
  String consegna = request.getParameter("consegna");

  boolean formValido=!nome.trim().equals("") && nome.trim().length()<=lunghezzaMassima && !cognome.trim().equals("") && cognome.trim().length()<=lunghezzaMassima && !indirizzo.trim().equals("") && indirizzo.trim().length()<=lunghezzaMassima && !pagamento.trim().equals("-") && !consegna.trim().equals("-");

  Carrello carrello=null;
  
  if(session.getValue("myShop.carrello")==null) {
    carrello=new Carrello();
    session.putValue("myShop.carrello",carrello);
  }
  else
    carrello=(Carrello)session.getValue("myShop.carrello");

  boolean carrelloVuoto=carrello.Vuoto();

  if(formValido && !carrelloVuoto) {
          DateFormat formatoData=new SimpleDateFormat("yyyy-MM-dd");

          try {
                Connection c = DriverManager.getConnection(stringaConnessione, utenteSQL, passwordSQL);
                Statement s = c.createStatement();
                
                try {
                  c.setAutoCommit(false);
                  ResultSet r = s.executeQuery("SELECT MAX(ID) FROM intestazioni_ordini");
                  int idOrdine=0;
                        
                  if(r.next()) {
                          idOrdine=r.getInt(1);
                  }
                        
                  r.close();
                
                  idOrdine++;
                  s.executeUpdate("INSERT INTO intestazioni_ordini (ID, Data, Nome, Cognome, Indirizzo, Pagamento, Consegna, SpeseSpedizione) VALUES ("+idOrdine+", '"+formatoData.format(new Date())+"', '"+CodificaApici(nome)+"', '"+CodificaApici(cognome)+"', '"+CodificaApici(indirizzo)+"', '"+CodificaApici(pagamento)+"', '"+CodificaApici(consegna)+"', "+contributoSpeseSpedizione+")");

                  for(int i=0; i<carrello.NumeroProdotti(); i++) {
                          ProdottoSelezionato ps=carrello.TrovaProdotto(i);
                          s.executeUpdate("INSERT INTO righe_ordini (IDOrdine, IDProdotto, Prezzo, Quantita) VALUES ("+idOrdine+", "+ps.id+", "+ps.prezzo+", "+ps.quantitaSelezionata+")");
                  }

                  c.commit();
                }
                catch(SQLException e) {
                  c.rollback();
                  response.sendRedirect("errore.jsp");
                }

                s.close();
                c.close();
                carrello.Svuota();
          } catch (SQLException e) {
                response.sendRedirect("errore.jsp");
          }

  }
  else {

        if(carrelloVuoto)
                response.sendRedirect("ordina.jsp");

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
        if(formValido) {
      %>
          <div class="intestazione">&nbsp;</div>
          <div class="intestazione">Grazie!<br /><br />Abbiamo ricevuto il tuo ordine che verr&agrave; evaso al pi&ugrave; presto.</div>
          <div class="intestazione">&nbsp;</div>
      <%
        }
        else {
      %>
          <div class="errore">&nbsp;</div>
          <div class="errore">Per inviare un ordine, devi compilare tutti i campi del form,<br />
                              controllando di non superare i <%= lunghezzaMassima%> caratteri per i campi testuali.<br /><br /><a href="javascript:window.history.go(-1)">Riprova.</a></div>
          <div class="errore">&nbsp;</div>
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