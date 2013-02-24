<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.group.member.invite.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
	    <script src="${context}/javascript/userselector.js"
			language="JavaScript" type="text/javascript"></script>
		<link href="${context}/theme/jquery/groupselect.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript">
				function checkForm(){
					var userData = getActivedata();
	                if(typeof(userData)=="undefined"){
	                	//J2bbCommon.formError("al-groupselect-active","<bean:message key="page.group.member.invite.error.username" />");
	                	return false;
	                }else{
	                	//var userArray = userData.split(",");
		                // alert(userArray.length);
		                $("#userInSearch").val(userData);
		                //var name=$("#usernameInput").val();
						//if(name==''){
							//J2bbCommon.formError("usernameInput","<bean:message key="page.group.member.invite.error.username" />","bottom");
							//return false;
						//}
						$("#submitButton").val("<bean:message key="page.common.button.submitting" />");
						$("#submitButton").attr("disabled","disabled");
						return true;
		            }
					
				}
				var getActivedata; //取结果	
				var activeParameters = gs_getGSParameters();
				$(activeParameters).attr(
						{'frameId':'al-groupselect-active',
						 'maxcount':8,
						 'ajaxRequestUrl':{'group':'userSelector.action?method=group','item':'userSelector.action?method=items','active':'userSelector.action?method=suggest'}
				});
				$(document).ready(function(){  		
				  getActivedata = gs_groupselect(activeParameters);	
				});
			</script>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.group.member.invite.title" />
					</div>
					<div class="fright">
						${systemMsg }
						<c:if test="${true eq done}">
							<img src="images/icons/accept.png" align="absmiddle" />&nbsp;<bean:message key="page.group.member.invite.done" />
						</c:if>
						<c:if test="${true eq userInGroup}">
							<img src="images/icons/exclamation.png" align="absmiddle" />&nbsp;<bean:message key="page.group.member.invite.already" />[${userInGroupUser }]
						</c:if>
					</div>
				</div>
				<div class="left_block top_blank_wide">
					<form action="" method="post" onsubmit="return checkForm();">
						<!--input name="gid" value="${group.id }" type="hidden" /-->
						<div class="title">
							<h1>&nbsp;<img src="images/myfriends.png" align="absmiddle" />&nbsp;<bean:message key="page.group.member.invite.title" /></h1>
						</div>
						<table width="700" cellspacing="10" cellpadding="0">
							<tr>
								<td width="90" align="right">
									<h2><bean:message key="page.group.member.invite.form.username" /></h2>
								</td>
								<td>
								<div id="al-groupselect-active" style="height:30px;"></div>
                                        <!-- <input type="text" name="username" id="usernameInput" class="formInput" size="20" />-->
                                        <input type="hidden" name="username" id="userInSearch"/>
								</td>
							</tr>
							<tr>
								<td width="72" align="right">
									<h2><bean:message key="page.group.member.role" /></h2>
								</td>
								<td>
									<select  name="role" id="role" class="formInput">
										<option value="2"><bean:message key="page.group.member.role.manager" /></option>
										<option value="1" selected="selected"><bean:message key="page.group.member.role.normal" /></option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">
									<h2><bean:message key="page.group.member.group" /></h2>
								</td>
								<td>
								<com:select items="${manageGroupList}" name="gid" value="id" selected="${group.id}" label="name"/>
									<!-- input type="text" name="groupname" class="formInput" size="20" value="${group.name }" readonly-->
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<input type="submit" value="<bean:message key="page.common.button.submit" />" class="input_btn" name="submitButton" id="submitButton" />&nbsp;&nbsp;<input type="button" class="input_btn" name="ok" value="<bean:message key="page.common.button.back" />(${group.name })" onclick="location.href='group/${group.id }';" />
								</td>
							</tr>
						</table>
					</form>
					<div class="line_blocks">
						<img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.group.member.invite.hint" />
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>