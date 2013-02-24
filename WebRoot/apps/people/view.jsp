<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../../common/include.jsp"%>
        <title>${model.name }&nbsp;-&nbsp;粉丝团&nbsp;-&nbsp;<bean:message
            key="nav.indexPage" /></title>
        <!--[if lte IE 6]>
        <style type="text/css">
            #photo_view img {
                zoom: expression( function(elm) {
                    if (elm.width>568) {
                        var oldVW = elm.width;
                        elm.width = 568;
                        <%--elm.height = elm.height*(568/oldVW);--%>
                    }
                        elm.style.zoom = '1';
                    }(this));
            }
        </style>
        <![endif]-->
        <style type="text/css">
            .score {
                width:350px;
                color: #527ada;
                padding: 10px 0 10px 5px;
                float: left;
                display: inline;
                height: 23px !important
            }

            .score .fleft {
                color: #f70;
                font-weight: bold;
                margin-right: 8px;
                border-bottom: 2px solid #f70
            }

            .score ul li {
                float: left;
                display: inline;
                height: 16px;
                width: 16px;
                padding: 1px;
                margin: 0 4px;
                border: 1px solid #749ce4
            }


            .score ul li a,.score ul li a:hover {
                float: left;
                width: 16px;
                height: 16px;
                color: #fff;
                display: inline;
                line-height: 16px;
                font-size: 11px;
                font-family: Arial, Helvetica, sans-serif;
                text-align: center;
                background: #749ce4;
                text-decoration: none
            }

            .score ul li a:hover {
                background: #506795
            }

            .goods_items {
                padding: 5px 10px;
                border-top: 1px solid #e9e9e9;
                border-bottom: 1px solid #e9e9e9;
            }
            .goods_items ul {
                _padding: 0 0 6px 0
            }

            .goods_items ul li {
                float: left;
                display: inline;
                width: 285px;
                height: 20px;
                line-height: 20px
            }

            .goods_items ul li.longtext {
                width: 400px
            }

            .goods_items ul li span {
                color: #3b5888
            }
        </style>

        <script type="text/javascript">
            function checkCommentForm(){
                var content = $("#topicContent").val();
                if(content==''){
                    alert("写点什么吧？");
                    return false;
                }
                if(content.length>50){
                    alert("您的评论太长鸟，请控制在50字以内");
                    return false;
                }
                return true;
            }
            function doVote(wid,value){
                <c:if test="${not empty __sys_username}">
                        $.ajax({ url: "equipmentUser.shtml",
                            type:"get",
                            data: "method=vote&value="+value+"&wid="+wid,
                            success:function(msg){
                                document.getElementById("scoreDiv").innerHTML=msg;
                            }
                        });
                </c:if>
                <c:if test="${empty __sys_username}">
                        J2bbCommon.showLoginForm();
                </c:if>
                    }
                    function doRef(wid){
                        var type = document.getElementById("equipmentUserSelect").options[document.getElementById("equipmentUserSelect").selectedIndex].value;
                        location.href="equipmentUser.shtml?method=save&type="+type+"&wid="+wid;
                    }


            //==============
			// 回复某楼的用户
			//==============
			function re(index,username){
				$("#para_reply_to").val(username);
				$("#topicContent").val("@"+username+" "+index+"#\n");
				$("#topicContent").focus();
			}
			//==============
			// ie6 实现 css2 伪类
			//==============
			function hover() {
				$("ul.opt2 li").mouseover(function(){
					$(this).addClass("hover");
				}).mouseout(function(){
					$(this).removeClass("hover");
				});
			}
			if (window.attachEvent) window.attachEvent("onload", hover);

        </script>
    </head>
    <body>
        <jsp:include flush="true" page="./head.jsp"/>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />
                        &nbsp;
                        <a href="${context }/"><bean:message key="nav.indexPage" />
                        </a>&nbsp;&gt;&nbsp;
                        <a href="${context }/equipment/">粉丝团</a>&nbsp;&gt;&nbsp;${model.name
                        }
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="../../_operationBlock.jsp" />
                    <jsp:include flush="true" page="../../_adBlock.jsp" />
                </div>
                <div id="mid_column" class="fright">

                    <jsp:include flush="true" page="./_info.jsp" />
                    <div class="mid_block top_notice clearfix orangelink">
                        <div class="clearfix" style="margin:3px;">
                            <h1>&nbsp;
                                <strong><a href="equipment/${model.id }">[${equipmentType }] ${model.name}</a>
                              /strong>
                            </h1>  <%--
                            <table width="95%" align="center">
                                <tr>
                                    <td align="center" style="width:80px;" valign="top">
                                        <div class="group_digg">
                                            <div class="diggnum" id="scoreDiv">
                                            </div>
                                            <span class="font_small color_gray">Pt</span>
                                        </div>
                                    </td>
                                    <td align="left" valign="top">
                                        <div class="score">
                                            <span class="fleft"> 评分 </span>
                                            <ul>
                                                <%
        for (int i = 1; i <= 10; i++) {
                                                %>
                                                <li>
                                                    <a href="javascript:doVote(${model.id },<%=i%>);"><%=i%></a>
                                                </li>
                                                <%
        }
                                                %>
                                                <br class="clear" />
                                            </ul>
                                        </div>

                                        &nbsp;&nbsp;你目前对它:
                                        <select id="equipmentUserSelect" onchange="doRef(${model.id });">
                                            <option value="0" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 0)}">selected</c:if>>
                                                不感冒
                                            </option>
                                            <option value="1" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 1)}">selected</c:if>>
                                                暂且关注
                                            </option>
                                            <option value="2" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 2)}">selected</c:if>>
                                                很喜欢,想拥有
                                            </option>
                                            <option value="3" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 3)}">selected</c:if>>
                                                手头正好有
                                            </option>
                                        </select>
                                       
                                    </td>
                                    <td align="right" valign="top">
                                        <c:if test="${empty nextModel}">
                                            <img
                                                src="<com:img value="${model.image }" type="sized" width="80"/>"
                                                width="80" align="right"
                                                style="margin-top: 15px; padding: 2px; border: 1px solid #ddd; margin-right: 8px" />
                                        </c:if>
                                        <c:if test="${not empty nextModel}">
                                            <a href="equipment/${nextModel.id }">
                                                <img
                                                    src="<com:img value="${model.image }" type="default"/>"
                                                    width="80" align="right"
                                                    style="margin-top: 15px; padding: 2px; border: 1px solid #ddd; margin-right: 8px" />
                                            </a></c:if>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <span class="color_gray ">简介:</span>
                                            <p>
                                            <com:topic value="${model.discription }" />
                                        </p>
                                    </td>
                                </tr>
                            </table>
                            --%>
                        </div><%--
                        <div class="line_block color_gray graylink clear clearfix">
                            <ul class="lo" style="float: right">
                                <li class="icons_user">
                                    ${model.username}&nbsp;<a href="equipment.shtml?method=list&wrap-uid=${authorModel.userId }">&lt;只看${model.username}的&gt;</a>&nbsp;|&nbsp;
                                </li>
                                <li class="icons_calendar_view_day">
                                    <community:formatTime time="${model.createTime }" />
                                    &nbsp;|&nbsp;
                                </li>
                                <li class="icons_note">
                                    ${model.hits }&nbsp;|&nbsp;
                                </li>
                                <c:if test="${not empty prevModel}">
                                <a href="equipment/${prevModel.id }">上一个</a></c:if>&nbsp;
                                <c:if test="${not empty nextModel}">
                                <a href="equipment/${nextModel.id }">下一个</a></c:if>&nbsp;&nbsp;
                                <c:if test="${model.username eq __sys_username}">
                                    <a href="equipment.shtml?method=form&wid=${model.id }">编辑</a>&nbsp;&nbsp;<a
                                        href="equipment.shtml?method=delete&wid=${model.id }"
                                        onclick="return confirm('您确定要删除么？');">删除</a>
                                </c:if>
                            </ul>
                        </div> --%>
                    </div>

