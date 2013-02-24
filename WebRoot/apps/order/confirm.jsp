<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.alipay.util.*"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>订单信息确认&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
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
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;订单信息确认
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">

					<div class="mid_block lightgray_bg">
						
						<div class="title">
							订单详情确认
						</div>
						<div class="color_orange">
							&nbsp;&nbsp;以下是您的交易订单详情，请确认。
						</div>
						<form name="eq_form" method="post" action="order.action?method=submit&id=10&type=activity" >
						 <input type="hidden" name="price" value="${model.price }" />
						  <input type="hidden" name="type" value="${model.type }" />
						  <input type="hidden" name="title" value="[支付]${model.title }" />
						  <input type="hidden" name="owner" value="${model.owner }" />
						  <input type="hidden" name="referenceUrl" value="${model.referenceUrl }" />
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
							</tr><tr>
								<td align="right">
									<b>收费者</b>
								</td>
								<td>
									${model.owner }
									&nbsp;&nbsp;<span class="color_gray">由扯谈社收费平台代收</span>
								</td>
							</tr>
							
							<tr>
								<td align="right">
									<b>联系电话</b>
								</td>
								<td>
									<input type="text" name="phone" id="phone" class="formInput"  size="50" />
									&nbsp;&nbsp;<span class="color_gray">请务必填写</span>
								</td>
							</tr>
							<tr>
								<td align="right">
									<b>提货方式</b>
								</td>
								<td>
									<select name="fetchType" id="fetchType">
									<option value="1">不需提货</option>
									<option value="2">上海市内自提</option>
									<option value="3">快递送货</option>
									</select>
									&nbsp;&nbsp;<span class="color_gray">如选择快递送货，费用统一为25元，由您来承担</span>
								</td>
							</tr>
							</tr>
						</table>
						<br/>
						<div class="title">
							补充联系信息
						</div><p class="color_orange">&nbsp;&nbsp;请填写您的联系信息，以便发送商品</p>
						<table class="ml30" cellspacing="10" cellpadding="0">
							<tr>
								<td align="right" width="100">
									<b>地址</b>
								</td>
								<td>
									<input type="text" name="address" id="address"  class="formInput"  size="50" />
									&nbsp;&nbsp;<span class="color_gray">如:上海市浦东新区XX路</span>
								</td>
							</tr><tr>
								<td align="right">
									<b>邮编</b>
								</td>
								<td>
									<input type="text" name="postNumber" id="postNumber"  class="formInput"  size="50" />
									&nbsp;&nbsp;<span class="color_gray">如:200010</span>
								</td>
							</tr>
							
							
							<tr>
								<td align="right">
									<b>收件人</b>
								</td>
								<td>
									<input type="text" name="recieverName" id="recieverName" value="${__sys_username }" class="formInput"  size="50" />
								</td>
							</tr>
							
							<tr>
								<td align="right">
									<b>备注</b>
								</td>
								<td>
									<textarea rows="2" cols="50" name="description" id="description"></textarea>
								</td>
							</tr>
							<tr>
								<td>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;
									<input type='submit' class="input_btn" name='v_action'
										value='确定提交' />
									&nbsp;&nbsp;&nbsp;
									<input type='button' class="input_btn"
										onclick="history.go(-1);" value='取消返回' />
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