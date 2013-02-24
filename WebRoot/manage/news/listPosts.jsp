<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>管理用戶投遞</title>
		<script>
		//***************
		//switch the state
		//***************
		function state(id){
			 $.get("newsManage.shtml",
				  { method:"postState",pid:id,loadType:"ajax"},
				  function(data){
				  		$("#state_"+id).html(data);
				  }
			);
		}
		</script>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					管理用戶投遞 &nbsp; <a href="newsManage.shtml?method=listPosts" class="button">未通过</a>&nbsp;<a href="newsManage.shtml?method=listPosts&all=true" class="button">全部</a>
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="newsManage.shtml" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="all" />
						<tr class="banner">
							<td>
								题目
							</td>
							<td>
								分类
							</td>
							<td>
								作者
							</td>
							<td>
								状态
							</td>
							<td>
								创建
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
										${model.title}
									</td>
									<td>
										<comm:select items="${requestScope.cats}" name="category" label="name" value="id" selected="${model.cat}"/>
									</td>
									<td>
										${model.author}
									</td>
									<td>
<%--									${model.done }--%>
									<div id="state_${model.id }" class="button" style="cursor:pointer" onclick="javascript:state('${model.id }');">
										<logic:equal value="0" name="model" property="done">
									NEW
									</logic:equal>
										<logic:equal value="1" name="model" property="done">
										DONE
									</logic:equal>
</div>
									<td>
										${model.createTime}
									</td>
									<td>
										<a href="newsManage.shtml?method=deletePost&pid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
										<a href="newsManage.shtml?method=newsForm&pid=${model.id }"
											class="button">到新聞</a>
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
