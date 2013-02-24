<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@page import="org.net9.domain.model.core.SysEmail"%>
<%@taglib prefix="community" uri="/WEB-INF/community.tld"%>
<%@ page import="com.fredck.FCKeditor.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>发送邮件</title>
			<script type="text/javascript" src="${context}/fckeditor/fckeditor.js"></script>
			<script type="text/javascript">
		function checkForm(){
			var title=$("#title").val();
			var toAddr=$("#toAddr").val();
			if ((title == null) || (title == "")||title.length>60) {
			    alert("信息标题不能为空,不能超過60字。");
			    return false;
			}
			if ((toAddr == null) || (toAddr == "")) {
			    alert("收件人不能为空。");
			    return false;
			}
			var Content =FCKeditorAPI.GetInstance("content").GetXHTML();
			if ((Content == null) || (Content == ""||Content.length>20000)) {
			    alert("信息内容不能为空。,不能超過兩萬字");
			    var oEditor = FCKeditorAPI.GetInstance('content');
					oEditor.Focus();
			    return false;
			}
			$("#submitButton").val("提交中，请稍候");
			$("#submitButton").attr("disabled","disabled");
			return true;
		}
		</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					郵件表單
				</div>
				<form name="form1" action="emailManage.shtml?method=doSend" method="post" onsubmit="return checkForm();">
					<input type="hidden" name="eid" value="${model.id }" />
					<table cellspacing="10" cellpadding="0">
					<tr>
							<td   align="right"><h4>标题</h4></td>
							<td><input name="title" id="title" class="formInput" size="70" value="${model.title }" /></td>
						</tr>
						<tr>
							<td width="102" align="right"><h4>收件人</h4></td>
							<td>
							<textarea  class="formTextarea" name="toAddr" id="toAddr">${model.toAddr }</textarea>
							</td>
						</tr>
						
						<tr>
							<td align="right" valign="top"><h4>正文</h4></td>
							<td>
								<%
									String content = "";
									SysEmail entry = (SysEmail) pageContext.findAttribute("model");
									if (entry != null)
										content = entry.getContent();
									FCKeditor oFCKeditor;
									oFCKeditor = new FCKeditor(request, "content");
									oFCKeditor.setBasePath(request.getContextPath() + "/fckeditor/");
									oFCKeditor.setValue(content);
									oFCKeditor.setHeight("500px");
									oFCKeditor.setWidth("760px");
									out.println(oFCKeditor.create());
								%>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" id="submitButton" value="发送郵件" class="input_btn" />&nbsp;
								<input type="reset" value="重新撰写" class="input_btn" />&nbsp;
								<input type="button" onclick="location.href='emailManage.shtml?method=sent';" value="返回列表" class="input_btn" />
							</td>
						</tr>
					</table>
					</form>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
