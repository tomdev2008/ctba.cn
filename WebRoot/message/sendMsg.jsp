<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><bean:message key="user.post.mail" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <%@include file="../common/include.jsp"%>
        <%-- <link rel="stylesheet" type="text/css" href="${context }/theme/jquery/jquery.autocomplete.css" />
        <script type="text/javascript" src="${context}/javascript/jquery.bgiframe.min.js"></script>
        <script type="text/javascript" src="${context}/javascript/dimensions.js"></script>
        <script type="text/javascript" src="${context}/javascript/jquery.autocomplete.js"></script>
        --%>
        <script src="${context}/javascript/userselector.js"
			language="JavaScript" type="text/javascript"></script>
		<link href="${context}/theme/jquery/groupselect.css"
			rel="stylesheet" type="text/css" />
        <script type="text/javascript">
            //check the form
            function checkTopicForm(){
                J2bbCommon.removeformError("topicContent");
                J2bbCommon.removeformError("title");
                J2bbCommon.removeformError("al-groupselect-active");
                var userData = getActivedata();
                if(typeof(userData)=="undefined"){
                	J2bbCommon.formError("al-groupselect-active","<bean:message key="page.message.error.reciever"/>");
                	return false;
                }else{
                	//var userArray = userData.split(",");
	                // alert(userArray.length);
	                $("#userInSearch").val(userData);
	                var content = $("#topicContent").val();
	                var title = $("#title").val();
	                if(title.length>30||title.length<1){
	                    J2bbCommon.formError("title","<bean:message key="page.message.error.title"/>","bottom");
	                    return false;
	                }
	                if(content.length>10000||content.length<1){
	                    J2bbCommon.formError("topicContent","<bean:message key="page.message.error.content"/>","bottom");
	                    return false;
	                }
	                $("#submitMsg").val("<bean:message key="page.common.button.submitting"/>");
	                $("#submitMsg").attr("disabled","disabled");
	                return true;
                }
            }
		var getActivedata; //取结果	
		var activeParameters = gs_getGSParameters();
		$(activeParameters).attr(
				{'frameId':'al-groupselect-active',
				 'ajaxRequestUrl':{'group':'userSelector.action?method=group','item':'userSelector.action?method=items','active':'userSelector.action?method=suggest'}
		});
		$(document).ready(function(){  		
		  getActivedata = gs_groupselect(activeParameters);	
		});
		function _dummyAlertResult(){
			alert('你选的是： ' + getActivedata());
		}
		</script>
		        
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp">
            <jsp:param name="submenu" value="user" />
        </jsp:include>
        <div id="wrapper">
            <%--
            <script type="text/javascript">
            $(function(){$("#userInSearch").autocomplete("ajax.do?method=searchUser",
             {delay: 150, selectFirst: true,max:100,limit:10000,minChars: 0, matchContains: true}
            );
            });
            </script>
            --%>
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="user.do?method=listMsgs"><bean:message key="user.mail" /></a>&nbsp;&gt;&nbsp;<bean:message key="user.post.mail" />
                    </div>
                    <div class="fright">
                        <div id="errorMsg">${userMsg }</div>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include page="../_operationBlock.jsp"></jsp:include>
                    <jsp:include page="../_adBlock.jsp"></jsp:include>
                </div>

                <div id="mid_column" class="fright">
                    <div class="mail_bar">
                        <jsp:include flush="true" page="_msgBar.jsp"></jsp:include>
                    </div>
                    <div class="mail_info2">
                        &nbsp;
                    </div>
                    <form name="topicForm" id="topicForm" action="message.action?method=save" method="post" onsubmit="return checkTopicForm();">
                        <div class="mail_sub">
                            <ul>
                                <table cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td align="right"><b><bean:message key="page.message.header.reciever"/>&nbsp;</b></td>
                                        <td>
                                        <div id="al-groupselect-active" style="height:30px;"></div>
                                        <!-- input type="text" name="msgTo" id="userInSearch" value="${msgTo }" class="formInput" size="20" /> -->
                                        <input type="hidden" name="msgTo" id="userInSearch" value="${msgTo }"  />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right"><b><bean:message key="page.message.form.title"/>&nbsp;</b></td>
                                        <td><input type="text" name="title" id="title" class="formInput" size="30" style="width:300px;"/></td>
                                    </tr>
                                </table>
                                <li class="graylink mail_ubb"><com:ubb /></li>
                                <li><textarea class="topicContent" name="topicContent" id="topicContent" style="width:500px;"></textarea></li>
                                <li><input type="submit" class="mail_btn pointer" name="submitMsg" id="submitMsg" value="<bean:message key="page.message.button.send"/>" />&nbsp;&nbsp;<input type="button" class="mail_btn pointer" onclick="location.href='user.do?method=listMsgs';" value="<bean:message key="page.message.button.back"/>" /></li>
                            </ul>
                        </div>
                    </form>
                    <div class="mail_pager clearfix">&nbsp;
                    </div>
                    <div class="mail_bar_bottom">
                        <jsp:include flush="true" page="_msgBar.jsp"></jsp:include>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include flush="true" page="../user/right.jsp"></jsp:include>
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
        <script type="text/javascript" src="javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>