<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<ul id="tabs_gray" class="lightgray_bg graylink">
	<li <c:if test="${param.currTab eq 'home' }">class="current"</c:if><c:if test="${not (param.currTab eq 'home' ) }">class="normal"</c:if>><c:if test="${param.currTab eq 'home' }">CT币中心</c:if><c:if test="${not (param.currTab eq 'home' ) }"><a href="bank/">CT币中心</a></c:if></li><li <c:if test="${param.currTab eq 'trans' }">class="current"</c:if><c:if test="${not (param.currTab eq 'trans' ) }">class="normal"</c:if>><c:if test="${param.currTab eq 'trans'  }">转账</c:if><c:if test="${not (param.currTab eq 'trans' ) }"><a href="bank.action?method=trans">转账</a></c:if></li><li <c:if test="${param.currTab eq 'log'  }">class="current"</c:if><c:if test="${not (param.currTab eq 'log' ) }">class="normal"</c:if>><c:if test="${param.currTab eq 'log'  }">操作记录</c:if><c:if test="${not (param.currTab eq 'log'  ) }"><a href="bank.action?method=logs">操作记录</a></c:if></li><li <c:if test="${param.currTab eq 'fun'  }">class="current"</c:if><c:if test="${not (param.currTab eq 'fun' ) }">class="normal"</c:if>><c:if test="${param.currTab eq 'fun'  }">兑换</c:if><c:if test="${not (param.currTab eq 'fun'  ) }"><a href="bank.action?method=fun">兑换</a></c:if></li>
</ul>