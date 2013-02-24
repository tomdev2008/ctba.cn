<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<title><bean:message key="page.foundContact.title" />&nbsp;-&nbsp;<bean:message key="sys.name" />
		</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<bean:message key="page.foundContact.title" />
					</div>
					<div class="fright">
						<html:messages id="message" message="true">
							<div class="message_info">
								${message }
							</div>
						</html:messages>
					</div>
				</div>


				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../_operationBlock.jsp" />
					<jsp:include flush="true" page="../_adBlock.jsp" />
				</div>


			<logic:notEmpty name="friendList">
			<div id="mid_column" class="fright">
					<div class="title">
						<h3>
							&nbsp;
							&nbsp;<bean:message key="page.foundContact.friendList" />
						</h3>
					</div>
						<table width="50%" cellspacing="0" cellpadding="0" id="listable"
							align="center">
								<logic:iterate id="model" name="friendList" indexId="index">
									<tr>
										<td width="78%" <%if(index%2==1){ %> class="at_sp" <%} %>
											class="list_sp">
											&nbsp;&nbsp;
											<a target="_blank"
												href="userpage/<community:url value="${model.username }"/>">${model.username
												}</a>&nbsp;
										</td>
										<td width="22%" <%if(index%2==1){ %> class="alternative"
											<%} %>>
										</td>
									</tr>
								</logic:iterate>
						
						</table>
				</div>
					</logic:notEmpty>
				
				<div id="mid_column" class="fright">
					<div class="title">
						<h3>
							&nbsp;
							&nbsp;<bean:message key="page.foundContact.notFriendList" />
						</h3>
					</div>
					<form name="inviteForm" action="c.action?method=find" method="post">
						<table width="50%" cellspacing="0" cellpadding="0" id="listable"
							align="center">
							<logic:notEmpty name="contactList">
								<logic:iterate id="model" name="contactList" indexId="index">
									<tr>
										<td width="78%" <%if(index%2==1){ %> class="at_sp" <%} %>
											class="list_sp">
											&nbsp;&nbsp;
											<a target="_blank"
												href="userpage/<community:url value="${model.username }"/>">${model.username
												}</a>&nbsp;
										</td>
										<td width="22%" <%if(index%2==1){ %> class="alternative"
											<%} %>>
											<input type="checkbox" name="contact_${model.username }"
												checked="checked" />
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
							<tr>
								<td width="78%" align="center">
									<img src="images/icons/user_add.png" align="absmiddle" />
									&nbsp;
									<input value="<bean:message key="page.foundContact.form.button.add" />" type="submit" class="input_btn" />
									&nbsp;
									<input value="<bean:message key="page.foundContact.form.button.skip" />" type="button" onclick="location.href='c.action?method=find';" class="input_btn" />
								</td>
								<td width="22%">
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../bottom.jsp"></jsp:include>
	</body>
</html>
