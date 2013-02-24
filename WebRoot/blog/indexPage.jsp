<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <jsp:include page="../_metadataBlock.jsp"><jsp:param name="currPage" value="blog"/></jsp:include>
        <title><bean:message key="blog.indexPage.main"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="menu.blog.navigate" />
                </div>
                <div class="left_block clearfix">
                    <div class="board_sep">
                        <div class="title">
                            <img src="images/movies.png" align="absmiddle" />&nbsp;<bean:message key="blog.indexPage.banner"/>
                        </div>
                        <ul class="blog_users_large clearfix">
                            <c:if test="${not empty hotBlogs}">
                                <c:forEach items="${hotBlogs}" var="model" varStatus="index">
                                    <li><a class="blog_border_large" href="<ctba:wrapblog blog="${model.blog}" linkonly="true" user="${model.author}"/>"><img src="<com:img type="default" value="${model.blog.image}" />" title="${model.blog.author}" width="120" height="120" /></a><br /><span class="blog_name color_darkgray">${model.blog.name}</span></li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <div class="left_block clearfix">
                    <ul id="tabs" class="graylink">
                        <li class="current"><a name="tab_content"><bean:message key="blog.newEntry" /></a></li><li><a name="tab_content_hot"><bean:message key="blog.hot" /></a></li><li><a name="tab_content_commented">最受关注</a></li>
                    </ul>
                    <div id="tab_content" class="tabswrap">
                        <c:if test="${not empty newEntriesMapList}">
                            <c:forEach items="${newEntriesMapList}" var="map" varStatus="index">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index.index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="${context }/userpage/<g:url value="${map.entry.author}" />" title="${map.entry.author}"><img src="<com:img type="mini" value="${map.author.userFace}"/>" align="absmiddle" width="16" height="16" class="userFace_border" alt="${map.entry.author}" /></a>&nbsp;&nbsp;<a href="${context }/userpage/<g:url value="${map.entry.author}" />">${map.entry.author}</a>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="blog/entry/${map.entry.id}" title="${map.entry.title}"  ><g:limit value="${map.entry.title}" maxlength="26" /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${map.entry.commentCnt }/${map.entry.hits }</span>
                                    </li>
                                    <li class="col20" style="overflow:hidden">
                                        <ctba:wrapblog blog="${map.entry.blogBlog}" user="${map.author}" maxlength="12"/>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${map.entry.date }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div id="tab_content_hot" class="tabswrap">
                        <c:if test="${not empty hotEntriesMapList}">
                            <c:forEach items="${hotEntriesMapList}" var="map" varStatus="index">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index.index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="${context }/userpage/<g:url value="${map.entry.author}" />" title="${map.entry.author}"><img src="<com:img type="mini" value="${map.author.userFace}"/>" align="absmiddle" width="16" height="16" class="userFace_border" alt="${map.entry.author}" /></a>&nbsp;&nbsp;<a href="${context }/userpage/<g:url value="${map.entry.author}" />">${map.entry.author}</a>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="blog/entry/${map.entry.id}" title="${map.entry.title}"  ><g:limit value="${map.entry.title}" maxlength="26" /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${map.entry.commentCnt }/${map.entry.hits }</span>
                                    </li>
                                    <li class="col20" style="overflow:hidden">
                                        <ctba:wrapblog blog="${map.entry.blogBlog}" user="${map.author}" maxlength="12"/>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${map.entry.date }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div id="tab_content_commented" class="tabswrap">
                        <c:if test="${not empty mostCommentedEntriesMapList}">
                            <c:forEach items="${mostCommentedEntriesMapList}" var="map" varStatus="index">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index.index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="${context }/userpage/<g:url value="${map.entry.author}" />" title="${map.entry.author}"><img src="<com:img type="mini" value="${map.author.userFace}"/>" align="absmiddle" width="16" height="16" class="userFace_border" alt="${map.entry.author}" /></a>&nbsp;&nbsp;<a href="${context }/userpage/<g:url value="${map.entry.author}" />">${map.entry.author}</a>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="blog/entry/${map.entry.id}" title="${map.entry.title}"  ><g:limit value="${map.entry.title}" maxlength="26" /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${map.entry.commentCnt }/${map.entry.hits }</span>
                                    </li>
                                    <li class="col20" style="overflow:hidden">
                                        <ctba:wrapblog blog="${map.entry.blogBlog}" user="${map.author}" maxlength="12"/>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${map.entry.date }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>
                <div class="state">
                    <div class="titles" onclick="$('#hotBlogs').toggle('slow');"><bean:message key="blog.active" /></div>
                    <ul class="linkbar" id="hotBlogs">
                        <c:if test="${not empty activeBlogs}">
                            <c:forEach items="${activeBlogs}" var="model" varStatus="index">
                                <li>
                                    <img class="photo_link" src="<com:img type="sized" width="16" value="${model.blog.image}" />" align="absmiddle" width="16" height="16" title="${model.blog.author}" />&nbsp;<ctba:wrapblog blog="${model.blog}" user="${model.author}" />
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <div class="state">
                    <div class="titles" onclick="$('#radomGroups').toggle('slow');"><bean:message key="blog.establish.latest" /></div>
                    <div id="radomGroups">
                        <ul class="linkbar">
                            <c:if test="${not empty newBlogs}">
                                <c:forEach items="${newBlogs}" var="model" varStatus="index">
                                    <li>
                                        <img class="photo_link" src="<com:img type="sized" width="16" value="${model.blog.image}" />" align="absmiddle" width="16" height="16" />&nbsp;<ctba:wrapblog blog="${model.blog}" user="${model.author}" />
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <jsp:include page="../_adBlockRight.jsp"></jsp:include>
                <div class="state">
                    <ul class="infobar">
                        <li class="icons_group"><bean:message key="blog.number.total" />&nbsp;<span class="color_orange">${blogCnt }</span></li>
                        <li class="icons_emoticon_happy"><bean:message key="page.blog.indexPage.entryCnt"/>&nbsp;<span class="color_orange">${entryCnt }</span></li>
                        <li class="icons_feed"><a href="rss.shtml?type=blog" target="_blank"><bean:message key="rss.feed" /></a></li>
                    </ul>
                    <hr size="1" color="#eeeeee" />
                    <jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
                </div>
            </div>
        </div>
        <jsp:include page="bottom.jsp" flush="true" />
    </body>
</html>