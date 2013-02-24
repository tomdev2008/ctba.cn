<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>合作伙伴&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@ include file="../common/include.jsp"%>
		<style type="text/css">
			.bh_block ol {
				list-style: none
			}
			.bh_block ol li {
				float: left;
				height: 38px;
				margin: 0 22px 0 0
			}
			.bh_block ol li a#partners_j2bb {
				display: block;
				width: 128px;
				height: 38px;
				text-indent: -9999px;
				background: url(images/co/j2bb.png) no-repeat
			}
			.bh_block ol li a#partners_mockee {
				display: block;
				width: 178px;
				height: 30px;
				margin: 4px 0 0 0;
				text-indent: -9999px;
				background: url(images/co/mockee.gif) no-repeat
			}
			.bh_block ol li a#partners_dime {
				display: block;
				width: 156px;
				height: 38px;
				text-indent: -9999px;
				background: url(images/co/dime.gif) no-repeat
			}
		</style>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;合作伙伴
					</div>
				</div>
				<div class="left_block">
					<div class="bh_title">
						<h2 class="bold">合作伙伴</h2>
					</div>
					<div class="bh_block orangelink">
						<ol class="clearfix">
							<li>
								<a href="http://www.j2bb.com" id="partners_j2bb" title="J2BB">J2BB</a>
							</li>
							<li>
								<a href="http://www.mockee.com" id="partners_mockee" title="Mockee">Mockee</a>
							</li>
							<li>
								<a href="http://www.dimechina.com" id="partners_dime" title="篮天下Dime">篮天下Dime</a>
							</li>
						</ol>
					</div>
				</div>
			</div>
			<div id="area_right">
				<div class="state">
					<ul class="right_menu">
						<li><a href="aboutus">关于我们</a></li>
						<li class="current">合作伙伴</li>
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