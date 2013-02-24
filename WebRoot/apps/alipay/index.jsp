<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付宝支付</title>
</head>
<%
Date Now_Date=new Date();
String paygateway	=	"https://www.alipay.com/cooperate/gateway.do?";	//'支付接口
String service      = "trade_create_by_buyer";//	create_direct_pay_by_user
String sign_type       =   "MD5";
String out_trade_no		= Now_Date.toString();	//商户网站订单
String input_charset   =  "utf-8";       
String partner			=	"2088002895849562"; //支付宝合作伙伴id (账户内提取)
String key             =    "8lth1lag62j6t2u9a3r2fnly4qobfrl2"; //支付宝安全校验码(账户内提取)
String seller_email		= "gladstone9@gmail.com";		 //卖家支付宝帐户
//******以上是账户信息，以下是商品信息**************************
String body			= "a"; //商品描述，推荐格式：商品名称（订单编号：订单编号）
String subject			= "啊啊";			 //商品名称
String price			= "0.01";				 //订单总价
String quantity    =   "1";
String show_url        =   "www.sina.com.cn";
String payment_type    =   "1";
String discount     =  "0";
//******物流信息和支付宝通知，一般商城不需要通知，请删除此参数，并且在payment.java里面相应删除参数********
String logistics_type = "EMS";
String logistics_fee  = "0.01";
String logistics_payment = "SELLER_PAY";
//String notify_url		= "http://10.2.17.136:8081/jsp_xuni/alipay_notify.jsp";	//通知接收URL
String return_url		= "http://10.2.17.136:8081/jsp_xuni/alipay_return.jsp";	//支付完成后跳转返回的网址URL

//String ItemUrl=Payment.CreateUrl(paygateway,service,sign_type,out_trade_no,input_charset,partner,key,seller_email,body,subject,price,quantity,show_url,payment_type,discount,logistics_type,logistics_fee,logistics_payment,return_url);
                                            //notify_url需要的话请把这个参数加上到上面createurl
%>
		<form name="alipaysubmit" method="post" action="https://www.alipay.com/cooperate/gateway.do?_input_charset=utf-8">		 
		 	<input type=hidden name="body" value="<%=body%>">

		 	<input type=hidden name="logistics_type" value="<%=logistics_type%>">
			<input type=hidden name="logistics_fee" value="<%=logistics_fee%>">
			<input type=hidden name="logistics_payment" value="<%=logistics_payment%>">
			<input type=hidden name="out_trade_no" value="<%=out_trade_no%>">
			<input type=hidden name="partner" value="<%=partner%>">
			<input type=hidden name="payment_type" value="<%=payment_type%>"> 
			<input type=hidden name="seller_email" value="<%=seller_email%>">
			<input type=hidden name="service" value="<%=service%>">
		<%-- 	<input type=hidden name="sign" value="ItemUrl%>"> --%>
			<input type=hidden name="sign_type" value="MD5">      
			<input type=hidden name="subject" value="<%=subject%>">
			<input type=hidden name="price" value="<%=price%>">
			<input type=hidden name="quantity" value="<%=quantity%>">
			<input type=hidden name="discount" value="<%=discount%>">
			<input type=hidden name="show_url" value="<%=show_url%>">
			<input type=hidden name="return_url" value="<%=return_url%>">
		<table>
		<tr>
		<td>
		<input type='button' name='v_action' value='支付宝网上安全即时支付平台' onClick='document.alipaysubmit.submit()'>
		</td>
		</tr>
		</table>
		</form>
<body>

</body>
</html>