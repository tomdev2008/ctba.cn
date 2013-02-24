<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.net9.domain.model.news.NewsEntry"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/community.tld" prefix="comm"%>
<%@ page import="com.fredck.FCKeditor.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../include.jsp"%>
        <title>${model.title }</title>
        <script type="text/javascript" src="${context}/fckeditor/fckeditor.js"></script>
        <script type="text/javascript">
            function checkForm(){
                var title = $("#title").val();
                var author = $("#author").val();
                if(title==''){
                    alert("题目不能为空");
                    return false;
                }
                if(author==''){
                    alert("作者不能为空");
                    return false;
                }
                var Content =FCKeditorAPI.GetInstance("content").GetXHTML();
                if ((Content == null) || (Content == "")) {
                    alert("信息内容不能为空。");
                    var oEditor = FCKeditorAPI.GetInstance('content');
                    oEditor.Focus();
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <jsp:include page="../head.jsp" flush="true"></jsp:include>
        <div id="wrap">
            <div id="content">
                <div class="navigator">
                    ${model.title }
                </div>
                <div id="errorMsg">
                </div>
                <div id="modelForm">
                    <form action="newsManage.shtml?method=saveNews" method="post"
                          onsubmit="return checkForm();" style="padding-left:20px;">
                        <input type="hidden" name="nid" value="${model.id }" />
						题目
                        <br />
                        <input type="text" name="title" id="title" size="30"
                               value="${model.title }" class="formInput" />
                        <br />
						作者
                        <br />
                        <input type="text" name="author" id="author" size="30"
                               value="${author }" class="formInput" />
                        <br />


						描述
                        <br />
                        <%
String subtitle = "";
NewsEntry entry = (NewsEntry) pageContext.findAttribute("model");
if (entry != null) {
subtitle = entry.getSubtitle();
}
FCKeditor oFCKeditor;
oFCKeditor = new FCKeditor(request, "subtitle");
oFCKeditor.setBasePath(request.getContextPath() + "/fckeditor/");
oFCKeditor.setValue(subtitle);
oFCKeditor.setHeight("300px");
oFCKeditor.setWidth("560px");
out.println(oFCKeditor.create());
                        %>
                        <%--						<textarea rows="5" name="subtitle" id="subtitle" cols="50"--%>
                        <%--							class="formTextarea" style="width:500px;height:100px">${model.subtitle}</textarea>--%>
                        <br />
						标签
                        <br />
                        <input type="text" name="tags" size="30" class="formInput"
                               value="${model.tags}" />
                        <br />
						正文
                        <br />
                        <%
String content = "";
entry = (NewsEntry) pageContext.findAttribute("model");
if (entry != null) {
content = entry.getContent();
}
oFCKeditor = new FCKeditor(request, "content");
oFCKeditor.setBasePath(request.getContextPath() + "/fckeditor/");
oFCKeditor.setValue(content);
oFCKeditor.setHeight("500px");
oFCKeditor.setWidth("560px");
out.println(oFCKeditor.create());
                        %>
                        <br />
						类型
                        <c:if test="${not empty model.newsCategory}"><comm:select items="${cats}" name="cid" label="name"
                                                                                  selected="${model.newsCategory.id}" value="id" /></c:if>
                        <c:if test="${empty model.newsCategory}"><comm:select items="${cats}" name="cid" label="name"
                                                                              selected="${selectedCat}" value="id" /></c:if>

                            <br />
						状态
                            <select name="state">
                                <option value="0"
                                <logic:present  name="model" >
                                    <logic:equal value="0" name="model" property="state">
                                        selected
                                    </logic:equal>
                                </logic:present>
                                >WAITING</option>
                            <option value="2"
                                    <logic:present  name="model" >
                                        <logic:equal value="2" name="model" property="state">
                                            selected
                                        </logic:equal>
                                    </logic:present>
                                    >OK</option>
                        </select>
                        <br class="clear" />
						更新时间
                        <br />
                        <input type="text" name="updateTime" id="updateTime" size="30"
                               value="${model.updateTime }" class="formInput" />
                        <br />
                        <br class="clear" />
						魔法地址
                        <br />
                        <input type="text" name="fakeUrl" id="fakeUrl" size="30"
                               value="${model.fakeUrl }" class="formInput" />
                        <br />

                        <br class="clear" />
						DIGG
                        <input type="text" name="hitGood" id="hitGood" size="5"
                               value="${model.hitGood }" class="formInput" />
                        &nbsp;
						点击
                        <input type="text" name="hits" id="hits" size="5"
                               value="${model.hits }" class="formInput" />
                        <br />
                        <br class="clear" />
                        <input type="submit" value=" 提 &nbsp; &nbsp;交 " class="button"
                               name="edit" />
                        <br />
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true"></jsp:include>
    </body>
</html>
