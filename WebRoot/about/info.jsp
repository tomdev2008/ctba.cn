<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>关于我们&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@ include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;关于我们
					</div>
				</div>
				<div class="left_block">
					<div class="bh_title">
						<h2 class="bold">网站简介</h2>
					</div>
					<div class="bh_block">
						<img src="images/cs.png" align="left" />扯谈社（英文缩写 CTBA）是一家 Web2.0 体育互动网站。它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。
					</div>
					<div class="bh_title">
						<h2 class="bold">扯谈社历史</h2>
					</div>
					<div class="bh_block orangelink">
						<ul id="history">
							<li><b>2002 年 5 月</b><br />扯谈社以社团形式创建于网易体育社区，以篮球评论为主；</li>
							<li><b>2002 年 6 月</b><br />扯谈社成立独立网站，开始网站化运作；凭借其独到的篮球见解与网络化的调侃文风，受到广大篮球爱好者一致好评；</li>
							<li><b>2002 年 7 月 - 2005 年 9 月</b><br />扯谈社网站与《体育世界-灌篮》（现更名《灌篮》）杂志及多家篮球网站进行专栏合作；</li>
							<li><b>2005 年 10 月 - 2007 年 7 月</b><br />扯谈社网站与《NBA体育时空》杂志进行专栏及社区合作并多次开展线下篮球活动；</li>
							<li><b>2007 年 7 月 - 2007 年 11 月</b><br />扯谈社网站结束与平面媒体的合作，并暂时关闭社区服务，重新规划内容；</li>
							<li><b>2007 年 12 月</b><br />扯谈社重新开放，重构后的网站摒弃了以往的社区、新闻产品，坚持自主开发原则，旨在根据用户需求不断灵活改进，并将网站定位由原先的篮球评论扩展到体育互动，融入了更多的 SNS 元素。 </li>
							<li class="last"><b>2008 年 12 月</b><br />扯谈社网站与《篮天下Dime》杂志进行线上及线下合作，这也是扯谈社合作的第三本官方授权篮球类刊物。</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="area_right">
				<div class="state">
					<ul class="right_menu">
						<li class="current">关于我们</li>
						<li><a href="#">合作伙伴</a></li>
						<li><a href="media">媒体活动</a></li>
						<li><a href="privacy">隐私保护</a></li>
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