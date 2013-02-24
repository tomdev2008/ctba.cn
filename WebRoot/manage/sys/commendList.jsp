<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<%@include file="../include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>推荐链接列表</title>
		<script>
		function checkForm(){
			var url = document.getElementById("url").value;
			var label = document.getElementById("label").value;
			if(url==""||label==""){
				alert("请正确输入数据");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					推荐链接 &nbsp;&nbsp;
					<form style="display: inline" method="post"
						onsubmit="return checkForm();" action="commend.action?method=save">
						标题&nbsp;&nbsp;
						<input name="label" id="label" class="formInput" />
						&nbsp;&nbsp;链接&nbsp;&nbsp;
						<input name="link" id="url" class="formInput" />
						&nbsp;&nbsp;类型&nbsp;&nbsp;
						<select name="type">
							<option value="100">
								首页
							</option>
							<option value="0">
								论坛
							</option>
							<option value="1">
								博客
							</option>
							<option value="2">
								小组
							</option>
							<option value="3">
								新闻
							</option>
							<option value="4">
								专题
							</option>
							<option value="5">
								投票
							</option>
							<option value="6">
								用户
							</option>
							<option value="7">
								其他
							</option>
						</select>
						<input type="submit" class="button" value="推荐" />
						&nbsp;
						<a href="commend.action?method=list">全部</a>
					</form>
					&nbsp;&nbsp;
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="commend.action" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="type" />
						<tr class="banner">
							<td>
								NO.
							</td><td>
								标题
							</td>
							<td>
								链接
							</td>
							<td>
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
										${model.idx }
									</td><td>
										${model.label }
									</td>
									<td>
										<a href="${model.link }">${model.link }</a>
									</td>
									<td>
										<a href="commend.action?method=list&type=${model.type }">
											<c:if test="${model.type eq 100 }">首页</c:if> <c:if
												test="${model.type eq 0 }">论坛</c:if> <c:if
												test="${model.type eq 1 }">博客</c:if> <c:if
												test="${model.type eq 2 }">小组</c:if> <c:if
												test="${model.type eq 3 }">新闻</c:if> <c:if
												test="${model.type eq 4 }">专题</c:if> <c:if
												test="${model.type eq 5 }">投票</c:if> <c:if
												test="${model.type eq 6 }">用户</c:if> <c:if
												test="${model.type eq 7 }">其他</c:if> </a>
									</td>
									<td>
										<a href="commend.action?method=up&id=${model.id }"
											class="button">上移</a>
										<a href="commend.action?method=off&id=${model.id }"
											class="button">下移</a>
										<a href="commend.action?method=delete&id=${model.id }"
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