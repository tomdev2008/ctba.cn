<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>
         装备列表
        &nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;>&nbsp;装备列表
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="_userBar.jsp" />
                    <jsp:include page="../../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <jsp:include flush="true" page="./_info.jsp" />
                    <jsp:include page="../../ad/_raywowAdBlock.jsp" flush="true"/>
                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="list"/>
                    </jsp:include>
                    <div class="mid_block">
                        <c:if test="${not empty models}">
                            <pg:pager items="${count}" url="equipment.shtml" index="center"
                                      maxPageItems="30" maxIndexPages="6"
                                      isOffset="<%=false%>"
                                      export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                      scope="request">
                                <pg:param name="method" />
                                <pg:param name="wrap-uid" />
                                <pg:index><br class="clear" />
                                    <div class="pageindex nborder">
                                        <jsp:include flush="true" page="../../common/jsptags/simple.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                                <table width="100%" cellspacing="0" cellpadding="0"	id="listable">
                                    <c:forEach items="${models}" var="model" varStatus="status">
                                        <tr
                                            <c:choose>
                                                <c:when test="${status.count%2==0}">
                                                    class="alternative "
                                                </c:when>
                                            </c:choose>>
                                            <td width="62%">
                                                <img height="16" width="16" src="<community:img value="${model.image }" type="mini" />"/>
                                                &nbsp;<a
                                                    href="equipment/${model.id }">
                                                <community:limit maxlength="30" value="${model.name }" /> </a>
                                            </td>
                                            <td width="10%">
                                                <span class="font_small color_orange">${model.username} </span>
                                            </td>
                                            <td width="15%">
                                                <span class="font_small color_gray"> <g:date limit="11" start="0"
                                                                                             value="${model.createTime }" /> </span>
                                            </td>
                                            <td width="18%">
                                                <c:if test="${model.username eq __sys_username}">
                                                <a href="equipment.shtml?method=form&wid=${model.id }">编辑</a>&nbsp;<a href="equipment.shtml?method=delete&wid=${model.id }">删除</a>
                                           </c:if> </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </pg:pager>
                        </c:if>
                </div></div>
            </div><div id="area_right">
                <jsp:include page="_right.jsp" flush="true" />
        </div></div>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>