<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>系统配置信息</title>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					系统配置信息
				</div>
				<form name="form1" action="bbsConfig.action" method="post">
				    <input type="hidden" name="id" size="30"
									value="${model.id }" />
					<table border=0 cellpadding="6" cellspacing="0" width="100%">
						<tr></tr>
						<tr>
							<td align="right">
								系统URL
							</td>
							<td>
								<input type="text" name="domainRoot" size="30"
									value="${model.domainRoot }" />
							</td>
						</tr>
						<tr>
							<td align="right">
								RSS前缀
							</td>
							<td>
								<input type="text" name="rssTitlePrefix" size="30"
									value="${model.rssTitlePrefix }" class="formInput" />
							</td>
						</tr>
						<tr>
							<td align="right">
								邮箱地址
							</td>
							<td>
								<input type="text" name="mailSmtpAddress" size="30"
									value="${model.mailSmtpAddress }" class="formInput" />
							</td>
						</tr>
						<tr>
							<td align="right">
								邮箱主机
							</td>
							<td>
								<input type="text" name="mailSmtpHost" size="30"
									value="${model.mailSmtpHost }" class="formInput" />
							</td>
						</tr>
						<tr>
							<td align="right">
								邮箱用户
							</td>
							<td>
								<input type="text" name="mailSmtpUsername" size="30"
									value="${model.mailSmtpUsername }" class="formInput" />
							</td>
						</tr>
						<tr>
							<td align="right">
								邮箱密码
							</td>
							<td>
								<input type="text" name="mailSmtpPassword" size="30"
									value="${model.mailSmtpPassword }" class="formInput" />
							</td>
						</tr>

						<tr>
							<td align="right" valign="top">
								过滤词(换行(\n)或分号(;)分隔)
							</td>
							<td>
								<textarea name="forbiddenWords" rows="3" cols="50"
									class="formTextarea">${model.forbiddenWords }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
							</td>
							<td>
								<input type="submit" name="submit" value="更改配置" class="button" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>