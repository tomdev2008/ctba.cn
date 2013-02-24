<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="include.jsp"%>
		<title>管理黑名单</title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					取消访问权限
				</div>
				<form name="form1" action="manage.shtml?method=saveBlack"
					method="post" style="display: inline">
					<input type="hidden"
						name=type  value="0" />
						用户名
					<input type="text" class="formInput" id="userInSearch"
						name=username size="20" />
						&nbsp;
						理由<input type="text" class="formInput"
						name=reason size="20" />&nbsp;
					<input type="submit" name="submit" value="根据用户名取消访问权限" class="button" />
				</form>
				<br class="clear" />
				<form name="form1" action="manage.shtml?method=saveBlack"
					method="post" style="display: inline">
					<input type="hidden"
						name=type  value="1" />
					IP地址
					<input type="text" class="formInput" id="ip"
						name=ip size="20" />
						&nbsp;
						理由<input type="text" class="formInput"
						name=reason size="20" />&nbsp;
					<input type="submit" name="submit" value="根据IP地址取消访问权限" class="button" />
				</form>
				<br class="clear" />
				<div class="navigator">
					黑名单列表 &nbsp;
					<form name="searchFrom" action="manage.do" method="post"
						style="display: inline; margin-left: 10px">
						用户名:<input type="text" class="formInput" name="username"
							value="${sessionScope.username}" />
							IP:<input type="text" class="formInput" name="ip"
							value="${sessionScope.ip}" />
						<input type="hidden" name="method" value="listBlacks" />
						<input type="submit" value="搜索" class="button" />
					</form>
				</div>
				<pg:pager items="${count}" url="manage.do" index="center"
					maxPageItems="30" maxIndexPages="6"
					isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method" />
					<pg:index>
						<jsp:include flush="true" page="../common/jsptags/manage.jsp"></jsp:include>
					</pg:index>
					
					<table width="100%" border=0 cellpadding="3" cellspacing="0">
						<tr class="banner">
							<td>
								NO.
							</td>
							<td>
								用户名
							</td>

							<td>
								IP
							</td><td>
								理由
							</td><td>
								时间
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="blackList">
							<logic:iterate id="u" name="blackList" indexId="index">
								<tr <%if(index%2==1){out.print("class=alternative");}%>>
									<td>
								${u.id }
							</td>
							<td>
								${u.username }
							</td>

							<td>
							${u.ip }
							</td><td>
								${u.reason }
							</td><td>
								${u.createTime }
							</td>
									<td>
									<a
										href="manage.shtml?method=deleteBlack&id=${u.id }"
										onclick="return confirm('您确实要解除么？');" class="button">解除 </a>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</table>
				</pg:pager>
			</div>
		</div>
		<jsp:include page="bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
