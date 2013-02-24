<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>扯谈分享秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp"><jsp:param name="submenu" value="app" /></jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="share/">分享</a>&nbsp;&gt;&nbsp;扯谈分享秀
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include page="../../_operationBlock.jsp"></jsp:include>
					<jsp:include page="../../_adBlockMini.jsp"></jsp:include>
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="normal"><a href="share.shtml?method=shares">我的分享</a></li><li class="normal"><a href="share.shtml?method=shares&type=all">大家的分享</a></li><li class="normal"><a href="share.action?method=myComments">我收到的评论</a></li><li class="current">分享秀</li>
						</ul>
					</div>
					<style type="text/css">
						#builder_form p {
							margin: 8px 8px 18px 8px
						}
					</style>
					<div id="builder_form" style="padding:8px" class="mid_block tabswrap ">
						
						<h2>
							分享秀代码
						</h2>
						
						<div class="color_gray">
						想要在你的 Blog 中实时更新你在扯谈社的分享信息？<br >
						很简单，只需要将下面的代码粘贴到你的 Blog 模板或想要显示它的地方:)
						</div>
						
						<p>
							<textarea name="ctba_share_code" style="padding: 5px; height: 60px; width: 500px; font-size: 13px; font-family: 'Courier New',Courier,monospace;">&lt;script type="text/javascript" src="http://www.ctba.cn/share.action?method=widget&uid=${user.userId }&limit=${limit }&width=${width }&hidelogo=${hidelogo }"&gt;&lt;/script&gt;</textarea>
						</p>
						<h2>自定义风格</h2>
						<form action="share.action?method=builder" method="post">
							<p style="background:#fffddd;padding:5px 10px">
								宽度&nbsp;&nbsp;<input value="${width }" name="width" id="width" style="width:80px;padding:1px" />&nbsp;px&nbsp;&nbsp;
								条数&nbsp;&nbsp;<input value="${limit }" name="limit" id="limit" style="width:80px;padding:1px" />&nbsp;&nbsp;
								隐藏标志<input type="checkbox" name="hidelogo" <c:if test="${not empty hidelogo}">checked="checked"</c:if> />&nbsp;&nbsp;
								<input type="submit" class="input_btn" value="预览"/>
							</p>
						</form>
						<h2 style="display:block;padding-bottom:5px">分享秀预览</h2>
						<script src="share.action?method=widget&uid=${user.userId }&limit=${limit }&width=${width }&hidelogo=${hidelogo }"></script>
					</div>
				</div>
			</div>
			<div id="area_right">
				<div class="state">
					<div class="titles">
						分享秀预览
					</div>
					<script src="share.action?method=widget&uid=${user.userId }&limit=${limit }&width=${width }&hidelogo=${hidelogo }"></script>
				</div>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>