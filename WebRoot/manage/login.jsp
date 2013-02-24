<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><bean:message key="sys.name" />管理登录</title>
<style type="text/css">
	body {
		padding: 0;
		margin: 0;
		background: url(../images/loginbg.gif);
		font: 12px Verdana, Arial, Helvetica, sans-serif
	}
	form, input {
		margin: 0;
		padding: 0
	}
	#ctlogin {
		margin: 180px auto 8px auto;
		width: 500px;
		height: auto;
		background: #fff;
		border: 5px solid #cfcfcf
	}
	.minput {
		width: 300px;
		color: #333;
		padding: 5px;
		font-family: Verdana, Arial, Helvetica, sans-serif;
		background: #fff;
		border: 1px solid #999
	}
	#ctlogintitle {
		font-size: 14px;
		margin: 24px auto 10px auto;
		color: #666;
		width: 460px;
		padding: 0 0 10px 0;
		border-bottom: 1px solid #eee
	}
	#ctl {
		margin: 0 0 15px 0
	}
	#loginbottom {
		width: 400px;
		clear: both;
		margin: 0 auto;
		font-size: 10px;
		text-align: center
	}
	.oranglink a, .oranglink a:hover {
		color: #ff7700;
		text-decoration: none
	}
	.oranglink a:hover {
		color: #fff;
		background: #ff7700
	}
</style>
</head>
<body>
<div id="ctlogin">
  <div id="ctlogintitle">CTBA┊管理登录 <img src="../images/cticon.png" alt="" align="texttop" /></div>
  <form action="../manage/manageLogin.do" method="post" id="Login" onsubmit="">
    <input type="hidden" value="<%=request.getParameter("target")%>" name="target" />
    <table width="100%" border="0" cellspacing="0" cellpadding="6" id="ctl">
      <tr>
        <td width="24%" align="right">Username: </td>
        <td width="76%"><input type="text" class="minput" id="username" name="username"
      onfocus="this.style.borderColor = '#ff7700'; this.style.backgroundColor = '#ffffff';" 
      onblur="this.style.borderColor = '#999999'; this.style.backgroundColor = '#f5f5f5';"
      maxlength="100" /></td>
      </tr>
      <tr>
        <td align="right">Password: </td>
        <td><input class="minput" id="password"
      onblur="this.style.borderColor = '#999999'; this.style.backgroundColor = '#f5f5f5';" 
      onfocus="this.style.borderColor = '#ff7700'; this.style.backgroundColor = '#ffffff';" 
      type="password" maxlength="32" name="password" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><input type="image" src="../images/login.gif" name="Submit" /></td>
      </tr>
    </table>
  </form>
</div>
<div id="loginbottom" class="oranglink">&copy;&nbsp;2008&nbsp;<a href="http://ctba.cn">CTBA.CN</a>&nbsp;(CTBA)&nbsp;All&nbsp;Rights&nbsp;Reserved.</div>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
<script type="text/javascript">
_uacct = "UA-568469-6";
urchinTracker();
</script>
</body>
</html>
