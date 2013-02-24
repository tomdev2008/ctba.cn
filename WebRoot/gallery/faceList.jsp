<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.gallery.face.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;头像相册
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<c:if test="${not empty models}">
						<div class="mid_block">
							<table width="100%" cellspacing="10" cellpadding="10">
								<tr>
									<c:forEach items="${models}" var="model" varStatus="status">
										<td align="center">
											<img src="<com:img value="${model.path }" type="default"/>" width="120" height="120" />
											<br />
											<a href="img.shtml?method=setUserFace&galleryId=${galleryModel.id}&id=${model.id}"><bean:message key="page.gallery.face.useThis" /></a>
											<a class="bluelink" href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath }img.shtml?method=deleteImg&id=${model.id }&type=face');return false;">x</a>
										</td>
									<c:if test="${(status.index+1)%3==0}"></tr><tr></c:if>
									</c:forEach>
								</tr>
							</table>
						</div>
					</c:if>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>