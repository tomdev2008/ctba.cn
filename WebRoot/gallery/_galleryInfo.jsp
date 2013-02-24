<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<div id="top_info_wrapper" class="mid_block top_notice clearfix">
	<div id="top_info_pic" class="fleft">
		<a href="albumn/${galleryModel.id}">
			<img src="<community:img value="${galleryModel.face }" type="sized" width="80" />" alt="${galleryModel.name }" width="80" />
		</a>
	</div>
	<div id="top_info_text" class="fleft">
		<h1 class="color_orange orangelink">
			<strong><a href="userpage/<g:url value="${galleryModel.username }"/>">${galleryModel.username }</a>&nbsp;的相册&nbsp;-&nbsp;<a href="albumn/${galleryModel.id}">${galleryModel.name }</a></strong>
		</h1>
		<h3>
			<community:topic value="${galleryModel.description}" />
		</h3>
		<ul class="clearfix color_gray">
			<li class="head"><bean:message key="page.gallery.info.place"/>：${galleryModel.place }</li>
			<li class="font_mid">|</li>
			<li><bean:message key="page.gallery.info.updateTime"/>：<span class="font_mid"><g:date limit="11" start="0" value="${galleryModel.updateTime }" /></span></li>
			<li class="font_mid">|</li>
			<li><bean:message key="page.gallery.info.hits"/>：<span class="font_mid">${galleryModel.hits }</span></li>
			<c:if test="${galleryModel.username eq __sys_username}">
				<li class="font_mid">|</li>
				<li><a class="bluelink" href="img.shtml?method=galleryForm&id=${galleryModel.id }"><bean:message key="page.gallery.action.edit"/></a><span class="font_mid">&nbsp;/&nbsp;</span><a class="bluelink" href="multiImgUpload.shtml?method=form&galleryId=${galleryModel.id }"><bean:message key="page.gallery.action.upload"/></a></li>
			</c:if>
		</ul>
	</div>
</div>
