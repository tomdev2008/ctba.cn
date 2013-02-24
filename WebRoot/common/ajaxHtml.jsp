<%---------------------------------------
page:
	ajax response page
author: 
	gladstone
date:
	2007-7-15
-----------------------------------------%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
%>${message }