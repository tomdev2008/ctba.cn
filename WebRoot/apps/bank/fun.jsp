<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>兑换&nbsp;-&nbsp;CT币中心&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;CT币中心&nbsp;&gt;&nbsp;兑换
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<jsp:include page="_tab.jsp" flush="true">
							<jsp:param name="currTab" value="fun" />
						</jsp:include>
					</div>
<br class="clear"/>
					<div id="tab_bbs_latest_col3">
					<div class="message_info">目前扯谈社的CTB兑换活动尚未开始，请稍候。您也可以<a href="feedback.action?method=form&type=ad">给我们提出建议</a></div>
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