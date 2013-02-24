<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%><%@ taglib
	uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>订单管理&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;
						<a href="/order.action?method=list">订单管理</a>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block">
						<logic:notEmpty name="models">
							<pg:pager items="${count}" url="order.action" index="center"
								maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>"
								export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
								scope="request">
								<pg:param name="method" />
								<pg:param name="type" />
								<div class="pageindex">
									<jsp:include flush="true"
										page="../../common/jsptags/simple.jsp"></jsp:include>
								</div>
							</pg:pager>
							<div id="event_all" class="clearfix">
								<logic:iterate id="model" name="models" indexId="index">
									<div class="clearfix"
										style="border-bottom: 1px solid #eee; line-height: 17px; padding: 11px 0 8px 0; margin: 0 5px">
										<h2>
											<a href="order.action?method=view&id=${model.id }"
												title="${model.title}"> ${model.title} </a>
										</h2>
										<span class="font_mid color_gray"> <community:date
												value="${model.updateTime }" start="2" limit="11" /> </span>
									 	<span class="color_orange"
											style="position: absolute; margin: 0 0 0 407px"> <c:if
												test="${model.transState eq 0}">新建</c:if> <c:if
												test="${model.transState eq 1}">已付款</c:if> <c:if
												test="${model.transState eq 2}">已发货</c:if> <c:if
												test="${model.transState eq 3}">成交</c:if> 
												&nbsp;
												<c:if test="${model.transState eq 0}"><a class="link_btn" href="/money.action?method=pay&orderId=${model.id }">网上安全支付</a></c:if>
						&nbsp;
												&nbsp;
												&nbsp;
												
												
												</span>
									</div>
								</logic:iterate>
							</div>
						</logic:notEmpty>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>