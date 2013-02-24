<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${entryModel.title }&nbsp;-&nbsp;${blogModel.name}</title>
        <jsp:include page="_head.jsp"></jsp:include>
        <script>
           
                var loader = '<img src="images/ajax-loader.gif" />';
                function firstPage () {
                    var currOffset = 0;
                    loadNotes(currOffset);
                }
                function lastPage (currOffset, pageSize, totalCnt) {
                    currOffset = pageSize * (parseInt('' + totalCnt / pageSize, 10));
                    loadNotes(currOffset);
                }
                function nextPage (currOffset, pageSize, totalCnt) {
                    currOffset = currOffset+pageSize;
                    if (currOffset >= totalCnt) {
                        currOffset = currOffset - pageSize;
                    }
                    loadNotes(currOffset);
                }
                function prevPage (currOffset, pageSize) {
                    currOffset = currOffset - pageSize;
                    if (currOffset <= 0) {
                        currOffset = 0;
                    }
                    loadNotes(currOffset);
                }
                function submitNote(){
                    var name = $('#authorName').val(),
                        content = $('#topicContent').val(),
                        verImg = 'test',
                        repliedUsername = $('#para_reply_to').val();
                    if (content === '' || content.length > 500) {
                        J2bbCommon.formError('topicContent', '<bean:message key="page.blog.comment.error.content"/>', 'red');
                        return;
                    }
                    $('#submitButton').val('<bean:message key="page.common.button.submitting"/>').attr('disabled', 'disabled');
                    $.post(
                        'com.action',
                        {
                            method: 'save',
                            bid: '${board.boardId}',
                            eid: '${entryModel.id}',
                            body: content,
                            name: name,
                            para_reply_to: repliedUsername,
                            loadType: 'ajax'
                        },
                        function (data) {
                            $('#noteContent').val('');
                            $('#authorName').val('');
                            loadNotes(0);
                        }
                    );
                    J2bbCommon.removeformError('authorName');
                    J2bbCommon.removeformError('noteContent');
                }
                function loadNotes (offSet) {
                    $('#comments').html(loader);
                    $.ajax({ url: 'com.action?method=list',
                        type: 'get',
                        data: 'themeDir=${blogModel.theme}&loadType=ajax&eid=${entryModel.id}&pager.offset=' + offSet,
                        success: function (msg) {
                            $('#comments').html(msg);
                        }
                    });
                }
                function reNote (username) {
                    $('#para_reply_to').val(username);
                    $('#topicContent').val('@' + username + '\n').focus();
                }
 $(function () {
                // load comment list    
                loadNotes(0);
            });
        </script>
    </head>
    <body>
        <div id="index">
            <div id="container">
                <div id="header">
                    <div class="header-top">&nbsp;</div>
                    <h1 class="blogName">
                        <ctba:wrapblog blog="${blogModel}" user="${blogAuthor}"/>
                    </h1>
                    <div class="description">
                        <com:topic value="${blogModel.description}"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div id="innerContainer">
                    <div class="innerTop"></div>
                    <div id="outerContent">
                        <div id="content" class="content">
                            <div class="contentTop" id="Top"></div>
                            <ul id="posts">
                                <div class="postsTop"></div>
                                <li>
                                    <div class="postHeader">
                                        <h3>${entryModel.publishDate }</h3>
                                        <h2>
                                            ${entryModel.title }
                                            <c:if test="${not empty categoryModel}">
                                                <span class="category">&nbsp;-&nbsp;[&nbsp;<a href="blog/${blogModel.id}/${categoryModel.id}"><com:limit maxlength="14" value="${categoryModel.name}" /></a>&nbsp;]</span>
                                            </c:if>
                                        </h2>
                                    </div>
                                    <div class="clear"></div>
                                    <div class="postBody">
                                        ${entryModel.body }
                                        <div class="clear"></div>
                                    </div>
                                    <div class="postFooter">
                                        <div class="tags">
                                            <logic:present name="prevModel"><img src="images/icons/pico_left.gif" align="absmiddle" />&nbsp;<a href="blog/entry/${prevModel.id }">${prevModel.title }</a></logic:present><span class="color_gray">&nbsp;&nbsp;|&nbsp;&nbsp;</span><logic:present name="nextModel"><a href="blog/entry/${nextModel.id }">${nextModel.title }</a><img src="images/icons/pico_right.gif" align="absmiddle" /></logic:present>
                                        </div>
                                        <div class="menubar">
                                            <c:if test="${entryModel.author eq  __sys_username }"><a href="be.action?method=form&eid=${entryModel.id }" class="edit"><bean:message key="page.blog.entry.edit"/></a>&nbsp;|&nbsp;<a href="be.action?method=list" class="readmore"><bean:message key="menu.blog.entryList" /></a>&nbsp;|&nbsp;</c:if><span class="author"><a href="u/${blogAuthor.userId}">${blogModel.author}</a></span>&nbsp;<bean:message key="page.blog.entry.postTime"/>&nbsp;<span class="time">${entryModel.publishDate}</span>&nbsp;|&nbsp;<bean:message key="page.blog.entry.hits"/>&nbsp;<span class="count">${entryModel.hits }</span>
                                        </div>
                                        <jsp:include page="../common/_share_simple.jsp">
                                            <jsp:param name="share_type" value="1" />
                                            <jsp:param name="share_id" value="${entryModel.id}" />
                                            <jsp:param name="share_url" value="${basePath }blog/entry/${entryModel.id}" />
                                            <jsp:param name="share_title" value="${entryModel.title }" />
                                            <jsp:param name="share_content" value="" />
                                        </jsp:include>
                                        <script type="text/javascript" src="http://9.douban.com/js/button_widget.js"></script>
                                        <a href="feedback.action?method=form&type=blog&id=${entryModel.id }" class="link_btn_orange w85 mt10">举报不良信息</a>
                                        <c:if test="${not empty refEntries}">
                                            <div class="correlation">
                                                <h3><bean:message key="page.blog.entry.refEntries"/></h3>
                                                <ul class="clearfix">
                                                    <c:forEach items="${refEntries}" var="model">
                                                        <li>
                                                            <a href="${model.url}">${model.label}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:if>
                                    </div>
                                </li>
                                <div class="postsBottom"></div>
                            </ul>
                            <div id="comments"></div>
                            <div class="contentBottom"></div>
                        </div>
                    </div>
                    <div id="outerSidebar">
                        <jsp:include flush="true" page="_right.jsp"></jsp:include>
                    </div>
                    <div class="innerBottom"></div>
                    <div class="clear"></div>
                    <div id="footer">
                        <div class="copyright">
                            Copyright © 2002-2010 <a href="http://ctba.cn">CTBA.CN</a>, All Rights Reserved.&nbsp;<a href="http://www.miibeian.gov.cn/">京ICP备05063221号</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="_bottom.jsp"></jsp:include>
    </body>
</html>
