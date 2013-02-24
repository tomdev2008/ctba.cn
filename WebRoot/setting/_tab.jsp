<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<div id="integrity" class="clearfix">
	<div id="integrity_text" class="fleft">
		<bean:message key="page.setting.completeRate" />
	</div>
	<div id="integrity_bar" class="fleft">
		<span style="width:${infoCompletePercent*310/100-5 }px">${infoCompletePercent }%</span>
	</div>
</div>
<ul id="tabs_gray" class="graylink">
	<li <c:if test="${param.currTab eq 'face' }">class="current"</c:if> <c:if test="${not (param.currTab eq 'face') }">class="normal"</c:if>><c:if test="${param.currTab eq 'face' }"><bean:message key="page.setting.tab.face" /></c:if><c:if test="${not (param.currTab eq 'face') }"><a href="userSetting.action?method=face"><bean:message key="page.setting.tab.face" /></a></c:if></li>
	<li <c:if test="${param.currTab eq 'bbs' }">class="current"</c:if><c:if test="${not (param.currTab eq 'bbs') }">class="normal"</c:if>><c:if test="${param.currTab eq 'bbs' }"><bean:message key="page.setting.tab.bbs" /></c:if><c:if test="${not (param.currTab eq 'bbs') }"><a href="userSetting.action?method=bbs"><bean:message key="page.setting.tab.bbs" /></a></c:if></li>
	<li <c:if test="${param.currTab eq 'info' }">class="current"</c:if><c:if test="${not (param.currTab eq 'info') }">class="normal"</c:if>><c:if test="${param.currTab eq 'info' }"><bean:message key="page.setting.tab.info" /></c:if><c:if test="${not (param.currTab eq 'info') }"><a href="userSetting.action?method=info"><bean:message key="page.setting.tab.info" /></a></c:if></li>
	<li <c:if test="${param.currTab eq 'privacy' }">class="current"</c:if><c:if test="${not (param.currTab eq 'privacy') }">class="normal"</c:if>><c:if test="${param.currTab eq 'privacy' }"><bean:message key="page.setting.tab.privacy" /></c:if><c:if test="${not (param.currTab eq 'privacy') }"><a href="userSetting.action?method=privacy"><bean:message key="page.setting.tab.privacy" /></a></c:if></li>
	<li <c:if test="${param.currTab eq 'email' }">class="current"</c:if><c:if test="${not (param.currTab eq 'email') }">class="normal"</c:if>><c:if test="${param.currTab eq 'email' }"><bean:message key="page.setting.tab.email" /></c:if><c:if test="${not (param.currTab eq 'email') }"><a href="userSetting.action?method=email"><bean:message key="page.setting.tab.email" /></a></c:if></li>
	<li <c:if test="${param.currTab eq 'password' }">class="current"</c:if><c:if test="${not (param.currTab eq 'password') }">class="normal"</c:if>><c:if test="${param.currTab eq 'password' }"><bean:message key="page.setting.tab.password" /></c:if><c:if test="${not (param.currTab eq 'password') }"><a href="userSetting.action?method=password"><bean:message key="page.setting.tab.password" /></a></c:if></li>
</ul>