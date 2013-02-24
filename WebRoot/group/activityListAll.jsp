<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.activity.title" />&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" />&nbsp;-&nbsp;<bean:message key="nav.indexPage"/></title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;>&nbsp;小组活动
					</div>
				</div>
				<div class="left_block top_blank">
					<div class="boardChild">
						<c:if test="${not empty models}">
							<pg:pager items="${count}" url="activity.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="method" />
								<pg:index>
									<div class="pageindex_list">
										<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
									</div>
								</pg:index>
								<ul class="column_2c clearfix">
									<c:forEach items="${models}" var="model" varStatus="status">
										<li class="<c:choose><c:when test="${status.count % 4 == 1}">lightgray_bg radius_left_all</c:when><c:when test="${status.count % 4 == 2}">lightgray_bg radius_right_all</c:when></c:choose>">
											<div class="fleft graylink">
												<a href="group/${model.activity.groupModel.id}" style="background-image:url(<com:img type="sized" width="48" value="${model.activity.groupModel.face}" />)" title="${model.activity.groupModel.name}"></a>
											</div>
											<div class="fright">
												<h3>
													<a href="group/activity/${model.activity.id }">
														<com:limit maxlength="20" value="${model.activity.title }" />
													</a>
												</h3>
												<p>
													<span class="color_gray">
														<g:date limit="13" start="5" value="${model.activity.startTime }" />:00&nbsp;-&nbsp;<g:date limit="13" start="5" value="${model.activity.endTime }" />:00
													</span>
													<br />
													<%--										<img src="<com:img value="${model.user.userFace}" type="mini"/>"/>--%>
													<span class="color_orange">
														<%--${model.activity.username}--%>${model.activity.groupModel.name}
													</span>
													<%--<g:date limit="11" start="0" value="${model.activity.createTime }" />--%>
												</p>
											</div>
										</li>
									</c:forEach>
								</ul>
								<jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>
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