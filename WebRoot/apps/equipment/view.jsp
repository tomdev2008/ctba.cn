<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>${model.name }&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message key="nav.indexPage" /></title>
		<script type="text/javascript">
			// 是否已经投过票
			var voted = false;
			function checkCommentForm(){
				var content = $("#topicContent").val();
				if(content==''){
					alert("写点什么吧？");
					return false;
				}
				if(content.length>500){
					alert("您的评论太长鸟，请控制在500字以内");
					return false;
				}
				$("#submitButton").val("提交中，请稍候...");
				$("#submitButton").attr("disabled","disabled");
				return true;
			}
			function doVote(wid,value){
					<c:if test="${not empty __sys_username}">
					$.ajax({ url: "equipmentUser.shtml",
					type:"get",
					data: "method=vote&value="+value+"&wid="+wid,
					success:function(msg){
						document.getElementById("scoreDiv").innerHTML=msg+"<img src='images/text_scores.jpg'/>";
						voted = true;
					}
				});
					</c:if>
					<c:if test="${empty __sys_username}">
					J2bbCommon.showLoginForm();
					</c:if>
				}

			function doCommend(){
				var commendId = $("#commendId").val();
				var commendLabel = $("#commendLabel").val();
				if(commendLabel==''){alert("请填写您的售价或者建议");return;}
				$.ajax({
					url: "shopCommend.action",
					type: "post",
					data: "method=save&type=1&eid="+commendId+"&commendLabel="+commendLabel,
					success: function(msg){
						document.getElementById("commendTitleDiv").innerHTML="提交成功!";
						$('#commendDiv').hide();
					}
				});
			}
				<c:if test="${__user_is_editor}">
				function doSuperCommend(){
				var label = $("#superCommendLabel").val();
				var url = $("#superCommendUrl").val();
				if(label==''){alert("请填写文字");return;}
				if(url==''){alert("请填写链接");return;}
				$.ajax({
					url: "shopCommend.action",
					type: "post",
					data: "method=saveSuperCommend&type=1&eid="+${model.id}+"&url="+url+"&label="+label,
					success: function(msg){
						//document.getElementById("commendTitleDiv").innerHTML="提交成功!";
						$("#superCommendLabel").val("");
						$("#superCommendUrl").val("");
						window.location.reload();
					}
				});
			}

			function deleteCommend(id){
				$.ajax({
					url: "shopCommend.action",
					type: "post",
					data: "method=deleteSuperCommend&id="+id,
					success: function(msg){
						window.location.reload();
					}
				});
			}
				</c:if>
				function doRef(wid){
				var type = document.getElementById("equipmentUserSelect").options[document.getElementById("equipmentUserSelect").selectedIndex].value;
				location.href="${context}/equipmentUser.shtml?method=save&type="+type+"&wid="+wid;
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
			function setHover() {
				$("ul.opt2 li").mouseover(function(){
					$(this).addClass("hover");
				}).mouseout(function(){
					$(this).removeClass("hover");
				});
			}

			var rateIt = function(){
				if(voted){return;}
				var currId = parseInt($(this).attr("rate"));
				$("#rateArea li a").css({background: "#FFFDDD" });
				$("#rateArea li").each(
					function(){
						var subId = parseInt($(this).attr("rate"));
						if(subId <= currId){
							$(this).find("a").css({background: "#FF7700" });
						}
					}
				);
			};
			
			$(function(){
				setHover();
				// #772 (评分显示样式修改)
				// #FF7700 | #FFFDDD
				$("#rateArea li").hover(
					rateIt,
					function() {
						if(!voted){
							$("#rateArea li a").css({background: "#fffddd"});
						}
					}
				);
				$("#rateArea li").click(
					rateIt
				);
			}
		);
		</script>
		<style type="text/css">
			.simple_list li {
				padding: 4px 0;
				border-bottom: 1px dashed #eee
			}
			.simple_list li.last {
				border: none
			}
		</style>
	</head>
	<body>
		<jsp:include flush="true" page="./head.jsp"/>
		<div id="wrapper">
			<div id="area_left">

				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;>&nbsp;<com:limit maxlength="30" value="${model.name }" />
					</div>
				</div>

				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_userBar.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<div class="mid_ad">
					</div>
					<h1 class="equipment_name">
						<a href="equipment/${model.id }">${model.name}</a>&nbsp;&nbsp;<span class="graylink"><%--<a href="equipment/${model.id }"><img src="images/icons/addfav.gif" /></a>--%></span>&nbsp;&nbsp;<span class="graylink color_gray font_small">&nbsp;by <a href="u/${authorModel.userId }">${authorModel.userName}</a></span>
					<c:if test="${model.state eq 0}"><span class="message_error">
								&nbsp;审批中
					</span></c:if>
					
					</h1>
					<div class="equipment_view mid_block lightgray_bg">
						<div class="grade_wrap clearfix">
							<div class="grade_left"></div>
							<ul id="rateArea" class="fleft clearfix">
								<% for (int i = 1; i <= 10; i++) {%>
								<li id="rate_<%=i%>" rate="<%=i%>">
									<a href="javascript:doVote(${model.id },<%=i%>);" title="<%=i%>"></a>
								</li>
								<% }%>
							</ul>
							<div id="scoreDiv" class="grade_average color_orange">
								${voteScore }
								<img src="images/text_scores.jpg" alt="scores" />
							</div>
							<div class="grade_user_state">
								<select id="equipmentUserSelect" onchange="doRef(${model.id });">
									<option value="0" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 0)}">selected</c:if>>
										不感冒
									</option>
									<option value="1" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 1)}">selected</c:if>>
										暂且关注
									</option>
									<option value="2" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 2)}">selected</c:if>>
										很喜欢, 想拥有
									</option>
									<option value="3" <c:if test="${(not empty equipmentUser) &&(equipmentUser.type eq 3)}">selected</c:if>>
										手头正好有
									</option>
								</select>
							</div>
						</div>
						<div class="equipment_photo_large center">
							<c:if test="${empty nextModel}">
								<img src="<com:img value="${model.image }" type="none" />" onload='if(this.width>570){this.width=570;}' alt="${model.name}" />
							</c:if>
							<c:if test="${not empty nextModel}">
								<a href="equipment/${nextModel.id }">
									<img src="<com:img value="${model.image }" type="none" />" onload='if(this.width>570){this.width=570;}' alt="${model.name}" />
								</a>
							</c:if>
						</div>
						<div class="equipment_intro_text lightyellow_bg radius_all">
							简介：<com:topic value="${model.discription }" />
						</div>

						<c:if test="${not empty shopModel}">
							<div class="equipment_intro_text lightyellow_bg radius_all">
								所属商铺：<a href="equipment/shop/${shopModel.id}">${shopModel.name}</a>
							</div>
						</c:if>

						<div class="equipment_intro_items radius_all clearfix">
							<dl>
								<dt>装备品牌：</dt>
								<dd>${model.brand }</dd>
								<dt>包含技术：</dt>
								<dd>${model.tech }</dd>
								<dt>装备货号：</dt>
								<dd>${model.num }</dd>
								<dt>官方售价：</dt>
								<dd style="color:#f70">${model.officailprice }</dd>
								<dt>关键词：</dt>
								<dd>${model.keyword }</dd>
							</dl>
							<dl>
								<dt>装备类别：</dt>
								<dd>${equipmentType }</dd>
								<dt>简短名称：</dt>
								<dd>${model.shortname }</dd>
								<dt>上市时间：</dt>
								<dd style="color:#f70">${model.saleTime }</dd>
								<dt>购买价格：</dt>
								<dd>${model.price }</dd>
								<dt>官方网站：</dt>
								<dd class="bluelink"><a <c:if test="${not empty model.link }">href="${model.link }"</c:if> <c:if test="${empty model.link }">href="equipment/${model.id }"</c:if> target="_blank">点击浏览</a></dd>
							</dl>
						</div>
					</div>
					<div class="line_block color_gray graylink clear clearfix">
						<ul class="lo" style="float: right">
							<!--li class="icons_user">
								${model.username}&nbsp;<a href="equipment.shtml?method=list&wrap-uid=${authorModel.userId }">[只看${model.username}的]</a>&nbsp;|&nbsp;
							</li-->
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
								<a href="equipment.shtml?method=form&wid=${model.id }">编辑</a>&nbsp;&nbsp;
								<a href="equipment.shtml?method=delete&wid=${model.id }" onclick="return confirm('您确定要删除么？');">删除</a>
							</c:if>
						</ul>
					</div>
					<pg:pager items="${count}" url="equipment.shtml" index="center" maxPageItems="15" maxIndexPages="6" isOffset="<%=false%>" export="pageOffset,pageNo=pageNumber,currentPageNumber=pageNumber" scope="request">
						<pg:param name="method" />
						<pg:param name="wid" />
						<logic:notEmpty name="commentList">
							<div class="mid_block top_blank_wide clearfix">
								<logic:iterate id="map" indexId="index" name="commentList">
									<div>
										<div class="reply_list orangelink clearfix">
											<div class="reply_user">
												<img src="<community:img value="${map.user.userFace }" type="sized" width="32" />" width="32" height="32" title="${map.user.userName}" alt="${map.user.userName}" />
											</div>
											<div id="reply_info" class="linkblue_b">
												<a href="u/${map.user.userId}">${map.user.userName}</a>
											</div>
											<div class="reply_date">
												<div class="fleft">
													<span class="font_small color_gray"><community:date value="${map.comment.updateTime }" start="2" limit="16" /></span>
													<c:if test="${not empty __sys_username}">
														|&nbsp;<a href="javascript:re(${30*(currentPageNumber-1)+index+1 },'${map.user.userName }');">回复</a>
													</c:if>
													<c:if test="${not empty __sys_username and __sys_username eq map.comment.username}">
														|&nbsp;<a href='javascript:void(0);' onclick="J2bbCommon.confirmURL('您确定要删除么？','${basePath }equipment.shtml?method=deleteComment&cid=${map.comment.id }');return false;">删除</a>
													</c:if>

												</div>
											</div>
											<div id="reply_content_mid">
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
						<div class="mid_block top_blank_wide clearfix radius_all lightgray_bg" style="padding: 10px 10px 3px 10px; _padding: 10px">
							<form action="equipment.shtml?method=saveComment" method="post"
							onsubmit="return checkCommentForm();">
								<input type="hidden" name="wid" value="${model.id }" />
								<input type="hidden" name="para_reply_to" id="para_reply_to" value="${authorModel.userName }"/>
								<com:ubb />
								<textarea class="ftt" style="width:536px"  name="content" id="topicContent"></textarea>
								<br />
								<input type="submit" name="submitButton" id="submitButton" class="input_btn btn_mt" value="发表评论" />
							</form>
						</div>
					</c:if>
					<jsp:include page="../../_loginBlock.jsp"></jsp:include>
				</div>
			</div>
			<div id="area_right">
			
			<c:if test="${could_buy}">
			<div class="state">
			<a href="order.action?method=create&type=equipment&id=${model.id }"><img alt="购买" src="images/buy_now.jpg"/></a>
			</div>
			</c:if>
			
				<div class="state">
					<c:if test="${not empty commendList}">
						<div class="titles" style="color:#f70">
							还有在哪可以买到？
						</div>
						<ul class="simple_list">
							<%--
					<c:if test="${not empty shopModel}">
						<li class="clearfix">
							<span class="fleft">
								<a href="equipment/shop/${shopModel.id}">${shopModel.name}</a>
							</span>
							<span class="fright color_orange">
							${model.price}
							</span>
						</li>
					</c:if>
					--%>
							<c:forEach items="${commendList}" var="model" varStatus="status">
								<c:if test="${(model.type == 1) and (not (model.shopModel.id eq shopModel.id))}">
									<li class="clearfix">
										<span class="fleft">
											<a href="equipment/shop/${model.shopModel.id}">${model.shopModel.name}</a>
										</span>
										<span class="fright color_orange">
										</span>
									</li>
								</c:if>

								<c:if test="${model.type == 2}">
									<li class="clearfix">
										<span class="fleft">
											<a target="_blank" href="${model.url}" title="${model.label}"><com:limit maxlength="15" value="${model.label}"/></a> &nbsp;
											<c:if test="${__user_is_editor}">
												<a onclick="return confirm('您确定要删除么?');" href="javascript:deleteCommend('${model.id }');"><img src="images/icons/close.gif" width="8"/></a>
											</c:if>
										</span>
										<span class="fright color_orange">
										</span>
									</li>
								</c:if>
							</c:forEach>

						</ul>
					</c:if>
					<%--
					<c:if test="${(not empty __sys_username) and (not empty __request_shop) and (__request_shop.id != shopModel.id)}">
					<!--<div id="commendTitleDiv" class="titles" style="color:#f70" onclick="$('#commendDiv').toggle();">
						我的店铺也有!
					</div>-->

					<!--div id="commendDiv" style="display:none;"-->
					<div id="commendDiv">
					<input name="eid" id="commendId" value="${model.id}" type="hidden">
					我的店铺售<input id="commendLabel" name="label" class="formInput" style="width:50px;" value="${model.price }">
					<input type="button" class="input_btn" value="提交" onclick="doCommend();"/>
					</div>
					</c:if>
					--%>
					<c:if test="${__user_is_editor }">
					
						<div id="commendTitleDiv" class="titles" style="color:#f70" onclick="$('#editorCommendDiv').toggle();">
							添加来源
						</div>
						<div id="editorCommendDiv">
							文字&nbsp;<input id="superCommendLabel" name="label" class="formInput" style="width:120px;" ">
										<br/>
							链接&nbsp;<input id="superCommendUrl" name="url" class="formInput" style="width:120px;"  ">
										<br/>
							<input type="button" class="input_btn" value="提交" onclick="doSuperCommend();"/>
						</div>
						<br class="clear"/>
						<div>
							<a class="link_btn" href="equipment.shtml?method=toggleState&wid=${model.id }">切换审批状态</a>
							
						</div>	
					</c:if>

				</div>
				<%--<logic:notEmpty name="refEquipmentList">
					<div class="state">
						<div class="titles">
							相关装备
						</div>
						<ul class="hot_group clearfix" id="hot_group">
							<c:forEach items="${refEquipmentList}" var="model">
								<li>
									<a class="stars_border" href="equipment/${model.id }">
										<img src="<community:img value="${model.image }" type="sized" width="48" />" width="48" height="48" /><span class="group_name">${model.name}</span>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</logic:notEmpty>--%>
				<c:if test="${not empty equipmentUsersList}">
					<div class="state" id="equipmentUsersList">
						<div class="titles">
							会员关注
						</div>
						<ul>
							<c:forEach items="${equipmentUsersList}" var="model">
								<li class="tlist_wrap clearfix">
									<img src="<com:img type="mini" value="${model.user.userFace}"/>" width="16" height="16" align="absmiddle" class="" />
									&nbsp;<a href="u/${model.user.userId}">${model.user.userName}</a>:
									<c:choose>
										<c:when test="${model.gu.type eq 0}">不感冒</c:when>
										<c:when test="${model.gu.type eq 1}">暂且关注</c:when>
										<c:when test="${model.gu.type eq 2}">很喜欢</c:when>
										<c:when test="${model.gu.type eq 3}">手头有</c:when>
									</c:choose>
									&nbsp;<span class="color_gray"><com:formatTime time="${model.gu.updateTime}"/></span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<div class="state">
					<ul class="infobar">
						<li class="icons_feed">
							<a href="rss.shtml" target="_blank"><bean:message key="rss.feed" />
							</a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${context}/javascript/j2bb-editor-3.8.js"></script>
		<jsp:include page="../../bottom.jsp" flush="true" />
	</body>
</html>