<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%><%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>捐赠扯谈&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<script type="text/javascript">
			//check the form
			function checkForm(){
					var value = $("#donateNum").val();
					if(isNaN(parseInt(value))){
						J2bbCommon.formError("donateNum","请正确填写捐赠金额","bottom");
						return false;
					}
					if(parseInt(value)<=0){
						J2bbCommon.formError("donateNum","捐赠金额必须大于0","bottom");
						return false;
					}
					//$("#submitMsg").val("<bean:message key="page.common.button.submitting"/>");
					//$("#submitMsg").attr("disabled","disabled");
					return true;
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
			<jsp:param name="submenu" value="user" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;
						<a href="/donate/">捐赠扯谈</a>
					</div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">

					<jsp:include flush="true" page="_donateBlock.jsp" />
					<div class="mid_block lightgray_bg">
						<form name="alipaysubmit" method="post"
							action="donate.action?method=confirm" onsubmit="return checkForm();">
							<table align="center">
								<tr>
									<td>
										<img src="images/coins.png" style="width:60px;"  align="absmiddle" />
										&nbsp;&nbsp;<input type="text" name="donateNum" id="donateNum"
											style="height: 38px; width: 180px;font-size:35px">&nbsp;<span style="font-size:20px" class="color_orange">元 RMB</span>
									</td>
								</tr>
								<tr>
									<td>
										 &nbsp;
									</td>
								</tr><tr>
									<td>
										<input type='submit' class="input_btn" name='v_action'
											value='使用支付宝网上安全支付平台捐赠' />
									</td>
								</tr>
							</table>
						</form>

					</div>
					
					<div class="mid_block ">
					<logic:notEmpty name="donateLogs">
					<div class="title">光荣榜</div>
					
					<div id="event_all" class="clearfix">
							<ul>
								
									<logic:iterate id="model" name="donateLogs" indexId="index">
										<li>
											<div class="fleft userface">
												<a href="<ctba:wrapuser user="${model.user}" linkonly="true"/>">
													<img src="<community:img value="${model.user.userFace}" type="sized" width="32" />" width="32" height="32" align="absmiddle"/>
												</a>
											</div>
											<div class="fleft w450">
												${model.eventStr }
											</div>
											<div class="fright color_gray font_mid pr5">
												<community:formatTime time="${model.event.updateTime }"/>
											</div>
										</li>
									</logic:iterate>
								
							</ul>
						</div></logic:notEmpty>
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