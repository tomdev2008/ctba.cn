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
		<title>新闻分类</title>
		<script>
		function update(name,code,id){
			$("#name").val(name);
			$("#code").val(code);
			$("#cid").val(id);
		}
		function checkForm(){
			var name= $("#name").val();
			var code= $("#code").val();
			if(name==''){
				alert("请填写名称");
				return false;
			}
			if(code==''){
				alert("请填写代码");
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
					管理新闻分类 &nbsp;
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
								代码
							</td>
							<td>
								是否插件
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
										${model.name}
									</td>
									<td>
										${model.code }
									</td>
									<td>
									<logic:equal value="0" name="model" property="isPlugin">
									否</logic:equal>
									<logic:equal value="1" name="model" property="isPlugin">
									是</logic:equal>
									</td>
									<td>
										${model.createTime}
									</td>
									<td>
										<a href="newsManage.shtml?method=deleteCat&cid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
										<a
											href="javascript:update('${model.name}','${model.code}','${model.id }');"
											class="button">更新</a>
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
				<div class="navigator">
					新闻分类表单
				</div>
				<form name="form3" action="newsManage.shtml" method="post"
					onsubmit="return checkForm();">
					<input type="hidden" name="method" value="saveCat" />
					<input type="hidden" name="cid" id="cid" value="" />
					名称：
					<input type="text" name="name" id="name" class="formInput"
						size="20" />
					&nbsp; 代码：
					<input type="text" name="code" id="code" class="formInput"
						size="20" />
					&nbsp; 是否插件(不在首页显示)
					<select name="isPlugin" id="isPlugin" class="formInput">
					<option value="0">否</option>
					<option value="1">是</option>
					</select>
					<input type="submit" name="subimt" class="button" value="操作" />
				</form>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
