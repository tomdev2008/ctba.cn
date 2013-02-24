/*_cookie.js*/

var cookieUtils={getCookieVal:function(offset){var endstr=document.cookie.indexOf(";",offset);if(endstr==-1){endstr=document.cookie.length;}
return unescape(document.cookie.substring(offset,endstr));},setCookie:function(name,value){var expdate=new Date();var argv=cookieUtils.setCookie.arguments;var argc=cookieUtils.setCookie.arguments.length;var expires=(argc>2)?argv[2]:null;var path="/";var domain="ctba.cn";var secure=(argc>5)?argv[5]:false;if(expires!=null){expdate.setTime(expdate.getTime()+(expires*1000));}
document.cookie=name+"="+escape(value)+((expires==null)?"":("; expires="+expdate.toGMTString()))+((path==null)?"":("; path="+path))+((domain==null)?"":("; domain="+domain))+((secure==true)?"; secure":"");},delCookie:function(name){var exp=new Date();exp.setTime(exp.getTime()-1);var cval=GetCookie(name);document.cookie=name+"="+cval+"; expires="+exp.toGMTString();},getCookie:function(name){var arg=name+"=";var alen=arg.length;var clen=document.cookie.length;var i=0;while(i<clen){var j=i+alen;if(document.cookie.substring(i,j)==arg){return cookieUtils.getCookieVal(j);}
i=document.cookie.indexOf(" ",i)+1;if(i==0){break;}}
return null;}}
/*_modalMessageUtils.js*/

