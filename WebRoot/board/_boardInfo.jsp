<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<div class="left_block" style="padding: 6px 8px">
	<%--<img src="<com:img type="default" value="${board.boardFace}"/>" align="absmiddle" />--%>
	<h1>
		<img src="images/board.png" alt="${board.boardName }" title="${board.boardName }" align="absmiddle" />
		<a href="board/${board.boardId }">${board.boardName }</a>
		<a href="board/${board.boardId }">
			<img src="images/icons/shape_move_forwards.png" align="absmiddle" />
		</a>
	</h1>
	<div style="margin: 3px 0 5px 5px">
		<com:topic value="${board.boardInfo}" />
	</div>
	<hr size="1" />
	<ul class="lo clearfix">
		<li class="icons_page_white_edit">
			 <bean:message key="page.board.info.topic"/> : <span class="color_orange font_mid">${board.boardTopicNum}</span>&nbsp;&nbsp;|&nbsp;&nbsp;
		</li>
		<li class="icons_comments">
			 <bean:message key="page.board.info.reply"/> : <span class="color_orange font_mid">${board.boardReNum}</span>&nbsp;&nbsp;|&nbsp;&nbsp;
		</li>

		<li style="background: url(images/icons/user_gray.jpg) no-repeat">
			 <bean:message key="page.board.info.boardMaster"/> : <logic:empty name="board" property="boardMaster1"><bean:message key="page.board.info.noBoardMaster"/></logic:empty><logic:notEmpty name="board" property="boardMaster1">${board.boardMaster1}</logic:notEmpty>
		</li>
	</ul>
</div>