<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="./common/error_page.jsp"%>
<%@include file="./common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<c:import url="common/include.jsp"></c:import>
		<title><bean:message key="sys.name"/></title>
		<style type="text/css">
			.input_login {
				padding: 3px;
				width: 172px
			}
			ul.simple_login li.low {
				margin: 8px 0 2px 0
			}
			ul.simple_login li.high {
				margin: 8px 0 2px 0
			}
			ul.simple_login li.exclamation {
				padding: 5px 0 0 20px;
				margin: 13px 0 0 0;
				border-top: 1px solid #eee;
				background: url(theme/default/img/exclamation.gif) no-repeat 0 6px
			}
			ul.simple_login li.high input.input_btn {
				width: 180px;
				letter-spacing: 3px;
				font-weight: bold
			}
			input.cbox {
				border: none;
				vertical-align: middle
			}
			.welcome_area {
				height: 238px;
				height: 240px!important;
				overflow: hidden;
				background: #fff url(images/temp1.gif) no-repeat
			}
		</style>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper" class="clearfix">
			<div id="area_left">
				<div class="left_block welcome_area">
					&nbsp;
				</div>
			</div>
			<div id="area_right">
				<div class="state" style="background:#fff url(images/bg_2008.png) no-repeat">
					<ul class="simple_login">
						<li><h2><b>用户登录</b></h2></li>
						<li class="low">用户名&nbsp;或&nbsp;E-mail：</li>
						<li><input class="input_login" type="text" /></li>
						<li class="low">密&nbsp;&nbsp;&nbsp;码：</li>
						<li><input class="input_login" type="password" /></li>
						<li class="high">
							<label>
								<input class="cbox" type="checkbox" name="remember_user" />
								 下次自动登录
							</label>
						</li>
						<li class="high"><input class="input_btn" type="submit" value="立即登录" /></li>
						<li class="high exclamation orangelink">忘记密码？<a href="#"><u>点击这里</u></a></li>
					</ul>
				</div>
			</div>
		</div>
		<jsp:include page="bottom.jsp" flush="true" />
	</body>
</html>