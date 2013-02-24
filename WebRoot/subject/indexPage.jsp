<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="menu.subject.indexPage" /></title>
		<jsp:include page="../_metadataBlock.jsp"><jsp:param name="currPage" value="subject"/></jsp:include>
		<%@include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="menu.subject.navigate" />
					</div>
				</div>
				<div class="left_block">
					<c:if test="${not empty subsWithType}">
					<c:forEach items="${subsWithType}" var="subType" varStatus="index">
					<div class="board_sep">
						<div onclick="$('#subsWithType_${subType.typeMap.subjectTypeCode }').toggle('slow');" class="title">
							${subType.typeMap.subjectType }
						</div>
						<div id="subsWithType_${subType.typeMap.subjectTypeCode }" class="subject_wrap clearfix">
							<c:if test="${not empty subType.subs}">
							<c:forEach items="${subType.subs}" var="model" varStatus="index">
							<ul class="fleft">
								<li>
									<div class="subject_pic">
										<a href="subject/${model.subject.id }" class="blog_border_large"><img src="<community:img value="${model.subject.image}" type="none" />" alt="${model.subject.title}" title="${model.subject.title}" /></a>
									</div>
									<div class="subject_intro clearfix">
										<ul>
											<li>
												<span class="sub_name">专辑:</span>
												<span class="sub_info orangelink">
													<a href="subject/${model.subject.id }">${model.subject.title}</a>
												</span>
											</li>
											<li>
												<span class="sub_name">简介:</span>
												<span class="sub_info">${model.subject.description }</span>
											</li>
											<li>
												<span class="sub_name">日期:</span>
												<span class="sub_info color_gray">
													<g:date value="${model.subject.createTime }" start="0" limit="10" />
												</span>
											</li>
											<li>
												<span class="sub_name">文章:</span>
												<span class="sub_info">${model.topicCnt}</span>
											</li>
										</ul>
									</div>
								</li>
							</ul>
							</c:forEach>
							</c:if>
						</div>
					</div>
					</c:forEach>
					</c:if>
					<jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>