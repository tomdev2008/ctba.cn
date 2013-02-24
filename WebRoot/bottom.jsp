<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="community"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<div id="footer">
    <div id="binner">
        <div class="fleft">
            <img src="images/ctmini.png" alt="CTBA" title="CTBA" />
        </div>
        <div class="fright">
            <ul class="clearfix">
                <li>
                    <a href="aboutus">关于我们</a>
                </li>
                <li class="color_orange font_mid">|</li>
                <li>
                    <a href="partners">合作伙伴</a>
                </li>
                <li class="color_orange font_mid">|</li>
                <li>
                    <a href="media">媒体活动</a>
                </li>
                <li class="color_orange font_mid">|</li>
                <li>
                    <a href="privacy">隐私保护</a>
                </li>
                <li class="color_orange font_mid">|</li>
                <li>
                    <a href="link">友情链接</a>
                </li>
                <li class="color_orange font_mid">|</li>
                <li>
                    <a href="board/7">社员反馈</a>
                </li>
                <li class="color_orange font_mid">|</li>
                <li>
                    <a href="contact">联系我们</a>
                </li>
            </ul>
            <div id="copyright" class="right font_mid color_gray graylink2">
                ©2002-2010&nbsp;<a href="http://www.ctba.cn">CTBA.CN</a>,&nbsp;All rights reserved.&nbsp;<a href="http://www.miibeian.gov.cn/">京ICP备05063221号</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${context}/javascript/j2bb-3.8.js?ct=090311"></script>
<script type='text/javascript'>
    $(function() {
        $('.boxy').boxy();
    });
</script>
<%
//方便本地调试
if (request.getServerName().indexOf("test.ctba.cn") < 0) {%>
<script type="text/javascript">
    /* title flash */
    var origin_title = document.title,
        flash_title_text = ['<bean:message key="page.bottom.notice.blink"/>  ','<bean:message key="page.bottom.notice.blink.empty"/>  '],
        flash_times = 0;
    <logic:greaterThan value="0" name="__sys_noticeCnt">
    setInterval('if(flash_times>=flash_title_text.length){flash_times=0};document.title=flash_title_text[flash_times++]+origin_title',600);
    </logic:greaterThan>

    /* google adsense */
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
    var pageTracker = _gat._getTracker("UA-568469-6");
    pageTracker._initData();
    pageTracker._trackPageview();
</script>
<%}%>

<script type="text/javascript">
    
</script> 
<logic:notEmpty name="request_search_result">
    <div id="searchEngineHintDiv">
        <h2 class="clearfix">
            <span class="fleft">
				您在找&nbsp;[<strong><community:limit maxlength="10" value="${request_search_keyword }"/></strong>]&nbsp;吗?
            </span>
            <span class="fright">
                <a href="javascript:void(0);" onclick="$('#searchEngineHintDiv').hide();">x</a>
            </span>
        </h2>
        <p>
			下面的信息可能对您有帮助&nbsp;|&nbsp;<a href="search.action" class="ornagelink ">找更多>></a>
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
<%--加入雅虎的统计--%>
<%--<script type="text/javascript" src="http://js.tongji.cn.yahoo.com/686634/ystat.js"></script><noscript><a href="http://tongji.cn.yahoo.com"><img src="http://img.tongji.cn.yahoo.com/686634/ystat.gif"/></a></noscript>--%>
