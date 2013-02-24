<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<div id="top_info_wrapper" class="mid_block top_notice clearfix">
	<div id="top_info_pic" class="fleft">
		<img src="<community:img value="${shopModel.logo }" type="default" />" width="80" />
	</div>
	<div id="top_info_text" class="fleft">
		<h1 class="color_orange orangelink">
			<b><a href="userpage/<g:url value="${shopModel.username }"/>">${shopModel.username }</a>&nbsp;的店铺&nbsp;-&nbsp;<a href="equipment/shop/${shopModel.id}">${shopModel.name }</a></b>
		</h1>
		<h3>
			<community:topic value="${shopModel.description}" />
		</h3>
		<ul class="clearfix color_gray">
            <li class="head">主营：${shopModel.mainBiz }</li>
		</ul>
		<ul class="clearfix color_gray">
            <li class="head">装备：${shopModel.equipmentCnt }</li>
			<li class="font_mid">|</li>
			<li>更新：<span class="font_mid"><g:date limit="11" start="0" value="${shopModel.updateTime }" /></span></li>
			<li class="font_mid">|</li>
			<li>点击：<span class="font_mid">${shopModel.hits }</span></li>
		</ul>
		
		<c:if test="${not empty __request_shop}">
		<ul class="clearfix color_gray orangelink">
			<li class="head">操作：
			<li><a href="equipment.shtml?method=form">上传装备</a></li>
			<li><a href="shopCategory.action?method=list">管理分类</a></li>
			<li><a href="equipment.shtml?method=manageList">装备列表</a></li>
			<li><a href="shop.action?method=form">修改信息</a></li>
		</ul>
		</c:if>
	</div>
</div>
