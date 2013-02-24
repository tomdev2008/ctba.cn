<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="page.user.forgetPassword.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@include file="../common/include.jsp"%>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp" />
		<script type="text/javascript">
	    	function checkForm(){
	    		J2bbCommon.removeformError("usernameForget");
				var u = $('#usernameForget').val();
				var done =J2bbCommonValidator.isWordsIncludingChinese(u);
				if (! done){
					J2bbCommon.formError("usernameForget","<bean:message key="page.user.forgetPassword.error.username" />","bottom");
					$('#usernameForget').focus();
					return false;
				}
				 $("#submitButton").val("<bean:message key="page.common.button.submitting" />");
		    	 $("#submitButton").attr("disabled","disabled");
				return true;
	   		}
		</script>

		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.user.forgetPassword.title" />
					</div>
					<div class="fright">
						<html:messages id="message" message="true">
							<div class="message_info">
								${message }
							</div>
						</html:messages>
					</div>
				</div>
				<div class="left_block top_blank_wide radius_top_none clearfix">
					<form action="userExt.shtml?method=forget" method="post" name="form1"
						id="form1" style="padding-left:20px"
						onsubmit="return checkForm();">
						<p>
							<bean:message key="page.user.forgetPassword.form.username" />&nbsp;&nbsp;
							<input type="text" class="formInput" name="username"
								id="usernameForget" maxlength="20" 
								<c:if test="${__sys_action_done }">disabled="true"</c:if>
								/>&nbsp;&nbsp;&nbsp;
							<input type="submit" name="submitButton" id="submitButton"
								value="<bean:message key="page.common.button.submit" />" class="input_btn" <c:if test="${__sys_action_done }">disabled="true"</c:if>/>
								&nbsp;
								<input type="button" 
								value="<bean:message key="page.common.button.back" />" class="input_btn" onclick="history.back();"/>
								
						</p>
						<br />
						<br />
					</form>
				</div>
			</div>
			<div id="area_right">
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp" />
	</body>
</html>
