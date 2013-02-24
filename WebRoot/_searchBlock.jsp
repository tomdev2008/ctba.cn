<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="community"%>
<form action="search.action" method="post">
	<select name="searchType" style="padding: 1px 0 2px 0">
		<option value="1">
            <bean:message key="page.search.bbs"/>
		</option>
		<option value="2">
            <bean:message key="page.search.blog"/>
		</option>
		<option value="3">
            <bean:message key="page.search.news"/>
		</option>
		<option value="4">
            <bean:message key="page.search.group"/>
		</option>
		<option value="5">
            <bean:message key="page.search.subject"/>
		</option>
	</select>
	<input class="search_input" name="title" style="width: 110px" />
</form>
<%--
<form method="get" action="http://www.google.cn/custom" target="google_window">
	<input type="hidden" name="domains" value="www.ctba.cn" />
	<label for="sbi" style="display: none">输入您的搜索字词</label>
	<input type="text" name="q" size="31" maxlength="255" value="" id="sbi" class="search_input" />
	<input type="submit" name="sa" value="搜索" id="sbb" class="input_btn" />
	<label for="sbb" style="display: none">提交搜索表单</label>
	<div style="display:none">
	<input type="radio" name="sitesearch" value="" id="ss0" class="nborder" /><label for="ss0" title="搜索网络">互联网</label>
	<input type="radio" name="sitesearch" value="www.ctba.cn" checked id="ss1" class="nborder" /><label for="ss1" title="搜索 www.ctba.cn">扯谈社</label>
	</div>
	<input type="hidden" name="client" value="pub-3911382285138100" />
	<input type="hidden" name="forid" value="1" />
	<input type="hidden" name="ie" value="UTF-8" />
	<input type="hidden" name="oe" value="UTF-8" />
	<input type="hidden" name="safe" value="active" />
	<input type="hidden" name="cof" value="GALT:#ff7700;GL:1;DIV:#ffffff;VLC:663399;AH:center;BGC:FFFFFF;LBGC:ff7700;ALC:0466AC;LC:0466AC;T:000000;GFNT:aaaaaa;GIMP:aaaaaa;LH:38;LW:225;L:http://www.ctba.cn/images/ctlogobeta1.png;S:http://www.ctba.cn;FORID:1" />
	<input type="hidden" name="hl" value="zh-CN" />
</form>--%>