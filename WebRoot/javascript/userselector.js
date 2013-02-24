/*groupselect.js*/

function gs_getGSParameters(){return $.extend(true,{},gs_initParameters);}
var gs_initParameters={'frameId':'','edit':{'need':false,'gid':'','item':''},'canseldiffitem':true,'groupInfo':{'divId':'','gid':'','gname':''},'needactive':false,'checkmax':true,'maxcount':3,'ajaxRequestType':'POST','ajaxRequestUrl':{'group':'','item':'','active':''},'ajaxRequestKey':{'groupid':'id','activetext':'text'},'getStaticData':{'activeData':'','groupData':'','itemData':''},'img':{'downOver':'./images/downover.gif','downOut':'./images/downout.gif','upOver':'./images/upover.gif','upOut':'./images/upout.gif'},'message':{'imgTitle':'选择好友','select':'请选择好友','buttonOK':'确定','buttonCancle':'取消','active1':'请输入好友的姓名','active2':'请输入好友的姓名(支持拼音首字母输入)','active3':'姓名不在好友列表哦，请重新输入','noactive':'请从下拉菜单中选择！','max1':'最多只能选择','max2':'个用户！','result':'请选择好友！'}};function gs_groupselect(pars){var data=[];var activedata=[];var suggestitems=[];var itemsdata=[];var itemcheckbox=[];var checkmax=pars.checkmax;var maxcount=pars.maxcount;var gs_alert=false;var topFrame;var contentFrame;var popEvent;var popImg;var popMenu;var activeDiv;var active;var suggest;var emptysuggest;var groupspan;var groupInput;var allitems;var selectedGroupId;gs_init();function gs_init(){topFrame=$('<div/>').appendTo($('<div/>').appendTo('#'+pars.frameId).addClass('frame l it_s').css('width','300px')).addClass('it1');contentFrame=$('<div/>').appendTo(topFrame).addClass('l').css({cursor:'text',height:'23px',width:'215px'}).click(function(){gs_inputOnclick();});var flowFrame=$('<div/>').appendTo(topFrame).addClass('fsg22');$('<div/>').appendTo(topFrame).addClass('c');popEvent=$('<div/>').appendTo(flowFrame).click(function(){gs_getPopData();});popMenu=$('<div/>').appendTo(flowFrame).addClass('fsg_nr').css('width','310px');popImg=$('<img/>').appendTo(popEvent).addClass('cp').attr(({src:pars.img.downOut,alt:pars.message.imgTitle,title:pars.message.imgTitle})).mouseover(function(){this.src=pars.img.downOver}).mouseout(function(){this.src=pars.img.downOut});var groupMenu=$('<div/>').appendTo(popMenu).addClass('sgt_on').css('width','300px');allitems=$('<div/>').appendTo(popMenu).css({width:'300px',height:'100px',overflow:'scroll','overflow-x':'hidden'});var buttonsMenu=$('<div/>').appendTo(popMenu).addClass('tac p5');$('<div/>').appendTo(groupMenu).addClass('l').text(pars.message.select);groupspan=$('<span/>').appendTo($('<div/>').appendTo(groupMenu).addClass('r').css('padding-right','20px'));$('<div/>').appendTo(groupMenu).addClass('c');groupInput=$('<select></select>').appendTo(groupspan).change(function(){gs_getItemData();});var buttonsDiv=$('<div/>').appendTo(buttonsMenu).addClass('gbs1');$('<input type="button"/>').appendTo(buttonsDiv).attr({value:pars.message.buttonOK,title:pars.message.buttonOK}).addClass('gb1-12').mouseover(function(){this.className='gb2-12'}).mouseout(function(){this.className='gb1-12'}).click(function(){gs_selectitem();});$('<input type="button"/>').appendTo(buttonsDiv).attr({value:pars.message.buttonCancle,title:pars.message.buttonCancle}).addClass('gb1-12').mouseover(function(){this.className='gb2-12'}).mouseout(function(){this.className='gb1-12'}).click(function(){gs_cancleitem();});$('<div/>').appendTo(buttonsMenu).addClass('c');gs_initGroup();}
function gs_initGroup(){if(pars.ajaxRequestUrl.group!=""){$.ajax({type:pars.ajaxRequestType,url:pars.ajaxRequestUrl.group,dataType:"json",success:function(json){gs_getGroupData(json);gs_initGroupOther();}});}else{gs_getGroupData(pars.getStaticData.groupData());gs_initGroupOther();}}
function gs_initGroupOther(){if(pars.edit.need){$(groupInput).val(pars.edit.gid);$(pars.edit.item).each(function(){$(this).attr('type','static');data[data.length]=this;});gs_superView();}
if(null!=pars.groupInfo.divId&&''!=pars.groupInfo.divId){gs_setGroupInfo();}}
function gs_setGroupInfo(){$('#'+pars.groupInfo.divId).attr(pars.groupInfo.gid,$(groupInput).val()).attr(pars.groupInfo.gname,$(groupInput).find(':selected').text()).text($(groupInput).find(':selected').text());}
function gs_inputOnclick()
{$(popMenu).css('display','none');if(gs_hasActive(data)!=-1)
{if($(active).val()!=""&&$(suggest).css('display','none'))
{$(active).val('');}
$(active).focus();}
else
{data[data.length]={type:"active"};gs_superView();}}
function gs_inputOnfocus(thisobj)
{if(thisobj.value=="")
{$(emptysuggest).css('display','block');if(pars.needactive){$(emptysuggest).text(pars.message.active1);}else{$(emptysuggest).text(pars.message.noactive);}}}
function gs_inputOnkeydown(evnt)
{$(active).css('width',b_strlen($(active).val())*6+20+"px");if(evnt.keyCode==13)
{return false;}
var activenum=gs_hasActive();if(evnt.keyCode==8&&data[activenum-1]&&$(active).val()=="")
{var tdata=[];var j=0;var len=data.length;for(var i=0;i<len;i++)
{if(activenum-1==i)
{continue;}
tdata[j]=data[i];j++;}
data=tdata;gs_superView();return false;}
if(evnt.keyCode==37&&data[activenum-1]&&$(active).val()=="")
{return;}
if(evnt.keyCode==39&&data[activenum+1]&&$(active).val()=="")
{return;}
if(evnt.keyCode==40)
{var hotinfo=gs_getHotNum();var hotnum=hotinfo.hotnum;var num=hotinfo.totalnum;if($(suggestitems[0])!=null&&$(suggest).css('display')=="block")
{if(hotnum==-1)
{$(suggestitems[0]).removeClass();$(suggestitems[0]).addClass('sgt_on');}
else
{var nextnum=hotnum==num-1?0:hotnum+1;$(suggestitems[hotnum]).removeClass();$(suggestitems[hotnum]).addClass("sgt_of");$(suggestitems[nextnum]).removeClass();$(suggestitems[nextnum]).addClass("sgt_on");}
return false;}}
if(evnt.keyCode==38)
{var hotinfo=gs_getHotNum();var hotnum=hotinfo.hotnum;var num=hotinfo.totalnum;if($(suggestitems[0])!=null&&$(suggest).css('display')=="block")
{if(hotnum==-1)
{$(suggestitems[num-1]).removeClass();$(suggestitems[num-1]).addClass('sgt_on');}
else
{var prevnum=hotnum==0?num-1:hotnum-1;$(suggestitems[hotnum]).removeClass();$(suggestitems[hotnum]).addClass("sgt_of");$(suggestitems[prevnum]).removeClass();$(suggestitems[prevnum]).addClass("sgt_on");}}}
if(!pars.needactive){return false;}}
function gs_inputOnkeyup(evnt)
{if(!pars.needactive){return false;}
if(evnt.keyCode==13)
{gs_getUser();}
if(evnt.keyCode==38||evnt.keyCode==40)
{}
else
{var text=encodeURIComponent($(active).val());if(pars.ajaxRequestUrl.active!=""){$.ajax({type:pars.ajaxRequestType,url:pars.ajaxRequestUrl.active,data:pars.ajaxRequestKey.activetext+'='+text,dataType:"json",success:function(json){gs_getActiveData(json);}});}else{gs_getActiveData(pars.getStaticData.activeData(text));}}}
function gs_inputOnblur(thisobj)
{if($(active)&&$(active).val()!="")
{gs_getUser();$(active).val('');$(active).blur();}
$(suggest).css('display','none');$(emptysuggest).css('display','none');}
function gs_getActiveData(serverdata)
{activedata=serverdata;if(activedata.length==0)
{$(suggest).css('display','none');$(emptysuggest).css('display','block');if($(active).val()=="")
{$(emptysuggest).text(pars.message.active2);}
else
{$(emptysuggest).text(pars.message.active3);}
return;}
$(suggest).empty();for(var i=0;i<activedata.length;i++)
{suggestitems[i]=$('<div/>').appendTo(suggest).addClass('sgt_of').css('width','200px').text(activedata[i].name).mouseover(function(){gs_suggestOnmouseover(this);}).mousedown(function(){gs_suggestOnmousedown(this);});}
$(suggest).css('display','block');$(emptysuggest).css('display','none');if($(suggestitems[0])!=null&&$(suggest).css('display')=='block')
{$(suggestitems[0]).removeClass();$(suggestitems[0]).addClass('sgt_on');}}
function gs_getPopData()
{gs_iniItemDiv();}
function gs_iniItemDiv(){if($(suggest))
{$(suggest).css('display','none');}
if($(popMenu).css('display')=='block')
{$(popMenu).css('display','none');$(popImg).attr('src',pars.img.downOut).mouseover(function(){this.src=pars.img.downOver}).mouseout(function(){this.src=pars.img.downOut});}
else
{gs_getItemData();}}
var tindex=0;function gs_getItemData()
{var count=0;var len=itemsdata.length;for(var i=0;i<len;i++)
{if($(itemcheckbox[i])[0].checked)
{count++;}}
var confirmret=0;if(pars.ajaxRequestUrl.item!=""){$.ajax({type:pars.ajaxRequestType,url:pars.ajaxRequestUrl.item,data:pars.ajaxRequestKey.groupid+'='+$(groupInput).val(),dataType:"json",success:function(json){gs_getItems(json);}});}else{gs_getItems(pars.getStaticData.itemData($(groupInput).val()));}
$(popMenu).css('display','block');$(popImg).attr('src',pars.img.upOut).mouseover(function(){this.src=pars.img.upOver}).mouseout(function(){this.src=pars.img.upOut});if($(groupInput).html()!="")
{tindex=$(groupInput)[0].selectedindex;}}
function gs_getItems(serverdata)
{$(allitems).empty();itemsdata=serverdata;var len=Math.ceil(itemsdata.length/3)*3;for(var i=0;i<len;i++)
{var tempItemDiv;if(i%3==0)
{tempItemDiv=$('<div/>').addClass('sgt_of').css('width','300px');}
if(itemsdata[i])
{var titemdiv=$('<div/>').appendTo(tempItemDiv).addClass('l').css('width','100px').attr('title',itemsdata[i].name);itemcheckbox[i]=$('<input type="checkbox"></input>').appendTo(titemdiv).click(function(){gs_countcheck(this);});$(data).each(function(){if($(this).attr('id')==itemsdata[i].id){itemcheckbox[i].attr('checked','true');return false;}});$(titemdiv).append(itemsdata[i].name);}
if(i%3==2)
{$('<div/>').appendTo(tempItemDiv).addClass('c');$(tempItemDiv).appendTo(allitems);}}}
function gs_countcheck(obj)
{var count=0;var len=itemcheckbox.length;for(var i=0;i<len;i++)
{if($(itemcheckbox[i])[0].checked)
{count++;if(checkmax&&count>maxcount)
{obj.checked=false;alert(pars.message.max1+maxcount+pars.message.max2);break;}}}
return count;}
function gs_getGroupData(serverdata)
{var arr=serverdata;var len=arr.length;for(var i=0;i<len;i++)
{$('<option/>').appendTo(groupInput).val(arr[i].id).text(arr[i].name);}}
function getCheckedCount(){var count=0;var len=itemcheckbox.length;for(var i=0;i<len;i++)
{if($(itemcheckbox[i])[0].checked)
{count++;}}
return count;}
function gs_cancleitem()
{$(groupInput).val(selectedGroupId);gs_popEventCancle();}
function gs_selectitem()
{if(!pars.canseldiffitem){data=[];}
if(null!=pars.groupInfo.divId&&''!=pars.groupInfo.divId){gs_setGroupInfo();}
var len=itemsdata.length;for(var i=0;i<len;i++)
{if($(itemcheckbox[i])[0].checked)
{var obj=itemsdata[i];obj.type="static";if(!gs_checkCount(checkmax))
{break;}
var isDuplicate=false;$(data).each(function(){if($(this).attr('id')==itemsdata[i].id){isDuplicate=true;return false;}});if(isDuplicate){continue;}
data[data.length]=itemsdata[i];}}
var tdata=[];var j=0;len=data.length;for(var i=0;i<len;i++)
{if(data[i].type=="active")
{continue;}
tdata[j]=data[i];j++;}
data=tdata;gs_popEventCancle();}
function gs_popEventCancle(){$(popMenu).css('display','none');$(popImg).attr('src',pars.img.downOut).mouseover(function(){this.src=pars.img.downOver}).mouseout(function(){this.src=pars.img.downOut});gs_superView();}
function gs_suggestOnmouseover(thisobj)
{$.each(suggestitems,function(i,n){if(n.text()==$(thisobj).text()){n.removeClass();n.addClass('sgt_on');}else{n.removeClass();n.addClass('sgt_of');}});}
function gs_suggestOnmousedown(thisobj)
{if(gs_checkCount(checkmax))
{var num=0;$.each(suggestitems,function(i,n){if(n.text()==$(thisobj).text()){num=i;}});var friendobj=activedata[num];friendobj.type="static";var activenum=gs_hasActive();for(var i=data.length;i>activenum;i--)
{data[i]=data[i-1];}
data[activenum]=friendobj;}
gs_superView();}
function gs_getUser()
{var hotinfo=gs_getHotNum();var hotnum=hotinfo.hotnum;var totalnum=hotinfo.totalnum;var hasuser=$(suggest).css('display')=="block"&&hotnum!=-1&&totalnum>0;if(hasuser)
{if(gs_checkCount(checkmax))
{var friendobj=activedata[hotnum];friendobj.type="static";var activenum=gs_hasActive();for(var i=data.length;i>activenum;i--)
{data[i]=data[i-1];}
data[activenum]=friendobj;}
gs_superView();}}
function gs_checkCount(checkmax)
{var c=0;var len=data.length;for(var i=0;i<len;i++)
{if(data[i].type=="static")
{c++;}}
if(c>=maxcount)
{gs_alert=checkmax;}
if(checkmax)
return c<maxcount;else
return true;}
function gs_getHotNum()
{var obj;var hotnum=-1;for(var i=0;i<suggestitems.length;i++){if(suggestitems[i].hasClass('sgt_on')){hotnum=i;}}
return{"hotnum":hotnum,"totalnum":suggestitems.length};}
function gs_superView()
{$(contentFrame).empty();var len=data.length;for(var i=0;i<len;i++)
{if(data[i].type=="static")
{if(data[i].id=="0"||data[i].id==0)
{$('<div/>').appendTo(contentFrame).addClass('fsg_hy').css('background','#fff9d7').text(data[i].name);}
else
{$('<div/>').appendTo(contentFrame).addClass('fsg_hy').text(data[i].name);}}
else
{activeDiv=$('<div/>').appendTo(contentFrame).addClass('fsg_id').css('width','50px');active=$('<input type="text" />').appendTo(activeDiv).addClass('fsg_it').attr({value:'',size:'2',Autocomplete:'off',maxlength:'50'}).keydown(function(event){return gs_inputOnkeydown(event);}).keyup(function(event){gs_inputOnkeyup(event);}).blur(function(){gs_inputOnblur(this);}).focus(function(){gs_inputOnfocus(this);});suggest=$('<div/>').appendTo(activeDiv).addClass('fsg_nl').css({display:'none',width:'210px'});emptysuggest=$('<div/>').appendTo(activeDiv).addClass('fsg_nl').css({'padding-left':'7px',background:'#eeeeee',color:'#666666',width:'220px'}).text(pars.message.active1);}}
var childs=$(contentFrame).contents();var mintop=1000000;var maxbottom=0;var len=childs.length;$.each(childs,function(i,n){if($(n).hasClass('fsg_hy')||$(n).hasClass('fsg_id'))
{if($(n).offset().top<mintop)
{mintop=$(n).offset().top;}
if(($(n).offset().top+$(n).height())>maxbottom)
{maxbottom=$(n).offset().top+$(n).height();}}});var height=maxbottom-mintop;height=height<23?23:height;$(contentFrame).css('height',height+"px");gs_activeFocus();if(gs_alert)
{gs_alert=false;alert(pars.message.max1+maxcount+pars.message.max2);}}
function gs_activeFocus()
{for(var i=0;i<5;i++)
{if($(active))
{$(active).focus();}}}
function b_strlen(fData)
{var intLength=0;for(var i=0;i<fData.length;i++)
{if((fData.charCodeAt(i)<0)||(fData.charCodeAt(i)>255))
intLength=intLength+2;else
intLength=intLength+1;}
return intLength;}
function gs_hasActive()
{var len=data.length;for(var i=0;i<len;i++)
{if(data[i].type=="active")
{return i;}}
return-1;}
function getdata()
{var v_tags="";for(var i=0;i<data.length;i++)
{if(data[i].type=="static")
{v_tags+=data[i].id+",";}}
if(v_tags=="")
{alert(pars.message.result);return;}
return v_tags;}
return getdata;}
