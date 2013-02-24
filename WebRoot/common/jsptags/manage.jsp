<%@ page session="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<jsp:useBean id="currentPageNumber" type="java.lang.Integer"
	scope="request" />
<bean:message key="page.common.pager.count" arg0="${count}"/>

<pg:first>
	<a href="<%=pageUrl%>"><nobr>
			&nbsp;<bean:message key="page.common.pager.first"/>&nbsp;
		</nobr> </a>
</pg:first>
<pg:prev>
	<a href="<%=pageUrl%>"><nobr>
			&nbsp;<bean:message key="page.common.pager.prev"/>&nbsp;
		</nobr> </a>
</pg:prev>
<pg:pages>
	<%
	if (pageNumber == currentPageNumber) {
	%>
	<a href="<%=pageUrl%>"
		style="text-decoration:none;font-size:16px;color:black"><b><%=pageNumber%>
	</b> </a>
	<%
	} else {
	%>
	<a href="<%=pageUrl%>"><%=pageNumber%> </a>
	<%
	}
	%>
</pg:pages>
<pg:next>
	<a href="<%=pageUrl%>"><nobr>
			&nbsp;<bean:message key="page.common.pager.next"/>&nbsp;
		</nobr> </a>
</pg:next>
<pg:last>
	<a href="<%=pageUrl%>"><nobr>
			&nbsp;<bean:message key="page.common.pager.last"/>&nbsp;
		</nobr> </a>
</pg:last>
