<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帖子打包发送</title>
<style type="text/css">
<!--
body {
	font-family: Verdana, Tahoma, "宋体";
	font-size: 14px;
        margin-top: 0px;
}
table {
	font-family: Verdana, Tahoma, "宋体";
	font-size: 14px;
}
-->
</style>
</head>

<body>
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td><strong>以下内容由${website}发送</strong></td>
  </tr>
  <#list nl as f>
  <tr>
    <td><strong>${f.title}</strong></td>
  </tr>
  <tr>
    <td>${f.detail}</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
  </tr>
  </#list>
</table>
</body>
</html>