DHTML_modalMessage=function()
{var url;var htmlOfModalMessage;var divs_transparentDiv;var divs_content;var iframe;var layoutCss;var width;var height;var existingBodyOverFlowStyle;var dynContentObj;var cssClassOfMessageBox;var shadowDivVisible;var shadowOffset;var MSIE;this.url='';this.htmlOfModalMessage='';this.layoutCss='modal-message.css';this.height=200;this.width=400;this.cssClassOfMessageBox=false;this.shadowDivVisible=true;this.shadowOffset=5;this.MSIE=false;if(navigator.userAgent.indexOf('MSIE')>=0)this.MSIE=true;}
DHTML_modalMessage.prototype={setSource:function(urlOfSource)
{this.url=urlOfSource;},setHtmlContent:function(newHtmlContent)
{this.htmlOfModalMessage=newHtmlContent;},setSize:function(width,height)
{if(width)this.width=width;if(height)this.height=height;},setCssClassMessageBox:function(newCssClass)
{this.cssClassOfMessageBox=newCssClass;if(this.divs_content){if(this.cssClassOfMessageBox)
this.divs_content.className=this.cssClassOfMessageBox;else
this.divs_content.className='modalDialog_contentDiv';}},setShadowOffset:function(newShadowOffset)
{this.shadowOffset=newShadowOffset},display:function()
{if(!this.divs_transparentDiv){this.__createDivs();}
this.divs_transparentDiv.style.display='block';this.divs_content.style.display='block';this.divs_shadow.style.display='block';if(this.MSIE)this.iframe.style.display='block';this.__resizeDivs();window.refToThisModalBoxObj=this;setTimeout('window.refToThisModalBoxObj.__resizeDivs()',150);this.__insertContent();},setShadowDivVisible:function(visible)
{this.shadowDivVisible=visible;},close:function()
{this.divs_transparentDiv.style.display='none';this.divs_content.style.display='none';this.divs_shadow.style.display='none';if(this.MSIE)this.iframe.style.display='none';},addEvent:function(whichObject,eventType,functionName,suffix)
{if(!suffix)suffix='';if(whichObject.attachEvent){whichObject['e'+eventType+functionName+suffix]=functionName;whichObject[eventType+functionName+suffix]=function(){whichObject['e'+eventType+functionName+suffix](window.event);}
whichObject.attachEvent('on'+eventType,whichObject[eventType+functionName+suffix]);}else
whichObject.addEventListener(eventType,functionName,false);},__createDivs:function()
{this.divs_transparentDiv=document.createElement('DIV');this.divs_transparentDiv.className='modalDialog_transparentDivs';this.divs_transparentDiv.style.left='0px';this.divs_transparentDiv.style.top='0px';document.body.appendChild(this.divs_transparentDiv);this.divs_content=document.createElement('DIV');this.divs_content.className='modalDialog_contentDiv';this.divs_content.id='DHTMLSuite_modalBox_contentDiv';this.divs_content.style.zIndex=100000;if(this.MSIE){this.iframe=document.createElement('<IFRAME src="about:blank" frameborder=0>');this.iframe.style.zIndex=90000;this.iframe.style.position='absolute';document.body.appendChild(this.iframe);}
document.body.appendChild(this.divs_content);this.divs_shadow=document.createElement('DIV');this.divs_shadow.className='modalDialog_contentDiv_shadow';this.divs_shadow.style.zIndex=95000;document.body.appendChild(this.divs_shadow);window.refToModMessage=this;this.addEvent(window,'scroll',function(e){window.refToModMessage.__repositionTransparentDiv()});this.addEvent(window,'resize',function(e){window.refToModMessage.__repositionTransparentDiv()});},__getBrowserSize:function()
{var bodyWidth=document.documentElement.clientWidth;var bodyHeight=document.documentElement.clientHeight;var bodyWidth,bodyHeight;if(self.innerHeight){bodyWidth=self.innerWidth;bodyHeight=self.innerHeight;}else if(document.documentElement&&document.documentElement.clientHeight){bodyWidth=document.documentElement.clientWidth;bodyHeight=document.documentElement.clientHeight;}else if(document.body){bodyWidth=document.body.clientWidth;bodyHeight=document.body.clientHeight;}
return[bodyWidth,bodyHeight];},__resizeDivs:function()
{var topOffset=Math.max(document.body.scrollTop,document.documentElement.scrollTop);if(this.cssClassOfMessageBox)
this.divs_content.className=this.cssClassOfMessageBox;else
this.divs_content.className='modalDialog_contentDiv';if(!this.divs_transparentDiv)return;var st=Math.max(document.body.scrollTop,document.documentElement.scrollTop);var sl=Math.max(document.body.scrollLeft,document.documentElement.scrollLeft);window.scrollTo(sl,st);setTimeout('window.scrollTo('+sl+','+st+');',10);this.__repositionTransparentDiv();var brSize=this.__getBrowserSize();var bodyWidth=brSize[0];var bodyHeight=brSize[1];this.divs_content.style.width=this.width+'px';this.divs_content.style.height=this.height+'px';var tmpWidth=this.divs_content.offsetWidth;var tmpHeight=this.divs_content.offsetHeight;this.divs_content.style.left=Math.ceil((bodyWidth-tmpWidth)/2)+'px';;this.divs_content.style.top=(Math.ceil((bodyHeight-tmpHeight)/2)+topOffset)+'px';if(this.MSIE){this.iframe.style.left=this.divs_content.style.left;this.iframe.style.top=this.divs_content.style.top;this.iframe.style.width=this.divs_content.style.width;this.iframe.style.height=this.divs_content.style.height;}
this.divs_shadow.style.left=(this.divs_content.style.left.replace('px','')/1+this.shadowOffset)+'px';this.divs_shadow.style.top=(this.divs_content.style.top.replace('px','')/1+this.shadowOffset)+'px';this.divs_shadow.style.height=tmpHeight+'px';this.divs_shadow.style.width=tmpWidth+'px';if(!this.shadowDivVisible)this.divs_shadow.style.display='none';},__repositionTransparentDiv:function()
{this.divs_transparentDiv.style.top=Math.max(document.body.scrollTop,document.documentElement.scrollTop)+'px';this.divs_transparentDiv.style.left=Math.max(document.body.scrollLeft,document.documentElement.scrollLeft)+'px';var brSize=this.__getBrowserSize();var bodyWidth=brSize[0];var bodyHeight=brSize[1];this.divs_transparentDiv.style.width=bodyWidth+'px';this.divs_transparentDiv.style.height=bodyHeight+'px';},__insertContent:function()
{if(this.url){ajax_loadContent('DHTMLSuite_modalBox_contentDiv',this.url);}else{this.divs_content.innerHTML=this.htmlOfModalMessage;}}}
/*_utils.js*/

