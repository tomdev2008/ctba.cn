<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>${topic.title }&nbsp;-&nbsp;<bean:message key="menu.subject.navigate" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="subject/"><bean:message key="menu.subject.navigate" /></a>&nbsp;&gt;&nbsp;<a href="subject.shtml?method=listTopics&sid=${subject.id }">${subject.title}</a>&nbsp;&gt;&nbsp;正文
					</div>
					<div class="fright">
					<jsp:include page="../common/_share.jsp">
						<jsp:param name="share_type" value="4"/>
						<jsp:param name="share_id" value="${topic.id}"/>
						<jsp:param name="share_url" value="${basePath }/subject/article/${topic.id}"/>
					</jsp:include>
					</div>
				</div>
				<div class="left_block radius clearfix">
					<div class="subject_headline">
						<h1><b>${topic.title }</b></h1>
					</div>
					<div class="subject_intro_wrap clearfix" style="font-family:Tahoma">
						<div class="color_gray">
							发表于&nbsp;<com:date value="${topic.createTime }" start="2" limit="16" />&nbsp;&nbsp;本文有&nbsp;<span class="color_orange">${topic.hits }</span>&nbsp;位读者
						</div>
						<div class="subject_intro_detail">
							<b>专题简介：</b><com:html value="${subject.description } " />
						</div>
					</div>
					<div class="subject_content">
						<%--<com:topic value="${topic.content }"/>--%>
						${topic.content }
						
						<br /><br />
							<script type="text/javascript">
								<!--
								google_ad_client = "pub-3911382285138100";
								/* 468x60, @ 08-2-20 */
								google_ad_slot = "6855482256";
								google_ad_width = 468;
								google_ad_height = 60;
								//-->
							</script>
							<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
						
					</div>
					<div class="subject_origin color_gray right">
						<c:if test="${not empty prevTopic}">
							<img align="absmiddle" src="images/icons/pico_left.gif"/>&nbsp;<a href="article/${prevTopic.id}"><com:limit maxlength="20" value="${prevTopic.title }"/></a>&nbsp;|
						</c:if>
						<c:if test="${not empty nextTopic}">
							<a href="subject/article/${nextTopic.id}"><com:limit maxlength="20" value="${nextTopic.title }"/></a>&nbsp;<img align="absmiddle" src="images/icons/pico_right.gif"/>
						</c:if>
					</div>
					<div class="subject_origin clearfix">
						<div class="fleft">
						<logic:equal value="e" name="topic" property="type">
						<img src="images/icons/book_open.png" align="absmiddle" />&nbsp;<a href="entry/${topic.entryId }">文章来源 - 扯谈博客</a>
						</logic:equal>
						<logic:equal value="t" name="topic" property="type">
						<img src="images/icons/book_open.png" align="absmiddle" />&nbsp;<a href="t/${topic.topicId }">文章来源 - 扯谈论坛</a>
						</logic:equal>
						</div>
						<div class="fright darkgraylink color_gray">
							<a href="subject/${subject.id }">返回专题</a>&nbsp;|&nbsp;<a href="javascript:window.scroll(0,0);">返回正文</a>
						</div>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>