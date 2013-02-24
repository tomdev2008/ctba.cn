<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.user.search.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp"></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.user.search.title" />
				</div>
				
					<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../_adBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
				
				<ul id="tabs_gray" class="graylink">
					<li class="normal"><a href="search.action"><bean:message key="page.topic.search.title" /></a></li>
					<li class="current"><bean:message key="page.user.search.title" /></li>
				</ul>

<div class="mid_block clearfix" id="search_user" align="left">
					<form action="user.shtml?method=search" method="post">
						&nbsp;&nbsp; &nbsp;&nbsp;<input class="font_mid color_gray formInput" style="width:300px;" value="${key}" id="username" name="username"/>
						&nbsp;<input type="submit" class="input_btn"  value="<bean:message key="page.user.search.button" />"/>
					</form>
					</div>
					
					<div class="mid_block clearfix">
					
					<pg:pager items="${count}" url="user.shtml" index="center"
						maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method"/>
						<pg:index>
							<div class="pageindex_list">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
							</div>
						</pg:index>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<div <%if(index%2==1){ %> class="altermt" <%} %> class="mytlist">
							<img src="<com:img value="${model.userFace }" type="mini"/>" alt="${model.userName }" height="16" width="16"/>	&nbsp;<span class="color_gray">...</span>
									<ctba:wrapuser user="${model}"/>
								</div>
							</logic:iterate>
						</logic:notEmpty>
					</pg:pager>
				</div></div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"></jsp:include>
	</body>
</html>