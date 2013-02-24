<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
        String contextPath = request.getContextPath();
        String common = contextPath + "/common";
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("context", request.getContextPath());
        request.setAttribute("common", common);
%>
<div id="header">
    <div id="header_inner">
        <div id="logo" class="fleft">
            <h1><a href="${context }/" title="扯谈社 CTBA">扯谈社 CTBA</a></h1>
        </div>
        <div id="go" class="fright yellowlink clearfix">
            <logic:present name="__sys_username">
                <ul>
                    <li>${__sys_username }&nbsp;<a href="${context}/messages"><img src="images/icons/email.png" title="站内信" alt="站内信" align="absmiddle">&nbsp;(${__sys_msgCnt })</a>&nbsp;<a href="${context}/notices" ><logic:greaterThan value="0" name="__sys_noticeCnt"><img src="images/notice_new.gif" alt="提醒" align="absmiddle">&nbsp;您有新提醒<span class="font-mid">(${__sys_noticeCnt})</span>&nbsp;</logic:greaterThan><logic:equal value="0" name="__sys_noticeCnt"><img src="images/notice.gif" title="${__sys_noticeCnt }个系统提醒" align="absmiddle"></logic:equal></a></li>
                    <li><a href="${context}/setting">修改资料</a>&nbsp;</li>
                    <li><a href="user.shtml?method=invite"><bean:message key="common.invite" /></a>&nbsp;</li>
                    <li class="last"><a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要暂时离开<bean:message key="sys.name" />么？','${basePath }userLogout.action');return false;"><bean:message key="menu.bbs.logout" /></a>&nbsp;</li>
                </ul>
            </logic:present>
            <logic:notPresent name="__sys_username">
                <ul>
                    <li><a id="login_wrap" href="javascript:J2bbCommon.showLoginForm();"><bean:message key="common.login" /></a>&nbsp;</li>
                    <li class="last"><a href="${context }/register"><bean:message key="common.regist" /></a>&nbsp;</li>
                </ul>
            </logic:notPresent>
        </div>
    </div>
