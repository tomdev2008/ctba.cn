<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="rss.feed.title.timeline" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp"/>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="rss.feed.title.timeline" />
					</div>
					<div class="fright">
						<bean:message key="page.timeline.today" arg0="${eventCntToday }"/>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block top_notice tl clearfix">
						<h2 class="color_orange">
							<strong><bean:message key="page.timeline.title" /></strong>
						</h2>
						<bean:message key="page.timeline.description" />
					</div>
					<ul id="tabs_gray" class="lightgray_bg graylink">
						<li class="current">朋友们的新鲜事</li><li class="normal"><a href="user.shtml?method=timeline">大家的新鲜事</a></li>
					</ul>
					<pg:pager items="${count}" url="user.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
						<pg:param name="method"/><pg:param name="date"/>
						<div id="event_all" class="clearfix">
							<ul>
								<logic:notEmpty name="models">
									<logic:iterate id="model" name="models" indexId="index">
										<li>
											<div class="fleft userface">
												<a href="<ctba:wrapuser user="${model.user}" linkonly="true"/>">
													<img src="<community:img value="${model.user.userFace}" type="sized" width="32" />" width="32" height="32" align="absmiddle"/>
												</a>
											</div>
											<div class="fleft w450">
												${model.eventStr }
											</div>
											<div class="fright color_gray pr5 font_mid">
												<community:formatTime time="${model.event.updateTime }"/>
											</div>
										</li>
									</logic:iterate>
								</logic:notEmpty>
							</ul>
						</div>
						<pg:index>
							<div class="pageindex nborder">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>&nbsp;<a href="rss.shtml"><img src="images/icons/feed.png" align="absmiddle" /></a>
							</div>
						</pg:index>
					</pg:pager>
				</div>
			</div>
			<div id="area_right">
				<div class="state">
					<div class="titles"><bean:message key="page.timeline.old" /></div>
					<div class="ew">
						<a class="rss" href="timeline">ALL</a>
					</div>
					<ul class="right_menu">
						<c:forEach items="${dateList}" var="dateStr">
							<li>
								<c:if test="${curreply_date eq dateStr}"><strong></c:if>
									<a href="user.shtml?method=timeline&date=${dateStr }">${dateStr }</a>
								<c:if test="${curreply_date eq dateStr}"></strong></c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="state" style="padding-left:20px">
					<iframe id="baiduSpFrame" border="0" vspace="0" hspace="0" marginwidth="0" marginheight="0" framespacing="0" frameborder="0" scrolling="no" width="160" height="600" src="http://spcode.baidu.com/spcode/spstyle/style1907.jsp?tn=gladstone_sp&ctn=0&styleid=1907"></iframe>
				</div>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>