<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <jsp:include page="../_metadataBlock.jsp"><jsp:param name="currPage" value="news"/></jsp:include>
        <title><bean:message key="menu.news.indexPage" />&nbsp;-&nbsp;<bean:message key="menu.news.navigate" /></title>
        <script>
            function dig(id){
                $.get("${context }/news.shtml",
                { method:"good",nid:id,loadType:"ajax"},
                function(data){
                    if(data.indexOf("login")>=0){
                        J2bbCommon.showLoginForm();
                    }else{
                        $("#group_digg_"+id).html(data);
                    }
                }
            );
            }
        </script>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div class="left_block">
                    <div class="topbar_wrap">
                        <%Integer picIndex = new Random().nextInt(3) + 1;%>
                        <img src="images/ctba/news_logo/<%=picIndex%>.jpg" class="noZoom" />
                    </div>
                </div>
                <div class="left_block clearfix">
                    <ul id="tabs" class="graylink">
                        <li class="current"><bean:message key="page.news.allNews"/></li><c:if test="${not empty cats}"><c:forEach items="${cats}" var="model" varStatus="index"><li class="normal"><a href="news/list/${model.id}"><community:limit maxlength="13" value="${model.name}" /></a></li></c:forEach></c:if>
                    </ul>
                    <c:if test="${not empty newsList}">
                        <pg:pager items="${count}" url="news.shtml" index="center"
                                  maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>"
                                  export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                  scope="request">
                            <pg:param name="method" />
                            <pg:param name="cid" />
                            <pg:index>
                                <div class="top_blank">
                                    <div class="pageindex_list">
                                        <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>&nbsp;&nbsp;<a href="rss.shtml?from_news=true" target="_blank"><img src="images/icons/feed.png" /></a>
                                    </div>
                                </pg:index>
                                <c:forEach items="${newsList}" var="model" varStatus="status">
                                    <ul class="digg">
                                        <li>
                                            <div class="group_digg gd_news">
                                                <div class="diggnum" id="group_digg_${model.entry.id }">${model.entry.hitGood }</div>
                                                <span class="font_small color_gray">diggs</span>
                                                <div class="diggit" title="<bean:message key="page.news.diggIt"/>" onclick="dig('${model.entry.id }');"><bean:message key="page.news.diggIt"/></div>
                                            </div>
                                            <div id="topicstuff" class="font_small color_blue">&nbsp;</div>
                                            <div id="dw">
                                                <div id="dt">
                                                    <div id="ctl"></div>
                                                    <div id="tl"></div>
                                                    <div id="ctr"></div>
                                                </div>
                                                <div id="nla"></div>
                                                <div id="dt2">
                                                    <h2><b>
                                                            <c:if test="${not empty model.entry.fakeUrl }" >
                                                                <a href="news/${model.entry.fakeUrl }"  >${model.entry.title}</a>
                                                            </c:if>
                                                            <c:if test="${empty model.entry.fakeUrl }">
                                                                <a href="news/${model.entry.id }"  >${model.entry.title}</a>
                                                            </c:if>
                                                        </b></h2>
                                                </div>
                                                <div id="dc2" class="orangelink">
                                                    ${model.entry.subtitle }&nbsp;&nbsp;
                                                    <c:if test="${not empty model.entry.fakeUrl }" >
                                                        <a href="news/${model.entry.fakeUrl }"  ><bean:message key="page.news.continueReading"/></a>
                                                    </c:if>
                                                    <c:if test="${empty model.entry.fakeUrl }">
                                                        <a href="news/${model.entry.id }"  ><bean:message key="page.news.continueReading"/></a>
                                                    </c:if>
                                                </div>
                                                <div id="db">
                                                    <div id="cbl"></div>
                                                    <div id="bl"></div>
                                                    <div id="cbr"></div>
                                                </div>
                                            </div>
                                            <div class="line_block color_gray graylink clearfix">
                                                <ul class="lo" style="float:right">
                                                    <li class="icons_user"><c:if test="${not empty model.entry.author}"><bean:message key="page.news.poster"/>:<a href="userpage/<community:url value="${model.entry.author }"/>">${model.entry.author }</a>&nbsp;|&nbsp;</c:if></li>
                                                    <li class="icons_calendar_view_day"><bean:message key="page.news.createTime"/>:<community:formatTime time="${model.entry.updateTime }" />&nbsp;|&nbsp;</li>
                                                    <li class="icons_note"><bean:message key="page.news.hits"/>:${model.entry.hits }&nbsp;|&nbsp;</li>
                                                    <li class="icons_comment"><bean:message key="page.news.commentCnt"/>:${model.commentCnt }</li>
                                                </ul>
                                            </div>
                                        </li>
                                    </ul>
                                </c:forEach>
                                <pg:index>
                                    <div class="pageindex_list_bottom">
                                        <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                                <br class="clear" />
                            </pg:pager>
                        </c:if>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <div id="search">
                    <jsp:include flush="true" page="../_searchBlock.jsp"></jsp:include>
                </div>
                <div class="state">
                    <div class="titles"><bean:message key="page.news.digg"/></div>
                    <ul class="digglist2">
                        <c:if test="${not empty goodNewses}">
                            <c:forEach items="${goodNewses}" var="model" varStatus="index">
                                <li>
                                    <div class="diggmini">
                                        <b>${model.hitGood}</b>
                                    </div>
                                    <div class="digglt2">
                                        <c:if test="${not empty model.fakeUrl }" >
                                            <a href="news/${model.fakeUrl }"  >${model.title}</a>
                                        </c:if>
                                        <c:if test="${empty model.fakeUrl }">
                                            <a href="news/${model.id }"  >${model.title}</a>
                                        </c:if>
                                        <span class="font_small color_gray">
                                            <community:date value="${model.updateTime }" start="5" limit="16" />
                                        </span>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <div class="state clearfix">
                    <div class="titles"><bean:message key="page.news.newestComment"/></div>
                    <ul>
                        <c:if test="${not empty newComments}">
                            <c:forEach items="${newComments}" var="model" varStatus="index">
                                <li class="tlist_wrap <c:if test="${index.index % 2 == 1}">lightgray_bg radius_all</c:if> clearfix">
                                    <div class="tlist_icon icons_newticket"></div>
                                    <div class="tlist_text">
                                        <a href="news/${model.newsId }"><community:limit maxlength="24" value="${model.content}" ignoreUbb="true"/></a>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <div class="state">
                    <div class="titles"><bean:message key="page.news.hotNews"/></div>
                    <ul class="digglist2">
                        <c:if test="${not empty hotNewses}">
                            <c:forEach items="${hotNewses}" var="model" varStatus="index">
                                <li>
                                    <div class="diggmini">
                                        <b>${model.hitGood}</b>
                                    </div>
                                    <div class="digglt2">
                                        <c:if test="${not empty model.fakeUrl }" >
                                            <a href="news/${model.fakeUrl }">${model.title}</a>
                                        </c:if>
                                        <c:if test="${empty model.fakeUrl }">
                                            <a href="news/${model.id }">${model.title}</a>
                                        </c:if>
                                        <span class="font_small color_gray">
                                            <community:date value="${model.updateTime }" start="5" limit="16" />
                                        </span>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <jsp:include page="../_adBlockRight.jsp"></jsp:include>
                <div class="state">
                    <ul class="infobar">
                        <li class="icons_feed">
                            <a href="rss.shtml" target="_blank"><bean:message key="rss.feed"/></a>
                        </li>
                    </ul>
                    <hr size="1" color="#eeeeee" />
                    <jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
                </div>
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>