#not in use
<div class="titles">本版最受关注</div>
<div id="hot">
	<ul class="infobar">
		<#list models as model>
		<li><img src="images/icons/newticket.png" align="absmiddle" />&nbsp;<a href="t_${model.topicId}">${model.topic.topicTitle}</a></li>
		</#list>
	</ul>
</div>