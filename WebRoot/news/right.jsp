<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="community"%>
<script type="text/javascript">
    function loadCats(){
        $("#catsDiv").html("<img src='${context}/images/ajax-loader.gif' />");
        $("#catsDiv").show();
        $.get("${context }/news.shtml",
        { method:"catList",loadType:"ajax"},
        function(data){
            $("#catsDiv").html(data);
        }
    );
    }
</script>
<div id="search">
    <jsp:include flush="true" page="../_searchBlock.jsp"></jsp:include>
</div>
<div class="state" id="catsDiv"></div>
<c:if test="${not empty refEntries}">
    <div class="state clearfix" id="refEntries">
        <div class="titles"><bean:message key="page.news.refNews"/></div>
        <ul>
            <c:forEach items="${refEntries}" var="model">
                <li class="tlist_wrap clearfix">
                    <div class="tlist_icon icons_newticket"></div>
                    <div class="tlist_text"><a href="${model.url}">${model.label}</a>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<c:if test="${not empty lastNewses}">
    <div class="state">
        <div class="titles"><bean:message key="page.news.newestNews"/></div>
        <ul class="digglist2">
            <c:forEach items="${lastNewses}" var="model" varStatus="index">
                <li>
                    <div class="diggmini">
                        <b>${model.hitGood}</b>
                    </div>
                    <div class="digglt2">
                        <a href="news/${model.id}">${model.title}</a>
                      <span class="font_small color_gray">
									<community:date value="${model.updateTime }" start="5" limit="16" />
								</span>
                    </div>
                    
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<c:if test="${not empty goodNewses}">
    <div class="state">
        <div class="titles"><bean:message key="page.news.hotNews"/></div>
        <ul class="digglist2">
            <c:forEach items="${goodNewses}" var="model" varStatus="index">
                <li>
                    <div class="diggmini">
                        <b>${model.hitGood}</b>
                    </div>
                    <div class="digglt2">
                        <a href="news/${model.id}">${model.title}</a>
                        <span class="font_small color_gray">
									<community:date value="${model.updateTime }" start="5" limit="16" />
								</span>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<c:if test="${not empty newComments}">
    <div class="state">
        <div class="titles"><bean:message key="page.news.newestComment"/></div>
        <div id="newComments">
            <ul class="infobar">
                <c:forEach items="${newComments}" var="model" varStatus="index">
                    <li><img src="images/icons/newticket.png" align="absmiddle" />&nbsp;<a href="news/${model.newsId }" class="stars_border"><community:limit maxlength="12" value="${model.content}" ignoreUbb="true"/></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</c:if>
<script type="text/javascript">
    loadCats();
</script>
<jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>
<div class="adsenes_right_block">
    <script type="text/javascript"><!--
        google_ad_client = "pub-3911382285138100";
        /* 180x150, at 09-2-23 */
        google_ad_slot = "3404358666";
        google_ad_width = 180;
        google_ad_height = 150;
        //-->
    </script>
    <script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
</div>
<div class="state">
    <ul class="infobar">
        <li class="icons_feed">
            <a href="rss.shtml" target="_blank"><bean:message key="rss.feed"/></a>
        </li>
    </ul>
    <hr size="1" color="#eeeeee" />
    <jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
</div>