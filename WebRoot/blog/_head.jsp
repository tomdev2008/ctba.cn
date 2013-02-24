<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
    request.setAttribute("context", request.getContextPath());
    pageContext.setAttribute("context", request.getContextPath());
    String basePath ="";
    if (request.getServerName().contains("test.ctba.cn") ) {
      basePath  = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }else{
      //如果不是本地调试的话，不用加端口信息
      basePath  = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
    } request.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="description" content="${blogModel.description}">
<meta name="keyword" content="${blogModel.keyword}">
<link rel="Shortcut Icon" href="common/favicon.ico">
<link rel="alternate" type="application/rss+xml" title="${blogModel.name}" href="rss.shtml?type=blog&cid=${categoryModel.id}&bgid=${blogModel.id }">
<link rel="stylesheet" type="text/css" href="blogThemes/${blogModel.theme }/css.css?ct=090320" media="all">
<link rel="stylesheet" type="text/css" href="${context}/theme/jquery/boxy.css" media="all">
<script src="${context}/javascript/jquery-1.4.2.min.js"></script>
<script src="${context}/javascript/jquery.lazyload.min.js"></script>
<script src="${context}/javascript/jquery.cycle.all.pack.js"></script>
<script src="${context}/javascript/jquery.boxy.min.js"></script>
<script>$(function(){$('.postBody img').lazyload({threshold:200,placeholder:'images/grey.gif'});$('.boxy').boxy();if($.browser.msie&&$.browser.version==6.0){var max_width=parseInt($('.postBody img').css('max-width'));$('.postBody img').each(function(){if($(this).width()>max_width)$(this).width(max_width);});}if(document.location.pathname==='/shimano/blog/'){document.location.href='http://www.ctba.cn/blog/8';}});</script>
