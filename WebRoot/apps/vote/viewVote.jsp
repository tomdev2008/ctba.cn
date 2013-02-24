<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>
			${vote.question }&nbsp;-&nbsp;扯谈投票&nbsp;-&nbsp;<bean:message key="sys.name" />
		</title>
		<script type="text/javascript">
			function checkForm() {J2bbCommon.removeformError("topicContent");
				var content = $("#topicContent").val();
				if(content==''||content.length>500){
				J2bbCommon.formError("topicContent","内容不能为空，不能多于500字","red");
 				return false;
				}
				$("#submitButton").val("提交中，请稍候...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			//$(document).ready(function() {
				//loadNewEM(0);
   			//});
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
		<jsp:include flush="true" page="../../head.jsp">
		<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<logic:present name="board"><a href="b/${board.boardId }">${board.boardName }</a>&nbsp;&gt;&nbsp;投票</logic:present><logic:notPresent name="board"><a href="vote.do?method=listVotes">扯谈投票</a>&nbsp;&gt;&nbsp;${vote.question }</logic:notPresent>
					</div>
					<div class="fright">
						<jsp:include page="../../common/_share.jsp">
							<jsp:param name="share_type" value="5"/>
							<jsp:param name="share_id" value="${vote.id}"/>
							<jsp:param name="share_url" value="${basePath }vote/${vote.id}"/>
						</jsp:include>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="_logo.jsp"></jsp:include>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div id="vote_user" class="mid_block top_notice clearfix">
						<div class="fleft">
							<img src="<community:img value="${user.userFace}" type="sized" width="48" />" alt="${vote.username }" width="48" />
						</div>
						<div class="fleft vote_info">
							<h3><b><a href="u/${user.userId}">${vote.username }</a>&nbsp;发起的投票</b></h3>
							<ul class="clearfix">
								<li>开始时间：<span class="color_orange font_mid"><community:date limit="11" start="0" value="${vote.updateTime }" /></span></li>
								<li>截止时间：<span class="color_orange font_mid">${vote.overTime }</span></li>
								<li>
								<a href="feedback.action?method=form&type=vote&id=${vote.id }" class="link_btn ">举报</a>
								</li>
								<%--投票选项:${answersCnt }<br/>--%>
							</ul>
						</div>
					</div>
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="current tlink">当前投票</li><li class="normal tlink"><a href="vote.do?method=listVotes&bid=${board.boardId }">投票列表</a></li>
						<%-- 	<li class="normal tlink"><a href="vote.do?method=listVotes&bid=${board.boardId }">本版其他投票</a></li>
						--%>
						</ul>
						<c:if test="${not empty __sys_username }">
						<div class="lt_post graylink">
							<a href="vote.do?method=doVote&vid=${vote.id }">参与本投票</a>
						</div>
						</c:if>
					</div>
					<div id="vote_info" class="mid_block clearfix">
						<div id="vote_title" class="center">
							<h2 class="color_orange"><b><img src="images/icons/chart_bar.png" align="absmiddle" />&nbsp;${vote.question }</b></h2>
						</div>
						<div id="vote_intro">
							<community:html value="${vote.memo }" />
						</div>
						<div class="vote_wrapper clearfix">
							<ul class="vote_items">
								<logic:iterate id="result" name="resultList" indexId="index">
								<li>
									<div>
										${result.answer }
									</div>
									<div class="vote_bar_view">
										<img src="images/_vote/${(index+1)%6+1 }.gif" height="10" width="${result.percent*250+10 }" />&nbsp;
										<span class="font_mid"><b>${result.votedCnt }</b></span>&nbsp;票&nbsp;<span class="font_mid color_orange">(<community:limit maxlength="5" value="${result.percent*100 }" quote="false" />%)</span>
									</div>
								</li>
								</logic:iterate>
							</ul>
						</div>
					</div>
					
					<logic:notEmpty name="commentMapList">
					<div class="mid_block top_blank_wide clearfix">
					<pg:pager items="${count}" url="vote.do" index="center"
						maxPageItems="15" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="vid" />
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
											<c:if test="${not empty __sys_username}">
															<li><a href="javascript:re(${15*(currentPageNumber-1)+index+1},'${map.user.userName }');">回复本楼</a></li>
														</c:if>
												
													<c:if test="${not empty __sys_username and __sys_username eq map.comment.username}">
												<li>	<a href='javascript:void(0);' onclick="J2bbCommon.confirmURL('您确定要删除么？','${basePath }vote.shtml?method=deleteComment&vid=${map.comment.id }');return false;">删除本楼</a>
												</li>	</c:if>
												
												<li><a href="javascript:window.scroll(0,0)">返回投票</a></li>
											</ul>
										</li>
									</ul>
								</div>
								<div id="reply_content_mid">
									<community:topic value="${map.comment.body}"/>
								</div>
							</div>
						</div>
						</logic:iterate>
						<div class="pageindex nborder">
							<jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
					</pg:pager>
					</div>
					</logic:notEmpty>
					
					<c:if test="${not empty __sys_username}">
					<div class="mid_block top_blank_wide clearfix radius_all lightgray_bg" style="padding: 10px 10px 3px 10px;_padding:10px">
						<form action="vote.shtml?method=saveComment" method="post" onsubmit="return checkForm();">
							<community:ubb/>
							<input name="vid" value="${vote.id }" type="hidden"/><input type="hidden" name="para_reply_to" id="para_reply_to" value="${vote.username }"/>
							<textarea class="ftt" style="width:536px" name="body" id="topicContent"></textarea><br />
							<input type="submit" class="input_btn btn_mt" id="submitButton" value="发表评论" />
						</form>
					</div>
					</c:if>
					
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp" />
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>