<%--
                    <div class="mid_block">
                        <div class="graylink2">
                            <div class="goods_items">
                                <ul>
                                    <li>
                                        装备品牌：
                                        <span>${model.brand }</span>
                                    </li>
                                    <li>
                                        装备类别：
                                        <span>${equipmentType }</span>
                                    </li>
                                    <li>
                                        官方售价：
                                        <span>${model.officailprice }</span>
                                    </li>
                                    <li>
                                        购买价格：
                                        <span>${model.price }</span>
                                    </li>
                                    <li>
                                        上市时间：
                                        <span>${model.saleTime }</span>
                                    </li>
                                    <li>
                                        简短名称：
                                        <span>${model.shortname }</span>
                                    </li>
                                    <li>
                                        技术：
                                        <span>${model.tech }</span>
                                    </li>
                                    <li>
                                        官方链接：
                                        <a
                                            <c:if test="${not empty model.link }">href="${model.link }"</c:if>
                                            <c:if test="${empty model.link }">href="equipment/${model.id }"</c:if>
                                            target="_blank">点击浏览</a>
                                    </li>
                                    <li>
                                        产品编号：
                                        <span>${model.num }</span>
                                    </li>
                                    <li class="longtext">
                                        关键词：
                                        <span>${model.keyword }</span>
                                    </li>
                                </ul>
                                <br class="clear" />
                            </div>
                        </div>
                    </div>
