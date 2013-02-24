<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><bean:message key="page.user.resetPassword.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<%@include file="../common/include.jsp"%>
		<script type="text/javascript">
    	function checkForm(){
    		J2bbCommon.removeformError("newPassword");
			var p = $('#newPassword').val();
			if(p.length<6){
				J2bbCommon.formError("newPassword","<bean:message key="page.user.resetPassword.error.password" />");
				return false;
			}
			 $("#submitButton").val("<bean:message key="page.common.button.submitting" />");
	    	 $("#submitButton").attr("disabled","disabled");
			return true;
   		}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp" />
		<div id="wrapper">

			<div id="area_left">
			
			<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.user.resetPassword.title" />
					</div>
					<div class="fright">
						<html:messages id="msg" message="true">
						<div class="message_info">
						${msg}</div></html:messages>
					</div>
				</div>
				<div class="left_block top_blank_wide radius_top_none clearfix">
					<form action="userExt.shtml?method=reset" method="post"
						name="form1" id="form1" style="padding-left:20px"
						onsubmit="return checkForm();">
						<input type="hidden" name="username" value="${user.username }"/>
						<br />
						<div class="color_orange"><b>${user.username }</b></div>
						<br />
						<bean:message key="page.user.resetPassword.label" />
						<br />
						<input type="password" class="formInput" name="newPassword"
							id="newPassword"  
							maxlength="50" <c:if test="${__sys_action_done }">disabled="true"</c:if>/>
						<br />
						<p style="padding-top:20px">
							<input type="submit" name="submitButton" id="submitButton"	value="<bean:message key="page.user.resetPassword.button.submit" />" class="input_btn" <c:if test="${__sys_action_done }">disabled="true"</c:if>/>
								&nbsp;
							<input type="button" value="<bean:message key="page.user.resetPassword.button.back" />" class="input_btn" onclick="location.href='index.shtml';"/>
						</p>
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
