<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>编辑小组</title>
		<script type="text/javascript">
			function checkForm(){
				var content = $("#name").val();
				if(content==''){
					alert("名称不能为空");
					return false;
				}var content = $("#topicContent").val();
				if(content==''){
					alert("名称不能为空");
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
					编辑小组
					[${model.name }]
					&nbsp;&nbsp;<a href="groupManage.shtml?method=listGroups" class="button">返回列表</a>
				</div>
				<div id="errorMsg">
				</div>
				<form name="form1" action="groupManage.shtml?method=saveGroup" method="post"
					  onsubmit="return checkForm();" style="padding-left:20px;">
					 <input name="gid" value="${model.id }" type="hidden" />
                        <table cellspacing="10" cellpadding="0" id="group_form">
                            <tr>
                                <td class="name" align="right" ><h2><bean:message key="page.group.form.name" /></h2></td>
                                <td class="items" colspan="2"><input type="text" name="name" id="name" class="formInput" size="50" value="${model.name }" onblur="checkName();" /></td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.description" /></h2></td>
                                <td colspan="2">
                                    <textarea rows="3" name="discription" id="topicContent" class="formTextarea" cols="50">${model.discription}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.authType" /></h2></td>
                                <td colspan="2">
                                    <select name="authLevel" class="formInput">
                                        <option value="0" <c:if test="${not empty model and model.authLevel==0 }">selected</c:if>>
                                            <bean:message key="page.group.form.authType.public" />
                                        </option>
                                        <option value="1" <c:if test="${not empty model and model.authLevel==1 }">selected</c:if>>
                                            <bean:message key="page.group.form.authType.protected" />
                                        </option>
                                        <option value="2" <c:if test="${not empty model and model.authLevel==2 }">selected</c:if>>
                                            <bean:message key="page.group.form.authType.private" />
                                        </option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.type" /></h2></td>
                                <td class="sort"><com:select items="${groupTypes}" name="type" label="name" selected="${model.type}" value="index" /></td>
                                <td class="tags" id="commendTags">
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.tag" /></h2></td>
                                <td colspan="2"><input type="text" name="tags" id="tags" class="formInput" size="50" value="${model.tags }" /></td>
                            </tr><c:if test="${not empty model.face}">
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.image" /></h2></td>
                                <td colspan="2">
                                    <div id="divPreview">
                                            <bean:message key="page.group.form.imageAdded" /><br />
                                            <img src="../<com:img value="${model.face }" type="sized" width="80" />" class="group_border" />
                                    </div>
                                </td>
                            </tr> </c:if>
                            <tr>
                                <td class="name" align="right"><h2><bean:message key="page.group.form.alias" /></h2></td>
                                <td class="items" colspan="2"><span class="color_gray">http://www.ctba.cn/group/</span><input type="text" name="url" id="url" class="formInput" size="30" value="${model.magicUrl }"  /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="2">
                                   <input type="submit" value="<bean:message key="page.common.button.submit" />" class="button" name="submitButton" id="submitButton" />
                                   <input type="button" value="<bean:message key="page.group.form.visit" />" class="button" onclick="location.href='gt.action?method=list&gid=${model.id }';" />
                                </td>
                            </tr>
                        </table>
				</form>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
