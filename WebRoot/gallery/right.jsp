<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<logic:notEmpty name="refGalleries">
	<div class="state">
		<div class="titles">
			<bean:message key="page.gallery.common.refGallery"/>
		</div>
		<style type="text/css">
			.album_list_col2 { padding-top: 10px }
			.album_list_col2 p { margin: 0; line-height: 18px; text-align: center; word-wrap: break-word }
			.album_list_col2 li { float: left; display: inline; width: 50%; margin-bottom: 15px }
			.album_list_col2 li img { border: none }
			.album_list_col2 li a:hover img { background: none }
		</style>
		<ul class="album_list_col2 clearfix">
			<logic:iterate id="gallery" name="refGalleries" indexId="index">
				<li>
					<p>
						<a href="albumn/${gallery.id }">
							<img src="<community:img value="${gallery.face }" type="sized" width="80" />" alt="${gallery.name }" />
						</a>
					</p>
					<p>
						<a href="albumn/${gallery.id }" title="${gallery.name }">${gallery.name }</a>
					</p>
				</li>
				<c:if test="${index % 2 == 1}"><br style="clear:both;font-size:0" /></c:if>
			</logic:iterate>
		</ul>
	</div>
</logic:notEmpty>
<c:if test="${not empty globalCommentList}">
	<div class="state clearfix" id="globalCommentList">
		<div class="titles"><bean:message key="page.gallery.common.newestComment"/></div>
		<ul>
			<c:forEach items="${globalCommentList}" var="model">
				<li class="tlist_wrap clearfix">
					<div class="tlist_icon icons_newticket"></div>
					<div class="tlist_text">
						<a href="album/photo/${model.imageModel.id}"><com:limit maxlength="25" value="${model.body}"/></a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<div class="state">
	<ul class="infobar">
		<li class="icons_feed"><a href="rss.shtml" target="_blank"><bean:message key="rss.feed" /></a></li>
	</ul>
	<hr size="1" color="#eeeeee" />
	<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
</div>