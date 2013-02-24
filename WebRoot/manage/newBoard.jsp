<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@taglib prefix="community" uri="/WEB-INF/community.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="include.jsp"%>
		<title>新增版面</title>
		<script type="text/javascript">
			function errorMsg(msg){
				$("#errorMsg").html(msg);
 				$("#errorMsg").css({color:"#ffffff",background: "red"});
 				$("#errorMsg").fadeIn("fast");
 				$("#errorMsg").fadeOut(2000);
			}
			function checkForm(){
				var content = $("#boardName").val();
				if(content==''){
					errorMsg("名称不能为空");
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
				<div class="navigator">
					新增版面
				</div>
				<div id="errorMsg">
				</div>
				<form name="form1" action="editBoard.action?method=newBoard" method="post"
					enctype="multipart/form-data" onsubmit="return checkForm();"
					style="padding-left:20px;">
					版面名称
					<br />
					<input type="text" name="boardName" id="boardName" size="20"
						class="formInput">
					<br />
					所属版面
					<br />
					<community:select items="${topBoards}" name="boardParent"
						label="boardName" value="boardId" />
					<br />
					版面简介
					<br />
					<textarea rows="2" name="boardInfo" id="boardInfo" cols="30"
						class="formTextarea"></textarea>
					<br />
					版面附图
					<br />
					<input type="file" name="face" size="50" class="formInput" />
					<br />
					<input type="submit" value="新增版面" name="add" class="button" />
				</form>
			</div>
		</div>
		<jsp:include page="bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
