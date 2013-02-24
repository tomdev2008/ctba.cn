<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>${board.boardName }&nbsp;-&nbsp;<bean:message key="nav.indexPage" /><</title>
        <script type="text/javascript">
            //==============
            // ie6 实现 css2 伪类
            //==============
            function hover() {
                $("ul.opt li").mouseover(function(){
                    $(this).addClass("hover");
                }).mouseout(function(){
                    $(this).removeClass("hover");
                });
            }
            if (window.attachEvent) window.attachEvent("onload", hover);
        </script>
    </head>
    <body>
        <c:import url="./head.jsp"></c:import>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage"/></a>&nbsp;&gt;&nbsp;<%--${parentBoard.boardName}&nbsp;>&nbsp;--%><a href="board/${board.boardId }">${board.boardName}</a>
                    </div>
                </div>
                <jsp:include page="_boardInfo.jsp"></jsp:include>
                <div class="left_block top_blank">
                    <logic:equal value="prime" name="type">
                        <ul id="tabs" class="graylink">
                            <li class="normal" id="t_content"><a href="board/${board.boardId }"><bean:message key="page.board.tab.topicList"/></a></li><li class="current"><bean:message key="page.board.tab.primeList"/></li>
                        </ul>
                        <logic:equal value="true" name="logined">
                            <div class="lt_post graylink">
                                <a href="newtopic/${board.boardId }"><bean:message key="page.board.topic.post"/></a>
                            </div>
                        </logic:equal>
                    </logic:equal>
                    <logic:notEqual value="prime" name="type">
                        <ul id="tabs" class="graylink">
                            <li class="current" id="t_content"><bean:message key="page.board.tab.topicList"/></li><li class="normal" id="t_content_hot"><a href="b.action?type=prime&bid=${board.boardId }"><bean:message key="page.board.tab.primeList"/></a></li>
                        </ul>
                        <logic:equal value="true" name="logined">
                            <div class="lt_post graylink">
                                <a href="newtopic/${board.boardId }"><bean:message key="page.board.topic.post"/></a>
                            </div>
                        </logic:equal>
                    </logic:notEqual>
                    <div class="top_blank">
                        <logic:notEmpty name="models">
                            <pg:pager items="${count}" url="b.action" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                                <pg:param name="method" />
                                <pg:param name="type" />
                                <pg:param name="bid" />
                                <pg:index>
                                    <div class="pageindex_list">
                                        <jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>&nbsp;<a href="rss.shtml?type=bbs&bid=${board.boardId }" target="_blank"><img src="images/icons/feed.png" title="<bean:message key="page.board.rss"/>" /></a>
                                    </div>
                                </pg:index>
                                <div id="tab_group">
                                    <logic:iterate id="map" indexId="index" name="models">
                                        <ul class="column_5c clearfix <c:if test="${index % 2 == 1}">yellow_bg</c:if>">
                                            <li class="col3-5 center ${map.tagStyleClass }"></li>
                                            <li class="col63-5">
                                                <a href="topic/${map.topic.topicId }" title="${map.topic.topicTitle}">
                                                    <c:if test="${map.topic.topicTop ==1 }">
                                                        <span class="topic_top" >
                                                            &nbsp;<community:limit value="${map.topic.topicTitle}" maxlength="24" />
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${map.topic.topicPrime ==1 && map.topic.topicTop !=1 }">
                                                        <span class="topic_prime" >
                                                            &nbsp;<community:limit value="${map.topic.topicTitle}" maxlength="24" />
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${map.topic.topicTop !=1&& map.topic.topicPrime !=1 }">
                                                        &nbsp;<community:limit value="${map.topic.topicTitle}" maxlength="26" />
                                                    </c:if>
                                                </a>
                                                <span class="font_small color_orange">&nbsp;${map.topic.topicReNum }&nbsp;replies</span>&nbsp;<span class="font_small color_gray">...&nbsp;${map.topic.topicHits}&nbsp;hits</span>
                                            </li>
                                            <li class="col17 list_sp2">
                                                <a href="<ctba:wrapuser user="${map.user}" linkonly="true"/>"> <img src="<community:img value="${map.user.userFace}" type="mini"/>" width="16" class="userFace_border" align="absmiddle" /></a>&nbsp;&nbsp;<a href="userpage/<community:url value="${map.topic.topicAuthor}"/>"class="userName">${ map.user.userName }</a>
                                            </li>
                                            <li class="col14 font_small color_gray">
                                                <community:date value="${map.topic.topicTime }" start="2" limit="16" />
                                            </li>
                                            <li class="col2">
                                                <logic:equal value="true" name="manager">
                                                    <ul class="opt">
                                                        <li>
                                                            <a href="javascript:void(0);"><img src="images/icons/options.gif" /></a>
                                                            <ul>
                                                                <li><a href="topicState.action?method=top&topicId=${map.topic.topicId }"><bean:message key="page.board.manage.top"/></a></li>
                                                                <li><a href="topicState.action?method=primer&topicId=${map.topic.topicId }"><bean:message key="page.board.manage.prime"/></a></li>
                                                                <li><a href="topicState.action?method=re&topicId=${map.topic.topicId }"><bean:message key="page.board.manage.forbidden"/></a></li>
                                                                <li><a href="board.shtml?method=transeTopic&tid=${map.topic.topicId }"><bean:message key="page.board.manage.translate"/></a></li>
                                                                <li><a href="topic.action?method=form&tid=${map.topic.topicId }"><bean:message key="page.board.manage.edit"/></a></li>
                                                                <li><a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }topic.action?method=delete&tid=${map.topic.topicId }');return false;"><bean:message key="page.board.manage.delete"/></a></li>
                                                            </ul>
                                                        </li>
                                                    </ul>
                                                </logic:equal>
                                            </li>
                                        </ul>
                                    </logic:iterate>
                                </div>
                                <pg:index>
                                    <div class="pageindex_list_bottom clearfix">
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