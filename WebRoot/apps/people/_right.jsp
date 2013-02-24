<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<logic:notEmpty name="newEquipmentList">
    <div class="state">
        <div class="titles">
            我的粉丝
        </div>
        <ul class="hot_group clearfix" id="hot_group">
            <c:forEach items="${newEquipmentList}" var="model">
                <li>
                    <a class="stars_border" href="equipment/${model.id }"> <img
                            src="<community:img value="${model.image }" type="sized" width="48" />"
                            width="48" height="48" /><span class="group_name">${model.name}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</logic:notEmpty>

<c:if test="${not empty newCommentList}">
    <div class="state" id="epcomment">
        <div class="titles">
            最新评论
        </div>
        <ul>
            <c:forEach items="${newCommentList}" var="model">
                <li class="tlist_wrap clearfix">
                    <div class="tlist_icon icons_newticket"></div>
                    <div class="tlist_text">
                        <a href="equipment/${model.goodsWare.id}"><community:topic
                                value="${model.content}" />
                        </a>
                        <span class="normal_gray">(${model.username})</span>
                    </div>

                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<jsp:include flush="true" page="../../_commendBlock.jsp"></jsp:include>
<div class="adsenes_right_block">
    <script type="text/javascript"><!--
        google_ad_client = "pub-3911382285138100";
        /* 180x150, 创建于 09-2-23 */
        google_ad_slot = "3404358666";
        google_ad_width = 180;
        google_ad_height = 150;
        //-->
    </script>
    <script type="text/javascript"
            src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
</div>

<div class="state">
    <ul class="infobar">
        <li class="icons_feed">
            <a href="rss.shtml" target="_blank"><bean:message key="rss.feed" />
            </a>
        </li>
    </ul>
    <hr size="1" color="#eeeeee" />
    <jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
</div>