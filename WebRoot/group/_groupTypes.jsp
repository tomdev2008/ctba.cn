<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
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
<div class="sort_wrap">
	<div class="group_sort">
	    <ul id="sort_tab">
            <span class="st_titile"><bean:message key="page.group.widget.type"/></span>
	        <li><a href="g.action?method=list"><bean:message key="page.group.widget.type.allType"/></a></li>
	        <c:if test="${not empty typeList}">
			<c:forEach items="${typeList}" var="model" varStatus="index">
			<c:if test="${((empty t_index ) and (index.index==0)) or ((not empty t_index) and t_index-1 == index.index)}">
			<li class="current"><a name="s${model.index}">${model.name}</a></li>
			</c:if>
			<c:if test="${((empty t_index ) and index.index!=0) or ( (not empty t_index) and t_index-1 != index.index)}">
			<li><a name="s${model.index}">${model.name}</a></li>
			</c:if>
			</c:forEach>
			</c:if>
		</ul>
		<c:if test="${not empty typeList}">
		<c:forEach items="${typeList}" var="model" varStatus="index">
		<ul id="s${model.index}" 
		<c:if test="${((empty t_index ) and index.index!=0) or ( (not empty t_index) and t_index-1 != index.index)}">style="display:none"</c:if> 
		class="group_sort_detail">
			<li class="sortall"><a href="g.action?method=list&t=${model.index}&t_index=${model.index }"><bean:message key="page.group.widget.type.all"/></a></li>
			<c:forEach items="${model.tags}" var="tag">
			<li><a href="g.action?method=list&t_index=${model.index }&tag=<community:url value=" ${tag }"/>">${tag }</a></li>
			</c:forEach>
		</ul>
		</c:forEach>
		</c:if>
	</div>
</div>