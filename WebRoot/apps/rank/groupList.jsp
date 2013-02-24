<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>小组排名&nbsp;-&nbsp;扯谈热榜&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
    </head>
    <body>
       <jsp:include flush="true" page="../../head.jsp">
		<jsp:param name="submenu" value="group" />
		</jsp:include>
		
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage"/></a>&nbsp;&gt;&nbsp;<a href="${context }/group/">扯谈小组</a>&nbsp;&gt;&nbsp;小组排名
                    </div>
                </div>
               <div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
				
				 <c:if test="${not empty groups}">
                            <pg:pager items="${count}" url="rank.action" index="center"
                                      maxPageItems="15" maxIndexPages="6"
                                      isOffset="<%=false%>"
                                      export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                                      scope="request">
                                <pg:param name="method" />
                                <pg:index><br class="clear" />
                                    <div class="pageindex nborder">
                                        <jsp:include flush="true" page="../../common/jsptags/simple_15.jsp"></jsp:include>
                                    </div>
                                </pg:index>
                               <div id="equipment_latest_list" class="tabswrap">
								<ul class="mid_block_list">
									<c:forEach items="${groups}" var="model" varStatus="status">
										<li>
											<div class="clearfix">
												<div class="equipment_info">
													<div class="digg_wrap">
														<span id="group_digg_${model.id }">${15*(currentPageNumber-1)+status.index+1 }</span>
													</div>
												</div>
												<div class="mid_block_text clearfix">
													<h2>
														<strong><a href="group/${model.id }">${model.name }</a></strong>
													</h2>
													<p class="color_darkgray">
														<comm:limit value="${model.discription}" maxlength="100" />
													</p>
													<p class="equipment_items">
														<img src="images/icons/e_comment.jpg" alt="话题/评论" align="absmiddle" />&nbsp;<span class="font_mid color_orange">${model.topicCnt}话题</span>&nbsp;<span class="font_mid color_gray">/&nbsp;${model.replyCnt }评论</span>&nbsp;
														<img src="images/icons/group_go.png" alt="加入" align="absmiddle" />&nbsp;<span class="font_mid color_orange">${model.userCnt}</span>&nbsp;<span class="color_gray">人加入</span>&nbsp;
													<img src="images/icons/lightbulb.jpg" alt="点击" align="absmiddle" />&nbsp;<span class="font_mid color_orange">${model.hits}</span>&nbsp;<span class="color_gray">点击</span>&nbsp;
													</p>
												</div>
												<div class="equipment_photo">
													<a href="group/${model.id }">
														<img src="<com:img value="${model.face }" type="default" />" width="80" />
													</a>
												</div>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
                            </pg:pager>
                        </c:if>
                
                 </div>
                 
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>