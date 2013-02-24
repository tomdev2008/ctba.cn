<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
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
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;${group.name }
					</div>
					<div class="fright">
						<c:if test="${true eq logined and not waiting}">
							<c:if test="${false eq isUser}">
								<img src="images/icons/group_add.png" align="absmiddle" />&nbsp;<a href="g.action?method=mine&action=join&gid=${group.id }">加入本小组</a>
							</c:if>
						</c:if>
					</div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp" />
				</div>
				<div id="topicList" class="left_block top_blank">
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="topic" />
					</jsp:include>
					<div class="top_blank">
						<c:if test="${not empty topicsMap}">
							<pg:pager items="${count}" url="gt.action" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="gid" />
								<pg:param name="url" />
								<pg:param name="method" />
								<pg:index>
									<div class="pageindex_list">
										<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
									</div>
								</pg:index>
								<div id="tab_group">
									<c:forEach items="${topicsMap}" var="model" varStatus="status">
										<ul class="column_5c clearfix <c:if test="${status.index % 2 == 1}">yellow_bg</c:if>">
											<li class="col3-5 center <c:if test="${model.topic.topState eq 1}">tag_top</c:if><c:if test="${model.topic.topState eq 0}">tag_none</c:if>"></li>
											<li class="col63-5">
												&nbsp;<a href="group/topic/${model.topic.id}" title="${model.topic.title}"><com:limit value="${model.topic.title}" maxlength="28"  /></a>&nbsp;&nbsp;<span class="font_small color_orange">${model.reCnt}&nbsp;replies</span>&nbsp;<span class="font_small color_gray">...&nbsp;${model.topic.hits}&nbsp;hits</span>
											</li>
											<li class="col17 list_sp2">
											
												<a href="<ctba:wrapuser user="${model.author}" linkonly="true"/>"><img src="<com:img value="${model.author.userFace }" type="mini" />" width="16" height="16" class="userFace_border" align="absmiddle" /></a>&nbsp;&nbsp;<a href="<ctba:wrapuser user="${model.author}" linkonly="true"/>" class="userName">${model.author.userName }</a>
											</li>
											<li class="col14 font_small color_gray">
												<com:date value="${model.topic.createTime }" start="2" limit="16" />
											</li>
											<li class="col2">
												<ul class="opt">
													<li>
														<a href="javascript:void(0);">
															<img src="images/icons/options.gif" />
														</a>
														<ul>
															<c:if test="${__group_is_manager}">
																<li>
																	<a href="gt.action?method=dealTop&tid=${model.topic.id }">切换置顶</a>
																</li>
															</c:if>
															<c:if test="${model.isAuthor eq true}">
																<li>
																	<a href="gt.action?method=form&tid=${model.topic.id }&gid=${group.id }">修改主题</a>
																</li>
																<li>
																	<a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath}gt.action?method=delete&tid=${model.topic.id }');return false;" class="bluelink">删除主题</a>
																</li>
															</c:if>
															<li>
																<a href="javascript:window.scroll(0,0)">返回顶部</a>
															</li>
														</ul>
													</li>
												</ul>
											</li>
										</ul>
									</c:forEach>
								</div>
							</pg:pager>
						</c:if>
						<div class="group_search_block clearfix" style="margin:5px 0 10px;padding-left:28px">
							<form action="search.action" method="post" class="fleft">
								<input class="search_input" name="title" value="搜索小组话题" style="color: #ddd;width: 280px" onfocus="if(value=='搜索小组话题') {value=''}" onblur="if(value=='') {value='搜索小组话题'}" />
								<input type="radio" name="searchType" value="4" class="hide" checked="checked"></input>
								<input type="submit" value="搜索" class="input_btn" />
							</form>
							<c:if test="${true eq __group_is_manager}">
								<span class="fright icons_group orangelink" style="height:25px;line-height:25px;margin-right:10px;padding-left:20px;background-position:-8px -260px">
									<a href="group.shtml?method=invite&gid=${group.id }">邀请朋友加入${group.name }</a>
								</span>
							</c:if>
						</div>
						<div class="adsense_w728">
							<script type="text/javascript"><!--
								google_ad_client = "pub-3911382285138100";
								/* 728x90, at 09-2-21 */
								google_ad_slot = "7055898710";
								google_ad_width = 728;
								google_ad_height = 90;
								//-->
							</script>
							<script type="text/javascript"
								 src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
							</script>
						</div>
					</div>
				</div>
				<c:if test="${not empty newImageList}">
					<div class="left_block">
						<div class="board_sep">
							<div class="gtitle">
								<span class="gtitle_text" style="float:none">
									<img src="images/groupix.png" align="absmiddle" />&nbsp;<bean:message key="page.group.image.newest" />
								</span>
								<span class="group_type">
									<a class="gt" href="group/gallery/${group.id }">浏览全部相片</a>
								</span>
							</div>
							<div class="clearfix">
								<c:forEach items="${newImageList}" var="model" varStatus="status">
									<div class="group_photo_wrap">
										<div class="group_photo_pic">
											<a class="photo" href="group/gallery/photo/${model.img.id }" >
												<img src="<com:img value="${model.img.path }" type="default"/>" alt="${model.img.name}" />
											</a>
										</div>
										<div class="group_photo_info">
											<span class="color_orange">
												<g:limit maxlength="25" value="${model.img.name}" />
											</span>
											<br />
											<span class="color_gray">
												<bean:message key="page.group.image.createTime" />:<g:date limit="11" start="0" value="${model.img.createTime }" />
												<br />
												<bean:message key="page.group.image.creator" />:<a href="userpage/<g:url value="${model.img.username }"/>">${model.img.username }</a>
											</span>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<br class="clear" />
					</div>
				</c:if>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>