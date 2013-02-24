<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/include.jsp"%>
<%--<jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>--%>
<c:if test="${not empty hotTopics}">
	<div class="state clearfix">
		<div class="titles">
			最热专题文章
		</div>
		<ul>
			<c:if test="${not empty hotTopics}">
				<c:forEach items="${hotTopics}" var="model" varStatus="index">
					<li class="tlist_wrap clearfix">
						<div class="tlist_icon icons_newticket"></div>
						<div class="tlist_text">
							<a href="subject/article/${model.id}" title="${model.title}">
								${model.title}
							</a>
						</div>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
</c:if>
<c:if test="${not empty newTopics}">
	<div class="state clearfix">
		<div class="titles">
			最新专题文章
		</div>
		<ul>
			<c:if test="${not empty newTopics}">
				<c:forEach items="${newTopics}" var="model" varStatus="index">
					<li class="tlist_wrap clearfix">
						<div class="tlist_icon icons_newticket"></div>
						<div class="tlist_text">
							<a href="subject/article/${model.id}" title="${model.title}">
								${model.title}
							</a>
						</div>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
</c:if>
<jsp:include page="../_adBlockRight.jsp"></jsp:include>
<div class="state">
	<ul class="infobar">
		<li class="icons_feed">
			<a href="rss.shtml" target="_blank"><bean:message key="rss.feed"/></a>
		</li>
	</ul>
	<hr size="1" color="#eeeeee" />
	<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
</div>