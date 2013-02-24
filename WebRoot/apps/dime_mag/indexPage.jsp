<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>篮天下Dime官方网站</title>
		<%@include file="./include.jsp"%>
		<link rel="stylesheet" type="text/css" href="${pluginContext}/css/dime.css" media="all" />
	</head>
	<body>
		<div id="wrapper">
			<jsp:include page="header.jsp"></jsp:include>
			<div id="content" class="clearfix">
				<div id="news" class="fleft">
					<h3>新闻列表&nbsp;News</h3>
					<c:if test="${not empty newsList}">
					<pg:pager items="${count}" url="dimeMagPlugin.action" index="center"
						maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="cid" />
						<pg:index>
					</pg:index>
					<c:forEach items="${newsList}" var="model" varStatus="status">
					<div class="post">
						<div class="info">
							<%--<a rel="category tag" title="dime" href="#">Dime</a> /--%>
						<span class="date">${model.entry.updateTime } </span>
					</div>
					<h2>
						<a href="news/${model.entry.id }">${model.entry.title } </a>
					</h2>
					<p class="byline redlink">
						By&nbsp;<c:if test="${not empty model.entry.author}"><ctba:wrapuser user="${model.user }"/></c:if>
					</p>
					<div class="entry redlink">
						${model.entry.subtitle }&nbsp;&nbsp;<a href="news/${model.entry.id }">继续阅读 »</a>
					</div>
					<p class="postmetadata redlink">
						<a title="${model.entry.title }" href="news/${model.entry.id }">${model.commentCnt } 条评论 »</a>&nbsp;|&nbsp;<c:if test="${not empty model.entry.author}"><a rel="category tag" href="userpage/<community:url value="${model.entry.author }"/>">${model.entry.author }</a>&nbsp;投递</c:if>
						<%--&nbsp;|&nbsp; ><span><a class="stbutton stico_default" title="" href="#"><span class="stbuttontext">分享本文</span></a></span>--%>
					</p>
				</div>
				</c:forEach>
				<div class="pageindex_list_bottom">
					<jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>&nbsp;&nbsp;
				</div>
				</pg:pager>
				</c:if>
			</div>
			<div id="sidebar" class="fleft">
				<jsp:include page="_currentissue.jsp"></jsp:include>
				<h3>热门新闻 Hot News</h3>
				<ul class="digglist">
					<c:if test="${not empty hotNewses}">
					<c:forEach items="${hotNewses}" var="model" varStatus="index">
					<li>
						<div class="fleft diggmini">
							<b>${model.hitGood}</b>
						</div>
						<div class="fleft digglt2">
							<a title=">${model.title}" href="news/${model.id}">${model.title}</a>
							<span class="font_small color_red"><b>NEW!</b></span>
						</div>
						</li>
					</c:forEach>
					</c:if>
				</ul>
				<jsp:include page="_hotplayers.jsp"></jsp:include>
					<%--
					暂时没有
					<h3>
						热门球鞋 Sneakers
					</h3>
					<ul class="pix_show clearfix">
						<li class="fleft">
							<a href="#"><img src="${pluginContext}/img/thumb_0.01823200 1214275027_1.jpg" />
							</a>
						</li>
						<li class="fright">
							<a href="#"><img src="${pluginContext}/img/thumb_0.26444400 1214792357_1.jpg" />
							</a>
						</li>
						<li class="fleft">
							<a href="#"><img src="${pluginContext}/img/thumb_0.76927000 1218517105_3.jpg" />
							</a>
						</li>
						<li class="fright">
							<a href="#"><img
									src="${pluginContext}/img/thumb_0.78644600 1213598545_AJ704.jpg" /> </a>
						</li>
						<li class="fleft">
							<a href="#"><img src="${pluginContext}/img/thumb_20080625_1.jpg" /> </a>
						</li>
						<li class="fright">
							<a href="#"><img src="${pluginContext}/img/thumb_20080630_1.jpg" /> </a>
						</li>
						<li class="fleft">
							<a href="#"><img
									src="${pluginContext}/img/thumb_0.33399900 1209447421_air foamposite one.jpg" />
							</a>
						</li>
						<li class="fright">
							<a href="#"><img src="${pluginContext}/img/thumb_0.75548200 1218517092_1.jpg" />
							</a>
						</li>
					</ul>
					--%>
					<%--
					暂时没有
					<h3>
						投票区 Poll
					</h3>
					<div id="vote" class="brown_bg clearfix">
						<div class="vote_title">
							今夏哪笔自由球员续约最失败？
						</div>
						<div class="dem-results">
							<ul>
								<li>
									<input name="" type="radio" value="" />
									<label>
										奇才5k万续约贾米森
									</label>
								</li>
								<li>
									<input name="" type="radio" value="" />
									<label>
										山猫7200万续约奥卡福
									</label>
								</li>
								<li>
									<input name="" type="radio" value="" />
									<label>
										老鹰5800万跟进史密斯
									</label>
								</li>
								<li>
									<input name="" type="radio" value="" />
									<label>
										勇士6600万续约埃利斯
									</label>
								</li>
								<li>
									<input name="" type="radio" value="" />
									<label>
										勇士5400万续德林斯
									</label>
								</li>
								<li>
									<input name="" type="radio" value="" />
									<label>
										猛龙4k万续约卡尔德隆
									</label>
								</li>
							</ul>
							<input class="dem-vote-button" name="" type="button"
								value="&nbsp;投票&nbsp;" />
							<a href="#" class="dem-vote-link">查看结果</a>
						</div>
					</div>
					--%>
					<jsp:include page="_columns.jsp"></jsp:include>
					<%-- 
				    暂时没有
					<h3>
						视频区&nbsp;Videos
					</h3>
					<div id="video">
						<img src="${pluginContext}/img/video.png" alt="" width="201" height="140" />
					</div>
					--%>
				</div>
				<div id="adbar" class="fleft">
					<c:if test="${empty __sys_username}">
						<h3 class="color_red">登录&nbsp;Login</h3>
						<div id="login">
							<form action="userLogin.action" method="post">
								<ul>
									<li>
										<input class="text" name="username" type="text" />
									</li>
									<li>
										<input class="text" name="password" type="password" />
									</li>
									<li class="right">
										<input class="btn" name="" type="button" onclick="location.href='userExt.do?method=regForm';" value="&nbsp;注册&nbsp;" />
										<input class="btn" name="" type="submit" value="&nbsp;登录&nbsp;" />
									</li>
								</ul>
							</form>
						</div>
					</c:if>
					<img src="${pluginContext}/img/ad.jpg" alt="" width="160" height="599" />
					<br />
					<br />
					<script type="text/javascript"><!--
		            google_ad_client = "pub-3911382285138100";
		            /* 160x600, 创建于 08-9-10 */
		            google_ad_slot = "7481171517";
		            google_ad_width = 160;
		            google_ad_height = 600;
		            //-->
		            </script>
					<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>