var commonUtils={previewImg:function(fileCompId,prevComId){var fileValue=document.getElementById(fileCompId).value;var fileext=fileValue.substring(fileValue.lastIndexOf("."),fileValue.length);fileext=fileext.toLowerCase();if((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp')){document.getElementById(fileCompId).focus();}else{var img=document.createElement("img");img.setAttribute("src","file://localhost/"+fileValue);img.setAttribute("width","200");img.setAttribute("id","img_preview");document.getElementById(prevComId).innerHTML="";document.getElementById(prevComId).appendChild(img);}},message:function(str){J2bbCommon.showMessage(str);},validateImg:function(fileCompId,maxFileSize){var fileValue=document.getElementById(fileCompId).value;var fileext=fileValue.substring(fileValue.lastIndexOf("."),fileValue.length);fileext=fileext.toLowerCase();if((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp')){document.getElementById(fileCompId).focus();}else{var img=document.createElement("img");img.setAttribute("src","file://localhost/"+fileValue);img.setAttribute("width","200");}}}
/*_common.js*/

J2bbCommon=function(){}
J2bbCommon.confirmWin="<div class='messageTitle'>确认</div> <div id='confirmWinMsg' ></div><input type='button' id='confirmButton' value=' 确  定 ' class='input_btn'  style='margin-bottom:10px;'/> <input type='button' value=' 取  消 ' class='input_btn' onclick='J2bbCommon.closeMessage();' style='margin-bottom:10px;'/>";J2bbCommon.messageWin="<div class='messageTitle'>提示</div> <div id='messageWinMsg' ></div><input type='button' value=' 确  定 ' class='input_btn' onclick='J2bbCommon.closeMessage();' style='margin-bottom:10px;'/> ";J2bbCommon.loginDivStr="";J2bbCommon.inviteDivStr="";J2bbCommon.messageObj=new DHTML_modalMessage();J2bbCommon.messageObj.setShadowOffset(0);J2bbCommon.version='3.5';J2bbCommon.isIE=false;if(navigator.userAgent.indexOf('MSIE')>=0)J2bbCommon.isIE=true;J2bbCommon.confirmURL=function(str,url2Go){J2bbCommon.displayMessage(J2bbCommon.confirmWin,"modalDialog_confirm");$("#confirmWinMsg").html(str);$("#confirmButton").click(function(){J2bbCommon.closeMessage();location.href=url2Go;});}
J2bbCommon.confirmAction=function(str,actionFunc){J2bbCommon.displayMessage(J2bbCommon.confirmWin,"modalDialog_confirm");$("#confirmWinMsg").html(str);$("#confirmButton").click(function(){J2bbCommon.closeMessage();eval(actionFunc);});}
J2bbCommon.showMessage=function(str){J2bbCommon.displayMessage(J2bbCommon.messageWin,"modalDialog_message");$("#messageWinMsg").html(str);}
J2bbCommon.displayMessage=function(messageContent,cssClass){J2bbCommon.messageObj.setHtmlContent(messageContent);J2bbCommon.messageObj.setSize(330,150);J2bbCommon.messageObj.setCssClassMessageBox(cssClass);J2bbCommon.messageObj.setSource(false);J2bbCommon.messageObj.setShadowDivVisible(false);J2bbCommon.messageObj.display();}
J2bbCommon.displaySizedMessage=function(messageContent,cssClass,width,height){J2bbCommon.messageObj.setHtmlContent(messageContent);J2bbCommon.messageObj.setSize(width,height);J2bbCommon.messageObj.setCssClassMessageBox(cssClass);J2bbCommon.messageObj.setSource(false);J2bbCommon.messageObj.setShadowDivVisible(false);J2bbCommon.messageObj.display();}
J2bbCommon.closeMessage=function(){J2bbCommon.messageObj.close();}
J2bbCommon.changTheme=function(themeName){var themeKey="j2bb_community_them";cookieUtils.setCookie(themeKey,themeName);var themeCookie=GetCookie(themeKey);window.location.reload();}
J2bbCommon.encodeURL=function(src){var reval=src.substring(src.lastIndexOf("/")+1);while(reval.indexOf(".")>0){reval=reval.replace("\.","_DOT_");}
while(reval.indexOf("?")>0){reval=reval.replace("\?","_QUES_");}
while(reval.indexOf("=")>0){reval=reval.replace("=","_EQUAL_");}
while(reval.indexOf("&")>0){reval=reval.replace("&","_AND_");}
reval+="."+"xhtml";return src.substring(0,src.lastIndexOf("/")+1)+reval;}
J2bbCommon.decodeURL=function(src){var reval=src;reval=src.substring(0,src.lastIndexOf("xhtml")-1);reval=reval.replace("_DOT_",".");reval=reval.replace("_QUES_","?");reval=reval.replace("_EQUAL_","=");reval=reval.replace("_AND_","&");return reval;}
J2bbCommon.buildMenu=function(){if($.browser.msie&&$.browser.version==6.0){$('#menu_inner li').hover(function(){$(this).addClass('hover');},function(){$(this).removeClass('hover');});}};J2bbCommon.switchTabs=function(){var currName=$('#tabs li a').attr('name');$('#tabs li').bind('click',function(){var clickName=$('a',this).attr('name');if(clickName!=currName){$('#tabs li').removeClass();$(this).addClass('current');$('#'+clickName).show();$('#'+currName).hide();}
currName=clickName;});};J2bbCommon.showLoginForm=function(){J2bbCommon.loginDivStr=$("#loginDiv").html();$("#loginDiv").html('');J2bbCommon.displayMessage(J2bbCommon.loginDivStr,"modalDialog_login");$("#username").focus();}
J2bbCommon.closeLoginForm=function(){J2bbCommon.closeMessage();$("#loginDiv").html(J2bbCommon.loginDivStr);}
J2bbCommon.showInviteForm=function(){J2bbCommon.inviteDivStr=$("#inviteDiv").html();$("#inviteDiv").html('');J2bbCommon.displayMessage(J2bbCommon.inviteDivStr,"modalDialog_login");}
J2bbCommon.closeInviteForm=function(){J2bbCommon.closeMessage();$("#inviteDiv").html(J2bbCommon.inviteDivStr);}
J2bbCommon.checkUserName=function(){var userName=$("#username").val();var done=false;done=J2bbCommonValidator.isWordsIncludingChinese(userName);if(!done){$("form > #loginError").html("您的用户名不符合要求");$("form >  #loginError").css({color:"#ffffff",background:"red"});$("form >  #loginError").fadeIn("fast");$("#username").focus();$("form >  #loginError").fadeOut(2000);return false;}else{return true;}}
J2bbCommon.checkPass=function(){var userPassword=$("#password").val();var patrn=/^(\w){6,20}$/;if(!patrn.exec(userPassword)){$("form > #loginError").html("您的密码不符合要求");$("form > #loginError").css({color:"#fff",background:"red"});$("form > #loginError").fadeIn("fast");$("#password").focus();$("form > #loginError").fadeOut(2000);return false;}
return true;}
J2bbCommon.checkLoginForm=function(){if(!J2bbCommon.checkUserName()){return false;}
if(!J2bbCommon.checkPass()){return false;}
cookieUtils.setCookie("j2bbCookieURL",location.href);$("#loginButton").val("登录中，请稍候...");$("#loginButton").attr("disabled","disabled");return true;}
J2bbCommon.checkInviteForm=function(){var invite_email=$("#invite_email").val();var invite_content=$("#invite_content").val();var done=false;done=J2bbCommonValidator.isEmail(invite_email);if(!done){$("#inviteError").html("Email不符合要求");$("#inviteError").css({color:"#ffffff",background:"red"});$("#inviteError").fadeIn("fast");$("#invite_email").focus();$("#inviteError").fadeOut(2000);return false;}
$("#inviteButton").val("处理中，请稍候...");$("#inviteButton").attr("disabled","disabled");return true;}
J2bbCommon.getAbsolutePos=function(el){var r={x:el.offsetLeft,y:el.offsetTop};if(el.offsetParent){var tmp=J2bbCommon.getAbsolutePos(el.offsetParent);r.x+=tmp.x;r.y+=tmp.y;}
return r;}
J2bbCommon.removeClass=function(el,className){if(!(el&&el.className)){return;}
var cls=el.className.split(" ");var ar=new Array();for(var i=cls.length;i>0;){if(cls[--i]!=className){ar[ar.length]=cls[i];}}
el.className=ar.join(" ");}
J2bbCommon.addClass=function(el,className){J2bbCommon.removeClass(el,className);el.className+=" "+className;}
J2bbCommon.formInfo=function(objId,msgStr,showPosition){var obj=document.getElementById(objId);var p=J2bbCommon.getAbsolutePos(obj);J2bbCommon.addClass(obj,"validate_input_info");var divInfo=document.getElementById("divFormInfo");if(divInfo!=null){divInfo.parentNode.removeChild(divInfo);}
var divInfoTop;var divInfoLeft;if("bottom"==showPosition){divInfoTop=p.y+obj.offsetHeight+"px";divInfoLeft=p.x+"px";}else{divInfoTop=p.y+"px";divInfoLeft=p.x+obj.offsetWidth+"px";}
divInfo=document.createElement("div");divInfo.id="divFormInfo";divInfo.appendChild(document.createTextNode(msgStr));document.body.appendChild(divInfo);divInfo.className="message_info";divInfo.style.top=divInfoTop;divInfo.style.left=divInfoLeft;divInfo.setAttribute("z-index","1000");divInfo.style.display="block";divInfo.style.position="absolute";}
J2bbCommon.removeFormInfo=function(objId){var obj=document.getElementById(objId);J2bbCommon.removeClass(obj,"validate_input_info");var divInfo=document.getElementById("divFormInfo");if(divInfo!=null){divInfo.style.display="none";}}
J2bbCommon.formError=function(objId,msgStr,showPosition){J2bbCommon.removeformError(objId);var obj=document.getElementById(objId);var p=J2bbCommon.getAbsolutePos(obj);J2bbCommon.addClass(obj,"validate_input_error");var divInfo=document.getElementById("divFormError");if(divInfo!=null){divInfo.parentNode.removeChild(divInfo);}
var divInfoTop;var divInfoLeft;if("bottom"==showPosition){divInfoTop=p.y+obj.offsetHeight+"px";divInfoLeft=p.x+"px";}else{divInfoTop=p.y+"px";divInfoLeft=p.x+obj.offsetWidth+"px";}
divInfo=document.createElement("div");divInfo.id="divFormError";document.body.appendChild(divInfo);divInfo.appendChild(document.createTextNode(msgStr));divInfo.className="message_error";divInfo.style.top=divInfoTop;divInfo.style.left=divInfoLeft;divInfo.setAttribute("z-index","1000");divInfo.style.display="block";divInfo.style.position="absolute";}
J2bbCommon.removeformError=function(objId){var obj=document.getElementById(objId);J2bbCommon.removeClass(obj,"validate_input_error");var divInfo=document.getElementById("divFormError");if(divInfo!=null){divInfo.style.display="none";}}
J2bbCommon.errorWithElement=function(divId,msgStr){var divInfo=document.getElementById(divId);if(divInfo==null){return;}
divInfo.className="message_error";divInfo.style.display="block";divInfo.innerHTML=msgStr;$("#"+divId).fadeOut(2000);}
J2bbCommon.msgDivStr="<div id='slideMessageTitle'><a href='javascript:J2bbCommon.removeSlideMsg();'> <img src='images/icons/closes.gif' align='absmiddle' /></a>&nbsp;&nbsp;</div><div id='slideMessageInfo'><img src='images/comment.gif' align='absmiddle' />&nbsp;</div>";J2bbCommon.slideMsg=function(msgStr){divInfo=document.createElement("div");divInfo.id="slideMessageDiv";document.body.appendChild(divInfo);$("#slideMessageDiv").html(J2bbCommon.msgDivStr);document.getElementById("slideMessageInfo").appendChild(document.createTextNode(msgStr));$("#slideMessageDiv").animate({height:'toggle'},1500);setTimeout("J2bbCommon.hideSlideMsg ();",3000);}
J2bbCommon.hideSlideMsg=function(){divInfo=document.getElementById("slideMessageDiv");if(divInfo==null)return;$("#slideMessageDiv").animate({height:'toggle'},1500);setTimeout("J2bbCommon.removeSlideMsg();",2000);}
J2bbCommon.removeSlideMsg=function(){divInfo=document.getElementById("slideMessageDiv");if(divInfo==null)return;divInfo.parentNode.removeChild(divInfo);}
J2bbCommon.strLen=function(str){return str.replace(/[^\x00-\xff]/g,"**").length;}
var J2bbCommonValidator={isEmail:function(s){if(s=='')return false;var patrn=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;if(!patrn.exec(s)){return false;}
return true;},isMobile:function(s){if(s=='')return false;var patrn=/^(13)[0-9]{9}/;if(!patrn.exec(s)){return false;}
return true;},isDate:function(s){if(s=='')return false;var patrn=/^(19)\d{2}\-\d{1-2}\-\d{1-2}/;if(!patrn.exec(s)){return false;}
return true;},isQQ:function(s){if(s=='')return false;var patrn=/[0-9]{3,12}/;if(!patrn.exec(s)){return false;}
return true;},isWords:function(s){if(s=='')return false;return this.test(s,'^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){2,19}$');},isWordsIncludingChinese:function(s){if(s=='')return false;return this.test(s,'[\u4e00-\u9fa5_a-zA-Z0-9]{1,}');},isAbsUrl:function(s){return this.test(s,'^(news|telnet|nttp|file|http|ftp|https)://[-A-Za-z0-9\\.]+\\/?.*$');},isSize:function(s){return this.test(s,'^[0-9]+(px|%)?$');},isId:function(s){return this.test(s,'^[A-Za-z_]([A-Za-z0-9_])*$');},isEmpty:function(s){var nl,i;if(s.nodeName=='SELECT'&&s.selectedIndex<1)
return true;if(s.type=='checkbox'&&!s.checked)
return true;if(s.type=='radio'){for(i=0,nl=s.form.elements;i<nl.length;i++){if(nl[i].type=="radio"&&nl[i].name==s.name&&nl[i].checked)
return false;}
return true;}
return new RegExp('^\\s*$').test(s.nodeType==1?s.value:s);},isNumber:function(s,d){return!isNaN(s.nodeType==1?s.value:s)&&(!d||!this.test(s,'^-?[0-9]*\\.[0-9]*$'));},test:function(s,p){s=s.nodeType==1?s.value:s;return s==''||new RegExp(p).test(s);}};String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
String.prototype.endWith=function(str){if(str==null||str==""||this.length==0||str.length>this.length)
return false;if(this.substring(this.length-str.length)==str)
return true;else
return false;return true;}
String.prototype.startWith=function(str){if(str==null||str==""||this.length==0||str.length>this.length)
return false;if(this.substr(0,str.length)==str)
return true;else
return false;return true;}
function copyToClip(meintext){if(window.clipboardData){window.clipboardData.setData("Text",meintext);}
else if(window.netscape){try{netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');}catch(e){alert("出于安全的考虑浏览器拒绝了您的操作\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'")}
var clip=Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);if(!clip)return;var trans=Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);if(!trans)return;trans.addDataFlavor('text/unicode');var str=new Object();var len=new Object();var str=Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);var copytext=meintext;str.data=copytext;trans.setTransferData("text/unicode",str,copytext.length*2);var clipid=Components.interfaces.nsIClipboard;if(!clip)return false;clip.setData(trans,null,clipid.kGlobalClipboard);}
return false;}
/*_page.js*/

