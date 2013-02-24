<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
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
<div class="state" id="epcomment">
	<div class="titles">
		品牌分类
	</div>
	<div class="ew">
		<a class="rss" href="equipmentSearch.action?method=brandList">MORE</a>
	</div>
	<div class="brand_icons clearfix">
		<a class="nike" href="equipment/search/nike" title="nike">nike</a>
		<a class="adidas" href="equipment/search/adidas" />
		<a class="adidasoriginal" href="equipment/search/adidasoriginal" title="adidas original">adidas original</a>
		<a class="converse" href="equipment/search/converse" title="converse">converse</a>
		<a class="newbalance" href="equipment/search/newbalance" title="new balance">new balance</a>
		<a class="kappa" href="equipment/search/kappa" title="kappa">kappa</a>
		<a class="lecoq" href="equipment/search/lecoq" title="lecoq">lecoq</a>
		<a class="puma" href="equipment/search/puma" title="puma">puma</a>
		<a class="lining" href="equipment/search/lining" title="lining">lining</a>
		<a class="mizuno" href="equipment/search/mizuno" title="mizuno">mizuno</a>
		<a class="umbro" href="equipment/search/umbro" title="umbro">umbro</a>
		<a class="thenorthface" href="equipment/search/thenorthface" title="the northface">the northface</a>
		<a class="knoway" href="equipment/search/knoway" title="knoway">knoway</a>
		<a class="avia" href="equipment/search/avia" title="avia">avia</a>
		<a class="crocs" href="equipment/search/crocs" title="crocs">crocs</a>
	</div>
</div>
<c:if test="${not empty newCommentList}">
	<div class="state" id="epcomment">
		<div class="titles">
			最新评论
		</div>
		<%--
		<div class="ew">
			<a class="rss" href="#">MORE</a>
		</div>
		--%>
		<ul>
			<c:forEach items="${newCommentList}" var="model">
				<li class="tlist_line_wrap clearfix">
					<div class="tlist_icon icons_newticket"></div>
					<div class="tlist_text">
						<a href="equipment/${model.goodsWare.id}"> <community:topic
							value="${model.content}" /> </a>
						<%--<span class="color_orange">&nbsp;${model.username}</span>--%>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
