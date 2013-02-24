<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <link href="${context }/manage/css/style.css" rel="stylesheet"
              type="text/css"/>
        <title><bean:message key="page.board.search.title"/></title>
        <script type="text/javascript">
            function fixBoard(boardName,boardId){
                if(parent!=undefined){
                    parent.document.getElementById("boardInSearchName").value=boardName;
                    parent.document.getElementById("boardInSearchId").value=boardId;
                    if(parent.document.getElementById("boardFrame")!=undefined){
                        parent.document.getElementById("boardFrame").style.display="none";
                    }
                }
            }
        </script>
    </head>
    <body>
        <div style="width:300px">
            <div>
                <div class="title">
                    <form action="board.do?method=search" method="post">
                        <input type="text" name="boardName" value="${boardName }"
                               class="formInput" />
                        <input type="submit" value="搜索" class="button">
                    </form>
                </div>
            </div>
            <div>
                <pg:pager items="${count}" url="board.shtml" index="center"
                          maxPageItems="30" maxIndexPages="2"
                          isOffset="<%=false%>"
                          export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                          scope="request">
                    <pg:param name="method" value="search" />
                    <div id="banner">
                        <pg:index>
                            <jsp:include flush="true" page="../common/jsptags/pager.jsp"></jsp:include>
                        </pg:index>
                    </div>
                    <logic:notEmpty name="models">
                        <logic:iterate id="model" name="models" indexId="index">
                            <div style="padding-left:30;padding-bottom:3px;width:100%;"
                                 <%if (index % 2 == 0) {%> class="alterNative" <%}%>>
                                <a href="javascript:fixBoard('${model.boardName }','${model.boardId }');">${model.boardName}</a>
                            </div>
                        </logic:iterate>
                    </logic:notEmpty>
                    <logic:empty name="models">
                        <bean:message key="common.noContent" />
                    </logic:empty>
                </pg:pager>
            </div>
        </div>
    </body>
</html>
