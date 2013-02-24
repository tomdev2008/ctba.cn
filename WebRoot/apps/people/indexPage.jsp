<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>粉丝团&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
        </title>
    </head>
    <body>
        <jsp:include flush="true" page="./head.jsp"/>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;粉丝团
                    </div>
                    <div class="fright"></div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="../../_operationBlock.jsp" />
                    <jsp:include flush="true" page="../../_adBlockMini.jsp" />
                </div>
                <div id="mid_column" class="fright">
                    <jsp:include flush="true" page="./_info.jsp" />


                    <c:if test="${not empty __sys_username_focus}">
                        <ul id="tabs" class="graylink">
                            <li
                                class="current"
                                >
                                ${__sys_username_focus }的装备
                            </li>

                            <li
                                <c:if test="${param.currTab eq 'list' }">class="current"</c:if>
                                <c:if test="${not (param.currTab eq 'list') }">class="normal"</c:if>>
                                <c:if test="${param.currTab eq 'list' }">装备列表</c:if>
                                <c:if test="${not (param.currTab eq 'list') }">
                                    <a href="equipment.shtml?method=list">装备列表</a>
                                </c:if>
                            </li>
                            <li
                                class="normal" >
                                <a href="equipment.shtml?method=form">上传装备</a>
                            </li>
                        </ul>
                    </c:if>
                    <div class="mid_block">
                        <c:if test="${not empty hotModels}">
                            <div class="mid_block">
                                <pg:pager items="${count}" url="equipment.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                                    <pg:param name="method" />
                                    <pg:index>
                                        <div class="pageindex nborder">
                                            <jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
                                        </div>
                                    </pg:index><ul class="mid_block_list">
                                        <c:forEach items="${hotModels}" var="model" varStatus="status">
                                            <li>
                                                <div class="clearfix">
                                                    <div class="group_digg">
                                                        <div class="diggnum" id="group_digg_${model.equipment.id }">${model.equipment.voteScore }</div>
                                                        <span class="font_small color_gray">Pt</span>
                                                    </div>
                                                    <div class="mid_block_text clearfix">
                                                        <h2>
                                                            <strong><a href="equipment/${model.equipment.id }"  >${model.equipment.name }</a></strong>
                                                        </h2>
                                                        <p class="color_gray ">
                                                            <com:topic value="${model.equipment.discription }" />
                                                        </p>
                                                    </div>
                                                    <a href="equipment/${model.equipment.id }"  ><img src="<com:img value="${model.equipment.image }" type="default" />" width="80" align="right" style="margin-top:15px;padding:2px;border:1px solid #ddd;margin-right:8px" /></a>
                                                </div>
                                                <div class="line_block color_gray graylink clear clearfix">
                                                    <ul class="lo" style="float:right">
                                                        <li class="icons_user"><a href="u/${model.author.userId }">${model.equipment.username}</a>&nbsp; <a href="equipment.shtml?method=list&wrap-uid=${model.author.userId }"><span class="color_orange">&lt;只看${model.equipment.username}的&gt;</span></a>&nbsp;|&nbsp;</li>
                                                        <li class="icons_calendar_view_day"><community:formatTime time="${model.equipment.createTime }" />&nbsp;|&nbsp;</li>
                                                        <li class="icons_note">${model.equipment.hits }&nbsp;|&nbsp;</li>
                                                        <li class="icons_comment">${model.commentCnt}</li>
                                                    </ul>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <pg:index>
                                        <div class="pageindex nborder">
                                            <jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
                                        </div>
                                    </pg:index>
                                </pg:pager>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div id="area_right">

                <logic:notEmpty name="newModels">
                    <div class="state">
                        <div class="titles">
                            最新偶像
                        </div>
                        <ul class="hot_group clearfix" id="hot_group">
                            <logic:iterate id="model" name="newModels">
                                <li>
                                    <a class="stars_border" href="equipment/${model.id }">
                                        <img src="<community:img value="${model.image }" type="sized" width="48" />" width="48" height="48" /><span class="group_name">${model.name}</span>
                                    </a>
                                </li>
                            </logic:iterate>
                        </ul>
                    </div>
                </logic:notEmpty>
                <jsp:include flush="true" page="./_right.jsp"></jsp:include>
            </div>
        </div>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>