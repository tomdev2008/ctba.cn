<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.group.join" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.group.join" />
                </div>
                <div class="left_block">
                    <c:if test="${true eq forbidden}">
                        <h1><bean:message key="group.join.forbidden" arg0="${group.name}"/></h1>
                        &nbsp;
                        <input type="button" class="input_btn" name="cancel" value="<bean:message key="page.group.cancel" />" onclick="history.back();" />
                        <br class="clear" />
                    </c:if>
                    <c:if test="${false eq forbidden}">
                        <form name="form1" action="group.shtml" method="post">
                            <input type="hidden" value="${group.id }" name="gid" />
                            <input type="hidden" value="join" name="method" />
                            <div class="msgwrap">
                                <img src="images/msg_confirm.gif" align="absmiddle" />
                                <h1><bean:message key="page.group.join.confirm.hint" arg0="${group.name }" /></h1>&nbsp;&nbsp;<input type="submit" class="input_btn" name="submit" value="<bean:message key="page.group.join.confirm" />" />&nbsp;<input type="button" class="input_btn" name="cancel" value="<bean:message key="page.group.cancel" />" onclick="history.back();" />
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${true eq done}">
                        <div class="msgwrap">
                            <img src="images/msg_ok.gif" align="absmiddle" />
                            <h1><bean:message key="page.group.join.done" arg0="${group.name }"/></h1>&nbsp;&nbsp;<input type="button" class="input_btn" name="ok" value="<bean:message key="page.group.visit" />" onclick="location.href='group/${group.id }';" />
                        </div>
                    </c:if>
                    <c:if test="${true eq needAuth}">
                        <div class="msgwrap">
                            <img src="images/msg_ok.gif" align="absmiddle" />
                            <h1><bean:message key="group.join.done" /></h1>
                            &nbsp;&nbsp;<input type="button" class="input_btn" name="ok" value="<bean:message key="page.group.visit" />" onclick="location.href='group/${group.id }';" />
                        </div>
                    </c:if>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="right.jsp"></jsp:include>
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
    </body>
</html>
