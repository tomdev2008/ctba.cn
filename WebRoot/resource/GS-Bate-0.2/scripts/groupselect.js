/*
 * 
 * author:alzhang
 * version:0.1
 * data:2009-04-28
 * depend: jquery-1.3.2
 * 
 * */

//请使用此方法取得一份初始参数！
function gs_getGSParameters () {
	return $.extend(true, {}, gs_initParameters);
}

//默认的，可以在此基础上修改为自定义的初始化参数！(请不要直接使用此参数)
var gs_initParameters = {
		'frameId':'',//div的ID，控件将于此div内生成
		'edit'://如果需要编辑请设置以下属性
			{'need':false,//是否是编辑状态，true的话同时必须设置下面2个属性
			 'gid':'',//已经选择的组Id
			 'item':''//已经选择的Item,数据格式为JSON: {'id':'','name':''}.
			},
		'canseldiffitem':true,//能否选择不同组的item (from V0.2 alzhang)
		'groupInfo':
			{'divId':'',//如果需要取得或者显示选取的group信息,请指定此Div的ID
			 'gid':'',//group id将存放于此attr中
			 'gname':''//group name将存放于此attr中
			},
		'needactive':false,//是否需要动态提示，比如首字母输入
		'checkmax':true,//是否需要最大数目限制
		'maxcount':3,//最大选择数
		'ajaxRequestType':'POST',//ajax请求方式，post or get
		'ajaxRequestUrl':
			{'group':'',//请求group数据的url
			 'item':'',//请求item的url
			 'active':''//请求active对应数据的url
		    },
			//如果不为空 发起ajax 请求，请求数据, (数据为json格式)
		'ajaxRequestKey':
			{'groupid':'id','activetext':'text'},
			//如果发起ajax 请求，提交的参数使用此key，groupid为取此group下的item, activetext为动态输入内容
		'getStaticData':
			{'activeData':'',//函数，如果不使用ajax则从此取得active对应的数据，提供输入text参数
			 'groupData':'',//函数，如果不使用ajax则从此取得Group数据
			 'itemData':''//函数，如果不使用ajax则从此取得Item数据，提供组id			
			},
		'img':
			{'downOver':'./images/downover.gif',//down focus
			 'downOut':'./images/downout.gif',//down blur
			 'upOver':'./images/upover.gif',//up focus
			 'upOut':'./images/upout.gif'//up blur
			},
		'message':
			{'imgTitle':'选择好友',
			 'select':'请选择好友',
			 'buttonOK':'确定',
			 'buttonCancle':'取消',
			 'active1':'请输入好友的姓名',
			 'active2':'请输入好友的姓名(支持拼音首字母输入)',
			 'active3':'姓名不在好友列表哦，请重新输入',
			 'noactive':'请从下拉菜单中选择！',
			 'max1':'最多只能选择',
			 'max2':'个用户！',
			 'result':'请选择好友！'
			 //'result':'DUMMY'
			}
		};

