<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑用户CTB</title>
		<%@include file="../include.jsp"%>
		<script type="text/javascript">
			function up(){
				var plusValue= parseInt($("#plusValue").val());
				if(isNaN(plusValue)){
					alert("请输入数字");
					return;
				}
				var params;
				if(document.getElementById("sendNotice").checked){
					params =  { log:$("#hintContent").val(),method:"plus",value: plusValue,uid:${model.userId},loadType:"ajax",notice:"true"};
				}else{
					params =  {log:$("#hintContent").val(), method:"plus",value: plusValue,uid:${model.userId},loadType:"ajax"};
				}
				$.post("scoreManage.action",
					  params,
					  function(data){
					  	    	$("#scoreDiv").html(data);
					 });
			}
			function off(){
				var plusValue= -parseInt($("#plusValue").val());
				if(isNaN(plusValue)){
					alert("请输入数字");
					return;
				}
				var params;
				if(document.getElementById("sendNotice").checked){
					params =  { log:$("#hintContent").val(),method:"plus",value: plusValue,uid:${model.userId},loadType:"ajax",notice:"true"};
				}else{
					params =  {log:$("#hintContent").val(), method:"plus",value: plusValue,uid:${model.userId},loadType:"ajax"};
				}
				$.post("scoreManage.action",
					  params,
					  function(data){
					  	    	$("#scoreDiv").html(data);
					 });
			}
			function setScore(){
				var v = document.getElementById("scoreManager").options[document.getElementById("scoreManager").selectedIndex].value;
				$("#plusValue").val(v)
				var hint = document.getElementById("scoreManager").options[document.getElementById("scoreManager").selectedIndex].text;
				hint = "您因为" + hint + "而被奖励:"+v+"CTB";
				$("#hintContent").val(hint)
			}
			</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					编辑用户CTB &nbsp;&nbsp;[${model.userName }]&nbsp;&nbsp;
					<a class="button" href="manage.do?method=users">返回用户列表</a>
				</div>

				<table width="600" cellpadding="6" cellspacing="0">
					<tr>
						<td align=right width="20%">
						</td>
						<td align=left>
							<div id="scoreDiv" style="font-size: 30px;">
								${baseScore+model.userScore }
							</div>
						</td>
					</tr>
					<tr>
						<td align=right>
						</td>
						<td align=left>
							<input id="plusValue" class="formInput" type="text"
								style="width: 50px;" />
							&nbsp;&nbsp;
							<a href="javascript:up();"><img border="0"
									src="../images/up.png" height="26px;" />
							</a> &nbsp;&nbsp;
							<a href="javascript:off();"><img border="0"
									src="../images/off.png" height="26px;" />
							</a> &nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td align=right>
						</td>
						<td align=left>变更理由
						<br/>
							<select id="scoreManager" onchange="setScore();">
								<option value="2" hint="您因为举报不良信息被奖励:">
									举报不良信息
								</option>
								<option value="1" hint="您因为小组排名进入热榜被奖励:">
									小组排名进入热榜
								</option>
								<option value="5" hint="您因为文章被置顶/加精华被奖励:">
									文章被置顶/加精华
								</option>
								<option value="5" hint="您因为文章/日志被推荐首页被奖励:">
									文章/日志被推荐首页
								</option>
								<option value="5" hint="您因为发起的活动参加人数超过50人被奖励:">
									发起的活动参加人数超过50人
								</option>
								<option value="3" hint="您因为上传的装备被评上热榜前10位被奖励:">
									上传的装备被评上热榜前10位
								</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td align=right>
						</td>
						<td align=left>
						用户提示 &nbsp;&nbsp;同时发送系统提醒<input type="checkbox" id="sendNotice"/>
						<br/>
						<input id="hintContent" class="formInput" type="text"
								style="width: 400px;" />
						</td>
					</tr>
				</table>

			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
