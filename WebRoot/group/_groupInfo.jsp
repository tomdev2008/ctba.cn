<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<div id="group_img">
	<img src="<com:img value="${group.face }" type="sized" width="80" />" width="80" class="group_border" />
</div>
<div id="group_text">
	<div class="color_orange clearfix" style="margin-bottom:5px">
		<h2 class="fleft orangelink">
			<strong>
				<c:if test="${empty group.magicUrl}"><a href="group/${group.id}">${group.name }</a></c:if>
				<c:if test="${not empty group.magicUrl}"><a href="group/${group.magicUrl}">${group.name }</a></c:if>
			</strong>
		</h2>
		<span class="fright">
			<img src="images/icons/feed.png" align="absmiddle" />&nbsp;<a href="rss.shtml?type=group&gid=${group.id }" target="_blank"><bean:message key="page.group.rss"/></a>
		</span>
	</div>
	<p class="color_gray" style="margin-bottom:5px">
		${group.manager }&nbsp;<bean:message key="page.group.info.createTime"/>&nbsp;<span class="font_mid color_gray"><g:date limit="11" start="0" value="${group.createTime }" /></span><br />
		<bean:message key="group.topic" />&nbsp;<span class="font_mid color_orange">${topicCnt}</span>&nbsp;&nbsp;
		<bean:message key="group.activity" />&nbsp;<span class="font_mid color_orange">${activityCnt}</span>&nbsp;&nbsp;
		<bean:message key="group.user" />&nbsp;<span class="font_mid color_orange">${userCnt }</span>&nbsp;&nbsp;
		<bean:message key="group.album" />&nbsp;<span class="font_mid color_orange">${imageCnt }</span>&nbsp;&nbsp;
		<bean:message key="group.analytics" />&nbsp;<span class="font_mid color_orange">${group.hits }</span>&nbsp;&nbsp;
		小组排名&nbsp;<span class="font_mid color_orange">${groupRank }</span>&nbsp;&nbsp;
		
	</p>
	<p class="orangelink">
		<com:topic value="${group.discription }"/>
	</p>
	<%--	#253<g:htmlEncode value="${group.discription }" />--%>
</div>
<!--[if lte IE 7]>
<br class="clear" />
<![endif]-->