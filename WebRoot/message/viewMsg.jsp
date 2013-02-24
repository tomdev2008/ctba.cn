<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.message.view.title"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            //check the form
            function checkTopicForm(){
                J2bbCommon.removeformError("title");
                J2bbCommon.removeformError("topicContent");
                var content = $("#topicContent").val();
                var title = $("#title").val();
                if(title.length>30||title.length<1){
                    J2bbCommon.formError("title","<bean:message key="page.message.error.title"/>","bottom");
                    return false;
                }
                if(content.length>10000||content.length<1){
                    J2bbCommon.formError("topicContent","<bean:message key="page.message.error.content"/>","bottom");
                    return false;
                }
                $("#submitRe").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitRe").attr("disabled","disabled");
                return true;
            }
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp">
            <jsp:param name="submenu" value="user" />
        </jsp:include>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="user.do?method=listMsgs"><bean:message key="user.mail" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.message.view.title"/>
                    </div>
                    <div class="right" id="errorMsg">${userMsg }</div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlock.jsp"></jsp:include>
                </div>

                <div id="mid_column" class="fright">

                    <form name="form1" action="message.action?method=save" method="post" onsubmit="return checkTopicForm();">
                        <input type="hidden" name="re" value="on" />
                        <input type="hidden" name="parent" value="${model.msgId }" />
                        <input type="hidden" name="msgTo" value="${model.msgFrom }" />
                        <div class="mail_bar">
                            <ul>
                                <li><a href="messages"><bean:message key="page.message.button.back"/></a></li>
                                <li><a class="pointer" onclick="$('#mail_reply').toggle('slow')"><bean:message key="page.message.button.reply"/></a></li>
                                <li><a href="message.action?method=list&read=false"><bean:message key="user.mail.unread" /></a></li>
                                <li><logic:notEmpty name="prevModel"><a href="message.action?method=view&mid=${prevModel.msgId }"><bean:message key="page.message.button.prev"/></a></logic:notEmpty></li>
                                <li><logic:notEmpty name="nextModel"><a href="message.action?method=view&mid=${nextModel.msgId }"><bean:message key="page.message.button.next"/></a></logic:notEmpty></li>
                                <li class="mail_post"><a href="message.action?method=form"><bean:message key="user.post.mail" /></a></li>
                            </ul>
                        </div>
                        <div class="mail_info">
                            <h2><b>${model.msgTitle }</b></h2>
                            <table cellspacing="0" class="color_gray">
                                <tr>
                                    <td><bean:message key="page.message.header.sender"/>:</td>
                                    <td><b class="color_orange">${uFrom.userName }</b>&nbsp;&lt;<a href="u/${uFrom.userId }" class="bluelink">ctba.cn/u/${uFrom.userId }</a>&gt;</td>
                                </tr>
                                <tr>
                                    <td><bean:message key="page.message.header.time"/>:</td>
                                    <td>${model.msgTime}&nbsp;(<com:formatTime time="${model.msgTime}" />)</td>
                                </tr>
                                <tr>
                                    <td><bean:message key="page.message.header.reciever"/>:</td>
                                    <td>${uTo.userName }&nbsp;&lt;<a href="u/${uTo.userId }">ctba.cn/u/${uTo.userId }</a>&gt;</td>
                                </tr>
                            </table>
                        </div>
                        <div class="mail_content">
                            ${msgContent }
                        </div>
                        <div class="mail_sender clearfix">
                            <ul></ul>
                            <ul id="mail_reply" class="hide">
                                <li><input type="text" name="title" id="title" style="width:300px;" value="Re:<com:limit maxlength="20" value="${model.msgTitle }"/>" class="formInput" /></li>
                                <li class="graylink mail_ubb"><com:ubb/></li>
                                <li><textarea class="formTextarea" name="topicContent" id="topicContent" style="width:500px;"></textarea></li>
                                <li><input class="pointer" type="submit" id="submitRe" name="submit" value="发 送" /></li>
                            </ul>
                        </div>
                        <div class="mail_bar_bottom">
                            <ul>
                                <li><a href="messages"><bean:message key="page.message.button.back"/></a></li>
                                <li><a class="pointer" onclick="$('#mail_reply').toggle('slow')"><bean:message key="page.message.button.reply"/></a></li>
                                <li><a href="message.action?method=list&read=false"><bean:message key="user.mail.unread" /></a></li>
                                <li><logic:notEmpty name="prevModel"><a href="message.action?method=view&mid=${prevModel.msgId }"><bean:message key="page.message.button.prev"/></a></logic:notEmpty></li>
                                <li><logic:notEmpty name="nextModel"><a href="message.action?method=view&mid=${nextModel.msgId }"><bean:message key="page.message.button.next"/></a></logic:notEmpty></li>
                                <li class="mail_post"><a href="message.action?method=form"><bean:message key="user.post.mail" /></a></li>
                            </ul>
                        </div>
                    </form>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../user/right.jsp"></jsp:include>
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
        <script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>
