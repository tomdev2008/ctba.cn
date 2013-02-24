<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>扯谈羽球&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<script type="text/javascript">
			$(function(){
			    $("#pic_slider").flashSlider();
				$("#tabs_gray li.tlink a").click(function(){
					$("#tabs_gray li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});
				$(".ew span span").click(function(){
					$(".ew span").removeClass("bold");
					$(this).parent("span").addClass("bold");
					$("div.scoretabwrap").hide();
					$("#" + $(this).attr("class")).show();
					return false;
				});
			});
        </script>
        <style>
		/*容器*/
		slider ul,#slider li {
			margin: 0;
			padding: 0;
			list-style: none;
		}
		
		/*数字导航样式*/
		#flashnvanum {
			bottom: 10px;
			position: absolute;
			right: 20px;
			width: 90px;
		}
		
		#flashnvanum span {
			background: transparent url(images/slider/flashbutton.gif) no-repeat
				scroll -15px 0;
			color: #86A2B8;
			cursor: pointer;
			float: left;
			font-family: Arial;
			font-size: 12px;
			height: 15px;
			line-height: 15px;
			margin: 1px;
			text-align: center;
			width: 15px;
		}
		
		#flashnvanum span.on {
			background: transparent url(images/slider/flashbutton.gif) no-repeat
				scroll 0 0;
			color: #FFFFFF;
			height: 15px;
			line-height: 15px;
			width: 15px;
		}
		</style>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="channel" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;
						羽毛球
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">

					<div class="mb8">
						<div id="pic_slider">
							<ul>
								<li>
									<a href="http://www.udong.com.cn" target="_blank">
                                      <img src="images/ctba/ymq/2.jpg" alt="" />
									</a>
								</li><li>
									<a href="http://www.udong.com.cn" target="_blank">
                                      <img src="images/ctba/ymq/3.jpg" alt="" />
									</a>
								</li><li>
									<a href="http://www.ctba.cn/equipment/shop/9">
                                      <img src="images/ctba/ymq/4.jpg" alt="" />
									</a>
								</li>
								
							</ul>
						</div>
						<%-- 
						<a href="http://www.ctba.cn/equipment/shop/9" target="_blank"> <img src="images/ctba/ymq/2.jpg"
								class="noZoom" /> </a>--%>
					</div>

					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="current tlink">
								<a name="tab_group_new_col3">最新话题</a>
							</li>
							<li class="normal tlink">
								<a name="tab_group_latest_col3">热门话题</a>
							</li>
						</ul>
					</div>
					<div id="tab_group_new_col3" class="tabswrap">
						<c:if test="${not empty updatedGroupTopicsMap}">
							<c:forEach items="${updatedGroupTopicsMap}" var="model">
								<ul
									class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
									<li class="col1-5">
									</li>
									<li class="col20-5">
										<a href="u/${model.user.userId}"><img
												src="<community:img value="${model.user.userFace}" type="mini"/>"
												width="16" height="16" class="userFace_border"
												alt="${model.user.userName}" align="absmiddle" /> </a>&nbsp;&nbsp;
										<a href="u/${model.user.userId}">${model.user.userName}</a>
									</li>
									<li class="col63" style="overflow: hidden">
										<a href="group/topic/${model.topic.id}"><community:limit
												value="${model.topic.title}" maxlength="30" /> </a>&nbsp;&nbsp;
										<span class="font_mid color_orange">${model.topic.replyCnt}/${model.topic.hits}&nbsp;</span>
									</li>
									<li class="col15 font_mid color_gray">
										<community:date value="${model.topic.updateTime }" start="5"
											limit="16" />
									</li>
								</ul>
							</c:forEach>
						</c:if>
					</div>
					<div id="tab_group_latest_col3" class="hide tabswrap">
						<c:if test="${not empty hotGroupTopicsMap}">
							<c:forEach items="${hotGroupTopicsMap}" var="model">
								<ul
									class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
									<li class="col1-5">
									</li>
									<li class="col20-5">
										<a href="u/${model.user.userId}"><img
												src="<community:img value="${model.user.userFace}" type="mini"/>"
												width="16" height="16" class="userFace_border"
												alt="${model.user.userName}" align="absmiddle" /> </a>&nbsp;&nbsp;
										<a href="u/${model.user.userId}">${model.user.userName}</a>
									</li>
									<li class="col63" style="overflow: hidden">
										<a href="group/topic/${model.topic.id}"><community:limit
												value="${model.topic.title}" maxlength="30" /> </a>&nbsp;&nbsp;
										<span class="font_mid color_orange">${model.topic.replyCnt}/${model.topic.hits}&nbsp;</span>
									</li>
									<li class="col15 font_mid color_gray">
										<community:date value="${model.topic.updateTime }" start="5"
											limit="16" />
									</li>
								</ul>
							</c:forEach>
						</c:if>
					</div>
					<a href="http://www.udong.com.cn" target="_blank">
<img src="images/ctba/ct_banner_s.gif" style="width:590px;" alt="绝品装备，尽在优动网" /></a>
					<table width="100%">
						<tr>
							<td valign="top" width="50%">

								<div class="subject_wrap_nar clearfix">
									<div class="gtitle lightgray_bg" style="margin: 0 0 5px 0">
										<span class="gtitle_text">羽球新闻</span>
										<span class="group_type"> &nbsp;&nbsp;&nbsp;<a
											class="rss" href="http://www.ctba.cn/news/list/6">MORE</a> </span>
									</div>

									<ul class="digglist2">
										<c:forEach items="${newsList}" var="model" varStatus="index">
											<li style="width: 260px; margin: 2px; height: 22px;">
												<a href="news/${model.id}"><com:limit
														value="${model.title}" maxlength="15" />
												</a>
												<span class="font_small color_gray"> ${model.hitGood}</span>
											</li>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td valign="top" width="50%">

								<div class="subject_wrap_nar clearfix">
									<div class="gtitle lightgray_bg" style="margin: 0 0 5px 0">
										<span class="gtitle_text">测评博客</span>
										<span class="group_type"> &nbsp;&nbsp;&nbsp;<a
											class="rss" href="http://www.ctba.cn/blog/870">MORE</a> </span>
									</div>
									<ul class="digglist2">
										<c:forEach items="${entryList}" var="model" varStatus="index">
											<li style="width: 260px; margin: 2px; height: 22px;">
												<a href="blog/entry/${model.id}"><com:limit
														value="${model.title}" maxlength="15" />
												</a>
												<span class="font_small color_gray"> ${model.hits}</span>
											</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
					</table>


				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>