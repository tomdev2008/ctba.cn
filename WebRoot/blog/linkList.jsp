<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="menu.blog.linkList" />&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
        <script type="text/javascript">
            function updateLink(lid,title,url){
                $("#title").val(title);
                $("#lid").val(lid);
                $("#url").val(url);
            }
            function checkForm(){
                J2bbCommon.removeformError("title");
                J2bbCommon.removeformError("url");
                var title=$("#title").val();
                var url=$("#url").val();
                if(title==''){
                    J2bbCommon.formError("title","<bean:message key="page.blog.link.error.name"/>");
                    return false;
                }
                if(url==''){
                    J2bbCommon.formError("url","<bean:message key="page.blog.link.error.url"/>");
                    return false;
                }
                $("#submitButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitButton").attr("disabled","true");
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;>&nbsp;<a href="bg.action?method=view&username=<g:url value="${model.author }"/>">${blogModel.name}</a>&nbsp;&gt;&nbsp;<bean:message key="menu.blog.linkList" />
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange"><b><bean:message key="menu.blog.linkList" /></b></h2>
                       <bean:message key="page.blog.link.hint"/>
                        <form action="bl.action?method=save" method="post" onsubmit="return checkForm();">
                            <input type="hidden" name="lid" id="lid" />
                            <ul id="share_form" class="clearfix">
                                <li><b><bean:message key="page.blog.link.label.name"/>:</b>&nbsp;<input type="text" class="shareInput" name="title" id="title" value="" /></li>
                                <li><b><bean:message key="page.blog.link.label.url"/>:</b>&nbsp;<input type="text" class="shareInput font_mid" name="url" id="url" value="http://" /></li>
                                <li><input type="submit" class="input_btn" name="submitButton" id="submitButton" value="<bean:message key="page.blog.link.button.submit"/>" /></li>
                            </ul>
                        </form>
                    </div>
                    <div class="mid_block lightgray_bg">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="link"/>
                        </jsp:include>
                    </div>
                    <c:if test="${not empty links}">
                        <pg:pager items="${count}" url="bl.action" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                            <pg:param name="method"/>
                            <div class="mid_block">
                                <ul class="artList">
                                    <c:forEach items="${links}" var="model" varStatus="status">
                                        <li class="clearfix">
                                            <div class="fleft">
                                                <span class="color_orange">${model.title }</span>&nbsp;
                                                <span class="color_darkgray font_mid">${model.url }</span>
                                            </div>
                                            <div class="fright color_gray">
                                                <a href="bl.action?method=up&lid=${model.id }"><bean:message key="page.common.button.up"/></a>&nbsp;|
                                                <a href="bl.action?method=off&lid=${model.id }"><bean:message key="page.common.button.down"/></a>&nbsp;|
                                                <a href="javascript:updateLink('${model.id }','${model.title }','${model.url }');"><bean:message key="page.common.button.edit"/></a>&nbsp;|
                                                <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }bl.action?method=delete&lid=${model.id }');return false;"><bean:message key="page.common.button.delete"/></a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <pg:index>
                                <div class="pageindex nborder">
                                    <jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
                                </div>
                            </pg:index>
                        </pg:pager>
                    </c:if>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>