<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title>${topic.topicTitle }&nbsp;-&nbsp;${board.boardName}&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			//added by mockee 090114
			function maxWidth(){
				var maxWidth = parseInt($('#dc img').css('max-width'));
				$('#dc img').each(function(){
					if ($(this).width() > maxWidth)
						$(this).width(maxWidth);
				});
			}
			if (window.attachEvent) window.attachEvent("onload", maxWidth);

			var userLogined=${logined };
			//==============
			//验证回复表单
			//==============
			function checkTopicForm(){
				var content = $("#topicContent").val();
				if(content.length>15000||content.trim().length<1){
					J2bbCommon.errorWithElement("errorMsg","<bean:message key="page.board.topic.view.error.reply"/>");
					return false;
				}
				$("#submitRe").attr("disabled","disabled");
				$("#submitRe").val("<bean:message key="page.common.button.submitting"/>");
				return true;
			}

			//==============
			// 回复某楼的用户
			//==============
			function re(index,username){
				$("#para_reply_to").val(username);
				$("#topicContent").val("@"+username+" "+index+"#\n");
				$("#topicContent").focus();
			}
			//==============
			// ie6 实现 css2 伪类
			//==============
			function hover() {
				$("ul.opt2 li").mouseover(function(){
					$(this).addClass("hover");
				}).mouseout(function(){
					$(this).removeClass("hover");
				});
			}
			if (window.attachEvent) window.attachEvent("onload", hover);
		</script>
	</head>
	<body>
		<c:import url="./head.jsp"></c:import>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="board/${board.boardId }">${board.boardName}</a>&nbsp;&gt;&nbsp;正文
					</div>
					<div class="fright">
						<jsp:include page="../common/_share.jsp">
							<jsp:param name="share_type" value="0"/>
							<jsp:param name="share_id" value="${topic.topicId}"/>
							<jsp:param name="share_url" value="${basePath }topic/${topic.topicId}"/>
							<jsp:param name="share_title" value="${topic.topicTitle }"/>
							<jsp:param name="share_content" value=""/>
						</jsp:include>
					</div>
				</div>
				<jsp:include page="_boardInfo.jsp"></jsp:include>
				<div class="left_block clearfix">
					<div id="group_tab_wrap">
						<ul id="tabs" class="graylink">
							<li class="current"><bean:message key="page.board.tab.currentTopic"/></li><li class="normal" id="t_content"><a href="board/${board.boardId }"><bean:message key="page.board.tab.topicList"/></a></li><li class="normal" id="t_content_hot"><a href="b.action?type=prime&bid=${board.boardId }"><bean:message key="page.board.tab.primeList"/></a></li>
						</ul>
						<div class="lt_post graylink">
							<a href="newtopic/${board.boardId }"><bean:message key="page.board.tab.newTopic"/></a>
						</div>
						<div class="lt_post new graylink">
							<a href="javascript:window.scroll(0,document.body.scrollHeight)"><bean:message key="page.board.tab.replyTopic"/></a>
						</div>
					</div>
					<div id="uinfo">
						<div id="uhead">
							<img src="<com:img value='${author.userFace }' type='sized' width='80' />" alt="" width="80" class="group_border" />
						</div>
						<div id="udetail" class="linkblue">
							<a href="<ctba:wrapuser user="${author}" linkonly="true"/>" class="userName">${author.userName }</a>
							<br />
							<span class="color_orange"><com:ipData value="${topic.topicIP }" /></span>
							<br />
						</div>
					</div>
					<div id="topicstuff" class="font_small color_orange">
						${topic.topicReNum }&nbsp;Replies and ${topic.topicHits }&nbsp;hits&nbsp;<img src="images/icons/btn_default.gif" align="absmiddle" />
					</div>
					<div id="dw">
						<div id="dt">
							<div id="ctl"></div>
							<div id="tl"></div>
							<div id="ctr"></div>
						</div>
						<div id="la"></div>
						<div id="dt2" class="text_shadow">
							<h1><strong>${topic.topicTitle }</strong></h1>
						</div>
						<div id="dc" class="orangelink">
							${topicContent }
							<br />
							${topicAttach }
							<br />
							<img src="images/signature.gif"/>
							<br />
							${userQMD }
						</div>
						<div id="db">
							<div id="cbl"></div>
							<div id="bl"></div>
							<div id="cbr"></div>
						</div>
					</div>
					<div class="line_block right">
						<div id="topicdate" class="color_gray">
							<img src="images/icons/color_swatch.png" align="absmiddle" />&nbsp;<bean:message key="page.board.topic.createTime"/>&nbsp;<com:formatTime time="${topic.topicTime }"/>
						</div>
						<logic:notEmpty name="prevTopic">
							<img src="images/icons/pico_left.gif" align="absmiddle" />&nbsp;<a href="topic/${prevTopic.topicId }"><bean:message key="page.board.topic.prev"/></a>
						</logic:notEmpty>
						<span class="color_gray">&nbsp;|&nbsp;</span>
						<logic:notEmpty name="nextTopic">
							<a href="topic/${nextTopic.topicId }"><bean:message key="page.board.topic.next"/></a>&nbsp;<img src="images/icons/pico_right.gif" align="absmiddle" />
						</logic:notEmpty>
					</div>
					<style type="text/css">
						.line_block ul {
							margin: 5px 0
						}
						.line_block ul li {
							float: right;
							margin: 0 0 0 8px
						}
					</style>
					<div class="line_block clearfix">
						<ul class="clearfix">
							<li>
							</li>
							<li>
								<a href="javascript:window.scroll(0,0)" class="link_btn"><bean:message key="page.common.button.backToTop"/></a>
							</li>
							<li>
								<a href="feedback.action?method=form&type=topic&id=${topic.topicId }" class="link_btn">举报</a>
							</li>

							<c:if test="${manager }">
								<li>
								<a href='board.shtml?method=transeTopic&tid=${topic.topicId }' class="link_btn"><bean:message key="page.board.manage.translate"/></a>
								<li>
							</c:if>
							<c:if test="${ isAuthor or manager or __user_is_editor}">
								<li>
									<a href='topic.action?method=form&tid=${topic.topicId }' class="link_btn"><bean:message key="page.board.manage.edit"/></a>
								</li>
								<li>
									<logic:equal value="false" name="outOfDiff">
										<a href='javascript:void(0);' class="link_btn" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }topic.action?method=delete&tid=${topic.topicId }');return false;">删除</a>
									</logic:equal>
								</li>
							</c:if>
							<li>
								<a href="news.shtml?method=post&tid=${topic.topicId }" class="link_btn"><bean:message key="page.board.topic.toNews"/></a>
							</li>
						</ul>
					</div>
					<div class="adsense_w728 clear">
						<script type="text/javascript"><!--
							google_ad_client = "pub-3911382285138100";
							/* 728x90, at 09-2-21 */
							google_ad_slot = "7055898710";
							google_ad_width = 728;
							google_ad_height = 90;
							//-->
						</script>
						<script type="text/javascript"
								src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
						</script>
					</div>
				</div>
				<div class="left_block top_blank_wide orangelink">
					<pg:pager items="${count}" url="t.action" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
						<pg:param name="method" />
						<pg:param name="tid" />
						<pg:param name="bid" />
						<pg:index>
							<div class="pageindex">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
							</div>
						</pg:index>
						<logic:notEmpty name="reTopics">
							<logic:iterate id="reTopic" name="reTopics" indexId="index">
								<div>
									<div class="reply_list clearfix">
										<div class="reply_user">
											<img src="<com:img value="${reTopic.author.userFace }" type="sized" width="32" />" alt="${reTopic.author.userName }" width="32" />
										</div>
										<div id="reply_info" class="linkblue_b">
											<a href="<ctba:wrapuser user="${reTopic.author}" linkonly="true"/>" class="userName">${reTopic.author.userName }</a>&nbsp;
											<span class="color_gray"><com:ipData value="${reTopic.topic.topicIP }" /></span>
										</div>
										<div class="reply_date">
											<div class="fleft"><span class="font_small color_orange">${start+index+1 }/${count}</span>&nbsp;&nbsp;<span class="font_small color_gray"><com:date value="${reTopic.topic.topicTime }" start="2" limit="16" />&nbsp;|</span></div>
											<ul class="opt2 fleft">
												<li>
													<span class="opt_arrow"></span>
													<ul>
														<c:if test="${not empty __sys_username}">
															<li><a href="javascript:re(${start+index+1 },'${reTopic.author.userName }');"><bean:message key="page.board.topic.replyComment"/></a></li>
														</c:if>
														<logic:equal value="true" name="reTopic" property="isAuthor">
															<li><a href='reply.action?method=delete&tid=${reTopic.topic.topicId }' onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','reply.action?method=delete&tid=${reTopic.topic.topicId }');return false;"><bean:message key="page.board.topic.deleteComment"/></a></li>
														</logic:equal>
														<logic:equal value="false" name="reTopic" property="isAuthor">
															<logic:equal value="true" name="manager">
																<li><a href='reply.action?method=delete&tid=${reTopic.topic.topicId }' onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','reply.action?method=delete&tid=${reTopic.topic.topicId }');return false;"><bean:message key="page.board.topic.deleteComment"/></a></li>
															</logic:equal>
														</logic:equal>
														<li><a href="javascript:window.scroll(0,0)"><bean:message key="page.board.topic.backToTopic"/></a></li>
													</ul>
												</li>
											</ul>
										</div>
										<div id="reply_content">
											${reTopic.topicContent }
											<br />
											${reTopic.topicAttach }
										</div>
									</div>
								</div>
							</logic:iterate>
						</logic:notEmpty>
						<pg:index>
							<div class="pageindex_bottom">
								<jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
							</div>
						</pg:index>
					</pg:pager>
					<jsp:include page="../_loginBlock.jsp"></jsp:include>
					<div class="line_block_b right darkgraylink">
						<a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop"/></a>&nbsp;|&nbsp;<a href="newtopic/${board.boardId }" name="gobt"><bean:message key="page.board.topic.post"/></a>&nbsp;|&nbsp;<span class="color_gray"><bean:message key="page.board.topic.replyCnt" arg0="${topic.topicReNum }"/></span>
					</div>
				</div>
				<logic:equal value="true" name="logined">
					<logic:equal value="true" name="topicCanPost">
						<div class="title_bar">
							Re:&nbsp;${topic.topicTitle }
						</div>
						<div class="left_block top_blank_wide radius_top_none">
							<div id="errorMsg"></div>
							<c:if test="${(topic.topicScore<=0) or (topic.topicScore< currScore)}">
								<div class="replyform">
									<form name="topicForm" id="topicForm" action="reply.action?method=save" method="post" enctype="multipart/form-data" onsubmit="return checkTopicForm();">
										<div class="replyitems">
											<ul>
												<li><com:ubb /></li>
												<li class="clearfix">
													<textarea name="topicContent" class="fleft topicContent" id="topicContent" style="width:554px;height:150px"></textarea>
													<c:if test="${(topic.topicScore >0) and (topic.topicScore <= currScore)}">
														<div class="fleft post_tip orangelink">
															楼主设置了：<br /><a href="go.jsp?key=faq-what-is-ctb-topic-trad" title="什么是收益值？" target="_blank">收益值</a><br />回复本帖需支付：<br /><span class="color_orange">${topic.topicScore }.00</span>&nbsp;<span class="font_small color_gray">CTB</span><br />您目前还有：<br /><a href="bank/" title="查看历史交易详情" target="_blank">${currScore }</a>&nbsp;<span class="font_small color_gray">CTB</span>
														</div>
													</c:if>
													<c:if test="${topic.topicScore > currScore}">
														<div class="fleft post_tip orangelink">
															楼主设置了：<br /><a href="go.jsp?key=faq-what-is-ctb-topic-trad" title="什么是收益值？" target="_blank">收益值</a><br />回复本帖需支付：<br /><span class="color_orange">${topic.topicScore }.00</span>&nbsp;<span class="font_small color_gray">CTB</span><br />您目前余额不足，无法回复:(
														</div>
													</c:if>
												</li>
												<li>
													<input type="file" name="topicAttach" size="50" class="formInput" />
													<input type="hidden" name="topicTitle" value="${topic.topicTitle }" />
													<input type="hidden" name="topicId" value="${topic.topicId }" />
													<input type="hidden" name="topicBoardId" value="${topic.topicBoardId }" />
													<input type="hidden" name="para_reply_to" id="para_reply_to" value="${author.userName }"/>
												</li>
												<li><input class="input_btn" name="submit" id="submitRe" type="submit" value="<bean:message key="page.board.topic.reply"/>" /></li>
											</ul>
										</div>
										<%--<div id="emotionsDiv"></div> --%>
									</form>
								</div>
							</c:if>
							<div class="line_blocks orangelink"><img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.board.hint"/></div>
						</div>
					</logic:equal>
				</logic:equal>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
	</body>
</html>