<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>${model.title }&nbsp;-&nbsp;${category.name}&nbsp;-&nbsp;<bean:message key="menu.news.navigate" /></title>
        <script type="text/javascript">

            //==============
            // 回复某楼的用户
            //==============
            function re(index,username){
                $("#para_reply_to").val(username);
                $("#topicContent").val("@"+username+" "+index+"#\n");
                $("#topicContent").focus();
            }
            //added by mockee 090114
            function maxWidth(){
                var maxWidth = parseInt($('#dc img').css('max-width'));
                $('#dc img').each(function(){
                    if ($(this).width() > maxWidth)
                        $(this).width(maxWidth);
                });
            }
            if (window.attachEvent) window.attachEvent("onload", maxWidth);

            function dig(id){
                $.get("${context }/news.shtml",
                { method:"good",nid:id,loadType:"ajax"},
                function(data){
                    if(data.indexOf("login")>=0){
                        J2bbCommon.showLoginForm();
                    }else{
                        $("#group_digg_"+id).html(data);
                    }
                }
            );
            }
            function checkForm(){
                var content = $("#topicContent").val();
                if(content.length>1000||content.length<1){
                    J2bbCommon.errorWithElement("errorMsg","<bean:message key="page.news.post.error.comment"/>");
                    return false;
                }
                $("#submitRe").attr("disabled","disabled");
                $("#submitRe").val("<bean:message key="page.common.button.submitting"/>");
                return true;
            }
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="news.shtml?method=indexPage"><bean:message key="menu.news.navigate" /></a>&nbsp;&gt;&nbsp;<a href="news/list/${category.id }">${category.name}</a>&nbsp;&gt;&nbsp;${model.title }
                    </div>
                    <div class="fright">
                   
                        <jsp:include page="../common/_share.jsp">
                            <jsp:param name="share_type" value="3"/>
                            <jsp:param name="share_id" value="${model.id}"/>
                            <jsp:param name="share_url" value="${basePath }news/${model.id}"/>
                        </jsp:include>
                         
                    </div>
                </div>
                <div class="left_block top_blank_wide radius clearfix">
                    <div class="group_digg gd_news">
                        <div class="diggnum" id="group_digg_${model.id }">${model.hitGood }</div>
                        <span class="font_small color_gray">diggs</span>
                        <div class="diggit" title="<bean:message key="page.news.diggIt"/>" onclick="dig('${model.id }');"><bean:message key="page.news.diggIt"/></div>
                    </div>
                    <div id="topicstuff" class="font_small color_orange">
                        &nbsp;
                    </div>
                    <div id="dw">
                        <div id="dt">
                            <div id="ctl"></div>
                            <div id="tl"></div>
                            <div id="ctr"></div>
                        </div>
                        <div id="nla"></div>
                        <div id="dt2" class="text_shadow">
                            <h1><b>${model.title }</b></h1>
                        </div>
                        <div id="dc3" class="orangelink">
                            ${model.content }
                        </div>
                        <div id="db">
                            <div id="cbl"></div>
                            <div id="bl"></div>
                            <div id="cbr"></div>
                        </div>
                    </div>
                    <div class="line_block color_gray clearfix">
                        <ul class="lo" style="float:right">
                            <c:if test="${not empty model.author}">
                                <li class="icons_user"><bean:message key="page.news.poster"/>:<a href="userpage/<com:url value="${model.author }"/>">${model.author }</a>&nbsp;|&nbsp;</li>
                            </c:if>
                            <li class="icons_calendar_view_day"><bean:message key="page.news.createTime"/>:<com:date value="${model.updateTime }" start="2" limit="16" />&nbsp;|&nbsp;</li>
                            <li class="icons_note"><bean:message key="page.news.hits"/>:${model.hits }&nbsp;|&nbsp;</li>
                            <li class="icons_comment"><bean:message key="page.news.commentCnt"/>:<span class="color_orange">${commentCnt }</span></li>
                            <li ><a href="feedback.action?method=form&type=news&id=${model.id }" class="link_btn color_orange ">举报</a> &nbsp;&nbsp;</li>
                            
                            
                        </ul>
                    </div>
                    <jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>
                </div>
                <div class="left_block top_blank_wide">
                    <logic:notEmpty name="comments">
                        <pg:pager items="${commentCnt}" url="news.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                            <pg:param name="method" />
                            <pg:param name="nid" />
                            <pg:index>
                                <div class="pageindex">
                                    <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                </div>
                            </pg:index>
                            <logic:iterate id="map" name="comments" indexId="index">
                                <div>
                                    <div class="reply_list clearfix">
                                        <div class="reply_user">
                                            <img src="<com:img value="${map.user.userFace }" type="sized" width="32" />" alt="${map.user.userName }" width="32" />
                                        </div>
                                        <div id="reply_info" class="linkblue_b">
                                            <a href="userpage/<com:url value='${map.user.userName}'/>" class="userName">${map.user.userName }</a>&nbsp;<span class="color_gray"><com:ipData value="${map.comment.ip }" /></span>
                                        </div>
                                        <div class="reply_date">
                                            <div class="fleft">
                                                <span class="font_small color_orange">${start+index+1 }/${commentCnt}</span>&nbsp;&nbsp;<span class="font_small color_gray"><com:date value="${map.comment.updateTime}" start="2" limit="16" /></span>
                                            </div>
                                            <ul class="opt2 fleft">
                                                <li>
                                                    <span class="opt_arrow"></span>
                                                    <ul>
                                                        <logic:equal value="true" name="map" property="isAuthor">
                                                            <li><a href='javascript:void(0);' onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }news.shtml?method=deleteComment&cid=${map.comment.id}');return false;"><bean:message key="page.news.comment.delete"/></a></li>
                                                        </logic:equal>
                                                        <c:if test="${not empty __sys_username}">
                                                            <li><a href="javascript:re(${15*(currentPageNumber-1)+index+1 },'${map.user.userName }');"><bean:message key="page.news.comment.reply"/></a>
                                                            </li></c:if>
                                                            <li><a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop"/></a></li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div id="reply_content">
                                                ${map.content}
                                            </div>
                                        </div>
                                    </div>
                            </logic:iterate>
                        </pg:pager>
                    </logic:notEmpty>
                    <jsp:include page="../_loginBlock.jsp"></jsp:include>
                </div>
                <logic:equal value="true" name="isUser">
                    <div class="title_bar"><bean:message key="page.news.comment.label"/>:${model.title }</div>
                    <div class="left_block top_blank_wide radius_top_none clearfix">
                        <div id="errorMsg"></div>
                        <div class="replyform">
                            <form name="topicForm" id="topicForm" action="news.shtml?method=saveComment" method="post" onsubmit="return checkForm();">
                                <div class="replyitems">
                                    <ul>
                                        <li><com:ubb /></li>
                                        <li>
                                            <textarea rows="5" cols="100" name="content" id="topicContent" class="topicContent" style="width:554px;height:150px"></textarea>
                                            <input type="hidden" name="para_reply_to" id="para_reply_to" />
                                            <input type="hidden" name="nid" value="${model.id}" />
                                        </li>
                                        <li><input class="input_btn btn_mt" name="submit" id="submitRe" type="submit" value="<bean:message key="page.news.comment.button"/>" /></li>
                                    </ul>
                                </div>
                            </form>
                        </div>
                        <div class="line_blocks"><img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.news.comment.hint"/></div>
                    </div>
                    <script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
                </logic:equal>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>