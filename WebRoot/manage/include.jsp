<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="org.net9.core.constant.*"%>
<%@ page import="org.net9.core.util.*"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("UTF-8");
	request.setAttribute("context", request.getContextPath());
	pageContext.setAttribute("context", request.getContextPath());
%>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
