<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%--
default (index page) 
--%>
<c:if test="${empty param.currPage }">
	<meta charset="UTF-8" />
	<meta name="description" content="扯谈社（CTBA）是一家 Web2.0 体育互动网站，它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。" />
	<meta name="keyword" content="体育社区,体育论坛,体育博客,体育,Web2.0,NBA,CBA,篮球评论,中国篮球,街头篮球,奥运会" />
</c:if>

<%--
group 
--%>
<c:if test="${param.currPage eq 'group' }">
	<meta charset="UTF-8" />
	<meta name="description" content="扯谈社（CTBA）是一家 Web2.0 体育互动网站，它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。" />
	<meta name="keyword" content="体育社区,体育论坛,体育博客,体育,Web2.0,NBA,CBA,篮球评论,中国篮球,街头篮球,奥运会" />
</c:if>

<%--
blog
--%>
<c:if test="${param.currPage eq 'blog' }">
	<meta charset="UTF-8" />
	<meta name="description" content="扯谈社（CTBA）是一家 Web2.0 体育互动网站，它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。" />
	<meta name="keyword" content="体育社区,体育论坛,体育博客,体育,Web2.0,NBA,CBA,篮球评论,中国篮球,街头篮球,奥运会" />
</c:if>

<%--
news
--%>
<c:if test="${param.currPage eq 'news' }">
	<meta charset="UTF-8" />
	<meta name="description" content="扯谈社（CTBA）是一家 Web2.0 体育互动网站，它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。" />
	<meta name="keyword" content="体育社区,体育论坛,体育博客,体育,Web2.0,NBA,CBA,篮球评论,中国篮球,街头篮球,奥运会" />
</c:if>

<%--
subject
--%>
<c:if test="${param.currPage eq 'subject' }">
	<meta charset="UTF-8" />
	<meta name="description" content="扯谈社（CTBA）是一家 Web2.0 体育互动网站，它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。" />
	<meta name="keyword" content="体育社区,体育论坛,体育博客,体育,Web2.0,NBA,CBA,篮球评论,中国篮球,街头篮球,奥运会" />
</c:if>
