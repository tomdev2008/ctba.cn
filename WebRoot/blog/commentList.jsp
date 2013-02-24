<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="menu.blog.commentList" />&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;>&nbsp;<a href="bg.action?method=view&username=<g:url value="${model.author }"/>">${blogModel.name}</a>&nbsp;&gt;&nbsp;<bean:message key="menu.blog.commentList" />
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlock.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange">
                            <b><bean:message key="menu.blog.commentList" /></b>
                        </h2>
                        <bean:message key="page.blog.comment.hint"/>
                    </div>
                    <div class="mid_block lightgray_bg">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="comment"/>
                        </jsp:include>
                    </div>
                    <c:if test="${not empty commentMapList}">
                        <pg:pager items="${count}" url="com.action" index="center"
                                  maxPageItems="15" maxIndexPages="6"
                                  isOffset="<%=false%>"
                                  export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                  scope="request">
                            <pg:param name="method"/>
                            <div class="mid_block top_blank_wide clearfix">
                                <c:forEach items="${commentMapList}" var="model" varStatus="status">
                                    <div class="reply_list clearfix">
                                        <div class="reply_user">
                                            <a href="u/${model.author.userId }"><img src="<com:img value="${model.author.userFace}" type="default"/>" class="userFace_border" width="32" align="absmiddle" /></a>
                                        </div>
                                        <div id="reply_info" style="width:200px" class="linkblue_b">
                                            <a href="u/${model.author.userId }">${model.author.userName }</a>&nbsp;<span class="color_gray"><com:ipData value="${model.comment.ip }"/></span>
                                        </div>
                                        <div class="reply_date">
                                            <div class="fleft">
                                                <span class="font_small color_gray">${model.comment.updateTime }&nbsp;|</span>
                                            </div>
                                            <ul class="opt2 fleft">
                                                <li>
                                                    <span class="opt_arrow"></span>
                                                    <ul>
                                                        <li>
                                                            <a target="_blank" href="blog/entry/${model.comment.blogBlogentry.id }"><bean:message key="page.blog.comment.origin"/></a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete"/>','${basePath }com.action?method=delete&cid=${model.comment.id }');return false;"><bean:message key="page.common.button.delete"/></a>
                                                        </li>
                                                        <li><a href="javascript:window.scroll(0,0)"><bean:message key="page.common.button.backToTop"/></a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </div>
                                        <div id="reply_content_mid">
                                            <p>
                                                <com:topic value="${model.comment.body }"/>
                                            </p>
                                            <p style="border-top:1px solid #f7f7f7;padding-top:3px;margin-top:5px">
                                                <span class="color_orange"><bean:message key="page.blog.comment.origin"/>: </span><a href="blog/entry/${model.comment.blogBlogentry.id }" target="_blank"><comm:limit value="${model.comment.blogBlogentry.title}" maxlength="20" /></a>
                                            </p>
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
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>