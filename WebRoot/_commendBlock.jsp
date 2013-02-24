<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:if test="${not empty commendList}">
	<div class="state" id="commendList">
		<div class="titles">
            <bean:message key="page.common.state.commend"/>
		</div>
		<ul>
			<c:forEach items="${commendList}" var="model">
				<li class="tlist_wrap clearfix">
					<div class="tlist_icon icons_newticket"></div>
					<div class="tlist_text">
						<a href="${model.link}">${model.label}</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>