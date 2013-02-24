<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="include.jsp"%>
		<title>管理用户</title>
		<script type="text/javascript">
		function selectUser(username){
			$("#username").val(username);
			$("#usernameEditor").val(username);
			$("#usernameBlack").val(username);
			$("#username").focus();
		}
		function showBoardFrame(){
			if($("#boardFrame").css("display")=="none"){
			$("#boardFrame").css("display","");
			$("#boardFrame").attr("src","${context}/board.do?method=search");
			}else{
			$("#boardFrame").css("display","none");
			}
		}
		
		function upScore(){
		}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="head.jsp"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					管理用户 &nbsp;
					<form action="manage.do?method=users" method="post"
						style="display: inline;">
						<select name="userOption">
							<option value="">
								全部用户
							</option>
							<option value="2">
								版主
							</option>
							<option value="1">
								普通用户
							</option>
						</select>
						<input type="text" name="key" value="${key }" class="formInput" />
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
								用户名称
							</td>
							<td>
								权限
							</td>
							<td>
								邮件
							</td>
							<td>
								注册时间
							</td>
							<td>
								上次登录
							</td>
							<td>
								登录IP
							</td>
							<td>
								生日
							</td>

							<td>
								QQ
							</td>
							<td>
								页面计数
							</td>
							<td>
								编辑权限
							</td><td>
								积分
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="userMapList">
							<logic:iterate id="model" name="userMapList" indexId="index">
								<tr <%if(index%2==1){out.print("class=alternative");}%>>
									<td>
										<a href="javascript:selectUser('${model.user.userName }');">
											${model.user.userName} </a>
									</td>
									<td>
										<c:if test="${model.user.userOption <2}">
									用户
									</c:if>
										<c:if test="${model.user.userOption eq 2}">
									版主
									</c:if>
									</td>
									<td>
										${model.mainUser.email }
									</td>
									<td>
										${model.mainUser.regTime }
									</td>
									<td>
										${model.mainUser.loginTime }
									</td>

									<td>
										${model.mainUser.loginIp }
									</td>
									<td>
										${model.mainUser.birthday }
									</td>

									<td>
										${model.mainUser.qq }
									</td>
									<td>
										${model.user.userPageCount }
									</td>
									<td>
										<c:if test="${model.user.userIsEditor eq 1}">
									网站编辑
									</c:if>
										<c:if test="${not (model.user.userIsEditor  eq 1)}">
									无
									</c:if>
									</td>
									<td>
										${model.userScore }
									</td>
									<td>
										<a href="javascript:selectUser('${model.user.userName }');"
											class="button">选择</a>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>

					</table>
				</pg:pager>
				<br class="clear" />
				<%-- 
				
				<div class="navigator">
					黑名单
				</div>
				<form name="form3" action="manage.shtml" method="post">
					取消
					<input type="hidden" name="method" value="saveBlack" />
					<input type="hidden" name=type value="0" />
					<input type="text" name="username" id="usernameBlack"
						class="formInput" size="20" readonly="readonly" />
					的访问权限
					<input type="submit" name="subimt" class="button" value="操作" />
				</form>
--%>
				<div class="navigator">
					管理版主
				</div>
				<form name="form1" action="../editBoard.action?method=manager"
					method="post">
					切换
					<input type="text" name="userName" id="username" class="formInput"
						size="20" readonly="readonly" />
					在
					<input type="hidden" name="boardId" id="boardInSearchId" />

					<input type="text" name="boardName" id="boardInSearchName"
						class="formInput" size="20" readonly="readonly" />
					<div style="display: inline; position: relative">
						<a href="javascript:showBoardFrame();"><img
								src="images/boardSearch.gif" border=0 height="20" alt="搜索版面" />
						</a>
						<div style="border: 1px #fff solid; position: absolute">
							<iframe name="boards" id="boardFrame" width="330" height="400"
								style="visibility: inherit;display:none;position:relative;border:1px #fff solid;z-index: 222"
								frameborder="1"></iframe>
						</div>
					</div>
					版的版主权限
					<input type="submit" name="subimt" class="button" value="操作" />
				</form>
				<div class="navigator">
					管理编辑
				</div>
				<form name="form2" action="../dealEditor.action" method="post">
					切换
					<input type="text" name="userName" id="usernameEditor"
						class="formInput" size="20" readonly="readonly" />
					的编辑权限
					<input type="submit" name="subimt" class="button" value="操作" />
				</form>


			</div>
		</div>
		<jsp:include flush="true" page="bottom.jsp"></jsp:include>
	</body>
</html>
