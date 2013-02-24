<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="include.jsp"%>
		<title>管理系統用戶</title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					管理系統用戶 &nbsp;<a href="sysAdmin.shtml?method=adminForm" class="button">新建用户</a>
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="newsManage.shtml" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<tr class="banner">
							<td>
								名称
							</td>
							<td>
								创建
							</td><td>
								更新
							</td>
							<td>
								操作
							</td>
						</tr>
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
										${model.username}
									</td>
									<td>
										${model.createTime}
									</td><td>
										${model.updateTime}
									</td>
									<td>
										<a href="sysAdmin.shtml?method=deleteAdmin&username=${model.username }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
										<a
											href="sysAdmin.shtml?method=adminForm&username=${model.username }"
											class="button">編輯</a>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
						<tr>
							<pg:index>
								<jsp:include flush="true" page="../common/jsptags/manage.jsp"></jsp:include>
							</pg:index>

						</tr>
					</pg:pager>
				</table>
			</div>
		</div>

		<jsp:include page="bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
