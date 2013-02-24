<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%
//这里，如果Exception是ClassNotFoundException的话，跳转到原先的URL
        if (exception instanceof ClassNotFoundException) {
            response.sendRedirect((String) session.getAttribute(org.net9.core.util.HttpUtils.getReferer(request)));
            return;
        }
%>
<%
//这里，如果Exception是CharConversionException的话，跳转到原先的URL
        if (exception instanceof java.io.CharConversionException) {
            response.sendRedirect((String) session.getAttribute(org.net9.core.util.HttpUtils.getReferer(request)));
            return;
        }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><bean:message key="page.common.error.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <%@include file="include.jsp"%>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp; <bean:message key="page.common.error.title" />
                </div>
                <br class="clear" />
                <div class="left_block">
                    <p style="text-align:center">
                        <img src="./images/404_1.jpg" alt="" />
                    </p>
                    <div class="errormsg" style="text-align:center;">
                      <bean:message key="page.common.error.hint" />
                    </div>
                    <div style="text-align:center;margin:10px;">
                        <a href="javascript:history.back();" class="input_btn"><bean:message key="message.back" /></a>
                        <br class="clear"/>
                        <br class="clear"/>
                        <div class="message_info">
                            <%--						<%= exception.getMessage() %>--%>
                            <%exception.printStackTrace(response.getWriter());%>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            <div id="area_right"></div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
    </body>
</html>
