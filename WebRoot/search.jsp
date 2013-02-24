<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="./common/error_page.jsp"%>
<%@include file="./common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="common/include.jsp"%>
		<title><bean:message key="page.topic.search.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			//$(document).ready(function() {
			//$("#author").autocomplete("ajax.do?method=searchUser", {delay: 150,selectFirst: true,max:100,limit:10000,minChars: 0, matchContains: true});
			//});
			//====================
			//将页面中的关键字高亮显示
			//====================
			function highLight(nWord){
				var orange = document.body.createTextRange();
				orange.moveToElementText(document.getElementById("left_block top_blank"));
				while(orange.findText(nWord)){
					orange.pasteHTML("<span style='background:yellow;'>" + orange.text + "</span>");
					orange.moveStart('character',1);
				}
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp"></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.topic.search.title" />
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="./_operationBlock.jsp"></jsp:include>
					<jsp:include page="./_adBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<ul id="tabs_gray" class="lightgray_bg graylink">
						<li class="current"><bean:message key="page.topic.search.title" /></li><li class="normal"><a href="user.shtml?method=search"><bean:message key="page.user.search.title" /></a></li>
					</ul>
					<div class="mid_block top_blank_wide clearfix">
						<pg:pager items="${count}" url="search.action" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
							<pg:param name="searchType"/>
							<pg:index>
								<div class="pageindex">
									<jsp:include flush="true" page="./common/jsptags/simple.jsp"></jsp:include>
								</div>
							</pg:index>
							<logic:notEmpty name="models">
								<logic:iterate id="model" name="models" indexId="index">
									<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
										<li class="col1-5">
											&nbsp;
										</li>
										<li class="col20-5">
											<a href="u/${model.author.userId }"><img src="<com:img type="mini" value="${model.author.userFace }" />" class="userFace_border" alt="${model.author.userName }" align="absmiddle" height="16" width="16" /></a>&nbsp;&nbsp;<ctba:wrapuser user="${model.author}"/>
										</li>
										<li class="col63" style="overflow: hidden">
											<a href="${model.url }"><community:limit value="${model.label }" maxlength="46" /></a>
											<%--${model.category }--%>
										</li>
										<li class="col15 font_mid color_gray">
											<community:date value="${model.createTime }" start="0" limit="11" />
										</li>
									</ul>
								</logic:iterate>
							<pg:index>
								<div class="pageindex_bottom" style="margin-top:8px">
									<jsp:include flush="true" page="./common/jsptags/simple.jsp"></jsp:include>
								</div>
							</pg:index>
							</logic:notEmpty>
						</pg:pager>
				</div></div>
			</div>
			<div id="area_right">
				<div class="state">
					<form action="search.action" method=post>
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td><bean:message key="page.search.header.author" /></td>
								<td><input type="text" class="search_input" name="author" id="author" value="${author }" /></td>
							</tr>
							<tr>
								<td><bean:message key="page.search.header.title" /></td>
								<td><input type="text" class="search_input" name="title" value="${title }" /></td>
							</tr>
							<tr>
								<td><bean:message key="page.search.header.type" /></td>
								<td>
									<select name="searchType" class="search_input">
										<option value="1" <c:if test="${searchType eq '1' }">selected="selected"</c:if>>
											<bean:message key="page.search.bbs.long" />
										</option>
										<option value="2" <c:if test="${searchType eq '2' }">selected="selected"</c:if>>
											<bean:message key="page.search.blog.long" />
										</option>
										<option value="3" <c:if test="${searchType eq '3' }">selected="selected"</c:if>>
											<bean:message key="page.search.news.long" />
										</option>
										<option value="4" <c:if test="${searchType eq '4' }">selected="selected"</c:if>>
											<bean:message key="page.search.group.long" />
										</option>
										<option value="5" <c:if test="${searchType eq '5' }">selected="selected"</c:if>>
											<bean:message key="page.search.subject.long" />
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right"><input type="submit" value="<bean:message key="page.search.button.submit" />" class="input_btn" /></td>
							</tr>
						</table>
					</form>
					<%--
					<form method="get" action="http://www.google.cn/custom" target="google_window">
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>谷歌</td>
								<td>
									<input type="hidden" name="domains" value="www.ctba.cn"></input>
									<label for="sbi" style="display: none">输入您的搜索字词</label>
									<input type="text" name="q" size="31" maxlength="255" value="" id="sbi" class="search_input"></input>
								</td>
							</tr>
							<tr>
								<td class="right" colspan="2">
									<label for="sbb" style="display: none">提交搜索表单</label>
									<input type="radio" name="sitesearch" value="" id="ss0" class="nborder"></input><label for="ss0" title="搜索网络">互联网</label>
									<input type="radio" name="sitesearch" value="www.ctba.cn" checked id="ss1" class="nborder"></input><label for="ss1" title="搜索 www.ctba.cn"><bean:message key="sys.name" /></label>
									<input type="hidden" name="client" value="pub-3911382285138100"></input>
									<input type="hidden" name="forid" value="1"></input>
									<input type="hidden" name="ie" value="UTF-8"></input>
									<input type="hidden" name="oe" value="UTF-8"></input>
									<input type="hidden" name="safe" value="active"></input>
									<input type="hidden" name="cof" value="GALT:#ff7700;GL:1;DIV:#ffffff;VLC:663399;AH:center;BGC:FFFFFF;LBGC:ff7700;ALC:0466AC;LC:0466AC;T:000000;GFNT:aaaaaa;GIMP:aaaaaa;LH:38;LW:225;L:http://www.ctba.cn/images/ctlogobeta1.png;S:http://www.ctba.cn;FORID:1"></input>
									<input type="hidden" name="hl" value="zh-CN"></input>
								</td>
							</tr>
							<tr>
								<td class="right" colspan="2"><input type="submit" name="sa" value="搜索" id="sbb" class="input_btn"></input></td>
							</tr>
						</table>
					</form>
					--%>
				</div>
				<div class="adsenes_right_block">
					<script type="text/javascript"><!--
						google_ad_client = "pub-3911382285138100";
						/* 180x150, 09-2-23 */
						google_ad_slot = "3404358666";
						google_ad_width = 180;
						google_ad_height = 150;
						//-->
					</script>
					<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
				</div>
				<div class="state">
					<ul class="infobar">
						<li class="icons_feed">
							<a href="rss.shtml" target="_blank"><bean:message key="rss.feed" /></a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="./common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include flush="true" page="bottom.jsp"></jsp:include>
	</body>
</html>