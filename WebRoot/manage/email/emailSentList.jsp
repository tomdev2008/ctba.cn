<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>发件箱</title>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					发件箱 &nbsp;<a href="emailManage.shtml?method=form" class="button">发送邮件</a>
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="emailManage.shtml" index="center"
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
								创建
							</td>
							<td>
								更新
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="emailList">
							<logic:iterate id="model" name="emailList" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
									<comm:limit maxlength="30" value="${model.title}"/>
										
									</td>
									<td>
										${model.username}
									</td><td>
										${model.updateTime}
									</td>
									<td>
										<a href="emailManage.shtml?method=delete&eid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
										<a
											href="emailManage.shtml?method=form&eid=${model.id }"
											class="button">編輯</a>
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
