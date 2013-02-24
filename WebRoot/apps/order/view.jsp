<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.alipay.util.*"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>查看订单详情&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
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
						查看订单详情
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">


					<div class="mid_block lightgray_bg">

						<div class="color_orange">
							&nbsp;&nbsp;以下是您的交易订单详情
						</div>
					</div>

					<div class="mid_block">
						<table class="ml30" cellspacing="10" cellpadding="0">
							<tr>
								<td align="right" width="100">
									<b>名称</b>
								</td>
								<td>
									[支付]${model.title }
								</td>
							</tr>
							<tr>
								<td align="right">
									<b>支付价格</b>
								</td>
								<td>
									${model.price }
								</td>
							</tr>
							<tr>
								<td align="right">
									<b>收费者</b>
								</td>
								<td>
									${model.owner } &nbsp;&nbsp;
									<span class="color_gray">由扯谈社收费平台代收</span>
								</td>
							</tr>

							<tr>
								<td align="right">
									<b>联系电话</b>
								</td>
								<td>
									${model.phone }
								</td>
							</tr>
							<tr>
								<td align="right">
									<b>提货方式</b>
								</td>
								<td>
									<c:if test="${model.fetchType eq 1}">不需提货</c:if>
									<c:if test="${model.fetchType eq 2}">上海市内自提</c:if>
									<c:if test="${model.fetchType eq 3}">快递送货&nbsp;&nbsp;<span
											class="color_gray">费用统一为25元，由您来承担</span>
									</c:if>
								</td>
							</tr>
							</tr>
						</table>
						<br />
					</div>
					<div class="mid_block lightgray_bg">
						<p class="color_orange">
							&nbsp;&nbsp;以下您填写的联系信息
						</p>
					</div>
					<div class="mid_block ">
						<table class="ml30" cellspacing="10" cellpadding="0">
							<tr>
								<td align="right" width="100">
									<b>地址</b>
								</td>
								<td>
									${model.address }
								</td>
							</tr>
							<tr>
								<td align="right">
									<b>邮编</b>
								</td>
								<td>
									${model.postNumber }
								</td>
							</tr>


							<tr>
								<td align="right">
									<b>收件人</b>
								</td>
								<td>
									${model.recieverName }
								</td>
							</tr>

							<tr>
								<td align="right">
									<b>备注</b>
								</td>
								<td>
									<com:topic value="${model.description }" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<b>状态</b>
								</td>
								<td>
									 <c:if
												test="${model.transState eq 0}">新建</c:if> <c:if
												test="${model.transState eq 1}">已付款</c:if> <c:if
												test="${model.transState eq 2}">已发货</c:if> <c:if
												test="${model.transState eq 3}">成交</c:if> 
								</td>
							</tr>
							
							
							
						</table>
						<br class="clear" />
						<c:if test="${model.transState eq 0}">&nbsp;&nbsp;<a href="/money.action?method=pay&orderId=${model.id }" class="link_btn">使用支付宝网上安全支付</a></c:if>
						&nbsp;&nbsp;&nbsp;
						<a href="${model.referenceUrl }" class="link_btn">查看订单来源</a>
						&nbsp;&nbsp;&nbsp;
						<a href="order.action?method=list" class="link_btn">管理订单</a>
						&nbsp;&nbsp;&nbsp;
						<a href="/" class="link_btn">返回首页</a>
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