$(function(){J2bbCommon.buildMenu();J2bbCommon.switchTabs();});var confirmWin="<div class='messageTitle'>确认</div> <div id='confirmWinMsg' ></div><input type='button' id='confirmButton' value=' 确  定 ' class='input_btn'  style='margin-bottom:10px;'/> <input type='button' value=' 取  消 ' class='input_btn' onclick='closeMessage();' style='margin-bottom:10px;'/>";var messageWin="<div class='messageTitle'>提示</div> <div id='messageWinMsg' ></div><input type='button' value=' 确  定 ' class='input_btn' onclick='closeMessage();' style='margin-bottom:10px;'/> ";messageObj=new DHTML_modalMessage();messageObj.setShadowOffset(0);function confirmURL(str,url2Go){displayMessage(confirmWin,"modalDialog_confirm");$("#confirmWinMsg").html(str);$("#confirmButton").click(function(){closeMessage();location.href=url2Go;});}
function confirmAction(str,actionFunc){displayMessage(confirmWin,"modalDialog_confirm");$("#confirmWinMsg").html(str);$("#confirmButton").click(function(){closeMessage();eval(actionFunc);});}
function showMessage(str){displayMessage(messageWin,"modalDialog_message");$("#messageWinMsg").html(str);}
function displayMessage(messageContent,cssClass)
{messageObj.setHtmlContent(messageContent);messageObj.setSize(330,150);messageObj.setCssClassMessageBox(cssClass);messageObj.setSource(false);messageObj.setShadowDivVisible(false);messageObj.display();}
function displaySizedMessage(messageContent,cssClass,width,height)
{messageObj.setHtmlContent(messageContent);messageObj.setSize(width,height);messageObj.setCssClassMessageBox(cssClass);messageObj.setSource(false);messageObj.setShadowDivVisible(false);messageObj.display();}
function displayUrlMessage(url,width,height)
{messageObj.setSource(url);messageObj.setCssClassMessageBox(false);messageObj.setSize(width,height);messageObj.setShadowDivVisible(true);messageObj.display();}
function closeMessage()
{messageObj.close();}
/*_loginForm.js*/

