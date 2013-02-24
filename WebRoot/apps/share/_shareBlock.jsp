<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<c:if test="${not empty __sys_username}">
<div class="mid_block top_notice tl clearfix orangelink">
	<h2 class="color_orange">
        <strong class="fleft"><bean:message key="page.user.share"/></strong>
	</h2>
	<c:if test="${not empty __sys_username}">
		<span id="share_tip" class="fright orangelink color_gray">
			<bean:message key="page.user.share.counter"/><strong class="color_darkgray" id="leftNum">160</strong>
		</span>
		<form action="share.shtml?method=saveShare" method="post" onsubmit="return checkForm();">
			<p>
				<textarea name="label" id="share_text" class="color_dgray"><bean:message key="page.user.share.hint"/></textarea>
			</p>
			<p class="clearfix">
				<span class="fleft mt3">
					<input id="share_link" name="url" type="text" value="" class="hide color_gray font_small" />
					<input name="" type="checkbox" value="" class="nborder" id="share" />
					<label for="share" class="pointer"><bean:message key="page.user.share.link" /></label>
					<input id="share_private" name="state" type="checkbox" value="state" class="nborder" id="state" />
					<label for="share_private" class="pointer"><bean:message key="page.user.share.private" /></label>
				</span>
				<span id="share_btn" class="fright">
					<input id="submitButton" class="input_btn" type="submit" value="<bean:message key="page.user.share.button"/>" />
				</span>
			</p>
			<p class="clearfix">
			</p>
		</form>
	</c:if>
</div>
</c:if>