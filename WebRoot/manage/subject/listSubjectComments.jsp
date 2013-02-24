<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>管理专题文章</title>
		<%@include file="../include.jsp"%>
	</head>

	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					专题文章列表 &nbsp;&nbsp;
					<%--					<form style="display:inline" method="post"--%>
					<%--						action="manage.do?method=listBoards">--%>
					<%--												<input name="searchKey" class="formInput" value="${searchKey }" />--%>
					<%--						<input type="submit" class="button" value="GO" />--%>
					<%--					</form>--%>
					&nbsp;&nbsp;
					<a href="subManage.shtml?method=subjectTopicForm" class="button">新建专题文章</a>

				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="subManage.shtml" index="center"
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
								专题
							</td><td>
								文章号
							</td><td>
								类型
							</td>
							<td>
								创建时间
							</td>
							<td>
								修改时间
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
										<a href="../subject.shtml?method=viewSubjectTopic&sid=${model.id}"
											target="_blank">${model.title} </a>
									</td>
									<td>
										<comm:select items="${subjectList}" name="subject"
											label="title" value="id" selected="${model.subjectId}" />
									</td>
							
									<td>
										<c:if test="${'e' eq model.type}">${model.entryId}</c:if>
										<c:if test="${'t' eq model.type}">${model.topicId}</c:if>
									
									</td><td>
										<c:if test="${'e' eq model.type}">博客文章</c:if>
										<c:if test="${'t' eq model.type}">论坛文章</c:if>
									</td><td>
										${model.createTime }
									</td>
									<td>
										${model.updateTime }
									</td>
									<td>
										<a href="subManage.shtml?method=subjectTopicForm&sid=${model.id }"
											class="button">更改</a>
										<a
											href="subManage.shtml?method=deleteSubjectTopic&sid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
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
