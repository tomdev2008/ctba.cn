<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%
            request.setAttribute("context", request.getContextPath());
            pageContext.setAttribute("context", request.getContextPath());
            String basePath = "";
            if (request.getServerName().contains("test.ctba.cn")) {
                basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            } else {
                //如果不是本地调试的话，不用加端口信息
                basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
            }
            request.setAttribute("basePath", basePath);
%>
<link href="<%=request.getParameter("themeDir")%>/css.css" rel="stylesheet" type="text/css" />
<div id="commentFrame" class="left_block top_blank_wide orangelink">
    <div class="pageindex">
        <div id="pgindex">
            <ul id="pg">
                <li><a href='javascript:prevPage(${start },${limit });' class="pg_normal">Prev</a></li>
                <li><a href='javascript:firstPage();' class="pg_current">${start + 1 }</a></li>
                <li><a href='javascript:nextPage(${start },${limit },${count });' class="pg_total">Next</a></li>
            </ul>
            <div id="pg_all">
                ${count} ITEMS / ${limit } PER PAGE
            </div>
        </div>
    </div>
    <c:if test="${not empty commentMapList}">
        <c:forEach items="${commentMapList}" var="cMap" varStatus="status">
            <c:if test="${not empty cMap.author}">
                <div class="reply_list clearfix">
                    <div class="reply_user">
                        <img src="<com:img value="${cMap.author.userFace}" type="sized" width="32" />" width="32" alt="${cMap.author.userName}" class="userFace_border" />
                    </div>
                    <div id="reply_info" class="linkblue_b">
                        <a href="u/${cMap.author.userId}">${cMap.author.userName}</a>
                    </div>
                    <div class="reply_date">
                        <span class="font_small color_gray">${cMap.comment.updateTime }</span>&nbsp;<span class="font_small color_orange orangelink"><a href="javascript:reNote('${cMap.comment.author}'<%--,'<com:limit maxlength="20" value="${cMap.comment.body}"/>'--%>)" title="回复此留言">回复</a></span>
                    </div>
                    <div id="reply_content">
                        <com:topic value="${cMap.comment.body }" />
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </c:if>
    <div class="line_block_b right darkgraylink">
       <a href="blog/${blogModel.id}">${blogModel.name}</a>&nbsp;|&nbsp;<bean:message key="page.blog.comment.commentCnt" arg0="${count }"/>
    </div>
    <br class="clear" />
    <jsp:include page="../_loginBlock.jsp"></jsp:include>
</div>
<c:if test="${not empty __sys_username}">
    <div class="left_block top_blank_wide radius_top_none radius">
        <div id="noteForm" class="replyform">
            <input type="hidden" id="authorName" value="${__sys_username }"/>
            <input type="hidden" name="para_reply_to" id="para_reply_to" value="${entryModel.author}"/>
            <ul>
                <li style="display:block;clear:both"><com:ubb/></li>
                <li style="display:block;clear:both"><textarea class="ftt" name="noteContent" id="topicContent"></textarea></li>
                <li style="display:block;clear:both">
                    <input type="button"  id="submitButton" onclick="submitNote();" class="input_btn btn_mt" value="提交留言" />
                </li>
            </ul>
        </div>
        <div class="line_blocks">
        </div>
    </div>
    <script type='text/javascript'>
        $(function() {
            $('.boxy').boxy();
        });
    </script>
</c:if>