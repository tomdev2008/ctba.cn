<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>提交反馈&nbsp;-&nbsp;<bean:message key="sys.name" />
		</title>
		<script type="text/javascript">
			function checkForm(){
				J2bbCommon.removeformError("topicContent");
				var topicContent = $("#topicContent").val();
				if(topicContent.length<1){
					J2bbCommon.formError("topicContent","请填写反馈内容");
					return false;
				}
				$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;提交反馈
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div  class="tabswrap clearfix">
						<form name="feedbackForm" action="feedback.action?method=save" method="post" onsubmit="return checkForm();">
							<input type="hidden" value="${__request_refurl }" name="refurl"/>
							<table width="90%" cellspacing="10" cellpadding="0" class="top_blank_wide">
								<tr>
									<td align="left">
									<div class="message_info">
									请将您举报的问题或者意见,建议填写到下面文本框中，谢谢您的参与
									</div>
									</td>
								</tr>
								<tr>
									<td align="left">
									<community:ubb/>
										<textarea name="description" class="topicContent" id="topicContent" style="width: 570px; height: 150px;" ><c:if test="${not empty __request_label }">你好，我发现下面的页面有不良信息，请及时处理

[url=${__request_refurl  }]${__request_label }[/url]</c:if></textarea>
									</td>
								</tr>
								<tr>
									<td align="left">
										<span class="color_orange">类型</span>
										<select name="type"><option value="0">举报不良信息</option><option value="1" <c:if test="${param.type eq 'ad' }">selected</c:if>>意见或建议</option></select>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="submit" class="input_btn" name="submitButton" id="submitButton" value="提交反馈" />
									</td>
									<td></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"></jsp:include>
				<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>
