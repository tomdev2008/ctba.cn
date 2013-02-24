<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="./common/error_page.jsp"%>
<%@include file="./common/taglibs.jsp"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="common/include.jsp"></c:import>
        <jsp:include page="_metadataBlock.jsp"><jsp:param name="currPage" value=""/></jsp:include>
        <title><bean:message key="sys.name"/></title>
        <script>
            $(function () {
                // ad banner slider
                $('#pic-slider').flashSlider();

                // news digg
                var dig = function (id) {
                    $.get('${context }/news.shtml',
                        { method: 'good', nid: id, loadType: 'ajax' },
                        function (data) {
                            if(data.indexOf('login') >= 0) {
                                J2bbCommon.showLoginForm();
                            } else { $('#group_digg_' + id).html(data); }
                        }
                    );
                }
            });
        </script>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper" class="clearfix">
            <div id="area_left">
                <jsp:include flush="true" page="_picBlock.jsp"></jsp:include>
                <c:if test="${not empty newsList}">
                    <div class="left_block clearfix">
                        <div class="group_digg gd_news">
                            <div class="diggnum" id="group_digg_${newsList[newsIndex].entry.id }">${newsList[newsIndex].entry.hitGood }</div>
                            <span class="font_small color_gray">diggs</span>
                            <div class="diggit" title="挖!" onclick="dig('${newsList[newsIndex].entry.id }');">挖它</div>
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
                                <h2 class="fleft text_shadow">
                                    <strong>
                                        <c:if test="${not empty newsList[newsIndex].entry.fakeUrl }">
                                            <a href="news/${newsList[newsIndex].entry.fakeUrl }">${newsList[newsIndex].entry.title }</a>
                                        </c:if>
                                        <c:if test="${empty newsList[newsIndex].entry.fakeUrl }">
                                            <a href="news/${newsList[newsIndex].entry.id }">${newsList[newsIndex].entry.title }</a>
                                        </c:if>
                                    </strong>
                                </h2>
                                <span class="news_rss"><a class="a_rss" title="订阅扯谈新闻" href="rss.shtml?type=news">订阅</a></span>
                            </div>
                            <div id="dc2" class="orangelink">
                                ${newsList[newsIndex].entry.subtitle }&nbsp;&nbsp;
                                <c:if test="${not empty newsList[newsIndex].entry.fakeUrl }">
                                    <a href="news/${newsList[newsIndex].entry.fakeUrl }">继续阅读...</a>
                                </c:if>
                                <c:if test="${empty newsList[newsIndex].entry.fakeUrl }">
                                    <a href="news/${newsList[newsIndex].entry.id }">继续阅读...</a>
                                </c:if>
                            </div>
                            <div id="db">
                                <div id="cbl"></div>
                                <div id="bl"></div>
                                <div id="cbr"></div>
                            </div>
                        </div>
                        <div class="line_block sublb color_gray clearfix">
                            <ul class="lo fright">
                                <li class="icons_user">投递：<ctba:wrapuser user="${newsList[newsIndex].user}" /> &nbsp;|&nbsp;</li>
                                <li class="icons_calendar_view_day">发布时间：<community:formatTime time="${newsList[newsIndex].entry.createTime }" />&nbsp;|&nbsp;</li>
                                <li class="icons_note">阅读：${newsList[newsIndex].entry.hits }&nbsp;|&nbsp;</li>
                                <li class="icons_comment">评论：${newsList[newsIndex].commentCnt }</li>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <div class="left_block">
                    <ul id="tabs" class="graylink">
                        <li class="current"><a name="tab_group_latest"><bean:message key="common.post.update" /></a></li><li><a name="tab_group_new"><bean:message key="common.post.new" /></a></li><li><a name="tab_group_hot"><bean:message key="common.post.hot" /></a></li><li><a name="tab_bbs_latest"><bean:message key="common.bbs.lastupdated" /></a></li><li><a name="tab_bbs_new"><bean:message key="common.bbs.latest" /></a></li><li><a name="tab_blog_new">扯谈最新日志</a></li>
                    </ul>
                    <div id="tab_group_latest" class="tabswrap">
                        <c:if test="${not empty updatedGroupTopicsMap}">
                            <c:forEach items="${updatedGroupTopicsMap}" var="topicMap" varStatus="status">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${status.index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="<ctba:wrapuser user="${topicMap.user}" linkonly="true"/>"><img src="<community:img type="sized" value="${topicMap.user.userFace}" width="32" />" width="16" height="16" class="userFace_border" align="absmiddle" alt="${topicMap.topic.groupModel.name}" title="${topicMap.topic.groupModel.name}" /></a>&nbsp;&nbsp;<ctba:wrapuser user="${topicMap.user}"/>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="<c:url value="/group/topic/${topicMap.topic.id}"/>" title="${topicMap.topic.title}"><g:limit value="${topicMap.topic.title}" maxlength="24"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topicMap.topic.replyCnt }/${topicMap.topic.hits }</span>
                                    </li>
                                    <li class="col20">
                                        <ctba:wrapgroup group="${topicMap.topic.groupModel}" maxlength="13"/>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${topicMap.topic.updateTime }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div id="tab_group_new" class="tabswrap hide">
                        <%--<div class="list_sp_gg">
                            <div class="fleft"><img src="images/icons/link/google.gif" width="16" height="16" class="userFace_border" /></div>
                            <div class="googad"><script type="text/javascript">google_ad_client = "pub-3911382285138100";google_ad_slot = "7706689736";google_ad_width = 468;google_ad_height = 15;</script><script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script></div>
                        </div>--%>
                        <c:if test="${not empty newTopicsMap}">
                            <c:forEach items="${newTopicsMap}" var="topicMap" varStatus="status">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${status.index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="<ctba:wrapuser user="${topicMap.user}" linkonly="true"/>"><img src="<community:img type="sized" value="${topicMap.user.userFace}" width="32" />" width="16" height="16" class="userFace_border" align="absmiddle" alt="${topicMap.topic.author }" title="${topicMap.topic.author }" /></a>&nbsp;&nbsp;<ctba:wrapuser user="${topicMap.user}"/>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="<c:url value="/group/topic/${topicMap.topic.id}"/>" title="${topicMap.topic.title}"><g:limit value="${topicMap.topic.title}" maxlength="24"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topicMap.topic.replyCnt }/${topicMap.topic.hits }</span>
                                    </li>
                                    <li class="col20" style="overflow:hidden">
                                        <ctba:wrapgroup group="${topicMap.topic.groupModel}" maxlength="13"/>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${topicMap.topic.createTime }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                        <%--<a href="rss.shtml?b_type=new" class="gt">订阅最新贴</a> --%>
                    </div>
                    <div id="tab_group_hot" class="hide tabswrap">
                        <c:if test="${not empty hotGroupTopicsMap}">
                            <c:forEach items="${hotGroupTopicsMap}" var="topicMap" varStatus="status">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${status.index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="<ctba:wrapuser user="${topicMap.user}" linkonly="true"/>"><img src="<community:img type="sized" value="${topicMap.user.userFace}" width="32" />" width="16" height="16" class="userFace_border" align="absmiddle" alt="${topicMap.topic.groupModel.name}" title="${topicMap.topic.groupModel.name}" /></a>&nbsp;&nbsp;<ctba:wrapuser user="${topicMap.user}"/>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="<c:url value="/group/topic/${topicMap.topic.id}"/>" title="${topicMap.topic.title}"><g:limit value="${topicMap.topic.title}" maxlength="24"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topicMap.topic.replyCnt }/${topicMap.topic.hits }</span>
                                    </li>
                                    <li class="col20" style="overflow:hidden">
                                        <ctba:wrapgroup group="${topicMap.topic.groupModel}" maxlength="13"/>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${topicMap.topic.createTime }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div id="tab_bbs_new" class="hide tabswrap">
                        <logic:notEmpty name="topics">
                            <logic:iterate id="topic" name="topics" scope="request" indexId="index">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="<ctba:wrapuser user="${topic.user}" linkonly="true"/>"><img src="${topic.userFace}" width="16" height="16" class="userFace_border" alt="${topic.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<ctba:wrapuser user="${topic.user}"/>
                                    </li>
                                    <li class="col61" style="overflow:hidden">
                                        <a href="topic/${topic.topic.topicId }" title="${topic.topic.topicTitle}"><community:limit value="${topic.topic.topicTitle}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.topic.topicReNum}/${topic.topic.topicHits}&nbsp;</span>
                                    </li>
                                    <li class="col20 font_mid color_gray">
                                        <g:date value="${topic.topic.topicTime }" start="2" limit="16" />
                                    </li>
                                </ul>
                            </logic:iterate>
                        </logic:notEmpty>
                    </div>
                    <div id="tab_bbs_latest" class="hide tabswrap">
                        <logic:notEmpty name="lastUpdatedTopics">
                            <logic:iterate id="topic" name="lastUpdatedTopics" scope="request" indexId="index">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <a href="<ctba:wrapuser user="${topic.user}" linkonly="true"/>"><img src="${topic.userFace}" width="16" height="16" class="userFace_border" alt="${topic.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<ctba:wrapuser user="${topic.user}"/>
                                    </li>
                                    <li class="col61" style="overflow:hidden">
                                        <a href="topic/${topic.topic.topicId }" title="${topic.topic.topicTitle}"><community:limit value="${topic.topic.topicTitle}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.topic.topicReNum}/${topic.topic.topicHits}</span>
                                    </li>
                                    <li class="col20 font_mid color_gray">
                                        <g:date value="${topic.topic.topicUpdateTime }" start="2" limit="16" />
                                    </li>
                                </ul>
                            </logic:iterate>
                        </logic:notEmpty>
                    </div>
                    <div id="tab_blog_new" class="hide tabswrap">
                        <c:if test="${not empty newEntriesMapList}">
                            <c:forEach items="${newEntriesMapList}" var="map" varStatus="index">
                                <ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index.count % 2 == 0}">lightgray_bg</c:if>">
                                    <li class="col1-5">&nbsp;</li>
                                    <li class="col17-5">
                                        <img src="<community:img type="mini" value="${map.author.userFace}"/>" alt="${map.entry.author}" align="absmiddle" width="16" height="16" class="userFace_border" />&nbsp;&nbsp;<ctba:wrapuser user="${map.author}"/>
                                    </li>
                                    <li class="col49" style="overflow:hidden">
                                        <a href="blog/entry/${map.entry.id}" title="${map.entry.title}" ><g:limit value="${map.entry.title}" maxlength="25"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${map.entry.commentCnt }/${map.entry.hits }</span>
                                    </li>
                                    <li class="col20" style="overflow:hidden">
                                        <a href="blog/${map.entry.blogBlog.id}">${map.entry.blogBlog.name}</a>
                                    </li>
                                    <li class="col12 font_mid color_gray">
                                        <g:date value="${map.entry.date }" start="5" limit="16" />
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <jsp:include page="./ad/_fanqieshuAdBlock.jsp" />
                </div>
                <logic:notEmpty name="stars">
                    <div class="left_block clearfix">
                        <div class="board_sep">
                            <div class="title"><bean:message key="common.user.active" /></div>
                            <ul class="active_users_large">
                                <logic:iterate id="s" name="stars">
                                    <li>
                                        <a href="u/${s.uid }" class="stars_border_large"><img src="<community:img value="${s.face }" type="sized" width="80" />" width="80" height="80" alt="${s.username }" /><span class="user_name">${s.username}</span></a>
                                    </li>
                                </logic:iterate>
                            </ul>
                        </div>
                    </div>
                </logic:notEmpty>
                <div class="left_block">
                    <div class="board_sep">
                        <div class="gtitle">
                            <span class="gtitle_text">CTers 都在做什么？</span>
                            <span class="group_type">
                                <b>|</b>&nbsp;&nbsp;&nbsp;<a href="timeline" class="gt"><bean:message key="rss.feed.title.timeline" /></a>
                               <%--  &nbsp;&nbsp;今天有&nbsp;<span class="color_orange">${eventCntToday }</span>&nbsp;件新鲜事儿
                                --%>
                                &nbsp;&nbsp;<a class="rss" href="rss.shtml">RSS</a>

                            </span>
                        </div>
                    </div>
                    <div class="group_info_list clearfix">
                        <ul class="clearfix">
                            <logic:notEmpty name="userEventList">
                                <logic:iterate id="model" name="userEventList" indexId="index">
                                    <li class="<c:if test="${index % 4 == 2 || index % 4 == 3}"> lightgray_bg <c:if test="${index % 2 == 0}"> radius_left_all </c:if><c:if test="${index % 2 == 1}">radius_right_all</c:if></c:if>">
                                        <div class="group_pic_index"><a href="<ctba:wrapuser user="${model.user}" linkonly="true"/>"><img src="<community:img value="${model.user.userFace}" type="sized" width="32" />" width="32" height="32" /></a></div><div class="group_info_index">${model.eventStr }&nbsp;
                                            <span class="font_small color_gray">
                                                <community:formatTime time="${model.event.updateTime }"/>
                                            </span>
                                        </div>
                                    </li>
                                </logic:iterate>
                            </logic:notEmpty>
                        </ul>
                    </div>
                </div>
                <div class="left_block clearfix">
                    <logic:notEmpty name="boards">
                        <logic:iterate id="model" name="boards">
                            <div class="board_sep">
                                <div id="boardParent_${model.board.boardId }" onclick="$('#boardChild_${model.board.boardId }').toggle('slow');" class="boardParent">
                                    <img src="images/board_title.png" align="absmiddle" />&nbsp;${model.board.boardName}
                                </div>
                                <div id="boardChild_${model.board.boardId }">
                                    <logic:notEmpty name="model" property="subBoards">
                                        <logic:iterate id="subBoard" name="model" property="subBoards" indexId="index">
                                            <ul class="board_wrap clearfix">
                                                <li>
                                                    <div class="fleft board_pic">
                                                        <img src="<community:img type="none" value="${subBoard.board.boardFace }"/>" width="48" />
                                                    </div>
                                                    <div class="fleft board_intro">
                                                        <h2><a href="board/${subBoard.board.boardId}" title="<community:htmlDecode value="${subBoard.board.boardInfo }"/>">${subBoard.board.boardName}</a></h2>
                                                        <p class="color_gray">${subBoard.board.boardInfo }</p>
                                                    </div>
                                                    <div class="fright board_ana font_small">
                                                        [&nbsp;${subBoard.board.boardTopicNum}&nbsp;/&nbsp;${subBoard.board.boardReNum+subBoard.board.boardTopicNum}&nbsp;]
                                                    </div>
                                                </li>
                                            </ul>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </div>
                            </div>
                        </logic:iterate>
                    </logic:notEmpty>
                </div>
            </div>
            <div id="area_right">
                <div id="search">
                    <jsp:include flush="true" page="./_searchBlock.jsp"></jsp:include>
                </div>
                <div class="state">
                    <div class="titles"><bean:message key="news.channel" /></div>
                    <div class="ew">
                        <a class="rss" href="news/">MORE</a>
                    </div>
                    <ul class="digglist2">
                        <logic:notEmpty name="newestNewsList">
                            <logic:iterate id="news" name="newestNewsList" scope="request" indexId="index">
                                <li>
                                    <div class="diggmini">
                                        <b>${news.entry.hitGood }</b>
                                    </div>
                                    <div class="digglt2">
                                        <c:if test="${not empty news.entry.fakeUrl }" >
                                            <a href="news/${news.entry.fakeUrl }" title="${news.entry.title}">${news.entry.title}</a>
                                        </c:if>
                                        <c:if test="${empty news.entry.fakeUrl }">
                                            <a href="news/${news.entry.id }" title="${news.entry.title}">${news.entry.title}</a>
                                        </c:if>
                                        <span class="font_small color_gray">
                                            <community:date value="${news.entry.updateTime }" start="5" limit="16" />
                                        </span>
                                    </div>
                                </li>
                            </logic:iterate>
                        </logic:notEmpty>
                    </ul>
                </div>
                <%--<logic:notEmpty name="hotGroups">
                <div class="state">
                    <div class="titles" onclick="$('#hot_group').toggle('slow');"><bean:message key="group.hot" /></div>
                    <ul class="hot_group clearfix" id="hot_group">
                        <logic:iterate id="group" name="hotGroups">
                        <li><a class="stars_border" href="group/${group.id }"><img src="<community:img value="${group.face }" type="sized" width="80" />" width="48" height="48" /><span class="group_name">${group.name }</span></a></li>
                        </logic:iterate>
                    </ul>
                </div>
                </logic:notEmpty>--%>
                <div class="state">
                    <div class="titles" onclick="$('#hot_group').toggle('slow');">
                        <bean:message key="group.hot" />
                    </div>
                    <div class="ew">
                        <a class="rss" href="rank.action?method=group">MORE</a>
                    </div>
                    <ul class="hot_group clearfix" id="hot_group">
                        <li><a class="stars_border" href="group/pistons"><img src="UploadFile/sized/2009-01-19/NmRmZDU4ZDg3YTQ5OWFmMTM5MDEyZmMx_19_41_02_680_48.jpg" /><span class="group_name">底特律活塞</span></a></li>
                        <li><a class="stars_border" href="group/lakers"><img src="UploadFile/sized/2009-03-03/aW1hZ2Vz_00_28_25_411_48.jpg" /><span class="group_name">洛杉矶湖人</span></a></li>
                        <li><a class="stars_border" href="group/pengpu"><img src="http://static.ctba.cn/files/sized/2009-07-10/Mg==_01_05_41_580_48.gif" /><span class="group_name">彭浦羽球</span></a></li>
                        <li><a class="stars_border" href="group/badminton"><img src="UploadFile/sized/2009-03-01/MTIxMDU4ODUzNl85NTU2NjYwMA==_16_03_32_578_48.jpg" /><span class="group_name">CT羽球党</span></a></li>
                        <li><a class="stars_border" href="group/rumor"><img src="UploadFile/sized/2007-12-10/RFNDMDk1NjI=_23_41_39_836_48.JPG" /><span class="group_name">不准八卦</span></a></li>
                        <li><a class="stars_border" href="group/beauty"><img src="UploadFile/sized/2007-12-10/ZmlyZWZveGJhYmU=_17_13_33_117_48.jpg" /><span class="group_name">活色生香美女图片</span></a></li>
                        <li><a class="stars_border" href="group/comic"><img src="UploadFile/sized/2009-03-14/zrTD/MP7_20_19_36_443_48.jpg" /><span class="group_name">动漫菜刀门</span></a></li>
                        <li><a class="stars_border" href="group/nownow"><img src="UploadFile/sized/2008-11-06/czMwNzU5MTE=_18_59_39_959_48.jpg" /><span class="group_name">nownow女巫加盟店</span></a></li>
                        <li><a class="stars_border" href="group/shiniancafe"><img src="UploadFile/sized/2008-12-30/UGljdHVyZSAy_22_45_46_883_48.png" /><span class="group_name">拾年咖啡</span></a></li>
                        <li><a class="stars_border" href="group/tifafa"><img src="UploadFile/sized/2008-06-11/MDgwNjExY3TQodfpzbfP8Q==_13_19_20_010_48.jpg" /><span class="group_name">Studio Tifafa</span></a></li>
                        <li><a class="stars_border" href="group/sitcoffee"><img src="UploadFile/sized/2009-03-19/c2l0Y29mZm_00_32_32_458_48.jpg" /><span class="group_name">雕刻时光</span></a></li>
                        <li><a class="stars_border" href="group/digg"><img src="UploadFile/sized/2008-06-25/ZGlnZw==_13_36_43_728_48.png" /><span class="group_name">digg & share</span></a></li>
                    </ul>
                </div>
                <c:if test="${not empty newActivityList}">
                    <div class="state">
                        <div class="titles" onclick="$('newActivities').toggle();">最新活动</div>
                        <div class="ew">
                            <a class="rss" href="group/activities/">MORE</a>
                        </div>
                        <div id="newActivities">
                            <ul>
                                <c:forEach items="${newActivityList}" var="model" varStatus="index">
                                    <li class="tlist_wrap clearfix" <c:if test='${status.index % 2 == 1}'>lightgray_bg radius_all</c:if>>
                                        <div class="tlist_icon icons_newticket"></div>
                                        <div class="tlist_text">
                                            <a href="group/activity/${model.id }" class="stars_border">
                                                <community:limit value="${model.title }"  maxlength="50"/>
                                            </a>
                                            <span class="font_small color_gray">
                                                <g:date limit="11" start="2" value="${model.createTime }" />
                                            </span>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <logic:notEmpty name="galleryList">
                    <div class="state">
                        <div class="titles">最新相册</div>
                        <div class="ew">
                            <a class="rss" href="album/">MORE</a>
                        </div>
                        <ul class="user_list clearfix">
                            <logic:iterate id="gallery" name="galleryList">
                                <li class="graylink font_mid">
                                    <span class="userface">
                                        <a href="album/${gallery.id }" style="background:url(<community:img value='${gallery.face }' type='sized' width='48' />) no-repeat center" title="${gallery.name }"></a>
                                    </span>
                                    <span class="username">
                                        <a href="album/${gallery.id }" title="${gallery.name }">${gallery.name }</a>
                                    </span>
                                </li>
                            </logic:iterate>
                        </ul>
                    </div>
                </logic:notEmpty>
                <%--<jsp:include flush="true" page="./_commendBlock.jsp"></jsp:include>--%>
                <c:if test="${not empty voteList}">
                    <div class="state">
                        <div class="titles" onclick="$('voteList').toggle();">热门投票</div>
                        <div class="ew">
                            <a class="rss" href="vote/">MORE</a>
                        </div>
                        <div id="voteList">
                            <ul>
                                <c:forEach items="${voteList}" var="model" varStatus="index">
                                    <li class="tlist_wrap clearfix" <c:if test='${status.index % 2 == 1}'>lightgray_bg radius_all</c:if>>
                                        <div class="tlist_icon icons_newticket"></div>
                                        <div class="tlist_text">
                                            <a href="vote/${model.id}">${model.question}</a>
                                            <%--
                                    <span class="font_small color_gray">
                                        <g:date limit="11" start="2" value="${model.updateTime }" />
                                    </span>
                                            --%>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <div id="ad_alimama">
                    <jsp:include flush="true" page="./_commendBlock.jsp"></jsp:include>
                    <%--<script type="text/javascript">
                    alimama_pid="mm_13387385_1923694_7989901";
                    alimama_type="h";
                    alimama_sizecode="9999";
                    alimama_tkw = {};
                    alimama_tkw.tc_c="3366cc";
                    alimama_tkw.bgc_c="ffffff";
                    alimama_tkw.bdc_c="dddddd";
                    alimama_tkw.pc_c="434343";
                    alimama_tkw.lg_i=1;
                    alimama_tkw.pb_i=0;
                    alimama_tkw.w_i=210;
                    alimama_tkw.h_i=120;
                    </script>
                    <script src="http://a.alimama.cn/inf.js" type="text/javascript"></script>--%>
                </div>
                <div class="state">
                    <ul class="infobar">
                        <li class="icons_user"><bean:message key="common.number.total" />&nbsp;<span class="color_orange">${userCnt }</span></li>
                        <%--
                        <li class="icons_emoticon_happy"><a href="online"><bean:message key="common.user.online" /></a>&nbsp;<span class="color_orange">${onlineCnt }</span></li>
                        <li class="icons_emoticon_smile"><a href="guests"><bean:message key="common.tourist.online" /></a>&nbsp;<span class="color_orange">${onlineCntOfAll }</span></li>
                        --%>
                        <li class="icons_weather_sun">
                            <span class="pointer" onclick="$('#newuser').toggle('slow');"><bean:message key="common.join.latest" /></span>
                        </li>
                        <li id="newuser">
                            <ul>
                                <logic:notEmpty name="newUsers">
                                    <logic:iterate id="s" name="newUsers">
                                        <li><img src="<community:img value="${s.userFace }" type="sized" width="16" />" width="16" height="16" align="absmiddle" class="userFace_border" />&nbsp;<ctba:wrapuser user="${s}" /></li>
                                        </logic:iterate>
                                    </logic:notEmpty>
                            </ul>
                        </li>
                        <li class="icons_feed"><a href="rss.shtml" target="_blank"><bean:message key="rss.feed" /></a></li>
                    </ul>
                    <hr size="1" color="#eeeeee" />
                    <jsp:include page="common/_right_mini_block.jsp"></jsp:include>
                </div>
                <div class="state">
                    <jsp:include flush="true" page="_linkBlock.jsp"></jsp:include>
                </div>
            </div>
        </div>
        <jsp:include page="bottom.jsp" flush="true" />
    </body>
</html>
