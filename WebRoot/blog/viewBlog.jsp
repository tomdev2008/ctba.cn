<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html>
<html>
    <head>
        <title><c:if test="${not empty categoryModel}">${categoryModel.name}&nbsp;-&nbsp;</c:if><c:if test="${not empty currentMonth}"><bean:message key="page.blog.summary.title"/>&nbsp;${currentMonth }&nbsp;-&nbsp;</c:if>${blogModel.name}</title>
        <jsp:include page="_head.jsp"></jsp:include>
    </head>
    <body>
        <div id="index">
            <div id="container">
                <div id="header">
                    <div class="header-top">
                        &nbsp;
                    </div>
                    <h1 class="blogName">
                        <ctba:wrapblog blog="${blogModel}" user="${blogAuthor}"/>
                    </h1>
                    <div class="description">
                        <com:topic value="${blogModel.description}" />
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty categoryModel}">
                    <div class="blog_category">
                        {&nbsp;<a href="blog/${blogModel.id}/${categoryModel.id}">${categoryModel.name}</a>&nbsp;}
                    </div>
                    </c:if>
                    <c:if test="${not empty currentMonth}">
                    <div class="blog_category">
                        {&nbsp;${currentMonth }&nbsp;}
                    </div>
                    </c:if>
                </div>
                <div id="innerContainer">
                    <div class="innerTop"></div>
                    <div id="outerContent">
                        <div id="content" class="content">
                            <div class="contentTop" id="Top"></div>
                            <ul id="posts">
                                <div class="postsTop"></div>
                                    <c:if test="${not empty entryMapList}">
                                    <pg:pager items="${count}" url="bg.action" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                                    <pg:param name="bid" />
                                    <pg:param name="cid" />
                                    <pg:param name="method" />
                                    <pg:param name="username" />
                                    <pg:param name="nick" />
                                    <c:forEach items="${entryMapList}" var="model" varStatus="status">
                                    <li>
                                        <div class="postHeader">
                                            <h3>
                                                ${model.entry.date }
                                            </h3>
                                            <h2>
                                                <a href="blog/entry/${model.entry.id }">${model.entry.title}</a>
                                            </h2>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="postBody">
                                            <c:if test="${blogModel.listType ==1}">
                                            <c:if test="${empty model.errorMessage}"><com:limit maxlength="30" value="${model.entry.body }"/></c:if>
                                            <c:if test="${not empty model.errorMessage}">${model.errorMessage}</c:if>
                                            </c:if>
                                            <c:if test="${blogModel.listType ==0}">
                                            <c:if test="${empty model.errorMessage}">${model.entry.body }</c:if>
                                            <c:if test="${not empty model.errorMessage}">${model.errorMessage}</c:if>
                                            </c:if>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="postFooter">
                                            <c:if test="${not empty model.commentUserList}">
                                            <div class="tags">
                                                <img src="images/icons/color_swatch.png" align="absmiddle" alt="<bean:message key="page.blog.commentedUsers"/>" />
                                                <c:forEach items="${model.commentUserList}" var="u" varStatus="status">
                                                <a href="<ctba:wrapuser user="${u}" linkonly="true"/>">
                                                    <img class="userFace_border" align="absmiddle" src="<com:img value="${u.userFace}" type="mini"/>" width="16" height="16" alt="${u.userName }" title="${u.userName }" />
                                                </a>&nbsp;
                                                </c:forEach>
                                            </div>
                                            </c:if>
                                            <div class="menubar">
                                                <span class="author"><a href="userpage/<com:url value="${model.entry.author}"/>">${model.entry.author}</a></span>&nbsp;发表于&nbsp;<span class="time">${model.entry.date}</span>&nbsp;|&nbsp;<a href="blog/entry/${model.entry.id }">阅读全文</a>&nbsp;|&nbsp;浏览&nbsp;<span class="time">${model.entry.hits }</span>
                                            </div>
                                        </div>
                                    </li>
                                    </c:forEach>
                                    <pg:index>
                                    <div class="pageNavi">
                                        <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                    </div>
                                    </pg:index>
                                    </pg:pager>
                                    </c:if>
                                <div class="postsBottom"></div>
                            </ul>
                            <div id="notes"></div>
                            <div class="contentBottom"></div>
                        </div>
                    </div>
                    <div id="outerSidebar">
                        <jsp:include flush="true" page="_right.jsp"></jsp:include>
                    </div>
                    <div class="innerBottom"></div>
                    <div class="clear"></div>
                    <div id="footer">
                        <div class="copyright">
                            Copyright © 2002-2010
                            <a href="http://ctba.cn">CTBA.CN</a>, All Rights Reserved.&nbsp;<a href="http://www.miibeian.gov.cn/">京ICP备05063221号</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="_bottom.jsp"></jsp:include>
    </body>
</html>
