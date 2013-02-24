<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.news.post"/>&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript" src="${context}/javascript/j2bb-nice-editor.js" ></script>
        <script>
            var contentTextArea;
            function toggleContentTextArea() {
                if(!contentTextArea) {
                    contentTextArea = new nicEditor({fullPanel : true}).panelInstance('content',{hasPanel : true});
                } else {
                    contentTextArea.removeInstance('content');
                    contentTextArea = null;
                }
            }
        </script>
        <script type="text/javascript">
            function checkForm(){
                var title = $("#title").val();
                if(title==''){
                    J2bbCommon.errorWithElement("sysMessage","<bean:message key="page.news.post.error.title"/>");
                    return false;
                }
                var content=  contentTextArea.nicInstances[0].getContent();
                if ((content == null) || (content == "")) {
                    J2bbCommon.errorWithElement("sysMessage","<bean:message key="page.news.post.error.content"/>");
                    return false;
                }
                $("#submitNewButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitNewButton").attr("disabled","disabled");
                return true;
            }

        </script>
        <script type='text/javascript' src='javascript/jquery.boxy.js'></script>
        <link rel="stylesheet" href="theme/jquery/boxy.css" type="text/css" />
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/news.shtml?method=indexPage"><bean:message key="menu.news.navigate" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.news.post"/>
                    </div>
                    <div class="fright">
                        <html:messages id="msg" message="true"><img src="images/icons/accept.png" align="absmiddle" />&nbsp;${msg}</html:messages>
                    </div>
                </div>
                <div class="left_block top_blank_wide radius_all radius">
                    <div class="title">
                        <h1>&nbsp;<img src="images/postnews.gif" align="absmiddle" />&nbsp;<bean:message key="page.news.post"/></h1>
                    </div>
                    <form action="news.shtml?method=savePost" method="post" onsubmit="return checkForm();">
                        <input type="hidden" name="nid" value="${model.id }" />
                        <table width="650" cellspacing="10" cellpadding="0">
                            <tr>
                                <td width="72" align="right"><h2><bean:message key="page.news.post.title"/></h2></td>
                                <td><input type="text" name="title" id="title" size="50" value="${proxyTitle }" class="formInput" /></td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.news.post.content"/></h2></td>
                                <td>
                                    <textarea id="content" name="content" rows="" cols="" style="width:500px;height:560px">${proxyContent }</textarea>
                                    <script type="text/javascript">
                                        //var oFCKeditor=new FCKeditor('content',560,500,'Default','${model.content}');
                                        //oFCKeditor.BasePath="${context}/fckeditor/";
                                        //oFCKeditor.Create();
                                        toggleContentTextArea();
                                    </script>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.news.post.category"/></h2></td>
                                <td><community:select name="category" items="${requestScope.cats}" selected="${requestScope.category.id}" label="name" value="id" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="submit" value="<bean:message key="page.news.post"/>" class="input_btn" name="submitNewButton" id="submitNewButton" /></td>
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