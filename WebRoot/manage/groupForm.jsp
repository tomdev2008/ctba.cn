<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户權限組</title>
		<%@include file="include.jsp"%>
		<script type="text/javascript">
			function errorMsg(msg){
			$("#errorMsg").html(msg);
 				$("#errorMsg").css({color:"#ffffff",background: "red"});
 				$("#errorMsg").fadeIn("fast");
 				$("#errorMsg").fadeOut(2000);
			}
			function checkForm(){
			var content0 = $("#name").val();
			var content1 = $("#optionStr").val();
			if(content0==''){
				errorMsg("權限名称不能为空");
				return false;
			}
			if(content1==''){
				errorMsg("权限字符不能为空");
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
				<form action="${context }/manage/sysAdmin.shtml?method=saveGroup" name="form1"
					method="post" onsubmit="return checkForm();";>
					<input name="gid" id=""gid"" 
									type="hidden" value="${model.id }"/>
									
					<div class="navigator">
						用户權限組 &nbsp;&nbsp;
					</div>
					<div id="errorMsg">
						${systemError }
					</div>
					<table width="400" cellpadding="6" cellspacing="0">
						<tr>
							<td align=right>
								權限名称&nbsp;&nbsp;&nbsp;
								<input name="name" id="name" class="formInput"
									type="text" size="20" style="width:200px;" value="${model.name }"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align=right>
								权限字符&nbsp;&nbsp;&nbsp;
								<input type="text" class="formInput" name="optionStr"
									id="optionStr" size="20" style="width:200px;" value="${model.optionStr }"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">
								<input type="submit" name="submit" value="提交" class="button" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</form>

			</div>
		</div>
		<jsp:include page="bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
