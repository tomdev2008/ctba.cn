<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>
			<c:if test="${__sys_is_self}"><bean:message key="page.user.friend.list.mine"/></c:if><c:if test="${not __sys_is_self}"><bean:message key="page.user.friend.list.user" arg0="${__sys_username_focus }"/></c:if>&nbsp;-&nbsp;${__sys_username_focus }&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle"/>&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;我的好友
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block top_notice friend orangelink clearfix">
                        <c:if test="${__sys_is_self}"><h2 class="color_orange"><b><bean:message key="page.user.friend.list.mine"/></b></h2></c:if>
                        <c:if test="${not __sys_is_self}"><h2 class="color_orange"><b><a href='userpage/<community:url value="${__sys_username_focus }"/>'><bean:message key="page.user.friend.list.user" arg0="${__sys_username_focus }"/></a></b></h2></c:if>
						<bean:message key="page.user.friend.list.info"/>
					</div>
					<div class="mid_block">
					<pg:pager items="${count}" url="user.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
						<pg:param name="method" />
						<pg:param name="wrap-uid" />
						<ul class="square_photo_list clearfix">
							<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
							<li>
								<a class="photo" href="u/${model.user.userId}">
									<div class="userface"><img src="<community:img value="${model.user.userFace}" type="sized" width="80" />" width="80" height="80" alt="${model.user.userName}" title="${model.user.userName}" /></div>
									<div class="username">${model.user.userName}</div>
								</a>
								<c:if test="${__sys_is_self}">
								<span class="font_small bluelink">
									<a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.user.friend.button.delete"/>','${basePath }friend.action?method=delete&id=${model.friend.frdId }');return false;">x</a>
								</span>
								</c:if>
							</li>
							</logic:iterate>
							</logic:notEmpty>
						</ul>
						<pg:index>
						<div class="pageindex nborder">
							<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
						</div>
						</pg:index>
					</pg:pager>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>