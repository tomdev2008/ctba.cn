<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>${galleryModel.name }&nbsp;-&nbsp;个人相册&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="album/">个人相册</a>&nbsp;&gt;&nbsp;${galleryModel.name }
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_logo.jsp" />
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block">
						<jsp:include page="_galleryInfo.jsp"></jsp:include>
					</div>
					<div class="mid_block">
					<c:if test="${not empty models}">
					<pg:pager items="${count}" url="img.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
						<pg:param name="method" />
						<pg:param name="id" />
						<pg:index>
						<div class="pageindex nborder top_blank">
							<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
						<table width="100%" cellspacing="10" cellpadding="10">
							<tr>
								<c:forEach items="${models}" var="model" varStatus="status">
								<td align="center" class="graylink2" width="33.33%">
									<a class="photo" href="album/photo/${model.img.id }">
										<img src="<com:img value="${model.img.path }" type="default"/>" alt="${model.img.name}" />
									</a>
									<h4>
										<strong><community:limit maxlength="15" value="${model.img.name}" /></strong>
									</h4>
									<c:if test="${model.isAuthor }">
									<a href="img.shtml?method=setGalleryFace&galleryId=${galleryModel.id}&id=${model.img.id}">设为封面</a>
									<a href="javascript:void(0);" class="bluelink" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath }img.shtml?method=deleteImg&id=${model.img.id }');return false;">x</a>
									</c:if>
								</td>
								<c:if test="${(status.index+1)%3==0}"></tr><tr></c:if>
								</c:forEach>
							</tr>
						</table>
						<pg:index>
						<div class="pageindex nborder">
							<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
					</pg:pager>
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