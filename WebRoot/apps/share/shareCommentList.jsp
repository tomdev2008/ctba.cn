<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>${shareModel.label }&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			function maxWidth(){
				var maxWidth = parseInt($('#reply_info_mid img').css('max-width'));
				$('#reply_info_mid img').each(function(){
					if ($(this).width() > maxWidth)
						$(this).width(maxWidth);
				});
			}
			if (window.attachEvent) window.attachEvent("onload", maxWidth);
			function checkForm() {
				J2bbCommon.removeformError("topicContent");
				var content = $("#topicContent").val();
				if(content==''||content.length>500){
					J2bbCommon.formError("topicContent","内容不能为空，不能多于500字","red");
					return false;
				}
				$("#submitButton").val("提交中，请稍候...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			//==============
			// 回复某楼的用户
			//==============
			function re(index,username){
				$("#para_reply_to").val(username);
				$("#topicContent").val("@"+username+" "+index+"#\n");
				$("#topicContent").focus();
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp"><jsp:param name="submenu" value="app" /></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="share/">分享</a>&nbsp;>&nbsp;${shareModel.username }&nbsp;的分享
				</div>
				<div id="sidebar" class="fleft clearfix">
					<%--<jsp:include page="_logo.jsp"></jsp:include>--%>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlockMini.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block top_notice tl clearfix">
						<h2 style="line-height: 23px">
							<strong class="color_orange"> ${shareModel.username }: </strong>
							<c:if test="${not empty shareModel.url}">
								<a href="${shareModel.url }" target="_blank">${shareModel.label }</a>&nbsp;<span class="color_gray">(链接)</span><img src="images/icons/shape_move_forwards.png" align="absmiddle" alt="在新窗口打开分享链接" title="在新窗口打开分享链接" />
							</c:if>
							<c:if test="${empty shareModel.url}">
							${shareModel.label }
							</c:if>
						</h2>
						<hr size="1" color="#eeeeee" style="_margin-top: 0" />
						<p class="color_gray">
							<c:if test="${shareModel.state eq 1}">
							<span class="font_mid color_orange">[悄悄地]</span></c:if>分享于&nbsp;<com:date limit="16" start="2" value="${shareModel.updateTime }" />&nbsp;|&nbsp;共&nbsp;${count}&nbsp;评论&nbsp;|&nbsp;${shareModel.hits}&nbsp;点击&nbsp;|&nbsp;<a href="feedback.action?method=form&type=share&id=${shareModel.id }" class="orange_link color_orange">举报</a>&nbsp;&nbsp;&nbsp;
						</p>
					</div>
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="current tlink">
								评论列表
							</li>
							<li class="normal tlink">
								<a href="share.shtml?method=shares&wrap-uid=${authorModel.userId }">
									${shareModel.username }&nbsp;的分享
								</a>
							</li>
						</ul>
					</div>
					<logic:notEmpty name="models">
						<div class="mid_block top_blank_wide clearfix">
							<pg:pager items="${count}" url="share.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
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
														<c:if test="${not empty __sys_username}">
															|&nbsp;<a href="javascript:re(${30*(currentPageNumber-1)+index+1 },'${map.user.userName }');">回复</a>
														</c:if>
														<c:if test="${not empty __sys_username and __sys_username eq map.comment.username}">
															|&nbsp;<a href='javascript:void(0);' onclick="J2bbCommon.confirmURL('您确定要删除么？','${basePath }share.shtml?method=deleteShareComment&id=${map.comment.id }');return false;">删除</a>
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

					<c:if test="${not empty __sys_username}">
						<div class="mid_block top_blank_wide clearfix radius_all lightgray_bg" style="padding: 10px 10px 3px 10px; _padding: 10px">
							<form action="share.shtml?method=saveShareComment" method="post" onsubmit="return checkForm();">
								<community:ubb />
								<input name="sid" value="${shareModel.id }" type="hidden" />
								<input type="hidden" name="para_reply_to" id="para_reply_to" value="${shareModel.username}"/>
								<textarea class="ftt" style="width: 536px" name="body" id="topicContent"></textarea>
								<br />
								<input type="submit" class="input_btn btn_mt" id="submitButton" value="发表评论" />
							</form>
						</div>
					</c:if>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="../right.jsp" flush="true" />
			</div>
		</div>
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>