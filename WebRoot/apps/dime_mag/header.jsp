<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<div id="header" class="clearfix">
	<div id="top_ad">
		<img src="${pluginContext}/img/top_ad.png" width="900" height="90" alt="" />
	</div>
	<div id="logo" class="fleft">
		<h1>
			<a href="apps/dimemag">CTBA DIME</a>
		</h1>
	</div>
	<p class="fright quote">
		本网站图文均为《篮天下Dime》杂志所有，<a href="http://ctba.cn/">扯谈社（CTBA）</a>网站授权使用。
		<br />
		其他任何网站和平面媒体未经许可，不得转载、复制或以其他方式变相传播，违者负法律责任。
		<br />
		The Game. The Player. The Life. &nbsp;引领篮球生活杂志新潮流。
	</p>
	<div id="nav" class="fright">
		<ul>
			<li>
				<a href="apps/dimemag">首页</a> /
			</li>
			<li>
				<a href="/">扯谈社</a> /
			</li>
			<c:if test="${not empty __sys_username}">
			<li>
				<a href="userpage/">我的Dime</a> /
			</li>
			</c:if>
			<li>
				<a href="http://www.ctba.cn/g.action?method=list&t_index=1&tag=IMC6x%2FI=">球星</a> /
			</li>
			<li>
				<a href="http://ctba.cn/group/130">球鞋</a> /
			</li>
			<li>
				<a href="http://ctba.cn/group/139">街球</a>&nbsp;
			</li>
		</ul>
	</div>
	<div id="searchbar" class="clearfix">
		<ul id="feeds" class="fright">
			<li>
				<img src="${pluginContext}/img/feed-icon.gif" alt="rss" align="absmiddle" />
				&nbsp;
				<a href="rss.shtml?type=news">订阅DIME</a>
			</li>
		</ul>
		<form id="searchform" action="search.action" class="fleft" method="post">
			<input id="title" type="text" name="title" />
			<input id="searchsubmit" height="19" width="19" type="image" title="Search" alt="Search" src="${pluginContext}/img/search.gif" value="" />
		</form>
	</div>
</div>