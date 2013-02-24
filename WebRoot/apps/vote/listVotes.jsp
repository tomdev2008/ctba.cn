<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>
			<logic:empty name="board">
				站内投票&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
			</logic:empty>
			<logic:notEmpty name="board">
				版内投票&nbsp;-&nbsp;${board.boardName }&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
			</logic:notEmpty>
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp" >
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<logic:empty name="board">站内投票</logic:empty>
					<logic:notEmpty name="board">
						<a href="b/${board.boardId }">${board.boardName }</a>&nbsp;>&nbsp;版内投票
					</logic:notEmpty>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="_logo.jsp"></jsp:include>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
				
				<jsp:include page="../../ad/_raywowAdBlock.jsp" />
				
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li <c:if test="${empty _is_mine }">class="current"</c:if> <c:if test="${not empty _is_mine }">class="normal"</c:if> id="t_content"><a href="vote.do?method=listVotes">投票列表</a></li><li <c:if test="${not empty _is_mine }">class="current"</c:if> <c:if test="${empty _is_mine }">class="normal"</c:if>><a href="vote.shtml?method=listVotes&type=mine">我的投票</a></li>
						</ul>
						<c:if test="${not empty __sys_username }">
							<div class="lt_post graylink">
								<a href="vote.do?method=voteForm&bid=${board.boardId }">发起新投票</a>
							</div>
						</c:if>
					</div>
					<div class="mid_block clearfix" style="margin:5px 0 10px 0;">
						<logic:notEmpty name="voteMapList">
							<pg:pager items="${count}" url="vote.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="method" />
								<pg:param name="bid" />
								<pg:param name="type" />
								<div class="pageindex">
									<jsp:include flush="true" page="../../common/jsptags/simple.jsp"></jsp:include>
								</div>
								<logic:iterate id="map" indexId="index" name="voteMapList">
									<div class="clearfix" style="border-bottom: 1px solid #eee;line-height:17px;padding:11px 0 8px 0;margin:0 5px">
										<a href="userPage.do?username=<community:url value="${map.vote.username}" />">
											<img src="<community:img value="${map.user.userFace }" type="sized" width="32" />" style="margin:0 6px 0 0" width="32" height="32" alt="${map.vote.username}" title="${map.vote.username}" align="left" />
										</a>
										<h2>
											<a href="vote/${map.vote.id }" title="${map.vote.question}">
												${map.vote.question}
											</a>
										</h2>
										<span class="font_mid color_gray">
											<community:date value="${map.vote.updateTime }" start="2" limit="11" />
										</span>
										<span class="orangelink" style="position:absolute;margin:0 0 0 437px">
											<a href="vote.shtml?method=doVote&vid=${map.vote.id }"><u>参加投票</u></a>
										</span>
									</div>
								</logic:iterate>
							</pg:pager>
						</logic:notEmpty>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp" />
	</body>
</html>