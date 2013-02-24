<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<style type="text/css">
	#blog_share .a_share_kaixin001,
	#blog_share .a_share_xiaonei,
	#blog_share .a_share_native { float: left; display: inline; margin-right: 8px; height: 15px; width: 25px; line-height: 15px; *line-height: 18px; overflow: hidden; padding: 0 18px 0 5px }
	#blog_share .a_share_kaixin001 { color: #d01e3b; margin-right: 8px; border: 1px solid #d01e3b; background: #fff url(../../../images/shares/kaixin001_ico.gif) no-repeat right; *background: #fff url(../../../images/shares/kaixin001_ico.gif) no-repeat right 0 }
	#blog_share .a_share_kaixin001:hover { color: #fff; border: 1px solid #d01e3b; background: #d01e3b url(../../../images/shares/kaixin001_ico.gif) no-repeat right; *background: #3b5888 url(../../../images/shares/kaixin001_ico.gif) no-repeat right 0 }
	#blog_share .a_share_xiaonei { color: #525c97; margin-right: 4px; border: 1px solid #7f93bc; background: #fff url(../../../images/shares/xiaonei_ico.gif) no-repeat right; *background: #fff url(../../../images/shares/xiaonei_ico.gif) no-repeat right 0 }
	#blog_share .a_share_xiaonei:hover { color: #fff; border: 1px solid #3b5888; background: #3b5888 url(../../../images/shares/xiaonei_ico.gif) no-repeat right; *background: #3b5888 url(../../../images/shares/xiaonei_ico.gif) no-repeat right 0 }
	#blog_share .a_share_native { color: #e0862d; border: 1px solid #e0862d; background: #fff url(../../../images/shares/native.gif) no-repeat right top }
	#blog_share .a_share_native:hover { color: #fff; background: #fbbb4f url(../../../images/shares/native.gif) no-repeat 100% -20px }
</style>
<div id="blog_share">
	<a target="_blank" class="a_share_native" title="<bean:message key="page.common.share.label.site" />" href="share.shtml?method=shareProxy&type=<%=request.getParameter("share_type")%>&id=<%=request.getParameter("share_id")%>"><bean:message key="page.common.share.label.site.short" /></a>
	<a target="_blank" class="a_share_kaixin001" title="转帖到开心网" href='http://www.kaixin001.com/repaste/share.php?rtitle=<%=request.getParameter("share_title")%>&rurl=<%=request.getParameter("share_url")%>&rcontent=<%=request.getParameter("share_content")%>'>开心</a>
	<a target="_blank" class="a_share_xiaonei" title="<bean:message key="page.common.share.label.xiaonei" />" href="http://share.xiaonei.com/share/ShareOperate.do?action=sharelink&weblink=<%=request.getParameter("share_url")%>"><bean:message key="page.common.share.label.xiaonei.short" /></a>
</div>