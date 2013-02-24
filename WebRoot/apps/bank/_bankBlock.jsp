<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<c:if test="${not empty __sys_username}">
	<div class="mid_block top_notice bank clearfix">
		<h2 class="color_orange">
			<strong>我的帐户余额：${currScore }&nbsp;<img src="images/icon_ctb.gif" alt="CT币" title="CT币" align="absmiddle" /></strong>
		</h2>
		<p>
			查看<a href="go.jsp?key=faq-ctb-rule" target="_blank">《积分规则》</a><span class="color_gray">(如何获得积分和赚取CT币?)</span>、<a href="group/topic/110843" title="交易规则">《交易规则》</a><span class="color_gray">(CT币能做些什么?)</span>
		</p>
	</div>
</c:if>