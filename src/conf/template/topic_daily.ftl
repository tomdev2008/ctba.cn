您好，亲爱的 ${user.username}，最近社区热点由扯谈社 (CTBA) 为您发送！

如果不能正常浏览此邮件，请点击 <a href="http://www.ctba.cn">这里</a>

	
<#list mList as m>
[${m.type}]&nbsp;<a href="http://www.ctba.cn/${m.url }" title="${m.label}"><strong>${m.label}<strong></a>&nbsp; ${m.author.userName} 
</#list>

以上内容由系统自动发送，请勿回复  --${sendDate}

不想收到系统邮件？点击 <a href='http://www.ctba.cn/setting'>这里</a> 修改您的邮件设置
