<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<div class="state">
	<jsp:include page="../_searchBlock.jsp"></jsp:include>
</div>
<c:if test="${not empty refTopics}">
	<div class="state" id="refTopics">
        <div class="titles"><bean:message key="page.board.widget.referenceTopic"/></div>
		<ul>
			<c:forEach items="${refTopics}" var="model">
				<li class="tlist_wrap clearfix">
					<div class="tlist_icon icons_newticket"></div>
					<div class="tlist_text"><a href="${model.url}">${model.label}</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${not empty boardHotTopics}">
	<div class="state" id="hotTopics">
        <div class="titles"><bean:message key="page.board.widget.hotTopic"/></div>
		<ul>
			<c:forEach items="${boardHotTopics}" var="model">
				<li class="tlist_wrap clearfix">
					<div class="tlist_icon icons_newticket"></div>
					<div class="tlist_text">
						<a href="topic/${model.topicId}">${model.topic.topicTitle}</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>
<div class="adsenes_right_block">
	<script type="text/javascript"><!--
		google_ad_client = "pub-3911382285138100";
		/* 180x150, 09-2-23 */
		google_ad_slot = "3404358666";
		google_ad_width = 180;
		google_ad_height = 150;
		//-->
	</script>
	<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
</div>
<div class="state">
	<ul class="infobar">
		<li class="icons_feed">
			<a href="rss.shtml?type=bbs" target="_blank"><bean:message key="rss.feed"/></a>
		</li>
	</ul>
	<hr size="1" color="#eeeeee" />
	<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
</div>
