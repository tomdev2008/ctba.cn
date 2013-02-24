<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="community"%>
<script type="text/javascript" src="${context}/javascript/j2bb-3.8.js"></script>
<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
<logic:greaterThan value="0" name="__sys_noticeCnt">
<script type="text/javascript">
    var origin_title = document.title,
        flash_title_text = ['<bean:message key="page.bottom.notice.blink"/>  ','<bean:message key="page.bottom.notice.blink.empty"/>  '],
        flash_times = 0;
    setInterval('if(flash_times>=flash_title_text.length){flash_times=0};document.title=flash_title_text[flash_times++]+origin_title',600);
</script> 
</logic:greaterThan>
<logic:notEmpty name="request_search_result">
<div id="searchEngineHintDiv">
    <h2 class="clearfix">
        <span class="fleft">
            <bean:message key="page.bottom.search.finding"/>&nbsp;[<strong><community:limit maxlength="10" value="${request_search_keyword }"/></strong>]&nbsp;?
        </span>
        <span class="fright">
            <a href="javascript:void(0);" onclick="$('#searchEngineHintDiv').hide();">x</a>
        </span>
    </h2>
    <p>
        <bean:message key="page.bottom.search.hint"/>
    </p>
    <ul>
        <logic:iterate id="model" name="request_search_result">
        <li class="bullet_yellow"><a target="_balnk" href="${model.url }">${model.label }</a></li>
        </logic:iterate>
    </ul>
</div>
</logic:notEmpty>
<!--[if IE 6]>
<script src="http://letskillie6.googlecode.com/svn/trunk/letskillie6.zh_CN.pack.js"></script>
<![endif]-->
<script>var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));</script>
<script>var pageTracker = _gat._getTracker("UA-568469-6");pageTracker._initData();pageTracker._trackPageview();</script>
