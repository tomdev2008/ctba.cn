<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../common/error_page.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>部件展示&nbsp;-&nbsp;<bean:message key="nav.indexPage" />
		</title>
		<%@ include file="../common/include.jsp"%>
		<script type="text/javascript"
			src="javascript/jquery.jeditable.mini.js"></script>
		<script type="text/javascript" src="javascript/jquery.form.js"></script>
		<script type="text/javascript" src="javascript/j2bb-3.8.js?ct=090311"></script>
		<script src="javascript/jquery.d.imagechange.min.js"></script>
		<script src="javascript/jquery.flashSlider-1.0.min.js"
			type="text/javascript">
		<script>
			$(document).ready(function() {
				$(".edit").editable("http://www.example.com/save.php", {
					indicator : "更新中...",
					name : 'newvalue',
					tooltip   : '点击修改...'
				});
				$(".edit_area").editable("http://www.example.com/save.php", {
					type      : 'textarea',
					cancel    : '取消',
					submit    : '提交',
					indicator : "<img src='images/indicator.gif'>",
					tooltip   : '点击修改...'
				});
			});
		</script>
		<script src="./resource/GS-Bate-0.2/scripts/groupselect.js"
			language="JavaScript" type="text/javascript"></script>
		<link href="./resource/GS-Bate-0.2/styles/groupselect.css"
			rel="stylesheet" type="text/css" />
		<script type='text/javascript' src='javascript/jquery.boxy.js'></script>
		<link rel="stylesheet" href="theme/jquery/boxy.css" type="text/css" />
		<script type='text/javascript'>
			$(function() {
				$('.boxy').boxy();
				//$('#searchEngineHintDiv').boxy();
			});
		</script>

		<style>
/*容器*/
slider ul,#slider li {
	margin: 0;
	padding: 0;
	list-style: none;
}

/*数字导航样式*/
#flashnvanum {
	bottom: 10px;
	position: absolute;
	right: 20px;
	width: 90px;
}

#flashnvanum span {
	background: transparent url(images/slider/flashbutton.gif) no-repeat
		scroll -15px 0;
	color: #86A2B8;
	cursor: pointer;
	float: left;
	font-family: Arial;
	font-size: 12px;
	height: 15px;
	line-height: 15px;
	margin: 1px;
	text-align: center;
	width: 15px;
}

