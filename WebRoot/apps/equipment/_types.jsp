<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#sort_tab li a").click(function(){
			$("#sort_tab li").removeClass();
			$(this).parent("li").attr("class","current");
			$("ul.group_sort_detail").hide();
			$("#" + $(this).attr("name")).show();
			return false;
		});
	});
</script>
<c:if test="${not empty equipmentTypeList}">
	<div class="sort_wrap">
		<div class="group_sort">
			<ul id="s_type" class="group_sort_detail" style="width: 530px;">
				<c:forEach items="${equipmentTypeList}" var="model"
					varStatus="index">
					<li>
					<c:if test="${param.type eq model.index}"><b>${model.name}</b></c:if>
						<c:if test="${not (param.type eq model.index)}"><a href="equipmentSearch.action?method=search&type=${model.index }&name=<community:url value=" ${model.name }"/>">
						${model.name}
						</a></c:if>
						
						
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<%--<div class="sort_wrap">
		<div class="group_sort">
			<ul id="sort_tab">
				<span class="st_titile">装备分类</span>
				<li class="current"><a name="s1">鞋类</a></li>
				<li><a name="s2">服饰</a></li>
				<li><a name="s3">器械</a></li>
			</ul>
			<ul id="s1" class="group_sort_detail" style="width: 541px">
				<li class="sortall"><a href="equipmentSearch.action?method=search&type=1&name=wLrH8tCs">篮球鞋</a></li>
				<li><a href="equipmentSearch.action?method=search&type=3&name=1%2bPH8tCs">足球鞋</a></li>
				<li class="sortall"><a href="equipmentSearch.action?method=search&type=2&name=0%2FDDq8fy0Kw=">羽毛球鞋</a></li>
				<li><a href="equipmentSearch.action?method=search&type=6&name=zfjH8tCs">网球鞋</a></li>
				<li><a href="equipmentSearch.action?method=search&type=4&name=0bXBt9Cs">训练鞋</a></li>
				<li><a href="equipmentSearch.action?method=search&type=5&name=0N3P0NCs">休闲鞋</a></li>
				<li><a href="equipmentSearch.action?method=search&type=5&name=0N3P0NCs">慢跑鞋</a></li>
			</ul>
			<ul id="s2" class="group_sort_detail hide" style="display:none;width: 541px">
				<li><a href="equipmentSearch.action?method=search&type=7&name=x%2FK3%2Fg==">球服</a></li>
				<li class="orangelink"><a href="equipmentSearch.action?method=search&type=10&name=VND0">T恤</a></li>
				<li><a href="">瑜伽服</a></li>
				<li class="orangelink"><a href="equipmentSearch.action?method=search&type=11&name=tsy%2F4w==">裤裙</a></li>
				<li><a href="equipmentSearch.action?method=search&type=12&name=s7HB97f%2bys4=">潮流</a></li>
				<li><a href="equipmentSearch.action?method=search&type=8&name=sPzA4A==">包类</a></li>
				<li><a href="equipmentSearch.action?method=search&type=9&name=1Mu2r7ukvt8=">护具</a></li>
			</ul>
			<ul id="s3" class="group_sort_detail" style="display:none;width: 541px">
				<li><a href="equipmentSearch.action?method=search&type=16&name=wLrH8g==">篮球</a></li>
				<li><a href="equipmentSearch.action?method=search&type=18&name=0%2FDDq8fy">羽毛球</a></li>
				<li><a href="equipmentSearch.action?method=search&type=17&name=0%2FDDq8fyxcQ=">羽拍</a></li>
				<li><a href="equipmentSearch.action?method=search&type=20&name=zfjH8tPDxrc=">网球</a></li>
				<li><a href="equipmentSearch.action?method=search&type=20&name=zfjH8tPDxrc=">网拍</a></li>
				<li><a href="">轮滑滑板</a></li>
				<li><a href="">瑜伽球</a></li>
				<li><a href="">瑜伽垫</a></li>
			</ul>
		</div>
	</div>--%>
</c:if>