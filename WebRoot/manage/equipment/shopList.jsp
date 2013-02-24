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
		<title>店铺列表</title>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					店铺列表 &nbsp;
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="shopManage.action" index="center"
						maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<tr class="banner">
							<td>
								名称
							</td>
							<td>
								点击
							</td>
							<td>
								店主
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
						<tr>
							<pg:index>
								<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
							</pg:index>
						</tr>
						<logic:notEmpty name="modelList">
							<logic:iterate id="model" name="modelList" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
										${model.name}
									</td>
									<td>
										${model.hits }
									</td>

									<td>
										<%--										<a href="equipment.shtml?method=view&wid=${model.id }">--%>
										<%--									<img src="<community:img value="${model.image }" type="default"/>" title="${model.name }" width="115" />--%>
										<%--									</a>--%>
										${model.username }
									</td>
									<td>
										${model.createTime}
									</td>
									<td>
										${model.updateTime}
									</td>
									<td>
										<a target="_blank" href="../equipment/shop/${model.id }"
											class="button">访问</a>
										<a href="shopManage.action?method=form&id=${model.id }"
											class="button">编辑</a>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</pg:pager>
				</table>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>