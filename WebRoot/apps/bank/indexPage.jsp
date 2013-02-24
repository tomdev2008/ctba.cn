<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>CT币中心&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="/bank/" title="CT币中心">CT币中心</a>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<jsp:include flush="true" page="_bankBlock.jsp" />
					<div class="mid_block lightgray_bg">
						<jsp:include page="_tab.jsp" flush="true">
							<jsp:param name="currTab" value="home" />
						</jsp:include>
					</div>
					<div class="pt5">
						<div class="title">
							历史操作记录
						</div>
						<div class="ew">
							<a class="rss mr10" href="bank.action?method=logs">MORE</a>
						</div>
						<div id="event_all" class="clearfix" style="padding-top:5px">
							<ul>
								<logic:notEmpty name="newLogs">
									<logic:iterate id="model" name="newLogs" scope="request" indexId="index">
										<li>
											<div class="fleft w450">
												<community:limit maxlength="145" value="${model.target}" />
											</div>
											<div class="fright font_mid color_gray pr5">
												<community:date limit="16" start="2" value="${model.updateTime}" />
											</div>
										</li>
									</logic:iterate>
								</logic:notEmpty>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>