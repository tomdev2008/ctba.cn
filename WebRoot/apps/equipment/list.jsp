<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>
          <c:if test="${param.type eq 'hot'}">热门装备</c:if><c:if test="${not (param.type eq 'hot')}">最新装备</c:if>&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
			$(function(){
				$(".mid_block_list li").mouseover(function(){
					$(this).addClass("lightgray_bg");
				}).mouseout(function(){
					$(this).removeClass("lightgray_bg")
				});
			});
		</script>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;>&nbsp;<c:if test="${param.type eq 'hot'}">热门装备</c:if><c:if test="${not (param.type eq 'hot')}">最新装备</c:if>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="_userBar.jsp"></jsp:include>
                    <jsp:include flush="true" page="_compShops.jsp" />
                    <jsp:include page="../../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <jsp:include flush="true" page="_types.jsp" />
                    <c:if test="${param.type eq 'hot'}"> <jsp:include page="_tab.jsp" flush="true"><jsp:param name="currTab" value="hot"/></jsp:include></c:if>
                    <c:if test="${not (param.type eq 'hot')}"> <jsp:include page="_tab.jsp" flush="true"><jsp:param name="currTab" value="new"/></jsp:include></c:if>
                    <div class="mid_block">
                        <c:if test="${not empty models}">
                            <pg:pager items="${count}" url="equipment.shtml" index="center"
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
								<ul class="mid_block_list">
									<c:forEach items="${models}" var="model" varStatus="status">
										<li>
											<div class="clearfix">
												<div class="equipment_info">
													<div class="digg_wrap">
														<span id="group_digg_${model.equipment.id }">${model.voteScore }</span>
													</div>
													<c:if test="${(not empty model.equipment.shopId) and (model.equipment.shopId >0)}">
													<div class="shop_flag">
														<a href="equipment/${model.equipment.id }"></a>
													</div>
													</c:if>
												</div>
												<div class="mid_block_text clearfix">
													<h2>
														<strong><a href="equipment/${model.equipment.id }">${model.equipment.name }</a></strong>
													</h2>
													<p class="color_darkgray">
														<com:topic value="${model.equipment.discription }" />
													</p>
													<p class="equipment_items">
														<img src="images/icons/e_comment.jpg" alt="评论/浏览" align="absmiddle" />&nbsp;<span class="font_mid color_orange">${model.commentCnt}</span>&nbsp;<span class="font_mid color_gray">/&nbsp;${model.equipment.hits }</span>&nbsp;
														<img src="images/icons/e_favicon.jpg" alt="关注" align="absmiddle" />&nbsp;<span class="font_mid color_orange">${model.refCnt}</span>&nbsp;<span class="color_gray">人关注</span>&nbsp;
														<img src="<com:img value="${model.author.userFace}" type="mini"/>" height="16" width="16" alt="${model.author.userName}" align="absmiddle" class="userFace_border" />&nbsp;<a href="u/${model.author.userId }" class="bluelink">${model.equipment.username}</a>&nbsp;<span class="color_gray">发布于</span>&nbsp;<span class="color_orange"><community:formatTime time="${model.equipment.createTime }" /></span>
													</p>
												</div>
												<div class="equipment_photo">
													<a href="equipment/${model.equipment.id }">
														<img src="<com:img value="${model.equipment.image }" type="default" />" width="80" />
													</a>
												</div>
											</div>
											<%--<a href="u/${model.author.userId }">${model.equipment.username}</a>&nbsp; <a href="equipment.shtml?method=list&wrap-uid=${model.author.userId }">--%>
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