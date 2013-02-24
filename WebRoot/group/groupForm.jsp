<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>
            <c:if test="${empty model}"><bean:message key="page.group.form.title.new" />&nbsp;-&nbsp;<bean:message key="sys.name" /></c:if>
            <c:if test="${not empty model}"><bean:message key="page.group.form.title.edit" />&nbsp;-&nbsp;<bean:message key="sys.name" /></c:if>
        </title>
        <script type="text/javascript">
            function checkForm(){
                if(!checkName()){
                    return false;
                }
                if(!checkUrl()){
                    return false;
                }
                J2bbCommon.removeformError("topicContent");
                J2bbCommon.removeformError("name");
                J2bbCommon.removeformError("face");
                var discription=$("#topicContent").val();
                var name=$("#name").val();
                var face=$("#face").val();
                if(name==''||name.length>20){
                    J2bbCommon.formError("name","<bean:message key="page.group.form.error.name" />");
                    return false;
                }
                if(discription==''||discription.length>5000){
                    J2bbCommon.formError("topicContent","<bean:message key="page.group.form.error.description" />");
                    return false;
                }
            <c:if test="${empty model}">
                    if(face==''){
                        J2bbCommon.formError("face","<bean:message key="page.group.form.error.image.empty" />");
                        return false;
                    }
            </c:if>
                    var imgPatten = /\.jpg$|\.jpeg$|\.gif$|\.bmp|\.png$/;
                    if(face!=''&&!imgPatten.test(face.toLowerCase())){
                        J2bbCommon.formError("face","<bean:message key="page.group.form.error.image" />");
                        return false;
                    }
                    $("#submitButton").val("<bean:message key="page.common.button.submitting" />");
                    $("#submitButton").attr("disabled","disabled");
                    return true;
                }
                function changeCommendTags(type){
                    $("#commendTags").html("<img src='images/ajax-loader.gif' />");
                    $.ajax({ url: "g.action",
                        type:"get",
                        data:"method=tags&t="+type+"&loadType=ajax",
                        success:function(msg){
                            $("#commendTags").html(msg);
                        }
                    });
                }
                function checkName(){
                    var name = $('#name').val();
                    if(name==''||name.length>20){
                        J2bbCommon.formError("name","<bean:message key="page.group.form.error.name" />");
                        return false;
                    }
                    var originId ="";
                    <c:if test="${not empty model}"> originId = ${model.id};</c:if>
                            $.post("ajax.shtml",
                            { method:"checkGroupByName",loadType:"ajax",name:""+name,originId:""+originId },
                            function(data){ 
                                if(data.trim()!=''){
                                    J2bbCommon.formError("name","<bean:message key="page.group.form.error.name.taken" />");
                                    return false;
                                }
                            }
                        );
                            return true;
                        }

                        function checkUrl(){ J2bbCommon.removeformError("url");
                            var url = $('#url').val();
                            if(url==''){return true;}
                          
                            var originId ="";
                    <c:if test="${not empty model}"> originId = ${model.id};</c:if>
                            var patrn=/^[a-zA-Z][a-zA-Z]{1,12}/;
                            if (!patrn.exec(url)||J2bbCommon.strLen(url)>12){
                                J2bbCommon.formError("url","<bean:message key="page.group.form.error.alias" />");
                                return false;
                            }  
                                $.post("ajax.shtml",
                                { method:"checkGroupByUrl",loadType:"ajax",url:""+url,originId:""+originId },
                                function(data){
                                    if(data.trim()!=''){
                                        J2bbCommon.formError("url","<bean:message key="page.group.form.error.alias.taken" />");
                                        return false;
                                    } 
                                }
                            );
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
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage"/></a>&nbsp;&gt;&nbsp;<a href="${context }/group/"><bean:message key="menu.group.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/group.shtml?method=myGroups"><bean:message key="menu.group.mine" /></a>&nbsp;&gt;&nbsp;<c:if test="${empty model}"><bean:message key="page.group.form.title.new" /></c:if><c:if test="${not empty model}"><bean:message key="page.group.form.title.edit" /></c:if>
                    </div>
                </div>
                <div class="left_block top_blank_wide radius_top_none radius">
                    <div id=systemMsg>${systemMsg }</div>
                    <form action="g.action?method=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm();">
                        <div class="title">
                            &nbsp;<img src="images/ngroups.png" align="absmiddle" />&nbsp;<h2 style="display:inline"><c:if test="${empty model}"><bean:message key="page.group.form.title.new" /></c:if><c:if test="${not empty model}"><bean:message key="page.group.form.title.edit" /></c:if></h2>
                        </div>
                        <input name="gid" value="${model.id }" type="hidden" />
                        <table cellspacing="10" cellpadding="0" id="group_form">
                            <tr>
                                <td class="name" align="right" ><h2><bean:message key="page.group.form.name" /></h2></td>
                                <td class="items" colspan="2"><input type="text" name="name" id="name" class="formInput" size="50" value="${model.name }" onblur="checkName();" /></td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.description" /></h2></td>
                                <td colspan="2">
                                    <com:ubb/>
                                    <textarea rows="3" name="discription" id="topicContent" class="formTextarea" cols="50">${model.discription}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.authType" /></h2></td>
                                <td colspan="2">
                                    <select name="authLevel" class="formInput">
                                        <option value="0" <c:if test="${not empty model and model.authLevel==0 }">selected</c:if>>
                                            <bean:message key="page.group.form.authType.public" />
                                        </option>
                                        <option value="1" <c:if test="${not empty model and model.authLevel==1 }">selected</c:if>>
                                            <bean:message key="page.group.form.authType.protected" />
                                        </option>
                                        <option value="2" <c:if test="${not empty model and model.authLevel==2 }">selected</c:if>>
                                            <bean:message key="page.group.form.authType.private" />
                                        </option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.type" /></h2></td>
                                <td class="sort"><com:select items="${groupTypes}" name="type" label="name" selected="${model.type}" value="index" /></td>
                                <td class="tags" id="commendTags">
                                    <script type="text/javascript">
                                        changeCommendTags(document.getElementById("type").options[document.getElementById("type").selectedIndex].value);
                                        document.getElementById("type").onchange = function(){ changeCommendTags(this.options[this.selectedIndex].value);};
                                    </script>

                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.tag" /></h2></td>
                                <td colspan="2"><input type="text" name="tags" id="tags" class="formInput" size="50" value="${model.tags }" /></td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.group.form.image" /></h2></td>
                                <td colspan="2">
                                    <input type="file" name="face" id="face" size="50" class="formInput"/>
                                    <div id="divPreview">
                                        <c:if test="${not empty model.face}">
                                            <bean:message key="page.group.form.imageAdded" /><br />
                                            <img src="<com:img value="${model.face }" type="sized" width="80" />" class="group_border" />
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="name" align="right"><h2><bean:message key="page.group.form.alias" /></h2></td>
                                <td class="items" colspan="2"><span class="color_gray">http://www.ctba.cn/group/</span><input type="text" name="url" id="url" class="formInput" size="30" value="${model.magicUrl }" onblur="checkUrl();" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="2">
                                    <input type="submit" value="<bean:message key="page.common.button.submit" />" class="input_btn" name="submitButton" id="submitButton" />
                                    <c:if test="${not empty model}">&nbsp;
                                        <input type="button" value="<bean:message key="page.group.form.visit" />" class="input_btn" onclick="location.href='gt.action?method=list&gid=${model.id }';" />
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <div class="line_blocks">
                        <img src="images/icons/information.png" align="absmiddle" />&nbsp;<bean:message key="page.group.form.hint" />
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />
        <script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>