<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>联系我们&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@ include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;隐私保护
					</div>
				</div>
				<div class="left_block">
					<div class="bh_title">
						<h2 class="bold">联系我们</h2>
					</div>
					<div class="bh_block orangelink">
						关于网站的使用问题和改进建议请&nbsp;<a id="login_wrap" href="javascript:J2bbCommon.showLoginForm();">登陆</a>&nbsp;后到&nbsp;<a href="b/7">社员反馈</a>&nbsp;板块发贴。<br />
						<ul>
							<li>邮件地址：ctba.cn(a)gmail.com</li>
							<li>联系地址：北京市海淀区五道口华清嘉园 6#503</li>
							<li>邮政编码：100083</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="area_right">
				<div class="state">
					<ul class="right_menu">
						<li><a href="aboutus">关于我们</a></li>
						<li><a href="#">合作伙伴</a></li>
						<li><a href="media">媒体活动</a></li>
						<li><a href="privacy">隐私保护</a></li>
						<li><a href="link">友情链接</a></li>
						<li><a href="board/7">社员反馈</a></li>
						<li class="current last">联系我们</li>
					</ul>
				</div>
				<div class="adsenes_right_block">
					<script type="text/javascript"><!--
						google_ad_client = "pub-3911382285138100";
						/* 180x150, 创建于 09-2-23 */
						google_ad_slot = "3404358666";
						google_ad_width = 180;
						google_ad_height = 150;
						//-->
					</script>
					<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
				</div>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>