<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>扯谈篮球&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			$(function(){
				$("#tabs_gray li.tlink a").click(function(){
					$("#tabs_gray li").attr("class","normal");
					$(this).parent("li").attr("class","current");
					$("div.tabswrap").hide();
					$("#" + $(this).attr("name")).show();
					return false;
				});
				$(".ew span span").click(function(){
					$(".ew span").removeClass("bold");
					$(this).parent("span").addClass("bold");
					$("div.scoretabwrap").hide();
					$("#" + $(this).attr("class")).show();
					return false;
				});
			});
        </script>
	</head>
	<body>
		<jsp:include flush="true" page="../../head.jsp">
		<jsp:param name="submenu" value="channel" />
		</jsp:include>
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;篮球
					</div>
					<div class="fright"></div>
				</div>
				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="../../_operationBlock.jsp" />
					<jsp:include flush="true" page="../../_adBlock.jsp" />
				</div>
				<div id="mid_column" class="fright">
				
				<jsp:include page="../../ad/_raywowAdBlock.jsp" />
				
					<div class="mid_block lightgray_bg">
						<ul id="tabs_gray" class="graylink">
							<li class="current tlink"><a name="tab_bbs_latest_col3">庶民上篮</a></li><li class="normal tlink"><a name="tab_bbs_hot_col3">论坛热门话题</a></li><li class="normal tlink"><a name="tab_group_new_col3">小组最近更新</a></li><li class="normal tlink"><a name="tab_group_latest_col3">小组热门话题</a></li>
						</ul>
					</div>
					<div id="tab_bbs_latest_col3" class="tabswrap">
						<logic:notEmpty name="lastUpdatedTopics">
						<logic:iterate id="topic" name="lastUpdatedTopics" scope="request" indexId="index">
						<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
							<li class="col1-5">
								&nbsp;
							</li>
							<li class="col20-5">
								<a href="u/${topic.user.userId}"><img src="<community:img value="${topic.user.userFace}" type="mini"/>" width="16" height="16" class="userFace_border" alt="${topic.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${topic.user.userId}">${topic.user.userName}</a>
							</li>
							<li class="col63" style="overflow: hidden">
								<a href="topic/${topic.topic.topicId }" title="${topic.topic.topicTitle}"><community:limit value="${topic.topic.topicTitle}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.topic.topicReNum}/${topic.topic.topicHits}</span>
							</li>
							<li class="col15 font_mid color_gray">
								<community:date value="${topic.topic.topicUpdateTime }" start="5" limit="16" />
							</li>
						</ul>
						</logic:iterate>
						</logic:notEmpty>
					</div>
					<div id="tab_bbs_hot_col3" class="hide tabswrap">
						<logic:notEmpty name="hotTopics">
						<logic:iterate id="topic" name="hotTopics" indexId="index">
						<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
							<li class="col1-5">
								&nbsp;
							</li>
							<li class="col20-5">
								<a href="u/${topic.user.userId}"><img src="<community:img value="${topic.user.userFace}" type="mini"/>" width="16" height="16" class="userFace_border" alt="${topic.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${topic.user.userId}">${topic.user.userName}</a>
							</li>
							<li class="col63" style="overflow: hidden">
								<a href="topic/${topic.topic.topicId }" title="${topic.topic.topicTitle}"><community:limit value="${topic.topic.topicTitle}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.topic.topicReNum}/${topic.topic.topicHits}&nbsp;</span>
							</li>
							<li class="col15 font_mid color_gray">
								<community:date value="${topic.topic.topicTime }" start="5" limit="16" />
							</li>
						</ul>
						</logic:iterate>
						</logic:notEmpty>
					</div>
					<div id="tab_group_new_col3" class="hide tabswrap">
						<c:if test="${not empty updatedGroupTopicsMap}">
						<c:forEach items="${updatedGroupTopicsMap}" var="model">
						<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
							<li class="col1-5">
							</li>
							<li class="col20-5">
							<a href="u/${model.user.userId}"><img src="<community:img value="${model.user.userFace}" type="mini"/>" width="16" height="16" class="userFace_border" alt="${model.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${model.user.userId}">${model.user.userName}</a>
							</li>
							<li class="col63" style="overflow: hidden">
								<a href="group/topic/${model.topic.id}"><community:limit value="${model.topic.title}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${model.topic.replyCnt}/${model.topic.hits}&nbsp;</span>
							</li>
							<li class="col15 font_mid color_gray">
							<community:date value="${model.topic.updateTime }" start="5" limit="16" />
							</li>
						</ul>
						</c:forEach>
						</c:if>
					</div>
					<div id="tab_group_latest_col3" class="hide tabswrap">
					<c:if test="${not empty hotGroupTopicsMap}">
						<c:forEach items="${hotGroupTopicsMap}" var="model">
						<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${index % 2 == 1}">lightgray_bg</c:if>">
							<li class="col1-5">
							</li>
							<li class="col20-5">
							<a href="u/${model.user.userId}"><img src="<community:img value="${model.user.userFace}" type="mini"/>" width="16" height="16" class="userFace_border" alt="${model.user.userName}" align="absmiddle" /></a>&nbsp;&nbsp;<a href="u/${model.user.userId}">${model.user.userName}</a>
							</li>
							<li class="col63" style="overflow: hidden">
								<a href="group/topic/${model.topic.id}"><community:limit value="${model.topic.title}" maxlength="30"  /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${model.topic.replyCnt}/${model.topic.hits}&nbsp;</span>
							</li>
							<li class="col15 font_mid color_gray">
							<community:date value="${model.topic.updateTime }" start="5" limit="16" />
							</li>
						</ul>
						</c:forEach>
						</c:if>
					</div>
					<c:if test="${not empty newSubjects}">
					<div class="subject_wrap_nar clearfix">
						<div class="gtitle" style="margin:0 0 5px 0 ">
							<span class="gtitle_text">扯谈专题</span>
							<span class="group_type">
							<b>|</b>&nbsp;
							<a class="gt" href="#">体育文学</a>
							<a class="gt" href="#">视频特辑</a>
							<a class="gt" href="#">名人堂</a>
							</span>
						</div>
						<c:forEach items="${newSubjects}" var="model" varStatus="index">
						<ul>
							<li <c:if test="${index.count % 3 == 0}">class="last"</c:if>>
								<div class="subject_pic_nar">
									<a href="sub/${model.subject.id }" class="blog_border_large">
										<img src="<community:img value="${model.subject.image}" type="default" />" alt="${model.subject.title}" title="${model.subject.title}" width="165" />
									</a>
								</div>
								<div class="subject_intro_nar clearfix">
									<ul>
										<li>
											<span class="sub_name">专辑:</span>
											<span class="sub_info orangelink">
												<a href="sub/${model.subject.id }">${model.subject.title}</a>
											</span>
										</li>
										<li>
											<span class="sub_name">简介:</span>
											<span class="sub_info">${model.subject.description }</span>
										</li>
									</ul>
								</div>
							</li>
						</ul>
						</c:forEach>
					</div>
					</c:if>
					
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="right.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include flush="true" page="../../bottom.jsp"></jsp:include>
	</body>
</html>