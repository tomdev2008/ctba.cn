<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.title.link"/>&nbsp;-&nbsp;${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" />
		</title>
		<script>
		function updateLink(id,label,url){
			$("#lid").val(id);
			$("#label").val(label);
			$("#url").val(url);
		}
		function checkForm(){
			J2bbCommon.removeformError("label");
			J2bbCommon.removeformError("url");
		    var label=$("#label").val();
		    var url=$("#url").val();
			if(label==''||label.length>30){
				J2bbCommon.formError("label","<bean:message key="page.group.link.error.label" />","bottom");
		    	return false;
			}
			if(url==''||url.length>100){
				J2bbCommon.formError("url","<bean:message key="page.group.link.error.url" />","bottom");
		    	return false;
			}
			$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
			$("#submitButton").attr("disabled","disabled");
			return true;
		}
		</script>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;<a href="group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${group.id}">${group.name }</a>&nbsp;&gt;&nbsp;<bean:message key="page.group.title.link"/>
					</div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp" />
				</div>
				<div id="list" class="left_block  top_blank">
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="link" />
					</jsp:include>
					<table width="100%" cellspacing="0" cellpadding="0" id="listable">
						<c:if test="${true eq __group_is_manager}">
							<form action="gLink.action?method=save" method="post"
								onsubmit="return checkForm();">
							<tr>
								<td width="62%">
									<input type="hidden" name="lid" id="lid" />
									<input type="hidden" name="gid" id="gid" value="${group.id }" />
									&nbsp;&nbsp;<bean:message key="page.group.link.label" />&nbsp;
									<input type="text" class="formInput" name="label" id="label" />
									&nbsp;&nbsp;<bean:message key="page.group.link.url" />&nbsp;
									<input type="text" class="formInput" style="width: 200px;"
										name="url" id="url" />
								</td>
								<td width="38%">
									<input type="submit" id="submitButton" class="input_btn"
										value="<bean:message key="page.common.button.submit" />" />
							</tr>
							</tr>
							</form>
						</c:if>

						<c:forEach items="${models}" var="model" varStatus="status">
							<tr
								<c:choose>
							<c:when test="${status.count%2==1}">
							class="alternative "
							</c:when>
							</c:choose>>
								<td width="62%">
									&nbsp;&nbsp;&nbsp;&nbsp;<a href="${model.url }"> <community:limit maxlength="30"
											value="${model.label}" /> </a>
								</td>
								<td width="38%">
									<span class="font_small color_orange">${model.username}
									</span>
									&nbsp;
									<a class="button"
										href="gLink.action?method=off&id=${model.id }">
										<img src="images/btn_down.png" border="0" title="<bean:message key="page.common.button.down" />" /> </a>
									<a class="button" href="gLink.action?method=up&id=${model.id }"
										 >
										<img src="images/btn_up.png" border="0" title="<bean:message key="page.common.button.up" />" /> </a>
										<a class="button"
										href="javascript:updateLink('${model.id }','${model.label}','${model.url }')">
										<img src="images/soul.gif" border="0" title="<bean:message key="page.common.button.edit" />" /> </a>
									<a class="button" href="javascript:void(0);"
										onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm" />','${basePath }gLink.action?method=delete&id=${model.id }');return false;">
										<img src="images/recycle_v2.gif" border="0" title="<bean:message key="page.common.button.delete" />" /> </a>
										
										
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>