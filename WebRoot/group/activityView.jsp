<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>${model.title }&nbsp;-&nbsp;${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
		<script type="text/javascript">

			//==============
			// 回复某楼的用户
			//==============
			function re(index,username){
				$("#para_reply_to").val(username);
				$("#topicContent").val("@"+username+" "+index+"#\n");
				$("#topicContent").focus();
			}

			function checkForm() {
				J2bbCommon.removeformError("topicContent");
				var content = $("#topicContent").val();
				if(content==''||content.length>5000){
					J2bbCommon.formError("topicContent","<bean:message key="page.group.activity.error.comment" />","red");
					return false;
				}
				$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			
			function checkJoinForm() {
				J2bbCommon.removeformError("join_cnt");
				var join_cnt = $("#join_cnt").val();
				if(join_cnt==''||isNaN(join_cnt)){
					J2bbCommon.formError("join_cnt","请正确填写外挂人数","bottom");
					return false;
				}
				return true;
			}
			
			function maxWidth(){
				var maxWidth = parseInt($('#dc img').css('max-width'));
				$('#dc img').each(function(){
					if ($(this).width() > maxWidth)
						$(this).width(maxWidth);
				});
			}
			if (window.attachEvent) window.attachEvent("onload", maxWidth);
		</script>
        <style type="text/css">
        .activity_info { color: #666; width: 360px; line-height: 22px; padding: 10px 15px; margin: 0 0 10px 0; border: 1px solid #eee; background: #fff url(../../../images/flower_bg.gif) no-repeat 290px 83px }
        .activity_info ul li.options { margin: 5px 0 }
        .activity_info ul li.options a { margin: 0 5px 0 0 }
        </style>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;
						<a href="${context }/group/"><bean:message
								key="menu.group.indexPage" />
						</a>&nbsp;&gt;&nbsp;
						<a href="group/${group.id }">${group.name }</a>&nbsp;&gt;&nbsp;小组活动
					</div>
					<div class="fright"></div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp" />
				</div>
				<%-- topic content--%>
				<div class="left_block">
					<div class="clearfix">
						<div id="group_tab_wrap">
							<jsp:include page="_tab.jsp" flush="true">
								<jsp:param name="currTab" value="currActivity" />
							</jsp:include>
						</div>
						<div id="uinfo">
							<div id="uhead">
								<img
									src="<com:img value="${author.userFace }" type="sized" width="80" />"
									alt="" width="80" class="group_border" />
							</div>
							<div id="udetail" class="color_orange linkblue">
								<a href="u/${author.userId}" class="userName">${author.userName
									}</a>
								<br />
								<bean:message key="page.group.activity.creator" />
							</div>
						</div>
						<div id="topicstuff" class="font_small color_orange">
							${count}&nbsp;Replies&nbsp;and&nbsp;${model.hits}&nbsp;hits&nbsp;
							<img src="images/icons/btn_default.gif" align="absmiddle" />
						</div>
						<div id="dw">
							<div id="dt">
								<div id="ctl"></div>
								<div id="tl"></div>
								<div id="ctr"></div>
							</div>
							<div id="la"></div>
							<div id="dt2" class="text_shadow">
								<h1>
									<b>${model.title }</b>
								</h1>
							</div>
							<div id="dc">
								<div class="activity_info">
									<ul>
										<li>
											<bean:message key="page.group.activity.startTime"
												arg0="${model.startTime }" />
										</li>
										<li>
											<bean:message key="page.group.activity.endTime"
												arg0="${model.endTime }" />
										</li>
										<li>
											<bean:message key="page.group.activity.place"
												arg0="${model.place }" />
										</li>
										<li>
											<bean:message key="page.group.activity.type"
												arg0="${activityType }" />
										</li>
										<li>
											<strong>人数限制：</strong>
											<c:if
												test="${(not empty model.memberLimit) and (model.memberLimit > 0 )}">${model.memberLimit}人</c:if>
											<c:if
												test="${(empty model.memberLimit) or (model.memberLimit <= 0 )}">无人数限制</c:if>
										</li>
										<li>
											<bean:message key="page.group.activity.memberCnt"
												arg0="${memberCount }" />
										</li>
										<li class="options">
											<c:if test="${empty __sys_username}">
												<a href="http://www.ctba.cn/register"
													title="<bean:message key="page.group.activity.join.register" />"
													class="link_btn"><bean:message
														key="page.group.activity.join.register" />
												</a>
											</c:if>
											<c:if
												test="${(not empty __sys_username) and (not __group_is_waiting)}">
												<c:if test="${(false eq __group_is_user)}">
													<a href="g.action?method=mine&action=join&gid=${group.id }"
														class="link_btn"><bean:message
															key="page.group.activity.join.group" />
													</a>
												</c:if>
											</c:if>

											<c:if test="${model.stopped eq 0}">
												<c:if
													test="${(not empty __sys_username) and (not __group_is_waiting)}">
													<c:if
														test="${(true eq __group_is_user) and (not __group_is_member)  and (not (author.userName eq __sys_username) )}">
														<a href="activity.shtml?method=join&aid=${model.id }"
															class="link_btn"><bean:message
																key="page.group.activity.join" />
														</a>
														<a href="javascript:void(0);"
															onclick="$('#plusArea').toggle();" class="link_btn">我要多带几个人</a>
													</c:if>
												</c:if>
											</c:if>



											<c:if
												test="${(author.userName eq __sys_username) or __group_is_manager or __user_is_editor}">
												<a href="activity.shtml?method=form&aid=${model.id }"
													class="link_btn"><bean:message
														key="page.group.activity.edit" />
												</a>
												<c:if test="${model.stopped eq 0}">
													<a href="activity.shtml?method=toggleStop&aid=${model.id }"
														class="link_btn"><bean:message
															key="page.group.activity.stop" />
													</a>
												</c:if>
												<c:if test="${model.stopped >0}">
													<a href="activity.shtml?method=toggleStop&aid=${model.id }"
														class="link_btn"><bean:message
															key="page.group.activity.stop.reopen" />
													</a>
												</c:if>
											</c:if>
											<c:if test="${__group_is_manager or __user_is_editor}">
												<a
													href="activity.shtml?method=form&amp;copy_src=${model.id }"
													class="link_btn">复制活动</a>
											</c:if>
											<c:if
												test="${(not empty __sys_username) and (__group_is_member) }">
												<a href="activity.shtml?method=quit&aid=${model.id }"
													class="link_btn"><bean:message
														key="page.group.activity.quit" />
												</a>
											</c:if>
										</li>
										<li class="options" id="plusArea" style="display: none;">
											<c:if
												test="${(not empty __sys_username) and (not __group_is_waiting)}">
												<c:if
													test="${(true eq __group_is_user) and (not __group_is_member)  and (not (author.userName eq __sys_username) )}">
													<form method="get" action="activity.shtml"
														onsubmit="return checkJoinForm();">
														<input type="hidden" name="method" value="join">
														<input type="hidden" name="aid" value="${model.id }">
														<span class="color_orange">我要多带<input type="text"
																style="height: 20px; width: 50px;" name="join_cnt"
																id="join_cnt">个人(除我之外)</span>
														<input type="submit" value="参加!" class="input_btn">
													</form>
												</c:if>
											</c:if>
										</li>
									</ul>
									<c:if test="${model.stopped >0}">
										<div class="message_error">
											<bean:message key="page.group.activity.stopped" />
										</div>
									</c:if>

									<c:if test="${model.recieveMoney >0}">
										<br/>
										<div class="message_info">
											此活动为收费活动
										</div>
										<c:if
												test="${(not empty __sys_username) and (__group_is_member or __group_is_manager) }">
												&nbsp;<input type='button' class="input_btn" name='v_action'
											value='使用支付宝网上安全支付' onclick="location.href='/order.action?method=create&id=${model.id }&type=activity';"/>
											</c:if>
										
									</c:if>
								</div>
								<p class="orangelink">
									<com:topic value="${model.content }" />
									<br />
									<img src="images/signature.gif" />
									<br />
									${userQMD }
									<br />
								</p>
							</div>
							<div id="db">
								<div id="cbl"></div>
								<div id="bl"></div>
								<div id="cbr"></div>
							</div>
						</div>
						<c:if test="${not empty activityUserMaps}">
							<div class="title right clear">
								<bean:message key="page.group.activity.members" />
								&nbsp;&nbsp;
							</div>
							<ul class="square_photo_list_wide" class="clearfix">
								<c:forEach items="${activityUserMaps}" var="model"
									varStatus="status">
									<li>
										<a class="photo" href="u/${model.user.userId }"
											class="userName">
											<div class="userface">
												<img
													src="<com:img value="${model.user.userFace }" type="sized" width="80" />"
													width="80" height="80" />
											</div>
											<div class="username">
												${model.user.userName }
												<c:if test="${model.au.joinCnt>1}">×${model.au.joinCnt}人</c:if>
											</div> </a>
										<span class="color_orange"> <c:if
												test="${model.au.role eq 0}">
												<bean:message key="page.group.activity.member" />
											</c:if> <c:if test="${not (model.au.role eq 0)}">
												<bean:message key="page.group.activity.creator" />
											</c:if> </span>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
				</div>
				<div class="left_block top_blank_wide orangelink">
					<pg:pager items="${count}" url="activity.shtml" index="center"
						maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="aid" />
						<div class="pageindex_list">
							<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						<logic:iterate id="map" indexId="index" name="commentMapList">
							<div>
								<div class="reply_list orangelink clearfix">
									<div class="reply_user">
										<img
											src="<com:img value="${map.user.userFace }" type="sized" width="32" />"
											width="32" height="32" title="${map.user.userName}"
											alt="${map.user.userName}" />
									</div>
									<div id="reply_info" class="linkblue_b">
										<a href="u/${map.user.userId}">${map.user.userName}</a>
										<span class="color_gray"> <com:ipData
												value="${map.comment.ip }" /> </span>
									</div>
									<div class="reply_date">
										<div class="fleft">
											<span class="font_small color_gray"><com:date
													value="${map.comment.updateTime }" start="2" limit="16" />&nbsp;|</span>
										</div>
										<ul class="opt2 fleft">
											<li>
												<span class="opt_arrow"></span>
												<ul>
													<c:if
														test="${not empty __sys_username and __sys_username eq map.comment.author}">
														<li>
															<a
																href='activity.shtml?method=deleteComment&cid=${map.comment.id }'><bean:message
																	key="page.group.activity.comment.delete" />
															</a>
														</li>
													</c:if>
													<c:if test="${not empty __sys_username}">
														<li>
															<a
																href="javascript:re(${15*(currentPageNumber-1)+index+1 },'${map.user.userName }');"><bean:message
																	key="page.group.activity.comment.reply" />
															</a>
														</li>
													</c:if>
													<li>
														<a href="javascript:window.scroll(0,0)"><bean:message
																key="page.common.button.backToTop" />
														</a>
													</li>
												</ul>
											</li>
										</ul>
									</div>
									<div id="reply_content">
										<com:topic value="${map.comment.body}" />
									</div>
								</div>
							</div>
						</logic:iterate>
					</pg:pager>
				</div>
				<c:if test="${not empty __sys_username}">
					<div class="title_bar">
						<bean:message key="page.group.activity.hint.comment" />
					</div>
					<div class="left_block top_blank_wide radius_top_none">
						<form action="activity.shtml?method=saveComment" method="post"
							onsubmit="return checkForm();">
							<div class="replyitems">
								<ul>
									<li>
										<com:ubb />
									</li>
									<li>
										<input name="aid" value="${model.id }" type="hidden" />
										<input type="hidden" name="para_reply_to" id="para_reply_to"
											value="${author.userName }" />
										<textarea class="topicContent" name="body" id="topicContent"
											style="width: 554px; height: 150px"></textarea>
									</li>
									<li>
										<input type="submit" class="input_btn btn_mt"
											id="submitButton"
											value="<bean:message key="page.group.activity.button.comment" />" />
									</li>
								</ul>
							</div>
							<div class="replyemo">
								<%--<div id="emotionsDiv"></div> --%>
							</div>
						</form>
					</div>
				</c:if>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
		<script type="text/javascript"
			src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>
