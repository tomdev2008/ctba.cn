<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>封禁列表</title>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
			<div id="content">
				<div class="navigator">
					封禁列表
				</div>
				<logic:notEmpty name="models">

					<pg:pager items="${count}" url="manage.do" index="center"
						maxPageItems="30" maxIndexPages="6"
						isOffset="<%=false%>"
						export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
						scope="request">
						<pg:param name="method" />
						<pg:param name="bid" />
						<pg:index>
						<jsp:include flush="true" page="../../common/jsptags/manage.jsp"></jsp:include>
					</pg:index>

						<logic:iterate id="map" indexId="index" type="java.util.Map"
							name="models">
							<div style="width:100%;padding-bottom:10px;" <%if(index%2==0){ %>
								class="alternative" <%} %>>
								<div style="float:left;width:80%;text-align:left;">
									[${map.board.boardName }版] ${map.forbidden.fbnUser
									}由于${map.forbidden.fbnReason }被 ${map.forbidden.fbnBm }
									封禁${map.forbidden.fbnDays }天 ---${map.forbidden.fbnTime }
								</div>
								<div style="float:left;width:20%;text-align:right;">
									<a
										href="manage.do?method=delForbidden&fbnId=${map.forbidden.fbnId }"
										onclick="return confirm('您确实要解除么？');" class="button">解除</a>
								</div>
							</div>
						</logic:iterate>
						<pg:index>
							<jsp:include flush="true" page="../../common/jsptags/simple.jsp"></jsp:include>
						</pg:index>
					</pg:pager>
				</logic:notEmpty>

				<logic:notEmpty name="bbsForbiddens">
					<logic:iterate id="u" indexId="index" name="bbsForbiddens">
						<div style="width:100%;" <%if(index%2==0){ %>
							class="alternative" <%} %>>
							<div style="float:left;width:80%;text-align:left;">
								[封禁全站] ${u.userName }
							</div>
							<div style="float:left;width:20%;text-align:left;">
							
								<a href="bbsForbidden.action?userName=<comm:url value="${u.userName }"/>"
									onclick="return confirm('您确实要解除么？');" class="button">解除 </a>
							</div>
						</div>
					</logic:iterate>
				</logic:notEmpty>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>