</div>
<div id="menu">
    <ul id="menu_inner">
        <li id="communityLi">
            <a href="${context }/">&nbsp;<bean:message key="menu.bbs.indexPage" /></a>
            <ul>
                <li>
                    <a href="${context }/timeline">CT新鲜事</a>
                </li>
                <logic:present name="__sys_username">
                    <li>
                        <a href="${context }/search.action"><bean:message key="menu.bbs.search" /></a>
                    </li>
                    <li>
                        <a href="${context }/user.shtml?method=search">用户搜索</a>
                    </li>
                    <li class="sep"></li>
                    <li>
                        <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要暂时离开<bean:message key="sys.name" />么？','${basePath }userLogout.action');return false;"><bean:message key="menu.bbs.logout" /></a>
                    </li>
                </logic:present>
                <logic:notPresent name="__sys_username">
                    <li>
                        <a href="javascript:J2bbCommon.showLoginForm();"><bean:message key="menu.bbs.login" /></a>
                    </li>
                    <li>
                        <a href="${context }/register"><bean:message key="menu.bbs.reg" /></a>
                    </li>
                    <li class="sep"></li>
                    <li>
                        <a href="${context }/forget-password"><bean:message key="menu.bbs.forget" /></a>
                    </li>
                </logic:notPresent>
            </ul>
        </li>
        <logic:present name="__sys_username">
            <li id="spaceLi">
                <a href="${context }/userpage/"><bean:message key="menu.user.indexPage" /></a>
                <ul>
                    <li>
                        <a href="${context }/setting"><bean:message key="menu.user.info" /></a>
                    </li>
                    <li class="sep"></li>
                    <li>
                        <a href="${context }/messages"><bean:message key="menu.user.msg" /></a>
                    </li>
                    <li>
                        <a href="${context }/user.shtml?method=listFriends"><bean:message key="menu.user.friend" /></a>
                    </li>
                    <li>
                        <a href="${context }/notices">系统提醒</a>
                    </li>
                     <li>
                        <a href="${context }/bank/">CT币中心</a>
                    </li>
                     <li>
                        <a href="${context }/donate/">捐赠扯谈</a>
                    </li>
                    <li>
                        <a href="${context }/feedback.action?method=form">提交反馈</a>
                    </li>
                </ul>
            </li>
        </logic:present>
        <li id="appLi">
            <a href="${context }/apps/">&nbsp;扯谈应用</a>
            <ul>
                <li>
                    <a href="${context }/album/">扯谈相册</a>
                </li>
                <li>
                    <a href="${context }/share/">扯谈分享</a>
                </li>
                <li>
                    <a href="${context }/vote/">扯谈投票</a>
                </li>
                <!-- 
                 <li>
                    <a href="${context }/order.action?method=list">订单管理</a>
                </li> -->
                <logic:present name="__sys_username">
                    <li class="sep"></li>
                    <li>
                        <a href="${context }/multiImgUpload.shtml?method=form">上传图片</a>
                    </li>
                    <li>
                        <a href="${context }/img.shtml?method=galleryList">我的相册</a>
                    </li>
                    <li>
                        <a href="${context }/share.shtml?method=shares">我的分享</a>
                    </li>
                    <li>
                        <a href="${context }/vote.shtml?method=voteForm">发起投票</a>
                    </li>
                </logic:present>
            </ul>
        </li>
        
        <li id="newsLi">
            <a href="${context}/news/">&nbsp;<bean:message key="menu.news.navigate" /></a>
            <ul>
                <li>
                    <a href="${context}/news/list/"><bean:message key="menu.news.list" /></a>
                </li>
                <logic:present name="__app_news_type_list">
                    <logic:iterate name="__app_news_type_list" scope="application" id="newsType">
                        <li>
                            <a href="${context}/news/list/${newsType.id}">${newsType.name}</a>
                        </li>
                	</logic:iterate>
                </logic:present>
                <logic:present name="__sys_username">
                    <li class="sep"></li>
                    <li>
                        <a href="${context }/news.shtml?method=post"><bean:message key="menu.news.post" /></a>
                    </li>
                </logic:present>
            </ul>
        </li>
        <li id="equipmentLi">
            <a href="${context }/equipment/">&nbsp;扯谈装备</a>
            <ul>
                <li>
                    <a href="${context }/equipment.shtml?method=list&type=hot">热门装备</a>
                </li>
                <li>
                    <a href="${context }/equipmentSearch.action?method=brandList">品牌检索</a>
                </li>
                <li>
                    <a href="http://www.ctba.cn/group/shop-rank">旺铺推荐</a>
                </li>
                
                <logic:present name="__sys_username">
                    <li class="sep"></li>
                    <li>
                        <a href="${context }/equipment.shtml?method=manageList">我的装备</a>
                    </li>
                    <li>
                        <a href="${context }/equipment.shtml?method=favList">我的收藏</a>
                    </li>
                    <li>
                        <a href="${context }/equipment.shtml?method=form">上传装备</a>
                    </li>
                    <li>
                        <a href="${context }/equipmentWidget.action?method=builder">取得代码</a>
                    </li>
                    
                </logic:present>
            </ul>
        </li>
        
        <li id="groupLi">
            <a href="${context}/group/">&nbsp;<bean:message key="menu.group.indexPage" /></a>
            <ul>
                <li>
                    <a href="${context }/g.action?method=list"><bean:message key="menu.group.listGroups" /></a>
                </li>
                <li>
                    <a href="${context }/group/activities/">小组活动</a>
                </li>
                  <li>
                    <a href="${context }/rank.action?method=group">小组热榜</a>
                </li>
                <logic:present name="__sys_username">
                    <li class="sep"></li>

                    <li>
                        <a href="${context }/group/mine/"><bean:message key="menu.group.mine" /></a>
                    </li>
                    <li>
                        <a href="${context }/gt.action?method=personal">我的话题</a>
                    </li>
                    <li>
                        <a href="${context }/newgrouptopic/">发表话题</a>
                    </li>
                    <li>
                        <a href="${context }/g.action?method=form"><bean:message key="menu.group.form" /></a>
                    </li>
                </logic:present>
            </ul>
        </li>
        <li id="boardLi">
            <a href="${context}/forum/">&nbsp;扯谈论坛</a>
            <ul>
                <li><a href="board/5">庶民上篮</a></li>
                <li><a href="board/65">光明顶</a></li>
                <li><a href="board/20">燃情岁月</a></li>
                <li><a href="board/7">社员反馈</a></li>
                <logic:present name="__sys_username">
                    <li class="sep"></li>
                    <li>
                        <a href="newtopic/">发表文章</a>
                    </li>
                    <li>
                        <a href="${context }/user.shtml?method=listMyTopics"><bean:message key="menu.user.myTopic" /></a>
                    </li>
                </logic:present>

            </ul>
        </li>
        <li id="blogLi">
            <a href="${context}/blog/">&nbsp;<bean:message key="menu.blog.navigate" /></a>
            <logic:present name="__sys_username"><ul>

                    <li>
                        <a href="${context }/bg.action?method=view"><bean:message key="menu.blog.mine" /></a>
                    </li>
                    <li>
                        <a href="${context }/bg.action?method=form"><bean:message key="menu.blog.edit" /></a>
                    </li>
                    <li class="sep"></li>
                    <li>
                        <a href="${context }/blog/newentry/"><bean:message key="menu.blog.entryForm" /></a>
                    </li>
                    <li>
                        <a href="${context }/be.action?method=list"><bean:message key="menu.blog.entryList" /></a>
                    </li>
                    <li>
                        <a href="${context }/be.action?method=drafts"><bean:message key="menu.blog.draft" /></a>
                    </li>

                    <li>
                        <a href="${context }/com.action?method=list"><bean:message key="menu.blog.commentList" /></a>
                    </li>
                    <li>
                        <a href="${context }/cat.action?method=list"><bean:message key="menu.blog.catList" /></a>
                    </li>
                    <li>
                        <a href="${context }/bl.action?method=list"><bean:message key="menu.blog.linkList" /></a>
                    </li>
                    <li>
                        <a href="${context }/bv.action?method=list"><bean:message key="menu.blog.vest" /></a>
                    </li>

                </ul></logic:present>
            </li>
            <%-- 
            <li id="subjectLi">
                <a href="${context}/subject/">&nbsp;<bean:message key="menu.subject.navigate" /></a>
                <ul>
                    <li>
                        <a href="${context}/subject/list/"><bean:message key="menu.subject.list" /></a>
                    </li>
                    <li class="sep"></li><logic:present name="__app_subject_type_list">
                    <logic:iterate name="__app_subject_type_list" scope="application" id="subType">
                        <li>
                            <a href="${context}/subject/list/${subType.subjectTypeCode}">${subType.subjectType}</a>
                        </li>
                </logic:iterate></logic:present>
            </ul>
        </li>--%>
        
         <li id="channelLi">
            <a href="${context }/">&nbsp;扯谈频道</a>
            <ul>
                <li>
                    <a href="${context }/channel/badminton">扯谈羽球</a>
                </li> 
                 <li>
                    <a href="${context }/channel/hoop">扯谈篮球</a>
                </li>
              
                <%--
                <li>
                    <a href="${context }/apps/sneaker">扯谈Sneaker</a>
                </li>--%>
               
                
                <li>
                    <a href="${context }/channel/dimemag">Dime篮天下</a>
                </li>
            </ul>
        </li>
    </ul>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        if ($(".modalDialog_login").css("display")=="block"){
            $("#username")[0].focus();
        }
    });

