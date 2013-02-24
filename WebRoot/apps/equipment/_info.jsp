<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<%--<div class="mid_ad">
						<img src="images/ad/sneaker_ad.gif" width="590" height="73" />
					</div>
					--%>
					<%--
<div id="top_info_wrapper"  class="mid_block top_notice clearfix orangelink">
    <div id="top_info_pic" class="fleft">
        <img src="images/ctba-xiaonei-icon_70.png" height="25"/>
    </div>
    <div id="top_info_text" class="fleft">
        <h2 class="color_orange">
            <strong class="fleft">装备秀</strong>
        </h2>
        &nbsp;
        目前有${wareCntAll }件装备/${commentCntAll }条评论。
        &nbsp;&nbsp;
        <c:if test="${not empty __sys_username}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;<c:if
            test="${not empty userWareCnt and userWareCnt >0}">您已经上传了${userWareCnt }件装备。</c:if>
        <c:if test="${empty userWareCnt or userWareCnt ==0}">
            您还没有上传装备。
        </c:if>
        <span class="color_gray">
            | &nbsp;<a href="equipment.shtml?method=form" class="redlink">上传</a>&nbsp;/&nbsp;<a href="equipment.shtml?method=list" class="redlink">管理</a>&nbsp;/&nbsp;<a href="equipment/" class="redlink">首页</a>
        </span>]
        </c:if>
        <c:if test="${empty __sys_username}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;您还没有<a href="javascript:J2bbCommon.showLoginForm();">登录</a>或者<a href="register">注册</a>
            <span class="color_gray">
                | &nbsp;<a href="equipment/" class="redlink">首页</a>
            </span>]
        </c:if>
    </div>
</div>
 --%>