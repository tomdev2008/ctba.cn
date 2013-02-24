<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<div class="state">
					<form action="g.action?method=list" method="post">
						<input type="text" name="groupSearchKey" id="groupSearchKey" class="search_input" <c:if test="${empty groupSearchKey}">value="<bean:message key="page.group.search.hint" />" onfocus="if(value=='<bean:message key="page.group.search.hint" />') {value=''}" onblur="if(value=='') {value='<bean:message key="page.group.search.hint" />'}"</c:if><c:if test="${not empty groupSearchKey}">value="${groupSearchKey }"</c:if>  />
						<input type="submit" class="input_btn" value="<bean:message key="page.group.search.button" />"/>
					</form>
</div>