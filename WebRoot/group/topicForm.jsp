<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>
			<logic:present name="topicModel"><bean:message key="page.group.topic.title.edit" />&nbsp;-&nbsp;${topicModel.title }&nbsp;-&nbsp;${topicModel.groupModel.name }</logic:present><logic:notPresent name="topicModel"><bean:message key="page.group.topic.title.new" /></logic:notPresent>&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<%@include file="../common/include.jsp"%>
		<script type="text/javascript">
			//check the form
			function checkTopicForm(){
				J2bbCommon.removeformError("topicContent");
				J2bbCommon.removeformError("topicTitle");
				var form = document.getElementById("topicForm");
				var content = $("#topicContent").val();
				var title = $("#topicTitle").val();
				if(content.length>10000||content.length<1){
					J2bbCommon.formError("topicContent","<bean:message key="page.group.topic.error.content" />"+content.length+"");
					return false;
				}
				if(title.length<1||title.length>50){
					J2bbCommon.formError("topicTitle","<bean:message key="page.group.topic.error.title" />");
					$("#topicTitle").focus();
					return false;
				}
				$("#submitNewButton").val("<bean:message key="page.common.button.submitting" />");
				$("#submitNewButton").attr("disabled","disabled");
				return true;
			}

			function addAttachement(index){
				if($("#attachment_"+(index-1)).val()!=''){
					$("#attachment_"+index).show();
					$("#attachmentSpan_"+index).show();
				}
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp"></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<logic:present name="topicModel"><bean:message key="page.group.topic.title.edit" />&nbsp;&gt;&nbsp;${topicModel.title }</logic:present><logic:notPresent name="topicModel"><bean:message key="page.group.topic.title.new" /></logic:notPresent>
				</div>
				<div class="left_block top_blank_wide radius">
					<div class="title">
						<h1>&nbsp;<img src="images/ico_conf.gif" align="absmiddle" />&nbsp;
							<logic:present name="topicModel">
								<bean:message key="page.group.topic.title.edit" />
							</logic:present>
							<logic:notPresent name="topicModel">
								<bean:message key="page.group.topic.title.new" />
							</logic:notPresent>
						</h1>
					</div>
					<form method="post" name="form1" id="topicForm" action="gt.action?method=save" enctype="multipart/form-data" onsubmit="return checkTopicForm();">
						<%-- <input type="hidden" name="gid" value="${groupModel.id }" />--%>
						<input type="hidden" name="tid" value="${topicModel.id }" />
						<input type="hidden" id="oldContent" value="${topicModel.content }" />
						<table width="700" cellspacing="10" cellpadding="0">
							<tr>
								<td width="41" align="right"><h2><bean:message key="page.group.topic.form.title" /></h2>
								<td>
									<input type="text" name="title" id="topicTitle" value="${topicModel.title }" class="formInput" size="60" />
								</td>
							</tr>
							<tr>
								<td width="41" align="right"><h2><bean:message key="page.group.topic.form.group" /></h2>
								<td>
									<community:select items="${groupList}" name="gid" selected="${groupModel.id}" label="name" value="id" />
								</td>
							</tr>
							<tr>
								<td align="right" valign="top"><h2><bean:message key="page.group.topic.form.content" /></h2></td>
								<td>
									<com:ubb />
									<textarea name="topicContent" class="topicContent" id="topicContent" style="width:554px;height:250px">${topicModel.content }</textarea>
								</td>
							</tr>
							<tr>
								<td align="right">
									<h2><bean:message key="page.group.topic.form.attachment" /></h2>
								</td>
								<td>
									<c:if test="${not empty attachments}">
										<ul class="attach_list">
											<c:forEach items="${attachments}" var="attachment" varStatus="status">
												<li class="font_mid color_gray">
													${attachment }&nbsp;<a href='gt.action?method=deleteAttachemnt&tid=${topicModel.id }&index=${status.index}'>x</a>
												</li>
											</c:forEach>
										</ul>
									</c:if>
									<ul class="attach_list">
										<c:if test="${attachmentsCnt<3}">
											<li>
												<input type="file" name="attachment_1" id="attachment_1" />
												<c:if test="${attachmentsCnt<2}"><span id="attachmentSpan_1"><a href='javascript:addAttachement(2);'><img src="images/icons/add.gif" align="absmiddle" alt="<bean:message key="page.group.topic.form.attachment.addMore" />" /></a></span>
											</li>
											<li>
												<input type="file" name="attachment_2" id="attachment_2" class="hide" /></c:if>
												<c:if test="${attachmentsCnt<1}"><span id="attachmentSpan_2" style="display:none"><a href='javascript:addAttachement(3);'><img src="images/icons/add.gif" align="absmiddle" alt="<bean:message key="page.group.topic.form.attachment.addMore" />" /></a></span>
											</li>
											<li>
												<input type="file" name="attachment_3" id="attachment_3" class="hide" /></c:if>
											</li>
										</c:if>
									</ul>
									<style>
										ul.attach_list li {
											margin: 3px 0
										}
										input.input_file {
											width: 250px;
											border: 1px solid #ddd
										}
									</style>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<input type="submit" name="submit" id="submitNewButton" value="<bean:message key="page.group.topic.button.post" />" class="input_btn" />
									<logic:present name="topicModel">
										&nbsp;<input type="button" onclick="location.href='group/topic/${topicModel.id }';" class="input_btn" value="<bean:message key="page.group.topic.button.back" />" />
									</logic:present>
									&nbsp;<input type="button" onclick="location.href='group/${group.id }';" class="input_btn" value="<bean:message key="page.group.topic.button.backToGroup" />" />
								</td>
							</tr>
						</table>
						<div class="line_blocks">
							<img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.group.topic.hint" />
						</div>
					</form>
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