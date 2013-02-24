<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="page.user.regSucceed.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@include file="../common/include.jsp"%>
		<script language='Javascript'>
			function go(){
				window.location="setting"; 
			}
			//setTimeout("go()",5000);
			$(function(){
				$("#tabs_gray li.tlink a").click(function(){
					$("#tabs_gray li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});
			});
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />
					&nbsp;
					<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;>&nbsp;<bean:message key="page.user.regSucceed.title" />
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../_adBlock.jsp"></jsp:include>
				</div>

				<div id="mid_column" class="fright">
					<div class="mid_block top_notice clearfix">
						<h2 class="color_orange">
							&nbsp;&nbsp;
							<b>${__sys_username }, <bean:message key="page.user.regSucceed.hint" /></b>
						</h2>
						<br />
						<table>
							<tr>
								<td>
									&nbsp;&nbsp;
									<img src="images/ico_sendf.gif" />
									&nbsp;&nbsp;
								</td>
								<td>
									<bean:message key="page.user.regSucceed.hint.continue" />
								</td>
							</tr>
						</table>

					</div>

					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="current tlink">
								<a name="commend_group"><bean:message key="page.user.regSucceed.tab.group" /></a>
							</li>
							<li class="normal tlink">
								<a name="commend_bbs"><bean:message key="page.user.regSucceed.tab.bbs" /></a>
							</li>
							<li class="normal tlink">
								<a name="commend_news"><bean:message key="page.user.regSucceed.tab.news" /></a>
							</li>
							<li class="normal tlink">
								<a name="commend_blog"><bean:message key="page.user.regSucceed.tab.blog" /></a>
							</li>
						</ul>
					</div>

					<div id="commend_group" class="mid_block tabswrap">
						<ul class="artList">
							<c:forEach items="${commendGroup}" var="model">
								<li class=" clearfix">
									<a href="${model.link}">${model.label}</a>
								</li>
							</c:forEach>
						</ul>
						<div class="clearfix ">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="group/" class="rss">MORE</a>
						</div>
					</div>
					
					<div id="commend_bbs" class="mid_block tabswrap hide">
						<ul class="artList">
							<c:forEach items="${commendBbs}" var="model">
								<li class=" clearfix">
									<a href="${model.link}">${model.label}</a>
								</li>
							</c:forEach>
						</ul>
						<div class="clearfix ">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="board/5" class="rss">MORE</a>
						</div>
					</div>
					
					<div id="commend_news" class="mid_block tabswrap hide">
						<ul class="artList">
							<c:forEach items="${commendNews}" var="model">
								<li class=" clearfix">
									<a href="${model.link}">${model.label}</a>
								</li>
							</c:forEach>
						</ul>
						<div class="clearfix ">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="news/" class="rss">MORE</a>
						</div>
					</div>
					
					<div id="commend_blog" class="mid_block tabswrap hide">
						<ul class="artList">
							<c:forEach items="${commendBlog}" var="model">
								<li class=" clearfix">
									<a href="${model.link}">${model.label}</a>
								</li>
							</c:forEach>
						</ul>
						<div class="clearfix ">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="blog/" class="rss">MORE</a>
						</div>
					</div>
					
					

				</div>
			</div>
			<div id="area_right">
				<div class="state orangelink">
					<bean:message key="page.user.regSucceed.hint.right" />
				</div>
				<jsp:include flush="true" page="right.jsp" />
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>