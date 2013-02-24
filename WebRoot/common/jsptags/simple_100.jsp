<%@ page session="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="currentPageNumber" type="java.lang.Integer"
	scope="request" />
<jsp:useBean id="count" type="java.lang.Integer" scope="request" />
<div id="pgindex">
	<ul id="pg">
		<li><pg:first><a href="<%=pageUrl%>" class="pg_total">H</a></pg:first></li>
		<pg:pages>
			<%
			if (pageNumber == currentPageNumber) {
			%>
			<li><a href="<%=pageUrl%>" class="pg_current"><%=pageNumber%></a></li>
			<%
			} else {
			%>
			<li><a href="<%=pageUrl%>" class="pg_normal"><%=pageNumber%></a></li>
			<%
			}
			%>
		</pg:pages>
		<li><pg:last>
				<a href="<%=pageUrl%>" class="pg_total">E.
						<%
				if (count % 100 == 0) {
				%>
				<%=(int)count/100 %>
						<%
						}
						%>
						<%
						if (count % 100 != 0) {
						%>
						<%=(int)count/100+1 %>
						<%
						}
						%>
					</a>
			</pg:last></li>
	</ul>
	<div id="pg_all">
		${count} ITEMS / 100 PER PAGE
	</div>
</div>
