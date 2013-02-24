<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title><bean:message key="page.group.imageForm.title" />&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            function checkAllEmpty(){
                for(var i=1;i<=6 ;i++){
                    var value=$("#uploadFile"+i).val();
                    if(value!=''){
                        return false;
                    }
                }
                return true;
            }
            function checkForm(){
                if(checkAllEmpty()){
                    return false;
                }
                for(var i=1;i<=6 ;i++){
                    if(!checkSingle("uploadFile"+i)){
                        return false;
                    }
                }
                $("#submitButton").val("<bean:message key="page.common.button.submitting" />");
                $("#submitButton").attr("disabled","disabled");
                return true;
            }

            function checkSingle(fieldId){
                J2bbCommon.removeformError(fieldId);
                var value=$("#"+fieldId).val();
                var imgPatten = /\.jpg$|\.jpeg$|\.gif$|\.bmp|\.png$/;
                if(value!=''&&!imgPatten.test(value.toLowerCase())){
                    J2bbCommon.formError(fieldId,"<bean:message key="page.group.imageForm.error.image" />");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <jsp:include page="head.jsp" flush="true" />
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<bean:message key="page.group.imageForm.title" />
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="../_operationBlock.jsp" />
                </div>
                <div id="mid_column" class="fright">
                    <div class="left_block top_blank">
                        <div class="title">
                            <h1>
                                &nbsp;<img src="images/upix.png" align="absmiddle" />&nbsp;<bean:message key="page.group.imageForm.title" />
                            </h1>
                        </div>
                        <form action="imgUpload.shtml?method=save" method="post"
                              enctype="multipart/form-data" onsubmit="return checkForm();">
                            <table width="100%" border="0" cellspacing="10" cellpadding="0">
                                <tr>
                                    <td align="right">
                                        <h2>
                                            <bean:message key="page.group.imageForm.group" />
                                        </h2>
                                    </td>
                                    <td>
                                        <community:select items="${groupList}" name="gid" selected="${group.id}" label="name" value="id" />
                                    </td>
                                    <td align="left">
                                        <span class="fright graylink2">
											<a class="graylink" href="group/${group.id }">[${group.name }]</a>
										</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <h2>
                                           <bean:message key="page.group.imageForm.image" />
                                        </h2>
                                    </td>
                                    <td>
                                        <input type="file" name="uploadFile1" id="uploadFile1" class="formInput" size="30" />
                                        <br />
                                        <input type="file" name="uploadFile2" id="uploadFile2" class="formInput" size="30" />
                                        <br />
                                        <input type="file" name="uploadFile3" id="uploadFile3" class="formInput" size="30" />
                                        <br />
                                        <input type="file" name="uploadFile4" id="uploadFile4" class="formInput" size="30" />
                                        <br />
                                        <input type="file" name="uploadFile5" id="uploadFile5" class="formInput" size="30" />
                                        <br />
                                        <input type="file" name="uploadFile6" id="uploadFile6" class="formInput" size="30" />
                                        <br />
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="submit" value="<bean:message key="page.common.button.submit" />" class="input_btn" name="submitButton" id="submitButton" />
                                    </td>
                                    <td></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>