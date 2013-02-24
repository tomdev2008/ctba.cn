<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<link rel="Shortcut Icon" href="${context}/common/favicon.ico" />
<link href="${context }/theme/default/css/manage.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="screen" href="${context }/theme/jquery/superfish.css" /> 
<link rel="stylesheet" type="text/css" media="screen" href="${context }/theme/jquery/superfish-navbar.css" /> 
<script type="text/javascript" src="${context }/javascript/jquery-1.2.6.min.js"></script>
<script type="text/javascript" src="${context }/javascript/lib/hoverIntent.js"></script> 
<script type="text/javascript" src="${context }/javascript/lib/superfish.js"></script> 
<script type="text/javascript"> 
    $(document).ready(function(){ 
        $("ul.sf-menu").superfish({ 
            pathClass:  'current' 
        }); 
    }); 
</script>
<div id="head">
<ul class="sf-menu sf-js-enabled sf-shadow" >
			<c:if test="${not empty rootMenuItem}">
				<c:forEach items="${rootMenuItem.children}" var="menuItem"
					varStatus="status">
					<li>
						<a href=
						<c:if test="${'#' eq menuItem.link}">
						"${menuItem.link}"
					</c:if>
					
					<c:if test="${not '#' eq menuItem.link}">
						"${context }/${menuItem.link}"
					</c:if>
							<c:if test="${status.index==0 }">style="border-left: 1px solid #4C435A"</c:if>>
							${menuItem.name}</a>
						<ul>
							<c:forEach items="${menuItem.children}" var="subMenuItem">
								<li>
									<a href="${context }/${subMenuItem.link}"
										<c:if test="${not empty subMenuItem.confirm }">onclick="return confirm('${subMenuItem.confirm }');"</c:if>>
										${subMenuItem.name}</a>
								</li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</c:if>
		</ul>
</div>
<html:errors />
<logic:present name="systemError">
	<div id="messages">
		${systemError}
	</div>
</logic:present>
<html:messages id="message" message="true">
	<div id="messages">
		${message}
	</div>
</html:messages>
<br style="clear:both;" />