<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="./common/taglibs.jsp"%>
<c:if test="${empty __sys_username}">
	<script type="text/javascript">
		//==============
		//验证底端用户登录表单
		//==============
		function checkUserLoginForm() {
			J2bbCommon.removeformError("username_topic");
			J2bbCommon.removeformError("password_topic");
			var username = $("#username_topic").val();
			var password = $("#password_topic").val();
			var patrn=/[\u4e00-\u9fa5_a-zA-Z0-9]{2,}/;
			if (!patrn.exec(username)){
				J2bbCommon.formError("username_topic","您的用户名不符合要求");
				return false;
			}
			patrn=/^(\w){6,20}$/;
			if (!patrn.exec(password)){
				J2bbCommon.formError("password_topic","请输入6至20位密码");
				return false;
			}
			return true;
		}
	</script>
	<div class="line_block_b color_gray">
		<img src="images/icons/shape_move_forwards.png" align="absmiddle" />&nbsp;有话想说？那就赶快登录写下来吧:)
	</div>
	<div class="line_block_b">
		<form id="login_vt" action="${context}/userLogin.action" method="post" onsubmit="return checkUserLoginForm();">
			<table width="500" cellpadding="0" cellspacing="3">
				<tr>
					<td align="right" width="25%">
						<h2>
							用&nbsp;户&nbsp;
						</h2>
					</td>
					<td width="45%">
						<input type="text" name="username" id="username_topic" class="formInput" size="30" />
					</td>
					<td width="30%"></td>
				</tr>
				<tr>
					<td align="right">
						<h2>
							密&nbsp;码&nbsp;
						</h2>
					</td>
					<td>
						<input type=password name="password" id="password_topic" class="formInput" size="30" />
					</td>
					<td>
						&nbsp;<input type="image" alt="登录" src="images/ctbtn.gif" class="nborder" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</c:if>