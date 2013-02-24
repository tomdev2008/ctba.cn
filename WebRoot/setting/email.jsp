<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.setting.tab.email" />&nbsp;-&nbsp;<bean:message key="user.modify.info" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            function checkForm(){
                $("#submitEmailSetting").val("<bean:message key="page.setting.info.submitting" />");
                $("#submitEmailSetting").attr("disabled","disabled");
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="user.modify.info" />&nbsp;&gt;&nbsp;${model.userName }
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
                        <h2 class="color_orange"><b><bean:message key="user.modify.info" /> - <bean:message key="page.setting.tab.email" /></b></h2>
                        <bean:message key="page.setting.email.hint"/>
                    </div>
                    <div class="mid_block clearfix">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="email"/>
                        </jsp:include>
                        <form name="infoForm" action="userSetting.action?method=email" method="post" onsubmit="return checkForm();">
                            <table cellspacing="10" cellpadding="3" align="center" width="100%" class="top_blank_wide">
                                <tr>
                                    <td width="40%" align="right">
                                        <h3>
                                            <bean:message key="page.setting.email.message"/>
                                        </h3>
                                    </td>
                                    <td>
                                        <select name="emailSettingMsg" id="emailSettingMsg"
                                                class="formInput">
                                            <option value="1"
                                                    <logic:equal value="1" name="main" property="emailSettingMsg">selected="selected"</logic:equal>>
                                                <bean:message key="page.setting.email.message.sync"/>
                                            </option>
                                            <option value="0"
                                                    <logic:notEqual value="1" name="main" property="emailSettingMsg">selected="selected"</logic:notEqual>>
                                                <bean:message key="page.setting.email.message.doNotSync"/>
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>
                                            <bean:message key="page.setting.email.content"/>
                                        </h3>
                                    </td>
                                    <td>
                                        <select name="emailSettingTopic" id="emailSettingTopic"
                                                class="formInput">
                                            <option value="1"
                                                    <logic:equal value="1" name="main" property="emailSettingTopic">selected="selected"</logic:equal>>
                                                <bean:message key="page.setting.email.get"/>
                                            </option>
                                            <option value="0"
                                                    <logic:notEqual value="1" name="main" property="emailSettingTopic">selected="selected"</logic:notEqual>>
                                                <bean:message key="page.setting.email.doNotGet"/>
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h3>
                                            <bean:message key="page.setting.email.timeline"/>
                                        </h3>
                                    </td>
                                    <td>
                                        <select name="emailSettingTimeline" id="emailSettingTimeline"
                                                class="formInput">
                                            <option value="1"
                                                    <logic:equal value="1" name="main" property="emailSettingTimeline">selected="selected"</logic:equal>>
                                                <bean:message key="page.setting.email.get"/>
                                            </option>
                                            <option value="0"
                                                    <logic:notEqual value="1" name="main" property="emailSettingTimeline">selected="selected"</logic:notEqual>>
                                                <bean:message key="page.setting.email.doNotGet"/>
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                    </td>
                                    <td>
                                        <input type="submit" value="<bean:message key="page.setting.button.submit"/>" class="input_btn" name="submitEmailSetting" id="submitEmailSetting" />
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