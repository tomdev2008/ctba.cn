<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>编辑店铺</title>
		<script type="text/javascript">
			function errorMsg(msg){
				$("#errorMsg").html(msg);
 				$("#errorMsg").css({color:"#ffffff",background: "red"});
 				$("#errorMsg").fadeIn("fast");
 				$("#errorMsg").fadeOut(2000);
			}
			function checkForm(){
				var content = $("#name").val();
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
					编辑店铺 ${model.name }
				</div>
				<div id="errorMsg">
				</div>
				<form action="shopManage.action?method=save" id="uploadForm"
					name="uploadForm" onsubmit="return checkForm();" method="post"
					enctype="multipart/form-data">
					<input name="wid" type="hidden" value="${model.id }" />
					<table width="100%" cellpadding="0" cellspacing="10"
						class="top_blank_wide">
						<c:if test="${not empty model.logo}">
							<tr>
								<td align="right" width="15%">
									当前LOGO
								</td>
								<td>
									<img src="<com:img value="${model.logo}" type="default" />" />
								</td>
							</tr>
						</c:if>
						<tr>
							<td align="right" width="15%">
								店铺名称
							</td>
							<td width="80%">
								<input name="name" id="name" type="text" value="${model.name }"
									class="formInput" />
							</td>
						</tr><tr>
							<td align="right" width="15%">
								店主
							</td>
							<td width="80%">
								<input name="username" id="username" type="text" value="${model.username }"
									class="formInput" />
							</td>
						</tr>
						<tr>
							<td align="right">
								主营业务
							</td>
							<td>
								<textarea rows="2" cols="30" style="width: 350px; height: 50px;"
									name="mainBiz" id="mainBiz" class="formTextarea">${model.mainBiz }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								店铺简介
							</td>
							<td>
								<textarea rows="5" cols="30" style="width: 350px;"
									name="description" id="description" class="formTextarea">${model.description }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								上传LOGO
							</td>
							<td>
								<input type="file" name="logo" id="imageFile" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								&nbsp;
							</td>
							<td>
								<span class="color_gray">调整图片宽至 570px，上传效果最佳</span>
							</td>
						</tr>
						<tr>
							<td align="right" width="15%">
								官方小组(ID)
							</td>
							<td width="80%">
								<input name="refGroupId" id="refGroupId" type="text"
									value="${model.refGroupId }" class="formInput" />
								<c:if test="${not empty model.refGroupId}">
									<a href="group/${model.refGroupId}" target="_blank"><span
										class="color_gray">访问官方小组</span>
									</a>
								</c:if>
							</td>
						</tr>
					</table>
					<table width="100%" cellpadding="0" cellspacing="10">
						<tr>
							<td align="right" width="15%"></td>
							<td>
								<input type="submit" name="submit" id="submitButton"
									value="提&nbsp;交" class="input_btn" />
								&nbsp;&nbsp;
								<c:if test="${not empty model}">
									<input type="button" name="visitButton" id="visitButton"
										value="访&nbsp;问" class="input_btn"
										onclick="location.href='shop.action?method=view&id=${model.id }';" />
								</c:if>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
