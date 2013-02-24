<%@ page language="java" contentType="text/x-javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
document.write('<ul id="ctba_share_widget" style="margin:0;padding:0;list-style:none;width:${width}px">');
<c:forEach items="${shareList}" var="model">
document.write('<li style="display:block;padding:5px;margin:0;border-bottom:1px dotted #ddd;line-height:18px;overflow:hidden;word-wrap:break-word;text-overflow:ellipsis"><a href="http://www.ctba.cn/share/comment/${model.share.id }" target="_blank"><community:limit flat="true" value="${model.share.label}" maxlength="30" /></a>&nbsp;<em style="font-style:normal;font-size:11px">(${model.commentCnt}/${model.share.hits})</em></li>');
</c:forEach>
<c:if test="${empty hidelogo}">document.write('<li class="ctba_share_url" style="padding:5px;margin:0"><a href="http://www.ctba.cn/share/"><img src="http://www.ctba.cn/images/cticon.png" align="absmiddle">&nbsp;扯谈分享</a></li></ul>');</c:if>