<%-- NOT IN USE --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			$(function(){
				$("#tabs_gray li.tlink a").click(function(){
					$("#tabs_gray li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});
				$(".mid_block_list li").mouseover(function(){
					$(this).addClass("lightgray_bg");
				}).mouseout(function(){
					$(this).removeClass("lightgray_bg")
				});
			});
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="./head.jsp"/>
		<div id="wrapper">
			<div id="area_left">
			 <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>
                    </div>
                </div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_userBar.jsp" />
					<jsp:include flush="true" page="_compShops.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					 <jsp:include page="../../ad/_raywowAdBlock.jsp" flush="true"/>
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="tlink current"><a href="javascript:void(0)" name="equipment_latest_list">最新装备</a></li><li class="tlink normal"><a href="javascript:void(0)" name="equipment_hot_list">热门装备</a></li><c:if test="${not empty __sys_username}"><li class="normal" ><a href="equipment.shtml?method=list">我的装备</a></li><li class="normal" ><a href="equipment.shtml?method=form">上传装备</a></li></c:if>
						</ul>
					</div>
					<div class="mid_block">
						<div class="mid_block">
							<div id="equipment_latest_list" class="tabswrap">
								<ul class="mid_block_list">
									<c:forEach items="${newModels}" var="model" varStatus="status">
										<li>
											<div class="clearfix">
												<div class="equipment_info">
													<div class="digg_wrap">
														<span id="group_digg_${model.equipment.id }">${model.equipment.voteScore }</span>
													</div>
													<div class="shop_flag">
														<a href="equipment/${model.equipment.id }"></a>
													</div>
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
							<div id="equipment_hot_list" class="tabswrap hide">
								<ul class="mid_block_list">
									<c:forEach items="${hotModels}" var="model" varStatus="status">
										<li>
											<div class="clearfix">
												<div class="digg_wrap">
													<span id="group_digg_${model.equipment.id }">${model.equipment.voteScore }</span>
												</div>
												<div class="mid_block_text clearfix">
													<h2>
														<strong><a href="equipment/${model.equipment.id }">${model.equipment.name }</a></strong>
													</h2>
													<p class="color_gray">
														<com:topic value="${model.equipment.discription }" />
													</p>
													<p class="equipment_items">
														<img src="images/icons/e_comment.jpg" alt="评论/浏览" align="absmiddle" />&nbsp;<span class="font_mid color_orange">${model.commentCnt}</span>&nbsp;<span class="font_mid color_gray">/&nbsp;${model.equipment.hits }</span>&nbsp;
														<img src="images/icons/e_favicon.jpg" alt="关注" align="absmiddle" />&nbsp;${model.refCnt}人关注&nbsp;
														<img src="<com:img value="${model.author.userFace}" type="mini" />" height="16" width="16" alt="${model.author.userName}" align="absmiddle" class="userFace_border" />&nbsp;<a href="u/${model.author.userId }" class="bluelink">${model.equipment.username}</a>&nbsp;<span class="color_gray">发布于</span>&nbsp;<span class="color_orange"><community:formatTime time="${model.equipment.createTime }" /></span>
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
						</div>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="./_right.jsp"></jsp:include>
				<div class="state">
					<ul class="infobar">
						<li class="icons_feed">
							<a href="rss.shtml" target="_blank"><bean:message key="rss.feed" />
							</a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../../bottom.jsp" flush="true" />
	</body>
</html>