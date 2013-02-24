<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.online.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.online.title" />
				</div>
				<div class="left_block top_blank_wide">
					<pg:pager items="${count}" url="user.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
						<pg:param name="method"/>
						<pg:index>
							<div class="pageindex_list">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
							</div>
						</pg:index>
						<ul class="online_wrap clearfix">
							<logic:notEmpty name="models">
								<logic:iterate id="model" name="models" indexId="index">
									<li class="radius_all">
										<p class="orangelink online_top">
											<a href="u/${model.user.userId }"><img src="<community:img value="${model.user.userFace}" type="sized" width="16" />" class="userFace_border" width="16" height="16" align="absmiddle"/></a>&nbsp;<a href="u/${model.user.userId }">${model.user.userName }</a>&nbsp;<span class="color_gray"><community:ipData value="${model.online.ip }"/></span>
										</p>
										<c:if test="${not empty __sys_username }">
											<p class="icons_email online_bottom"><a href="message.action?method=form&to=<community:url value="${model.user.userName }" />"><bean:message key="page.online.msg" /></a></p>
										</c:if>
									</li>
								</logic:iterate>
							</logic:notEmpty>
						</ul>
						<pg:index>
							<div class="pageindex_list_bottom">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
							</div>
						</pg:index>
					</pg:pager>
				</div>
			</div>
			<div id="area_right">
				<div class="adsenes_right_block">
					<script type="text/javascript"><!--
						google_ad_client = "pub-3911382285138100";
						/* 180x150, @ 09-2-23 */
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
							<a href="rss.shtml" target="_blank"><bean:message key="rss.feed" /></a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>