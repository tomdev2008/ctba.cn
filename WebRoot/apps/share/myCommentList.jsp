<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>我收到的评论&nbsp;-&nbsp;分享&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			function maxWidth(){
				var maxWidth = parseInt($('#reply_info_mid img').css('max-width'));
				$('#reply_info_mid img').each(function(){
					if ($(this).width() > maxWidth)
						$(this).width(maxWidth);
				});
			}
			if (window.attachEvent) window.attachEvent("onload", maxWidth);
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp"><jsp:param name="submenu" value="app" /></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="share/">分享</a>&nbsp;>&nbsp;我收到的评论
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="_logo.jsp"></jsp:include>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlockMini.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="normal"><a href="share.shtml?method=shares">我的分享</a></li><li class="normal"><a href="share.shtml?method=shares&type=all">大家的分享</a></li>
							<li class="current">
								我收到的评论
							</li>
							<li class="normal"><a href="share.action?method=builder">分享秀</a></li>
						</ul>
					</div>
					<logic:notEmpty name="models">
						<div class="mid_block top_blank_wide clearfix">
							<pg:pager items="${count}" url="share.action" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="method" />
								<div>
									<logic:iterate id="map" indexId="index" name="models">
										<div class="reply_list orangelink clearfix">
											<div class="reply_user">
												<img src="<community:img value="${map.user.userFace }" type="sized" width="32" />" width="32" height="32" title="${map.user.userName}" alt="${map.user.userName}" />
											</div>
											<div id="reply_info_mid" class="linkblue_b">
												<a href="u/${map.user.userId}">${map.user.userName}</a>
												<span class="color_gray">
													<community:ipData value="${map.comment.ip }" />
												</span>
											</div>
											<div class="reply_date">
												<div class="fleft">
													<span class="font_small color_gray">
														<community:date value="${map.comment.updateTime }" start="2" limit="16" />
														<c:if test="${not empty __sys_username and __sys_username eq map.comment.username}">
															|&nbsp;<a href='javascript:void(0);' onclick="J2bbCommon.confirmURL('您确定要删除么？','${basePath }share.shtml?method=deleteShareComment&id=${map.comment.id }&from=myList');return false;">删除</a>
														</c:if>

													</span>
												</div>
											</div>
											<div id="reply_content_mid">
												<community:topic value="${map.comment.body}" />
											</div>
										</div>
									</logic:iterate>
								</div>
								<pg:index>
									<div class="pageindex nborder">
										<jsp:include flush="true" page="../../common/jsptags/simple.jsp"></jsp:include>
									</div>
								</pg:index>
							</pg:pager>
						</div>
					</logic:notEmpty>

				</div>
			</div>
			<div id="area_right">
				<jsp:include page="../right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>