<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
        <title><bean:message key="page.board.new.title"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@include file="../common/include.jsp"%>
		<script type="text/javascript">
			//--------------
			//check the form
			//--------------
			function checkTopicForm(){
				var form = document.getElementById("topicForm");
				var content = $("#topicContent").val();
				var title = $("#topicTitle").val();
				if(content.length>10000||content.length<1){
					  J2bbCommon.errorWithElement("errorMsg","<bean:message key="page.board.topic.form.error.content"/>"+content.length+"");
                   return false;
				}
				if(title.length<1||title.length>50){
					J2bbCommon.errorWithElement("errorMsg","<bean:message key="page.board.topic.form.error.title"/>");
                    $("#topicTitle").focus();
					return false;
				}
				//var score = parseInt($("#topicScore"));
				//if(isNaN(score)||score<1){
				//	J2bbCommon.errorWithElement("errorMsg","请设置 CTB 回复收益");
                //    $("#topicScore").focus();
				//	return false;
				//}
				//if($("#imageNum").val().length<1){
				//	J2bbCommon.errorWithElement("errorMsg","验证码不能为空");
				//	$("#imageNum").focus();
				//	return false;
				//}
				$("#submitNewButton").val("<bean:message key="page.common.button.submitting"/>");
				$("#submitNewButton").attr("disabled","disabled");
				return true;
			}
		</script>
	</head>
	<body >
		<jsp:include flush="true" page="./head.jsp"></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.board.new.title"/>
					</div>
					<div class="fright">
						<div id="errorMsg"></div>
					</div>
				</div>
				<div class="left_block top_blank_wide radius">
					<div class="title">
						<h1>&nbsp;<img src="images/movies.png" align="absmiddle" />&nbsp;<bean:message key="page.board.new.title"/></h1>
					</div>
					<form method="post" name="form1" id="topicForm" enctype="multipart/form-data" action="newTopic.action?method=save" onsubmit="return checkTopicForm();">
						<table class="ml30" cellspacing="10" cellpadding="0">
							<tr>
								<td  align="right">
									<h2><bean:message key="page.board.edit.form.title"/></h2>
								</td>
								<td colspan="2">
									<input type="text" name="topicTitle" id="topicTitle" class="formInput" size="60" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<h2><bean:message key="page.board.edit.form.board"/></h2>
								</td>
								<td>
									<com:select items="${boardList}" name="topicBoardId" label="boardName" value="boardId" selected="${board.boardId }"/>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td align="right">
									<h2><bean:message key="page.board.edit.form.content"/></h2>
								</td>
								<td>
									<com:ubb />
									<textarea name="topicContent"  class="topicContent" id="topicContent" style="width:554px;height:250px"></textarea>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td align="right">
									<h2><bean:message key="page.board.edit.form.attach" /></h2>
								</td>
								<td colspan="2">
									<input type="file" name="topicAttach" class="formInput" size="50" />
									<logic:equal value="true" name="isEditor">
										<div>
											<a href="javascript:showFileFrame();"><img src="images/filetype/CLIP07.gif" border="0" width="20" /></a>
											<div<%--onmouseout="showFileFrame();"--%>>
												<iframe name="fileFrame" id="fileFrame" width="350" height="300" frameborder="1"></iframe>
											</div>
										</div>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td align="right">
									<h2>收益</h2>
								</td>
								<td colspan="2">
								<select id="topicScore" name="topicScore" class="formInput">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
								</select>&nbsp;
								<span class="color_orange">CTB</span>
									<!-- input type="text" id="topicScore" name="topicScore" class="formInput" size="20" /> -->
									&nbsp;<span class="color_gray">设置回复收益</span>
									<a href="go.jsp?key=faq-what-is-ctb-topic-trad" target="_blank" class="color_orange">(什么是回复收益?)</a>
								</td>
							</tr>
							<%--
							<logic:notEmpty property="userScore" name="user">
							<logic:lessEqual value="<%="" + Constant.USER_SCORE_NO_NEED_VALIDATE_CODE%>" property="userScore" name="user">
							<tr>
								<td align="right"><h2>验证</h2></td>
								<td colspan="2">
									<input type="text" name="imageNum" id="imageNum" class="formInput" style="width: 85px" />
									<img name="CheckNumber" src="verImg.action" width="60" height="20" alt="看不清？点击刷新!" style="cursor: pointer;" onclick="this.src='verImg.action'" />
								</td>
							</tr>
							</logic:lessEqual>
							</logic:notEmpty>
							--%>
							<tr>
								<td>
									&nbsp;
								</td>
								<td colspan="2">
									<input type="submit" name="submit" id="submitNewButton" autocomplete="off" value="<bean:message key="page.button.submit"/>" class="input_btn" />
									<input type="reset" class="input_btn" value="<bean:message key="page.button.reset"/>" />
								</td>
							</tr>
						</table>
					</form>
					<%-- <div class="line_blocks">
					<img src="images/icons/information.png" align="absmiddle" />&nbsp;Ctrl+Enter 可以直接提交文章哦:)
					</div>--%>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"></jsp:include>
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>