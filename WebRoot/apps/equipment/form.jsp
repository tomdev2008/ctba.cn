<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>上传装备&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<script type="text/javascript">
			function checkForm(){
				var name = $("#name").val();
				if(name==''){
					alert("名称不能为空");
					return false;
				}
				var discription =  $("#topicContent").val();
				if ((discription == null) || (discription == "")) {
					alert("简介不能为空。");
					return false;
				}
				if(discription.length>2000){
					alert("您的简介太长鸟，请控制在2000字以内");
					return false;
				}
				if(!checkFile()){
					return false;
				}
				$("#submitButton").val("提交中，请稍候...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			function checkFile(){
					<c:if test="${empty model}">
							var imageFile =  $("#imageFile").val();
							if ((imageFile == null) || (imageFile == "")) {
								alert("请上传图片");
								return false;
							}
					</c:if>
							return true;
						}
						//通过ajax上传
						function submitForm(){
							var name = $("#name").val();
							var discription = $("#discription").val();
							var brand = $("#brand").val();
							var officailprice = $("#officailprice").val();
							var price = $("#price").val();
							var saleTime = $("#saleTime").val();
							var shortname = $("#shortname").val();
							var tech = $("#tech").val();
							var link = $("#link").val();
							var keyword = $("#keyword").val();
							var num = $("#num").val();
							var type = $("#type").val();
							$.ajax({ url: "equipment.shtml",
								type:"post",
								data: "method=saveWareByAjax&loadType=ajax&name="+name+"&name="+name+"&saleTime="+saleTime+"&shortname="+shortname+"&tech="+tech
									+"&link="+link+"&keyword="+keyword+"&num="+num+"&discription="+discription+"&brand="+brand+"&officailprice="+officailprice+"&price="+price+"&wid=${model.id }&type="+type,
								success:function(msg){
									if(window.parent){
										window.parent.location.href='equipment.shtml?method=home';
									}else{
										location.href='equipment.shtml?method=home';
									}
								}
							});
						}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="./head.jsp"/>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;&gt;&nbsp;上传装备
                    </div>
                </div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_userBar.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					 <jsp:include page="../../ad/_raywowAdBlock.jsp" flush="true"/>
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="form" />
					</jsp:include>
					<div class="mid_block">
						<iframe src="about:blank" name="uploadIframe" style="width:0px; height:0px; display:none;"></iframe>
						<form action="equipment.shtml?method=save" id="uploadForm" name="uploadForm" onsubmit="return checkForm();" method="post" enctype="multipart/form-data">
							<input name="wid" type="hidden" value="${model.id }" />
							<table width="100%" cellpadding="0" cellspacing="10" class="top_blank_wide">
								<c:if test="${not empty model.image}">
									<tr>
										<td align="right" width="15%">
											当前图片
										</td>
										<td>
											<img src="<com:img value="${model.image}" type="default" />" />
										</td>
									</tr>
								</c:if>
								<tr>
									<td align="right" width="15%">
										装备名称
									</td>
									<td width="80%">
										<input name="name" id="name" type="text" value="${model.name }" class="formInput" />
										<com:select items="${equipmentTypeList}" name="type" selected="${model.type}" label="name" value="index" />
									</td>
								</tr>
								<tr>
									<td align="right">
										装备简介
									</td>
									<td>
									<community:ubb />
										<textarea rows="5" cols="30" style="width:350px;" name="discription" id="topicContent" class="formTextarea">${model.discription }</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										上传图片
									</td>
									<td>
										<input type="file" name="imageFile" id="imageFile" class="inputtext" />
										
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
								<c:if test="${not empty __request_shop}">
								<tr>
									<td align="right">
										上架到店铺?
									</td>
									<td>
										<select id="shopId" name="shopId">
										<option value="${__request_shop.id}" <c:if test="${((empty model)) or ((not empty model.shopId) and (model.shopId>0))}">selected</c:if>>上架</option>
										<option value="-1" <c:if test="${(not empty model) and ((empty model.shopId) or (model.shopId<=0))}">selected</c:if>>不上架</option>
										</select>
									</td>
								</tr>
								<c:if test="${not empty categoryList}">
								<tr>
									<td align="right">
										（店铺）分类
									</td>
									<td>
										<com:select items="${categoryList}" name="categoryId" label="label" value="id" selected="${model.categoryId}"/>
									</td>
								</tr>
								</c:if></c:if>
								<tr>
									<td align="right">
										购买价
									</td>
									<td>
										<input type="text" name="price" id="price" size="20" value="${model.price }" class="formInput" />
										&nbsp;<span class="color_gray">推荐格式(数字RMB)</span>
										</td>
								</tr>
								<tr>
									<td align="right">
									</td>
									<td>
										<input type="button" id="toggleControllerButton" value="有更多情报？" onclick="$('#optionalFields').toggle();" class="input_btn" />
										</td>
								</tr>
							</table>
							<table width="100%" cellpadding="0" cellspacing="10" id="optionalFields" style="display: none">
								<%--						可选字段--%>
								<tr>
									<td align="right" width="15%">
										品牌
									</td>
									<td>
										<com:select items="${brandList}" name="brand" selected="${model.brand}" label="alias" value="name" />
										&nbsp;<span class="color_gray">其他?</span><input type="text" name="brandOther" size="20" value="${model.brand }" class="formInput" />
									</td>
								</tr>
								<tr>
									<td align="right">
										官方售价
									</td>
									<td>
										<input type="text" name="officailprice" id="officailprice" size="20" value="${model.officailprice }" class="formInput" />
									&nbsp;<span class="color_gray">推荐格式(数字RMB)</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										上市时间
									</td>
									<td>
										<input type="text" name="saleTime" id="saleTime" maxlength="15" size="20" value="${model.saleTime }" class="formInput" />
										&nbsp;<span class="color_gray">推荐格式(YYYY-MM)</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										简短名称
									</td>
									<td>
										<input type="text" name="shortname" id="shortname" maxlength="20" size="20" value="${model.shortname }" class="formInput" />
									</td>
								</tr>
								<tr>
									<td align="right">
										采用技术
									</td>
									<td>
										<input type="text" name="tech" id="tech" size="40" maxlength="15" value="${model.tech }" class="formInput" />
									</td>
								</tr>
								<tr>
									<td align="right">
										官方链接
									</td>
									<td>
										<input type="text" name="link" id="link" size="40" maxlength="150" value="${model.link }" class="formInput" />
									</td>
								</tr>
								<tr>
									<td align="right">
										关键词
									</td>
									<td>
										<input type="text" name="keyword" id="keyword" maxlength="40" size="20" value="${model.keyword }" class="formInput" />
										&nbsp;<span class="color_gray">以半角逗号(,)分隔</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										产品编号
									</td>
									<td>
										<input type="text" name="num" id="num" maxlength="25" size="20" value="${model.num }" class="formInput" />
									</td>
								</tr>
							</table>
							<table width="100%" cellpadding="0" cellspacing="10">
								<tr>
									<td align="right" width="15%"></td>
									<td>
										<input type="submit" name="submit" id="submitButton" value="提&nbsp;交" class="input_btn" />
										<c:if test="${not empty model}">
										&nbsp;&nbsp;
										<input type="button" name="button" id="linkButton" value="查&nbsp;看" class="input_btn" onclick="location.href='equipment/${model.id}';"/>
										&nbsp;&nbsp;
										<input type="button" name="button" id="linkButton" value="删&nbsp;除" class="input_btn" onclick="location.href='${basePath }equipment.shtml?method=delete&wid=${model.id }';" />
										 </c:if>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="_right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../../bottom.jsp" flush="true" />
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>