#flashnvanum span.on {
	background: transparent url(images/slider/flashbutton.gif) no-repeat
		scroll 0 0;
	color: #FFFFFF;
	height: 15px;
	line-height: 15px;
	width: 15px;
}
</style>
	</head>
	<body>
		<jsp:include page="../head.jsp" flush="true" />
		<div id="wrapper">
			<div id="area_left">
				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />
						&nbsp;
						<a href="${context }/"><bean:message key="nav.indexPage" /> </a>&nbsp;&gt;&nbsp;部件展示
					</div>
				</div>
				<div class="left_block">
					<div class="bh_title">
						<h2 class="bold">
							网站简介
						</h2>
					</div>
					<div class="bh_block">
						<img src="images/cs.png" align="left" />
						扯谈社（英文缩写 CTBA）是一家 Web2.0
						体育互动网站。它能帮助互联网用户找到与自己兴趣相投的朋友，分享、交流共同关注的体育赛事、新闻，并参与真实的体育活动。
					</div>
					<br />
				</div>
				<div class="left_block">
					<div class="bh_title">
						http://www.appelsiini.net/projects/jeditable
					</div>
					<div class="edit" id="div_1">
						单行的
					</div>
					<div class="edit_area" id="div_2">
						试试多行的
					</div>
					<br />
				</div>

				<div class="left_block">
					<form method='post' action='' id='form' class='boxy'>
						<a href='dev/widgets.jsp#foobar' class='boxy' title='WOW'>DIV(div#foobar)</a>
						|
						<a href='img.shtml?method=miniImageForm' class='boxy' title='上传图片'>图片
						</a> |
						<a href='emotionFrame.jsp' class='boxy'>显示表情 </a>&nbsp;&nbsp;
						<a href='img.shtml?method=miniImageForm&type=ubb' class='boxy'
							title='相册图片'>相册图片</a>&nbsp;

						<input type='submit' value='确认上传试试' />
						<div id='foobar'
							style='display: none; font-size: 30px; padding: 15px'>
							嘿嘿嘿
						</div>
					</form>
					<br class="clearfix" />
				</div>
				<br />
				<br class="clearfix" />


				<div class="left_block">

					<style type="text/css">
.user_div_wrap {
	border: 1px solid #ddd;
	color: #333;
	padding: 3px;
	margin: 5px 0;
	width: 450px;
	cursor: text;
	height: 21px;
}

.inner_wrap {
	background: #eee;
	margin: 1px;
	padding: 1px;
	display: inline;
	line-height: 18px;
	height: 21px;
}
</style>

					<div class="bh_title">
						#732 (站内信件的改进)
					</div>
					<div>
						<form id="userForm">
							<input type="hidden" id="user_items_value" name="users"
								value="gladstone,shimano,tifa" />
							<div id="user_div_wrap" class="user_div_wrap formInput">
								<div id="u_123" class="inner_wrap">
									gladstone
									<a href="javascript:removeItem('u_123','gladstone');"
										class="color_blue font_small">X</a>
								</div>
								<div id="u_456" class="inner_wrap">
									shimano
									<a href="javascript:removeItem('u_456','shimano');"
										class="color_blue font_small">X</a>
								</div>
								<div id="u_789" class="inner_wrap">
									tifa
									<a href="javascript:removeItem('u_789','tifa');"
										class="color_blue font_small">X</a>
								</div>
						</form>
					</div>
				</div>
				<br />
				&nbsp;
				<input value="initDummyData()" onclick="initDummyData();"
					type="button" class="input_btn" />
				&nbsp;
				<input value="getRemoteData()" onclick="getRemoteData();"
					type="button" class="input_btn" />
			</div>


			<div class="left_block">

				<div class="bh_title">
					#732 (站内信件的改进) 通过Ajax动态获取数据
				</div>

				<div style="padding: 10px;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div style="margin-left: 20px;" id="al-groupselect-active"></div>
					<!-- a href='' onclick="getresultactive();">结果</a> -->
					<div>
						<textarea class="formTextarea"></textarea>
					</div>
				</div>
			</div>


			<div class="left_block">
				<div class="bh_title">
					图片轮换
				</div>
				<div style="padding: 10px;">
					<div id="pic1">
					</div>
				</div>
			</div>

			<script>
// 需要你写的数据
var data = [
	{title:'扯谈社篮球',src:'images/ctba/1.gif',href:'http://www.ctba.cn',target:'_blank'},
	{title:'扯谈社羽球',src:'images/ctba/2.gif',},
	{title:'扯谈社商城',src:'images/ctba/3.gif',}
];
	
$(document).ready(function(){
	$('#pic1').d_imagechange({
		data:data,
		height:130,
		width:728,
		descTop:0,
		animateStyle:'o'//默认值'o',可以不写	
	});
});
</script>

<div class="left_block">
			<div class="bh_title">
				另一个图片轮换
			</div>
			<div style="padding: 10px;">
				<div id="pic2">
					<ul>
						<li><a href="http://www.ctba.cn">
							<img src="images/ctba/1.gif" alt="" /></a>
						</li>
						<li>
							<img src="images/ctba/2.gif" alt="" />
						</li>
						<li>
							<img src="images/ctba/3.gif" alt="" />
						</li>
					</ul>
				</div>
			</div>
			<script>
				            $("#pic2").flashSlider();
				</script>
		</div>
		
		</div>

		
		<script language="JavaScript" type="text/javascript">
	
$(document).ready(function(){  		
  getactivedata = gs_groupselect(activeParameters);	
});

var getactivedata; //取结果	
var activeParameters = gs_getGSParameters();
$(activeParameters).attr(
		{'frameId':'al-groupselect-active',
		 'needactive':false,
		 'maxcount':3,
		 'ajaxRequestUrl':{'group':'userSelector.action?method=group','item':'userSelector.action?method=items','active':'userSelector.action?method=suggest'}
});
		 
function getresultactive(){
	alert('你选的是： ' + getactivedata());
}
</script>



		<script>
				function array_has(val){
				  var i;
				  for(i = 0; i < this.length; i++){
				   if(this[i] == val){
				    return true;
				   }
				  }
				  return false;
				 }
				  Array.prototype.has = array_has;
				 			 
				 function array_remove(val){
				  var i;
				  var j;
				  for(i = 0; i < this.length; i++){
				   if(this[i] == val){
				    for(j = i; j < this.length - 1; j++){
				     this[j] = this[j + 1];
				    }
				    this.length = this.length - 1;
				   }
				  }
				 }
				  Array.prototype.remove = array_remove;
				 
				 function array_removeAt(index)
				 {
				  var i;
				  if(index < this.length)
				  {
				   for(i = index; i < this.length - 1; i++)
				   {
				    this[i] = this[i + 1];
				   }
				   this.length = this.length - 1;
				  }
				 }
				  Array.prototype.removeAt = array_removeAt;
				function removeItem(id,name){
					//$("#user_div_wrap")
					$("#"+id).remove();
					var usersStr = $("#user_items_value").val();
					var users = usersStr.split(",");
					if(users.has(name)){
						users.remove(name);
					}
					usersStr = users.join(",");
					$("#user_items_value").val(usersStr);
				}
				
				function addItem(id,name){
					var usersStr = $("#user_items_value").val();
					var users = usersStr.split(",");
					if(users.has(name)){
						return;
					}
					users[users.length] = name;
					usersStr = users.join(",");
					$("#user_items_value").val(usersStr);
					
					var addedStr = "<div id='"+id+"' class='inner_wrap'>"+name+" <a href=\"javascript:removeItem('"+id+"','"+name+"');\" class='color_blue font_small'>X</a></div>";
					$("#user_div_wrap").append(addedStr);
				}
				
				function initDummyData(){
					addItem('u_123','gladstone');
					addItem('u_456','shimano');
					addItem('u_789','tifa');
				}
				
				function getRemoteData(){
					 var options = {
					 	 url: 'ajax.shtml?method=dummyUsers',
					 	 type: 'POST',
					     dataType: 'json',
						 success: function(data) {
						 	for(i =0;i<data.length;i++){
						 		addItem('u_'+data[i].userId,data[i].userName);
						 	}
						 }
					 };
					 $('#userForm').ajaxSubmit(options);
				}
				</script>
		<div id="area_right">
			<div class="state">
				<ul class="right_menu">
					<li class="#">
						右边菜单
					</li>
					<li>
						<a href="#">当前项目</a>
					</li>
					<li class="last">
						<a href="#">最后一个</a>
					</li>
				</ul>
			</div>
			<div class="state widgets">
				<div class="titles">
					我的分享
				</div>
				<!--<script src="share.action?method=widget&uid=13110&limit=10&width=180"></script>-->
			</div>
		</div>
		</div>
		<jsp:include page="../bottom.jsp" flush="true" />
		<style type="text/css">
/* Widget for Tip */
#searchEngineHintDiv {
	position: fixed !important;
	right: 5px;
	_position: absolute;
	bottom: 0;
	_bottom: auto;
	top: expression(eval(document .           compatMode &&             document
		. 
		     
		   compatMode ==     
		     'CSS1Compat') ?             documentElement .           scrollTop
		+ 
		        
		  (      
		    documentElement .     
		     clientHeight -             this .           clientHeight ) -
		          
		 1 :  
		      
		   document .      
		    body .     
		     scrollTop +             (           document .           body .
		       
		  clientHeight -   
		         this .      
		    clientHeight ) -             1 );
	background: #fff;
	width: 250px;
	-moz-border-radius-topleft: 4px;
	-moz-border-radius-topright: 4px;
	-webkit-border-top-left-radius: 4px;
	-webkit-border-top-right-radius: 4px
}

