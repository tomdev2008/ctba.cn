<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.net9.domain.model.blog.BlogEntry"%>
<%@ page import="com.fredck.FCKeditor.*"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>编辑日志 - ${model.title }<c:if test="${(empty model) or (model.type eq 1001)}">[草稿]</c:if></title>
		<script type="text/javascript" src="${context}/fckeditor/fckeditor.js"></script>
		<script type="text/javascript">
			function checkForm(){
				var title = $("#title").val();
				if(title==''){
					alert("题目不能为空");
					return false;
				}
				var Content =FCKeditorAPI.GetInstance("body").GetXHTML();
				if ((Content == null) || (Content == "")) {
				    alert("内容不能为空。");
				    var oEditor = FCKeditorAPI.GetInstance('body');
						oEditor.Focus();
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
					${model.title } <c:if test="${(empty model) or (model.type eq 1001)}">[草稿]</c:if>
				<a href="blogManage.shtml?method=listEntries" class="button">返回列表</a>
				
				</div>
				<div id="errorMsg">
				</div>
				<div id="modelForm">
					<form action="blogManage.shtml?method=saveEntry" method="post"
						onsubmit="return checkForm();" style="padding-left:20px;">
						<input name="id" value="${model.id}" type="hidden"/>
						<table class="blog_setting" cellspacing="10" cellpadding="0">
                            <tr>
                                <td width="30%" align="right">
                                    <h3><bean:message key="page.blog.form.form.title"/></h3>
                                </td>
                                <td>
                                    <input name="title" id="title" class="formInput" size="50" value="${model.title }" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.content"/></h3>
                                </td>
                                <td>
						<%
							String content = "";
							BlogEntry entry = (BlogEntry) pageContext.findAttribute("model");
							if (entry != null)
								content = entry.getBody();
							FCKeditor oFCKeditor = new FCKeditor(request, "body");
							oFCKeditor.setBasePath(request.getContextPath() + "/fckeditor/");
							oFCKeditor.setValue(content);
							oFCKeditor.setHeight("500px");
							oFCKeditor.setWidth("560px");
							out.println(oFCKeditor.create());
						%>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.category"/></h3>
                                </td>
                                <td>
                                    <comm:select items="${cats}" name="categoryId" value="id" label="name" selected="${model.categoryId }" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.visible"/></h3>
                                </td>
                                <td>
                                    <select name="state" id="state">
                                        <option value="0" <c:if test="${model.state==0}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.all"/></option>
                                        <option value="1" <c:if test="${model.state==1}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.users"/></option>
                                        <option value="2" <c:if test="${model.state==2}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.friends"/></option>
                                        <option value="3" <c:if test="${model.state==3}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.private"/></option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                   <input type="submit" value=" 提 &nbsp; &nbsp;交 " class="button"
							name="edit" />
                                </td>
                            </tr>
                        </table>
                        
						
						<br />
					</form>
				</div>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
