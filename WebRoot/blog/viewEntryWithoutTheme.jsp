<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <script type="text/javascript" src="${context}/javascript/jquery.cycle.all.pack.js"></script>
        <title>${entryModel.title }&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
        <script type="text/javascript">
            //added by mockee 090114
            function maxWidth(){
                var maxWidth = parseInt($('#dc img').css('max-width'));
                $('#dc img').each(function(){
                    if ($(this).width() > maxWidth)
                        $(this).width(maxWidth);
                });
            }
            if (window.attachEvent) window.attachEvent("onload", maxWidth);

            function firstPage(){
                var currOffset= 0;
                loadNotes(currOffset);
            }
            function lastPage(currOffset,pageSize,totalCnt){
                currOffset= pageSize*(parseInt(""+totalCnt/pageSize));
                loadNotes(currOffset);
            }
            function nextPage(currOffset,pageSize,totalCnt){
                currOffset= currOffset+pageSize;
                if(currOffset>=totalCnt)currOffset= currOffset-pageSize;
                loadNotes(currOffset);
            }
            function prevPage(currOffset,pageSize){
                currOffset= currOffset-pageSize;
                if(currOffset<=0)currOffset=0;
                loadNotes(currOffset);
            }
            $( function(){
                //loadNotes(0);
            });

            function submitNote(){
                var name = $("#authorName").val();
                var verImg = "test";
                var content = $("#topicContent").val();
                var repliedUsername = $("#para_reply_to").val();
                if(content==''||content.length>500){
                    J2bbCommon.formError("topicContent","<bean:message key="page.blog.comment.error.content"/>","red");
                    return;
                }
                $("#submitButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitButton").attr("disabled","disabled");
                $.post("com.action",
                { method:"save",bid:"${board.boardId}",para_reply_to:repliedUsername,eid:"${entryModel.id}",body:content,name:name,loadType:"ajax"},
                function(data){
                    $("#noteContent").val('');
                    $("#authorName").val('');
                    loadNotes(0);
                }
            );
                J2bbCommon.removeformError("authorName");
                J2bbCommon.removeformError("noteContent");
            }
            function loadNotes(offSet){
                $("#notes").html("<img src='images/ajax-loader.gif'/>");
                $.ajax({ url: "com.action?method=list",
                    type:"get",
                    data:"themeDir=${blogModel.theme}&loadType=ajax&eid=${entryModel.id}&pager.offset="+offSet,
                    success:function(msg){
                        $("#notes").html(msg);
                    }
                });
            }
            function reNote(username){
                $("#para_reply_to").val(username);
                //$("#topicContent").val('');
                //$("#topicContent").val("@"+username+"\n"+content+" \n==================\n");
                $('#topicContent').val('@'+username+'\n');
                $("#topicContent").focus();
            }
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="blog/"><bean:message key="menu.blog.navigate" /></a>&nbsp;&gt;&nbsp;<a href="blog/${blogModel.id}">${blogModel.name}</a>&nbsp;&gt;&nbsp;<c:if test="${not empty categoryModel}"><a href="blog/${categoryModel.id}"><com:limit maxlength="14" value="${categoryModel.name}" /></a>&nbsp;&gt;&nbsp;</c:if>正文
                    </div>
                    <div class="fright">
                        <jsp:include page="../common/_share.jsp">
                            <jsp:param name="share_type" value="1"/>
                            <jsp:param name="share_id" value="${entryModel.id}"/>
                            <jsp:param name="share_url" value="${basePath }blog/entry/${entryModel.id}"/>
                        </jsp:include>
                    </div>
                </div>
                <div class="left_block top_blank_wide clearfix radius">
                    <div id="uinfo" class="linkblue">
                        <div id="uhead">
                            <img src="<com:img value="${author.userFace}" type="sized" width="80"/>" width="80" class="group_border" />
                        </div>
                        <div id="udetail" class="linkblue">
                            <span class="color_orange"><a href="u/${blogAuthor.userId}">${blogModel.author}</a></span>
                        </div>
                    </div>
                    <div id="topicstuff" class="font_small color_orange">
                        ${entryModel.hits }&nbsp;hits&nbsp;<img src="images/icons/btn_default.gif" align="absmiddle" />
                    </div>
                    <div id="dw">
                        <div id="dt">
                            <div id="ctl"></div>
                            <div id="tl"></div>
                            <div id="ctr"></div>
                        </div>
                        <div id="la"></div>
                        <div id="dt2" class="text_shadow">
                            <h1><b>${entryModel.title }</b></h1>
                        </div>
                        <div id="dc" class="orangelink">
                            ${entryModel.body }
                        </div>
                        <div id="db">
                            <div id="cbl"></div>
                            <div id="bl"></div>
                            <div id="cbr"></div>
                        </div>
                    </div>
                    <div class="line_block right">
                        <div id="topicdate" class="font_small color_gray">
                            Post Date:&nbsp;${entryModel.publishDate }
                        </div>
                        <logic:present name="prevModel">
                            <img src="images/icons/pico_left.gif" align="absmiddle" />&nbsp;<a href="blog/entry/${prevModel.id }">${prevModel.title }</a>
                        </logic:present>
                        <span class="color_gray">&nbsp;|&nbsp;</span>
                        <logic:present name="nextModel">
                            <a href="blog/entry/${nextModel.id }">${nextModel.title }</a>&nbsp;<img src="images/icons/pico_right.gif" align="absmiddle" />
                        </logic:present>
                    </div>
                    <div class="line_block right">
                        <c:if test="${entryModel.author eq  __sys_username }">
                            <a href="be.action?method=form&eid=${entryModel.id }" class="link_btn"><bean:message key="page.blog.entry.edit"/></a>
                            <a href="be.action?method=list" class="link_btn"><bean:message key="menu.blog.entryList" /></a>
                        </c:if>
                        <a href="javascript:window.scroll(0,0)" class="link_btn"><bean:message key="common.backTop" /></a>
                        <a href="feedback.action?method=form&type=blog&id=${entryModel.id }" class="link_btn">举报不良信息</a> 
                                        &nbsp;
                                        <br class="clear" />
                    </div>
                    <c:if test="${not empty refEntries}">
                        <div class="correlation">
                            <div class="titles"><bean:message key="page.blog.entry.refEntries"/></div>
                            <ul class="clearfix">
                                <c:forEach items="${refEntries}" var="model">
                                    <li class="bullet_yellow"><a href="${model.url}">${model.label}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
                <div id="notes"></div>
            </div>
            <div id="area_right">
                <jsp:include page="./_rightWithoutTheme.jsp" flush="true" />
            </div>
        </div>
        <script>
            loadNotes(0);
        </script>
        <jsp:include page="../bottom.jsp" flush="true" />
        <script type="text/javascript" src="javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>