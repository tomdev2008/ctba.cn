<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<h1 id="equipment_logo" class="mb10">
	<a href="equipment/" title="扯谈装备秀">扯谈装备秀</a>
</h1>
<c:if test="${empty __sys_username}">
	<div class="side_block radius_all">
		<div class="side_title">
			<h2>
				<strong>我的装备</strong>
			</h2>
		</div>
		<div class="side_list">
			<div class="equipment_state orangelink lightyellow_bg radius_all">
				扯谈装备秀目前共
				<br />
				有装备&nbsp;<span class="color_orange">${wareCntAll }</span>&nbsp;件
				<br />
				今日添加&nbsp;<span class="color_orange">${wareCntToday }</span>&nbsp;件
				<br />
				请您&nbsp;<a href="javascript:J2bbCommon.showLoginForm();">登录</a>&nbsp;或&nbsp;<a href="register">注册</a>
			</div>
			<div class="mb3 graylink">
				<a href="javascript:J2bbCommon.showLoginForm();" title="上传装备">
					<img src="images/e_upload.jpg" alt="上传装备" />
				</a>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${not empty __sys_username}">
	<div class="side_block radius_all">
		<div class="side_title">
			<h2>
				<strong>我的装备</strong>
			</h2>
		</div>
		<div class="side_list">
			<ul>
				<li style="background: url(images/icons/tag_purple.gif) no-repeat">
					<h2>
						<a href="equipment.shtml?method=favList&type=1">关注</a>
						<span class="font_mid color_gray">(${refCntLevel1})</span>
					</h2>
				</li>
				<li style="background: url(images/icons/tag_yellow.gif) no-repeat">
					<h2>
						<a href="equipment.shtml?method=favList&type=2">想收</a>
						<span class="font_mid color_gray">(${refCntLevel2})</span>
					</h2>
				</li>
				<li style="background: url(images/icons/tag_green.gif) no-repeat">
					<h2>
						<a href="equipment.shtml?method=favList&type=3">已收</a>
						<span class="font_mid color_gray">(${refCntLevel3})</span>
					</h2>
				</li>
				<li style="background: url(images/icons/tag_red.gif) no-repeat">
					<h2>
						<a href="equipment.shtml?method=manageList">我的</a>
						<span class="font_mid color_gray">(${userWareCnt})</span>
					</h2>
				</li>
			</ul>
			<div class="equipment_state lightyellow_bg radius_all">
				扯谈装备秀目前共
				<br />
				有装备&nbsp;<span class="color_orange">${wareCntAll }</span>&nbsp;件
				<br />
				今日添加&nbsp;<span class="color_orange">${wareCntToday }</span>&nbsp;件
			</div>
			<div class="mb3 graylink">
				<a href="equipment.shtml?method=form" title="上传装备">
					<img src="images/e_upload.jpg" alt="上传装备" />
				</a>
			</div>
			<c:if test="${not empty __request_shop}">
			<div class="mb3 graylink">
				<a target="_blank" href="equipment/shop/${__request_shop.id}" title="我的店铺">
					<img src="images/equipment_myshop.jpg" alt="我的店铺" />
				</a>
			</div>
			</c:if>
			<%--
			<c:if test="${empty __request_shop}">
			<div class="mb3 graylink">
				<a href="http://www.ctba.cn/group/shop-rank" target="_blank" title="我要开店">
					<img src="images/equipment_openshop.jpg" alt="我要开店" />
				</a>
			</div>
			</c:if>	--%>
		</div>
	</div>
</c:if>
