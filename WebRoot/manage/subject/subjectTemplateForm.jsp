<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>${model.title }</title>
		<script type="text/javascript">
			function errorMsg(msg){
				$("#errorMsg").html(msg);
 				$("#errorMsg").css({color:"#ffffff",background: "red"});
 				$("#errorMsg").fadeIn("fast");
 				$("#errorMsg").fadeOut(2000);
			}
			function checkForm(){
				var content = $("#title").val();
				if(content==''){
					errorMsg("题目不能为空");
					return false;
				}
				content = $("#code").val();
				if(content==''){
					errorMsg("代码不能为空");
					return false;
				}
				content = $("#content").val();
				var url = $("#url").val();
				if(content==''&&url==''){
					errorMsg("内容和链接不能都为空");
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
					<form action="subManage.shtml?method=saveSubjectTemplate"
						method="post" onsubmit="return checkForm();"
						style="padding-left:20px;">
						<input type="hidden" name="id" value="${model.id }"
							style="width:500px;" />
						题目
						<br />
						<input type="text" name="title" id="title" size="30"
							value="${model.title }" class="formInput" />
						<br />
						代码
						<br />
						<input type="text" name="code" id="code" size="30"
							value="${model.code }" class="formInput" />
						<br />
						链接
						<br />
						<input type="text" name="url" id="url" size="30"
							value="${model.url }" class="formInput" />
						<br />
						内容
						<br />
						<textarea rows="2" name="content" id="content" cols="30"
							class="formTextarea" style="width:500px;height:500px">${model.content }</textarea>
						<br />
						<br class="clear" />
						<br class="clear" />
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
