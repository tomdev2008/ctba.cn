<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.user.friend.add.succeedTitle" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<c:import url="../head.jsp">
		<c:param name="submenu" value="user"></c:param>
		</c:import>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle"/>&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.user.friend.add.succeedTitle" />
					</div>
				</div>

				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">

				<div class="mid_block top_notice clearfix">&nbsp;&nbsp;
		<h2 class="color_orange"><b><bean:message key="page.user.friend.add.succeedTitle" /></b></h2>
		<br />
		&nbsp;&nbsp;<img src="images/ico_sendf.gif" /><br />
        &nbsp;&nbsp;<bean:message key="page.user.friend.add.succeed" arg0="${friend.userName }"/><br/>
		&nbsp;&nbsp;<a href="user.shtml?method=listFriends"><bean:message key="page.user.friend.add.toList" /></a>&nbsp;&nbsp;
		|
		&nbsp;&nbsp;<a	href="u/${friend.userId }"><bean:message key="page.user.friend.add.toPage" arg0="${friend.userName }"/></a>
		|
		&nbsp;&nbsp;<a	href="message.action?method=form&to=<community:url value="${friend.userName }"/>"><bean:message key="page.user.friend.add.toMessage" arg0="${friend.userName }"/></a>
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
