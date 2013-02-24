<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.net9.domain.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.net9.common.util.*"%>
<%@ page import="org.net9.core.constant.*"%>
<%@ page import="org.net9.core.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
        String contextPath = request.getContextPath();
        String common = contextPath + "/common";
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("context", request.getContextPath());
        pageContext.setAttribute("context", request.getContextPath());
        request.setAttribute("common", common);
        pageContext.setAttribute("common", common);
        String basePath = "";
        if (request.getServerName().contains("test.ctba.cn")) {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        } else {
            //如果不是本地调试的话，不用加端口信息
            basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        }
        request.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="${context}/theme/${themeDir }/css/general.css" media="all" />
<link rel="stylesheet" type="text/css" href="${context}/theme/jquery/boxy.css" media="all" />
<link rel="Shortcut Icon" href="${common}/favicon.ico" />
<link rel="alternate" type="application/rss+xml" title="<bean:message key="rss.feed.title.group"/>" href="http://ctba.cn/rss.shtml?type=group" />
<link rel="alternate" type="application/rss+xml" title="<bean:message key="rss.feed.title.new"/>" href="http://ctba.cn/rss.shtml?type=bbs" />
<link rel="alternate" type="application/rss+xml" title="<bean:message key="rss.feed.title.best"/>" href="http://ctba.cn/rss.shtml?type=bbs&b_type=best" />
<link rel="alternate" type="application/rss+xml" title="<bean:message key="rss.feed.title.news"/>" href="http://ctba.cn/rss.shtml?type=news" />
<link rel="alternate" type="application/rss+xml" title="<bean:message key="rss.feed.title.timeline"/>" href="http://ctba.cn/rss.shtml" />
<link rel="alternate" type="application/rss+xml" title="<bean:message key="rss.feed.title.shares"/>" href="http://ctba.cn/rss.shtml?type=share" />
<script src="${context}/javascript/jquery-1.4.2.min.js"></script>
<script src="${context}/javascript/jquery.boxy.js"></script>
<script src="${context}/javascript/jquery.flashSlider-1.0.min.js"></script>
