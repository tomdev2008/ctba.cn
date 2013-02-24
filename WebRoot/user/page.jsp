<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.user.page.title" arg0="${user.userName }"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <!--[if lte IE 6]>
        <style type="text/css">
            #reply_content img {
                zoom: expression( function(elm) {
                    if (elm.width>600) {
                        var oldVW = elm.width;
                        elm.width = 600;
                        elm.height = elm.height*(600/oldVW);
                    }
                        elm.style.zoom = '1';
                    }(this));
            }
        </style>
        <![endif]-->
        <script type="text/javascript">
            $(function(){
                var currName = $('#tabs_gray li a').attr('name');
                $('#tabs_gray li').bind('click', function () {
                    var clickName = $('a', this).attr('name');
                    if(clickName != currName) {
                        $('#tabs_gray li').removeClass();
                        $(this).addClass('current');
                        $('#' + clickName).show();
                        $('#' + currName).hide();
                    }
                    currName = clickName;
                });
                var checkNoteForm = function () {
                    J2bbCommon.removeformError("topicContent");
                    var content=$("#topicContent").val();
                    if(content.length>1000||content.length<1){
                        J2bbCommon.formError("topicContent", "<bean:message key='page.user.page.error.comment' />");
                        return false;
                    }
                    $("#submitButton").val("<bean:message key='page.common.button.submitting' />");
                    $("#submitButton").attr("disabled","disabled");
                    return true;
                };
                var re = function (id, username) {
                    $("#para_reply_to").val(username);
                    $("#parentNote").val(id);
                    $("#topicContent").val("@"+username+"\n");
                    $("#topicContent").focus();
                };
                //==============
                // ie6 实现 css2 伪类
                //==============
                if($.browser.msie && $.browser.version == 6.0) {
                    $("ul.opt2 li").hover(
                    function () {
                        $(this).addClass('hover');
                    },
                    function () {
                        $(this).removeClass('hover');
                    }
                );
                };
            });
        </script>
    </head>
    <body>
        <logic:equal value="true" name="isSelf">
            <jsp:include flush="true" page="../head.jsp">
                <jsp:param name="submenu" value="user" />
            </jsp:include>
        </logic:equal>
        <logic:equal value="false" name="isSelf">
            <jsp:include flush="true" page="../head.jsp" />
        </logic:equal>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.user.page.title" arg0="${user.userName }"/>
                    </div>
                    <div class="fright">
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <div class="side_block radius_all side_face">
                        <logic:notEmpty name="user" property="userFace">
                            <img src="<comm:img value='${user.userFace }' type='default' />" alt="${userName }" />
                        </logic:notEmpty>
                        <logic:empty name="user" property="userFace">
                            <img src='${context }/images/nobody.png' width="120" />
                        </logic:empty>
                        <logic:equal value="true" name="logined">
                            <div class="side_line center">
                                <logic:equal value="true" name="isSelf">
                                    <a href="setting"><bean:message key="page.user.page.setting" /></a>
                                </logic:equal>
                                <logic:equal value="false" name="isSelf">
                                    <logic:equal value="false" name="isFriend">
                                        <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.user.page.addAsFriend.conform" arg0="${user.userName }"/>','${basePath }friend.action?method=save&username=<comm:url value="${user.userName }"/>');return false;"><bean:message key="page.user.page.addAsFriend"/></a>
                                    </logic:equal>
                                    <logic:equal value="true" name="isFriend">
                                        我们是朋友:)
                                    </logic:equal>
                                </logic:equal>
                            </div>
                        </logic:equal>
                    </div>
                    <c:if test="${not empty shares}">
                        <div class="side_block radius_all">
                            <div class="side_title clearfix">
                                <h2 class="fleft">
                                    <strong>
                                        <bean:message key="page.user.page.gallery" />
                                    </strong>
                                </h2>
                                <div class="fright">
                                    <a class="rss" href="img.shtml?method=galleryList&wrap-uid=${user.userId }">MORE</a>
                                </div>
                            </div>
                            <div class="side_album">
                                <logic:notEmpty name="galleryList">
                                    <ul>
                                        <logic:iterate id="gallery" name="galleryList">
                                            <li>
                                                <p class="cover">
                                                    <a href="album/${gallery.id }">
                                                        <img src="<community:img value='${gallery.face }' type='sized' width='80' />" alt="${gallery.name }" />
                                                    </a>
                                                </p>
                                                <p class="name">
                                                    <a href="album/${gallery.id }">${gallery.name }</a>
                                                </p>
                                            </li>
                                        </logic:iterate>
                                    </ul>
                                </logic:notEmpty>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block clearfix radius_top_all lightgray_bg" style="margin:0">
                        <div class="main_info">
                            <h2 style="display: inline"><strong>${user.userName }</strong></h2>&nbsp;&nbsp;<logic:equal value="1" name="main" property="sex"><img src="images/icons/female.png" alt="女" align="absmiddle" /></logic:equal><logic:equal value="0" name="main" property="sex"><img src="images/icons/male.png" alt="男" align="absmiddle" /></logic:equal>&nbsp;<bean:message key="page.user.page.hits" arg0="${user.userPageCount }"/>
                            <h2>${user.userSMD}</h2>
                            <hr />
                        </div>
                    </div>
                    <div class="mid_block lightgray_bg">
                        <ul id="tabs_gray" class="graylink">
                            <li class="current"><a name="event_friends"><bean:message key="page.user.page.timeline" /></a></li><li class="normal"><a name="share_latest"><bean:message key="page.user.page.share" /></a></li><li class="normal"><a name="mynewtopic"><bean:message key="user.topic.latest" /></a></li><%--<li class="normal tlink"><a name="mynewfav"><bean:message key="user.attention.latest" /></a></li>--%><li class="normal"><a name="mynewblog"><bean:message key="user.blog.latest" /></a></li><li class="normal"><a name="myinfo_all">个人资料</a></li>
                        </ul>
                    </div>
                    <div id="event_friends" class="tabswrap clearfix">
                        <ul>
                            <logic:notEmpty name="userLogs">
                                <logic:iterate id="model" name="userLogs" indexId="index">
                                    <li>
                                        <div class="fleft userface">
                                            <a href="<ctba:wrapuser user="${model.user}" linkonly="true"/>">
                                                <img src="<comm:img value="${model.user.userFace}" type="sized" width="16" />" width="16" height="16" title="${model.user.userName }" alt="${model.user.userName }" />
                                            </a>
                                        </div>
                                        <div class="fleft" style="width:440px">
                                            ${model.eventStr }
                                        </div>
                                        <div class="fright color_gray font_mid" style="padding:0 5px 0 0">
                                            <community:formatTime time="${model.event.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" />
                                        </div>
                                    </li>
                                </logic:iterate>
                            </logic:notEmpty>
                        </ul>
                    </div>
                    <div id="share_latest" class="mid_block tabswrap hide">
                        <ul class="share_list">
                            <c:forEach items="${shares}" var="model">
                                <li class="clearfix">
                                    <div class="share_list_icon icons_newticket"></div>
                                    <div class="share_list_text">
                                        <a href="share/comment/${model.id }">${model.label }</a>
                                    </div>
                                </li>
                            </c:forEach>
                            <li class="last color_gray graylink2">
                                &gt;&nbsp;<a href="share/${user.userId }">查看更多分享</a>
                            </li>
                        </ul>
                    </div>
                    <div id="myinfo_all" class="mid_block tabswrap hide">
                        <div class="profile_title">
                            <b><bean:message key="page.user.page.basic"/></b>
                        </div>
                        <div class="profile_info clearfix">
                            <dl>
                                <dt>
                                    <bean:message key="page.user.page.info.sex"/>
                                </dt>
                                <dd>
                                    <logic:equal value="1" name="main" property="sex"><bean:message key="page.user.page.sex.female" /></logic:equal>
                                    <logic:equal value="0" name="main" property="sex"><bean:message key="page.user.page.sex.male" /></logic:equal>
                                </dd>
                                <dt>
                                    <bean:message key="page.user.page.info.birthday"/>
                                </dt>
                                <dd>
                                    ${main.birthday }
                                </dd>
                            </dl>
                        </div>
                        <div class="profile_title">
                            <b><bean:message key="page.user.page.contact"/></b>
                        </div>
                        <div class="profile_info  clearfix orangelink">
                            <dl>
                                <dt>
                                    Google Talk
                                </dt>
                                <dd>
                                    ${main.gtalk }
                                </dd>
                                <dt>
                                    Windows Live
                                </dt>
                                <dd>
                                    ${main.msn }
                                </dd>
                            </dl>
                        </div>
                        <div class="profile_title">
                            <b><bean:message key="page.user.page.community"/></b>
                        </div>
                        <div class="profile_info clearfix orangelink">
                            <dl>
                                <dt>
                                    <bean:message key="page.user.page.score" />:
                                </dt>
                                <dd class="color_orange">
                                    ${__request_score }
                                </dd>
                                <dt>
                                    <bean:message key="page.user.page.degree" />:
                                </dt>
                                <dd>
                                    <logic:greaterEqual value="2" name="user" property="userOption"><bean:message key="page.user.page.degree.manager"/></logic:greaterEqual>
                                    <logic:lessThan value="2" name="user" property="userOption"><bean:message key="page.user.page.degree.member"/></logic:lessThan>
                                </dd>
                                <dt>
                                    <bean:message key="page.user.page.group" />
                                </dt>
                                <dd>
                                    <logic:notEmpty name="groups">
                                        <logic:iterate id="model" name="groups" indexId="index">
                                            <a href="group/${model.id }">${model.name }</a>
                                            •
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </dd>
                            </dl>
                        </div>
                    </div>
                    <div id="mynewtopic" class="mid_block tabswrap hide">
                        <ul class="artList">
                            <logic:notEmpty name="topics">
                                <logic:iterate id="model" name="topics" indexId="index">
                                    <li class="clearfix">
                                        <span class="fleft">
                                            <a href="topic/${model.topicId }">
                                                <comm:limit maxlength="50" value="${model.topicTitle }" />
                                            </a>
                                        </span>
                                        <logic:empty name="re">
                                            <span class="fleft font_mid color_orange">
                                                &nbsp;&nbsp;${model.topicReNum }/${model.topicHits }
                                            </span>
                                            <span class="fright font_mid color_gray">
                                                <comm:date limit="16" start="2" value="${model.topicTime}" />
                                            </span>
                                        </logic:empty>
                                    </li>
                                </logic:iterate>
                            </logic:notEmpty>
                        </ul>
                    </div>
                    <div id="mynewblog" class="mid_block tabswrap hide">
                        <ul class="artList">
                            <logic:notEmpty name="blogEntryies">
                                <logic:iterate id="model" name="blogEntryies" indexId="index">
                                    <li class="clearfix">
                                        <span class="fleft">
                                            <a href="blog/entry/${model.id }">
                                                <comm:limit  maxlength="50" value="${model.title }" />
                                            </a>
                                        </span>
                                        <span class="fleft font_mid color_orange">
                                            &nbsp;&nbsp;${model.commentCnt }/${model.hits }
                                        </span>
                                        <span class="fright font_mid color_gray">
                                            <comm:date limit="16" start="2" value="${model.date}" />
                                        </span>
                                    </li>
                                </logic:iterate>
                            </logic:notEmpty>
                        </ul>
                    </div>
                    <div class="mid_block top_blank_wide clearfix radius_all lightgray_bg" style="padding: 10px 10px 3px 10px;_padding:10px">
                        <c:if test="${not empty __sys_username}">
                            <form action="user.shtml?method=saveUserNote" method="post" name="noteForm" onsubmit="return checkNoteForm();">
                                <comm:ubb />
                                <input name="parent" id="parentNote" type="hidden" value="" />
                                <input name="u" type="hidden" value="${user.userName }" />
                                <input type="hidden" name="para_reply_to" id="para_reply_to"/>
                                <textarea class="ftt" style="width:536px" name="content" id="topicContent"></textarea><br />
                                <input type="submit" class="input_btn btn_mt" id="submitButton" value="<bean:message key="page.user.page.button.comment"/>" />
                            </form>
                        </c:if>
                    </div>
                    <div class="mid_block top_blank_wide clearfix">
                        <pg:pager items="${count}" url="userPage.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                            <pg:param name="method" />
                            <pg:param name="uid" />
                            <logic:notEmpty name="userNoteList">
                                <logic:iterate id="model" name="userNoteList" indexId="index">
                                    <div class="reply_list orangelink clearfix">
                                        <div class="reply_user">
                                            <img src="<comm:img value="${model.user.userFace}" type="sized" width="32" />" alt="${model.user.userName}" width="32" height="32" />
                                        </div>
                                        <div id="reply_info" style="width:200px" class="linkblue_b">
                                            <a href="<ctba:wrapuser user="${model.user}" linkonly="true"/>" class="userName">${model.user.userName}</a>&nbsp;
                                            <span class="color_gray">
                                                <comm:ipData value="${model.note.ip}" />
                                            </span>
                                        </div>
                                        <div class="reply_date">
                                            <div class="fleft font_small color_gray"><community:date value="${model.note.create_time}" start="2" limit="16" />&nbsp;|</div>
                                            <ul class="opt2 fleft">
                                                <li>
                                                    <span class="opt_arrow"></span>
                                                    <ul>
                                                        <c:if test="${not empty __sys_username}">
                                                            <li><a href="javascript:re(${model.note.id },'${model.user.userName }');"><bean:message key="page.common.button.reply"/></a></li>
                                                        </c:if>
                                                        <c:if test="${__sys_username eq user.userName}">
                                                            <li><a href='#' onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${context}/user.shtml?method=deleteUserNote&nid=${model.note.id }');return false;"><bean:message key="page.common.button.delete"/></a></li>
                                                        </c:if>
                                                        <li><a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop"/></a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </div>
                                        <div id="reply_content_mid">
                                            <comm:topic value="${model.note.content}" />
                                        </div>
                                    </div>
                                </logic:iterate>
                            </logic:notEmpty>
                            <pg:index>
                                <div class="pageindex nborder">
                                    <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                </div>
                            </pg:index>
                        </pg:pager>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <div class="state">
                    <div class="titles" onclick="$('#tourList').toggle('slow');">
                        <bean:message key="user.caller" />
                    </div>
                    <ul class="user_list clearfix">
                        <logic:notEmpty name="pageLogList">
                            <logic:iterate id="pageLog" name="pageLogList" indexId="index">
                                <li class="graylink font_mid">
                                    <span class="userface">
                                        <a href="u/${pageLog.uid }" style="background-image: url(<comm:img value='${pageLog.face }' type='sized' width='48' />);" title="<bean:message key='user.caller.date' />: <community:formatTime time='${pageLog.updatetime }' pattern='yyyy-MM-dd HH:mm:ss' />"></a>
                                    </span>
                                    <span class="username">
                                        <a href="u/${pageLog.uid }" title="${pageLog.username }">${pageLog.username }</a>
                                    </span>
                                </li>
                            </logic:iterate>
                        </logic:notEmpty>
                    </ul>
                </div>
                <div class="state clearfix">
                    <div class="titles">
                        <bean:message key="page.user.page.friends" />
                    </div>
                    <div class="ew">
                        <a class="rss" href="user.shtml?method=listFriends&wrap-uid=${user.userId }">MORE</a>
                    </div>
                    <ul class="user_list clearfix">
                        <logic:notEmpty name="friends">
                            <logic:iterate id="model" name="friends" indexId="index">
                                <li class="graylink font_mid">
                                    <span class="userface">
                                        <a href="u/${model.user.userId }" style="background-image: url(<comm:img value="${model.user.userFace }" type="sized" width="48" />);" title="${model.user.userName }"></a>
                                    </span>
                                    <span class="username">
                                        <a href="u/${model.user.userId }" title="${model.user.userName }">${model.user.userName }</a>
                                    </span>
                                </li>
                            </logic:iterate>
                        </logic:notEmpty>
                    </ul>
                </div>
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
        <script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>