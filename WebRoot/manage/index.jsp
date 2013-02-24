<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.net9.core.constant.*"%>
<%@page import="org.net9.common.util.StringUtils"%>
<%
	String username = (String) session
			.getAttribute(BusinessConstants.ADMIN_NAME);
	if (StringUtils.isEmpty(username)) {
		response.sendRedirect(request.getContextPath()
				+ "/manage/login.jsp");
		return;
	}
	response.sendRedirect(request.getContextPath()
			+ "/manage/indexPage.jsp");
%>
