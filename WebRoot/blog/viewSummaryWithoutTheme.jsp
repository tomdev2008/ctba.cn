<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <script type="text/javascript" src="${context}/javascript/jquery.cycle.all.pack.js"></script>
        <meta name="keyword" content="<bean:message key="page.blog.common.keyword"/>,${blogModel.keyword }" />
        <title> <bean:message key="page.blog.summary.title"/>&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;<a href="bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;&gt;&nbsp;<a title="${blogModel.description}" href="blog/${blogModel.id}">${blogModel.name}</a>&nbsp;&gt;&nbsp;<bean:message key="page.blog.summary.title"/>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange">
                            <b><bean:message key="page.blog.summary.title"/></b>
                        </h2>
                    </div>
                    <div class="mid_block top_blank_wide clearfix">
                        <table width="100%" cellpadding="3" cellspacing="1" class="blog_item_list">
                            <c:if test="${not empty summaryList}">
                                <c:forEach items="${summaryList}" var="model" varStatus="status">
                                    <tr>
                                        <td width="6%" align="center">
                                            <img src="images/icon_doc.png" align="absmiddle" />
                                        </td>
                                        <td width="94%" <c:choose><c:when test="${status.count%2==1}"> class="lightgray_bg" </c:when></c:choose>>
                                            &nbsp;
                                            <h2>
                                                <a href="blog/${blogModel.id }/summary/${model.month}">${model.month}</a>&nbsp;
                                            </h2>
                                            <span class="color_gray">
                                                [&nbsp;${model.cnt }&nbsp;]
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="./_rightWithoutTheme.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>