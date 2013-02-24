<%@ page session="false" contentType = " text/html;charset="UTF-8" %>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<font face="verdana" color="#aaaaaa" size="-1">
<pg:prev export="pageUrl" ifnull="<%= true %>">
  <% if (pageUrl == null) { %>
    <b>&laquo; <bean:message key="page.common.pager.prev"/></b>
  <% } else { %>
    <font color="#ff0000"><b>&laquo;</b></font>
    <a href="<%= pageUrl %>"><b><bean:message key="page.common.pager.prev"/></b></a>
  <% } %>
</pg:prev>
<pg:pages export="pageUrl,firstItem,lastItem">
     <a href="<%= pageUrl %>"><b><%= firstItem %> - <%= lastItem %></b></a> 
</pg:pages>
<pg:next export="pageUrl" ifnull="<%= true %>">
  <% if (pageUrl == null) { %>
    <b><bean:message key="page.common.pager.next"/> &raquo;</b>
  <% } else { %>
    <a href="<%= pageUrl %>"><b><bean:message key="page.common.pager.next"/></b></a>
    <font color="#ff0000"><b>&raquo;</b></font>
  <% } %>
</pg:next>