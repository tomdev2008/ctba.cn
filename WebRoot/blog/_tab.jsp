<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/taglibs.jsp"%>
<ul id="tabs_gray" class="graylink">
	<li <c:if test="${param.currTab eq 'edit' }">class="current"</c:if><c:if test="${not (param.currTab eq 'edit') }">class="normal"</c:if>><c:if test="${param.currTab eq 'edit' }"><bean:message key="blog.post.new" /></c:if><c:if test="${not (param.currTab eq 'edit') }"><a href="be.action?method=form"><bean:message key="blog.post.new" /></a></c:if></li><li <c:if test="${param.currTab eq 'draft' }">class="current"</c:if><c:if test="${not (param.currTab eq 'draft') }">class="normal"</c:if>><c:if test="${param.currTab eq 'draft' }"><bean:message key="menu.blog.draft" /></c:if><c:if test="${not (param.currTab eq 'draft') }"><a href="be.action?method=drafts"><bean:message key="menu.blog.draft" /></a></c:if></li><li <c:if test="${param.currTab eq 'entry' }">class="current"</c:if> <c:if test="${not (param.currTab eq 'entry') }">class="normal"</c:if>> <c:if test="${param.currTab eq 'entry' }"><bean:message key="menu.blog.entryList" /></c:if><c:if test="${not (param.currTab eq 'entry') }"><a href="be.action?method=list"><bean:message key="menu.blog.entryList" /></a></c:if></li><li <c:if test="${param.currTab eq 'comment' }">class="current"</c:if><c:if test="${not (param.currTab eq 'comment') }">class="normal"</c:if>> <c:if test="${param.currTab eq 'comment' }"><bean:message key="menu.blog.commentList" /></c:if><c:if test="${not (param.currTab eq 'comment') }"><a href="com.action?method=list"><bean:message key="menu.blog.commentList" /></a></c:if></li><li <c:if test="${param.currTab eq 'category' }">class="current"</c:if><c:if test="${not (param.currTab eq 'category') }">class="normal"</c:if>><c:if test="${param.currTab eq 'category' }"><bean:message key="menu.blog.catList" /></c:if><c:if test="${not (param.currTab eq 'category') }"><a href="cat.action?method=list"><bean:message key="menu.blog.catList" /></a></c:if></li><li <c:if test="${param.currTab eq 'link' }">class="current"</c:if><c:if test="${not (param.currTab eq 'link') }">class="normal"</c:if>><c:if test="${param.currTab eq 'link' }"><bean:message key="menu.blog.linkList" /></c:if><c:if test="${not (param.currTab eq 'link') }"><a href="bl.action?method=list"><bean:message key="menu.blog.linkList" /></a></c:if></li><li <c:if test="${param.currTab eq 'vest' }">class="current"</c:if><c:if test="${not (param.currTab eq 'vest') }">class="normal"</c:if>><c:if test="${param.currTab eq 'vest' }"><bean:message key="menu.blog.vest" /></c:if><c:if test="${not (param.currTab eq 'vest') }"><a href="bv.action?method=list"><bean:message key="menu.blog.vest" /></a></c:if></li>
</ul>