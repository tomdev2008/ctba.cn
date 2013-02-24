<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>转账&nbsp;-&nbsp;CT币中心&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script src="${context}/javascript/userselector.js" language="JavaScript" type="text/javascript"></script>
		<link href="${context}/theme/jquery/groupselect.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			//check the form
			function checkForm(){
				J2bbCommon.removeformError("value");
				J2bbCommon.removeformError("al-groupselect-active");
				var userData = getActivedata();
				if(typeof(userData)=="undefined"){
					//J2bbCommon.formError("al-groupselect-active","请正确填写收款人");
					return false;
				}else{
					$("#target").val(userData);
					var value = $("#value").val();
					if(isNaN(parseInt(value))){
						J2bbCommon.formError("value","请真确填写转账金额");
						return false;
					}
					if(parseInt(value)<=0){
						J2bbCommon.formError("value","转账金额必须大于0");
						return false;
					}
					if(parseInt(value)>${currScore}){
						J2bbCommon.formError("value","转账金额已经大于您所拥有的CTB数目");
						return false;
					}
					$("#submitMsg").val("<bean:message key="page.common.button.submitting"/>");
					$("#submitMsg").attr("disabled","disabled");
					return true;
				}
			}
			var getActivedata; //取结果
			var activeParameters = gs_getGSParameters();
			$(activeParameters).attr(
			{'frameId':'al-groupselect-active',
				'maxcount':1,
				'ajaxRequestUrl':{'group':'userSelector.action?method=group','item':'userSelector.action?method=items','active':'userSelector.action?method=suggest'}
			});
			$(document).ready(function(){
				getActivedata = gs_groupselect(activeParameters);
			});
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="/bank/" title="CT币中心">CT币中心</a>&nbsp;&gt;&nbsp;转账
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<jsp:include flush="true" page="_bankBlock.jsp" />
					<div class="mid_block lightgray_bg">
						<jsp:include page="_tab.jsp" flush="true">
							<jsp:param name="currTab" value="trans" />
						</jsp:include>
					</div>
					<div class="pt5">
						<div class="title pt5">转账给好友</div>
						<form name="topicForm" id="topicForm" action="bank.action?method=saveTrans" method="post" onsubmit="return checkForm();">
							<ul id="trans_form">
								<li class="clearfix">
									<div class="fleft">
										<h2>收款人</h2>
									</div>
									<div id="al-groupselect-active" class="fright"></div>
									<input type="hidden" name="target" id="target" value="${msgTo }"  />
								</li>
								<li class="clearfix">
									<div class="fleft">
										<h2>转账金额</h2>
									</div>
									<div class="fright">
										<input type="text" name="value" id="value" class="formInput" style="width:100px;" />
										<label class="color_gray">CTB</label>
									</div>
								</li>
								<li class="clearfix">
									<div class="fleft">
										<h2>简短附言</h2>
									</div>
									<div class="fright">
										<textarea type="text" name="hint" id="hint" class="formInput" style="width:300px;"></textarea>
										<label class="color_gray">此附言将发送给收款人，可为空</label>
									</div>
								</li>
								<li>
									<div class="fleft">&nbsp;</div>
									<div class="fright">
										<input type="submit" value="转帐" id="submitMsg" class="input_btn" />
									</div>
								</li>
							</ul>
						</form>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>