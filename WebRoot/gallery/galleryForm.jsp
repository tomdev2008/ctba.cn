<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>个人相册&nbsp;-&nbsp;${user.userName }&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#topicContent").val($("#oldContent").val());
		});
			function checkForm(){
				J2bbCommon.removeformError("description");
				J2bbCommon.removeformError("name");
			    var description=$("#topicContent").val();
			    var name=$("#name").val();
				
				if(name==''||name.length>20||name.length<1){
					J2bbCommon.formError("name","标题不能为空，不能超过20个字");
			    	return false;
				}
				if(description.length>300){
					J2bbCommon.formError("topicContent","简介不能多于300个字");
			    	return false;
				}
				$("#submitButton").val("提交中，请稍候");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="album/">个人相册</a>&nbsp;>&nbsp;编辑
					</div>
					<div class="fright">
						<div></div>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_logo.jsp" />
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="normal"><a href="img.shtml?method=galleryList">我的相册</a></li><c:if test="${empty model}"><li class="current">新建相册</li></c:if><c:if test="${not empty model}"><li class="current">修改相册</li></c:if>
						</ul>
					</div>
					<div class="mid_block">
						<form name="galleryForm" id="galleryForm" action="img.shtml?method=saveGallery" method="post" onsubmit="return checkForm();">
						<input type="hidden" value="${model.id }" name="id" />
						<input type="hidden" id="oldContent" value="${model.description }" />
							<table cellspacing="10" cellpadding="0">
								<tr>
									<td width="42" align="right"><h2>标题</h2></td>
									<td><input type="text" name="name" id="name" value="${model.name }" class="formInput" size="20" /></td>
								</tr>
								<tr>
									<td align="right"><h2>地点</h2></td>
									<td><input type="text" name="place" id="place" class="formInput" value="${model.place }" size="30" /></td>
								</tr>
								<tr>
									<td align="right"><h2>权限</h2></td>
									<td>
									<select name="viewOption" id="viewOption"  class="formInput" >
										<option value="0" <c:if test="${0 eq model.viewOption }">selected</c:if>>所有人</option>
										<option value="1" <c:if test="${1 eq model.viewOption }">selected</c:if>>我的好友</option>
										<option value="2" <c:if test="${2 eq model.viewOption }">selected</c:if>>我自己</option>
									</select>
								</tr>
								<tr>
									<td align="right"><h2>描述</h2></td>
									<td>
									<com:ubb/>
									<textarea cols="60" rows="3" class="topicContent" style="width:350px;height:100px;" name="description" id="topicContent">${model.description }</textarea>
									</td>
								</tr>
								<c:if test="${not empty model.face}">
								<tr>
									<td align="right"><h2>封面</h2></td>
									<td>
									<img src="<com:img value="${model.face }" type="default"/>" />
									</td>
								</tr>
								</c:if>
								<tr>
									<td align="right"></td>
									<td>
									<input type="submit" class="input_btn" name="submitButton" id="submitButton" value="提交" />
									&nbsp;&nbsp;<input type="button" class="input_btn" onclick="location.href='img.shtml?method=galleryList';" value="返回列表" />
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<script type="text/javascript" src="javascript/j2bb-editor-3.8.js"></script>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>