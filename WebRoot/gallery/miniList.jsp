<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head><%@ include file="../common/include.jsp"%>
		<script type="text/javascript">
			function setImage(imgPath){
				<c:if test="${param.type eq 'ubb' }">
				setImageForUBB(imgPath);
				</c:if>
				<c:if test="${not (param.type eq 'ubb') }">
				setImageForHtml(imgPath);
				</c:if>
				window.parent.globalBoxyInstance.hide();
			}
			function setImageForHtml(imgPath){
				if(window.parent.document.getElementById("src")==null){
				  setImageForUBB(imgPath);
				  return;
				}
				window.parent.document.getElementById("src").value=imgPath;
			}
			function setImageForUBB(imgPath) {
			    var txt = "[img]"+imgPath+"[/img]";
			    window.parent.document.getElementById('topicContent').value+=txt;
			}
		</script>
	</head>
	<body>
		<div>
		<div class="mid_block">
			<c:if test="${ empty galleries}">
				<table width="100%" border="0" cellspacing="10" cellpadding="0">
					<tr>
						<td align=center>
							<div id=systemMsg class="message_error">
                                <bean:message key="page.gallery.common.error.noGalleryYet"/>&nbsp;<a class="graylink" href="img.shtml?method=galleryForm&type=${param.type }" target="_blank"><bean:message key="page.gallery.form.title.new"/></a>
							</div>
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${not empty galleries}">
				<div id="top_info_wrapper" class="mid_block top_notice clearfix">
					<div id="top_info_text" class="fleft">
						&nbsp;
						<img src="images/upix.png" align="absmiddle" />
						&nbsp;
						<community:select items="${galleries}" name="gid"
						selected="${galleryModel.id}" label="name" value="id" onchange="location.href='img.shtml?method=miniImageList&id='+this.value+'&type=';"/>
						&nbsp;&nbsp;
						<a href="multiImgUpload.shtml?method=miniForm"><bean:message key="page.gallery.action.upload"/></a>
					</div>
				</div>
				<div class="mid_block">
					<c:if test="${not empty models}">
						<pg:pager items="${count}" url="img.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
							<pg:param name="method" />
							<pg:param name="id" />
                            <pg:param name="type" />
							<pg:index>
								<div class="pageindex nborder top_blank">
									<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
								</div>
							</pg:index>
							<table width="100%" cellspacing="3" cellpadding="3">
								<tr>
									<c:forEach items="${models}" var="model" varStatus="status">
										<td align="center">
											<a class="photo" href="javascript:setImage('<com:img value="${model.path }" type="none"/>');">
												<img src="<com:img value="${model.path }" type="sized" width="32"/>" />
											</a>
										</td>
										<c:if test="${(status.index+1)%5==0}">
										</tr>
										<tr>
										</c:if>
									</c:forEach>
								</tr>
							</table>
						</pg:pager>
					</c:if>
				</div>
			</c:if>

		</div>
	</body>
</html>