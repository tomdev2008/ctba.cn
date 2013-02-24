<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="menu.group.mine" />&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="menu.group.mine" />
					</div>
				</div>
				<jsp:include flush="true" page="_groupTypes.jsp"></jsp:include>
				<c:if test="${not empty manageGroups}">
				<div class="left_block top_blank">
					<table width="100%" cellspacing="0" class="grouptable">
						<c:forEach items="${manageGroups}" var="model" varStatus="status">
						<tr>
							<td width="10%" align="center">
								<div class="group_pic_wrap"><a href="gt.action?method=list&gid=${model.group.id }"><img src="<com:img value="${model.group.face }" type="sized" width="80" />" width="48" title="${model.group.name}" /></a></div>
							</td>
							<td width="70%" <c:if test="${status.index%2==1 }">class="lightgray_bg"</c:if>>
								<h2 style="display:inline"><a href="group/${model.group.id }"><g:limit maxlength="25" value="${model.group.name}" /></a></h2>&nbsp;&nbsp;<span class="orangelink"><a href="g.action?method=form&gid=${model.group.id }"><bean:message key="page.common.button.edit" /></a></span>
								<br />
								<span class="color_gray"><g:limit value="${model.group.discription}" maxlength="40"  /></span>
							</td>
							<td width="20%" <c:if test="${status.index%2==1 }">class="lightgray_bg"</c:if>>
								<span class="color_gray"><bean:message key="page.group.info.topicCnt" />:</span><span class="font_mid color_orange">${model.topicCnt }</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="color_gray"><bean:message key="page.group.info.memberCnt" />:</span><span class="font_mid color_orange">${model.userCnt }</span><br />
								<span class="color_gray"><bean:message key="page.group.info.createTime" />:</span><span class="font_mid color_gray"><g:date limit="11" start="0" value="${model.group.createTime }" /></span>
							</td>
						</tr>
						</c:forEach>
					</table>
					<div class="pageindex_list_bottom">
						<img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.group.mine.hint" />
					</div>
				</div>
				</c:if>
				<div class="left_block top_blank">
					<c:if test="${not empty groups}">
					<pg:pager items="${count}" url="group.shtml" index="center"
						maxPageItems="15" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:index>
						<div class="pageindex_list">
							<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
						<table width="100%" cellspacing="0" class="grouptable">
							<c:forEach items="${groups}" var="model" varStatus="status">
							<tr>
								<td width="10%" align="center">
									<div class="group_pic_wrap"><a href="gt.action?method=list&gid=${model.group.id }"><img src="<com:img value="${model.group.face }" type="sized" width="80" />" width="48" title="${model.group.name}" /></a></div>
								</td>
								<td width="70%" <c:if test="${status.index%2==1 }">class="lightgray_bg"</c:if>>
									<h2 style="display:inline"><a href="group/${model.group.id }"><g:limit maxlength="25" value="${model.group.name}" /></a></h2>&nbsp;&nbsp;<span class="orangelink"><a href="userpage/<g:url value="${model.group.manager }"/>" title="组长">${model.group.manager }</a></span>
									<br />
									<span class="color_gray"><g:limit value="${model.group.discription}" maxlength="40"  /></span>
								</td>
								<td width="20%" <c:if test="${status.index%2==1 }">class="lightgray_bg"</c:if>>
	                            <span class="color_gray"><bean:message key="page.group.info.topicCnt" />:</span><span class="font_mid color_orange">${model.topicCnt }</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="color_gray"><bean:message key="page.group.info.memberCnt" />:</span><span class="font_mid color_orange">${model.userCnt }</span><br />
								<span class="color_gray"><bean:message key="page.group.info.createTime" />:</span><span class="font_mid color_gray"><g:date limit="11" start="0" value="${model.group.createTime }" /></span>
								</td>
							</tr>
							</c:forEach>
						</table>
						<pg:index>
						<div class="pageindex_list_bottom">
							<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
						<br class="clear" />
					</pg:pager>
					</c:if>
				</div>
			</div>
			<div id="area_right">
			    <jsp:include flush="true" page="_groupSearch.jsp"></jsp:include>
			    <jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>