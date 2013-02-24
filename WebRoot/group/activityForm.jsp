<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.title.activity" />&nbsp;-&nbsp;${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
		<link rel="stylesheet" type="text/css" media="all" href="${context }/theme/calendar.css" />
		<script type="text/javascript">
			function checkForm(){
				J2bbCommon.removeformError("title");
				J2bbCommon.removeformError("place");
				J2bbCommon.removeformError("topicContent");
				J2bbCommon.removeformError("startTime");
				J2bbCommon.removeformError("endTime");
			    var title=$("#title").val();
			    var place=$("#place").val();
			    var topicContent=$("#topicContent").val();
			    var startTime=$("#startTime").val();
			    var endTime=$("#endTime").val();
			    var memberLimit=$("#memberLimit").val();
				if(title.length>50||title.length<1){
					J2bbCommon.formError("title","<bean:message key="page.group.activityForm.error.title" />");
			    	return false;
				}
				if(place.length>60||place.length<1){
					J2bbCommon.formError("place","<bean:message key="page.group.activityForm.error.place" />");
			    	return false;
				}
				if(startTime.length<1){
					J2bbCommon.formError("startTime","<bean:message key="page.group.activityForm.error.startTime" />","bottom");
			    	return false;
				}
				if(endTime.length<1){
					J2bbCommon.formError("endTime","<bean:message key="page.group.activityForm.error.endTime" />","bottom");
			    	return false;
				}
				if(topicContent==''||topicContent.length>3000){
					J2bbCommon.formError("topicContent","<bean:message key="page.group.activityForm.error.description" />");
			    	return false;
				}
				if((memberLimit!='') && isNaN(memberLimit)){
					J2bbCommon.formError("memberLimit","请正确填写人数限制","bottom");
			    	return false;
				}
				$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			
			function changeLimited(){
				if($("#limitedCheckbox").attr("checked") ){
					$("#memberLimitDiv").show();
				}else{
					$("#memberLimitDiv").hide();
				}
			}
			</script>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="group/${group.id }" title="${group.name }">${group.name }</a>&nbsp;&gt;&nbsp;<bean:message key="page.group.title.activity" />
					</div>
				</div>
				<div class="left_block top_blank">
					<div class="title">
						<h1>&nbsp;<img src="images/upix.png" align="absmiddle" />&nbsp;<bean:message key="page.group.activityForm.edit" /></h1>
					</div>
					<form action="activity.shtml?method=save" method="post" 
<%--					enctype="multipart/form-data" --%>
					onsubmit="return checkForm();">
						<input name="gid" value="${group.id }" type="hidden" />
						<c:if test="${empty param.copy_src}">
						<input name="id" value="${model.id }" type="hidden" />
						</c:if>
						<input type="hidden" name="privateState" value="1"/>
						<table width="600" cellspacing="10" cellpadding="0">
							<tr>
								<td width="72" align="right"><h2><bean:message key="page.group.activityForm.title" /></h2></td>
								<td>
									<input type="text" name="title" id="title" class="formInput" size="60" value="<c:if test="${not empty param.copy_src}">复制 </c:if>${model.title }" onblur="this.style.borderColor = '#999999'; this.style.backgroundColor = '#f9f9f9';" onfocus="this.style.borderColor = '#ff7700'; this.style.backgroundColor = '#ffffff';" />
								</td>
							</tr>
							<tr>
								<td  align="right"><h2><bean:message key="page.group.activityForm.place" /></h2></td>
								<td>
									<input type="text" name="place" id="place" class="formInput" size="60" value="${model.place }"  />
								</td>
							</tr>
							<tr>
								<td align="right"><h2><bean:message key="page.group.activityForm.description" /></h2></td>
								<td>
								    <community:ubb/>
									<textarea rows="3" name="topicContent" id="topicContent" class="topicContent" cols="50">${model.content}</textarea>
								</td>
							</tr>
							<tr>
								<td  align="right"><h2><bean:message key="page.group.activityForm.startTime" /></h2></td>
								<td>
									<input type="text" name="startTime" id="startTime" class="formInput" size="30" value="${startDate }" readonly="readonly" onclick="return showCalendar('startTime', 'y-mm-dd');"/>
									&nbsp;<community:select items="${timeList}" name="t_start" label="lable" selected="${t_start}" value="value" />点
								</td>
							</tr>
							<tr>
								<td  align="right"><h2><bean:message key="page.group.activityForm.endTime" /></h2></td>
								<td>
									<input type="text" name="endTime" id="endTime" readonly="readonly"  onclick="return showCalendar('endTime', 'y-mm-dd');" class="formInput" size="30" value="${endDate}" />
									&nbsp;<community:select items="${timeList}" name="t_end" label="lable" selected="${t_end}" value="value" />点
								</td>
							</tr>
							<tr>
								<td align="right"><h2>人数限制</h2></td>
								<td>
								<input type="checkbox" id="limitedCheckbox" name="limited" onClick="changeLimited();" <c:if test="${( not empty model.memberLimit) and (model.memberLimit>0)}">checked="true"</c:if>/> 限制人数&nbsp;
								<span id="memberLimitDiv" <c:if test="${( empty model.memberLimit) or (model.memberLimit<=0)}">style="display:none"</c:if>>
								<input type="text" name="memberLimit" id="memberLimit" class="formInput" size="30" value="${model.memberLimit }"  />
								<span>
								</td>
							</tr>
							<c:if test="${__user_is_editor}">
							<tr>
								<td align="right"><h2>活动收费</h2></td>
								<td>
								<input type="text" name="recieveMoney" id="recieveMoney" class="formInput" size="30" value="${model.recieveMoney }"  />
								<span class="color_gray">0表示不收费</span>
								</td>
							</tr></c:if>
							<tr>
								<td align="right"><h2><bean:message key="page.group.activityForm.type" /></h2></td>
								<td>
								<community:select items="${activityTypes}" name="type" label="activityType" selected="${model.type}" value="activityTypeCode" />
								</td>
							</tr>
<%--							<tr>--%>
<%--								<td align="right"><h2>活动图标</h2></td>--%>
<%--								<td>--%>
<%--									<input type="file" name="pic" id="pic" size="50" class="formInput" />--%>
<%--								</td>--%>
<%--							</tr>--%>
							<tr>
								<td></td>
								<td>
									<input type="submit" value="<bean:message key="page.group.activityForm.button.submit" />" class="input_btn" name="submitButton" id="submitButton" />&nbsp;
									<input type="button" value="<bean:message key="page.group.activityForm.button.list" />" class="input_btn" onclick="location.href='group/activities/${group.id }';"/>&nbsp;
									<input type="button" value="<bean:message key="page.group.activityForm.button.back" />" class="input_btn" onclick="location.href='group/${group.id }';"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
		<script type="text/javascript" src="${context}/javascript/j2bb-calendar.js"></script>
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>