<c:if test="${(empty param.submenu) or ( param.submenu eq 'community')}">
$("#communityLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'user'}">
$("#spaceLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'blog'}">
$("#blogLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'group'}">
$("#groupLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'group'}">
$("#groupLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'group'}">
$("#groupLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'subject'}">
$("#subjectLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'news'}">
$("#newsLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'forum'}">
$("#boardLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'app'}">
$("#appLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'channel'}">
$("#channelLi").attr("class","curr");
</c:if>
<c:if test="${param.submenu eq 'equipment'}">
$("#equipmentLi").attr("class","curr");
</c:if>
</script>
<div id="loginDiv">
    <form id="loginForm" action="${context }/userLogin.action" method="post" onsubmit="return J2bbCommon.checkLoginForm();">
        <div class="loginClose pointer"><a onclick="J2bbCommon.closeLoginForm();">X</a></div>
        <div class="loginTitle">
            <bean:message key="common.login.title" />
        </div>
        <table cellpadding="0" cellspacing="8" class="loginItems" align="right">
            <tr>
                <td align="right">Username&nbsp;</td>
                <td><input class="loginLine" type=text name="username" id="username" /></td>
            </tr>
            <tr>
                <td align="right">Password&nbsp;</td>
                <td><input class="loginLine" type=password name="password" id="password" /></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right">
                    <input type="image" class="nborder" src="images/ctbtn.gif" alt="<bean:message key='common.login' />"/>
                </td>
            </tr>
        </table>
        <span id="loginError" class="loginError"></span>
    </form>
</div>