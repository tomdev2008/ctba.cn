<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/ctba.tld" prefix="ctba"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/include.jsp"%>
		<jsp:include page="../_metadataBlock.jsp"><jsp:param name="currPage" value="group"/></jsp:include>
		<title><bean:message key="menu.group.navigate"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage"/></title>
	</head>
	<body>
		<jsp:include page="head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<jsp:include flush="true" page="_groupTypes.jsp"/>
				<c:if test="${not empty topics4User}">
				<div class="left_block">
					<div class="board_sep">
						<div class="gtitle">
							<span class="gtitle_text"><bean:message key="page.group.topic.mine" /></span>
							<span class="group_type">
								&nbsp;
							</span>
						</div>
						<div class="group_home_list">
							<c:forEach items="${topics4User}" var="topic" varStatus="status">
							<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${status.index % 2 == 1}">lightgray_bg</c:if>">
								<li class="col1-5">&nbsp;</li>
								<li class="col17-5">
									<img src="<community:img type="mini" value="${topic.groupModel.face}"/>" height="16" width="16" class="userFace_border" align="absmiddle" title="${topic.groupModel.name}" alt="${topic.groupModel.name}" />&nbsp;&nbsp;<a href="${context }/userpage/<g:url value="${topic.author}"/>">${topic.author }</a>
								</li>
								<li class="col49" style="overflow:hidden">
									<a href="group/topic/${topic.id}" title="${topic.title}"><g:limit value="${topic.title}" maxlength="24" /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.replyCnt }/${topic.hits }</span>
								</li>
								<li class="col20">
									<ctba:wrapgroup group="${topic.groupModel}" maxlength="15"/>
								</li>
								<li class="col12 font_mid color_gray">
									<g:date value="${topic.createTime }" start="5" limit="16" />
								</li>
							</ul>
							</c:forEach>
						</div>
					</div>
				</div>
				</c:if>
				<div class="left_block">
					<div class="board_sep">
						<div onclick="$('#latestGroup').toggle('slow');" class="title">
							<img src="images/ngroups.png" align="absmiddle" />&nbsp;<bean:message key="page.group.newGroup" />
						</div>
						<div id="latestGroup" class="clearfix">
							<c:if test="${not empty newGroupsMap}">
							<c:forEach items="${newGroupsMap}" var="model" varStatus="index">
							<div class="group_wrap">
								<div class="group_pic">
									<a href="group/${model.group.id }"><img src="<community:img value="${model.face }" type="default"/>" title="${model.group.name}" width="60" /></a>
								</div>
								<div class="group_info">
									<h2 class="graylink" style="display:inline">
									<ctba:wrapgroup group="${model.group}" maxlength="25"/>
									</h2>&nbsp;&nbsp;<a href="userpage/<g:url value="${model.group.manager}"/>"><img src="images/icons/vcard_add.png" title="组长：${model.group.manager}" /></a>&nbsp;<span class="font_small color_gray">${model.userCnt }&nbsp;<img src="images/icons/comment.png" align="absmiddle" />&nbsp;${model.topicCnt }</span>
									<br />
									<span class="color_gray"><g:limit value="${model.group.discription}" maxlength="20" />
									</span>
									<c:if test="${not empty model.topic}">
									<br />
									<img src="images/posts_new.gif" align="absmiddle" />&nbsp;<span class="orangelink"><a href="group/topic/${model.topic.id}" title="${model.topic.title}"><g:limit value="${model.topic.title}" maxlength="18" /></a></span>
									</c:if>
								</div>
							</div>
							</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
				<div class="left_block">
					<div class="board_sep">
						<div class="gtitle">
							<span class="gtitle_text"><bean:message key="page.group.topic.newest" /></span>
							<span class="group_type">
								&nbsp;
							</span>
						</div>
						<div class="group_home_list">
						<c:if test="${not empty newTopics}">
						<c:forEach items="${newTopics}" var="topic" varStatus="status">
							<ul class="column_5c list_sp2 clearfix radius_all <c:if test="${status.index % 2 == 1}">lightgray_bg</c:if>">
								<li class="col1-5">&nbsp;</li>
								<li class="col17-5">
									<img src="<community:img type="mini" value="${topic.groupModel.face}"/>" height="16" width="16" class="userFace_border" align="absmiddle" title="${topic.groupModel.name}" alt="${topic.groupModel.name}" />&nbsp;&nbsp;<a href="${context }/userpage/<g:url value="${topic.author}"/>">${topic.author }</a>
								</li>
								<li class="col49" style="overflow:hidden">
									<a href="group/topic/${topic.id}" title="${topic.title}"><g:limit value="${topic.title}" maxlength="24" /></a>&nbsp;&nbsp;<span class="font_mid color_orange">${topic.replyCnt }/${topic.hits }</span>
								</li>
								<li class="col20">
									<ctba:wrapgroup group="${topic.groupModel}" maxlength="15"/>
								</li>
								<li class="col12 font_mid color_gray">
									<g:date value="${topic.createTime }" start="5" limit="16" />
								</li>
							</ul>
						</c:forEach>
						</c:if>
						</div>
					</div>
				</div>
				<div class="left_block">
					<div class="board_sep">
						<div onclick="$('#hotGroup').toggle('slow');" class="title">
							<img src="images/hotgroup.png" align="absmiddle" />&nbsp;<bean:message key="page.group.hotGroup" />
                       &nbsp;&nbsp; <a class="rss" href="rank.action?method=group">MORE</a> 
						</div>
						<div id="hotGroup" class="clearfix">
							<c:if test="${not empty hotGroupsMap}">
							<c:forEach items="${hotGroupsMap}" var="model" varStatus="index">
							<div class="group_wrap">
								<div class="group_pic">
									<a href="group/${model.group.id }"><img src="<community:img value="${model.face }" type="default"/>" title="${model.group.name}" width="60" /></a>
								</div>
								<div class="group_info">
									<h2 class="graylink" style="display:inline"><a href="group/${model.group.id }"><g:limit maxlength="25" value="${model.group.name}" /></a></h2>&nbsp;&nbsp;<a href="userpage/<g:url value="${model.group.manager}"/>"><img src="images/icons/vcard_add.png" title="<bean:message key="page.group.captain" />:${model.group.manager}" /></a>&nbsp;<span class="font_small color_gray">${model.userCnt }&nbsp;<img src="images/icons/comment.png" align="absmiddle" />&nbsp;${model.topicCnt }</span>
									<br />
									<span class="color_gray">
									<g:limit value="${model.group.discription}" maxlength="20" />
									<c:if test="${not empty model.topic}">
									<br />
									<img src="images/posts_new.gif" align="absmiddle" />&nbsp;<span class="orangelink"><a href="group/topic/${model.topic.id}" title="${model.topic.title}"><g:limit value="${model.topic.title}" maxlength="18" /></a></span>
									</c:if>
									</span>
								</div>
							</div>
							</c:forEach>
							</c:if>
						</div>
					</div>
					<jsp:include page="../ad/_fanqieshuAdBlock.jsp" />
					<%--<jsp:include page="../_adBlockMiddle.jsp" ></jsp:include>--%>
				</div>
				<div class="left_block">
					<div class="board_sep">
						<div class="title">
							<img src="images/groupix.png" align="absmiddle" />&nbsp;<bean:message key="page.group.image.newest" />
						</div>
						<div id="newImages" class="clearfix">
							<c:if test="${not empty images}">
							<c:forEach items="${images}" var="model" varStatus="index">
							<div class="group_photo_wrap">
								<div class="group_photo_pic">
									<a class="photo" href="group/gallery/photo/${model.id }">
										<img src="<community:img value="${model.path }" type="default"/>" title="${model.name}" />
									</a>
								</div>
								<div class="group_photo_info">
									<span class="color_orange"><g:limit maxlength="25" value="${model.name}" /></span>
									<br />
									<span class="color_gray">
										<bean:message key="page.group.image.createTime" />:<g:date limit="11" start="0" value="${model.createTime }" />
										<br />
										<bean:message key="page.group.image.creator" />:<a href="userpage/<g:url value="${model.username }"/>">${model.username }</a>
									</span>
								</div>
							</div>
							</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<div id="area_right">
				<jsp:include flush="true" page="_groupSearch.jsp"></jsp:include>
				<%--<jsp:include flush="true" page="../_commendBlock.jsp"></jsp:include>--%>
				<c:if test="${not empty newActivityList}">
				<div class="state">
					<div class="titles" onclick="$('newActivities').toggle();"><bean:message key="page.group.activity.newest" /></div>
					<div id="newActivities">
						<ul>
							<c:forEach items="${newActivityList}" var="model" varStatus="index">
							<li class="tlist_wrap clearfix" <c:if test='${status.index % 2 == 1}'>lightgray_bg radius_all</c:if>>
								<div class="tlist_icon icons_newticket"></div>
								<div class="tlist_text">
									<a href="activity/${model.activity.id }" class="stars_border">
										<community:limit value="${model.activity.title }"  maxlength="50"/>
									</a>
									<span class="font_small color_gray">
										<g:date limit="11" start="2" value="${model.activity.createTime }" />
									</span>
								</div>
							</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				</c:if>
				<c:if test="${not empty randomGroups}">
				<div class="state">
					<div class="titles" onclick="$('#radomGroups').toggle();"><bean:message key="page.group.random" /></div>
					<div id="radomGroups">
						<ul class="digglist2">
							<c:forEach items="${randomGroups}" var="model" varStatus="index">
							<li>
								<div class="diggmini2">
									<img src="<community:img value="${model.face }" type="sized" width="32" />" width="32" height="32" />
								</div>
								<div class="digglt2">
									<a href="group/${model.id }" >${model.name}</a><br />
									<span class="color_gray"><bean:message key="page.group.captain" />: ${model.manager }</span>
								</div>
							</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				</c:if>
				<jsp:include page="../_adBlockRight.jsp"></jsp:include>
				<c:if test="${not empty hotUsers}">
				<div class="state">
					<div class="titles" onclick="$('#activeUser').toggle();">
						<bean:message key="page.group.hotUser" />
					</div>
					<div id="activeUser">
						<ul class="active_users clearfix">
							<c:forEach items="${hotUsers}" var="model" varStatus="index">
							<li><a href="u/${model[0]}" class="stars_border"><img src="<community:img value="${model[1] }" type="mini" />" height="30" width="30" /><span class="user_name">${model[2] }</span></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				</c:if>
				<div class="state">
					<ul class="infobar">
						<li class="icons_group"><bean:message key="page.group.groupCnt" />&nbsp;<span class="color_orange">${groupCnt }</span></li>
						<c:if test="${not empty newUsers}">
						<li class="icons_weather_sun">
							<span class="pointer" onclick="$('#newuser').toggle('slow');"><bean:message key="page.group.member.newest" /></span>
						</li>
						<li id="newuser">
							<ul>
								<c:forEach items="${newUsers}" var="model" varStatus="index">
								<li><a href="u/${model.userId}"><img src="<community:img value="${model.userFace }" type="mini" />" height="16" width="16" class="userFace_border" align="absmiddle" /></a>&nbsp;${model.userName }</li>
								</c:forEach>
							</ul>
						</li>
						</c:if>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
	</body>
</html>