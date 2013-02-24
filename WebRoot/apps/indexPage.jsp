<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>扯谈应用&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<style type="text/css">
		.top_info_text {
			line-height: 22px;
			padding: 10px;
			width: 380px;
			height:90px;
		}
		.top_info_pic{
			line-height: 22px;
			padding: 10px;
			height:90px;
		}
		</style>
	</head>
	<body>
		<c:import url="../head.jsp">
			<c:param name="submenu" value="app"></c:param>
		</c:import>

		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;扯谈应用
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
				<jsp:include flush="true" page="../ad/_raywowAdBlock.jsp" />
					<div class="mid_block top_notice clearfix">
						<div class="top_info_pic fleft">
							<a href="album/"><img src="images/album_logo.jpg" /> </a>
						</div>
						<div class="top_info_text fleft">
							<h1 class="color_orange orangelink">
								<a href="album/"><b>扯谈相册</b> </a>
							</h1>
							<h3 class="color_gray">
								用图片记录每一个美好瞬间。<br/>
								每个用户使用空间没有限制，个人照片尽情上传！
							</h3>
						</div>
					</div>

					<br class="clearfix" />

					<div class="mid_block top_notice clearfix">
						<div class="top_info_pic fleft">
							<a href="share/"><img src="images/share_logo.jpg" /> </a>
						</div>
						<div class="top_info_text fleft">
							<h1 class="color_orange orangelink">
								<a href="share/"><b>扯谈分享</b> </a>
							</h1>
							<h3 class="color_gray">
								随时随地记录和分享，这一刻你在做什么？
							</h3>
						</div>
					</div>
					<br class="clearfix" />

					<div class="mid_block top_notice clearfix">
						<div class="top_info_pic fleft">
							<a href="vote/"><img src="images/vote_logo.jpg" /></a>
						</div>
						<div class="top_info_text fleft">
							<h1 class="color_orange orangelink">
								<a href="vote/"><b>扯谈投票</b> </a>
							</h1>
							<h3 class="color_gray">
								外场打球选什么篮球鞋最好？大家平时都看什么杂志？哪项运动对减肥最有效？
								给出你的候选项，看看大家的答案和选择，让你的所有好友投票帮你决定吧！
							</h3>
						</div>
					</div>
					<br class="clearfix" />
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="../ad/_raywowFrameBlock.jsp" />
				<jsp:include flush="true" page="right.jsp" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>