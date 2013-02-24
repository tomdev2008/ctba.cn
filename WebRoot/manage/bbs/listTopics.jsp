<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>文章列表</title>
		<script type="text/javascript">
		function selectTopic(topicName,topicId){
			$("#topicName").val(topicName);
			$("#topicId").val(topicId);
			$("#topicName").focus();
		}
		function showBoardFrame(){
			if($("#boardFrame").css("display")=="none"){
				$("#boardFrame").css("display","");
				$("#boardFrame").attr("src","${context}/board.do?method=search");
			}else{
				$("#boardFrame").css("display","none");
			}
		}
		function deal(type){
			if(!confirm("您确定要执行此操作么? "))return;
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
					文章列表
					&nbsp;&nbsp;
					<a href="#" onclick="deal('top');"><img
							src="../images/mark/ding.gif" alt="切换置顶" width="15" height="13"
							border="0" /> </a>
					<a href="#" onclick="deal('prime');"><img
							src="../images/mark/jing.gif" width="15" alt="切换精华" height="13"
							border="0" /> </a>
					<a href="#" onclick="deal('remind');"><img
							src="../images/mark/jian.gif" alt="切换提醒" width="15" height="13"
							border="0" /> </a>
					<a href="#" onclick="deal('re');"><img
							src="../images/mark/notRe.gif" width="16" alt="切换不可回复"
							height="16" border="0" /> </a>
					<a href="#" onclick="deal('delete');"><img
							src="../images/mark/del.gif" alt="删除" width="16" height="16"
							border="0" /> </a>
							
				</div>
				<pg:pager items="${count}" url="manage.do" index="center"
					maxPageItems="30" maxIndexPages="6"
					isOffset="<%=false%>"
					export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="method" value="listTopics" />
					<pg:param name="type" />
					<pg:param name="bid" />
					<pg:index>
						<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
					</pg:index>
					<form name="dealTopics" id="dealTopics"
						action="manage.do?method=dealTopics" method="post">
						<table border="0" width="100%" cellpadding="1" cellspacing="0">
							<tr class="navigator">
							<td>
							&nbsp;
							<community:select items="${boards}" name="translateToBoard" label="boardName" value="boardId"/>
							<input type="submit" class="button" name="translateButton" id="translateButton" value="转移文章" onclick="return confirm('您确定要转移选定文章？');"/>
										</td>
							</tr></table>
						<table border=0 width="100%" cellpadding="1" cellspacing="0">
							<tr class="banner">
								<td width="50">
<%--									标记--%>
								</td>
								<td>
									题目
								</td>

								<td>
									版面
								</td>
								<td>
									作者
								</td>
								<td>
									时间
								</td>
								<td>
									回复
								</td>
								<td>
									点击
								</td>
								<td>
									操作
								</td>
							</tr>
							<logic:notEmpty name="models">
								<logic:iterate id="model" indexId="index" name="models">
									<tr <%if(index%2==1){ %> class="alternative" <%} %>>
										<td>
<%--											${model.topicTag }--%>
										</td>
										<td>
											<a target="_blank"
												href="${context }/t/${model.topic.topicId}">${model.topic.topicTitle}
											</a>
										</td>
										<td>
											${model.board.boardName}
										</td>
										<td>
											${model.topic.topicAuthor}
										</td>
										<td>
											${model.topic.topicTime}
										</td>
										<td>
											${model.topic.topicReNum}
										</td>
										<td>
											${model.topic.topicHits}
										</td>
										<td>
											<input type="checkbox" name="check_${model.topic.topicId }" />
											选择
											<a href="subManage.shtml?method=subjectTopicForm&tid=${model.topic.topicId }" class="button">到专题</a>
											<a href="manage.shtml?method=listReplies&tid=${model.topic.topicId }" class="button">看回复</a>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						
						<input type="hidden" name="dealType" id="dealType" value="none" />
					</form>
				</pg:pager>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
