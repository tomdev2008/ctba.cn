<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>投票列表</title>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					投票列表
					&nbsp;&nbsp;
				</div>
				<pg:pager items="${count}" url="manage.do" index="center"
					maxPageItems="30" maxIndexPages="6"
					isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method" value="listTopics" />
					<pg:param name="bid" />
					<pg:index>
						<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
					</pg:index>

						<table border=0 width="100%" cellpadding="1" cellspacing="0">
							<tr class="banner">
								<td>
									题目
								</td>
								<td>
									作者
								</td>
								<td>
									时间
								</td>
								<td>
									截止
								</td>
								<td>
									操作
								</td>
							</tr>
							<logic:notEmpty name="models">
								<logic:iterate id="model" indexId="index" name="voteMapList">
									<tr <%if(index%2==1){ %> class="alternative" <%} %>>
										<td>
										<a href="../vote.do?method=viewVote&vid=${model.vote.id }" >${model.vote.question}</a>
										</td>
										<td>
										${model.vote.username}
										</td>
										<td>
											${model.vote.updateTime }
										</td>
										<td>
											${model.vote.overTime }
										</td>
										<td>
											<a href="manage.shtml?method=deleteVote&vid=${model.vote.id }" onclick="return confirm('您确定要删除么？');" class="button">删除</a>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
				</pg:pager>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
