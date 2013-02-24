<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>装备分类&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
			$(function(){
				$(".mid_block_list li").mouseover(function(){
					$(this).addClass("lightgray_bg");
				}).mouseout(function(){
					$(this).removeClass("lightgray_bg")
				});
			});
            function checkForm(){
                J2bbCommon.removeformError("label");
                var label = $("#label").val();
                if(label==''){
                    J2bbCommon.formError("label","分类名不能为空","bottom");
                    return false;
                }
                $("#submitButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitButton").attr("disabled","true");
                return true;
            }
		</script>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;>&nbsp;装备分类
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                   <jsp:include flush="true" page="_userBar.jsp" />
                    <jsp:include page="../../_adBlockMini.jsp"/>
                </div>
                <div id="mid_column" class="fright">
                 <jsp:include flush="true" page="./_info.jsp" />
                 <jsp:include page="../../ad/_raywowAdBlock.jsp" flush="true"/>
                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="cat"/>
                    </jsp:include>
                    <div class="mid_block">
                    <br/>
                    <div >
                    &nbsp;&nbsp;
                     <form action="shopCategory.action?method=save" method="post" onsubmit="return checkForm();">
                            <input type="hidden" name="id" id="id" />
                            <ul id="share_form" class="clearfix">
                                <li>新增分类&nbsp;<input type="text" class="shareInput" name="label" id="label" value="" /></li>
                                <li><input type="submit" class="input_btn" name="submitButton" id="submitButton" value="提交" /></li>
                            </ul>
                        </form>
                    </div>
                    <br/>
                        <c:if test="${not empty modelList}">
                            <pg:pager items="${count}" url="shopCategory.action" index="center"
                                      maxPageItems="15" maxIndexPages="6"
                                      isOffset="<%=false%>"
                                      export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                      scope="request">
                                <pg:param name="method" />
                                <pg:param name="type" />
                                <pg:index><br class="clear" />
                                    <div class="pageindex nborder">
                                        <jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                               <div id="equipment_latest_list" class="tabswrap">
								<pg:param name="method" />
                                <ul class="artList">
                                    <c:forEach items="${modelList}" var="model" varStatus="status">
                                        <li class="clearfix">
                                            <div class="fleft">
                                                <span class="color_orange">${model.category.label }</span>
                                                &nbsp;&nbsp;
                                                <span class="color_gray">(${model.cnt })</span>
                                            </div>
                                            <div class="fright color_gray">
                                                <a href="shopCategory.action?method=up&id=${model.category.id }"><bean:message key="page.common.button.up"/></a>&nbsp;|
                                                <a href="shopCategory.action?method=off&id=${model.category.id }"><bean:message key="page.common.button.down"/></a>&nbsp;|
                                                <a href="shopCategory.action?method=off&id=${model.category.id }">访问</a>&nbsp;|
                                                <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }shopCategory.action?method=delete&cid=${model.category.id }');return false;"><bean:message key="page.common.button.delete"/></a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
							</div>
                            </pg:pager>
                        </c:if>
                </div></div>
            </div><div id="area_right">
                <jsp:include page="_right.jsp" flush="true" />
        </div></div>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>