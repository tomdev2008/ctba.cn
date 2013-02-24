<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>店铺信息&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message
				key="nav.indexPage" />
		</title>
		<script type="text/javascript">
			function checkForm(){
				var name = $("#name").val();
				if(name==''){
					alert("名称不能为空");
					return false;
				}
				var mainBiz = $("#mainBiz").val();
				if(mainBiz==''){
					alert("主营业务不能为空");
					return false;
				}
				var description = $("#topicContent").val();
				if ((description == null) || (description == "")) {
					alert("简介不能为空。");
					return false;
				}
				$("#submitButton").val("提交中，请稍候...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../equipment/head.jsp" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;
						<a href="${context }/equipment/">装备秀</a>&nbsp;&gt;&nbsp;店铺信息
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../equipment/_userBar.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
				 <jsp:include flush="true" page="./_info.jsp" />
				 <jsp:include page="../../ad/_raywowAdBlock.jsp" flush="true"/>
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="shopForm" />
					</jsp:include>
					<div class="mid_block">
						<form action="shop.action?method=save" id="uploadForm"
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
										<input name="name" id="name" type="text"
											value="${model.name }" class="formInput" />
									</td>
								</tr>
								<tr>
									<td align="right">
										主营业务
									</td>
									<td>
										<textarea rows="2" cols="30"
											style="width: 350px; height: 50px;" name="mainBiz"
											id="mainBiz">${model.mainBiz }</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										店铺简介
									</td>
									<td>
										<community:ubb />
										<textarea rows="5" cols="30" style="width: 350px;"
											name="description" id="topicContent" class="formTextarea">${model.description }</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										上传LOGO
									</td>
									<td>
										<input type="file" name="logo" id="imageFile"
											class="inputtext" />
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
										官方小组
									</td>
									<td width="80%">
									<c:if test="${not empty groupList}">
									 <community:select items="${groupList}" name="refGroupId" value="id" label="name" selected="${model.refGroupId }" />
									 </c:if>
										<!--input name="refGroupId" id="refGroupId" type="text"
											value="${model.refGroupId }" class="formInput" /-->
											<c:if test="${(empty model.refGroupId) or (empty groupList)}">
											<a href="g.action?method=form" target="_blank"><span class="color_gray">还没有创建官方小组?</span></a>
											</c:if>
											<c:if test="${not empty model.refGroupId}">
											<a href="group/${model.refGroupId}" target="_blank"><span class="color_gray">访问官方小组</span></a>
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
												onclick="location.href='equipment/shop/${model.id }';" />
										</c:if>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="./_right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../../bottom.jsp" flush="true" />
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>