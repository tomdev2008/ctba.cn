<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<ul id="tabs" class="graylink">
    <c:if test="${not empty __sys_username}">
         <li
        <c:if test="${param.currTab eq 'list'  }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'list'  ) }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'list' }">我的偶像</c:if>
        <c:if test="${not (param.currTab eq 'list'  ) }">
            <a href="people.action?method=list">我的偶像</a>
        </c:if>
    </li>
    <li
        <c:if test="${param.currTab eq 'list'   }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'list' ) }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'list' }">我的粉丝(${count})</c:if>
        <c:if test="${not (param.currTab eq 'list' ) }">
            <a href="people.action?method=list">我的粉丝</a>
        </c:if>
    </li>
    <li
        <c:if test="${param.currTab eq 'form' }">class="current"</c:if>
        <c:if test="${not (param.currTab eq 'form') }">class="normal"</c:if>>
        <c:if test="${param.currTab eq 'form' }">创建偶像</c:if>
        <c:if test="${not (param.currTab eq 'form') }">
            <a href="people.action?method=form">创建偶像</a>
        </c:if>
    </li>
    </c:if>
   
</ul>
