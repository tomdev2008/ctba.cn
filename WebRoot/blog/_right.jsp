<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<div class="sideTop"></div>
<div id="sidebar">
    <div id="profile" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="page.blog.right.profile" />
            </h2>
        </div>
        <div class="modBody">
            <a href="<ctba:wrapuser user="${blogAuthor}" linkonly="true"/>">
                <img src="<com:img value="${blogModel.image}" type="sized" width="80" />" class="group_border" />
            </a>
            <h3 class="author">
                <ctba:wrapuser user="${blogAuthor}"/>
            </h3>
            <div class="about">
                <a href="${context }/blog/"><bean:message key="page.blog.right.blogHome"/></a>&nbsp;|&nbsp;<a href="${context }/"><bean:message key="page.blog.right.home"/></a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="modBottom"></div>
    </div>
    <c:if test="${blogModel.author eq  __sys_username }">
        <div id="login" class="module">
            <div class="modTop">
                <h2>
                    <bean:message key="page.blog.right.manage"/>
                </h2>
            </div>
            <div class="modBody">
                <ul class="infobar">
                    <li>
                        <a href="be.action?method=list" class="link_btn">
                            <bean:message key="menu.blog.entryList" />
                        </a>
                    </li>
                    <li>
                        <a href="be.action?method=form" class="link_btn">
                            <bean:message key="menu.blog.entryForm" />
                        </a>
                    </li>
                    <li>
                        <a href="bg.action?method=form" class="link_btn">
                            <bean:message key="menu.blog.edit" />
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </c:if>
    <div id="cats" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="menu.blog.catList" />
            </h2>
        </div>
        <div class="modBody">
            <c:if test="${not empty cats}">
                <ul class="infobar">
                    <c:forEach items="${cats}" var="model" varStatus="status">
                        <li>
                            <a href="blog/${blogModel.id }/${model.id }">${model.name }</a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="modBottom"></div>
    </div>
    <div id="nPosts" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="menu.blog.entryList" />
            </h2>
        </div>
        <div class="modBody">
            <c:if test="${not empty newEntries}">
                <ul class="infobar">
                    <c:forEach items="${newEntries}" var="model" varStatus="status">
                        <li>
                            <a href="blog/entry/${model.id }">
                                <com:limit maxlength="20" value="${model.title }" ignoreUbb="true"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="modBottom"></div>
    </div>
    <div id="nComments" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="menu.blog.commentList" />
            </h2>
        </div>
        <div class="modBody">
            <c:if test="${not empty newComments}">
                <ul class="infobar">
                    <c:forEach items="${newComments}" var="model" varStatus="status">
                        <li>
                            <a href="blog/entry/${model.blogBlogentry.id }">
                                <com:limit maxlength="20" value="${model.body}" ignoreUbb="true"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="modBottom"></div>
    </div>
    <div id="links" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="menu.blog.linkList" />
            </h2>
        </div>
        <div class="modBody">
            <c:if test="${not empty links}">
                <ul class="infobar">
                    <c:forEach items="${links}" var="model" varStatus="status">
                        <li>
                            <a href="${model.url }" title="${model.title }" target="_blank">${model.title }</a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="modBottom"></div>
    </div>
    <c:if test="${not empty userImageList}">
        <div id="album" class="module">
            <div class="modTop">
                <h2>
                    <bean:message key="page.blog.right.gallery"/>
                </h2>
            </div>
            <div class="modBody">
                <div id="widget_gallery">
                    <c:forEach items="${userImageList}" var="model" varStatus="status">
                        <img src="<com:img value="${model.path}" type="default" />" />
                    </c:forEach>
                </div>
                <div style="margin-left: 5px">
                    <a href="img.shtml?method=galleryList&wrap-uid=${authorModel.userId }"><bean:message key="page.blog.right.seeAll"/></a>&nbsp;|&nbsp;<a href="${context }/album/">扯谈相册</a>
                </div>
            </div>
            <div class="modBottom"></div>
        </div>
    </c:if>
    <c:if test="${not empty shortSummaryList}">
        <div id="cats" class="module">
            <div class="modTop">
                <h2>
                    <bean:message key="page.blog.right.summary"/>
                </h2>
            </div>
            <div class="modBody">
                <form method="get" action="/">
                    <select onchange="document.location.href=this.options[this.selectedIndex].value;">
                        <option value="">月份选择</option>
                        <c:forEach items="${shortSummaryList}" var="model" varStatus="status">
                            <option value="blog/${blogModel.id }/summary/${model.month }">${model.month} (${model.cnt })</option>
                        </c:forEach>
                        <option value="bg.action?bid=${blogModel.id }&method=summary"><bean:message key="page.blog.right.seeAll" /></option>
                    </select>
                </form>
            </div>
            <div class="modBottom"></div>
        </div>
    </c:if>
    <div id="tags" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="page.blog.right.freeBlock"/>
            </h2>
        </div>
        <div class="modBody">
            ${blogModel.htmlBlock }
        </div>
    </div>
    <div id="archives" class="module">
        <div class="modTop">
            <h2>
                <bean:message key="page.blog.right.counter"/>
            </h2>
        </div>
        <div class="modBody">
            <ul class="infobar">
                <li class="hits">
                    ${blogModel.hits}
                </li>
                <li>
                    <a href="rss.shtml?type=blog&cid=${categoryModel.id}&bgid=${blogModel.id }">
                        <img src="blogThemes/${blogModel.theme }/img/rss.gif" alt="<bean:message key="page.blog.right.rss"/>" title="<bean:message key="page.blog.right.rss"/>" />
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="sideBottom"></div>
<script type="text/javascript">
    $('#widget_gallery').cycle({
        fx:     'fade',
        speed:   300,
        timeout: 3000,
        next:   '#widget_gallery',
        pause:   1
    });
</script>