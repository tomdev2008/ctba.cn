<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../include.jsp"%>
		<title>缓存信息</title>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true"></jsp:include>
		<div id="wrap">
		
		<div id="content">
				<div class="navigator">
					系统缓存信息
				</div>
				<div>
				${cacheEfficiencyReport}
				</div>
			</div>
			<div id="content">
				<div class="navigator">
					点击缓存信息
				</div>
					<table  cellpadding="2" cellspacing="1" width="300px">
						<tr></tr>
						<tr>
							<td align="right" class="alternative">
								博客点击缓存
							</td>
							<td>
								${blogHitMapCnt }
							</td>
						</tr>
						<tr>
							<td align="right" class="alternative">
								博客日志点击缓存
							</td>
							<td>
								${blogEntryHitMapCnt }
							</td>
						</tr>
						<tr>
							<td align="right" class="alternative">
								论坛话题点击缓存
							</td>
							<td>
								${topicHitMapCnt}
							</td>
						</tr>
						<tr>
							<td align="right" class="alternative">
								小组点击缓存
							</td>
							<td>
								${groupHitMapCnt}
							</td>
						</tr>
						<tr>
							<td align="right" class="alternative">
								小组话题点击缓存
							</td>
							<td>
								${groupTopicHitMapCnt}
							</td>
						</tr>
						
						<tr>
							<td align="right">
							</td>
							<td>
								<a href="manage.do?method=refreshHitMap" class="button" onClick="return confirm('您确定要刷新？这可能要耗费几分钟时间。');">刷新至数据库</a>
							</td>
						</tr>
					</table>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true"></jsp:include>
	</body>
</html>