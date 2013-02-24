<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.invite.title" />&nbsp;-&nbsp;<bean:message key="sys.name" />
		</title>
		<script type="text/javascript">
			function checkForm(){
				J2bbCommon.removeformError("friends");
				var friend = $("#friends").val();
				if(friend.length<1){
					J2bbCommon.formError("friends","<bean:message key="page.invite.form.error.email" />","bottom");
					$("#friend").focus();
					return false;
				}
				$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
		</script>

		<script type="text/javascript">
			function checkMsnForm(){
				J2bbCommon.removeformError("msnUsername");
				J2bbCommon.removeformError("msnPassword");
				var msnUsername = $("#msnUsername").val();
				var msnPassword = $("#msnPassword").val();
				if(msnUsername.length<1){
					J2bbCommon.formError("msnUsername","<bean:message key="page.invite.form.error.msn" />");
					$("#msnUsername").focus();
					return false;
				}
				if(msnPassword.length<1){
					J2bbCommon.formError("msnPassword","<bean:message key="page.invite.form.error.password" />");
					$("#msnPassword").focus();
					return false;
				}
				$("#msnButton").val("<bean:message key="page.common.button.submitting" />");
				$("#msnButton").attr("disabled","disabled");
				return true;
			}

			function checkGtalkForm(){
				J2bbCommon.removeformError("gtalkUsername");
				J2bbCommon.removeformError("gtalkPassword");
				var gtalkUsername = $("#gtalkUsername").val();
				var gtalkPassword = $("#gtalkPassword").val();
				if(gtalkUsername.length<1){
					J2bbCommon.formError("gtalkUsername","<bean:message key="page.invite.form.error.gtalk" />");
					$("#gtalkUsername").focus();
					return false;
				}
				if(gtalkPassword.length<1){
					J2bbCommon.formError("gtalkPassword","<bean:message key="page.invite.form.error.password" />");
					$("#gtalkPassword").focus();
					return false;
				}
				$("#gtalkButton").val("<bean:message key="page.common.button.submitting" />");
				$("#gtalkButton").attr("disabled","disabled");
				return true;
			}

			function checkEmailForm(){
				J2bbCommon.removeformError("emailUsername");
				J2bbCommon.removeformError("emailPassword");
				var qqUsername = $("#emailUsername").val();
				var qqPassword = $("#emailPassword").val();
				//var pattenQQ=/[0-9]{3,12}/;
				//if(!pattenQQ.exec(qqUsername)){
				if(qqUsername.length<1){
					J2bbCommon.formError("emailUsername","<bean:message key="page.invite.form.error.username" />");
					$("#emailUsername").focus();
					return false;
				}
				if(qqPassword.length<1){
					J2bbCommon.formError("emailPassword","<bean:message key="page.invite.form.error.password" />");
					$("#emailPassword").focus();
					return false;
				}
				$("#emailButton").val("<bean:message key="page.common.button.submitting" />");
				$("#emailButton").attr("disabled","disabled");
				return true;
			}
			$(function(){
				$("#tabs_gray li a").click(function(){
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
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.invite.title" />
					</div>
					<div class="fright">
						<c:if test="${not empty systemError}">
							<div class="message_info">
								${systemError }
							</div>
						</c:if>
						<c:if test="${(not empty addedCnt) and (addedCnt>0)}">
						<div class="message_info"><bean:message key="contact.added" arg0="${addedCnt}"/></div>
						</c:if>
						<c:if test="${(not empty addedCnt) and (addedCnt==0)}">
						<div class="message_info"><bean:message key="page.invite.info.noContactAdded" /></div>
						</c:if>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../_operationBlock.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block top_notice friend orangelink clearfix">
						<h2 class="color_orange"><strong><bean:message key="page.invite.title" /></strong></h2>
                        <bean:message key="page.invite.description" />
					</div>
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li <c:if test="${(empty addedCnt)}">class="current"</c:if> <c:if test="${(not empty addedCnt)}">class="normal"</c:if>><a name="invite_im">即时通讯工具查找</a></li>
							<li class="normal"><a name="invite_contact">邮件联系人查找</a></li>
							<li <c:if test="${(not empty addedCnt)}">class="current"</c:if> <c:if test="${(empty addedCnt)}">class="normal"</c:if>><a name="invite_email">发送邮件邀请</a></li>
						</ul>
					</div>
					<div id="invite_im" class="tabswrap clearfix <c:if test="${(not empty addedCnt)}">hide</c:if>">
						<form name="inviteForm" action="c.action?method=gtalk" method="post" onsubmit="return checkGtalkForm();">
							<table width="80%" cellspacing="10" cellpadding="0" class="top_blank_wide">
								<tr>
									<td width="120" align="right">
										<h2>
										<bean:message key="page.invite.form.username" />
										</h2>
									</td>
									<td width="200">
										<input name="gtalkUsername" class="formInput" id="gtalkUsername" size="25" />
									</td>
									<td rowspan="3">
										<img src="images/talk_logo.gif" />
									</td>
								</tr>
								<tr>
									<td align="right">
										<h2>
										<bean:message key="page.invite.form.password" />
										</h2>
									</td>
									<td>
										<input name="gtalkPassword" class="formInput" id="gtalkPassword" type="password" size="25" />
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="submit" class="input_btn" name="submitButton" id="gtalkButton" value="找&nbsp;Gtalk&nbsp;上的朋友" size="30" />
									</td>
								</tr>
							</table>
						</form>
						<form name="inviteForm" action="c.action?method=msn" method="post" onsubmit="return checkMsnForm();">
							<table width="80%" cellspacing="10" cellpadding="0">
								<tr>
									<td width="120" align="right">
										<h2>
											<bean:message key="page.invite.form.username" />
										</h2>
									</td>
									<td width="200">
										<input name="msnUsername" class="formInput" id="msnUsername" size="25" />
									</td>
									<td rowspan="3">
										<img src="images/msn_logo.gif" />
									</td>
								</tr>
								<tr>
									<td align="right">
										<h2>
											<bean:message key="page.invite.form.password" />
										
										</h2>
									</td>
									<td>
										<input name="msnPassword" class="formInput" id="msnPassword" type="password" size="25" />
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="submit" class="input_btn" name="submitButton" id="msnButton" value="找&nbsp;MSN&nbsp;上的朋友" />
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div id="invite_contact" class="tabswrap clearfix hide">
						<form name="inviteForm" action="c.action?method=email" method="post" onsubmit="return checkEmailForm();">
							<table width="80%" cellspacing="10" cellpadding="0" class="top_blank_wide">
								<tr>
									<td width="25%" align="right">
										<h2>
											<bean:message key="page.invite.form.email" />
										
										</h2>
									</td>
									<td width="60%">
										<input name="emailUsername" class="formInput" id="emailUsername" style="width: 80px" />
										<select name="emailHost" style="padding:1px 1px 2px 1px;width:85px">
											<option value="@gmail.com">
												@gmail.com
											</option>
											<option value="@yahoo.cn">
												@yahoo.cn
											</option>
											<option value="@yahoo.com">
												@yahoo.com
											</option>
											<option value="@hotmail.com">
												@hotmail.com
											</option>
										</select>
									</td>
									<td rowspan="3">
										<img src="images/reclaimyourinbox_small.png" />
									</td>
								</tr>
								<tr>
									<td align="right">
										<h2>
											<bean:message key="page.invite.form.password" />
										
										</h2>
									</td>
									<td>
										<input name="emailPassword" class="formInput" id="emailPassword" type="password" size="25" />
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="submit" class="input_btn" name="submitButton" id="emailButton" value="找朋友" />
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div id="invite_email" class="tabswrap clearfix <c:if test="${(empty addedCnt)}">hide</c:if>">
						<form name="inviteForm" action="user.shtml?method=invite" method="post" onsubmit="return checkForm();">
							<table width="90%" cellspacing="10" cellpadding="0" class="top_blank_wide">
								<tr>
									<td align="right">
										<bean:message key="page.invite.hint.multiemail" />
									</td>
								</tr>
								<tr>
									<td align="right">
										<textarea name="friends" class="topicContent" style="width: 400px; height: 200px;" id="friends">${contactList }</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
									
										<input type="submit" class="input_btn" name="submitButton" id="submitButton" value="<bean:message key="page.invite.form.submit" />" />
									</td>
									<td></td>
								</tr>
							</table>
						</form>
					</div>
					<div class="footer_tips_3col">
						<img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.invite.hint" />
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"></jsp:include>
	</body>
</html>
