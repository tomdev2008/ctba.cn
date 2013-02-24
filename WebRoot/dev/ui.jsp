<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="./common/error_page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>CTBA XHTML & CSS Illustration</title>
		<link rel="stylesheet" type="text/css" href="../theme/default/css/general.css" media="all" />
		<style type="text/css">
			body {
				font: 13px Arial, Helvetica, sans-serif
			}
			h1 {
				font-size: 22px;
				font-weight: bold
			}
			h2 {
				font-size: 15px;
				font-weight: bold
			}
			table {
				margin: 30px 0;
				text-align: center;
				background: #eee
			}
			table tr td {
				background: #fff
			}
			table tr td.items {
				background: #ddd
			}
			.color_white {
				color: #fff
			}
			.orange_bg {
				background: #f70
			}
			.font_code {
				font-family: "Courier New", Courier, monospace
			}
		</style>
	</head>
	<body>
		<div id="wrapper">
			<div id="content">
				<table width="100%" cellpadding="10" cellspacing="1">
					<tr>
						<td colspan="6" class="orange_bg color_white">
							<h1>
								CTBA XHTML & CSS Illustration v.090225
							</h1>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<h2>
								文字颜色（color）& 背景颜色（background-color）& 链接颜色（a, a:hover）
							</h2>
						</td>
					</tr>
					<tr>
						<td class="items">
							<strong>名称</strong>
						</td>
						<td class="items">
							<strong>实例</strong>
						</td>
						<td class="items">
							<strong>文字颜色</strong>
						</td>
						<td class="items">
							<strong>区域背景色</strong>
						</td>
						<td class="items">
							<strong>链接颜色</strong>
						</td>
						<td class="items">
							<strong>悬停颜色</strong>
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.color_gray
						</td>
						<td class="color_gray">
							灰色文字
						</td>
						<td class="color_gray">
							#999
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.color_darkgray
						</td>
						<td class="color_darkgray">
							深灰色文字
						</td>
						<td class="color_darkgray">
							#666
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.color_orange
						</td>
						<td class="color_orange">
							橙色文字
						</td>
						<td class="color_orange">
							#f70
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.color_red
						</td>
						<td class="color_red">
							红色文字
						</td>
						<td class="color_red">
							#cc2a50
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.color_blue
						</td>
						<td class="color_blue">
							蓝色文字
						</td>
						<td class="color_blue">
							#0093bb
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.ornagelink
						</td>
						<td class="orangelink">
							<a href="#">橙色链接</a>
						</td>
						<td class="color_orange">
							#f70
						</td>
						<td>
							none
						</td>
						<td class="color_orange">
							#f70
						</td>
						<td>
							<span class="orange_bg color_white">#fff</span>
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.graylink
						</td>
						<td class="graylink">
							<a href="#">灰色链接</a>
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td>
							#111
						</td>
						<td style="color:#90909f">
							#90909f
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.darkgraylink
						</td>
						<td class="darkgraylink">
							<a href="#">深灰色链接</a>
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td class="darkgraylink">
							#999
						</td>
						<td style="color:#000">
							#000
						</td>
					</tr>
					<tr class="font_code">
						<td>
							.bluelink
						</td>
						<td class="bluelink">
							<a href="#">蓝色链接</a>
						</td>
						<td>
							none
						</td>
						<td>
							none
						</td>
						<td style="color:#377bdc">
							#377bdc
						</td>
						<td style="color:#cc2a50">
							#cc2a50
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
