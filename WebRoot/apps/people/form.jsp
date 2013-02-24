<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>创建偶像&nbsp;-&nbsp;粉丝团&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
        </title>
        <script type="text/javascript">
            function checkForm(){
                var name = $("#name").val();
                if(name==''){
                    alert("名称不能为空");
                    return false;
                }
                var description =  $("#description").val();
                if ((description == null) || (description == "")) {
                    alert("简介不能为空。");
                    return false;
                }
                if(description.length>300){
                    alert("您的简介太长鸟，请控制在300字以内");
                    return false;
                }
                if(!checkFile()){
                    return false;
                }
                $("#submitButton").val("提交中，请稍候...");
                $("#submitButton").attr("disabled","disabled");
                return true;
            }

            function checkFile(){
                    <c:if test="${empty model}">
                    var face =  $("#face").val();
                if ((face == null) || (face == "")) {
                    alert("请上传图片");
                    return false;
                }
                    </c:if>
                    return true;
            }
        </script>
    </head>
    <body>
        <jsp:include flush="true" page="./head.jsp"/>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;<a href="${context }/people/">粉丝团</a>&nbsp;&gt;&nbsp;创建人物
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="../../_operationBlock.jsp" />
                    <jsp:include flush="true" page="../../_adBlock.jsp" />
                </div>

                <div id="mid_column" class="fright">
                    <jsp:include flush="true" page="./_info.jsp" />
                    <jsp:include page="_tab.jsp" flush="true">
                        <jsp:param name="currTab" value="form"/>
                    </jsp:include>
                    <div class="mid_block">
                        <c:if test="${not empty model.image}">
                            <table width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="20%">
                                    </td>
                                    <td>
                                        <img src="<com:img value="${model.face}" type="default"/>"
                                             class="inputtext" />
                                         </td>
                                </tr>
                            </table>
                        </c:if>
                        <form action="people.action?method=save" id="uploadForm"
                              name="uploadForm"
                              onsubmit="return checkForm();" method="post"
                              enctype="multipart/form-data">
                            <input name="pid" type="hidden" value="${model.id }" />
                            <table width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="15%">
                                                                    姓名
                                    </td>
                                    <td width="80%">
                                        <input name="name" id="name" type="text"
                                               value="${model.name }" class="formInput" />
                                        &nbsp;
                                        <select name="sex" id="sex">
                                        <option value="0">男</option>
                                        <option value="1">女</option>
                                        </select>
                                        &nbsp;
                                        <com:select items="${peopleTypeList}" name="type"
                                                    selected="${model.type}" label="name" value="name" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        简介
                                    </td>
                                    <td>
                                        <textarea rows="3" cols="30" style="width:350px;" name="description"
                                                  id="description" class="formTextarea">${model.description }</textarea>
                                    </td>
                                </tr>
                                 <tr>
                                    <td align="right">
                                        生平事记
                                    </td>
                                    <td>
                                        <textarea rows="5" cols="30" style="width:350px;" name="eventLog"
                                                  id="eventLog" class="formTextarea">${model.eventLog }</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        上传图片
                                    </td>
                                    <td>
                                        <input type="file" name="face" id="face"
                                               class="inputtext" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        &nbsp;
                                    </td>
                                    <td>
                                        <span class="font_mid color_gray">调整图片宽至455px，上传效果最佳</span>
                                    </td>
                                </tr>
                                    <tr>
                                        <td align="right">
                                                                            昵称
                                        </td>
                                        <td>
                                            <input type="text" name="nickname" id="nickname" size="20"
                                                   value="${model.nickname }" class="formInput" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                            链接
                                        </td>
                                        <td>
                                            <input type="text" name="link" id="link"
                                                   maxlength="15" size="20" value="${model.link }"
                                                   class="formInput" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                           编辑权限
                                        </td>
                                        <td>
                                        <select id="editOption" name="editOption">
                                         <option value="0">只有我可以编辑</option>
                                         <option value="1">只有我的好友可以编辑</option>
                                         <option value="2">只有扯谈会员可以编辑</option>
                                         <option value="3">谁都可以编辑</option>
                                        </select>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td align="right">
                                           查看权限
                                        </td>
                                        <td>
                                          <select id="viewOption" name="viewOption">
                                         <option value="0">只有我可以查看</option>
                                         <option value="1">只有我的好友可以查看</option>
                                         <option value="2">只有扯谈会员可以查看</option>
                                         <option value="3">谁都可以查看</option>
                                        </select>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <table width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="20%"></td>
                                    <td>
                                        <input type="submit" name="submit" id="submitButton"
                                               value="提&nbsp;交" class="input_btn" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="_right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>