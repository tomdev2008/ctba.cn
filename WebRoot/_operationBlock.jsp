<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
	<c:if test="${not empty __sys_username}">
	<div class="side_block radius_all">
		<div class="side_title">
			<h2><b>我的扯谈</b></h2>
		</div>
		<div class="side_list">
			<ul>
				<li class="icons_house">
					<h2 class="fleft"><a href="${context }/userpage/">主页</a></h2>
					<span class="fright graylink2"><a href="${context }/setting">修改</a></span>
				</li>
				<li class="icons_email">
					<h2 class="fleft"><a href="${context }/messages">信件</a></h2>
					<span class="fright graylink2"><a href="${context }/message.action?method=form">创建</a></span>
				</li>
				<li class="icons_world">
					<h2 class="fleft"><a href="${context }/bg.action?method=view">日志</a></h2>
					<span class="fright graylink2"><a href="${context }/blog/newentry/">撰写</a></span>
				</li>
				<li class="icons_group">
					<h2 class="fleft"><a href="${context }/group/mine/">小组</a></h2>
					<span class="fright graylink2"><a href="${context }/newgrouptopic/">发表</a></span>
				</li>
				<li class="icons_page_white_edit">
					<h2 class="fleft"><a href="${context }/user.shtml?method=listMyTopics">主题</a></h2>
					<span class="fright graylink2"><a href="${context }/newtopic/">发表</a></span>
				</li>
				<li style="background:url(images/icons/photos.png) no-repeat">
					<h2 class="fleft"><a href="${context }/album/">相册</a></h2>
					<span class="fright graylink2"><a href="${context }/multiImgUpload.shtml?method=form">上传</a></span>
				</li>
				<li style="background:url(images/icons/chart_bar.png) no-repeat">
					<h2 class="fleft"><a href="${context }/vote/">投票</a></h2>
					<span class="fright graylink2"><a href="${context }/vote.shtml?method=voteForm">发起</a></span>
				</li>
				<li class="icons_heart">
					<h2 class="fleft"><a href="${context }/share/">分享</a></h2>
					<span class="fright graylink2"><a href="${context }/share.shtml?method=shares">我的</a></span>
					
				</li>
				<li style="background:url(images/icons/dime.gif) no-repeat">
					<h2><a href="apps/hoop">篮球</a></h2>
				</li> 
				<%-- 
				<li style="background:url(images/ctba_show.gif) no-repeat">
					<h2><a href="equipment/">装备</a></h2>
				</li>
				<li style="background:url(images/ctba_show.gif) no-repeat">
					<h2><a href="people/">粉丝</a></h2>
				</li>
				--%>
				<li style="background:url(images/icons/tls.png) no-repeat">
					<h2><a href="timeline">新鲜事</a></h2>
				</li>
			</ul>
		</div>
	</div>
	</c:if>
	<c:if test="${empty __sys_username}">
		<div class="side_block radius_all">
			<div class="side_title">
				<h2>
					<strong>扯谈推荐</strong>
				</h2>
			</div>
			<div class="side_list">
			<ul>
				<li class="icons_group">
					<h2><a href="${context }/group/">小组</a></h2>
				</li>
				<li class="icons_world">
					<h2><a href="${context }/blog/">博客</a></h2>
				</li>
				<li style="background:url(images/icons/photos.png) no-repeat">
					<h2 class="fleft"><a href="${context }/gallery/">相册</a></h2>
				</li>
				<li class="icons_heart">
					<h2><a href="${context }/share/">分享</a></h2>
				</li>
				<li style="background:url(images/icons/chart_bar.png) no-repeat">
					<h2><a href="${context }/vote.do?method=listVotes">投票</a></h2>
				</li>
				<li style="background:url(images/icons/tls.png) no-repeat">
					<h2><a href="timeline">新鲜事</a></h2>
				</li>
				<li style="background:url(images/icons/dime.gif) no-repeat">
					<h2><a href="apps/hoop">篮球</a></h2>
				</li> 
			</ul>
			</div>
		</div>
	</c:if>