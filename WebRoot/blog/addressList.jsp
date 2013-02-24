<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="menu.blog.vest" />&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
        <script>
            function getContent(){
                $("#getContentButton").val("<bean:message key="page.blog.vest.info.updating"/>");
                document.getElementById("getContentButton").disabled=true;
                $.ajax({ url: "bv.action",
                    type:"get",
                    data: "method=rss&all=true",
                    success:function(msg){
                        $("#getContentButton").val("<bean:message key="page.blog.vest.info.update"/>");
                        document.getElementById("getContentButton").disabled=false;
                        window.location.reload();
                    }
                });
            }
            function updateVest(aid,url){
                $("#url").val(url);
                $("#aid").val(aid);
            }
            function checkForm(){
                J2bbCommon.removeformError("url");
                var url = $("#url").val();
                if(url==''){
                    J2bbCommon.formError("url","<bean:message key="page.blog.vest.error.url"/>");
                    return false;
                }
                $("#submitButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitButton").attr("disabled，disabled");
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;>&nbsp;<a href="bg.action?method=view&username=<g:url value="${model.author }"/>">${blogModel.name}</a>&nbsp;&gt;&nbsp;<bean:message key="menu.blog.vest" />
                    </div>
                    <div class="fright">
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange"><b><bean:message key="menu.blog.vest" /></b></h2>
                        <bean:message key="page.blog.vest.hint"/>
                        <form action="bv.action?method=save" method="post" onsubmit="return checkForm();">
                            <input type="hidden" name="aid" id="aid" />
                            <ul id="share_form" class="clearfix">
                                <li><b><bean:message key="blog.vest.rss" /></b>&nbsp;<input type="text" class="shareInput" name="url" id="name" value="" /></li>
                                <li><input type="submit" class="input_btn" name="submitButton" id="submitButton" value="<bean:message key="page.blog.vest.button.submit"/>" /></li>
                                <c:if test="${not empty models}">
                                    <li><input class="input_btn" type="button" value="<bean:message key="blog.vest.update" />" id="getContentButton" onclick="getContent();" /></li>
                                </c:if>
                            </ul>
                        </form>
                    </div>

                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="vest"/>
                    </jsp:include>

                    <div class="mid_block">
                        <c:if test="${not empty systemMsg}">
                            <div id=systemMsg class="message_error">
                                ${systemMsg }
                            </div>
                        </c:if>
                        <c:if test="${not empty models}">
                            <pg:pager items="${count}" url="bv.action" index="center"
                       maxPageItems="15" maxIndexPages="6"
                       isOffset="<%=false%>"
                       export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                       scope="request">
                                <ul class="artList">
                                    <c:forEach items="${models}" var="model" varStatus="status">
                                        <li class="clearfix">
                                            <div class="fleft">
                                                ${model.url }&nbsp;&nbsp;<bean:message key="page.blog.vest.info.updatedCnt" arg0="${model.gotEntriesCnt}"/>
                                            </div>
                                            <div class="fright color_gray">
                                                <a href="javascript:updateVest('${model.id }','${model.url }');"><bean:message key="page.common.button.edit"/></a>&nbsp;|
                                                <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }bv.action?method=delete&aid=${model.id }');return false;"><bean:message key="page.common.button.delete"/></a>
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