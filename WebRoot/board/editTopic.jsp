<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><bean:message key="page.board.edit.title"/>&nbsp;-&nbsp;${topic.topicTitle }</title>
        <%@include file="../common/include.jsp"%>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#topicContent").val($("#oldContent").val());
                //loadNewEM(0);
            });
            //check the form
            function checkTopicForm(){
                var form = document.getElementById("topicForm");
                var content = $("#topicContent").val();
                var title = $("#topicTitle").val();
                if(content.length>10000||content.trim().length<1){
                    J2bbCommon.errorWithElement("errorMsg","<bean:message key="page.board.topic.form.error.content"/>"+content.length+"");
                    return false;
                }
                if(title.length<1||title.length>50){
                    J2bbCommon.errorWithElement("errorMsg","<bean:message key="page.board.topic.form.error.title"/>");
                    $("#topicTitle").focus();
                    return false;
                }
                $("#submitNewButton").val("<bean:message key="page.common.button.submitting"/>");
                $("#submitNewButton").attr("disabled","disabled");
                return true;
            }
        </script>
    </head>
    <body >
        <jsp:include flush="true" page="./head.jsp"/>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="topic/${topic.topicId }">${topic.topicTitle }</a>&nbsp;&gt;&nbsp;<bean:message key="page.board.edit.title"/>
                </div>
                <div class="left_block top_blank_wide">
                    <div class="title">
                        <h1>&nbsp;<img src="images/ico_conf.gif" align="absmiddle" />&nbsp;<bean:message key="page.board.edit.title"/></h1>
                    </div>
                    <div id="errorMsg"></div>
                    <form method="post" name="form1" id="topicForm" enctype="multipart/form-data" action="topic.action?method=edit" onsubmit="return checkTopicForm();">
                        <input type="hidden" name="topicId" value="${topic.topicId }" />
                        <input type="hidden" id="oldContent" value="${topic.topicContent }" />
                        <table width="700" cellspacing="10" cellpadding="0">
                            <tr>
                                <td width="42" align="right"><h2><bean:message key="page.board.edit.form.title"/></h2>
                                <td><input type="text" name="topicTitle" id="topicTitle" value="${topic.topicTitle }" class="formInput" size="50" /></td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h2><bean:message key="page.board.edit.form.board"/></h2>
                                </td>
                                <td>
                                    <com:select items="${boardList}" name="topicBoardId" label="boardName" value="boardId" selected="${board.boardId }"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><h2><bean:message key="page.board.edit.form.content"/></h2></td>
                                <td>
                                    <com:ubb />
                                    <textarea name="topicContent" class="topicContent" id="topicContent" style="width:554px;height:250px">${topic.topicContent }</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <h2><bean:message key="page.board.edit.form.attach" /></h2>
                                </td>
                                <td><input type="file" name="topicAttach" class="formInput" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    ${topicAttach }
                                    <logic:notEmpty name="topic" property="topicAttachPath" scope="request">
                                        <br /><a href="topic.action?method=deleteAttach&tid=${topic.topicId }" class="button"><bean:message key="page.board.topic.form.deleteAttach"/></a>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            
                            <tr>
								<td align="right">
									<h2>收益</h2>
								</td>
								<td colspan="2">
								<select id="topicScore" name="topicScore" class="formInput">
								<option value="0" <c:if test="${topic.topicScore eq 0 }">selected</c:if>>0</option>
								<option value="1" <c:if test="${topic.topicScore eq 1 }">selected</c:if>>1</option>
								<option value="2" <c:if test="${topic.topicScore eq 2 }">selected</c:if>>2</option>
								<option value="3" <c:if test="${topic.topicScore eq 3 }">selected</c:if>>3</option>
								<option value="4" <c:if test="${topic.topicScore eq 4 }">selected</c:if>>4</option>
								<option value="5" <c:if test="${topic.topicScore eq 5 }">selected</c:if>>5</option>
								<option value="6" <c:if test="${topic.topicScore eq 6 }">selected</c:if>>6</option>
								</select><span class="color_orange">CTB</span>
									<!-- input type="text" id="topicScore" name="topicScore" class="formInput" size="20" /> -->
									&nbsp;<span class="color_gray">设置回复收益CTB，</span><a href="go.jsp?key=faq-what-is-ctb-topic-trad" target="_blank" class="color_orange">详情:什么是收益?</a>
								</td>
							</tr>
							
                            <tr>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <input type="submit" name="submit" id="submitNewButton" value="<bean:message key="page.button.submit"/>" class="input_btn" />
                                    <input type="reset" class="input_btn" value="<bean:message key="page.button.reset"/>" />
                                </td>
                            </tr>
                        </table>

                    </form>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <jsp:include flush="true" page="../bottom.jsp"></jsp:include>
        <script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
    </body>
</html>