<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.activity.title" />&nbsp;-&nbsp;${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;>&nbsp;<a href="group/${group.id}">${group.name }</a>&nbsp;>&nbsp;<bean:message key="page.group.activity.title" />
					</div>
				</div>
				<div class="left_block clearfix">
					<jsp:include flush="true" page="_groupInfo.jsp"/>
				</div>
				<div class="left_block top_blank">
					<jsp:include page="_tab.jsp" flush="true">
						<jsp:param name="currTab" value="activity"/>
					</jsp:include>
					<div class="top_blank">
						<c:if test="${not empty models}">
							<pg:pager items="${count}" url="activity.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
								<pg:param name="method" />
								<pg:param name="gid" />
								<pg:index>
									<div class="pageindex_list">
										<jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
									</div>
								</pg:index>
								<div id="tab_group">
									<c:forEach items="${models}" var="model" varStatus="status">
										<ul class="column_5c clearfix <c:if test="${status.index % 2 == 1}">lightgray_bg</c:if>">
											<li class="col3-5 center tag_none"></li>
											<li class="col63-5">
												&nbsp;<a href="group/activity/${model.activity.id }"><com:limit maxlength="30" value="${model.activity.title }" /></a>&nbsp;&nbsp;<span class="color_orange font_small" title="结束日期：${model.activity.endTime }:00"><g:date start="2" limit="13" value="${model.activity.startTime }" />:00</span><%--&nbsp; -> --%>
											</li>
											<li class="col17 list_sp2">
												<img src="<com:img value="${model.user.userFace}" type="mini"/>" width="16" height="16" class="userFace_border" align="absmiddle" />&nbsp;&nbsp;${model.activity.username}
											</li>
											<li class="col14 font_small color_gray">
												<g:date start="2" limit="16" value="${model.activity.createTime }" />
											</li>
											<li class="col2">
												<ul class="opt">
													<li>
														<a href="javascript:void(0);">
															<img src="images/icons/options.gif" />
														</a>
														<ul>
															<c:if test="${model.isManager eq true}">
																<li>
																	<a class="button" href="activity.shtml?method=form&aid=${model.activity.id }">
																		<bean:message key="page.group.activity.edit" />
																	</a>
																</li>
																<li>
																	<a class="button" href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath }activity.shtml?method=delete&aid=${model.activity.id }');return false;">
																		<bean:message key="page.group.activity.delete" />
																	</a>
																</li>
															</c:if>
															<li>
																<a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop" /></a>
															</li>
														</ul>
													</li>
												</ul>
											</li>
										</ul>
									</c:forEach>
								</div>
							</pg:pager>
						</c:if>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>
