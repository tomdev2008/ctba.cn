<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <jsp:include page="_head.jsp"></jsp:include>
        <title> <bean:message key="page.blog.summary.title"/>&nbsp;-&nbsp;${blogModel.name}</title>
    </head>
    <body>
        <div id="index">
            <div id="container">
                <div id="header">
                    <h1 class="blogName">
                        <ctba:wrapblog blog="${blogModel}" user="${blogAuthor}"/>
                    </h1>
                    <div class="description">
                        ${blogModel.description}
                    </div>
                    <div class="clear"></div>
                </div>
                <div id="innerContainer">
                    <div class="innerTop"></div>
                    <div id="outerContent">
                        <div id="content" class="content">
                            <h2>日志存档</h2>
                            <ul class="blog_summary clearfix">
                                <c:if test="${not empty summaryList}">
                                    <c:forEach items="${summaryList}" var="model" varStatus="status">
                                        <li>
                                            <a href="blog/${blogModel.id }/summary/${model.month}">${model.month}</a>&nbsp;&nbsp;<span class="font_mid">[${model.cnt }]</span>
                                        </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                    <div id="outerSidebar">
                        <jsp:include flush="true" page="_right.jsp"></jsp:include>
                    </div>
                    <div class="innerBottom"></div>
                    <div class="clear"></div>
                    <div id="footer">
                        <div class="copyright">
                            Copyright © 2002-2009
                            <a href="http://ctba.cn">CTBA.CN</a>, All Rights Reserved.&nbsp;<a href="http://www.miibeian.gov.cn/">京ICP备05063221号</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="_bottom.jsp"></jsp:include>
    </body>
</html>