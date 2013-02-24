<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>隐私保护&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
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
						<h2 class="bold">隐私声明</h2>
					</div>
					<div class="bh_block">
						扯谈社以此声明对本站用户隐私保护的许诺。扯谈社的隐私声明正在不断改进中，随着扯谈社服务范围的扩大，会随时更新隐私声明。我们欢迎您随时查看隐私声明。如果您在对本隐私政策任何细微修改发布后继续使用网站服务，则表示同意并遵守扯谈社最新补充修正的隐私政策。
					</div>
					<div class="bh_title">
						<h2 class="bold">隐私原则</h2>
					</div>
					<div class="bh_block orangelink">
						不经过您明确的允许，扯谈社不会在网站的任何页面公开，也不会向任何外部实体和个人透露您的&nbsp;email&nbsp;地址。您的&nbsp;email&nbsp;地址的用途限于辨别您的身份和保证在您忘记密码的时候能恢复您在扯谈社的身份和数据。
						以下三种情况例外：<br />
						<ul>
							<li>您同意公开个人资料，以享受扯谈社为您提供的产品和服务；</li>
							<li>当通过扯谈社&nbsp;<a href="c.action?method=find">邀请朋友</a>&nbsp;时，您的&nbsp;email&nbsp;地址将出现在邀请信中，使您的朋友能够辨别邀请来源；</li>
							<li>政府执法部门或监管机构依法有权要求扯谈社提供您的注册信息。</li>
						</ul>
						扯谈社将在网站进行重大更新或紧急意外出现时向您发出 email。除此之外的 email 发送都会经过您的明确许可。
					</div>
					<div class="bh_title">
						<h2 class="bold">Cookies</h2>
					</div>
					<div class="bh_block">
						扯谈社使用 Cookies 来储存用户的喜好和记录活动信息以确保用户不会重复收到同样的广告和定制的时事通讯、广告以及基于浏览器类型和成员档案信息的网页内容。我们不会把在 Cookies 中保存的信息与您在我们网站上提交的任何个人识别信息相连接。您可以通过设置您的浏览器以接受或者拒绝全部或部分的 Cookies，或要求在 Cookies 被设置时通知您。
					</div>
					<div class="bh_title">
						<h2 class="bold">密码保护</h2>
					</div>
					<div class="bh_block orangelink">
						扯谈社成员账号通过会员设置的密码确保其安全性。您应当对您密码的保密负责，请不要和他人分享此信息。如果您和他人共享一台电脑，您应该在离开扯谈社时&nbsp;<a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要暂时离开么？','${basePath }userLogout.action');return false;">退出系统</a>&nbsp;以保证您的信息不被后来使用该电脑者获取。扯谈社同时将严格确保您的密码安全，不会以任何形式公开您的密码，系统保留的是加密后的密码。
					</div>
					<br />
				</div>
			</div>
			<div id="area_right">
				<div class="state">
					<ul class="right_menu">
						<li><a href="aboutus">关于我们</a></li>
						<li><a href="partners">合作伙伴</a></li>
						<li><a href="media">媒体活动</a></li>
						<li class="current">隐私保护</li>
						<li><a href="link">友情链接</a></li>
						<li><a href="board/7">社员反馈</a></li>
						<li class="last"><a href="contact">联系我们</a></li>
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