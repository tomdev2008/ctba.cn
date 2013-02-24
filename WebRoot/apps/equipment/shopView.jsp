<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>
		<c:if test="${not empty categoryModel}">${categoryModel.label}&nbsp;-&nbsp;</c:if>
		${shopModel.name }&nbsp;-&nbsp;店铺&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<style type="text/css">
			.simple_list li {
				padding: 4px 0;
				border-bottom: 1px dashed #eee
			}
			.simple_list li.last {
				border: none
			}
		</style>
	</head>
	<body>
		<jsp:include flush="true" page="./head.jsp"/>
		<div id="wrapper">
			<div id="area_left">
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_userBar.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<jsp:include flush="true" page="_shopInfo.jsp" />
					<c:if test="${empty categoryModel}">
					<c:if test="${not empty commendList}">
					<div class="mid_block">
					<div class="title">
					掌柜推荐</div>
					<table width="100%" cellspacing="10" cellpadding="10">
							<tr>
								<c:forEach items="${commendList}" var="model" varStatus="status">
								<td align="center" class="graylink2">
									<a class="photo" href="equipment/${model.equipment.id }">
										<img src="<com:img value="${model.equipment.image }" type="default"/>" alt="${model.equipment.discription }" />
									</a>
									<h4>
										<strong>${model.label}</strong>
									</h4>
								</td>
								<c:if test="${(status.index+1)%3==0}"></tr><tr></c:if>
								</c:forEach>
							</tr>
						</table>
					</div>
					</c:if>
					</c:if>
					<div class="mid_block">
					<div class="title">
					<c:if test="${not empty categoryModel}">分类: ${categoryModel.label}</c:if>
					<c:if test="${empty categoryModel}">本店装备</c:if>
					</div>
					<c:if test="${not empty equipmentList}">
					<pg:pager items="${count}" url="shop.action" index="center"
						maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="id" />
						<pg:index>
						<div class="pageindex nborder top_blank">
							<jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
						<table width="100%" cellspacing="10" cellpadding="10">
							<tr>
								<c:forEach items="${equipmentList}" var="model" varStatus="status">
								<td align="center" class="graylink2">
									<a class="photo" href="equipment/${model.id }">
										<img src="<com:img value="${model.image }" type="default"/>" alt="${model.name }" />
									</a>
									<h4>
										<strong>${model.name}</strong>
									</h4>
								</td>
								<c:if test="${(status.index+1)%3==0}"></tr><tr></c:if>
								</c:forEach>
							</tr>
						</table>
						<pg:index>
						<div class="pageindex nborder">
							<jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
						</div>
						</pg:index>
					</pg:pager>
					</c:if>
					</div>
				</div>
			</div>
			<div id="area_right">
				<c:if test="${not empty categoryList}">
				<div class="state">
					<div class="titles">装备分类</div>
					<ul>
						<c:forEach items="${categoryList}" var="model" varStatus="status">
							<li class="tlist_wrap clearfix <c:if test="${status.index % 2 == 1}">lightgray_bg radius_all</c:if>">
								<div class="tlist_icon icons_newticket"></div>
								<div class="tlist_text"><a href="equipment/shop/${shopModel.id }/${model.category.id}">${model.category.label}</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				</c:if>
				<c:if test="${not empty groupTopicList}">
				<div class="state" id="groupTopicList">
					<div class="titles">小组话题</div>
					<div class="ew">
                        <a class="rss" target="_blank" href="group/${groupModel.id}" title="${groupModel.name}">到小组</a>
                    </div>
					<ul>
						<c:forEach items="${groupTopicList}" var="model" varStatus="status">
							<li class="tlist_wrap clearfix <c:if test="${status.index % 2 == 1}">lightgray_bg radius_all</c:if>">
								<div class="tlist_icon icons_newticket"></div>
								<div class="tlist_text"><a target="_blank" href="group/topic/${model.id}">${model.title}</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				</c:if>
				<div class="state">
					<ul class="infobar">
						<li class="icons_feed">
							<a href="rss.shtml" target="_blank"><bean:message key="rss.feed" />
							</a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
		<jsp:include page="../../bottom.jsp" flush="true" />
	</body>
</html>