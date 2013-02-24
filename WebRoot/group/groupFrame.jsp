<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
	</head>
	<body>
		<div class="titles" onclick="$('#myGroups').toggle();">${title }</div>
		<div id="myGroups">
			<ul class="infobar">
				<logic:notEmpty name="groupsMap">
				<logic:iterate id="model" indexId="index" name="groupsMap">
				<li><a href="g/${model.group.id}" title="${model.group.name}"><g:limit maxlength="10" value="${model.group.name}" /></a>&nbsp;<span class="font_small color_orange">(${model.userCnt })</span></li>
				</logic:iterate>
				</logic:notEmpty>
			</ul>
		</div>
	</body>
</html>