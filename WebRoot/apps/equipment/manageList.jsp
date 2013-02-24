<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>装备列表&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            function doCommend(id){
                $.get("${context }/shopCommend.action",
                { method:"save",eid:id,loadType:"ajax"},
                function(data){
                        $("#commendOp_"+id).html( "<a href=\"javascript:deleteCommend('"+id+"');\">取消推荐</a>");
                }
           		);
            }
             function deleteCommend(id){
                $.get("${context }/shopCommend.action",
                { method:"delete",eid:id,loadType:"ajax"},
                function(data){
                        $("#commendOp_"+id).html( "<a href=\"javascript:doCommend('"+id+"');\">推荐</a>");
                }
           		);
            }
        </script>
        </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;>&nbsp;装备列表
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="_userBar.jsp" />
                    <jsp:include page="../../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <jsp:include flush="true" page="./_info.jsp" />
                    <jsp:include page="../../ad/_raywowAdBlock.jsp" flush="true"/>
                    
                    <c:if test="${not empty categoryList}">
                    <com:select name="catSelect" selected="${param.category}" value="id" label="label" items="${categoryList }" onchange="location.href='equipment.shtml?method=manageList&category='+this.options[this.selectedIndex].value;"/>
                    &nbsp;&nbsp;
                    <a class="link_btn" href="equipment.shtml?method=manageList&category=">全部</a>
                    </c:if>
                    
                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="list"/>
                    </jsp:include>
                    <div class="mid_block">
                        <c:if test="${not empty models}">
                            <pg:pager items="${count}" url="equipment.shtml" index="center"
                                      maxPageItems="30" maxIndexPages="6"
                                      isOffset="<%=false%>"
                                      export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                      scope="request">
                                <pg:param name="method" />
                                <pg:param name="wrap-uid" />
                                <pg:index><br class="clear" />
                                    <div class="pageindex nborder">
                                        <jsp:include flush="true" page="../../common/jsptags/simple.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                                <table width="100%" cellspacing="0" cellpadding="0"	id="listable">
                                    <c:forEach items="${models}" var="model" varStatus="status">
                                        <tr
                                            <c:choose>
                                                <c:when test="${status.count%2==0}">
                                                    class="alternative "
                                                </c:when>
                                            </c:choose>>
                                            <td width="35%">
                                                <img height="16" width="16" src="<community:img value="${model.equipment.image }" type="mini" />"/>
                                                &nbsp;<a
                                                    href="equipment/${model.equipment.id }" alt="${model.equipment.name }">
                                                <community:limit maxlength="25" value="${model.equipment.name }" /> </a>
                                            </td>
                                            <td width="20%">
                                                <span class="font_small color_orange">${model.equipment.hits}点击/${model.refCnt}收藏/${model.commentCnt}评论
                                            </span></td>
                                            <td width="10%">
                                            
                                                  <c:if test="${(empty model.equipment.state) or (model.equipment.state eq 0)}">
                                                          <span class="font_small color_orange">待审批</span>
                                                  </c:if>
												 
                                                 <c:if test="${(not empty __request_shop) and (not empty model.equipment.state) and (model.equipment.state > 1)}">
                                                  <span class="font_small color_orange">
                                                  <c:if test="${(not empty model.equipment.shopId) and (model.equipment.shopId>0)}">
                                                          已上架
                                                  </c:if>
                                                  <c:if test="${(empty model.equipment.shopId) or (model.equipment.shopId<=0)}">
                                                          未上架
                                                  </c:if>
												  </span>
												 </c:if>
                                            </td>
                                            <td width="13%">
                                                <span class="font_small color_gray"> <g:date limit="11" start="0" value="${model.equipment.createTime }" /> </span>
                                            </td>
                                            <td width="27%">
                                            <a href="equipment.shtml?method=form&wid=${model.equipment.id }">编辑</a>&nbsp;
                                            <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('<bean:message key="page.common.button.delete.confirm"/>','${basePath }equipment.shtml?method=delete&wid=${model.equipment.id }');return false;">删除</a>
                                       &nbsp;<span id="commendOp_${model.equipment.id }">
                                        <c:if test="${(not empty model.equipment.shopId) and (model.equipment.shopId>0)}">
                                            <c:if test="${not empty model.shopCommend}">
                                             <a href="javascript:deleteCommend('${model.equipment.id }');">取消推荐</a>
                                            </c:if> 
                                            <c:if test="${empty model.shopCommend}">
                                             <a href="javascript:doCommend('${model.equipment.id }');">推荐</a>
                                            </c:if>      </c:if>             
                                            </span>
                                              </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </pg:pager>
                        </c:if>
                </div></div>
            </div><div id="area_right">
                <jsp:include page="_right.jsp" flush="true" />
        </div></div>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>