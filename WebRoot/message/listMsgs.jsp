<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="user.mail" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script>
            function toggleSelect(){
                var checked= document.getElementById("toggleSelectCheckBox").checked;
                var inputs = document.getElementsByTagName("input");
                for(var i=0;i<inputs.length;i++){
                    if(inputs[i].type=="checkbox"){
                        inputs[i].checked=checked;
                    }
                }
            }
            function deleteMsgs(){
                $("#actionMethod").val("delete");
                $("#messageActionForm").submit();
            }
            function toggleState(){
                $("#actionMethod").val("status");
                $("#messageActionForm").submit();
            }
            function toggleStar(){
                $("#actionMethod").val("star");
                $("#messageActionForm").submit();
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;${user.userName}&nbsp;&gt;&nbsp;<bean:message key="user.mail" />
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlock.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                
                <jsp:include page="../ad/_raywowAdBlock.jsp" />
                
                    <form action="message.action" id="messageActionForm" method="post">
                        <input type="hidden" id="actionMethod" name="method"/>
                        <div class="mid_block clearfix">
                            <div class="mail_bar">
                                <jsp:include flush="true" page="_msgBar.jsp"></jsp:include>
                            </div>
                            <div class="mail_info2">
                                <bean:message key="page.message.op"/>: <a onclick="toggleState();" class="pointer"><bean:message key="page.message.op.toggleRead"/></a>&nbsp;|&nbsp;<a onclick="toggleStar();" class="pointer"><bean:message key="page.message.op.toggleStar"/></a>&nbsp;|&nbsp;<a onclick="deleteMsgs();" class="pointer"><bean:message key="page.message.op.delete"/></a>
                            </div>
                            <pg:pager items="${count}" url="message.action" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                                <pg:param name="method" />
                                <pg:param name="read" />
                                <pg:param name="marked" />
                                <div id="mail_list" class="clearfix">
                                    <ul class="column_5c lightyellow_bg" style="height:28px">
                                        <li class="col3-5 center">
                                            <input type="checkbox" id="toggleSelectCheckBox" class="nborder" onclick="toggleSelect();" style="background:none" />
                                        </li>
                                        <li class="col3-5 center">
                                            <img src="images/star.gif" align="absmiddle" alt="星标" title="星标" />
                                        </li>
                                        <li class="col62">
                                            <b><bean:message key="page.message.header.title"/></b>
                                        </li>
                                        <li class="col20">
                                            <c:if test="${'outbox' == box}"><b><bean:message key="page.message.header.reciever"/></b></c:if>
                                            <c:if test="${'outbox' != box}"><b><bean:message key="page.message.header.sender"/></b></c:if>
                                        </li>
                                        <li class="col11">
                                            <b><bean:message key="page.message.header.date"/></b>
                                        </li>
                                    </ul>
                                    <logic:notEmpty name="models">
                                        <logic:iterate id="model" name="models" indexId="index">
                                            <ul class="clearfix column_5c <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
                                                <li class="col3-5 center" style="background:#fff">
                                                    <input type="checkbox" id="_msg_${model.message.msgId }" class="nborder" name="_msg_${model.message.msgId }" style="background:none" />
                                                </li>
                                                <li class="col3-5 center">
                                                    <c:if test="${model.message.msgStar!=1}"><img src="images/unstar.gif" align="absmiddle" /></c:if><c:if test="${model.message.msgStar==1}"><img src="images/star.gif" align="absmiddle" /></c:if>
                                                </li>
                                                <li class="col62 <c:if test="${model.message.msgRead eq 0 }">bold</c:if>" style="overflow:hidden">
                                                    <a href="message.action?method=view&mid=${model.message.msgId }"><comm:limit maxlength="50" value="${model.message.msgTitle}"/></a>&nbsp;
                                                </li>
                                                <li class="col20 list_sp2" style="overflow:hidden;font-family:Arial">
                                                    <c:if test="${'outbox' != box}">
                                                        <a href="u/${model.u.userId }"><img src="<comm:img value="${model.u.userFace}" type="mini" />" width="16" height="16" class="userFace_border" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${model.u.userId }">${model.u.userName}</a>
                                                    </c:if>
                                                    <c:if test="${'outbox' == box}">
                                                        <a href="u/${model.uTo.userId }"><img src="<comm:img value="${model.uTo.userFace}" type="mini" />" width="16" height="16" class="userFace_border" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${model.uTo.userId }">${model.uTo.userName}</a>
                                                    </c:if>
                                                </li>
                                                <li class="col11 font_mid color_gray">
                                                    <comm:date value="${model.message.msgTime }" start="2" limit="10" />
                                                </li>
                                            </ul>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </div>
                                <pg:index>
                                    <div class="mail_pager clearfix">
                                        <jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                            </pg:pager>
                            <div class="mail_bar_bottom">
                                <ul>
                                    <jsp:include flush="true" page="_msgBar.jsp"></jsp:include>
                                </ul>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../user/right.jsp" />
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp" />
    </body>
</html>