var loginDivStr="";function showLoginForm(){loginDivStr=$("#loginDiv").html();$("#loginDiv").html('');displayStaticMessage(loginDivStr,"modalDialog_login");$("#username").focus();}
function closeLoginForm(){closeMessage();$("#loginDiv").html(loginDivStr);}
function displayStaticMessage(messageContent,cssClass)
{messageObj.setHtmlContent(messageContent);messageObj.setSize(330,150);messageObj.setCssClassMessageBox(cssClass);messageObj.setSource(false);messageObj.setShadowDivVisible(false);messageObj.display();}
function closeMessage()
{messageObj.close();}
function checkUserName(){var userName=$("#username").val();var patrn=/[\u4e00-\u9fa5_a-zA-Z0-9]{2,}/;if(!patrn.exec(userName)){$("form > #loginError").html("您的用户名不符合要求");$("form >  #loginError").css({color:"#ffffff",background:"red"});$("form >  #loginError").fadeIn("fast");$("#username").focus();$("form >  #loginError").fadeOut(2000);return false;}else{return true;}}
function checkPass(){var userPassword=$("#password").val();var patrn=/^(\w){6,20}$/;if(!patrn.exec(userPassword)){$("form > #loginError").html("请输入不少于六位的密码");$("form > #loginError").css({color:"#ffffff",background:"red"});$("form > #loginError").fadeIn("fast");$("#password").focus();$("form > #loginError").fadeOut(2000);return false;}
return true;}
function checkLoginForm(){if(!checkUserName())
return false;if(!checkPass())
return false;cookieUtils.setCookie("j2bbCookieURL",location.href);$("#loginButton").val("登录中，请稍候...");$("#loginButton").attr("disabled","disabled");return true;}
/*_json.js*/

