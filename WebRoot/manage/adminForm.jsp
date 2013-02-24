<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系統用户</title>
		<%@include file="include.jsp"%>
		<script type="text/javascript">
			function errorMsg(msg){
			$("#errorMsg").html(msg);
 				$("#errorMsg").css({color:"#ffffff",background: "red"});
 				$("#errorMsg").fadeIn("fast");
 				$("#errorMsg").fadeOut(2000);
			}
			function checkForm(){
			var content0 = $("#adminUsername").val();
			var content1 = $("#adminPassword").val();
			var content2 = $("#passwordConfirm").val();
			if(content0==''){
				errorMsg("用户不能为空");
				return false;
			}
			if(content1==''){
				errorMsg("密码不能为空");
				return false;
			}
			if(content2!=content1){
				errorMsg("请确认密码");
				return false;
			}
			return true;
			}
			</script>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<form action="${context }/manage/sysAdmin.shtml?method=saveAdmin"
					name="form1" method="post" onsubmit="return checkForm();">
					<div class="navigator">
						系統用户 &nbsp;&nbsp;
					</div>
					<div id="errorMsg">
						${systemError }
					</div>
					<table width="400" cellpadding="6" cellspacing="0">
						<tr>
							<td align=right>
								用户名&nbsp;&nbsp;&nbsp;
								<input name="adminUsername" id="adminUsername" class="formInput"
									size="20" style="width:200px;" value="${model.username }" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align=right>
								用户密码&nbsp;&nbsp;&nbsp;
								<input type="password" class="formInput" name="adminPassword"
									id="adminPassword" size="20" style="width:200px;"
									value="${model.password }" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align=right>
								确认密码&nbsp;&nbsp;&nbsp;
								<input type="password" class="formInput" size="20"
									name="passwordConfirm" id="passwordConfirm"
									style="width:200px;" value="${model.password }" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">
								<input type="submit" name="submit" value="提交修改" class="button" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</form>
				<c:if test="${not empty model}">

					<div class="navigator">
						用户权限组 &nbsp;&nbsp;
					</div>
					<c:if test="${not empty adminGroups}">
						<table width="400" cellpadding="2" cellspacing="0">
							<tr class="navigator">
								<td>
									权限组
								</td>
								<td>
									权限字符串
								</td>
								<td>
									操作
								</td>
							</tr>
							<c:forEach items="${adminGroups}" var="g" varStatus="status">
								<tr
									<c:if test="${status.index%2==1}"> class="alternative"</c:if>>
									<td>
										<comm:select items="${groups}" name="group" label="name"
											selected="${g.optionStr }" value="optionStr" />
									</td>
									<td>
										${g.optionStr }
									</td>
									<td>
										<a
											href="${context }/manage/sysAdmin.shtml?method=deleteAdminGroup&id=${g.id }"
											class="button" onclick="return confirm('您确定要删除么？');">刪除</a>
									</td>
								</tr>
							</c:forEach>

						</table>
					</c:if>
					<br class="clear" />
					<form
						action="${context }/manage/sysAdmin.shtml?method=saveAdminGroup"
						name="form2" method="post">
						<input name="adminUsername" type="hidden"
							value="${model.username }" />
						<c:if test="${not empty groups}">
							<comm:select items="${groups}" name="gid" label="name" value="id" />
						</c:if>
						<input type="submit" name="submit" value="增加權限組" class="button" />
					</form>

				</c:if>
			</div>
		</div>
		<jsp:include page="bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
