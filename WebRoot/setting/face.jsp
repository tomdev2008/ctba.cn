<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.setting.tab.face" />&nbsp;-&nbsp;<bean:message key="user.modify.info" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            function checkFaceForm(){
                $("#submitFace").val("<bean:message key="page.setting.image.submitting"/>");
                $("#submitFace").attr("disabled","disabled");
                return true;
            }
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp">
            <jsp:param name="submenu" value="user" />
        </jsp:include>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="user.modify.info" />&nbsp;&gt;&nbsp;<bean:message key="page.setting.tab.face" />
                    </div>
                    <div id="errorMsg" class="fright">
                        ${message }
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice friend orangelink clearfix">
                        <h2 class="color_orange"><b><bean:message key="user.modify.info" /> - <bean:message key="page.setting.tab.face" /></b></h2>
                        <bean:message key="page.setting.face.hint"/>
                    </div>
                    <div class="mid_block clearfix">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="face"/>
                        </jsp:include>
                        <form name="up_face" action="userSetting.action?method=face" method="post" enctype="multipart/form-data" onsubmit="return checkFaceForm();">
                            <table class="top_blank_wide" width="500" cellspacing="10" cellpadding="3" align="center">
                                <tr>
                                    <td valign="middle" align="right">
                                        <h3>
                                            <bean:message key="user.face.show" />
                                        </h3>
                                    </td>
                                    <td>
                                        <logic:empty name="model" property="userFace">
                                            <img src="images/nopic.gif" alt="<bean:message key="page.setting.face.noYet"/>" width="80" />
                                        </logic:empty>
                                        <logic:notEmpty name="model" property="userFace">
                                            <img src="${userFace }" width="120" class="userFace_border" />&nbsp;
                                            <img src="${userFace }" width="80" class="userFace_border" />&nbsp;
                                            <img src="${userFace }" width="32" class="userFace_border" />&nbsp;
                                            <img src="${userFace }" width="16" class="userFace_border" />
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>
                                            <bean:message key="user.face.select" />
                                        </h3>
                                    </td>
                                    <td>
                                        <input type="file" name="face" size="25" />
                                    </td>
                                </tr>
                                <tr>
                                <td></td>
                                <td>
                                    <input type="submit" name="submitFace" id="submitFace" value="<bean:message key="page.setting.face.submit"/>" class="input_btn" />
                                    &nbsp;&nbsp;<input type="button"  value="<bean:message key="page.setting.face.gallery"/>" class="input_btn" onclick="location.href='img.shtml?method=faceGallery';"/>
                                </td>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../user/right.jsp"></jsp:include>
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
    </body>
</html>