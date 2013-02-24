<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <script type="text/javascript" src="${context}/javascript/jquery.cycle.all.pack.js"></script>
        <meta name="keyword" content="<bean:message key="page.blog.common.keyword"/>,${blogModel.keyword }" />
        <title>
            <c:if test="${not empty categoryModel}">
            ${categoryModel.name}&nbsp;-&nbsp;
            </c:if>
            <c:if test="${not empty currentMonth}">
                 <bean:message key="page.blog.summary.title"/>&nbsp;${currentMonth }&nbsp;-&nbsp;
            </c:if>
        ${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
        <script type="text/javascript">
            //added by mockee 090114
            function maxWidth(){
                var maxWidth = parseInt($('#dc img').css('max-width'));
                $('#dc img').each(function(){
                    if ($(this).width() > maxWidth)
                        $(this).width(maxWidth);
                });
            }
            if (window.attachEvent) window.attachEvent("onload", maxWidth);
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="blog/"><bean:message key="menu.blog.navigate" /></a>&nbsp;&gt;&nbsp;<a title="${blogModel.description}" href="blog/${blogModel.id}">${blogModel.name}</a><c:if test="${not empty categoryModel}">&nbsp;&gt;&nbsp;<a href="blog/${blogModel.id}/${categoryModel.id}"><com:limit maxlength="14" value="${categoryModel.name}" /></a></c:if><c:if test="${not empty currentMonth}">&nbsp;&gt;&nbsp;<a href="bg.action?bid=${blogModel.id }&method=summary">日志存档</a>&nbsp;&gt;&nbsp;${currentMonth }</c:if>
                    </div>
                </div>
                <c:if test="${not empty entryMapList}">
                    <pg:pager items="${count}" url="bg.action" index="center"
                  maxPageItems="15" maxIndexPages="6"
                  isOffset="<%=false%>"
                  export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                  scope="request">
                        <pg:param name="bid" />
                        <pg:param name="cid" />
                        <pg:param name="method" />
                        <pg:param name="username" />
                        <pg:param name="nick" />
                        <c:forEach items="${entryMapList}" var="model" varStatus="status">
                            <div class="left_block top_blank_wide radius clearfix">
                                <div id="uinfo">
                                    <div id="udetail" class="linkblue">
                                        <span class="color_orange">
                                            <img src="<com:img value="${model.author.userFace}" type="sized" width="80" />" class="group_border" />
                                        </span>
                                        <br />
                                        <span class="color_orange">
                                            <ctba:wrapuser user="${blogAuthor}"/>
                                        </span>
                                    </div>
                                </div>
                                <div id="topicstuff" class="font_small color_orange"></div>
                                <div id="dw">
                                    <div id="dt">
                                        <div id="ctl"></div>
                                        <div id="tl"></div>
                                        <div id="ctr"></div>
                                    </div>
                                    <div id="la"></div>
                                    <div id="dt2" class="text_shadow graylink">
                                        <h1 style="display:inline">
                                            <strong>${model.entry.title }</strong>
                                        </h1>
                                        <a href="blog/entry/${model.entry.id }">
                                            <img src="images/detail.png" align="absmiddle" alt="<bean:message key="page.blog.readMore"/>" />
                                        </a>
                                    </div>
                                    <div id="dc" class="orangelink">
                                        <c:if test="${blogModel.listType ==1}">
                                            <c:if test="${empty model.errorMessage}"><com:limit maxlength="30" value="${model.entry.body }"/></c:if>
                                            <c:if test="${not empty model.errorMessage}">${model.errorMessage}</c:if>
                                        </c:if>
                                        <c:if test="${blogModel.listType ==0}">
                                            <c:if test="${empty model.errorMessage}">${model.entry.body }</c:if>
                                            <c:if test="${not empty model.errorMessage}">${model.errorMessage}</c:if>
                                        </c:if>
                                        <br />
                                    </div>
                                    <div id="db">
                                        <div id="cbl"></div>
                                        <div id="bl"></div>
                                        <div id="cbr"></div>
                                    </div>
                                </div>
                                <div class="line_block right graylink list_sp">
                                    <!--${model.hits }-->
                                    <div id="topicdate" class="font_small color_gray">
                                        Post:&nbsp;${model.entry.date }
                                    </div>
                                    <img src="images/icons/color_swatch.png" align="absmiddle" alt="<bean:message key="page.blog.commentedUsers"/>" />
                                    <c:if test="${not empty model.commentUserList}">
                                        <c:forEach items="${model.commentUserList}" var="u" varStatus="status">
                                            <a href="userpage/<com:url value="${u.userName}"/>">
                                                <img class="userFace_border" align="absmiddle" src="<com:img value="${u.userFace}" type="mini"/>" width="16" height="16" alt="${u.userName }" title="${u.userName }" />
                                            </a>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                        <pg:index>
                            <div class="left_block">
                                <div class="pageindex_bottom">
                                    <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                </div>
                            </div>
                        </pg:index>
                    </pg:pager>
                </c:if>
            </div>
            <div id="area_right">
                <jsp:include page="./_rightWithoutTheme.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>