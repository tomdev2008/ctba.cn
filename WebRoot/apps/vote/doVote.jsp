<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>${vote.question }&nbsp;-&nbsp;参与投票</title>
		<script type="text/javascript">
		//=====================
		//validate the form
		//=====================
		function checkForm() {
			var inputs = document.getElementsByTagName("input");
			var cnt = 0;
			var maxCnt = ${vote.maxNum};
			for(var i =0;i<inputs.length;i++){
				input = inputs[i];
				if(input.id.indexOf("answer_")>=0){
					if(input.checked==true)
					cnt++;
				}
			}
			if(cnt<1){
				J2bbCommon.errorWithElement("errorMsg","请选择投票选项");
				return false;
			}
			if(cnt>maxCnt){
				J2bbCommon.errorWithElement("errorMsg","您选择了"+cnt+"项,不能选择超过"+maxCnt+"项");
				return false;
			}
			$("#submitButton").val("提交投票中，请稍候...");
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
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<logic:present name="board"><a href="b/${board.boardId }">${board.boardName }</a></logic:present><logic:notPresent name="board"><a href="vote.do?method=listVotes">扯谈投票</a></logic:notPresent>&nbsp;&gt;&nbsp;${vote.question }
					</div>
					<div class="fright">
						<div id="errorMsg"></div>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="_logo.jsp"></jsp:include>
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div id="vote_user" class="mid_block top_notice clearfix">
						<div class="fleft">
							<img src="<community:img value="${user.userFace}" type="default"/>" alt="" width="48" />
						</div>
						<div class="fleft vote_info">
							<h3><b>${vote.username }&nbsp;发起的投票</b></h3>
							<ul class="clearfix">
								<li>开始时间：<span class="color_orange font_mid"><community:date limit="11" start="0" value="${vote.updateTime }" /></span></li>
								<li>截止时间：<span class="color_orange font_mid">${vote.overTime }</span></li>
								<li>每人票数：<span class="color_orange font_mid">${vote.maxNum }</span></li>
								<%--投票选项:${answersCnt }<br/>--%>
							</ul>
						</div>
					</div>
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="current tlink">当前投票</li><li class="normal tlink"><a href="vote.do?method=viewVote&vid=${vote.id }">查看投票结果</a></li><li class="normal tlink"><a href="vote.do?method=listVotes&bid=${board.boardId }">本版其他投票</a></li>
						</ul>
					</div>
					<div id="vote_info" class="mid_block clearfix">
						<div id="vote_title" class="center">
							<h2 class="color_orange"><b><img src="images/icons/chart_bar.png" align="absmiddle" />&nbsp;${vote.question }</b></h2>
						</div>
						<div id="vote_intro">
							<community:html value="${vote.memo }" />
						</div>
						<div class="vote_wrapper clearfix">
							<form action="vote.do?method=saveAnswer" method="post" onsubmit="return checkForm();">
								<input type="hidden" name="vid" value="${vote.id }" />
								<ul class="vote_items">
									<logic:present name="answersList">
									<logic:iterate id="answer" indexId="index" name="answersList">
									<li>
										<div>
											<input name="answer_${index }" id="answer_${index }" type="checkbox" class="nborder" />&nbsp;${answer }
										</div>
										<div class="vote_bar">
											<img src="images/_vote/${(index+1)%6+1 }.gif" />
										</div>
									</li>
									</logic:iterate>
									</logic:present>
									<logic:equal value="1" name="vote" property="canBeOther">
									其他意见：
									<input type="text" name="vaOthereply_content" id="vaOthereply_content" class="formInput" size="35" />
									</logic:equal>
									<li class="vote_btn">
										<input type="submit" name="submitButton" id="submitButton" value="投票(${voteCnt })" class="input_btn" />
									</li>
								</ul>
							</form>
						</div>
							<%--<img src="images/signature.gif" alt="我的签名档..." />
							<br />
							<community:ubbDecode value="${user.userQMD }" />--%>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp" />
	</body>
</html>