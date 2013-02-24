<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.title.gallery"/>&nbsp;-&nbsp;${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
		<link media="all" href="${context}/theme/jquery/jquery.lightbox-0.4.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${context}/javascript/jquery.lightbox-0.4.pack.js"></script>
		<c:if test="${param.type eq 'gallery'}">
			<script type="text/javascript">
				$(document).ready(function() {
					$('.group_photo_pic a').lightBox();
				});
			</script>
		</c:if>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;>&nbsp;${group.name }&nbsp;>&nbsp;<bean:message key="page.group.title.gallery"/>
					</div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp"/>
				</div>
				<div class="left_block top_blank">
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="image"/>
					</jsp:include>
					<div class="boardChild top_blank_wide clearfix">
						<c:if test="${not empty models}">
							<pg:pager items="${count}" url="img.shtml" index="center" maxPageItems="16" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="method" />
								<pg:param name="gid" />
								<pg:param name="type" />
								<pg:index>
									<div class="pageindex_list" style="padding-top: 10px">
										<jsp:include flush="true" page="../common/jsptags/simple_16.jsp"></jsp:include>
										&nbsp;&nbsp;&nbsp;<span class="group_type">
											<c:if test="${param.type eq 'gallery'}">
												<a class="gt" href="img.shtml?method=groupImageList&gid=${group.id }&type=">浏览模式</a>
											</c:if>
											<c:if test="${not (param.type eq 'gallery')}">
												<a class="gt" href="img.shtml?method=groupImageList&gid=${group.id }&type=gallery">画廊模式</a>
											</c:if>
										</span>
									</div>
								</pg:index>
								<c:forEach items="${models}" var="model" varStatus="status">
									<div class="group_photo_wrap">
										<div class="group_photo_pic">
											<c:if test="${param.type eq 'gallery'}">
												<a class="photo" href="<com:img value="${model.img.path }" type="none"/>" title="${model.img.name}">
													<img src="<com:img value="${model.img.path }" type="default"/>" alt="${model.img.name}" />
												</a>
											</c:if>
											<c:if test="${not (param.type eq 'gallery')}">
												<a href="group/gallery/photo/${model.img.id }">
													<img src="<com:img value="${model.img.path }" type="default"/>" alt="${model.img.name}" />
												</a>
											</c:if>
										</div>
										<div class="group_photo_info">
											<span class="color_orange">
												<g:limit maxlength="25" value="${model.img.name}" />
											</span>
											<br />
											<c:if test="${model.isAuthor or isManager}">
												<a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm" />','${basePath }img.shtml?method=deleteGroupImg&id=${model.img.id }');return false;">
													<img src="images/btn_close.png" align="absmiddle" title="<bean:message key="page.common.button.delete" />" />
												</a>
											</c:if>
											<span class="color_gray">
												<bean:message key="page.group.image.createTime" />: <g:date limit="11" start="0" value="${model.img.createTime }" />
												<br />
												<bean:message key="page.group.image.creator" />: <a href="userpage/<g:url value="${model.img.username }"/>">${model.img.username }</a>
											</span>
										</div>
									</div>
								</c:forEach>
							</pg:pager>
						</c:if>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../blog/bottom.jsp" flush="true" />
	</body>
</html>
