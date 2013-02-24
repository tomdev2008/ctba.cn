<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
        <title><bean:message key="page.gallery.common.title"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			$(function(){
				$("#tabs_gray li.tlink a").click(function(){
					$("#tabs_gray li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});
			});
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="app" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.gallery.common.title"/>
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_logo.jsp" />
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
				
				<jsp:include page="../ad/_raywowAdBlock.jsp" />
				
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
                            <li class="current tlink"><a name="tab_hot_gallery"><bean:message key="page.gallery.common.hotGallery"/></a></li><li class="normal tlink"><a name="tab_hot_photo"><bean:message key="page.gallery.common.hotPhoto"/></a></li><c:if test="${not empty __sys_username}"><li class="normal tlink"><a name="tab_my_galleries"><bean:message key="page.gallery.common.myNewGallery"/></a></li><li class="normal tlink"><a name="tab_my_comments"><bean:message key="page.gallery.common.myComment"/></a></li></c:if>
						</ul>
					</div>
					<div id="tab_hot_gallery" class="tabswrap">
						<c:if test="${not empty hotGalleries}">
							<div class="mid_block">
								<ul id="album_list">
									<c:forEach items="${hotGalleries}" var="model" varStatus="status">
										<li class="clearfix">
											<div class="fleft album_cover center">
												<a class="blog_border_large" href="album/${model.gallery.id }">
												<img src="<com:img value="${model.gallery.face }" type="default" />" alt="${model.gallery.description }" /> </a>
											</div>
											<div class="fleft album_intro orangelink">
												<h2 class="color_orange">
													<strong><a href="album/${model.gallery.id }">${model.gallery.name }</a></strong>
												</h2>
												<br />
												<h3>
													<com:topic value="${model.gallery.description }" />
												</h3>
												<br />
												<span class="color_gray">
												<com:date limit="10" start="0" value="${model.gallery.updateTime }" /> 于 ${model.gallery.place }<br /> 共有 <span class="font_mid">${model.cnt }</span> 张照片&nbsp;</span>
											</div>
										</li>
									</c:forEach>
									<li class="clearfix" >
                                        <a class="rss" href="img.shtml?method=galleryList"><bean:message key="page.gallery.common.moreGallery"/></a>
									</li>
								</ul>
							</div>
						</c:if>
					</div>
					<div id="tab_my_galleries" class="hide tabswrap">
						<c:if test="${not empty myGalleries}">
							<div class="mid_block">
								<ul id="album_list">
									<c:forEach items="${myGalleries}" var="model" varStatus="status">
										<li class="clearfix">
											<div class="fleft album_cover center">
												<a class="blog_border_large" href="album/${model.gallery.id }">
												<img src="<com:img value="${model.gallery.face }" type="default" />" alt="${model.gallery.description }" /> </a>
											</div>
											<div class="fleft album_intro orangelink">
												<h2 class="color_orange">
													<b><a href="album/${model.gallery.id }">${model.gallery.name }</a> </b>
												</h2>
												<br />
												<h3>
													<com:topic value="${model.gallery.description }" />
												</h3>
												<br />
												<span class="color_gray">
													<com:date limit="10" start="0" value="${model.gallery.updateTime }" /> 于 ${model.gallery.place }<br /> 共有 <span class="font_mid">${model.cnt }</span> 张照片&nbsp;
													<a class="bluelink" href="img.shtml?method=galleryForm&id=${model.gallery.id }">修改</a>
													<span class="font_mid">/</span>
													<a class="bluelink" href="multiImgUpload.shtml?method=form&galleryId=${model.gallery.id }">上传</a>
													<span class="font_mid">/</span>
													<a href="javascript:void(0);" class="bluelink" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath }img.shtml?method=deleteGallery&id=${model.gallery.id }');return false;">删除</a>
												</span>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
					<div id="tab_my_comments" class="hide tabswrap">
						<c:if test="${not empty myCommentList}">
							<c:forEach items="${myCommentList}" var="model" varStatus="index">
								<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index.index % 2 == 1}">lightgray_bg</c:if>">
									<li class="col1-5"> </li>
									<li class="col20-5">
										<a href="album/photo/${model.imageModel.id}">
											<img src="<community:img value="${model.imageModel.path}" type="mini"/>" width="16" height="16" class="userFace_border" alt="${model.imageModel.name}" align="absmiddle" />
										</a>&nbsp;&nbsp;
									</li>
									<li class="col63" style="overflow: hidden">
										<a href="album/photo/${model.imageModel.id}">
											<com:limit maxlength="13" value="${model.body}" />
										</a>
									</li>
									<li class="col15 font_mid color_gray">
										<community:date value="${model.updateTime }" start="5" limit="16" />
									</li>
								</ul>
							</c:forEach>
						</c:if>
					</div>
					<div id="tab_hot_photo" class="hide tabswrap clearfix">
						<c:if test="${not empty hotImages}">
							<c:forEach items="${hotImages}" var="model" varStatus="index">
								<div class="group_photo_wrap">
									<div class="group_photo_pic">
										<a class="photo" href="album/photo/${model.id }">
											<img src="<community:img value="${model.path }" type="default"/>" title="${model.name}" />
										</a>
									</div>
									<div class="group_photo_info">
										<span class="color_orange">
											<g:limit maxlength="25" value="${model.name}" />
										</span>
										<br />
										<span class="color_gray">
											<bean:message key="page.gallery.photo.createTime"/>：<g:date limit="11" start="0" value="${model.createTime }" /><br />
                                            <bean:message key="page.gallery.photo.author"/>：<a href="userpage/<g:url value="${model.username }"/>">${model.username }</a>
										</span>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
			<div id="area_right">
				<logic:notEmpty name="galleries">
					<div class="state">
						<div class="titles">
							<bean:message key="page.gallery.common.newGallery"/>
						</div>
						<ul class="hot_group clearfix" id="hot_group">
							<logic:iterate id="model" name="galleries">
								<li>
									<a class="stars_border" href="album/${model.gallery.id }">
										<img src="<community:img value="${model.gallery.face }" type="sized" width="80" />" width="48" height="48" /><span class="group_name">${model.gallery.name }</span>
									</a>
								</li>
							</logic:iterate>
						</ul>
					</div>
				</logic:notEmpty>
				<jsp:include page="right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>