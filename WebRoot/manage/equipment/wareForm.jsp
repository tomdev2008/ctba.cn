<%--NOT IN USE--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="com"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>装备表单</title>
		<%@include file="../include.jsp"%>
		<script type="text/javascript">
			function checkForm(){
				var name = $("#name").val();
				if(name==''){
					alert("名称不能为空");
					return false;
				}
				var discription =  $("#discription").val();
				if ((discription == null) || (discription == "")) {
				    alert("简介不能为空。");
				    return false;
				}
				if(discription.length>300){
					alert("您的简介太长鸟，请控制在300字以内");
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
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
					<c:if test="${not empty model.image}">
						<table width="100%" cellpadding="0" cellspacing="10"><tr>
						<td align="right" width="20%">
							</td>
							<td>
								<img src="<com:img value="${model.image}" type="default"/>" class="inputtext" />
							</td>
						</tr>
						</table>
						</c:if>
						<!-- ファイル情報が返ってくるiframeを作成。表示上は見えないようにスタイル指定 -->
                    <iframe src="about:blank" name="uploadIframe" style="width:0px; height:0px; display:none;"></iframe>
				<form action="equipment.shtml?method=upload" id="uploadForm"  name="uploadForm" target="uploadIframe" onsubmit="return checkForm();"  method="post" enctype="multipart/form-data" >
					<input name="wid"  type="hidden" value="${model.id }"/>
					
					<%--						必填字段--%>
					<table width="100%" cellpadding="0" cellspacing="10">
						<tr>
							<td align="right" width="20%">
								装备名称
							</td>
							<td width="80%">
								<input name="name" id="name" type="text" value="${model.name }" class="inputtext" />
								<com:select items="${equipmentTypeList}" name="type" selected="${model.type}" label="name" value="index"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								装备简介
							</td>
							<td>
								<textarea rows="5" cols="30" name="discription" id="discription" class="areply_content" >${model.discription }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								上传图片
							</td>
							<td>
								<input type="file" name="imageFile" id="imageFile" class="inputtext"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" id="toggleControllerButton"  value="有更多情报？" onclick="$('#optionalFields').toggle();" class="input_btn" />
							</td>
						</tr>
						<tr>
							<td align="right">
								&nbsp;
							</td>
							<td>
								<a href="help" class="redlink">调整图片宽至455px，上传效果最佳</a>
							</td>
						</tr>
					</table>
					<div id="optionalFields" style="display:none">
						<table width="100%" cellpadding="0" cellspacing="10">
<%--						可选字段--%>
						<tr>
							<td align="right">
								品牌
							</td>
							<td>
								<input type="text" name="brand" id="brand" size="20" value="${model.brand }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								官方售价
							</td>
							<td>
								<input type="text" name="officailprice" id="officailprice" size="20" value="${model.officailprice }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								购买价
							</td>
							<td>
								<input type="text" name="price" id="price" size="20" value="${model.price }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								上市时间
							</td>
							<td>
								<input type="text" name="saleTime" id="saleTime" maxlength="15" size="20" value="${model.saleTime }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								简短名称
							</td>
							<td>
								<input type="text" name="shortname" id="shortname" maxlength="15" size="20" value="${model.shortname }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								采用技术
							</td>
							<td>
								<input type="text" name="tech" id="tech" size="20" maxlength="15" value="${model.tech }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								官方链接
							</td>
							<td>
								<input type="text" name="link" id="link" size="20" maxlength="35" value="${model.link }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								关键词
							</td>
							<td>
								<input type="text" name="keyword" id="keyword" maxlength="15" size="20" value="${model.keyword }" class="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="right">
								产品编号
							</td>
							<td>
								<input type="text" name="num" id="num" maxlength="25" size="20" value="${model.num }" class="inputtext" />
							</td>
						</tr>
					</table>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
