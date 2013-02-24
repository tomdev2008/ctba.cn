<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<div class="state clearfix">
    <div class="titles">
        <ctba:wrapblog blog="${blogModel}" user="${blogAuthor}"/>
    </div>
    <div style="float:left;display:inline;margin: 5px 0 0">
        <img src="<com:img value="${blogModel.image}" type="default" />" />
        <br />
        <b><bean:message key="page.blog.right.profile"/></b>&nbsp;
        <com:topic value="${blogModel.description}"/>
        <br />
        <b><bean:message key="page.blog.right.owner"/></b>&nbsp;<a href="u/${blogAuthor.userId}">${blogModel.author}</a>
        <br />
        <b><bean:message key="page.blog.right.counter.label"/></b>&nbsp;${blogModel.hits}&nbsp;
        <a class="rss" href="rss.shtml?type=blog&cid=${categoryModel.id}&bgid=${blogModel.id }" target="_blank">RSS</a>
        <br />
        ${blogModel.htmlBlock }
    </div>
</div>

<c:if test="${blogModel.author eq  __sys_username }">
    <div class="state">
        <div class="titles">
            <bean:message key="page.blog.right.manage"/>
        </div>
        <ul class="infobar">
            <li class="dot_gray">
                <a href="be.action?method=list"><bean:message key="menu.blog.entryList" /></a>
            </li>
            <li class="dot_gray">
                <a href="be.action?method=form"><bean:message key="menu.blog.entryForm" /></a>
            </li>
            <li class="dot_gray">
                <a href="bg.action?method=form"><bean:message key="menu.blog.edit" /></a>
            </li>
        </ul>
    </div>
</c:if>

<div class="state">
    <div class="titles">
        <bean:message key="menu.blog.catList" />
    </div>
    <c:if test="${not empty cats}">
        <ul class="infobar">
            <c:forEach items="${cats}" var="model" varStatus="status">
                <li class="icons_newticket">
                    <a href="blog/${blogModel.id }/${model.id }">${model.name }</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<div class="state">
    <div class="titles">
        <bean:message key="menu.blog.entryList" />
    </div>
    <c:if test="${not empty newEntries}">
        <ul class="infobar">
            <c:forEach items="${newEntries}" var="model" varStatus="status">
                <li class="icons_article">
                    <a href="blog/entry/${model.id }">
                        <com:limit maxlength="12" value="${model.title }" />
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<div class="state">
    <div class="titles">
        <bean:message key="menu.blog.commentList" />
    </div>
    <c:if test="${not empty newComments}">
        <ul class="infobar">
            <c:forEach items="${newComments}" var="model" varStatus="status">
                <li class="icons_comment_s">
                    <a href="blog/entry/${model.blogBlogentry.id }">
                        <com:limit maxlength="12" value="${model.body}" ignoreUbb="true"/>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<div class="state">
    <div class="titles">
        <bean:message key="menu.blog.linkList" />
    </div>
    <c:if test="${not empty links}">
        <ul class="infobar">
            <c:forEach items="${links}" var="model" varStatus="status">
                <li class="icons_extlink">
                    <a href="${model.url }">
                        <com:limit maxlength="12" value="${model.title }" />
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>

<c:if test="${not empty userImageList}">
    <div id="gal" class="state">
        <div class="titles">
            <bean:message key="page.blog.right.gallery"/>
        </div><div class="ew"><a class="rss" href="img.shtml?method=galleryList&wrap-uid=${authorModel.userId }">MORE</a></div>
        <div class="modBody">
            <div id="galleryDiv" class="pics"
                 style="position: relative; width: 155px; height: 155px; ">
                <c:forEach items="${userImageList}" var="model" varStatus="status">
                    <img width="150"  src="<com:img value="${model.path}" type="default"  />"
                         style="position: absolute; top: 0pt; left: 0pt; display: block; z-index: 3; opacity: 1;" />
                </c:forEach>
            </div>
        </div>
    </div>
    <script>
        $('#galleryDiv').cycle({
            fx:     'scrollLeft',
            speed:   300, 
            timeout: 3000,
            next:   '#galleryDiv',
            pause:   1
        });
    </script>
</c:if>

<c:if test="${not empty shortSummaryList}">
    <div class="state">
        <div class="titles">
            <bean:message key="page.blog.right.summary"/>
        </div>
        <ul class="infobar">
            <c:forEach items="${shortSummaryList}" var="model"
        varStatus="status">
                <li class="dot_gray">
                    <a href="blog/${blogModel.id }/summary/${model.month }">${model.month}(${model.cnt })</a>
                </li>
            </c:forEach>
            <li>
                <a href="bg.action?bid=${blogModel.id }&method=summary"><bean:message key="page.blog.right.seeAll"/></a>
            </li>
        </ul>
    </div>
</c:if>