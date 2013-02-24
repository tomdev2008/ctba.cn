<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<div id="top_info_wrapper"  class="mid_block top_notice clearfix orangelink">
    <div id="top_info_pic" class="fleft">
        <img src="images/ctba-xiaonei-icon_70.png" height="25"/>
    </div>
    <div id="top_info_text" class="fleft">
        <h2 class="color_orange">
            <strong class="fleft">粉丝团</strong>
        </h2>
        &nbsp;
        目前有${wareCntAll }个偶像/${commentCntAll }条评论。
        &nbsp;&nbsp;
        <c:if test="${not empty __sys_username}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;<c:if
            test="${not empty userWareCnt and userWareCnt >0}">您已经创建了${userWareCnt }个人物,是10个人物的粉丝。</c:if>
        <c:if test="${empty userWareCnt or userWareCnt ==0}">
            您还没有任何偶像。
        </c:if>
        <span class="color_gray">
            | &nbsp;<a href="people.action?method=form" class="redlink">创建</a>&nbsp;/&nbsp;<a href="people.action?method=list" class="redlink">管理</a>&nbsp;/&nbsp;<a href="people/" class="redlink">首页</a>
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
