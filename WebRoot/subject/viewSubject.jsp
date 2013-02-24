<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${subject.title}&nbsp;-&nbsp;<bean:message key="menu.subject.navigate" /></title>
		<%@include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="subject/"><bean:message key="menu.subject.navigate" /></a>&nbsp;&gt;&nbsp;${subject.title}
					</div>
				</div>
				<div class="left_block clearfix">
				<logic:notEmpty name="models">
				<pg:pager items="${count}" url="subject.shtml" index="center"
					maxPageItems="30" maxIndexPages="6"
					isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method" />
					<pg:param name="sid" />
					<pg:index>
					<div class="pageindex_list">
						<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
					</div>
					</pg:index>
					<logic:iterate id="model" indexId="index" name="models">
						<ul id="digglist">
							<li>
								<div class="diggmini" style="background:none">
									<img src="images/icon_doc.png" />
								</div>
								<div class="digglt orangelink">
									<h2 class="inline"><a href="subject/article/${model.id }" title="${model.title}"><comm:limit value="${model.title}" maxlength="24" /></a></h2>&nbsp;&nbsp;<img src="images/icons/page_white_star.png" align="absmiddle" /><%--&nbsp;<span class="color_gray">原创</span>&nbsp;<a href="userPage.do?method=userPage&username=<comm:url value="${model.author}"/>">${model.author}</a>--%>
									<%--<comm:date value="${model.createTime }" start="2" limit="11" />--%>
								</div>
								<div class="diggpt radius_all">
									共有&nbsp;<span class="font_mid color_gray">${model.hits}</span>&nbsp;位读者
								</div>
							</li>
						</ul>
					</logic:iterate>
				</pg:pager>
				</logic:notEmpty>
				</div>
			</div>
			<div id="area_right">
				<%--<div class="state">
					<span class="color_orange"><h2>
							${subject.title}
						</h2> </span>
					<br />
					<span class="color_gray">文章&nbsp;${count
						}&nbsp;...时间&nbsp;<comm:date limit="11" start="0"
							value="${subject.createTime }" /> </span>
					<br class="clear" />
					<span class="color_gray"><comm:html
							value="${subject.description }" /> </span>
				</div>--%>
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>