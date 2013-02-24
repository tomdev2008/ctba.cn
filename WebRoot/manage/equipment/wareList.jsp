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
		<title>装备列表</title>
		<script>
		//***************
		//switch the state
		//***************
		function state(id){
			 $.get("equipmentManage.shtml",
				  { method:"toggleState",wid:id,loadType:"ajax"},
				  function(data){
				  	    if(data.indexOf("1")>=0){
				  	    	$("#state_"+id).html("已通过");
				  	    }else{
				  	   		$("#state_"+id).html("待审批");
				  	    }
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
					装备列表 &nbsp;&nbsp;&nbsp;<a href="equipmentManage.shtml?method=listEquipments&state=1" class="button">已通过</a>&nbsp;&nbsp;<a href="equipmentManage.shtml?method=listEquipments" class="button">全部</a>&nbsp;&nbsp;<a  class="button" href="equipmentManage.shtml?method=listEquipments&state=0">待审批</a>
				</div>
				<table width="100%" cellpadding="1" cellspacing="0" border=0>
					<pg:pager items="${count}" url="equipmentManage.shtml"
						index="center" maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<tr class="banner">
							<td>
								题目
							</td>
							<td>
								点击
							</td>
							<td>
								作者
							</td>
							<td>
								创建
							</td>
							<td>
								更新
							</td>
							<td>
								状态
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
						<logic:notEmpty name="models">
							<logic:iterate id="model" name="models" indexId="index">
								<tr <%if(index%2==1){ %> class="alternative" <%} %>>
									<td>
										${model.name}
									</td>
									<td>
										${model.hits }
									</td>

									<td>
										${model.username }
									</td>
									<td>
										${model.createTime}
									</td>
									<td>
										${model.updateTime}
									</td>
									<td>
										<div id="state_${model.id }" class="button"
											style="cursor: pointer"
											onclick="javascript:state('${model.id }');">
											<logic:equal value="0" name="model" property="state">
																		待审批
																		</logic:equal>
											<logic:equal value="1" name="model" property="state">
																		已通过
																		</logic:equal>
											<logic:equal value="2" name="model" property="state">
																		已推荐
																		</logic:equal>
										</div>
									</td>
									<td>
										<a href="equipmentManage.shtml?method=delete&wid=${model.id }"
											onclick="return confirm('您确定要删除么？');" class="button">删除</a>
										<a href="../equipment/${model.id }" target="_blank"
											class="button">访问</a>
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