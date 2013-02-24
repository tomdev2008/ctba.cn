<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="community"%>
<div class="titles"><bean:message key="page.news.category"/></div>
<ul class="infobar">
    <c:if test="${not empty cats}">
        <c:forEach items="${cats}" var="model" varStatus="index">
            <li class="icons_newticket"><a href="news/list/${model.id}"><community:limit maxlength="13" value="${model.name}" /></a></li>
        </c:forEach>
    </c:if>
</ul>
