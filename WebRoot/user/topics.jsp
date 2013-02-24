<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>
			<logic:empty name="re"><bean:message key="page.user.topics.title.topic" /></logic:empty><logic:notEmpty name="re"><bean:message key="page.user.topics.title.reply" /></logic:notEmpty>&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="forum" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>
					<logic:empty name="re">
						<bean:message key="page.user.topics.title.topic" />
					</logic:empty>
					<logic:notEmpty name="re">
						<bean:message key="page.user.topics.title.reply" />
					</logic:notEmpty>
				</div>
				<div id="sidebar" class="fleft clearfix">
						<jsp:include flush="true" page="../_operationBlock.jsp" />
						<jsp:include flush="true" page="../_adBlock.jsp" />
					</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<logic:empty name="re">
							<li class="current"><bean:message key="page.user.topics.title.topic" /></li><li class="normal"><a href="${context }/user.shtml?method=listMyTopics&re=re"><bean:message key="page.user.topics.title.reply" /></a></li><li class="normal"><a href="${context }/gt.action?method=personal">我的小组话题</a></li>
							</logic:empty>
							<logic:notEmpty name="re">
							<li class="normal"><a href="${context }/user.shtml?method=listMyTopics"><bean:message key="page.user.topics.title.topic" /></a></li><li class="current"><bean:message key="page.user.topics.title.reply" /></li><li class="normal"><a href="${context }/gt.action?method=personal">我的小组话题</a></li>
							</logic:notEmpty>
						</ul>
					</div>
					<pg:pager items="${count}" url="user.shtml" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="re" />
						<div class="mid_block">
							<ul class="artList">
								<logic:notEmpty name="models">
								<logic:iterate id="model" indexId="index" name="models">
								<li class="clearfix">
									<logic:empty name="re">
									<div class="fleft">
										<a href="topic/${model.topic.topicId }"><community:limit maxlength="28" value="${model.topic.topicTitle}" /></a>
									</div>
									</logic:empty>
									<logic:notEmpty name="re">
									<div class="fleft">
										<span class="color_gray"><bean:message key="page.user.topics.reply" />: </span><a href="topic/${model.topic.topicOriginId }">${model.parent.topicTitle}</a>
									</div>
									</logic:notEmpty>
									<logic:empty name="re">
									<div class="fleft font_mid color_orange">
										&nbsp;&nbsp;${model.topic.topicReNum }/${model.topic.topicHits }
									</div>
									<div class="fright font_mid color_gray">
										<community:date limit="16" start="2" value="${model.topic.topicTime}" />
									</div>
									</logic:empty>
								</li>
								</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="models">
								<li>
									<bean:message key="common.noContent" />
								</li>
								</logic:empty>
							</ul>
						</div>
						<pg:index>
						<div class="pageindex_bottom">
							<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
						</div>
						</pg:index>
					</pg:pager>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"/>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"/>
	</body>
</html>