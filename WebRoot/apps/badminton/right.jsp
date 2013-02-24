<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<%-- 
<div class="state" id="epcomment">
	<div class="titles" style="color: #f70">
		旺铺推荐
	</div>
	<div class="ew">
		<a class="rss" href="http://www.ctba.cn/group/shop-rank">MORE</a>
	</div>
	<div>
		<img src="images/nike-air-stab-id-21-mercer.jpg" alt="nike air stab" />
		<span> </span>
	</div>
</div>
--%>
<div class="state">
	<div class="titles" onclick="$('#hot_group').toggle('slow');">
		羽球小组
	</div>
<ul class="hot_group clearfix" id="hot_group">
	<li>
		<a class="stars_border" href="group/ymq"><img
				src="http://static.ctba.cn/files/sized/2009-11-24/bG9nbw==_03_30_13_271_48.gif" /><span
			class="group_name">装备团购</span>
		</a>
	</li>
	<li>
		<a class="stars_border" href="group/pengpu"><img
				src="http://static.ctba.cn/files/sized/2009-07-10/Mg==_01_05_41_580_48.gif" /><span
			class="group_name">鸿羽俱乐部(彭浦羽球)</span>
		</a>
	</li>
	<li>
		<a class="stars_border" href="group/badminton"><img
				src="http://static.ctba.cn/files/sized/2009-03-01/MTIxMDU4ODUzNl85NTU2NjYwMA==_16_03_32_578_48.jpg" /><span
			class="group_name">CT羽球党</span>
		</a>
	</li>
</ul>
</div>

<logic:notEmpty name="equipmentList">
	<div class="state">
		<div class="titles" onclick="$('#hot_group').toggle('slow');">
			装备团购(HOT!)
		</div>
		<ul class="hot_group clearfix" id="hot_group">
			<logic:iterate id="model" name="equipmentList">
				<li>
					<a class="stars_border" href="equipment/${model.id }"><img
							src="<community:img value="${model.image }" type="sized" width="80" />"
							width="48" height="48" /><span class="group_name">${model.name
							}</span> </a>
				</li>
			</logic:iterate>
		</ul>
	</div>
</logic:notEmpty>


<div class="adsenes_right_block">
	<script type="text/javascript"><!--
		google_ad_client = "pub-3911382285138100";
		/* 180x150, 创建于 09-2-23 */
		google_ad_slot = "3404358666";
		google_ad_width = 180;
		google_ad_height = 150;
		//-->
	</script>
	<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
</div>
<div class="state">
	<ul class="infobar">
		<li class="icons_feed">
			<a href="rss.shtml" target="_blank">订阅本站</a>
		</li>
	</ul>
	<hr size="1" color="#eeeeee" />
	<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
</div>