<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.setting.tab.privacy"/>&nbsp;-&nbsp;<bean:message key="user.modify.info" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            function checkForm(){
                $("#submitButton").val("<bean:message key="page.setting.info.submitting" />");
                $("#submitButton").attr("disabled","disabled");
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
                        <h2 class="color_orange"><b><bean:message key="user.modify.info" /> - <bean:message key="page.setting.tab.privacy"/></b></h2>
                        <bean:message key="page.setting.privacy.hint"/>
                    </div>
                    <div class="mid_block clearfix">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="privacy"/>
                        </jsp:include>
                        <form name="infoForm" action="userSetting.action?method=privacy" method="post" onSubmit="return checkForm();">
                            <table width="500" align="center" cellspacing="10" cellpadding="3" class="top_blank_wide">
                                <tr>
                                    <td width="40%" align="right">
                                        <h3 class="orangelink"><bean:message key="page.setting.privacy.homepage"/></h3>
                                    </td>
                                    <td>
                                        <select name="privacySetting" id="privacySetting" class="formInput">
                                            <option value="3"
                                                    <logic:equal value="3" name="main" property="privacySetting">selected="selected"</logic:equal>>我自己
                                            </option>
                                            <option value="2"
                                                    <logic:equal value="2" name="main" property="privacySetting">selected="selected"</logic:equal>>我的好友
                                            </option>
                                            <option value="1"
                                                    <logic:equal value="1" name="main" property="privacySetting">selected="selected"</logic:equal>>扯谈会员
                                            </option>
                                            <option value="0"
                                                    <logic:equal value="0" name="main" property="privacySetting">selected="selected"</logic:equal>>谁都能看
                                            </option>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="40%" align="right">
                                        <h3 class="orangelink"><bean:message key="page.setting.privacy.timeline"/></h3>
                                    </td>
                                    <td>
                                        <select name="timelineSetting" id="timelineSetting" class="formInput">
                                            <option value="0"
                                                    <logic:equal value="0" name="main" property="timelineSetting">selected="selected"</logic:equal>>对外发布
                                            </option>
                                            <option value="1"
                                                    <logic:equal value="1" name="main" property="timelineSetting">selected="selected"</logic:equal>>不对外发布
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="submit" id="submitButton" value="<bean:message key="page.setting.button.submit"/>" class="input_btn" />
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