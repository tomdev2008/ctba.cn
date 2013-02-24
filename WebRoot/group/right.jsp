<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<c:if test="${(not empty __sys_username) and (not __group_is_waiting)}">
	<c:if test="${false eq __group_is_user}">
		<div class="notice clearfix">
			<div class="diggmini2">
				<img src="images/findfirends.png" />
			</div>
			<div class="digglt2 orangelink">
				<a href="g.action?method=mine&action=join&gid=${group.id }"><bean:message key="page.group.widget.joinHint"/></a>
			</div>
		</div>
	</c:if>
</c:if>
<c:if test="${not empty refGroupMaps}">
	<div class="state">
		<div class="titles">
			<bean:message key="group.refgroups" />
		</div>
		<ul class="user_list clearfix">
			<c:forEach items="${refGroupMaps}" var="model" varStatus="index">
				<li class="graylink font_mid">
					<span class="userface">
						<a href="<ctba:wrapgroup group="${model.group}" linkonly="true"/>" style="background: url(<com:img value="${model.group.face }" type="sized" width="48" />) no-repeat center;" title="${model.group.name }"></a>
					</span>
					<span class="username">
						<ctba:wrapgroup group="${model.group}" />
					</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${not empty newActivityList}">
	<div class="state">
		<div class="titles"><bean:message key="page.group.title.activity"/></div>
		<div id="newActivities">
			<ul>
				<c:forEach items="${newActivityList}" var="model" varStatus="index">
					<li class="tlist_wrap clearfix" <c:if test='${status.index % 2 == 1}'>lightgray_bg radius_all</c:if>>
						<div class="tlist_icon icons_newticket"></div>
						<div class="tlist_text">
							<a href="group/activity/${model.id }" class="stars_border">
								<community:limit value="${model.title }" maxlength="50"/>
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
<c:if test="${not empty refTopics}">
	<div class="state" id="refTopics">
		<div class="titles"><bean:message key="page.group.widget.referenceTopic"/></div>
		<ul>
			<c:forEach items="${refTopics}" var="model" varStatus="status">
				<li class="tlist_wrap clearfix <c:if test="${status.index % 2 == 1}">lightgray_bg radius_all</c:if>">
					<div class="tlist_icon icons_newticket"></div>
					<div class="tlist_text"><a href="${model.url}">${model.label}</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>

<c:if test="${not empty newUserMaps}">
	<div class="state">
		<div class="titles">
			<bean:message key="group.join.latest" />
		</div>
		<ul class="user_list clearfix">
			<c:forEach items="${newUserMaps}" var="model" varStatus="index">
				<li class="graylink font_mid">
					<span class="userface">
						<a href="<ctba:wrapuser user="${model.user}" linkonly="true"/>" style="background: url(<com:img value="${model.user.userFace }" type="sized" width="48" />) no-repeat center;" title="${model.user.userName }"></a>
					</span>
					<span class="username">
						<ctba:wrapuser user="${model.user}"/>
					</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${not empty userEventList}">
	<div class="state">
		<div onclick="$('#gevent').toggle('slow');" class="titles"><bean:message key="group.user.timeline" /></div>
		<ul>
			<c:forEach items="${userEventList}" var="model" varStatus="status">
				<li class="tlist_wrap clearfix <c:if test="${status.index % 2 == 1}">lightgray_bg radius_all</c:if>">
					<div class="tlist_icon icons_newticket">
						<%--<img src="<com:img value="${model.user.userFace}" type="sized" width="16" />" class="userFace_border" width="16" height="16" class="userFace_border" />--%>
					</div>
					<div class="tlist_text">
						${model.eventStr }&nbsp;<span class="font_small color_gray"><com:date value="${model.event.updateTime }" start="11" limit="16" /></span>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>
<%--<c:if test="${not empty activityList}">
	<div class="state">
		<div onclick="$('newActivities').toggle('slow');" class="titles"><bean:message key="group.activity" /></div>
	</div>
</c:if>--%>
<c:if test="${not empty hotUserMaps}">
	<div class="state">
		<div class="titles" onclick="$('#activeUser').toggle();">
			<bean:message key="group.user.active" />
		</div>
		<div id="activeUser">
			<ul class="active_users clearfix">
				<c:forEach items="${hotUserMaps}" var="model" varStatus="index">
					<li>
						<a href="u/${model[0] }" class="stars_border">
							<img src="<com:img value="${model[1] }" type="mini" />" height="30" width="30" />
							<span class="user_name">${model[2] }</span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>
<c:if test="${not empty groupLinkList}">
	<div class="state clearfix">
		<div onclick="$('#groupLinkList').toggle('slow');" class="titles"><bean:message key="page.group.title.link"/></div>
		<div id="groupLinkList">
			<ul class="infobar">
				<c:forEach items="${groupLinkList}" var="model" varStatus="status">
					<li class="icons_extlink">
						<a href="${model.url }" title="${model.label }" target="_blank"><com:limit maxlength="20" value="${model.label }" /></a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>
<%--<div class="adsenes_right_block">
	<script type="text/javascript"><!--
		google_ad_client = "pub-3911382285138100";
		/* 180x150,  09-2-23 */
		google_ad_slot = "3404358666";
		google_ad_width = 180;
		google_ad_height = 150;
		//-->
	</script>
	<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
</div>--%>
<div class="state">
	<ul class="infobar">
		<li class="icons_feed">
			<a href="rss.shtml" target="_blank"><bean:message key="rss.feed"/></a>
		</li>
	</ul>
	<hr size="1" color="#eeeeee" />
	<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
</div>
