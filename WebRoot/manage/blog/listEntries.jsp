<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@include file="../../common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>博客文章列表</title><%@include file="../include.jsp"%>
	</head>

	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					博客文章列表&nbsp;&nbsp;
					<%--					<form style="display:inline" method="post"--%>
					<%--						action="manage.do?method=listBoards">--%>
					<%--						<input name="searchKey" class="formInput" value="${searchKey }" />--%>
					<%--						<input type="submit" class="button" value="搜索" />--%>
					<%--					</form>--%>
					&nbsp;&nbsp;
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="blogManage.shtml" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<tr class="banner">
							<td>
								题目
							</td>
							<td>
								博客
							</td>
							<td>
								用户
							</td>
							<td>
								时间
							</td><td>
								可见
							</td><td>
								类型
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
										<a target="_blank" href="../blog/entry/${model.id}">${model.title}
										</a>
									</td>
									<td>
										<a target="_blank"
											href="../blog/entry/${model.blogBlog.id}">${model.blogBlog.name}
										</a>
									</td>
									<td>
										${model.author }
									</td>
									<td>
										${model.date }
									</td><td>
										<c:if test="${model.state==0}"><bean:message key="page.blog.form.form.viewOption.all"/></c:if> 
								<c:if test="${model.state==1}"><bean:message key="page.blog.form.form.viewOption.users"/></c:if> 
								<c:if test="${model.state==2}"><bean:message key="page.blog.form.form.viewOption.friends"/></c:if> 
								<c:if test="${model.state==3}"><bean:message key="page.blog.form.form.viewOption.private"/></c:if> 
									</td><td>
										<c:if test="${model.type==1000}">发布</c:if> 
								<c:if test="${model.type==1001}">草稿</c:if> 
									</td>
									<td>
										<a href="blogManage.shtml?method=entryForm&eid=${model.id }"
											  class="button">编辑</a>
											<a href="blogManage.shtml?method=deleteEntry&eid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
									<a href="subManage.shtml?method=subjectTopicForm&eid=${model.id}" class="button">到专题</a>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
						<tr>
							<pg:index>
								<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
							</pg:index>

						</tr>
					</pg:pager>
				</table>

			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
