<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="./common/error_page.jsp" %>
<%
    //提供简单的动态转向
	String key = request.getParameter("key");
	if("faq-what-is-ctb-topic-trad".equals(key)){
		response.sendRedirect("http://www.ctba.cn/group/topic/110843");
	}else if("faq-ctb-rule".equals(key)){
		response.sendRedirect("http://www.ctba.cn/group/topic/110842");
	}else if("ymq".equals(key)){
		response.sendRedirect("http://www.ctba.cn/equipment/shop/9");
	}else if("".equals(key)){
		response.sendRedirect("http://www.ctba.cn/");
	}
	
%>
