<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>回复列表</title>
		<script type="text/javascript">
		function selectTopic(topicName,topicId){
			$("#topicName").val(topicName);
			$("#topicId").val(topicId);
			$("#topicName").focus();
		}
		function deal(type){
			if(!confirm("您确定要执行"+type+"操作么? "))return;
		    $("#dealType").val(type);
			document.getElementById("dealTopics").submit();
		}
		</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
				<c:if test="${not empty parentTopic}">
				<a href="../t/${parentTopic.topicId }">${parentTopic.topicTitle }</a>的
				</c:if>
					回复列表 &nbsp;&nbsp;${count}篇回复&nbsp;&nbsp;
					<a class="button" href="manage.shtml?method=listReplies">全部回复</a>
				
				</div>
				<pg:pager items="${count}" url="manage.do" index="center"
					maxPageItems="30" maxIndexPages="6"
					isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method"   />
					<pg:param name="type" />
					<pg:param name="tid" />
					<pg:index>
						<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
					</pg:index>

					<form name="dealTopics" id="dealTopics"
						action="manage.do?method=dealTopics" method="post">
						<table border=0 width="100%" cellpadding="1" cellspacing="0">
							<tr class="banner">
								<td>
									原文
								</td>
								<td>
									原文版面
								</td>
								<td>
									作者
								</td>
								<td>
									时间
								</td>
								<td>
									操作
								</td>
							</tr>
							<logic:notEmpty name="models">
								<logic:iterate id="model" indexId="index" name="models">
									<tr class="alternative">
										<td>
											<a  
												href="manage.shtml?method=listReplies&tid=${model.topic.topicId}">${model.topic.topicTitle}
											</a>
										</td>
										<td>
											${model.board.boardName}
										</td>
										<td>
											${model.reply.topicAuthor}
										</td>
										<td>
											${model.reply.topicTime}
										</td>
										<td>
										<a class="button" href="manage.shtml?method=deleteReply&tid=${parentTopic.topicId }&id=${model.reply.topicId }" onclick="return confirm('您确定要删除这篇回复么?');">刪除</a>
											
										</td>
									</tr>
									<tr  >
										<td colspan="5">
										<community:topic value="${model.reply.topicContent}"/>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
					</form>
				</pg:pager>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
