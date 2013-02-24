<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><bean:message key="user.post.mail" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <%@include file="../common/include.jsp"%>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp">
            <jsp:param name="submenu" value="user" />
        </jsp:include>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="user.do?method=listMsgs"><bean:message key="user.mail" /></a>&nbsp;&gt;&nbsp;<bean:message key="user.post.mail" />
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlock.jsp"></jsp:include>
                </div>

                <div id="mid_column" class="fright">
                    <div class="mail_bar">
                        <jsp:include flush="true" page="_msgBar.jsp"></jsp:include>
                    </div>
                    <br class="clear"/>
                    <div class="mid_block top_notice clearfix">
                        &nbsp;&nbsp;<h2 class="color_orange"><b><bean:message key="page.message.info.sent"/></b></h2>
                        &nbsp;&nbsp;<img src="images/ico_sendf.gif"/><br/>
                        &nbsp;&nbsp;<a href="messages"><bean:message key="page.message.button.back"/></a>&nbsp;&nbsp;
                        |&nbsp;&nbsp;<a href="message.action?method=form"><bean:message key="page.message.button.sendNew"/></a>
                        <logic:present name="parent">
                            |&nbsp;&nbsp;<a href="message.action?method=view&mid=${parent }"><bean:message key="page.message.button.viewParent"/></a>
                        </logic:present>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../user/right.jsp"></jsp:include>
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
    </body>
</html>