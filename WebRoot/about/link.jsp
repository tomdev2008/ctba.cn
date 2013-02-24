<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>友情链接&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@ include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;友情链接
					</div>
				</div>
				<div class="left_block">
					<div class="bh_title">
						<h2 class="bold">合作方式</h2>
					</div>
					<style type="text/css">
						.bh_block table.link_list {width:692px;background:#f7f7f7;padding: 0 10px}
						.bh_block table.link_list tr td {width:20%}
						.bh_block table.link_list img {margin:0 6px 0 0}
					</style>
					<div class="bh_block orangelink">
						<ul>
							<li>
								如需交换友情链接，请您先在贵站做好本站链接：<a href="http://ctba.cn">扯谈社</a>
							</li>
							<li>
								发送友情链接申请邮件，内容包括网站名称、简介、地址、PR值、Alexa 排名、链接位置等
							</li>
							<li>
								邮件地址：<span class="color_orange">ctba.link(a)gmail.com</span>，我们将在 1 个工作日内完成审核并给予您回复
							</li>
							<li>
								基本要求：首页链接，贵站首页 PR 值至少为 5；内页链接，PR 值至少为 4
							</li>
							<li>
								其他合作：媒体专栏、线下活动等请邮件至 <span class="color_orange">ctba.cn(a)gmail.com</span>
							</li>
						</ul>
					</div>
					<div class="bh_title">
						<h2 class="bold">友情链接</h2>
					</div>
					<div class="bh_block">
						<table class="link_list">
							<tr>
								<td>
									<img class="photo_link" src="images/icons/cops/askform.gif" align="absmiddle" /><a href="http://www.askform.cn/" target="_blank">问道网</a>
								</td>
								<td>
									<img class="photo_link" src="images/icons/cops/tvvvt.gif" align="absmiddle" /><a href="http://www.tvvvt.cn/" target="_blank">TVVVT</a>
								</td>
								<td>
									<img class="photo_link" src="images/icons/cops/tom.gif" align="absmiddle" /><a href="http://bbs.sports.tom.com/f_106.html/" target="_blank">灌篮高手</a>
								</td>
								<td>
									<img class="photo_link" src="images/icons/cops/51soccer.gif" align="absmiddle" /><a href="http://www.51soccer.com/" target="_blank">球迷商城</a>
								</td>
								<td>
									<img class="photo_link" src="images/icons/cops/58.gif" align="absmiddle" /><a href="http://www.58.com">58同城分类</a>
								</td>
							</tr>
							<tr>
								<td>
									<img class="photo_link" src="images/icons/cops/ganji.gif" align="absmiddle" /><a href="http://club.ganji.com/">赶集社区</a>
								</td>
								<td>
									<img class="photo_link" src="images/icons/cops/baixing.gif" align="absmiddle" /><a href="http://bbs.baixing.com/">百姓网社区</a>
								</td>
								<td>
									<img class="photo_link" src="images/icons/cops/cuju2.gif" align="absmiddle" /><a href="http://www.cuju2.com/">Cuju梦幻体育社区</a>
								</td>
								<td><img class="photo_link" src="images/icons/cops/el.gif" align="absmiddle" /><a href="http://www.embeddedlinux.org.cn/">嵌入式Linux中文站</a>
								</td>
								<td></td>
							</tr>
						</table>
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
						<li class="current">友情链接</li>
						<li><a href="board/7">社员反馈</a></li>
						<li class="last"><a href="board/7">联系我们</a></li>
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