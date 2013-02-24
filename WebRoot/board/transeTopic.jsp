<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="page.board.transform.title"/>&nbsp;-&nbsp;${topic.topicTitle }&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@include file="../common/include.jsp"%>
		<script type="text/javascript">
		//check the form
		function checkForm(){
            $("#submitButton").val("<bean:message key="page.common.button.submitting"/>");
				$("#submitButton").attr("disabled","disabled");
				return true;
		}
		</script>
	</head>
	<body >
		<jsp:include flush="true" page="./head.jsp"></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="topic/${topic.topicId }">${topic.topicTitle }</a>&nbsp;&gt;&nbsp;<bean:message key="page.board.transform.title"/>
				</div>
				<br class="clear" />
				<div class="left_block top_blank_wide radius_top_none">
					<div class="title">
                        <h1>&nbsp;<img src="images/ico_conf.gif" align="absmiddle" />&nbsp;<bean:message key="page.board.transform.title"/></h1>
					</div>
					<logic:equal value="true" name="isManager">
					<form name="form1" action="board.shtml?method=transeTopic" method="post" onsubmit="return checkForm();">
						<table width="500" cellspacing="10" cellpadding="0">
							<tr>
								<td width="70" align="right"><h2><bean:message key="page.board.transform.label"/></h2></td>
								<td>
									<input type="hidden" name="tid" value="${topic.topicId }" />
									<h2 class="color_orange bold">${topic.topicTitle }</h2>
								</td>
							</tr>
							<tr>
                                <td align="right"><h2><bean:message key="page.board.transform.label.to"/></h2></td>
								<td>
									<com:select items="${boards}" name="bid" label="boardName" value="boardId" selected="${topic.topicBoardId}"/>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<input type="submit" class="input_btn" name="submit" id="submitButton" value="<bean:message key="page.board.transform.button"/>" />
								</td>
							</tr>
						</table>
					</form>
					<div class="line_blocks"><img src="images/icons/information.png" align="absmiddle" /><bean:message key="page.board.transform.hint"/></div>
					</logic:equal>
					<logic:equal value="false" name="isManager">
					<div class="message_error">
						<bean:message key="error.noOption.noLogin"/>
					</div>
					</logic:equal>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"></jsp:include>
	</body>
</html>