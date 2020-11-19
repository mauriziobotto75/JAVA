<%@ page import="java.util.Date" %>

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
      <div class="errore">&nbsp;</div>
      <div class="errore">Siamo spiacenti, ma il catalogo dei prodotti non &egrave; al momento disponibile.<br /><br />Riprovate pi&ugrave; tardi.</div>
      <div class="errore">&nbsp;</div>
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