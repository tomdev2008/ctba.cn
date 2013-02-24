<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="menu.blog.catList" />&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
        <script type="text/javascript">
            function updateCat(cid,name,tags){
                $("#name").val(name);
                $("#cid").val(cid);
            }
            function checkForm(){
                J2bbCommon.removeformError("name");
                var name = $("#name").val(name);
                if(name==''){
                    J2bbCommon.formError("name","<bean:message key="page.blog.category.error.name"/>");
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;>&nbsp;<a href="bg.action?method=view&username=<g:url value="${model.author }"/>">${blogModel.name}</a>&nbsp;>&nbsp;<bean:message key="menu.blog.catList" />
                    </div>
                    <div class="fright">
                        <c:if test="${not empty systemMsg}">
                            <div id=systemMsg class="message_error">
                                ${systemMsg }
                            </div>
                        </c:if>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange"><b><bean:message key="menu.blog.catList" /></b></h2>
                        <bean:message key="page.blog.category.hint"/>
                        <form action="cat.action?method=save" method="post" onsubmit="return checkForm();">
                            <input type="hidden" name="cid" id="cid" />
                            <ul id="share_form" class="clearfix">
                                <li><b><bean:message key="blog.category.name" /></b>&nbsp;<input type="text" class="shareInput" name="name" id="name" value="" /></li>
                                <li><input type="submit" class="input_btn" name="submitButton" id="submitButton" value="<bean:message key="page.blog.category.button.submit"/>" /></li>
                            </ul>
                        </form>
                    </div>
                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="category"/>
                    </jsp:include>
                    <c:if test="${not empty models}">
                        <div class="mid_block">
                            <pg:pager items="${count}" url="cat.action" index="center"
                                      maxPageItems="15"
                                      maxIndexPages="6" isOffset="<%=false%>"
                                      export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                      scope="request">
                                <pg:param name="method" />
                                <ul class="artList">
                                    <c:forEach items="${models}" var="model" varStatus="status">
                                        <li class="clearfix">
                                            <div class="fleft">
                                                <span class="color_orange">${model.name }</span>
                                            </div>
                                            <div class="fright color_gray">
                                                <a href="cat.action?method=up&cid=${model.id }"><bean:message key="page.common.button.up"/></a>&nbsp;|
                                                <a href="cat.action?method=off&cid=${model.id }"><bean:message key="page.common.button.down"/></a>&nbsp;|
                                                <a href="javascript:updateCat('${model.id }','${model.name }','${model.tags }');"><bean:message key="page.common.button.edit"/></a>&nbsp;|
                                                <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }cat.action?method=delete&cid=${model.id }');return false;"><bean:message key="page.common.button.delete"/></a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <pg:index>
                                    <div class="pageindex nborder">
                                        <jsp:include flush="true" page="../common/jsptags/simple_15.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                            </pg:pager>
                        </div>
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