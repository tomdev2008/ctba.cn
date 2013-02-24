<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="com"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="menu.blog.edit" />&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></title>
        <script type="text/javascript">
            function checkForm(){
                J2bbCommon.removeformError("name");
                J2bbCommon.removeformError("image");
                J2bbCommon.removeformError("topicContent");
                var description=$("#topicContent").val();
                var name=$("#name").val();
                var image=$("#image").val();
                if(name==''||name.length>30){
                    J2bbCommon.formError("name", "<bean:message key='page.blog.blogForm.error.name' />");
                    return false;
                }

                if( description.length==0 || description.length>100){
                    J2bbCommon.formError("topicContent", "<bean:message key='page.blog.blogForm.error.description' />");
                    return false;
                }

            <c:if test="${empty model}">
                    if(image==''){
                        J2bbCommon.formError("image", "<bean:message key='page.blog.blogForm.error.logo' />");
                        return false;
                    }
            </c:if>
                    $("#submitButton").val("<bean:message key='page.common.button.submitting' />");
                    $("#submitButton").attr("disabled", "disabled");
                    return true;
                }
                $(function(){
                    $("#tabs_gray li.tlink a").click(function(){
                        $("#tabs_gray li").attr("class","normal");
                        $(this).parent("li").attr("class","current");
                        $("div.tabswrap").hide();
                        $("#" + $(this).attr("name")).show();
                        return false;
                    });
                });
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;&gt;&nbsp;<bean:message key="menu.blog.edit" />
                    </div>
                    <div class="fright">
                        <c:if test="${not empty systemMsg}">
                            <div id=systemMsg class="message_error">${systemMsg }</div>
                        </c:if>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange">
                            <b><bean:message key="menu.blog.edit" /></b>
                        </h2>
                        <bean:message key="page.blog.blogForm.hint"/>
                    </div>
                    <div class="mid_block lightgray_bg">
                        <ul id="tabs_gray" class="graylink">
                            <li class="current tlink"><a name="blog_setting_basis"><bean:message key="page.blog.blogForm.tab.basic"/></a></li><li class="normal tlink"><a name="blog_setting_advance"><bean:message key="page.blog.blogForm.tab.more"/></a></li>
                        </ul>
                    </div>
                    <form name="form1" action="bg.action?method=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm();">
                        <input name="bid" value="${model.id }" type="hidden" />
                        <!-- input name="theme" value="none" type="hidden" /> -->
                        <div id="blog_setting_basis" class="tabswrap clearfix">
                            <table class="blog_setting" cellspacing="10" cellpadding="0">
                                <tr>
                                    <td width="30%" align="right">
                                        <h3>
                                            <bean:message key="blog.name" />
                                        </h3>
                                    </td>
                                    <td>
                                        <input type="text" name="name" id="name" class="formInput" size="40" value="${model.name }" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>
                                            <bean:message key="blog.template.select" />
                                        </h3>
                                    </td>
                                    <td>
                                        <dl id="blog_setting_skin">
                                            <dt>
                                                <input type="radio" name="theme" value="none" <c:if test="${ model.theme eq 'none' }">checked</c:if>/>
                                                <img src="./images/default_blog_skin.gif" alt="<bean:message key="page.blog.blogForm.theme.none"/>" align="absmiddle" />
                                            </dt>
                                            <dd>
                                                <input type="radio" name="theme" value="greenbud" <c:if test="${model.theme eq 'greenbud' }">checked</c:if>/>
                                                <img src="./blogThemes/greenbud/thumbnail.gif" alt="<bean:message key="page.blog.blogForm.theme.greenbud"/>" align="absmiddle"/>
                                            </dd>
                                            <dt>
                                                <input type="radio" name="theme" value="bus" <c:if test="${model.theme eq 'bus' }">checked</c:if>/>
                                                <img src="./blogThemes/bus/thumbnail.gif" alt="<bean:message key="page.blog.blogForm.theme.white"/>" align="absmiddle"/>
                                            </dt>
                                            <dd>
                                                <input type="radio" name="theme" value="easiness" <c:if test="${model.theme eq 'easiness' }">checked</c:if>/>
                                                <img src="./blogThemes/easiness/thumbnail.gif" alt="<bean:message key="page.blog.blogForm.theme.black"/>" align="absmiddle"/>
                                            </dd>
                                            <dt>
                                                <input type="radio" name="theme" value="memories" <c:if test="${model.theme eq 'memories' }">checked</c:if>/>
                                                <img src="./blogThemes/memories/thumbnail.gif" alt="Memories" align="absmiddle"/>
                                            </dt>
                                            <dd>
                                                <input type="radio" name="theme" value="tifafa" <c:if test="${model.theme eq 'tifafa' }">checked</c:if>/>
                                                <img src="./blogThemes/tifafa/thumbnail.gif" alt="Tifafa" align="absmiddle"/>
                                            </dd>
                                            <dt>
                                                <input type="radio" name="theme" value="mockee" <c:if test="${model.theme eq 'mockee' }">checked</c:if>/>
                                                <img src="./blogThemes/mockee/thumbnail.gif" alt="Mockee" align="absmiddle"/>
                                            </dt>
                                            <dt>
                                                <input type="radio" name="theme" value="badminton" <c:if test="${model.theme eq 'badminton' }">checked</c:if>/>
                                                <img src="./blogThemes/badminton/thumbnail.gif" alt="Badminton" align="absmiddle"/>
                                            </dt>
                                        </dl>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>
                                            <bean:message key="blog.logo" />
                                        </h3>
                                    </td>
                                    <td>
                                        <input type="file" name="image" id="image" size="45" class="formInput" />
                                    </td>
                                </tr>
                                <c:if test="${not empty model.image}">
                                    <tr>
                                        <td align="right">
                                            <h3>
                                                <bean:message key="blog.current.logo" />
                                            </h3>
                                        </td>
                                        <td>
                                            <img src="<com:img value="${model.image }" type="default" />" />
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td align="right">
                                        <h3><bean:message key="page.blog.blogForm.form.description"/></h3>
                                    </td>
                                    <td>
                                        <textarea name="description" class="ftt" id="topicContent">${model.description }</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3><bean:message key="page.blog.blogForm.form.keyword"/></h3>
                                    </td>
                                    <td>
                                        <textarea name="keyword" class="ftt">${model.keyword }</textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="blog_setting_advance" class="tabswrap clearfix hide">
                            <table class="blog_setting" cellspacing="10" cellpadding="0">
                                <tr>
                                    <td width="30%" align="right">
                                        <h3><bean:message key="page.blog.blogForm.form.listType"/></h3>
                                    </td>
                                    <td>
                                        <select name="listType">
                                            <option value="0" <c:if test="${model.listType == 0}">selected="selected"</c:if>><bean:message key="page.blog.blogForm.form.listType.all"/></option>
                                            <option value="1" <c:if test="${model.listType == 1}">selected="selected"</c:if>><bean:message key="page.blog.blogForm.form.listType.subtitle"/></option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="30%" align="right">
                                        <h3><bean:message key="page.blog.blogForm.form.gallery"/></h3>
                                    </td>
                                    <td>
                                        <select name="showGallery">
                                            <option value="0" <c:if test="${model.showGallery == 0}">selected="selected"</c:if>><bean:message key="page.blog.blogForm.form.gallery.notShow"/></option>
                                            <option value="1" <c:if test="${model.showGallery == 1}">selected="selected"</c:if>><bean:message key="page.blog.blogForm.form.gallery.show"/></option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="30%" align="right">
                                        <h3><bean:message key="page.blog.blogForm.form.ping"/></h3>
                                    </td>
                                    <td><textarea name="pingTargets" class="ftt">${model.pingTargets }</textarea></td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3><bean:message key="page.blog.blogForm.form.freeBlock"/></h3>
                                    </td>
                                    <td>
                                        <textarea name="htmlBlock" class="ftt">${model.htmlBlock }</textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="blog_setting_submit">
                            <input type="submit" value="<bean:message key="page.common.button.submit"/>" class="input_btn" name="submitButton" id="submitButton" id="button" />&nbsp;
                            <c:if test="${not empty model}">
                                <input type="button" value="<bean:message key="page.blog.blogForm.button.visit"/>" class="input_btn" onclick="location.href='blog/${model.id }';" />
                            </c:if>
                        </div>
                        <div class="footer_tips_3col">
                            <img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.blog.blogForm.hint.logo"/>
                        </div>
                    </form>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>