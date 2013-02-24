<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="../common/include.jsp"%>
        <title>${imageModel.name}&nbsp;-&nbsp;${galleryModel.name}&nbsp;-&nbsp;个人相册&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
        <script type="text/javascript">
            $(function(){
                $('.photo_title a').hide();
                $('#photo_view img').css('max-width', 'none').hide();

                photoTimer = setInterval(function() {
                    photoWidth = $('#photo_view img').width();
                    photoHeight = $('#photo_view img').height();
                    if(photoWidth) {
                        clearInterval(photoTimer);
                        $('#photo_view img').show();
                        if(photoWidth > 590) {
                            $('.photo_title a').html('查看原始尺寸' + '<span class="font_mid">: ' + photoWidth + ' x ' + photoHeight + '</span>').show();
                        }
                        $('#photo_view img').css('max-width', '590px');
                        $('#photo_view').css('background', 'url(images/photo_loader.gif) no-repeat center');
                        if($.browser.msie && $.browser.version == 6.0) {
                            if(photoWidth > 590) {
                                $('#photo_view img').width(590);
                            }
                        }
                    }
                }, 10);

            <c:if test="${ __user_is_editor or (imageModel.username eq __sys_username) }">
                    $('.photo_title strong')
                    .css('cursor', 'pointer')
                    .click(function(){
                        $('.photo_title').hide();
                        $('#modifyTitle').show();
                    })
                    .append('<em class="em_modify">(修改)</em>');
                    $("input[type='reset']").click(function(){
                        $('#modifyTitle').hide();
                        $('.photo_title').show();
                    });
            </c:if>

                });
                var checkForm = function() {
                    J2bbCommon.removeformError('topicContent');
                    var content = $('#topicContent').val();
                    if(content==''||content.length>500){
                        J2bbCommon.formError('topicContent', '内容不能为空，不能多于500字' ,'red');
                        return false;
                    }
                    $('#submitButton').val('提交中，请稍候...');
                    $('#submitButton').attr('disabled', 'disabled');
                    return true;
                }
                var copyAddr = function() {
                    copyToClip($('#imageAddress').val());
                    alert("图片地址已成功复制到剪贴板:)");
                }
			
                var re = function (index,username) {
                    $('#para_reply_to').val(username);
                    $('#topicContent').val('@'+username+' '+index+'#\n');
                    $('#topicContent').focus();
                }
        </script>
        <style type="text/css">
            .em_modify { font: italic 12px Arial; color: #ccc; padding-left: 5px }
            .photo_title { padding-top: 3px; }
            .photo_title strong { float: left; max-width: 410px; word-wrap: break-word }
            .photo_title em:hover { color: #f70 }
            .photo_title a { float: left; font-size: 12px; height: 18px; line-height: 18px; color: #fff; background: #ccc; padding: 0 5px; margin: 2px 0 0 10px }
            .photo_title a .font_mid { display: none }
            .photo_title a:hover { background: #f70 }
            .photo_title a:hover .font_mid { display: inline }
        </style>
    </head>
    <body>
        <jsp:include flush="true" page="../head.jsp">
            <jsp:param name="submenu" value="app" />
        </jsp:include>
        <div id="wrapper">
            <div id="area_left">
                <div id="webmap" class="clearfix">
                    <div class="fleft">
                        <img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="albumn/">扯谈相册</a>&nbsp;&gt;&nbsp;<a href="album/${galleryModel.id}">${galleryModel.name	}</a>
                    </div>
                </div>
                <div id="sidebar" class="fleft clearfix">
                    <jsp:include flush="true" page="_logo.jsp" />
                    <jsp:include flush="true" page="../_operationBlock.jsp" />
                    <jsp:include flush="true" page="../_adBlockMini.jsp" />
                </div>
                <div id="mid_column" class="fright">
                    <div class="mid_block">
                        <jsp:include page="_galleryInfo.jsp"></jsp:include>
                    </div>
                    <div class="mid_block">
                        <h1 class="photo_title clearfix">
                            <strong>${imageModel.name}</strong><a href="http://static.ctba.cn/files/${imageModel.path}" target="_blank" style="display:none"></a>
                        </h1>
                        <div id="modifyTitle" style="display:none">
                            <form action="img.shtml?method=saveImage" method="post">
                                <input name="imageId" value="${imageModel.id }" type="hidden"/>
                                <input class="font_mid color_gray formInput" style="width:300px;font-size:14px;font-weight:bold;" value="${imageModel.name}" id="imageName" name="imageName"/>
                                <c:if test="${(imageModel.username eq __sys_username) }">
                                    &nbsp;<community:select items="${galleryList}" name="galleryId" selected="${imageModel.galleryId}" label="name" value="id" />
                                </c:if>
                                <c:if test="${not (imageModel.username  eq __sys_username) }">
                                    <input name="galleryId" value="${imageModel.galleryId }" type="hidden" />
                                </c:if>
                                &nbsp;&nbsp;&nbsp;<input type="submit" class="input_btn"  value="修改" />
                                &nbsp;<input type="reset" class="input_btn" value="取消" />
                            </form>
                        </div>
                        <p id="photo_view" class="graylink center">
                            <c:if test="${not empty prevModel}">
                                <a href="albumn/photo/${prevModel.id }">
                                    <img src="<community:img value="${imageModel.path}" type="none" />" alt="${imageModel.name}" />
                                </a>
                            </c:if>
                            <c:if test="${empty prevModel}">
                                <a href="albumn/${galleryModel.id }">
                                    <img src="<community:img value="${imageModel.path}" type="none" />" alt="${imageModel.name}" />
                                </a>
                            </c:if>
                        </p>
                        <p align="center">
                            <c:if test="${not empty nextModel}">
                                <img src="images/icons/pico_left.gif" align="absmiddle" />&nbsp;<a href="albumn/photo/${nextModel.id }">上一张</a>&nbsp;&nbsp;&nbsp;
                            </c:if>
							共&nbsp;<span class="color_orange font_mid">${imageModel.hits }</span>&nbsp;次浏览&nbsp;&nbsp;&nbsp;
                            <a href="feedback.action?method=form&type=image&id=${imageModel.id }">举报</a>&nbsp;&nbsp;&nbsp;
                            <c:if test="${imageModel.username eq __sys_username }">
                                <a href="img.shtml?method=setGalleryFace&galleryId=${galleryModel.id}&id=${imageModel.id}">设为封面</a>&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0);" onclick="J2bbCommon.confirmURL('您确认要删除么？','${basePath }img.shtml?method=deleteImg&id=${imageModel.id }');return false;">删除</a>&nbsp;&nbsp;&nbsp;
                            </c:if>
                            <c:if test="${not empty prevModel}">
                                <a href="albumn/photo/${prevModel.id }">下一张</a>&nbsp;<img src="images/icons/pico_right.gif" align="absmiddle" />
                            </c:if>
                        </p>
                    </div>
                    <div class="mid_block">
                        &nbsp;照片地址&nbsp;&nbsp;<input class="font_mid color_gray formInput" style="width:400px;" value="http://static.ctba.cn/files/${imageModel.path}" id="imageAddress"/>&nbsp;&nbsp;<input type="button" class="input_btn" onclick="copyAddr();" value="复制" />
                    </div>
                    <logic:notEmpty name="commentMapList">
                        <div class="mid_block top_blank_wide clearfix">
                            <pg:pager items="${count}" url="img.shtml" index="center" maxPageItems="30" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
                                <pg:param name="method" />
                                <pg:param name="id" />
                                <logic:iterate id="map" indexId="index" name="commentMapList">
                                    <div>
                                        <div class="reply_list orangelink clearfix">
                                            <div class="reply_user">
                                                <img src="<community:img value="${map.user.userFace }" type="sized" width="32" />" width="32" height="32" title="${map.user.userName}" alt="${map.user.userName}" />
                                            </div>
                                            <div id="reply_info" class="linkblue_b">
                                                <a href="u/${map.user.userId}">${map.user.userName}</a>
                                                <span class="color_gray"><community:ipData value="${map.comment.ip }"/></span>
                                            </div>
                                            <div class="reply_date">
                                                <div class="fleft">
                                                    <span class="font_small color_gray"><community:date value="${map.comment.updateTime }" start="2" limit="16" />&nbsp;|</span>
                                                </div>
                                                <ul class="opt2 fleft">
                                                    <li>
                                                        <span class="opt_arrow"></span>
                                                        <ul>
                                                            <li>
                                                                <c:if test="${not empty __sys_username and __sys_username eq map.comment.username}">
                                                                    <a href='img.shtml?method=deleteImgComment&id=${map.comment.id }' onclick="J2bbCommon.confirmURL('您确定要删除么？','img.shtml?method=deleteImgComment&id=${map.comment.id }');return false;">删除本楼</a>
                                                                </c:if>
                                                            </li>
                                                            <c:if test="${not empty __sys_username }">
                                                                <li>
                                                                    <a href="javascript:re(${30*(currentPageNumber-1)+index+1 },'${map.user.userName }');">回复本楼</a>
                                                                </li>
                                                            </c:if>
                                                            <li><a href="javascript:window.scroll(0,0)">返回顶部</a></li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div id="reply_content_mid">
                                                <community:topic value="${map.comment.body}"/>
                                            </div>
                                        </div>
                                    </div>
                                </logic:iterate>
                                <div class="pageindex nborder">
                                    <jsp:include flush="true" page="../common/jsptags/simple.jsp"></jsp:include>
                                </div>
                            </pg:pager>
                        </div>
                    </logic:notEmpty>
                    <c:if test="${not empty __sys_username}">
                        <div class="mid_block top_blank_wide clearfix radius_all lightgray_bg" style="padding: 10px 10px 3px 10px;_padding:10px">
                            <form action="img.shtml?method=saveImgComment" method="post" onsubmit="return checkForm();">
                                <community:ubb/>
                                <input name="imageId" value="${imageModel.id }" type="hidden"/>
                                <input type="hidden" name="para_reply_to" id="para_reply_to" value="${imageModel.username}"/>
                                <textarea class="ftt" style="width:536px" name="body" id="topicContent"></textarea><br />
                                <input type="submit" class="input_btn btn_mt" id="submitButton" value="发表评论" />
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
            <div id="area_right">
                <jsp:include page="right.jsp" flush="true" />
            </div>
        </div>
        <script type="text/javascript" src="javascript/j2bb-editor-3.8.js"></script>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>
