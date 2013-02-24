<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.net9.core.util.*"%>
<%@page import="org.net9.core.constant.WebPageVarConstant;"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("UTF-8");
	request.setAttribute("context", request.getContextPath());
	pageContext.setAttribute("context", request.getContextPath());
	String sysUsername = UserHelper.getuserFromCookie(request);
	request.setAttribute(WebPageVarConstant.USERNAME,sysUsername);
   String basePath ="";
        if (request.getServerName().contains("test.ctba.cn") ) {
          basePath  = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        }else{
          //如果不是本地调试的话，不用加端口信息
          basePath  = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        }
        String pluginContext = "apps/dime_mag";
	request.setAttribute("pluginContext",pluginContext);
	pageContext.setAttribute("pluginContext", pluginContext);
%>
<base href="<%=basePath%>"/>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="Shortcut Icon" href="${common}/favicon.ico" />
<script type="text/javascript" src="${context}/javascript/jquery-1.2.6.min.js"></script>