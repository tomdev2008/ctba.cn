<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.net9.domain.model.subject.SubjectTopic"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
<%@ page import="com.fredck.FCKeditor.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>${model.title }</title>
		<script type="text/javascript" src="${context}/fckeditor/fckeditor.js"></script>
		<script type="text/javascript">
			function checkForm(){
				var title = $("#title").val();
				var num = $("#topicNum").val();
				if(num==''&&title==''){
					alert("题目不能为空");
					return false;
				}
				var content =FCKeditorAPI.GetInstance("content").GetXHTML();
				if(num =='' && content==''){
				    alert("内容不能为空。");
				    return false;
				}
				return true;
			}
			</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					${model.title }
				</div>
				<div id="errorMsg">
				</div>
				<div id="modelForm">
					<form action="subManage.shtml?method=saveSubjectTopic"
						method="post" onsubmit="return checkForm();"
						style="padding-left: 20px;">
						<input type="hidden" name="sid" value="${model.id }" />
						所属专题
						&nbsp;
						<comm:select items="${subjectList}" name="subjectId" label="title"
							value="id" selected="${model.subjectId}" />

						<br class="clear" />
						类型
						&nbsp;
						<select name="type" id="type">
							<option value="ctba"
								<c:if test="${'ctba' eq   model.type}">selected</c:if>>
								原创
							</option><option value="e"
								<c:if test="${'e' eq model.type}">selected</c:if>>
								博客文章
							</option>
							<option value="t"
								<c:if test="${'t' eq model.type}">selected</c:if>>
								论坛文章
							</option>
						</select>
&nbsp;						文章号
						&nbsp;
						<input type="text" name="topicNum" id="topicNum" size="6"
							value="${topicNum }" class="formInput" />
						<br class="clear" />
						题目
						&nbsp;
						<input type="text" name="title" id="title" size="30"
							value="${model.title }" class="formInput" />
						<br class="clear" />
						内容
						<br />
						<%
							String content = "";
							SubjectTopic topic = (SubjectTopic) pageContext
									.findAttribute("model");
							if (topic != null) {
								content = topic.getContent();
							}
							FCKeditor oFCKeditor;
							oFCKeditor = new FCKeditor(request, "content");
							oFCKeditor.setBasePath(request.getContextPath() + "/fckeditor/");
							oFCKeditor.setValue(content);
							oFCKeditor.setHeight("500px");
							oFCKeditor.setWidth("560px");
							out.println(oFCKeditor.create());
						%>
						<%-- <br class="clear" />描述
						<br />
						<textarea rows="5" name="description" id="description" cols="50"
							class="formTextarea" style="width:500px;height:200px">${model.description}</textarea>
						<br />
						--%>
						<br  />
						<input type="submit" value=" 提 &nbsp; &nbsp;交 " class="button"
							name="edit" />
						<br />
					</form>
				</div>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