#searchEngineHintDiv h2 {
	color: #fff;
	background: #f70;
	font-size: 12px;
	padding: 5px 8px;
	margin: 0;
	-moz-border-radius-topleft: 4px;
	-moz-border-radius-topright: 4px;
	-webkit-border-top-left-radius: 4px;
	-webkit-border-top-right-radius: 4px
}

#searchEngineHintDiv p {
	padding: 5px 8px;
	_padding: 7px 8px 5px 8px;
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom: 1px dashed #ddd
}

#searchEngineHintDiv span.fright a {
	color: yellow
}

#searchEngineHintDiv span.fright a:hover {
	color: white
}

#searchEngineHintDiv ul {
	padding: 6px 8px 8px 8px;
	_padding: 6px 8px;
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd
}

#searchEngineHintDiv ul li {
	width: 213px;
	height: 20px;
	line-height: 20px;
	padding-left: 18px;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
	-o-text-overflow: ellipsis; /*for opera*/
	background-position: -8px -262px
}

#searchEngineHintDiv ul li:after {
	content: "...";
	padding-left: 1px
}
</style>
		<div id="searchEngineHintDiv">
			<h2 class="clearfix">
				<span class="fleft"> 您在找&nbsp;[<strong>草泥马</strong>]&nbsp;吗?
				</span>
				<span class="fright"> <a href="javascript:void(0);"
					onclick="$('#searchEngineHintDiv').hide();">x</a> </span>
			</h2>
			<p>
				下面的信息可能对您有帮助
			</p>
			<ul>
				<li class="bullet_yellow">
					<a href="#">哈哈哈1</a>
				</li>
				<li class="bullet_yellow">
					<a href="#">哈哈哈2</a>
				</li>
				<li class="bullet_yellow">
					<a href="#">哈哈哈3</a>
				</li>
				<li class="bullet_yellow">
					<a href="#">哈哈哈4</a>
				</li>
				<li class="bullet_yellow">
					<a href="#">哈哈哈5</a>
				</li>
				<li class="bullet_yellow">
					<a href="#">六条的高度差不多六条的高度差不多六条的高度差不多</a>
				</li>
			</ul>
		</div>
	</body>
</html>