var JSON=function(){var m={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'},s={'boolean':function(x){return String(x);},number:function(x){return isFinite(x)?String(x):'null';},string:function(x){if(/["\\\x00-\x1f]/.test(x)){x=x.replace(/([\x00-\x1f\\"])/g,function(a,b){var c=m[b];if(c){return c;}
c=b.charCodeAt();return'\\u00'+
Math.floor(c/16).toString(16)+
(c%16).toString(16);});}
return'"'+x+'"';},object:function(x){if(x){var a=[],b,f,i,l,v;if(x instanceof Array){a[0]='[';l=x.length;for(i=0;i<l;i+=1){v=x[i];f=s[typeof v];if(f){v=f(v);if(typeof v=='string'){if(b){a[a.length]=',';}
a[a.length]=v;b=true;}}}
a[a.length]=']';}else if(x instanceof Object){a[0]='{';for(i in x){v=x[i];f=s[typeof v];if(f){v=f(v);if(typeof v=='string'){if(b){a[a.length]=',';}
a.push(s.string(i),':',v);b=true;}}}
a[a.length]='}';}else{return;}
return a.join('');}
return'null';}};return{copyright:'(c)2005 JSON.org',license:'http://www.crockford.com/JSON/license.html',stringify:function(v){var f=s[typeof v];if(f){v=f(v);if(typeof v=='string'){return v;}}
return null;},parse:function(text){try{return!(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(text.replace(/"(\\.|[^"\\])*"/g,'')))&&eval('('+text+')');}catch(e){return false;}}};}();
