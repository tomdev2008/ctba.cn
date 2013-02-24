<html>
<head>
<base href="http://www.ctba.cn/">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
您好，亲爱的 ${user.username}，本邮件由扯谈社 (CTBA) 为您发送！
<br/>
如果不能正常浏览此邮件，请点击 <a href="http://www.ctba.cn">这里</a>
<br/>
<ul>
  <#list timelineList as model>
<li>${model.eventStr }&nbsp;&nbsp;<span class="small_gray">${model.event.updateTime }</span><li>
  </#list>
</ul>
以上内容由系统自动发送，请勿回复  --${sendDate}
<br/>
不想收到系统邮件？点击 <a href='http://www.ctba.cn/setting'>这里</a> 修改您的邮件设置
</body>
</html>