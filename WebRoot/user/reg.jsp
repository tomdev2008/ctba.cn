<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="page.user.reg.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@include file="../common/include.jsp"%>
		<script src="${context}/javascript/passwordStrength.js" type="text/javascript"></script>
		<script type="text/javascript">
			//==========================
			//check the username by ajax
			//==========================
			function postUsername(){
				//J2bbCommon.removeformError("usernameReg");
				var u = $('#usernameReg').val();
				var patrnCN=/[\u4e00-\u9fa5]/;
				var patrn=/[\u4e00-\u9fa5_a-zA-Z0-9]{2,}/;
				if (patrn.exec(u)&& J2bbCommon.strLen(u)<=14){
					$.post("userExt.shtml",
					{ method:"checkUsername",loadType:"ajax",username:""+u},
					function(data){
						if(data.indexOf("<bean:message key="page.user.reg.log.code.error" />")>=0){
							J2bbCommon.formError("usernameReg",data);
							return false;
						}else{
							J2bbCommon.formInfo("usernameReg",data);
						}
					}
				);
				}else{
					if(J2bbCommon.strLen(u)>0){
						J2bbCommon.formError("usernameReg","<bean:message key="page.user.reg.log.username" />","right");
					}
				}
			}
			//==========================
			//check the form email
			//==========================
			function checkEmail(){
				var u = $('#emailReg').val();
				var patrn=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
				if (!patrn.exec(u)){
					J2bbCommon.formError("emailReg","<bean:message key="page.user.reg.log.email" />");
					$('#emailReg').focus();
					return false;
				}
				return true;
			}
			//==========================
			//check the form username
			//==========================
			function checkUsername(){
				var u = $('#usernameReg').val();
				var patrnCN=/[\u4e00-\u9fa5]/;
				var patrn=/[\u4e00-\u9fa5_a-zA-Z0-9]{2,}/;
				if (!patrn.exec(u)||J2bbCommon.strLen(u)>14){
					J2bbCommon.formError("usernameReg","<bean:message key="page.user.reg.log.username" />");
					$('#usernameReg').focus();
					return false;
				}
				return true;
			}
			//==========================
			//check the password
			//==========================
			function checkPassword(){
				var p0 = $('#passwordReg').val();
				var p1 = $('#password1Reg').val();
				if(p0.length<6){
					J2bbCommon.formError("passwordReg","<bean:message key="page.user.reg.log.password" />");
					$('#passwordReg').focus();
					return false;
				}
				if(p0!=p1){
					J2bbCommon.formError("password1Reg","<bean:message key="page.user.reg.log.passwordAgain" />");
					$('#password1Reg').focus();
					return false;
				}
				return true;
			}
			//==========================
			//check form
			//==========================
			function checkForm(){
				J2bbCommon.removeformError("passwordReg");
				J2bbCommon.removeformError("emailReg");
				J2bbCommon.removeformError("password1Reg");
				J2bbCommon.removeformError("usernameReg");
				J2bbCommon.removeFormInfo("usernameReg");
				if(checkUsername()){
					if(checkEmail()){
						if(checkPassword()){
							$("#regButton").val("<bean:message key="page.user.reg.log.submit" />");
							$("#regButton").attr("disabled","disabled");
							return true;
						}
					}
				}
				return false;
			}
			$(function() {
				$("#usernameReg").focus();
				$('#passwordReg').keyup(function(){ $('#pw_strength_result').html(passwordStrength($('#passwordReg').val())) });
			});
		</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.user.reg.title" />
				</div>
				<div class="left_block top_blank_wide clearfix">
					<div class="title">
						<h1>&nbsp;&nbsp;<bean:message key="common.regist.new" /></h1>
					</div>
					<html:messages id="message" message="true">${message}</html:messages>
					<form action="userExt.do?method=reg" name="reg" id="reg" method="post" onsubmit="return checkForm();">
						<ul id="register_form">
							<li class="clearfix">
								<div class="fleft">
									<h2><bean:message key="page.user.reg.form.username"/></h2>
								</div>
								<div class="fright">
									<input name="username" id="usernameReg" class="formInput" type="text" size="30" onblur="postUsername();" />
									<p class="font_mid color_gray">
										<bean:message key="page.user.reg.form.username.hint"/>
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fleft">
									<h2><bean:message key="page.user.reg.form.email"/></h2>
								</div>
								<div class="fright">
									<input name="email" id="emailReg" class="formInput" type="text" size="30" maxlength="50" />
									<p class="font_mid color_gray">
										<bean:message key="page.user.reg.form.email.hint"/>
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fleft">
									<h2><bean:message key="page.user.reg.form.password"/></h2>
								</div>
								<div class="fright">
									<input name="password" id="passwordReg" class="formInput" type="password" size="30" maxlength="30" />
									<p class="font_mid color_gray">
										<bean:message key="page.user.reg.form.password.hint"/>
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fleft">
									<h2><bean:message key="page.user.reg.form.passwordAgain"/></h2>
								</div>
								<div class="fright">
									<input type="password" class="formInput" name="password1" id="password1Reg" size="30" maxlength="30" />
									<p class="font_mid color_gray">
										<bean:message key="page.user.reg.form.passwordAgain.hint" />
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fleft">&nbsp;</div>
								<div id="pw_strength_result" class="fright"></div>
							</li>
							<li class="clearfix">
								<div class="fleft">&nbsp;</div>
								<div class="fright">
									<input type="submit" name="reg" id="regButton" value="<bean:message key="page.user.reg.button.submit" />" class="input_btn" />
								</div>
							</li>
						</ul>
						<span id="sysMsg"></span>
					</form>
					<div class="line_blocks"><img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.user.reg.hint" /></div>
				</div>
			</div>
			<div id="area_right">
				<div style="font-size:13px" class="state orangelink">
					<bean:message key="page.user.reg.info" />
				</div>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>