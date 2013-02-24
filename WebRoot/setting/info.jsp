<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.setting.tab.info" />&nbsp;-&nbsp;<bean:message key="user.modify.info" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <link rel="stylesheet" type="text/css" media="all" href="${context }/theme/calendar.css" />
        <script type="text/javascript" src="${context}/javascript/calendar/calendar.js"></script>
        <script type="text/javascript" src="${context}/javascript/calendar/calendar-zh.js"></script>
        <script type="text/javascript" src="${context}/javascript/calendar/calendar-setup.js"></script>
        <script type="text/javascript">
            function checkInfoForm(){
                J2bbCommon.removeformError("qq");
                J2bbCommon.removeformError("msn");
                J2bbCommon.removeformError("email");
                J2bbCommon.removeformError("gtalk");
                var qq = $('#qq').val();
                var msn = $('#msn').val();
                var email = $('#email').val();
                var gtalk=  $('#gtalk').val();
                var pattenMobile=/^(13)[0-9]{9}/;
                var pattenTel=/[0-9]/;
                var pattenBirthday=/^(19)\d{2}\-\d{1-2}\-\d{1-2}/;
                var pattenQQ=/[0-9]{3,12}/;
                var pattenMSN=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
                var patrnUsername=/[\x80-\xff_a-zA-Z0-9]{2,}/;

                if (qq!=""&&!pattenQQ.exec(qq)){
                    J2bbCommon.formError("qq","<bean:message key="page.setting.info.error.qq"/>");
                    return false;
                }
                if (msn!=""&&!pattenMSN.exec(msn)){
                    J2bbCommon.formError("msn","<bean:message key="page.setting.info.error.msn"/>");
                    return false;
                }
                if (email!=""&&!pattenMSN.exec(email)){
                    J2bbCommon.formError("email","<bean:message  key="page.setting.info.error.email"/>");
                    return false;
                }
                if (gtalk!=""&&!pattenMSN.exec(gtalk)){
                    J2bbCommon.formError("gtalk","<bean:message key="page.setting.info.error.gtalk"/>");
                    return false;
                }
                $("#submitBaseInfo").val("<bean:message key="page.setting.info.submitting" />");
                $("#submitBaseInfo").attr("disabled","disabled");
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="user.modify.info" />&nbsp;&gt;&nbsp;<bean:message key="page.setting.tab.info" />
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
                        <h2 class="color_orange"><b><bean:message key="user.modify.info" /> - <bean:message key="page.setting.tab.info" /></b></h2>
                        <bean:message key="page.setting.info.hint"/>
                    </div>
                    <div class="mid_block clearfix">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="info"/>
                        </jsp:include>
                        <form name="infoForm" action="userSetting.action?method=info" method="post" onSubmit="return checkInfoForm();">
                            <table class="top_blank_wide" cellspacing="10" cellpadding="3" align="center" width="60%">
                                <tr>
                                    <td align="right">
                                        <h3><bean:message key="page.setting.info.email"/></h3>
                                    </td>
                                    <td>
                                        <input type="text" name="email" id="email" value="${main.email}" size="30" onmousemove="this.focus()" class="formInput" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3><bean:message key="page.setting.info.sex"/></h3>
                                    </td>
                                    <td>
                                        <select name="sex" id="sex" class="formInput">
                                            <option value="0"
                                                    <logic:equal value="0" name="main" property="sex">selected="selected"</logic:equal>>男
                                            </option>
                                            <option value="1"
                                                    <logic:equal value="1" name="main" property="sex">selected="selected"</logic:equal>>女
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>GTALK</h3>
                                    </td>
                                    <td>
                                        <input type="text" name="gtalk" id="gtalk" value="${main.gtalk}" size="30" onmousemove="this.focus()" class="formInput" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>MSN</h3>
                                    </td>
                                    <td>
                                        <input type="text" name="msn" id="msn" value="${main.msn}" size="30" onmousemove="this.focus()" class="formInput" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>QQ</h3>
                                    </td>
                                    <td>
                                        <input type="text" name="qq" id="qq" value="${main.qq}" size="30" class="formInput" />
                                    </td>
                                </tr>

                                <tr>
                                    <td align="right">
                                        <h3><bean:message key="page.setting.info.birthday"/></h3>
                                    </td>
                                    <td>
                                        <input type="text" name="birthday" id="birthday" value="${main.birthday}" size="30" class="formInput" readonly="readonly" onclick="return showCalendar('birthday', 'y-mm-dd');" />
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="submit" id="submitBaseInfo" value="<bean:message key="page.setting.button.submit"/>" class="input_btn" />
                                    </td>
                                </tr>
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