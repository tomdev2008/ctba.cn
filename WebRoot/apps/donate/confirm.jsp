<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.alipay.util.*"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>捐赠扯谈&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;
						<a href="/donate/">捐赠扯谈</a>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">

					<jsp:include flush="true" page="_donateBlock.jsp" />
					<div class="mid_block lightgray_bg">
						<div class="message_info">
							您确定要向扯谈社捐赠 ${price } 元RMB?
						</div>
						<form name="alipaysubmit" method="post"
							action="https://www.alipay.com/cooperate/gateway.do">
							<input type=hidden name="body" value="${body }">
							<input type=hidden name="out_trade_no" value="${out_trade_no}">
							<input type=hidden name="partner" value="${partner}">
							<input type=hidden name="payment_type" value="${payment_type}">
							<input type=hidden name="seller_email" value="${seller_email}">
							<input type=hidden name="service" value="${service}">
							<input type=hidden name="sign" value="${itemUrl }">
							<input type=hidden name="sign_type" value="${sign_type }">
							<input type=hidden name="subject" value="${subject}">
							<input type=hidden name="price" value="${price}">
							<input type=hidden name="quantity" value="${quantity}">
							<input type=hidden name="discount" value="${discount}">
							<input type=hidden name="show_url" value="${show_url}">
							<input type=hidden name="return_url" value="${return_url}">
							<input type=hidden name="notify_url" value="${notify_url }">
							<input type=hidden name="_input_charset" value="${input_charset }">
							<input type=hidden name="logistics_type" value="EMS">
							<input type=hidden name="logistics_fee" value="0.01">
							<input type=hidden name="logistics_payment" value="SELLER_PAY">
		<table>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr><tr>
									<td>
										&nbsp;&nbsp;&nbsp;<input type='submit' class="input_btn" name='v_action'
											value='确定捐赠' />
										&nbsp;&nbsp;&nbsp;
										<input type='button' class="input_btn" onclick="history.go(-1);"
											value='返回' />
									</td>
								</tr>
							</table>
						</form>

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