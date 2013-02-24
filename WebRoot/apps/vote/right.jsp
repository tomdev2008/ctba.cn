<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="community"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<c:if test="${not empty newestVoteList}">
	<div class="state clearfix">
		<div class="titles">
			最新投票
		</div>
		<ul>
			<c:forEach items="${newestVoteList}" var="model">
			<li class="tlist_wrap clearfix">
				<div class="tlist_icon icons_newticket"></div>
				<div class="tlist_text">
					<a href="vote/${model.id}">
						${model.question}
					</a>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${not empty hotVoteList}">
	<div class="state clearfix">
		<div class="titles">
			热门投票
		</div>
		<ul>
			<c:forEach items="${hotVoteList}" var="model">
			<li class="tlist_wrap clearfix">
				<div class="tlist_icon icons_newticket"></div>
				<div class="tlist_text">
					<a href="vote/${model.id}">
						${model.question}
					</a>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<div class="state">
	<ul class="infobar">
		<li class="icons_feed">
			<a href="rss.shtml" target="_blank">订阅本站</a>
		</li>
	</ul>
	<hr size="1" color="#eeeeee" />
	<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
</div>