<%@ page session="false" %>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<font face="verdana" color="#aaaaaa" size="-1">
<pg:prev export="pageUrl" ifnull="<%= true %>">
  <% if (pageUrl == null) { %>
    <b>&laquo; Previous</b>
  <% } else { %>
    <font color="#ff0000"><b>&laquo;</b></font>
    <a href="<%= pageUrl %>"><b>Previous</b></a>
  <% } %>
</pg:prev>
<pg:pages export="pageUrl,firstItem,lastItem">
     <a href="<%= pageUrl %>"><b><%= firstItem %> thru <%= lastItem %></b></a> 
</pg:pages>
<pg:next export="pageUrl" ifnull="<%= true %>">
  <% if (pageUrl == null) { %>
    <b>Next &raquo;</b>
  <% } else { %>
    <a href="<%= pageUrl %>"><b>Next</b></a>
    <font color="#ff0000"><b>&raquo;</b></font>
  <% } %>
</pg:next>
</font>
