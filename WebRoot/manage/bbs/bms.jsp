<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>管理版主</title>
		<script type="text/javascript">
		function selectUser(username,boardName,boardId){
			$("#username").val(username);
			$("#boardInSearchName").val(boardName);
			$("#boardInSearchId").val(boardId);
			$("#username").focus();
		}
		</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					管理版主 &nbsp;
				</div>
				<pg:pager items="${count}" url="manage.do" index="center"
					maxPageItems="30" maxIndexPages="6"
					isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method" />
					<pg:index>
						<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
					</pg:index>

					<table width="100%" border=0 cellpadding="0" cellspacing="0">
						<tr class="banner">
							<td>
								用户名称
							</td>
							<td>
								版面
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr <%if(index%2==1){out.print("class=alternative");}%>>
									<td>
										${model.user.userName }
									</td>
									<td>
										${model.board.boardName}
									</td>
									<td>
										<a
											href="javascript:selectUser('${model.user.userName }','${model.board.boardName }','${model.board.boardId }');"
											class="button">选择删除</a>
										<%--												<a href="" onclick="return confirm('您确定要删除么?');">删除</a>--%>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>

					</table>
				</pg:pager>


				<div class="navigator">
					撤销版主
				</div>
				<form name="form1" action="../editBoard.action?method=manager" method="post">
					撤销
					<input type="text" name="userName" id="username" class="formInput"
						size="20" readonly="readonly" />
					在
					<input type="hidden" name="boardId" id="boardInSearchId" />
					<input type="text" name="boardName" id="boardInSearchName"
						class="formInput" size="20" readonly="readonly" />
					版的版主权限
					<input type="submit" name="subimt" class="button" value="操作" />
				</form>
			</div>
		</div>

		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
