<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>
            <c:if test="${not empty model}"><bean:message key="page.blog.form.title.edit"/>&nbsp;-&nbsp;${model.title }&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></c:if>
            <c:if test="${ empty model}"><bean:message key="page.blog.form.title.new"/>&nbsp;-&nbsp;${blogModel.name}&nbsp;-&nbsp;<bean:message key="menu.blog.navigate" /></c:if>
        </title>
        <script type="text/javascript" src="${context}/javascript/j2bb-nice-editor.js" ></script>
        <script>
            var contentTextArea;
            function toggleContentTextArea() {
                if(!contentTextArea) {
                    contentTextArea = new nicEditor({fullPanel : true}).panelInstance('topicContent',{hasPanel : true});
                } else {
                    contentTextArea.removeInstance('topicContent');
                    contentTextArea = null;
                }
            }
            function checkForm(){
                J2bbCommon.removeformError("title");
                J2bbCommon.removeformError("subtitle");
                var title=$("#title").val();
                if ((title == null) || (title == "")) {
                    alert("<bean:message key="page.blog.form.error.title"/>");
                    return false;
                }
                var subtitle=$("#subtitle").val();
                <c:if test="${not empty model }">
                var publishDate=$("#publishDate").val();
                var patrn=/\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}/;
				if (publishDate!=''&&!patrn.exec(publishDate)){
				     alert("<bean:message key="page.blog.form.error.publishDate"/>");
				     return false;
				}
                </c:if>
                var content=  contentTextArea.nicInstances[0].getContent();
                if ((content == null) || (content == "")) {
                    alert("<bean:message key="page.blog.form.error.content"/>");
                    return false;
                }
                $("#entryType").val("1000");
                $("#submitButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitButton").attr("disabled","disabled");
                return true;
            }
            $( function(){
                toggleContentTextArea();
            });

            function saveAsDraft(){
                $("#entryType").val("1001");
                J2bbCommon.removeformError("title");
                J2bbCommon.removeformError("subtitle");
                var title=$("#title").val();
                if ((title == null) || (title == "")) {
                    alert("<bean:message key="page.blog.form.error.title"/>");
                    return false;
                }
                var subtitle=$("#subtitle").val();
                var content=  contentTextArea.nicInstances[0].getContent();
                if ((content == null) || (content == "")) {
                    alert("<bean:message key="page.blog.form.error.content"/>");
                    return false;
                }
                $("#draftButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#draftButton").attr("disabled","disabled");
                toggleContentTextArea();
                document.getElementById("entryForm").submit();
            }
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="head.jsp" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;>&nbsp;<a href="${context }/bhome.action"><bean:message key="menu.blog.navigate" /></a>&nbsp;>&nbsp;<a href="blog/${model.id }">${blogModel.name}</a>&nbsp;>&nbsp;<c:if test="${not empty model}">编辑日志&nbsp;>&nbsp;${model.title }</c:if><c:if test="${ empty model}">撰写新日志</c:if>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlock.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice tl clearfix">
                        <h2 class="color_orange">
                            <b><bean:message key="page.blog.form.hint.title"/></b>
                        </h2>
                        <bean:message key="page.blog.form.hint"/>
                    </div>
                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="edit"/>
                    </jsp:include>
                    <form name="entryForm" id="entryForm" action="be.action?method=save" method="post" onsubmit="return checkForm();">
                        <input type="hidden" name="id" value="${model.id }" />
                        <input type="hidden" name="type" id="entryType" value="1000" />
                        <input type="hidden" name="blogId" value="${blogModel.id }" />
                        <table class="blog_setting" cellspacing="10" cellpadding="0">
                            <tr>
                                <td width="30%" align="right">
                                    <h3><bean:message key="page.blog.form.form.title"/></h3>
                                </td>
                                <td>
                                    <input name="title" id="title" class="formInput" size="50" value="${model.title }" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.content"/></h3>
                                </td>
                                <td>
                                    <textarea id="topicContent" name="body" style="width:500px;height:560px">${model.body }</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.category"/></h3>
                                </td>
                                <td>
                                    <g:select items="${cats}" name="categoryId" value="id" label="name" selected="${model.categoryId }" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.visible"/></h3>
                                </td>
                                <td>
                                    <select name="state" id="state">
                                        <option value="0" <c:if test="${model.state==0}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.all"/></option>
                                        <option value="1" <c:if test="${model.state==1}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.users"/></option>
                                        <option value="2" <c:if test="${model.state==2}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.friends"/></option>
                                        <option value="3" <c:if test="${model.state==3}">selected</c:if>><bean:message key="page.blog.form.form.viewOption.private"/></option>
                                    </select>
                                </td>
                            </tr>
                            <c:if test="${not empty model }">
                            <tr>
                                <td align="right">
                                    <h3><bean:message key="page.blog.form.form.publishDate"/></h3>
                                </td>
                                <td>
                                    <input name="publishDate" id="publishDate" class="formInput" size="30" value="${model.publishDate }" />
                                     <div class="font_mid color_gray">
                                            <bean:message key="page.blog.form.error.publishDate"/>
                                        </div>
                                </td>
                            </tr>
                           </c:if>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <input type="submit" id="submitButton" value="<bean:message key="page.blog.form.button.post"/>" class="input_btn" />&nbsp;
                                    <c:if test="${(empty model) or (model.type eq 1001)}">
                                        <input type="button" onclick="saveAsDraft();" id="draftButton" value="<bean:message key="page.blog.form.button.draft"/>" class="input_btn" />&nbsp;
                                    </c:if>
                                    <input type="reset" value="<bean:message key="page.blog.form.button.reset"/>" class="input_btn" />
                                </td>
                            </tr>
                        </table>
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
