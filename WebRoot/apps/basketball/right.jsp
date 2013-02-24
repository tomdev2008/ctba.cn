<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<div class="state">
	<jsp:include flush="true" page="../../_searchBlock.jsp"></jsp:include>
</div>
<logic:notEmpty name="groupsMap">
	<div class="state">
		<div class="titles" onclick="$('#hot_group').toggle('slow');">
			小组
		</div>
		<ul class="hot_group clearfix" id="hot_group">
			<logic:iterate id="group" name="groupsMap">
				<li>
					<a class="stars_border" href="group/${group.group.id }"><img
							src="<community:img value="${group.group.face }" type="sized" width="80" />"
							width="48" height="48" /><span class="group_name">${group.group.name
							}</span>
					</a>
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
	<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
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