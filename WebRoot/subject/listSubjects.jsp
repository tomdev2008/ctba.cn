<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="menu.subject.list" />&nbsp;-&nbsp;<bean:message key="menu.subject.navigate" /></title>
		<%@include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="subject/"><bean:message key="menu.subject.navigate" /></a>&nbsp;&gt;&nbsp;<bean:message key="menu.subject.list" />
					</div>
				</div>
				<div class="left_block top_blank_wide clearfix">
					<div class="title">
						&nbsp;<img align="absmiddle" src="images/message.png"/>
						<h1 style="display:inline">专题列表</h1>
					</div>
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
					<table width="100%" cellspacing="1" cellpadding="3" style="margin:0 0 10px 0">
						<logic:iterate id="model" indexId="index" name="models">
						<tr>
							<td width="6%" align="center">
								<img align="absmiddle" src="images/icon_doc.png"/>
							</td>
							<td width="93%" <%if(index%2==1){ %> class="lightgray_bg" <%} %>>
								<h2 style="display:inline">
									&nbsp;<a href="subject/${model.id }" title="${model.title}">${model.title}</a>&nbsp;
								</h2>
								<span class="color_gray">
									<comm:limit value="${model.description }" maxlength="45" />
								</span>
							</td>
							<td width="1%">
							</td>
							<%--<td width="18%" <%if(index%2==1){ %> class="alternative" <%} %>>
								<span class="font_small color_gray">${model.createTime }</span>
							</td>--%>
						</tr>
						</logic:iterate>
					</table>
					</pg:pager>
					</logic:notEmpty>
				</div>
			</div>
			<div id="area_right">
				<div id="search">
					<form action="" method="post">
						<input class="search_input" name="searchKey"
							onmousemove="this.focus();" value="${searchKey }" />
						<input class="input_btn" type="submit" value="我搜" />
					</form>
				</div>
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>
