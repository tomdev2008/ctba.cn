<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><bean:message key="page.setting.tab.password"/>&nbsp;-&nbsp;<bean:message key="user.modify.info" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <%@include file="../common/include.jsp"%>
        <script src="${context}/javascript/passwordStrength.js" type="text/javascript"></script>
        <script type="text/javascript">
            function checkForm(){
                J2bbCommon.removeformError("oldPassword");
                J2bbCommon.removeformError("newPassword");
                J2bbCommon.removeformError("newPassword1");
                var p0 = $('#oldPassword').val();
                var p1 = $('#newPassword').val();
                var p2 = $('#newPassword1').val();
                if(p0.length<6){
                    J2bbCommon.formError("oldPassword","<bean:message key="page.setting.password.error.old"/>");
                    return false;
                }
                if(p1.length<6){
                    J2bbCommon.formError("newPassword","<bean:message key="page.setting.password.error.new"/>");
                    return false;
                }
                if(p1!=p2){
                    J2bbCommon.formError("newPassword1","<bean:message key="page.setting.password.error.confirm"/>");
                    return false;
                }
                $("#submitPass").val("<bean:message key="page.setting.info.submitting" />");
                $("#submitPass").attr("disabled","disabled");
                return true;
            }
            $(function() {
                $('#newPassword').keyup(function(){ $('#pw_strength_result').html(passwordStrength($('#newPassword').val())) });
            });
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp">
            <jsp:param name="submenu" value="user" />
        </jsp:include>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.setting.tab.password"/>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlockMini.jsp"></jsp:include>
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block top_notice friend orangelink clearfix">
                        <h2 class="color_orange">
                            <b><bean:message key="user.modify.info" /> - <bean:message key="page.setting.tab.password"/></b>
                        </h2>
                        <p>
                            <bean:message key="page.setting.password.hint"/>
                        </p>
                    </div>
                    <div class="mid_block clearfix">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="password" />
                        </jsp:include>
                        <form action="userSetting.action?method=password" method="post" onsubmit="return checkForm();">
                            <table width="80%" cellspacing="10" cellpadding="3"
                                   align="center" class="top_blank_wide">
                                <tr>
                                    <td width="72" align="right" valign="top">
                                        <h3>
                                            <bean:message key="page.setting.password.old"/>
                                        </h3>
                                    </td>
                                    <td>
                                        <input type="password" class="formInput" name="password" id="oldPassword" maxlength="30" size="30" onmousemove="this.focus();" />
                                        <div class="font_mid color_gray">
                                            <bean:message key="page.setting.password.old.hint"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" valign="top">
                                        <h3>
                                            <bean:message key="page.setting.password.new"/>
                                        </h3>
                                    </td>
                                    <td>
                                        <input class="formInput" type="password" maxlength="30" size="30" name="newPassword" id="newPassword" />
                                        <div class="font_mid color_gray">
                                            <bean:message key="page.setting.password.new.hint"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" valign="top">
                                        <h3>
                                            <bean:message key="page.setting.password.confirm"/>
                                        </h3>
                                    </td>
                                    <td>
                                        <input class="formInput" type="password" maxlength="30" size="30" name="newPassword1" id="newPassword1" />
                                        <div class="font_mid color_gray"> <bean:message key="page.setting.password.confirm.hint"/></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right"></td>
                                    <td>
                                        <span id="pw_strength_result"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="submit" name="submitPass" id="submitPass" value="<bean:message key="page.setting.button.submit"/>" class="input_btn" />
                                    </td>
                                </tr>
                            </table>
                            <span id="sysMsg"></span> ${message }
                            <html:messages id="msg" message="true">${msg}</html:messages>
                        </form>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../user/right.jsp" />
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
    </body>
</html> 