<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.user.notice.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script>
			function toggleSelect(){
				var checked= document.getElementById("toggleSelectCheckBox").checked;
				var inputs = document.getElementsByTagName("input");
				for(var i=0;i<inputs.length;i++){
					if(inputs[i].type=="checkbox"){
						inputs[i].checked=checked;
					}
				}
			}
			function deleteNotices(){
				$("#actionMethod").val("delete");
				$("#noticeActionForm").submit();
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle"/>&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.user.notice.title" />
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../_adBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
				
				<jsp:include page="../ad/_raywowAdBlock.jsp" />
				
					<form action="notice.action" id="noticeActionForm" method="post">
						<input type="hidden" id="actionMethod" name="method"/>
						<div class="mid_block clearfix">
							<div class="mail_info2">
								<bean:message key="page.user.notice.label.op" />: &nbsp;<a onclick="deleteNotices();" class="pointer">删除所选</a>
							</div>
							<pg:pager items="${count}" url="notice.action" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="method" />
								<div id="mail_list" class="clearfix">
									<ul class="column_5c lightyellow_bg" style="height:28px">
										<li class="col1-5 center">&nbsp;</li>
										<li class="col83-5">
											<input type="checkbox" id="toggleSelectCheckBox" class="nborder" onclick="toggleSelect();" style="background:none" />&nbsp;<b><bean:message key="page.user.notice.head.content" /></b>
										</li>
										<li class="col15">
											<b><bean:message key="page.user.notice.head.date" /></b>
										</li>
									</ul>
									<logic:notEmpty name="models">
										<logic:iterate id="model" name="models" indexId="index">
											<ul class="clearfix column_5c <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
												<li class="col1-5 center">&nbsp;</li>
												<li class="col83-5 <c:if test="${model.notice.expired eq 0 }">bold</c:if>" style="overflow:hidden">
													<%--<comm:limit maxlength="100" value="${model.notice.title}"/>
													--%>
													<input type="checkbox" id="_notice_${model.notice.id }" class="nborder" name="_notice_${model.notice.id }" style="background:none" />&nbsp;${model.notice.title}
												</li>
												<li class="col15 font_mid color_gray">
													<comm:date value="${model.notice.updateTime }" start="5" limit="19" />
												</li>
											</ul>
										</logic:iterate>
									</logic:notEmpty>
								</div>
								<pg:index>
									<div class="mail_pager clearfix">
										<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
									</div>
								</pg:index>
							</pg:pager>
						</div>
					</form>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>