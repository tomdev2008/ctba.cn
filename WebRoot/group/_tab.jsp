<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<ul id="tabs" class="graylink">
    <c:if test="${param.currTab eq 'currTopic' }">
        <li class="current">
            <bean:message key="page.group.widget.tab.currTopic"/>
        </li>
    </c:if>
    <c:if test="${param.currTab eq 'currImage' }">
        <li class="current">
           <bean:message key="page.group.widget.tab.currImage"/>
        </li>
    </c:if>
    <c:if test="${param.currTab eq 'currActivity' }">
        <li class="current">
            <bean:message key="page.group.widget.tab.currActivity"/>
        </li>
    </c:if>
    <li id="t_content"
        <c:if test="${param.currTab eq 'topic' }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'topic') }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'topic' }"><bean:message key="page.group.title.topic"/></c:if>
        <c:if test="${not (param.currTab eq 'topic') }">
            <c:if test="${empty group.magicUrl}"><a href="group/${group.id}"><bean:message key="page.group.title.topic"/></a></c:if>
            <c:if test="${not empty group.magicUrl}"><a href="group/${group.magicUrl}"><bean:message key="page.group.title.topic"/></a></c:if>
        </c:if>
    </li>
    <li id="t_image"
        <c:if test="${param.currTab eq 'image' }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'image') }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'image' }"><bean:message key="page.group.title.gallery"/></c:if>
        <c:if test="${not (param.currTab eq 'image') }">
            <a href="group/gallery/${group.id }"><bean:message key="page.group.title.gallery"/></a>
        </c:if>
    </li>
    <li id="t_member"
        <c:if test="${param.currTab eq 'member' }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'member') }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'member' }"><bean:message key="page.group.title.member"/></c:if>
        <c:if test="${not (param.currTab eq 'member') }">
            <a href="group/members/${group.id }"><bean:message key="page.group.title.member"/></a>
        </c:if>
    </li>
    <li id="t_activity"
        <c:if test="${param.currTab eq 'activity' }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'activity') }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'activity' }"><bean:message key="page.group.title.activity"/></c:if>
        <c:if test="${not (param.currTab eq 'activity') }">
            <a href="group/activities/${group.id }"><bean:message key="page.group.title.activity"/></a>
        </c:if>
    </li>
    <c:if test="${true eq __group_is_manager}">
        <li id="t_link"
            <c:if test="${param.currTab eq 'link' }">class="current"</c:if>
            <c:if test="${not (param.currTab eq 'link') }">class="normal"</c:if>>
            <c:if test="${param.currTab eq 'link' }"><bean:message key="page.group.title.link"/></c:if>
            <c:if test="${not (param.currTab eq 'link') }">
                <a href="gLink.action?method=list&gid=${group.id }"><bean:message key="page.group.title.link"/></a>
            </c:if>
        </li>
    </c:if>
</ul>
<c:if test="${true eq __group_is_user}">
    <div class="lt_post graylink" onmouseover="$('#post_option').show()" onmouseout="$('#post_option').hide()">
        <span><bean:message key="page.group.widget.op"/></span>
        <ul id="post_option">
            <li>
                <a href="newgrouptopic/${group.id }"><bean:message  key="page.group.widget.op.newTopic"/></a>
            </li>
            <li>
                <a href="imgUpload.shtml?method=form&gid=${group.id }"><bean:message  key="page.group.widget.op.newPic"/></a>
            </li>
            <li>
                <a href="activity.shtml?method=form&gid=${group.id }"><bean:message  key="page.group.widget.op.newActivity"/></a>
            </li>
            <c:if test="${true eq __group_is_manager}">
                <li>
                    <a href="g.action?method=form&gid=${group.id }"><bean:message  key="page.group.widget.op.editGroup"/></a>
                </li>
                <li>
                    <a href="group.shtml?method=invite&gid=${group.id }"><bean:message  key="page.group.widget.op.invite"/></a>
                </li>
            </c:if>
            <li class="last">
                <a href="group.shtml?gid=${group.id }&method=leave"><bean:message  key="page.group.widget.op.leave"/></a>
            </li>
        </ul>
    </div>
</c:if>
<c:if test="${not empty __sys_username and (not __group_is_waiting) and (not __group_is_user)}">
    <div class="lt_post graylink">
        <a href="g.action?method=mine&action=join&gid=${group.id }"><bean:message  key="page.group.widget.op.join"/></a>
    </div>
</c:if>
<c:if test="${true eq __group_is_waiting}">
    <div class="lt_post graylink">
        <bean:message  key="page.group.widget.op.waitting"/>
    </div>
</c:if>