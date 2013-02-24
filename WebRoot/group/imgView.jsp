<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>${imageModel.name}&nbsp;-&nbsp;${groupModel.name}&nbsp;-&nbsp;<bean:message key="page.group.title.gallery"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			function checkForm() {
				J2bbCommon.removeformError("topicContent");
				var content = $("#topicContent").val();
				if(content==''||content.length>500){
					J2bbCommon.formError("topicContent","<bean:message key="page.group.image.error.comment" />","red");
					return false;
				}
				$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			function copyAddr(){
				copyToClip($('#imageAddress').val());
				alert("<bean:message key="page.group.image.copy.succeed" />");
			}
			function copyToClipboard(txt) {
				if(window.clipboardData){
					window.clipboardData.clearData();
					window.clipboardData.setData("Text", txt);
				}else if(navigator.userAgent.indexOf("Opera") != -1){
					alert("<bean:message key="page.group.image.copy.failed" />");
				}else if (window.netscape){
					try {
						netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
					}catch (e){
						alert("<bean:message key="page.group.image.copy.failed" />");
					}
				}
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
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="group" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.group.title.gallery"/>&nbsp;&gt;&nbsp;<a href="group/gallery/${groupModel.id}">${groupModel.name	}</a>
					</div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp"/>
				</div>
				<div class="left_block">
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="currImage"/>
					</jsp:include>
					<div class="graylink2 center" style="padding:19px">
						<p>
							<c:if test="${not empty prevModel}">
								<a href="group/gallery/photo/${prevModel.id }">
									<img src="<community:img value="${imageModel.path}" type="none" />" onload='if(this.width>700){ this.width=700;}' />
								</a>
							</c:if>
							<c:if test="${empty prevModel}">
								<a href="group/gallery/${groupModel.id }">
									<img src="<community:img value="${imageModel.path}" type="none" />" onload='if(this.width>700){ this.width=700;}' />
								</a>
							</c:if>
						</p>
						<p align="center">
							<c:if test="${not empty nextModel}">
								<img src="images/icons/pico_left.gif" align="absmiddle" />&nbsp;<a href="group/gallery/photo/${nextModel.id }"><bean:message key="page.group.image.prev" /></a>&nbsp;&nbsp;&nbsp;
							</c:if>
							<bean:message key="page.group.image.hits" arg0="${imageModel.hits }" />
							&nbsp;&nbsp;&nbsp;
							<c:if test="${imageModel.username eq __sys_username }">
								<a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm" />','${basePath }img.shtml?method=deleteGroupImg&id=${imageModel.id }');return false;"><bean:message key="page.common.button.delete" /></a>&nbsp;&nbsp;&nbsp;
							</c:if>
							<c:if test="${not empty prevModel}">
								<a href="group/gallery/photo/${prevModel.id }"><bean:message key="page.group.image.next" /></a>&nbsp;<img src="images/icons/pico_right.gif" align="absmiddle" />
							</c:if>
						</p>
					</div>
					<div style="border-top:1px solid #ddd;padding:10px">
						<c:if test="${__user_is_editor or (imageModel.username eq __sys_username) }">
							<form action="img.shtml?method=saveImage" method="post">
								<bean:message key="page.group.image.creator" />&nbsp;&nbsp;${imageModel.username}
								<input name="imageId" value="${imageModel.id }" type="hidden" />
								&nbsp;<bean:message key="page.group.image.imageTitle" />:&nbsp;&nbsp;<input class="font_mid color_gray formInput" style="width:200px;" value="${imageModel.name}" id="imageName" name="imageName"/>
								&nbsp;<input type="submit" class="input_btn"  value="<bean:message key="page.common.button.edit" />"/>
							</form>
						</c:if>
						<bean:message key="page.group.image.url" />&nbsp;&nbsp;<input class="font_mid color_gray formInput" style="width:400px;" value="http://static.ctba.cn/files/${imageModel.path}" id="imageAddress"/>&nbsp;&nbsp;<input type="button" class="input_btn" onclick="copyAddr();" value="<bean:message key="page.group.image.url.copy" />" />
					</div>
				</div>
				<div class="left_block top_blank_wide orangelink">
					<logic:notEmpty name="commentMapList">
						<pg:pager items="${count}" url="img.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
							<pg:param name="method" />
							<pg:param name="id" />
							<logic:iterate id="map" indexId="index" name="commentMapList">
								<div>
									<div class="reply_list orangelink clearfix">
										<div class="reply_user">
											<img src="<community:img value="${map.user.userFace }" type="sized" width="32" />" width="32" height="32" title="${map.user.userName}" alt="${map.user.userName}" />
										</div>
										<div id="reply_info" class="linkblue_b">
											<a href="u/${map.user.userId}">${map.user.userName}</a>
											<span class="color_gray"><community:ipData value="${map.comment.ip }"/></span>
										</div>
										<div class="reply_date">
											<div class="fleft">
												<span class="font_small color_gray"><community:date value="${map.comment.updateTime }" start="2" limit="16" />&nbsp;|</span>
											</div>
											<ul class="opt2 fleft">
												<li>
													<span class="opt_arrow"></span>
													<ul>
														<li>
															<c:if test="${not empty __sys_username and __sys_username eq map.comment.username}">
																<a href='img.shtml?method=deleteImgComment&id=${map.comment.id }' onclick="J2bbCommon.confirmURL('您确定要删除么？','img.shtml?method=deleteImgComment&id=${map.comment.id }');return false;">删除本楼</a>
															</c:if>
														</li>
														<c:if test="${not empty __sys_username }">
															<li>
																<a href="javascript:re(${30*(currentPageNumber-1)+index+1 },'${map.user.userName }');">回复本楼</a>
															</li>
														</c:if>
														<li><a href="javascript:window.scroll(0,0)">返回顶部</a></li>
													</ul>
												</li>
											</ul>
										</div>
										<div id="reply_content">
											<community:topic value="${map.comment.body}"/>
										</div>
									</div>
								</div>
							</logic:iterate>
							<div class="pageindex nborder">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
							</div>
						</pg:pager>
					</logic:notEmpty>
					<div class="line_block_b right darkgraylink">
						<a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop" /></a>&nbsp;|&nbsp;<bean:message key="page.group.topic.commentCnt" arg0="${count }"/>
					</div>
					<jsp:include page="../_loginBlock.jsp"></jsp:include>
				</div>
				<c:if test="${not empty __sys_username}">
					<div class="title_bar">
						评论此照片:&nbsp;${imageModel.name}
					</div>
					<div class="left_block top_blank_wide radius_top_none">
						<div class="replyform">
							<form action="img.shtml?method=saveImgComment" method="post" onsubmit="return checkForm();">
								<div class="replyitems">
									<ul>
										<li>
											<community:ubb/>
										</li>
										<li>
											<input name="imageId" value="${imageModel.id }" type="hidden" />
											<input type="hidden" name="para_reply_to" id="para_reply_to" value="${imageModel.username}" />
											<textarea name="body" id="topicContent" class="topicContent" style="width:554px;height:150px"></textarea>
										</li>
										<li class="attachment">
											<input type="submit" class="input_btn" id="submitButton" value="<bean:message key="page.group.image.button.comment" />" />
										</li>
									</ul>
								</div>
							</form>
						</div>
					</div>
				</c:if>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>