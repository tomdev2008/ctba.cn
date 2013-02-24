<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="./common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<c:import url="../common/include.jsp"></c:import>
		<jsp:include page="../_metadataBlock.jsp"><jsp:param name="currPage" value=""/></jsp:include>
		<title><bean:message key="sys.name"/></title>
		<script type="text/javascript">
			$(function(){
				$("#tabs li.tlink a").click(function(){
					$("#tabs li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});
				$(".ew span span").click(function(){
					$(".ew span").removeClass("bold");
					$(this).parent("span").addClass("bold");
					$("div.scoretabwrap").hide();
					$("#" + $(this).attr("class")).show();
					return false;
				});
			});
		</script>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper" class="clearfix">
			<div id="area_left">
			<jsp:include page="../ad/_fanqieshuAdBlock.jsp" />
			<div class="left_block clearfix">
					<logic:notEmpty name="boards">
					<logic:iterate id="model" name="boards">
					<div class="board_sep">
						<div id="boardParent_${model.board.boardId }" onclick="$('#boardChild_${model.board.boardId }').toggle('slow');" class="boardParent">
							<img src="images/board_title.png" align="absmiddle" />&nbsp;${model.board.boardName}
						</div>
						<div id="boardChild_${model.board.boardId }">
							<logic:notEmpty name="model" property="subBoards">
							<logic:iterate id="subBoard" name="model" property="subBoards" indexId="index">
							<ul class="board_wrap clearfix">
								<li>
									<div class="fleft board_pic">
										<img src="<community:img type="none" value="${subBoard.board.boardFace }"/>" width="48" />
									</div>
									<div class="fleft board_intro">
										<h2><a href="board/${subBoard.board.boardId}" title="<community:htmlDecode value="${subBoard.board.boardInfo }"/>">${subBoard.board.boardName}</a></h2>
										<p class="color_gray">${subBoard.board.boardInfo }</p>
									</div>
									<div class="fright board_ana font_small">
										[&nbsp;${subBoard.board.boardTopicNum}&nbsp;/&nbsp;${subBoard.board.boardReNum+subBoard.board.boardTopicNum}&nbsp;]
									</div>
								</li>
							</ul>
							</logic:iterate>
							</logic:notEmpty>
						</div>
					</div>
					</logic:iterate>
					</logic:notEmpty>
				</div>
				
				<div class="left_block clearfix">
				
				<jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>
				
					<ul id="tabs" class="graylink">
						<li class="current tlink"><a name="tab_bbs_new"><bean:message key="common.bbs.latest" /></a></li>
						<li class="normal tlink"><a name="tab_bbs_latest"><bean:message key="common.bbs.lastupdated" /></a></li>
					</ul>
					<div id="tab_bbs_new" class="tabswrap">
						<logic:notEmpty name="topics">
						<logic:iterate id="topic" name="topics" scope="request" indexId="index">
						<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
							<li class="col1-5">&nbsp;</li>
							<li class="col17-5">
								<a href="u/${topic.user.userId}"><img src="${topic.userFace}" width="16" height="16" class="userFace_border" alt="${topic.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${topic.user.userId}">${topic.user.userName}</a>
							</li>
							<li class="col61" style="overflow:hidden">
								<a href="topic/${topic.topic.topicId }" title="${topic.topic.topicTitle}"><community:limit value="${topic.topic.topicTitle}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.topic.topicReNum}/${topic.topic.topicHits}&nbsp;</span>
							</li>
							<li class="col20 font_mid color_gray">
								<g:date value="${topic.topic.topicTime }" start="2" limit="16" />
							</li>
						</ul>
						</logic:iterate>
						</logic:notEmpty>
					</div>
					<div id="tab_bbs_latest" class="hide tabswrap">
						<logic:notEmpty name="lastUpdatedTopics">
						<logic:iterate id="topic" name="lastUpdatedTopics" scope="request" indexId="index">
						<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
							<li class="col1-5">&nbsp;</li>
							<li class="col17-5">
								<a href="u/${topic.user.userId}"><img src="${topic.userFace}" width="16" height="16" class="userFace_border" alt="${topic.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${topic.user.userId}">${topic.user.userName}</a>
							</li>
							<li class="col61" style="overflow:hidden">
								<a href="topic/${topic.topic.topicId }" title="${topic.topic.topicTitle}"><community:limit value="${topic.topic.topicTitle}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.topic.topicReNum}/${topic.topic.topicHits}</span>
							</li>
							<li class="col20 font_mid color_gray">
								<g:date value="${topic.topic.topicUpdateTime }" start="2" limit="16" />
							</li>
						</ul>
						</logic:iterate>
						</logic:notEmpty>
					</div>
				</div>
				
			</div>
			<div id="area_right">
				<div id="search">
					<jsp:include flush="true" page="../_searchBlock.jsp"></jsp:include>
				</div>
				<jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>
				<div class="state">
					<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>