<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="page.common.404.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%--<meta http-equiv="refresh" content="2; url=http://ctba.cn" />--%>
		<%@include file="include.jsp"%>
		<style type="text/css">
		#nopage {
			clear: both;
			width: 449px;
			height: auto;
			margin: 250px auto 0 auto
		}
		</style>

	</head>
	<body>
		<div id="nopage">
			<img src="${context}/images/ct404.png" alt="<bean:message key="page.common.404.title" />" />
		</div>
	</body>
</html>