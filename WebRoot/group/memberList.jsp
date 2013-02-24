<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.title.member"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${group.id}">${group.name }</a>&nbsp;&gt;&nbsp;<bean:message key="page.group.title.member"/>
					</div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp"/>
				</div>
				<div class="left_block top_blank">
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="member"/>
					</jsp:include>
					<div class="top_blank">
						<pg:pager items="${count}" url="g.action" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
							<pg:param name="gid" />
							<pg:param name="method" />
							<pg:index>
								<div class="pageindex_list">
									<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
								</div>
							</pg:index>
							<c:if test="${not empty users}">
								<ul class="square_photo_list_wide clearfix">
									<c:forEach items="${users}" var="model" varStatus="status">
										<li>
											<a class="photo" href="u/${model.user.userId }" class="userName">
												<div class="userface"><img src="<community:img value="${model.user.userFace }" type="sized" width="80" />" width="80" height="80" /></div>
												<div class="username">${model.user.userName }</div>
											</a>
											<div class="color_orange">
												<c:if test="${model.gu.role>1}"><bean:message key="page.group.member.role.manager" /></c:if>
												<c:if test="${model.gu.role<=1}"><bean:message key="page.group.member.role.normal" /></c:if>
												<c:if test="${__group_is_manager eq true}">
													<a class="button" href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm" />','${basePath }g.action?method=delMember&id=${model.gu.id }');return false;"><img src="images/icons/closes.gif" align="absmiddle" /></a>
												</c:if>
											</div>
										</li>
									</c:forEach>
								</ul>
							</c:if>
						</pg:pager>
					</div>
					<c:if test="${__group_is_manager eq true}">
						<c:if test="${not empty usersWaiting}">
							<ul class="square_photo_list_wide" class="clearfix">
								<c:forEach items="${usersWaiting}" var="model" varStatus="status">
									<li>
										<a class="photo" href="${context }/userpage/<g:url value="${model.user.userName }"/>" class="userName">
											<div class="userface"><img src="<community:img value="${model.user.userFace }" type="sized" width="80" />" width="80" height="80" /></div>
											<div class="username">${model.user.userName }</div>
										</a>
										<span class="color_orange"><bean:message key="page.group.member.role.waiting" /></span>
										<a class="button" href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.group.member.pass.confirm" />','${basePath }group.shtml?method=pass&id=${model.gu.id }');return false;"><img src="images/good.gif" align="absmiddle" title="<bean:message key="page.group.member.pass" />" /></a>
										<a class="button" href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.group.member.reject.confirm" />','${basePath }g.action?method=delMember&id=${model.gu.id }');return false;"><img src="images/bad_1.gif" align="absmiddle" title="<bean:message key="page.group.member.reject" />" /></a>
									</li>
								</c:forEach>
							</ul>
						</c:if>
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
