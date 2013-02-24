<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.setting.tab.bbs" />&nbsp;-&nbsp;<bean:message key="user.modify.info" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            function checkForm(){
                J2bbCommon.removeformError("userQMD");
                J2bbCommon.removeformError("userSMD");
                if(!checkNick()){
                    return false;
                }
                var c1 = $('#userQMD').val();
                var c2 = $('#userSMD').val();
                if(c1.length>200){
                    J2bbCommon.formError("userQMD","<bean:message key="page.setting.error.qmd"/>","bottom");
                    return false;
                }
                if(c2.length>500){
                    J2bbCommon.formError("userSMD","<bean:message key="page.setting.error.smd"/>","bottom");
                    return false;
                }
                $("#submitInfo").val("<bean:message key="page.setting.info.submitting" />");
                $("#submitInfo").attr("disabled","disabled");
                return true;
            }
            
            function checkNick(){ 
                  J2bbCommon.removeformError("userNick");
                  var userNick = $('#userNick').val();
                  if(userNick==''){return true;}
                  var patrn=/^[a-zA-Z][a-zA-Z]{1,14}$/;
                  if (!patrn.exec(userNick)||J2bbCommon.strLen(userNick)>15){
                        J2bbCommon.formError("userNick","<bean:message key="page.setting.error.nick" />");
                        return false;
                  }  
                   $.post("ajax.shtml",
                    { method:"checkNickname",loadType:"ajax",nickname:""+userNick},
                   function(data){
                      if(data.trim()!=''){
                         J2bbCommon.formError("userNick","<bean:message key="page.setting.error.nick.taken" />");
                         return false;
                      } 
                   }
                  );
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
                        <h2 class="color_orange"><b><bean:message key="user.modify.info" /> - <bean:message key="page.setting.tab.bbs"/></b></h2>
                        <bean:message key="page.setting.bbs.hint"/>
                    </div>
                    <div class="mid_block clearfix">
                        <jsp:include page="_tab.jsp" flush="true">
                            <jsp:param name="currTab" value="bbs"/>
                        </jsp:include>
                        <form name="select_team" action="userSetting.action?method=bbs" method="post" onSubmit="return checkForm();">
                            <table width="500" cellspacing="10" cellpadding="3" class="top_blank_wide" align="center">
                                <tr>
                                    <td>
                                        <h3><bean:message key="user.nickname" /></h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                       <input type="text" name="userNick" id="userNick" class="formInput" size="30" value="${model.userNick }" onblur="checkNick();" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h3><bean:message key="user.signature" /></h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <textarea name="userQMD" id="userQMD" class="ftt">${model.userQMD }</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h3><bean:message key="user.intro.mine" /></h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <textarea name="userSMD" id="userSMD" class="ftt">${model.userSMD }</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="submit" value="<bean:message key="page.setting.button.submit"/>" class="input_btn" name="submitInfo" id="submitInfo" />
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