<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<div class="share_logo clearfix">
	<ul class="clearfix fright">
		<li class="text arrow color_orange"><bean:message key="page.common.share.label" /></li>
		<li>
			<a target="_blank" class="a_share_kaixin001" title="转帖到开心网" href='http://www.kaixin001.com/repaste/share.php?rtitle=<%=request.getParameter("share_title")%>&rurl=<%=request.getParameter("share_url")%>&rcontent=<%=request.getParameter("share_content")%>'>开心</a>
		</li>
		<li>
			<a target="_blank" class="a_share_xiaonei" title="<bean:message key="page.common.share.label.xiaonei" />" href="http://share.xiaonei.com/share/ShareOperate.do?action=sharelink&weblink=<%=request.getParameter("share_url") %>"><bean:message key="page.common.share.label.xiaonei.short" /></a>
		</li>
		<%--<li>
			<a target="_blank" class="a_share_google" title="<bean:message key="page.common.share.label.google" />" href="http://www.google.com/bookmarks/mark?op=add&bkmk=<%=request.getParameter("share_url") %>"><bean:message key="page.common.share.label.google.short" /></a>
		</li>--%>
		<li>
			<a target="_blank" class="a_share_native" title="<bean:message key="page.common.share.label.site" />" href="share.shtml?method=shareProxy&type=<%=request.getParameter("share_type") %>&id=<%=request.getParameter("share_id") %>"><bean:message key="page.common.share.label.site.short" /></a>
		</li>
	</ul>
</div>