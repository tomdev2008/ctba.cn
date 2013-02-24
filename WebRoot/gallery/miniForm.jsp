<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="../common/include.jsp"%>
		<script type="text/javascript" src="${context }/javascript/j2bb-3.8.js"></script>
		<script type="text/javascript">
			function checkAllEmpty(){
				for(var i=1;i<=6 ;i++){
					var value=$("#uploadFile"+i).val();
					if(value!=''){
						return false;
					}
				}
				return true;
			}
			function checkForm(){
				if(checkAllEmpty()){
					return false;
				}
				for(var i=1;i<=6 ;i++){
					if(!checkSingle("uploadFile"+i)){
						return false;
					}
				}
				$("#submitButton").val("提交中，请稍候");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}

			function checkSingle(fieldId){
				J2bbCommon.removeformError(fieldId);
				var value=$("#"+fieldId).val();
				var imgPatten = /\.jpg$|\.jpeg$|\.gif$|\.bmp|\.png$/;
				if(value!=''&&!imgPatten.test(value.toLowerCase())){
					J2bbCommon.formError(fieldId,"请上传正确的图片文件");
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<div>
			<div class="mid_block">
				<c:if test="${ empty galleryList}">
					<table width="100%" border="0" cellspacing="10" cellpadding="0">
						<tr>
							<td align=center>
								<div id=systemMsg class="message_error">
									<bean:message key="page.gallery.common.error.noGalleryYet"/>
                                    <a class="graylink" target="_blank" href="img.shtml?method=galleryForm"><bean:message key="page.gallery.form.title.new"/></a>
								</div>
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${not empty galleryList}">
					<form action="multiImgUpload.shtml?method=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm();">
						<input name="type" type="hidden" value="mini" />
						<input name="naviType" type="hidden" value="${param.type }" />
						<div id="top_info_wrapper" class="mid_block top_notice clearfix">
							<div id="top_info_text" class="fleft">
								&nbsp;
								<img src="images/upix.png" align="absmiddle" />
								&nbsp;
								<community:select items="${galleryList}" name="gid" selected="${gallery.id}" label="name" value="id" />
								&nbsp;&nbsp;
								<a href="img.shtml?method=miniImageList">返回</a>
							</div>
						</div>

						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="file" name="uploadFile1" id="uploadFile1"
									 class="formInput" size="30" />
									<br />
									<input type="file" name="uploadFile2" id="uploadFile2"
									 class="formInput" size="30" />
									<br />
									<input type="file" name="uploadFile3" id="uploadFile3"
									 class="formInput" size="30" />
									<br />
									<input type="file" name="uploadFile4" id="uploadFile4"
									 class="formInput" size="30" />
									<br />
									<input type="file" name="uploadFile5" id="uploadFile5"
									 class="formInput" size="30" />
									<br />
									<input type="file" name="uploadFile6" id="uploadFile6"
									 class="formInput" size="30" />
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<input type="submit" value="提交图片" class="input_btn" name="submitButton" id="submitButton" />
									&nbsp;
								</td>
							</tr>
						</table>
					</form>
				</c:if>
			</div>
		</div>
	</body>
</html>