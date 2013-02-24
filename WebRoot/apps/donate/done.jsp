<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.alipay.util.*"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>捐赠成功，衷心感谢您对扯谈社的支持&nbsp;-&nbsp;<bean:message
				key="nav.indexPage" /></title>
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
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;
						<a href="/donate/">捐赠扯谈</a>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<jsp:include flush="true" page="_donateBlock.jsp" />
					<div class="mid_block ">
						<div class="message_ok">
							捐赠成功，衷心感谢您对扯谈社的支持！
						</div>
						<div align="center" style="margin:5px;padding:5px">
							<img src="images/jugong.gif" style="border: #ccc 2px solid" />
							<br class="clear" /><br class="clear" /><br class="clear" />
							<a href="/" class="link_btn">返回首页</a>
							<input type="hidden" name="responseTxt" value="${responseTxt }"/>
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