// 返回结果为一个函数，其调用返回选择的item的Id的一个字符串。
function gs_groupselect(pars){	
	
	var data = [];// 存放最终选择结果
	var activedata = [];// 存放 active 数据
	var suggestitems = [];// 存放 备选择数据
	var itemsdata = [];// 下拉框 数据
	var itemcheckbox = [];// 选择item checkbox
	var checkmax = pars.checkmax;// 是否检查max count
	var maxcount = pars.maxcount;// 允许选择最多items

	var gs_alert = false;// 临时 alert

	var topFrame;
	var contentFrame;// div 包括输入框中全部items

	var popEvent;// 下拉事件
	var popImg;// 下拉图片
	var popMenu;// 下拉框

	var activeDiv;// 包括 active & suggest
	var active;// 动态输入框
	var suggest;// 供选择框
	var emptysuggest;// 提示动态输入信息
	var groupspan;// 组 选择
	var groupInput;// select 控件
	var allitems;// 全部选项
	var selectedGroupId;//已经选择的Group的index
	
		// initialize
		gs_init();
			
	  //gourp select !!	
		function gs_init () {
			
		  	topFrame = $('<div/>').appendTo($('<div/>').appendTo('#' + pars.frameId).addClass('frame l it_s').css('width','300px')).addClass('it1');
		  	
		  	// the content of top frame
			  contentFrame = $('<div/>').appendTo(topFrame).addClass('l').css({cursor:'text',height:'23px',width:'215px'}).click(function(){gs_inputOnclick();});
			  
			  ////////////////////////////////
				var flowFrame = $('<div/>').appendTo(topFrame).addClass('fsg22');
				$('<div/>').appendTo(topFrame).addClass('c');
				
				// the flow pop event
				popEvent = $('<div/>').appendTo(flowFrame).click(function(){gs_getPopData();});
				
				// the flow pop menu
				popMenu = $('<div/>').appendTo(flowFrame).addClass('fsg_nr').css('width','310px');
				
				popImg = $('<img/>').appendTo(popEvent).addClass('cp').attr(({src:pars.img.downOut,alt:pars.message.imgTitle,title:pars.message.imgTitle})).mouseover(function(){this.src=pars.img.downOver}).mouseout(function(){this.src=pars.img.downOut});
				//上部 group 选择
				var groupMenu = $('<div/>').appendTo(popMenu).addClass('sgt_on').css('width','300px');
				//选项选择区域
				allitems = $('<div/>').appendTo(popMenu).css({width:'300px',height:'100px',overflow:'scroll','overflow-x':'hidden'});
				//下部 按钮区域
				var buttonsMenu = $('<div/>').appendTo(popMenu).addClass('tac p5');
				
				$('<div/>').appendTo(groupMenu).addClass('l').text(pars.message.select);
				// group 
				groupspan = $('<span/>').appendTo($('<div/>').appendTo(groupMenu).addClass('r').css('padding-right','20px'));
				$('<div/>').appendTo(groupMenu).addClass('c');
				
				// group intpu
				groupInput = $('<select></select>').appendTo(groupspan).change(function(){gs_getItemData();});
				
				// buttons
				var buttonsDiv = $('<div/>').appendTo(buttonsMenu).addClass('gbs1');
				$('<input type="button"/>').appendTo(buttonsDiv).attr({value:pars.message.buttonOK,title:pars.message.buttonOK}).addClass('gb1-12').mouseover(function(){this.className='gb2-12'}).mouseout(function(){this.className='gb1-12'}).click(function(){gs_selectitem();});
				$('<input type="button"/>').appendTo(buttonsDiv).attr({value:pars.message.buttonCancle,title:pars.message.buttonCancle}).addClass('gb1-12').mouseover(function(){this.className='gb2-12'}).mouseout(function(){this.className='gb1-12'}).click(function(){gs_cancleitem();});
				$('<div/>').appendTo(buttonsMenu).addClass('c');
				
				gs_initGroup();
		}
	
		function gs_initGroup(){
			//if(($(groupspan).size() > 0) && ($(groupInput).html() == ""))
			//{
				//取得 group data
				//修改 在页面初始化时 初始化Group数据， V0.2 alzhang
				if(pars.ajaxRequestUrl.group != ""){
					$.ajax({
						   type: pars.ajaxRequestType,
						   url: pars.ajaxRequestUrl.group,
						   dataType: "json",
						   success: function(json){
						   		gs_getGroupData(json);
						   		gs_initGroupOther();
						   		//if(null == selectedGroupId || '' == selectedGroupId){
						   		//	selectedGroupId = $(groupInput).val();
						   		//}
						   }
					});
				}else {
					gs_getGroupData(pars.getStaticData.groupData());
					gs_initGroupOther();
			   		//if(null == selectedGroupId || '' == selectedGroupId){
			   		//	selectedGroupId = $(groupInput).val();
			   		//}
				}
			//}
			
		}
		
		function gs_initGroupOther(){
			//编辑状态，选择已经选择的group
			if(pars.edit.need){
				$(groupInput).val(pars.edit.gid);
				
				$(pars.edit.item).each(function(){//V 0.2 alzhang, 选择已经选择的item
					$(this).attr('type','static');
					data[data.length] = this;
				});
				
				gs_superView();
			}
			//同步设置外部group 信息
			if(null != pars.groupInfo.divId && '' != pars.groupInfo.divId){
				gs_setGroupInfo();
			}
		}
		
		function gs_setGroupInfo(){
			$('#' + pars.groupInfo.divId).attr(pars.groupInfo.gid,$(groupInput).val()).attr(pars.groupInfo.gname,$(groupInput).find(':selected').text()).text($(groupInput).find(':selected').text());
		}
		
			//输入框 点击事件
		function gs_inputOnclick()
		{
			$(popMenu).css('display','none'); //隐藏下拉框
			if(gs_hasActive(data) != -1) // 有active的数据时点击，自动选择数据
			{
				if($(active).val() != "" && $(suggest).css('display','none')) //这个判断情况不会发生
				{
					$(active).val('');
				}
				$(active).focus();
			}
			else
			{
				data[data.length] = {type:"active"};//添加一个 active 数据
				gs_superView();
			}
			
			//$(fs_superinput).parentNode.className = "it2"; //点击后选择外框式样变化 目前无变化
			
		}

		//active focus
		function gs_inputOnfocus(thisobj)
		{
			if(thisobj.value == "")
			{
				$(emptysuggest).css('display','block');
				if(pars.needactive){
					$(emptysuggest).text(pars.message.active1);
				}else {
					$(emptysuggest).text(pars.message.noactive);
				}
				
			}
		}

		// active key down 事件 up down left right
		function gs_inputOnkeydown(evnt)
		{
			$(active).css('width',b_strlen($(active).val())*6+20 + "px"); //根据Data长度 变化 active长度
			
			if (evnt.keyCode == 13) //禁止回车
			{
				return false;
			}
			
			var activenum = gs_hasActive();
			
			//BACKSPACE
			//删除数据 并更新data
			if(evnt.keyCode == 8 && data[activenum-1] && $(active).val() == "")
			{
				var tdata = [];
				var j=0;
				var len = data.length;
				for(var i=0; i<len ; i++)
				{
					if(activenum-1 == i)
					{
						continue;
					}
					tdata[j] = data[i];
					j++;
				}
				data = tdata;
				//fs_dirty = true;
				gs_superView();
				return false;
			}
		
			//LEFT 未实现
			if(evnt.keyCode == 37 && data[activenum-1] && $(active).val() == "")
			{
				return;
				//var obj = data[activenum];
				//data[activenum] = data[activenum-1];
				//data[activenum-1] = obj;
				//gs_superView();
				//return;
			}
			
			//RIGHT 未实现
			if(evnt.keyCode == 39 && data[activenum+1] && $(active).val() == "")
			{
				return;
				//var obj = data[activenum];
				//data[activenum] = data[activenum+1];
				//data[activenum+1] = obj;
				//gs_superView();
				//return;
			}
			
			//DOWN 下 包括suggest 中的选择
			if(evnt.keyCode == 40)
			{
				var hotinfo = gs_getHotNum();
				var hotnum = hotinfo.hotnum;
			  var num = hotinfo.totalnum;
			  
				if($(suggestitems[0]) != null && $(suggest).css('display') == "block")
				{
					if(hotnum == -1)
					{
						$(suggestitems[0]).removeClass();
						$(suggestitems[0]).addClass('sgt_on');
					}
					else
					{
						var nextnum = hotnum == num-1 ? 0 : hotnum+1;
						$(suggestitems[hotnum]).removeClass();
						$(suggestitems[hotnum]).addClass("sgt_of");
						$(suggestitems[nextnum]).removeClass();
						$(suggestitems[nextnum]).addClass("sgt_on");
					}
					return false;
				}
			}
			
			//UP
			if(evnt.keyCode == 38)
			{
				var hotinfo = gs_getHotNum();
				var hotnum = hotinfo.hotnum;
			  var num = hotinfo.totalnum;
			  
				if($(suggestitems[0]) != null && $(suggest).css('display') == "block")
				{
					if(hotnum == -1)
					{
						$(suggestitems[num-1]).removeClass();
						$(suggestitems[num-1]).addClass('sgt_on');
					}
					else
					{
						var prevnum = hotnum == 0 ? num-1 : hotnum-1;
						$(suggestitems[hotnum]).removeClass();
						$(suggestitems[hotnum]).addClass("sgt_of");
						$(suggestitems[prevnum]).removeClass();
						$(suggestitems[prevnum]).addClass("sgt_on");
					}
				}
			}
			
			if(!pars.needactive){
				return false;
			}
		}

		//active key up 事件
		function gs_inputOnkeyup(evnt)
		{
			if(!pars.needactive){
				return false;
			}
			
			//ENTER
			if (evnt.keyCode == 13)
			{
				gs_getUser();
			}
			if(evnt.keyCode == 38 || evnt.keyCode == 40)
			{
			}
			else //除 上下
			{
				var text = encodeURIComponent($(active).val());

				if(pars.ajaxRequestUrl.active != ""){
					$.ajax({
						   type: pars.ajaxRequestType,
						   url: pars.ajaxRequestUrl.active,
						   data: pars.ajaxRequestKey.activetext + '=' + text,
						   dataType: "json",
						   success: function(json){
						     gs_getActiveData(json);
						   }
					});
				}else {
					gs_getActiveData(pars.getStaticData.activeData(text));
				}
			}
		
		}

		//active blur
		function gs_inputOnblur(thisobj)
		{
			if($(active) && $(active).val() != "")
			{
				gs_getUser();
				
				$(active).val('');
		
				$(active).blur();
			}
			//重新设置top frame 式样 目前无变化
			//$(topFrame).addClass('it1');
			
			$(suggest).css('display','none');
			$(emptysuggest).css('display','none');
			
		}

		// 取得 首字母输入数据并显示active
		function gs_getActiveData(serverdata)
		{
			activedata = serverdata;
			
			if(activedata.length == 0)
			{
				$(suggest).css('display','none');
				$(emptysuggest).css('display','block');
				
				if($(active).val() == "")
				{
					$(emptysuggest).text(pars.message.active2);
				}
				else
				{
					//if (fs_mode == 1)
					//{
					//	$("emptysuggest").style.display = "none";
					//}
					//else
					//{
						$(emptysuggest).text(pars.message.active3);
					//}
				}
				return;
			}
			
			//刷新 suggest 数据
			$(suggest).empty();
			for(var i=0 ; i<activedata.length ; i++)
			{
				suggestitems[i] = $('<div/>').appendTo(suggest).addClass('sgt_of').css('width','200px').text(activedata[i].name).mouseover(function(){gs_suggestOnmouseover(this);}).mousedown(function(){gs_suggestOnmousedown(this);});
			}

			$(suggest).css('display','block');
			$(emptysuggest).css('display','none');
			
			if($(suggestitems[0]) != null && $(suggest).css('display') == 'block')
			{
				$(suggestitems[0]).removeClass();
				$(suggestitems[0]).addClass('sgt_on');
			}
			
		}
		
		//取得 下拉框中数据
		function gs_getPopData()
		{
//			if(($(groupspan).size() > 0) && ($(groupInput).html() == ""))
//			{
				//取得 group data
				//修改 在页面初始化时 初始化Group数据， V0.2 alzhang
//				if(pars.ajaxRequestUrl.group != ""){
//					$.ajax({
//						   type: pars.ajaxRequestType,
//						   url: pars.ajaxRequestUrl.group,
//						   dataType: "json",
//						   success: function(json){
//						   		gs_getGroupData(json);
//						   		if(null == selectedGroupId || '' == selectedGroupId){
//						   			selectedGroupId = $(groupInput)[0].options(0).value;
//						   		}
//						   		gs_iniItemDiv();
//						   }
//					});
//				}else {
//					gs_getGroupData(pars.getStaticData.groupData());
//			   		if(null == selectedGroupId || '' == selectedGroupId){
//			   			selectedGroupId = $(groupInput)[0].options(0).value;
//			   		}
//					gs_iniItemDiv();
//				}
				
//			}else {
				gs_iniItemDiv();// group 数据只在初始化时请求，得到后 不再请求
//			}
		}

		//初始化 下拉框div
		function gs_iniItemDiv(){
			if($(suggest))
			{
				$(suggest).css('display','none');
			}
		
			if($(popMenu).css('display') == 'block')
			{
				$(popMenu).css('display','none');
				$(popImg).attr('src',pars.img.downOut).mouseover(function(){this.src=pars.img.downOver}).mouseout(function(){this.src=pars.img.downOut});
			}
			else
			{
				gs_getItemData();
			}
		}
		
		var tindex = 0;
		// 初始化 每个item 信息
		function  gs_getItemData()
		{
			
			var count = 0;
			var len = itemsdata.length;
			for(var i=0; i<len ; i++)
			{
				if($(itemcheckbox[i])[0].checked)
				{
					count++;
				}
			}
			var confirmret = 0;
			//if(count==0 || ((count > 0) && (confirmret = confirm(pars.message.group))))
			//{
				//取得 item data
				//var selectedgroupid = $(groupInput).val();
				//if(null == selectedgroupid || '' == selectedgroupid){
				//	selectedgroupid = $(groupInput)[0].options(0).value;
				//}
				if(pars.ajaxRequestUrl.item != ""){
					$.ajax({
						   type: pars.ajaxRequestType,
						   url: pars.ajaxRequestUrl.item,
						   data: pars.ajaxRequestKey.groupid + '=' + $(groupInput).val(),
						   dataType: "json",
						   success: function(json){
								gs_getItems(json);
						   }
					});
				}else {
					gs_getItems(pars.getStaticData.itemData($(groupInput).val()));
				}
				
				$(popMenu).css('display','block');
				$(popImg).attr('src',pars.img.upOut).mouseover(function(){this.src=pars.img.upOver}).mouseout(function(){this.src=pars.img.upOut});

				if($(groupInput).html() != "")
				{
					tindex = $(groupInput)[0].selectedindex;
				}
			//}
			//else if(!confirmret) //如果是取消
			//{
			//	if($(groupInput).html() != "")
			//	{
			//		$(groupInput)[0].selectedIndex = tindex;
			//	}
			//}
		}

		//取得 items 数据
		function gs_getItems(serverdata)
		{
			
			$(allitems).empty();
			
			itemsdata = serverdata;
			
			var len = Math.ceil(itemsdata.length/3)*3;
			for(var i=0 ; i<len ; i++)
			{	
				var tempItemDiv;
				if(i%3 == 0)
				{
					tempItemDiv = $('<div/>').addClass('sgt_of').css('width','300px');
				}
				if(itemsdata[i])
				{
					var titemdiv = $('<div/>').appendTo(tempItemDiv).addClass('l').css('width','100px').attr('title',itemsdata[i].name);
					itemcheckbox[i] = $('<input type="checkbox"></input>').appendTo(titemdiv).click(function(){gs_countcheck(this);});
					$(data).each(function(){//V 0.2 alzhang, 勾选已经选择的item
						  if($(this).attr('id') == itemsdata[i].id){
							  itemcheckbox[i].attr('checked','true');
							  return false;
						  }
					});
					
					$(titemdiv).append(itemsdata[i].name);
				}
				if(i%3 == 2)
				{
					$('<div/>').appendTo(tempItemDiv).addClass('c');
					$(tempItemDiv).appendTo(allitems);
				}
			}
			
			//alzhang
			//选择 全部
			//if(arr.length > fs_maxcount)
			//{
			//	$(fs_selall).disabled = true;
			//}
			//else
			//{
			//	$(fs_selall).disabled = false;
			//}
			
		}

		//选择 check 总数
		function gs_countcheck(obj)
		{   //alert("gs_countcheck");
			var count = 0;
			var len = itemcheckbox.length;
			for(var i=0; i<len ; i++)
			{
				if($(itemcheckbox[i])[0].checked)
				{
					count++;
					if( checkmax && count > maxcount)
					{
						obj.checked = false;
						alert(pars.message.max1 + maxcount+ pars.message.max2);
						break;
					}
				}
			}
			return count;
		}

		// 初始  组信息。
		function gs_getGroupData(serverdata)
		{

			var arr = serverdata;
			
			var len = arr.length;
			for(var i=0; i<len; i++)
			{
				$('<option/>').appendTo(groupInput).val(arr[i].id).text(arr[i].name);
			}
			
		}

		//得到选择的item的总数
		function getCheckedCount(){
			var count = 0;
			var len = itemcheckbox.length;
			for(var i=0; i<len ; i++)
			{
				if($(itemcheckbox[i])[0].checked)
				{
					count++;
				}
			}
			return count;
		}
		
		//取消事件
		function gs_cancleitem()
		{
			$(groupInput).val(selectedGroupId);
			gs_popEventCancle();
		}
		
		// 下拉框 选择
		function gs_selectitem()
		{

			if(!pars.canseldiffitem){ //alzhang V0.2
				//if(data.length > 0 && selectedGroupId != $(groupInput).val() && getCheckedCount() > 0){
				//	if(confirm(pars.message.cannotseldiff)){
				//		selectedGroupId = $(groupInput).val();
						data = [];
				//	}else {
				//		return false;
				//	}
				//}
			}

			if(null != pars.groupInfo.divId && '' != pars.groupInfo.divId){
				gs_setGroupInfo();
			}

			var len = itemsdata.length;
			for(var i=0; i<len ; i++)
			{
				if($(itemcheckbox[i])[0].checked)
				{
					var obj = itemsdata[i];
					obj.type = "static";
					if (!gs_checkCount(checkmax))//check max count
					{
						break;
					}
					var isDuplicate = false;
					$(data).each(function(){//V 0.2 alzhang, 不能添加重复的item (active输入还未判断)
						  if($(this).attr('id') == itemsdata[i].id){
							  isDuplicate = true;
							  return false;
						  }
					});
					if(isDuplicate){
						continue;
					}
					data[data.length] = itemsdata[i];
				}
			}

			var tdata = [];
			var j=0;
			len = data.length;
			for(var i=0; i<len ; i++)
			{
				if(data[i].type == "active")
				{
					continue;
				}
				
				tdata[j] = data[i];
				j++;
			}
			data = tdata;
			//fs_dirty = true;
		
			gs_popEventCancle();
		}

		//取消下拉选择
		function gs_popEventCancle(){
			
			$(popMenu).css('display','none');
			$(popImg).attr('src',pars.img.downOut).mouseover(function(){this.src=pars.img.downOver}).mouseout(function(){this.src=pars.img.downOut});
				
			gs_superView();
		}
		
		//mouseover suggest
		function gs_suggestOnmouseover(thisobj)
		{
			$.each(suggestitems, function(i, n){
				if(n.text() == $(thisobj).text()){ //此处判断是否相等 以后需要修改 TODO
				  n.removeClass();
					n.addClass('sgt_on');
				} else {
					n.removeClass();
					n.addClass('sgt_of');
				}		
			});
			
		}

		//mousedown suggest
		function gs_suggestOnmousedown(thisobj)
		{
			if (gs_checkCount(checkmax))
			{
				var num = 0;
				$.each(suggestitems, function(i, n){
					if(n.text() == $(thisobj).text()){ //此处判断是否相等 以后需要修改 TODO
							num =  i;
					}
			  });
				var friendobj = activedata[num];
				friendobj.type = "static";
		
				var activenum = gs_hasActive();
				for(var i = data.length ; i> activenum ; i--)
				{
					data[i] = data[i-1];
				}
				data[activenum] = friendobj;
				//fs_dirty = true;
			}
			
			gs_superView();
		}

		
		//取得 当前选择的数据
		function gs_getUser()
		{
			var hotinfo = gs_getHotNum();
			var hotnum = hotinfo.hotnum;
			var totalnum = hotinfo.totalnum;
			var hasuser = $(suggest).css('display') == "block" && hotnum!=-1 && totalnum>0;
			//if (fs_mode == 1 && !hasuser && $(active).val().length)
			//{
			//	if (fs_checkCount(true))
			//	{
			//		var escape_real_name = $("active").value.replace(/&/g, "&amp;");
			//		escape_real_name = escape_real_name.replace(/</g, "&lt;");
			//		escape_real_name = escape_real_name.replace(/>/g, "&gt;");
			//		var friendobj = {id:"0",real_name:escape_real_name,real_name_unsafe:$("active").value,type:"static"};
		
			//		var activenum = fs_hasActive();
			//		for(var i=fs_data.length ; i> activenum ; i--)
			//		{
			//			fs_data[i] = fs_data[i-1];
			//		}
			//		fs_data[activenum] = friendobj;
			//		fs_dirty = true;
			//	}
				
			//	fs_superView();
			//}
			//else if (hasuser)
			if (hasuser)
			{
				if (gs_checkCount(checkmax))
				{
					var friendobj = activedata[hotnum];
					friendobj.type = "static";
				
					var activenum = gs_hasActive();
					for(var i = data.length ; i> activenum ; i--)
					{
						data[i] = data[i-1];
					}
					data[activenum] = friendobj;
					//fs_dirty = true;
				}
				
				gs_superView();
			}
		}

		//检查选择数目限制
		function gs_checkCount(checkmax)
		{
			var c = 0;
			var len = data.length;
			for(var i=0; i<len ; i++)
			{
				if(data[i].type == "static")
				{
					c++;
				}
			}
			if (c >= maxcount)
			{
				gs_alert = checkmax;
			}
			
			if(checkmax)
				return c < maxcount;
			else
				return true;	
		}

		//得到选中的item index 和 全部数目
		function gs_getHotNum()
		{
			var obj;
			var hotnum = -1;//没有选 就是-1
			for(var i = 0; i < suggestitems.length; i++){
				if(suggestitems[i].hasClass('sgt_on')){
					hotnum = i;
				}
			}
			return {"hotnum":hotnum,"totalnum":suggestitems.length};
			
		}

		//刷新输入框中内容
		function gs_superView()
		{

			$(contentFrame).empty();
			
			var len = data.length;
			for(var i=0; i<len ; i++)
			{
				if(data[i].type == "static")
				{
					if (data[i].id == "0" || data[i].id == 0)
					{
						$('<div/>').appendTo(contentFrame).addClass('fsg_hy').css('background','#fff9d7').text(data[i].name);
					}
					else
					{
						$('<div/>').appendTo(contentFrame).addClass('fsg_hy').text(data[i].name);
					}
				}
				else
				{
					//////////active $ suggest ////////////////////
				  activeDiv = $('<div/>').appendTo(contentFrame).addClass('fsg_id').css('width','50px');
				  active = $('<input type="text" />').appendTo(activeDiv).addClass('fsg_it').attr({value:'',size:'2',Autocomplete:'off',maxlength:'50'}).keydown(function(event){return gs_inputOnkeydown(event);}).keyup(function(event){gs_inputOnkeyup(event);}).blur(function(){gs_inputOnblur(this);}).focus(function(){gs_inputOnfocus(this);});
				  suggest = $('<div/>').appendTo(activeDiv).addClass('fsg_nl').css({display:'none',width:'210px'});
				  emptysuggest = $('<div/>').appendTo(activeDiv).addClass('fsg_nl').css({'padding-left':'7px',background:'#eeeeee',color:'#666666',width:'220px'}).text(pars.message.active1);
			  
					//$(activeDiv).appendTo(contentFrame);
				}
			}	
		
			////////////////////////////////////////////////当输入项多于1行时，重新设置输入框的高
			var childs = $(contentFrame).contents();
			var mintop = 1000000;
			var maxbottom = 0;
			var len = childs.length;
			
			$.each( childs, function(i, n){
			  if($(n).hasClass('fsg_hy') || $(n).hasClass('fsg_id'))
				{
					//var pos = getpos(childs[i]);
					if($(n).offset().top <  mintop)
					{
						mintop = $(n).offset().top;
					}
					if( ($(n).offset().top + $(n).height()) > maxbottom)
					{
						maxbottom = $(n).offset().top + $(n).height();
					}
				}
			});

			var height = maxbottom-mintop;
			height = height < 23 ? 23 : height;
			
			$(contentFrame).css('height', height + "px");
			////////////////////////////////////////////////////////////////////
			
			gs_activeFocus(); //操作后刷新 active
		
		// 选择数目限制
			if (gs_alert)
			{
				gs_alert = false;
				alert(pars.message.max1 + maxcount + pars.message.max2);
			}
			
			//刷新方法，未实现
			//if ('function' == typeof(fs_refresh))
			//{
			//	fs_refresh();
			//}
		}

		function gs_activeFocus()
		{
			for(var i=0 ; i<5 ; i++)
			{
				if($(active))
				{
					$(active).focus();
				}
			}
		}
	
				//得到data长度 按字
		function b_strlen(fData)
		{
			var intLength=0;
			for (var i=0;i<fData.length;i++)
			{
				if ((fData.charCodeAt(i) < 0) || (fData.charCodeAt(i) > 255))
					intLength=intLength+2;
				else
					intLength=intLength+1;   
			}
			return intLength;
		}

		// data 中 是否有type为active的数据
		function gs_hasActive()
		{
			var len = data.length;
			for(var i=0; i<len ; i++)
			{
				if(data[i].type == "active")
				{
					return i;
				}
			}
			return -1;
		}

		//取得结果
		function getdata()
		{
			var v_tags = "";
			for(var i=0; i<data.length ; i++)
			{
				if(data[i].type == "static")
				{
					v_tags += data[i].id+",";
				}
			}
			if (v_tags == "")
			{
				alert(pars.message.result);
				return;
			}
			return v_tags;
		}

	//返回取得结果函数
	return getdata;
}