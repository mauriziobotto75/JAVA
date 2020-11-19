<table class="blubg" style="color: white">
  <tr>
    <td class="blubg">
      &nbsp;
    </td>
    <td class="blubg">
      <strong>Menu:</strong>
    </td>
  </tr>
  <tr>
    <td class="blubg">
      <img src="images/TriangoloBlu.jpg" />
    </td>
    <td class="blubg">
      <a href="index.jsp" style="color: white">Home</a>
    </td>
  </tr>
  <tr>
    <td class="blubg">
      <img src="images/TriangoloBlu.jpg" />
    </td>
    <td class="blubg">
      <a href="prodotti.jsp" style="color: white">Prodotti</a>
    </td>
  </tr>
  <tr>
    <td class="blubg">
      <img src="images/TriangoloBlu.jpg" />
    </td>
    <td class="blubg">
      <a href="inserisci.jsp?stampa=1" style="color: white">Il tuo carrello</a>
    </td>
  </tr>
  <tr>
    <td class="blubg">
      <img src="images/TriangoloBlu.jpg" />
    </td>
    <td class="blubg">
      <a href="ordina.jsp" style="color: white">Ordina!</a>
    </td>
  </tr>
  <tr>
    <td class="blubg">
      <img src="images/TriangoloBlu.jpg" />
    </td>
    <td class="blubg">
      <a href="backoffice.jsp" style="color: white"><%= session.getValue("myShop.backofficeUser")!=null ? "Ordini" : "Area riservata"%></a>
    </td>
    </tr>
    <tr>
    <td class="blubg">
      <img src="images/TriangoloBlu.jpg" />
    </td>
    <td class="blubg">
      <a href="evasi.jsp" style="color: white"><%= session.getValue("myShop.backofficeUser")!=null ? "Evasi" : "Area riservata"%></a>
    </td>
  </tr>
<%
  if(session.getValue("myShop.backofficeUser")!=null) {
%>
    <tr>
      <td class="blubg">
        <img src="images/TriangoloBlu.jpg" />
      </td>
      
      <td class="blubg">
        <a href="logout.jsp" style="color: white">Logout</a>
      </td>
    </tr>
<%
  }
%>
</table>