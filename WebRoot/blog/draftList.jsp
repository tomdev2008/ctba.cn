<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="menu.blog.draft" />&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;>&nbsp;<a href="bg.action?method=view&username=<g:url value="${model.author }"/>">${blogModel.name}</a>&nbsp;>&nbsp;<bean:message key="menu.blog.draft" />
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../_adBlockMini.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block top_notice tl clearfix">
						<h2 class="color_orange">
							<b><bean:message key="menu.blog.draft" /></b>
						</h2>
                        <bean:message key="page.blog.draft.hint"/>
						 <br />
					</div>
					<div class="mid_block lightgray_bg">
						<jsp:include page="_tab.jsp" flush="true">
							<jsp:param name="currTab" value="draft"/>
					    </jsp:include>
					</div>
					<c:if test="${not empty entries}">
					<pg:pager items="${count}" url="be.action" index="center"
					maxPageItems="15"
					maxIndexPages="6" isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method"/>
					<div class="mid_block top_blank_wide clearfix">
						<table width="100%" cellpadding="3" cellspacing="1" class="blog_item_list">
							<c:forEach items="${entries}" var="model" varStatus="status">
							<tr>
								<td width="6%" align="center">
									<img src="images/icon_doc.png" align="absmiddle" />
								</td>
								<td width="70%" <c:choose><c:when test="${status.count%2==1}"> class="lightgray_bg" </c:when></c:choose>>
									&nbsp;
									<h2>
										<a href="be.action?method=form&eid=${model.id }"  >
											<community:limit maxlength="20" value="${model.title }"/>
										</a>
									</h2>
									&nbsp;&nbsp;
									<span class="font_mid color_gray">
									<comm:formatTime time="${model.date }"/>
									</span>&nbsp;
								</td>
								<td width="24%" align="center">
                                    <a href="be.action?method=form&eid=${model.id }" class="link_btn"><bean:message key="page.blog.draft.continue"/></a>&nbsp;
                                    <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }be.action?method=delete&eid=${model.id }&type=draft');return false;" class="link_btn"><bean:message key="page.common.button.delete"/></a>
								</td>
							</tr>
							</c:forEach>
						</table>
						<pg:index>
						<div class="pageindex_bottom">
							<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
					</div>
					</pg:pager>
					</c:if>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>