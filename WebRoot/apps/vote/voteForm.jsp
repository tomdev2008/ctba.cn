<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>发起投票</title>
		<%@include file="../../common/include.jsp"%>
		<link rel="stylesheet" type="text/css" media="all" href="${context }/theme/calendar.css" />
		<script type="text/javascript">
			//check the form
			function checkForm(){
				J2bbCommon.removeformError("question");
				J2bbCommon.removeformError("answers");
				J2bbCommon.removeformError("memo");
				J2bbCommon.removeformError("maxNum");
				J2bbCommon.removeformError("overTime");

				var question = $("#question").val();
				var answers = $("#answers").val();
				var memo = $("#memo").val();
				var maxNum = $("#maxNum").val();
				var overTime = $("#overTime").val();
				var pattenDate=/[0-9]{4}\-[0-9]{1,2}\-[0-9]{1,2}/;
				var pattenNum=/[0-9]{1,2}/;
				if(question.length<1){
					J2bbCommon.formError("question","请填写投票主题");
					$('#question').focus();
					return false;
				}
				if(memo.length<1){
					J2bbCommon.formError("memo","请填写投票内容介绍");
					$('#memo').focus();
					return false;
				}
				if(answers.length<1){
					J2bbCommon.formError("answers","请填写投票选项");
					$('#answers').focus();
					return false;
				}
				if(!pattenNum.exec(maxNum)){
					J2bbCommon.formError("maxNum","请填写最多可选项");
					$('#maxNum').focus();
					return false;
				}
				if (!pattenDate.exec(overTime)){
					J2bbCommon.formError("overTime","请正确填写过期日期");
					$('#overTime').focus();
					return false;
				}
				$("#submitButton").val("提交中，请稍候...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="vote.do?method=listVotes&bid=${board.boardId }">扯谈投票</a>&nbsp;>&nbsp;发起投票
					</div>
					<div class="fright">
						<div id="errorMsg"></div>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="_logo.jsp"></jsp:include>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlockMini.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<jsp:include page="../../ad/_raywowAdBlock.jsp"></jsp:include>
					<div class="title">
						<h1>&nbsp;<img src="images/movies.png" align="absmiddle" />&nbsp;发起新投票</h1>
					</div>
					<form method="post" id="voteForm" action="vote.do?method=saveVote" onsubmit="return checkForm();">
						<table cellspacing="10" cellpadding="0">
							<tr>
								<td width="70" align="right"><h2>投票主题</h2></td>
								<td colspan="2">
									<input type="hidden" name="bid" value="${board.boardId }" />
									<input type="hidden" name="vid" value="${vote.id }" />
									<input type="text" name="question" id="question" value="${vote.question }" class="formInput" size="50" />
								</td>
							</tr>
							<tr>
								<td align="right"><h2>投票内容</h2></td>
								<td>
									<textarea name="memo" class="formTextarea" id="memo">${vote.memo }</textarea>
								</td>
							</tr>
							<tr>
								<td align="right"><h2>投票选项</h2></td>
								<td>
									<textarea name="answers" class="formTextarea" id="answers">${vote.answers }</textarea>
								</td>
							</tr>
							<tr>
								<td align="right"><h2>过期时间</h2></td>
								<td>
									<input type="text" name="overTime" id="overTime" value="${vote.overTime }" class="formInput" size="30" onclick="return showCalendar('overTime', 'y-mm-dd');" />
								</td>
							</tr>
							<tr>
								<td align="right"><h2>最多可选</h2></td>
								<td>
									<input type="text" name="maxNum" id="maxNum" value="${vote.maxNum }" class="formInput" size="10" />
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<%--						可以填写其他意见--%>
									<%--						<br />--%>
									<%--						<input name="canBeOther" id="canBeOther" type="checkbox"--%>
									<%--						<logic:present name="vote" >--%>
									<%--							<logic:equal value="1" name="vote" property="canBeOther" >checked</logic:equal>--%>
									<%--							</logic:present> />--%>
									<input type="submit" name="submit" id="submitButton" value="提交投票" class="input_btn" />
									<input type="reset" class="input_btn" value="重新填写" />
									<%--<logic:present name="vote">--%>
									<%--<a href="vote.do?method=doVote&vid=${vote.id }">参加投票</a>--%>
									<%--</logic:present>--%>
								</td>
							</tr>
						</table>
					</form>
					<div class="footer_tips_3col">
						<img src="images/icons/information.png" align="absmiddle" />&nbsp;多个投票项目请以回车分隔，最多不能超过15项:)
					</div>
				</div>
			</div>
			<div id="area_right">
				<c:if test="${not empty newestVoteList}">
					<div class="state clearfix">
						<div class="titles">
							热门投票
						</div>
						<ul>
							<c:forEach items="${hotVoteList}" var="model">
								<li class="tlist_wrap clearfix">
									<div class="tlist_icon icons_newticket"></div>
									<div class="tlist_text">
										<a href="vote/${model.id}">
											${model.question}
										</a>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<div class="state">
					<ul class="infobar">
						<li class="icons_feed">
							<a href="rss.shtml" target="_blank">订阅本站</a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
		<script type="text/javascript" src="${context}/javascript/calendar/calendar.js"></script>
		<script type="text/javascript" src="${context}/javascript/calendar/calendar-zh.js"></script>
		<script type="text/javascript" src="${context}/javascript/calendar/calendar-setup.js"></script>
	</body>
</html>