--%>

                    <pg:pager items="${count}" url="equipment.shtml" index="center"
                              maxPageItems="15" maxIndexPages="6"
                              isOffset="<%=false%>"
                              export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber"
                              scope="request">
                        <pg:param name="method" />
                        <pg:param name="wid" />
                        <logic:notEmpty name="commentList">
                            <div class="mid_block top_blank_wide clearfix">
                                <logic:iterate id="map" indexId="index" name="commentList">
                                    <div>
                                        <div class="reply_list orangelink clearfix">
                                            <div class="reply_user">
                                                <img
                                                    src="<community:img value="${map.user.userFace }" type="sized" width="32" />"
                                                    width="32" height="32" title="${map.user.userName}"
                                                    alt="${map.user.userName}" />
                                            </div>
                                            <div id="reply_info" style="width: 200px" class="linkblue_b">
                                                <a href="u/${map.user.userId}">${map.user.userName}</a>
                                            </div>
                                            <div class="reply_date">
                                                <div class="fleft">
                                                    <span class="font_small color_gray"><community:date
                                                        value="${map.comment.updateTime }" start="2" limit="16" />&nbsp;|</span>
                                                </div>
                                            </div>
                                            <div id="reply_content" style="width: 459px !important">
                                                <community:topic value="${map.comment.content}" />
                                            </div>
                                        </div>
                                    </div>
                                </logic:iterate>
                                <div class="pageindex nborder">
                                    <jsp:include flush="true" page="../../common/jsptags/simple.jsp"></jsp:include>
                                </div>
                            </div>
                        </logic:notEmpty>
                    </pg:pager>


                    <c:if test="${not empty __sys_username}">
                        <div
                            class="mid_block top_blank_wide clearfix radius_all lightgray_bg"
                            style="padding: 10px 10px 3px 10px; _padding: 10px">
                            <form action="equipment.shtml?method=saveComment" method="post"
                                  onsubmit="return checkCommentForm();">
                                <input type="hidden" name="wid" value="${model.id }" />
                                <input type="hidden" name="para_reply_to" id="para_reply_to" value="${authorModel.userName }"/>
                                <com:ubb />
                                <textarea class="ftt" style="width:536px"  name="content" id="topicContent"></textarea>
                                <p>
                                    <input type="submit" name="submitButton" class="input_btn"
                                           value="发表评论" />
                                </p>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
            <div id="area_right">

                <logic:notEmpty name="refEquipmentList">
                    <div class="state">
                        <div class="titles">
                            相关偶像
                        </div>
                        <ul class="hot_group clearfix" id="hot_group">
                            <c:forEach items="${refEquipmentList}" var="model">
                                <li>
                                    <a class="stars_border" href="equipment/${model.id }"> <img
                                            src="<community:img value="${model.image }" type="sized" width="48" />"
                                            width="48" height="48" /><span class="group_name">${model.name}</span>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </logic:notEmpty>

                <c:if test="${not empty equipmentUsersList}">
                    <div class="state" id="equipmentUsersList">
                        <div class="titles">
                            相关用户
                        </div>
                        <ul>
                            <c:forEach items="${equipmentUsersList}" var="model">
                                <li class="tlist_wrap clearfix">
                                    <img src="<com:img type="mini" value="${model.user.userFace}"/>"
                                         width="16" height="16" />
                                    &nbsp;<a href="u/${model.user.userId}">${model.user.userName}</a>:
                                    <c:choose>
                                        <c:when test="${model.gu.type eq 0}">不感冒</c:when>
                                        <c:when test="${model.gu.type eq 1}">暂且关注</c:when>
                                        <c:when test="${model.gu.type eq 2}">很喜欢</c:when>
                                        <c:when test="${model.gu.type eq 3}">手头有</c:when>
                                    </c:choose>
                                    <br/><span class="color_gray"><com:formatTime time="${model.gu.updateTime}"/></span>

                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <jsp:include page="_right.jsp" flush="true" />
            </div>
        </div>
        <script type="text/javascript"
                src="${context}/javascript/j2bb-editor-3.8.js"></script>
        <jsp:include page="../../bottom.jsp" flush="true" />
    </body>
</html>