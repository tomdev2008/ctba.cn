<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><c:if test="${not empty __gallery_all}">大家</c:if><c:if test="${empty __gallery_all}">${user.userName }</c:if>的相册&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="album/">个人相册</a>&nbsp;&gt;&nbsp;<c:if test="${not empty __gallery_all}">大家</c:if><c:if test="${empty __gallery_all}">${user.userName }</c:if>的相册
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_logo.jsp" />
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<c:if test="${not empty __gallery_all}">
							<c:if test="${not empty user}">	<li class="normal"><a href="img.shtml?method=galleryList">${user.userName }&nbsp;的相册</a></li>
							</c:if>	<li class="current">大家的相册</li>
							</c:if>
							<c:if test="${empty __gallery_all}">
								<li class="current">${user.userName }&nbsp;的相册</li>
								<li class="normal"><a href="img.shtml?method=galleryList&type=all">大家的相册</a></li>
							</c:if>
							<c:if test="${not empty __sys_username}">
								<li class="normal">
									<a href="img.shtml?method=galleryForm">新建相册</a>
								</li>
							</c:if>
						</ul>
					</div>
					<div class="mid_block">
						<c:if test="${not empty models}">
							<div class="mid_block">
								<pg:pager items="${count}" url="img.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
									<pg:param name="method" />
									<pg:param name="type" />
									<pg:param name="gid" />
									<ul id="album_list">
										<c:forEach items="${models}" var="model" varStatus="status">
											<li class="clearfix">
												<div class="fleft album_cover center">
													<a class="blog_border_large" href="album/${model.gallery.id }">
														<img src="<com:img value="${model.gallery.face }" type="default" />" alt="${model.gallery.description }" />
													</a>
												</div>
												<div class="fleft album_intro orangelink">
													<h2 class="color_orange">
														<strong><a href="album/${model.gallery.id }">${model.gallery.name}</a></strong>
													</h2>
													<br />
													<h3>
														<com:topic value="${model.gallery.description }" />
													</h3>
													<br />
													<span class="color_gray">
														<com:date limit="10" start="0" value="${model.gallery.updateTime }" />&nbsp;于&nbsp;${model.gallery.place }<br />
														共有&nbsp;<span class="font_mid">${model.cnt }</span> 张照片&nbsp;
														<c:if test="${model.isAuthor }">
															<a class="bluelink" href="img.shtml?method=galleryForm&id=${model.gallery.id }">修改</a>
															<span class="font_mid">/</span>
															<a class="bluelink" href="multiImgUpload.shtml?method=form&galleryId=${model.gallery.id }">上传</a>
															<span class="font_mid">/</span>
															<a href="javascript:void(0);" class="bluelink" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath }img.shtml?method=deleteGallery&id=${model.gallery.id }');return false;">删除</a>
														</c:if>
													</span>
												</div>
											</li>
										</c:forEach>
									</ul>
									<pg:index>
										<div class="pageindex nborder">
											<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
										</div>
									</pg:index>
								</pg:pager>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>