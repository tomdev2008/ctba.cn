<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>我的小组话题&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
		<script type="text/javascript">
			//==============
			// ie6 实现 css2 伪类
			//==============
			function hover() {
				$("ul.opt li").mouseover(function(){
					$(this).addClass("hover");
				}).mouseout(function(){
					$(this).removeClass("hover");
				});
			}
			if (window.attachEvent) window.attachEvent("onload", hover);
		</script>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper" class="clearfix">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;我的小组话题
					</div>
					<div class="fright">
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
						<jsp:include flush="true" page="../_operationBlock.jsp" />
						<jsp:include flush="true" page="../_adBlock.jsp" />
					</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="normal"><a href="${context }/user.shtml?method=listMyTopics"><bean:message key="page.user.topics.title.topic" /></a></li><li class="normal"><a href="${context }/user.shtml?method=listMyTopics&re=re"><bean:message key="page.user.topics.title.reply" /></a></li><li class="current">我的小组话题</li>
						</ul>
					</div>
					<pg:pager items="${count}" url="gt.action" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<div class="mid_block">
							<ul class="artList">
								<logic:notEmpty name="topicsMap">
								<logic:iterate id="model" indexId="index" name="topicsMap">
								<li class="clearfix">
									<div class="fleft">
										<a href="group/topic/${model.topic.id }"><community:limit maxlength="28" value="${model.topic.title}" /></a>
									</div>
									<div class="fleft font_mid color_orange">
										&nbsp;&nbsp;<com:date value="${model.topic.createTime }" start="2" limit="16" />
									</div>
									<div class="fright font_mid color_gray">
										
										<ul class="opt">
													<li>
														<a href="javascript:void(0);">
															<img src="images/icons/options.gif" />
														</a>
														<ul>
																<li>
																	<a href="gt.action?method=form&tid=${model.topic.id }&gid=${model.topic.groupModel.id }">修改主题</a>
																</li>
																<li>
																	<a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath}gt.action?method=delete&return=personal&tid=${model.topic.id }');return false;" class="bluelink">删除主题</a>
																</li>
															<li>
																<a href="javascript:window.scroll(0,0)">返回顶部</a>
															</li>
														</ul>
													</li>
												</ul>
									</div>
								</li>
								</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="topicsMap">
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
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>