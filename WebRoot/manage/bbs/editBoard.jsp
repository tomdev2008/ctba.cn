<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>编辑版面</title>
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
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					编辑版面
					${model.boardName }
				</div>
				<div id="errorMsg">
				</div>
				<form name="form1" action="editBoard.action?method=save" method="post"
					enctype="multipart/form-data" onsubmit="return checkForm();" style="padding-left:20px;">

					<input type="hidden" name="boardId" value="${model.boardId}" />
					版面名称
					<br />
					<input type="text" name="boardName" id="boardName" size="20"
						value="${model.boardName }" class="formInput" />
					<br />
					所属版面
					<br />
					<community:select items="${topBoards}" name="boardParent"
						label="boardName" selected="${model.boardParent}" value="boardId" />
					<br />
					版面简介
					<br />
					<textarea rows="2" name="boardInfo" id="boardInfo" cols="30"
						class="formTextarea">${model.boardInfo }</textarea>
					<br />
					<img src="../<community:img value="${model.boardFace}" type="default"/>"/>
					
					<br /><br />
					<input type="file" name="face" size="50" class="formInput" />
					<br />
					<input type="submit" value="修改" class="button" name="edit" />
				</form>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
