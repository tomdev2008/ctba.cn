<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>分享&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<script type="text/javascript">
			$(function(){
				$("#tabs_gray li.tlink a").click(function(){
					$("#tabs_gray li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});

				/* added by mockee 090118 */

				/* 绑定 keyup */
				$("#share_text").bind("keyup", {c1:"share_text", c2:"leftNum", countLimit:160}, counter);
				$("#share_text").one("click", function(){$("#share_text").val("");})

				/* 避免刷新页面 checkbox 状态失效 */
				if ($("#share").attr("checked"))
					$("#share_link").show();
				else
					$("#share_link").hide();

				/* 切换分享文字与链接 */
				$("#share").click(function(){
					if ($("#share").attr("checked")) {
						$("#share_link").show();
						$("#share_text").val("将你感兴趣的话题、音乐链接或是视频的网址填入下方链接输入框中，推荐给你的好友。这里用来填写标题来描述你分享的内容:)");
					}
					else {
						$("#share_link").hide();
						$("#share_text").val("随时随地记录和分享，这一刻你在做什么？");
					}
					$("#share_text").one("click", function(){
						$("#share_text").val("");
					})
				});
				$(".artList li").mouseover(function(){$(this).css("background-color","#fffddd")});
				$(".artList li").mouseout(function(){$(this).css("background-color","#fff")});
			});

			/* 计算剩余字数 */
			function counter(event){
				tLen = $("#"+event.data.c1).val().length;
				tMax = event.data.countLimit;
				tLeft = tMax - tLen;
				$("#"+event.data.c2).text(tLeft);
				if (tLen > tMax) {
					var textLimit;
					textLimit = $("#"+event.data.c1).val().substring(0, tMax);
					$("#"+event.data.c1).val(textLimit);
					$("#"+event.data.c2).html("0");
				}
			}

			function starts(st, wi){
				if (st == '') {return wi == ''}
				return st.substring(wi.length,0)==wi
			}
			function checkForm(){
				J2bbCommon.removeformError("url");
				J2bbCommon.removeformError("label");
				var label=$("#share_text").val();
				var url=$("#share_link").val();
				if(label=='' && url==''){
					J2bbCommon.formError("label","标题和链接不能都为空","right");
					return false;
				}
				if(label.length>160){
					J2bbCommon.formError("label","标题不能多于 160 个字","right");
					return false;
				}
				if(url.length>160 ||(url!='' && !starts(url,"http://"))){
					alert("链接不能为空,不能超过 160 个字,必须是【http://】开头的链接");
					//J2bbCommon.formError("url","链接不能为空,不能超过 160 个字,必须是【http://】开头的链接","bottom");
					return false;
				}

				$("#submitButton").val("分享中...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp"><jsp:param name="submenu" value="app" /></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;分享
				</div>
				<div id="sidebar" class="fleft clearfix">
					<%--<jsp:include page="_logo.jsp"></jsp:include>--%>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlockMini.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<jsp:include page="_shareBlock.jsp" flush="true"/>
					<c:if test="${empty __sys_username}">
						<jsp:include page="../../ad/_raywowAdBlock.jsp"></jsp:include>
					</c:if>
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<c:if test="${empty __sys_username}">
								<li class="current tlink"><a name="share_all">大家的分享</a></li>
							</c:if>
							<c:if test="${not empty __sys_username}">
								<li class="current tlink"><a name="share_all">大家的分享</a></li><li class="normal tlink"><a name="share_friends">朋友们的分享</a></li><li class="normal tlink"><a name="share_mine">我的分享<span class="font_mid">(${myShareCnt })</span></a></li><li class="normal"><a href="share.action?method=myComments">我收到的评论</a></li><li class="normal"><a href="share.action?method=builder">分享秀</a></li>
							</c:if>
						</ul>
					</div>
					<div id="share_mine" class="mid_block tabswrap hide">
						<c:if test="${not empty myShareList}">
							<ul class="artList">
								<c:forEach items="${myShareList}" var="model">
									<li class="clearfix">
										<h3 class="fleft graylink" style="line-height: 20px">
											<c:if test="${model.share.state == 1}">
											<span class="color_gray">[悄悄地]</span></c:if>
											<c:if test="${empty model.share.url}">
												<a href="share/comment/${model.share.id }">
													<g:limit value="${model.share.label}" maxlength="160" />
												</a>
											</c:if>
											<c:if test="${not empty model.share.url}">
												<a href="share/comment/${model.share.id }">
													<g:limit value="${model.share.label}" maxlength="50" />
												</a>
												<a href="${model.share.url}" title="${model.share.label}" target="_blank">
													<img src="images/icons/shape_move_forwards.png" align="absmiddle" alt="在新窗口打开分享链接" title="在新窗口打开分享链接" />
												</a>
											</c:if>
											<span class="font_mid color_gray">
												&nbsp;(${model.commentCnt })&nbsp;<c:if test="${__sys_username eq model.share.username}"><a href="javascript:void(0);" class="bluelink" onclick="J2bbCommon.confirmURL('您确认要删除此分享么？','${basePath }share.shtml?method=deleteShare&id=${model.share.id }');return false;">x</a></c:if>
											</span>
										</h3>
									</li>
								</c:forEach>
							</ul>
							<span class="fright" style="line-height: 20px">
								&nbsp;&nbsp;&nbsp;
								<a class="rss" href="share.shtml?method=shares">查看更多</a>
							</span>
						</c:if>
					</div>
					<div id="comment_mine" class="mid_block tabswrap hide">
						<c:if test="${not empty myCommentList}">
							<ul class="artList">
								<c:forEach items="${myCommentList}" var="model">
									<li class="clearfix">
										<h3 class="fleft" style="line-height: 20px">
											<span class="graylink">
												<a href="share/comment/${model.share.id}" title="${model.body}"><g:limit value="${model.body}" maxlength="50" /> </a>&nbsp;
											</span>
											<span class="font_mid color_gray">
												(<a href="userpage/<com:url value="${model.username}"/>">${model.username}</a>)
											</span>
										</h3>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<div id="share_friends" class="mid_block tabswrap hide">
						<c:if test="${not empty friendsShareList}">
							<ul class="artList">
								<c:forEach items="${friendsShareList}" var="model">
									<li class="clearfix">
										<h3 class="fleft" style="line-height: 20px">
											<strong class="orangelink color_orange">
												<a href="userpage/<com:url value="${model.share.username}"/>">${model.share.username}</a>:
											</strong>
											<span class="graylink">
												<a href="share/comment/${model.share.id}" title="${model.share.label}"><g:limit value="${model.share.label}" maxlength="50" /></a>&nbsp;
											</span>
											<span class="font_mid color_gray">
												(${model.commentCnt })
											</span>
											<c:if test="${not empty model.share.url}">
												<a href="${model.share.url}" title="${model.share.label}" target="_blank">
													<img src="images/icons/shape_move_forwards.png" align="absmiddle" alt="在新窗口打开分享链接" title="在新窗口打开分享链接" />
												</a>
											</c:if>
										</h3>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<div id="share_all" class="mid_block tabswrap">
						<c:if test="${not empty allShareList}">
							<ul class="artList">
								<c:forEach items="${allShareList}" var="model">
									<li class="clearfix">
										<h3 class="fleft" style="line-height: 20px">
											<strong class="orangelink color_orange">
												<a href="userpage/<com:url value="${model.share.username}"/>">${model.share.username}</a>:
											</strong>
											<span class="graylink">
												<a href="share/comment/${model.share.id}" title="${model.share.label}">
													<g:limit value="${model.share.label}" maxlength="50" />
												</a>&nbsp;
											</span>
											<span class="font_mid color_gray">
												(${model.commentCnt })
											</span>
											<c:if test="${not empty model.share.url}">
												<a href="${model.share.url}" title="${model.share.label}" target="_blank">
													<img src="images/icons/shape_move_forwards.png" align="absmiddle" alt="在新窗口打开分享链接" title="在新窗口打开分享链接" />
												</a>
											</c:if>
										</h3>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
				</div>
			</div>
			<div id="area_right">
				<c:if test="${not empty allCommentList}">
					<div class="state clearfix" id="globalCommentList">
						<div class="titles">
							最新评论
						</div>
						<ul>
							<c:forEach items="${allCommentList}" var="model">
								<li class="tlist_wrap clearfix">
									<div class="tlist_icon icons_newticket"></div>
									<div class="tlist_text">
										<a href="share/comment/${model.share.id}">
											<com:limit maxlength="13" value="${model.body}" />
										</a>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<jsp:include page="../right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>