<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>新闻评论</title>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					新闻评论列表 &nbsp;&nbsp;
					&nbsp;&nbsp;
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="blogManage.shtml" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
<%--						<tr class="banner">--%>
<%--							<td>--%>
<%--								作者--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								文章--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								时间--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								来源--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								操作--%>
<%--							</td>--%>
<%--						</tr>--%>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr class="alternative">
									<td>
										${model.author}
									</td>
									<td>
										<a target="_blank"
											href="../news/${model.newsId}">
											${model.newsId}
										</a>
									</td>
									<td>
										${model.createTime}&nbsp;&nbsp;
<%--									</td>--%>
<%--									<td>--%>
										${model.ip}&nbsp;&nbsp;
<%--									</td>--%>
<%--									<td>--%>
										<a
											href="newsManage.shtml?method=deleteComment&cid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
									</td>
								</tr>
								<tr>
									<td colspan="7">
									<g:topic value="${model.content }"/>
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
