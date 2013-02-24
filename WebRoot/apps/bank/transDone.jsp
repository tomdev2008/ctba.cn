<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>转账成功&nbsp;-&nbsp;CT币中心&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>&nbsp;
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;CT币中心&nbsp;&gt;&nbsp;转账成功
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<jsp:include flush="true" page="_bankBlock.jsp" />
					<div class="mid_block lightgray_bg">
						<jsp:include page="_tab.jsp" flush="true">
							<jsp:param name="currTab" value="trans" />
						</jsp:include>
					</div>
					<div>
						<div class="title">转账成功</div>
						<div class="mid_block top_notice clearfix">
							&nbsp;&nbsp;<h2 class="color_orange"><b>转账成功：您给${targetUser.userName}转了${value}CTB</b></h2>
							&nbsp;&nbsp;<img src="images/ico_sendf.gif"/><br/>
							&nbsp;&nbsp;<a href="bank/">返回CTB中心</a>&nbsp;&nbsp;
							|&nbsp;&nbsp;<a href="bank.action?method=trans">继续转账</a>
							|&nbsp;&nbsp;<a href="u/${targetUser.userId}">查看${targetUser.userName}的页面</a>
						</div>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>