<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
		String pageOffsetStr = request.getParameter("p");
		Integer pageSize = 20;
		Integer totalCnt = 39;
		Integer pageOffset = 0;
		if (pageOffsetStr != null && !"".equals(pageOffsetStr)) {
			pageOffset = Integer.parseInt(pageOffsetStr);
		}
		Integer lineSize = 4;
		Integer pageCnt = totalCnt % pageSize == 0 ? (totalCnt / pageSize) : (totalCnt / pageSize + 1);
%>
<ul class="em clearfix">
	<%for (int emIndex = pageOffset * pageSize + 1; emIndex <= pageSize * (pageOffset + 1); emIndex++) {%>
	<%if (emIndex <= totalCnt) {%>
	<li><img src="images/em/panst/<%=emIndex%>.gif" onclick="newEM(<%=emIndex%>);" /></li>
	<%}%>
	<%}%>
</ul>
<div class="empg orangelink">
	<a href="javascript:loadNewEM(<%=pageOffset > 0 ? pageOffset - 1 : 0%>);">&lt;</a>&nbsp;<%=pageOffset + 1%>/<%=pageCnt%>&nbsp;<a href="javascript:loadNewEM(<%=pageOffset >= (pageCnt - 1) ? (pageCnt - 1) : (pageOffset + 1)%>);">&gt;</a>
</div>