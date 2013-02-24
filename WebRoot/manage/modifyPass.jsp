<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改用户密码</title>
		<%@include file="include.jsp"%>
		<script type="text/javascript">
			function errorMsg(msg){
			$("#errorMsg").html(msg);
 				$("#errorMsg").css({color:"#ffffff",background: "red"});
 				$("#errorMsg").fadeIn("fast");
 				$("#errorMsg").fadeOut(2000);
			}
			function checkForm(){
			var content0 = $("#userPassword0").val();
			var content1 = $("#userPassword1").val();
			var content2 = $("#userPassword2").val();
			if(content0==''){
				errorMsg("旧密码不能为空");
				return false;
			}
			if(content1==''){
				errorMsg("新密码不能为空");
				return false;
			}
			if(content2==''||content2!=content1){
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
			
			<c:if test="${not true eq forbidden}">
				<form action="${context }/manage/sysAdmin.shtml?method=password" name="form1"
					method="post" onsubmit="return checkForm();";>
					<div class="navigator">
						修改密码 &nbsp;&nbsp;
					</div>
					<div id="errorMsg">
						${systemError }
					</div>
					<table width="400" cellpadding="6" cellspacing="0">
						<tr>
							<td align=right>
								旧密码&nbsp;&nbsp;&nbsp;
								<INPUT name="userPassword0" id="userPassword0" class="formInput"
									type="password" size="20" style="width:200px;"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align=right>
								新密码&nbsp;&nbsp;&nbsp;
								<INPUT type="password" class="formInput" name="userPassword1"
									id="userPassword1" size="20" style="width:200px;"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align=right>
								确认新密码&nbsp;&nbsp;&nbsp;
								<INPUT type="password" class="formInput" size="20"
									name="userPassword2" id="userPassword2" style="width:200px;"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">
								<INPUT type="submit" name="submit" value="修改密码" class="button" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</form>
</c:if>
			</div>
		</div>



		<jsp:include page="bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
