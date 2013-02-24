<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>装备秀代码&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		 <jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="equipment/">装备秀</a>&nbsp;&gt;&nbsp;装备秀代码
				</div>
				<div id="sidebar" class="fleft clearfix">
					 <jsp:include flush="true" page="_userBar.jsp" />
                    <jsp:include page="../../_adBlockMini.jsp"/>
				</div>
				<div id="mid_column" class="fright">
					<style type="text/css">
						#builder_form p {
							margin: 8px 8px 18px 8px
						}
					</style>
					<div id="builder_form" style="padding:8px" class="mid_block tabswrap ">
						<h2>
							装备秀代码
						</h2>
						<div class="color_gray">
						想要在你的 Blog 中实时更新你在扯谈社的装备信息？<br >
						很简单，只需要将下面的代码粘贴到你的 Blog 模板或想要显示它的地方:)
						</div>
						<p>
							<textarea name="ctba_share_code" style="padding: 5px; height: 60px; width: 500px; font-size: 13px; font-family: 'Courier New',Courier,monospace;">&lt;script type="text/javascript" src="http://www.ctba.cn/equipmentWidget.action?method=widget&uid=${user.userId }&limit=${limit }&width=${width }&hidelogo=${hidelogo }"&gt;&lt;/script&gt;</textarea>
						</p>
						<h2>自定义风格</h2>
						<form action="equipmentWidget.action?method=builder" method="post">
							<p style="background:#fffddd;padding:5px 10px">
								宽度&nbsp;&nbsp;<input value="${width }" name="width" id="width" style="width:80px;padding:1px" />&nbsp;px&nbsp;&nbsp;
								条数&nbsp;&nbsp;<input value="${limit }" name="limit" id="limit" style="width:80px;padding:1px" />&nbsp;&nbsp;
								隐藏标志<input type="checkbox" name="hidelogo" <c:if test="${not empty hidelogo}">checked="checked"</c:if> />&nbsp;&nbsp;
								<input type="submit" class="input_btn" value="预览"/>
							</p>
						</form>
						<h2 style="display:block;padding-bottom:5px">装备秀预览</h2>
						<script src="equipmentWidget.action?method=widget&uid=${user.userId }&limit=${limit }&width=${width }&hidelogo=${hidelogo }"></script>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include page="_right.jsp" flush="true" />
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>