<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>
            <logic:notEmpty name="category">${category.name}</logic:notEmpty>
            <logic:empty name="category"><bean:message key="menu.news.list" /></logic:empty>
            &nbsp;-&nbsp;<bean:message key="menu.news.navigate" />
        </title>
    </head>
    <body>
        <jsp:include flush="true" page="./head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="news.shtml?method=indexPage"><bean:message key="menu.news.navigate" /></a><logic:notEmpty name="category">&nbsp;>&nbsp;${category.name}</logic:notEmpty>
                        <logic:empty name="category">&nbsp;&gt;&nbsp;<bean:message key="menu.news.list" /></logic:empty>
                    </div>
                    <div class="fright">
                        <logic:equal value="true" name="isUser">
                            <img src="images/icons/page_white_edit.png" align="absmiddle" />&nbsp;<a href="news.shtml?method=post"><bean:message key="page.news.post.user"/></a>
                        </logic:equal>
                        <logic:equal value="false" name="isUser">
                            <img src="images/icons/page_white_edit.png" align="absmiddle" />&nbsp;<a href="javascript:J2bbCommon.showLoginForm();"><bean:message key="page.news.post.guest"/></a>
                        </logic:equal>
                    </div>
                </div>
                <div class="left_block top_blank">
                    <div id="topic_content">
                        <logic:notEmpty name="newsList">
                            <pg:pager items="${count}" url="news.shtml" index="center"
                                      maxPageItems="30" maxIndexPages="6"
                                      isOffset="<%=false%>"
                                      export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                      scope="request">
                                <pg:param name="method" />
                                <pg:param name="cid" />
                                <pg:index>
                                    <div class="pageindex_list">
                                        <jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                                <logic:iterate id="model" indexId="index" name="newsList">
                                    <ul id="digglist">
                                        <li>
                                            <div class="diggmini">
                                                <b>${model.entry.hitGood }</b>
                                            </div>
                                            <div class="digglt orangelink">
                                                <h2 style="display:inline"><a href="news/${model.entry.id}" title="${model.entry.title}"><community:limit value="${model.entry.title}" maxlength="24"  /></a></h2>&nbsp;&nbsp;<img src="images/icons/calendar_view_day.png" align="absmiddle" />&nbsp;<span class="color_gray"><community:date value="${model.entry.updateTime }" start="2" limit="16" /></span>
                                            </div>
                                            <div class="diggpt radius_all">
                                                <bean:message key="page.news.hits"/>:<span class="color_orange">${model.entry.hits }</span>&nbsp;<span class="color_gray">|</span>&nbsp;<bean:message key="page.news.commentCnt"/>:<span class="color_orange">${model.commentCnt }</span>
                                            </div>
                                        </li>
                                    </ul>
                                </logic:iterate>
                                <br class="clear" />
                                <pg:index>
                                    <div class="pageindex_bottom">
                                        <jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                            </pg:pager>
                        </logic:notEmpty>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp" />
    </body>
</html>