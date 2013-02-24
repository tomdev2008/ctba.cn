<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<select style="padding:3px" name="commendTagsSelect" id="commendTagsSelect" onchange="document.getElementById('tags').value=document.getElementById('tags').value+this.options[this.selectedIndex].value+',';">
	<option><bean:message key="page.group.widget.tag"/></option>
	<c:forEach items="${currType.tags}" var="tag">
	<option value="${tag }">${tag }</option>
	</c:forEach>
</select>