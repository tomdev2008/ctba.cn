<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>管理版面</title>
		<%@include file="../include.jsp"%>
	</head>

	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					版面列表 &nbsp;&nbsp;
					<form style="display:inline" method="post"
						action="manage.do?method=listBoards">
						<input name="searchKey" class="formInput" value="${searchKey }" />
						<input type="submit" class="button" value="搜索" />
					</form>
					&nbsp;&nbsp;
					
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="manage.do" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<tr class="banner">
							<td>
								版面名称
							</td>
							<td>
								版主
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models"
							 indexId="index">
								<tr <%if(index%2==1){ %>class="alternative"<%} %>>
									<td>
									<a href="editBoard.action?method=form&boardId=${model.boardId}">${model.boardName}
										</a>
									</td>
									<td>
										${model.boardMaster1 }
									</td>
									<td>
										<a href="editBoard.action?method=form&boardId=${model.boardId }" class="button">更改</a>
										<a href="editBoard.action?method=delete&boardId=${model.boardId }"
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
