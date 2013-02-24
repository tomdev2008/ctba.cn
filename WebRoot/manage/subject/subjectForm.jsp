<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
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
			content = $("#description").val();
			if(content==''){
				errorMsg("描述不能为空");
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
					<form action="subManage.shtml?method=saveSubject" method="post"
						enctype="multipart/form-data"  onsubmit="return checkForm();" style="padding-left:20px;">
						<input type="hidden" name="sid" value="${model.id }" />
						题目
						<br />
						<input type="text" name="title" id="title" size="30"
							value="${model.title }" class="formInput" />
						<br />
						模板代码
						<br />
						<input type="text" name="template" size="30" class="formInput"
							value="${model.template}" />
						<br />
						描述
						<br />
						<textarea rows="5" name="description" id="description" cols="50"
							class="formTextarea" style="width:500px;height:200px">${model.description}</textarea>
						<br />
						类型
						<comm:select items="${typeList}" name="type" label="subjectType"
							selected="${model.type}" value="subjectTypeCode" />
						<br class="clear" />
						<input type="file" name="pic" size="50" class="formInput" />
						<br class="clear" />
						<c:if test="${not empty model}">
						<img src="../<comm:img value="${model.image}" type="default"/>"/>
						</c:if>
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
