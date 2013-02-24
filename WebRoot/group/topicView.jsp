<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>${topicModel.title }&nbsp;-&nbsp;${group.name }&nbsp;-&nbsp;<bean:message key="menu.group.indexPage" /></title>
        <script type="text/javascript">
            $(function(){
                $('.reply_user img').each(function() {
                    if($(this).width() > 50) {
                        $(this).width(32);
                    };
                });
            });

            var maxWidth = function() {
                var maxWidth = parseInt($('#dc img').css('max-width'));
                $('#dc img').each(function() {
                    if ($(this).width() > maxWidth) {
                        $(this).width(maxWidth);
                    }
                });
            };
            if (window.attachEvent) window.attachEvent("onload", maxWidth);

            var checkForm = function() {
                J2bbCommon.removeformError('topicContent');
                var form = $('topicForm');
                var content = $("#topicContent").val();
                if(content=='' || content.length > 20000) {
                    J2bbCommon.errorWithElement('errorMsg', '<bean:message key="page.group.topic.error.comment" />');
                    return false;
                }
                $('#submitRe').val('<bean:message key='page.common.button.submitting' />');
                $('#submitRe').attr('disabled', 'disabled');
                return true;
            };

            var reply = function(num,text) { }

            var newForm = function() {
                var obj = $('reGroup');
                alert(obj.style.display);
                if(obj.style.display!='none') {
                    obj.style.display='block';
                    obj.focus();
                } else {
                    obj.style.display='block';
                    obj.focus();
                }
            };

            var ctrlKeyEsc = function(evt) {
                evt = (evt) ? evt : ((window.event) ? event : null);
                if (evt) {
                    if (evt.ctrlKey && evt.keyCode==13) {
                    }
                }
            };

            //bad hit
            var bad = function(id) {
                $.get(
                "${context }/group.shtml",
                { method:'bad', tid:id, loadType:'ajax'},
                function(data) { $('#badhits_'+id).html(data); }
            );
            };

            //good hit
            var good = function(id) {
                $.get(
                '${context }/group.shtml',
                { method:'good', tid:id, loadType:'ajax'},
                function(data) { $('#goodhits_'+id).html(data); }
            );
            };

            //回复某楼的用户
            var re = function(index,username) {
                $("#para_reply_to").val(username);
                $("#topicContent").val("@"+username+" "+index+"#\n");
                $("#topicContent").focus();
            }

            //ie6 实现 css2 伪类
            if($.browser.msie && $.browser.version == 6.0) {
                $("ul.opt2 li").mouseover(function(){
                    $(this).addClass("hover");
                }).mouseout(function(){
                    $(this).removeClass("hover");
                });
            };

            var addAttachement = function(index) {
                if($("#attachment_"+(index-1)).val()!=''){
                    $("#attachment_"+index).show();
                    $("#attachmentSpan_"+index).show();
                }
            };
        </script>
        <style>
            .line_block ul {
                margin: 5px 0
            }
            .line_block ul li {
                float: right;
                margin: 0 0 0 8px
            }
        </style>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper" class="clearfix">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="group/${group.id }">${group.name }</a>&nbsp;&gt;&nbsp;正文
                    </div>
                    <div class="fright">
                        <jsp:include page="../common/_share.jsp">
                            <jsp:param name="share_type" value="2" />
                            <jsp:param name="share_id" value="${topicModel.id}" />
                            <jsp:param name="share_url" value="${basePath }group/topic/${topicModel.id}" />
                            <jsp:param name="share_title" value="${topicModel.title }" />
                            <jsp:param name="share_content" value="" />
                        </jsp:include>
                    </div>
                </div>
                <div class="left_block clearfix">
                    <jsp:include flush="true" page="_groupInfo.jsp" />
                </div>
                <%-- topic content--%>
                <div class="left_block top_blank clearfix">
                    <div id="group_tab_wrap">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="currTopic" />
                        </jsp:include>
                    </div>
                    <div id="uinfo">
                        <div id="uhead" style="width: 90px; height: 90px; padding-top: 8px;">
                            <img src="<com:img value='${author.userFace }' type='sized' width='80' />" alt="${author.userName }" />
                        </div>
                        <div id="udetail" class="linkblue">
                            <a href="<ctba:wrapuser user="${author}" linkonly="true"/>" class="userName">${author.userName }</a>
                            <br />
                            <span class="color_orange">
                                <com:ipData value="${topicModel.ip }" />
                            </span>
                            <br />
                        </div>
                    </div>
                    <div id="topicstuff" class="font_small color_orange">
                        ${count }&nbsp;Replies&nbsp;and&nbsp;${topicModel.hits }&nbsp;hits&nbsp;<img src="images/icons/btn_default.gif" align="absmiddle" />
                    </div>
                    <div id="dw">
                        <div id="dt">
                            <div id="ctl"></div>
                            <div id="tl"></div>
                            <div id="ctr"></div>
                        </div>
                        <div id="la"></div>
                        <div id="dt2" class="atitle">
                            <h1 class="text_shadow">
                                <strong>${topicModel.title }</strong>
                            </h1>
                        </div>
                        <div id="dc" class="orangelink">
                            <com:topic value="${topicModel.content }" />
                            <br />
                            <c:if test="${not empty attachments}">
                                <br />
                                <img src="images/paperclip.gif" width="15" height="15" />
                                <br />
                                <c:forEach items="${attachments}" var="attachment">
                                    <com:attachment path="${attachment }" />
                                    <br />
                                </c:forEach>
                            </c:if>
                            <br />
                            <img src="images/signature.gif"  />
                            <br />
                            ${userQMD }
                        </div>
                        <div id="db">
                            <div id="cbl"></div>
                            <div id="bl"></div>
                            <div id="cbr"></div>
                        </div>
                    </div>
                    <div class="line_block clearfix">
                        <div id="topicdate" class="color_gray">
                            <img src="images/icons/color_swatch.png" align="absmiddle" />&nbsp;<bean:message key="page.group.topic.createTime" />&nbsp;<com:formatTime time="${topicModel.createTime }" />
                        </div>
                        <%--
						<c:if test="${true eq isUser}">
							<a href="javascript:good('${topicModel.id }');"><img src="images/icons/emoticon_smile.png" align="absmiddle" title="对本话题感兴趣" /></a>&nbsp;<span class="color_orange" id="goodhits_${topicModel.id }">${topicModel.goodHits }</span>&nbsp;<span class="color_gray">人对本话题感兴趣</span>&nbsp;&nbsp;<a href="javascript:bad('${topicModel.id }');"><img src="images/icons/emoticon_unhappy.png" align="absmiddle" title="没兴趣" /></a>&nbsp;<span class="color_orange" id="badhits_${topicModel.id }">${topicModel.badHits }</span>&nbsp;<span class="color_gray">人没兴趣</span>
						</c:if>
						<c:if test="${false eq isUser}">
							<img src="images/icons/emoticon_smile.png" align="absmiddle" />&nbsp;<span class="normalorange">${topicModel.goodHits }</span>&nbsp;<span class="color_gray">人对本话题感兴趣</span>&nbsp;&nbsp;<img src="images/icons/emoticon_unhappy.png" align="absmiddle" title="没兴趣" />&nbsp;<span class="color_orange">${topicModel.badHits }</span>&nbsp;<span class="color_gray">人没兴趣</span>
						</c:if>
						--%>
                    </div>
                    <div class="line_block clearfix">
                        <ul class="clearfix">
                            <li>
                                <c:if test="${true eq __group_is_user}">
                                    <a href="javascript:window.scroll(0,document.body.scrollHeight)" class="link_btn"><bean:message key="page.group.topic.reply" /></a>
                                </c:if>
                            </li>
                            <li>
                                <a href="news.shtml?method=post&gtid=${topicModel.id }" class="link_btn"><bean:message key="page.group.topic.toNews" /></a>
                            </li>
                            <li>
                                <a href="feedback.action?method=form&type=group&id=${topicModel.id }" class="link_btn">举报</a>
                            </li>
                            <li>
                                <a href="javascript:window.scroll(0,0)" class="link_btn"><bean:message key="page.common.button.backToTop" /></a>
                            </li>
                            <c:if test="${(not empty __sys_username) and (not __group_is_waiting)}">
                                <c:if test="${false eq __group_is_user}">
                                    <li><a href="g.action?method=mine&action=join&gid=${group.id }" class="link_btn"><bean:message key="page.group.topic.joinToReply" /></a></li>
                                </c:if>
                            </c:if>
                            <c:if test="${__user_is_editor or isAuthor or __group_is_manager}">
                                <li><a href="gt.action?method=form&tid=${topicModel.id }" class="link_btn"><bean:message key="page.common.button.edit" /></a></li>
                            </c:if>
                        </ul>
                    </div>
                    <jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>
                </div>
                <div class="left_block top_blank_wide orangelink">
                    <c:if test="${not empty topicsMap}">
                        <pg:pager items="${count}" url="gt.action" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                            <pg:param name="gid" />
                            <pg:param name="tid" />
                            <pg:param name="method" />
                            <pg:index>
                                <div class="pageindex_list">
                                    <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                </div>
                            </pg:index>
                            <c:forEach items="${topicsMap}" var="model" varStatus="status">
                                <div>
                                    <div class="reply_list clearfix">
                                        <div class="reply_user">
                                            <img src="<com:img value="${model.author.userFace }" type="sized" width="32" />" alt="${model.author.userName }" />
                                        </div>
                                        <div id="reply_info" class="linkblue_b">
                                            <a href="<ctba:wrapuser user="${model.author}" linkonly="true"/>" class="userName">${model.author.userName }</a>&nbsp;&nbsp;<span class="color_gray"><com:ipData value="${model.topic.ip }" /></span>
                                        </div>
                                        <div class="reply_date">
                                            <div class="fleft">
                                                <span class="font_small color_orange">${15*(currentPageNumber-1)+status.index+1 }/${count}</span>&nbsp;&nbsp;<span class="font_small color_gray"><com:date value="${model.topic.createTime }" start="2" limit="16" />&nbsp;|</span>
                                            </div>
                                            <ul class="opt2 fleft">
                                                <li>
                                                    <span class="opt_arrow"></span>
                                                    <ul>
                                                        <c:if test="${not empty __sys_username}">
                                                            <li>
                                                                <a href="javascript:re(${15*(currentPageNumber-1)+status.index+1 },'${model.author.userName }');"><bean:message key="page.common.button.reply" /></a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${model.isAuthor or __group_is_manager}">
                                                            <li>
                                                                <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm" />','${basePath}gt.action?method=delete&tid=${model.topic.id }');return false;"><bean:message key="page.common.button.delete" /></a>
                                                            </li>
                                                        </c:if>
                                                        <li>
                                                            <a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop" /></a>
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </div>
                                        <div id="reply_content" class="orangelink">
                                            <com:topic value="${model.topic.content }" />
                                            <%--<g:htmlEncode value="${model.topic.content }" />--%>
                                            <c:if test="${not empty model.attachments}">
                                                <br />
                                                <img src='images/paperclip.gif' width=15 height=15>
                                                <br>
                                                <c:forEach items="${model.attachments}" var="attachment">
                                                    <com:attachment path="${attachment }" />
                                                    <br />
                                                </c:forEach>
                                            </c:if>
                                            <div class="rightpart"></div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <pg:index>
                                <div class="pageindex_bottom">
                                    <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                </div>
                            </pg:index>
                        </pg:pager>
                    </c:if>
                    <div class="line_block_b right darkgraylink">
                        <a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop" /></a>&nbsp;|&nbsp;<bean:message key="page.group.topic.commentCnt" arg0="${count }"/>
                    </div>
                    <jsp:include page="../_loginBlock.jsp"></jsp:include>
                </div>
                <c:if test="${true eq __group_is_user}">
                    <div class="title_bar">
                        <bean:message key="page.group.topic.reply" />:&nbsp;${topicModel.title }
                    </div>
                    <div class="left_block top_blank_wide radius_top_none">
                        <div id="errorMsg"></div>
                        <div class="replyform">
                            <form id="topicForm" name="topicForm" action="gt.action?method=save" enctype="multipart/form-data" method="post" onsubmit="return checkForm();">
                                <div class="replyitems">
                                    <ul>
                                        <li>
                                            <com:ubb />
                                        </li>
                                        <li>
                                            <textarea name="topicContent" id="topicContent" class="topicContent" style="width:554px;height:150px"></textarea>
                                            <input type="hidden" name="gid" value="${group.id }" />
                                            <input type="hidden" name="para_reply_to" id="para_reply_to" value="${author.userName }"/>
                                            <input type="hidden" name="pid" value="${topicModel.id }" />
                                        </li>
                                        <li class="attachment">
                                            <p class="color_darkgray">
                                                <bean:message key="page.group.topic.form.attachment" />
                                                <input type="file" name="attachment_1" id="attachment_1" />
                                                <span id="attachmentSpan_1"><a href='javascript:addAttachement(2);'><img src="images/icons/add.gif" align="absmiddle" alt="<bean:message key="page.group.topic.form.attachment.addMore" />" /></a></span>
                                                <input type="file" name="attachment_2" id="attachment_2" class="hide" />
                                                <span id="attachmentSpan_2" style="display: none"><a href='javascript:addAttachement(3);'><img src="images/icons/add.gif" align="absmiddle" alt="<bean:message key="page.group.topic.form.attachment.addMore" />" /></a></span>
                                                <input type="file" name="attachment_3" id="attachment_3" class="hide" />
                                            </p>
                                        </li>
                                        <li class="attachment">
                                            <input class="input_btn" name="submit" id="submitRe" type="submit" value="<bean:message key="page.common.button.submit" />" />
                                        </li>
                                    </ul>
                                </div>
                                <%--<div id="emotionsDiv"></div> --%>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
        <script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>