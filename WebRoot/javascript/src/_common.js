/***
 * common functions & objs
 * @author gladstone
 * @date 2007-12-7
 * @note: this work with jQuery!
 */

J2bbCommon = function(){}
J2bbCommon.confirmWin="<div class='messageTitle'>确认</div>	<div id='confirmWinMsg' ></div><input type='button' id='confirmButton' value=' 确  定 ' class='input_btn'  style='margin-bottom:10px;'/>	<input type='button' value=' 取  消 ' class='input_btn'	onclick='J2bbCommon.closeMessage();' style='margin-bottom:10px;'/>";
J2bbCommon.messageWin="<div class='messageTitle'>提示</div>	<div id='messageWinMsg' ></div><input type='button' value=' 确  定 ' class='input_btn' onclick='J2bbCommon.closeMessage();' style='margin-bottom:10px;'/>	";
J2bbCommon.loginDivStr="";
J2bbCommon.inviteDivStr="";

J2bbCommon.messageObj = new DHTML_modalMessage();
J2bbCommon.messageObj.setShadowOffset(0);
J2bbCommon.version = '3.5';
J2bbCommon.isIE = false;
if(navigator.userAgent.indexOf('MSIE')>=0) J2bbCommon.isIE = true;

/**
 * show the confirm window
 * @param str
 * @param url2Go
 */
J2bbCommon.confirmURL = function (str,url2Go) {
    J2bbCommon.displayMessage(J2bbCommon.confirmWin,"modalDialog_confirm");
    $("#confirmWinMsg").html(str);
    $("#confirmButton").click(function(){
        J2bbCommon.closeMessage();location.href=url2Go;
    });
}

/**
 * show the confirm window
 * @param str
 * @param actionFunc
 */
J2bbCommon.confirmAction = function (str,actionFunc) {
    J2bbCommon.displayMessage(J2bbCommon.confirmWin,"modalDialog_confirm");
    $("#confirmWinMsg").html(str);
    $("#confirmButton").click(function(){
        J2bbCommon.closeMessage();eval(actionFunc);
    });
}
/**
 * show a message
 * @param str
 */
J2bbCommon.showMessage = function(str) {
    J2bbCommon.displayMessage(J2bbCommon.messageWin,"modalDialog_message");
    $("#messageWinMsg").html(str);
}

/**
 * display a model message,
 * use the static message
 * @param messageContent
 * @param cssClass
 */
J2bbCommon.displayMessage = function (messageContent,cssClass) {
    J2bbCommon.messageObj.setHtmlContent(messageContent);
    J2bbCommon.messageObj.setSize(330,150);
    J2bbCommon.messageObj.setCssClassMessageBox(cssClass);
    J2bbCommon.messageObj.setSource(false);
    J2bbCommon.messageObj.setShadowDivVisible(false);
    J2bbCommon.messageObj.display();
}

/**
 * display a sized model message
 * @param messageContent
 * @param cssClass
 * @param width
 * @param height
 */
J2bbCommon.displaySizedMessage = function (messageContent,cssClass,width,height) {
    J2bbCommon.messageObj.setHtmlContent(messageContent);
    J2bbCommon.messageObj.setSize(width,height);
    J2bbCommon.messageObj.setCssClassMessageBox(cssClass);
    J2bbCommon.messageObj.setSource(false);
    J2bbCommon.messageObj.setShadowDivVisible(false);
    J2bbCommon.messageObj.display();
}

/**
 * close the model message
 */
J2bbCommon.closeMessage = function() {
    J2bbCommon.messageObj.close();
}
/**
 * change theme, not in use now
 * @param themeName
 * @deprecated
 */
J2bbCommon.changTheme = function(themeName) {
    var themeKey = "j2bb_community_them";
    cookieUtils.setCookie(themeKey,themeName);
    var themeCookie = GetCookie(themeKey);
    window.location.reload();
}
/**
 * encode url
 * @param src
 */
J2bbCommon.encodeURL = function (src) {
    var reval = src.substring(src.lastIndexOf("/") + 1);
    while(reval.indexOf(".")>0){
        reval = reval.replace("\.", "_DOT_");
    }
    while(reval.indexOf("?")>0){
        reval = reval.replace("\?", "_QUES_");
    }
    while(reval.indexOf("=")>0){
        reval = reval.replace("=", "_EQUAL_");
    }
    while(reval.indexOf("&")>0){
        reval = reval.replace("&", "_AND_");
    }
    reval +="."+ "xhtml";
    return src.substring(0, src.lastIndexOf("/") + 1) + reval;
}
/**
 * decode url
 * @param src
 */
J2bbCommon.decodeURL = function(src) {
    var  reval = src;
    reval = src.substring(0, src.lastIndexOf("xhtml")-1);
    reval = reval.replace("_DOT_", ".");
    reval = reval.replace("_QUES_", "?");
    reval = reval.replace("_EQUAL_", "=");
    reval = reval.replace("_AND_", "&");
    return reval;
}

/* modified by mockee 090909 */
J2bbCommon.buildMenu = function () {
    if($.browser.msie && $.browser.version == 6.0) {
        $('#menu_inner li').hover(
            function () { 
                $(this).addClass('hover');
            },
            function () { 
                $(this).removeClass('hover');
            }
        );
    }
};

J2bbCommon.switchTabs = function () {
    var currName = $('#tabs li a').attr('name');
    $('#tabs li').bind('click', function () {
        var clickName = $('a', this).attr('name');
        if(clickName != currName) {
            $('#tabs li').removeClass();
            $(this).addClass('current');
            $('#' + clickName).show();
            $('#' + currName).hide();
        }
        currName = clickName;
    });
};

/**
     * show a login form
     */
J2bbCommon.showLoginForm = function() {
    J2bbCommon.loginDivStr=$("#loginDiv").html();
    $("#loginDiv").html('');
    J2bbCommon.displayMessage(J2bbCommon.loginDivStr,"modalDialog_login");
    $("#username").focus();
}
/**
     * close the login window
     */
J2bbCommon.closeLoginForm = function (){
    J2bbCommon.closeMessage();
    $("#loginDiv").html(J2bbCommon.loginDivStr);
}

/**
     * show a invite form
     */
J2bbCommon.showInviteForm = function (){
    J2bbCommon.inviteDivStr=$("#inviteDiv").html();
    $("#inviteDiv").html('');
    J2bbCommon.displayMessage(J2bbCommon.inviteDivStr,"modalDialog_login");
}
/**
     * close the invite window
     */
J2bbCommon.closeInviteForm= function (){
    J2bbCommon.closeMessage();
    $("#inviteDiv").html(J2bbCommon.inviteDivStr);
}
/**
     * 校验登录名,包含中文
     */
J2bbCommon.checkUserName=function (){
    var userName=$("#username").val();
    var done = false;
    //if(global_user_auth_key=='email'){
    //	done =J2bbCommonValidator.isEmail(userName);
    //}else{
    done =J2bbCommonValidator.isWordsIncludingChinese(userName);
    //}
    if (!done){
        $("form > #loginError").html("您的用户名不符合要求");
        $("form >  #loginError").css({
            color:"#ffffff",
            background: "red"
        });
        $("form >  #loginError").fadeIn("fast");
        $("#username").focus();
        $("form >  #loginError").fadeOut(2000);
        return false;
    }else{
        return true;
    }
}

/**
     * 校验密码：只能输入6-20个字母、数字、下划线
     */
J2bbCommon.checkPass = function() {
    var userPassword=$("#password").val();
    var patrn=/^(\w){6,20}$/;
    if (!patrn.exec(userPassword)){
        $("form > #loginError").html("您的密码不符合要求");
        $("form > #loginError").css({
            color:"#fff",
            background: "red"
        });
        $("form > #loginError").fadeIn("fast");
        $("#password").focus();
        $("form > #loginError").fadeOut(2000);
        return false;
    }
    return true;
}
/**
     * 校验登录框
     */
J2bbCommon.checkLoginForm = function() {
    if(!J2bbCommon.checkUserName()) {
        return false;
    }
    if(!J2bbCommon.checkPass()) {
        return false;
    }
    cookieUtils.setCookie("j2bbCookieURL", location.href);
    $("#loginButton").val("登录中，请稍候...");
    $("#loginButton").attr("disabled","disabled");
    return true;
}
/**
     * 校验邀请框
     */
J2bbCommon.checkInviteForm=function (){
    var invite_email=$("#invite_email").val();
    var invite_content=$("#invite_content").val();
    var done = false;
    done =J2bbCommonValidator.isEmail(invite_email);
    if (!done){
        $("#inviteError").html("Email不符合要求");
        $("#inviteError").css({
            color:"#ffffff",
            background: "red"
        });
        $("#inviteError").fadeIn("fast");
        $("#invite_email").focus();
        $("#inviteError").fadeOut(2000);
        return false;
    }
    $("#inviteButton").val("处理中，请稍候...");
    $("#inviteButton").attr("disabled","disabled");
    return true;
}
J2bbCommon.getAbsolutePos = function(el) {
    var r = {
        x: el.offsetLeft,
        y: el.offsetTop
    };
    if (el.offsetParent) {
        var tmp = J2bbCommon.getAbsolutePos(el.offsetParent);
        r.x += tmp.x;
        r.y += tmp.y;
    }
    return r;
}
J2bbCommon.removeClass = function(el, className) {
    if (!(el && el.className)) {
        return;
    }
    var cls = el.className.split(" ");
    var ar = new Array();
    for (var i = cls.length; i > 0;) {
        if (cls[--i] != className) {
            ar[ar.length] = cls[i];
        }
    }
    el.className = ar.join(" ");
}

J2bbCommon.addClass = function(el, className) {
    J2bbCommon.removeClass(el, className);
    el.className += " " + className;
}
/**
     * show info with a input
     * @param objId
     * @param msgStr
     * @param showPosition: right or bottom
     */
J2bbCommon.formInfo = function (objId,msgStr,showPosition) {
    var obj = document.getElementById(objId);
    var p = J2bbCommon.getAbsolutePos(obj);
    J2bbCommon.addClass(obj,"validate_input_info");
    var divInfo=document.getElementById("divFormInfo");
    if(divInfo!=null){
        divInfo.parentNode.removeChild(divInfo);
    }
    var divInfoTop ;
    var divInfoLeft ;
    if("bottom"==showPosition){
        divInfoTop = p.y+obj.offsetHeight+"px";
        divInfoLeft = p.x+"px";
    }else{
        divInfoTop = p.y+"px";
        divInfoLeft = p.x+obj.offsetWidth+"px";
    }
    divInfo=document.createElement("div");
    divInfo.id="divFormInfo";
    divInfo.appendChild(document.createTextNode(msgStr));
    document.body.appendChild(divInfo);
    divInfo.className="message_info";
    divInfo.style.top=divInfoTop;
    divInfo.style.left=divInfoLeft;
    divInfo.setAttribute("z-index","1000");
    divInfo.style.display = "block";
    divInfo.style.position="absolute";
//$("#divFormInfo").fadeOut(2000);
//setTimeout("J2bbCommon.removeFormInfo('"+objId+"')",5000);
}
/**
     * remove info with a input
     * @param objId
     */
J2bbCommon.removeFormInfo=function (objId){
    var obj = document.getElementById(objId);
    J2bbCommon.removeClass(obj,"validate_input_info");
    var divInfo=document.getElementById("divFormInfo");
    if(divInfo!=null){
        divInfo.style.display = "none";
    }
}

/**
     * show error with a input
     * @param objId
     * @param msgStr
     * @param showPosition: right or bottom
     */
J2bbCommon.formError=function (objId,msgStr,showPosition){
    J2bbCommon.removeformError(objId);
    var obj = document.getElementById(objId);
    var p = J2bbCommon.getAbsolutePos(obj);
    J2bbCommon.addClass(obj,"validate_input_error");
    var divInfo=document.getElementById("divFormError");
    if(divInfo!=null){
        divInfo.parentNode.removeChild(divInfo);
    }
    var divInfoTop ;
    var divInfoLeft ;
    if("bottom"==showPosition){
        divInfoTop = p.y+obj.offsetHeight+"px";
        divInfoLeft = p.x+"px";
    }else{
        divInfoTop = p.y+"px";
        divInfoLeft = p.x+obj.offsetWidth+"px";
    }
    divInfo=document.createElement("div");
    divInfo.id="divFormError";
    document.body.appendChild(divInfo);
    divInfo.appendChild(document.createTextNode(msgStr));
    divInfo.className="message_error";
    divInfo.style.top=divInfoTop;
    divInfo.style.left=divInfoLeft;
    divInfo.setAttribute("z-index","1000");
    divInfo.style.display = "block";
    divInfo.style.position="absolute";
}
/**
     * remove error with a input
     * @param objId
     */
J2bbCommon.removeformError=function (objId){
    var obj = document.getElementById(objId);
    J2bbCommon.removeClass(obj,"validate_input_error");
    var divInfo=document.getElementById("divFormError");
    if(divInfo!=null){
        divInfo.style.display = "none";
    }
}
/**
     * show message in a div or span that already exests
     * @param divId
     * @param msgStr
     */
J2bbCommon.errorWithElement=function (divId,msgStr){
    var divInfo=document.getElementById(divId);
    if(divInfo==null){
        return;
    }
    divInfo.className="message_error";
    divInfo.style.display = "block";
    divInfo.innerHTML=msgStr;
    $("#"+divId).fadeOut(2000);
}

J2bbCommon.msgDivStr="<div id='slideMessageTitle'><a href='javascript:J2bbCommon.removeSlideMsg();'> <img src='images/icons/closes.gif' align='absmiddle' /></a>&nbsp;&nbsp;</div><div id='slideMessageInfo'><img src='images/comment.gif' align='absmiddle' />&nbsp;</div>";
/**
     * slide message from bottom
     */
J2bbCommon.slideMsg =function (msgStr){
    divInfo=document.createElement("div");
    divInfo.id="slideMessageDiv";
    document.body.appendChild(divInfo);
    $("#slideMessageDiv").html(J2bbCommon.msgDivStr);
    document.getElementById("slideMessageInfo").appendChild(document.createTextNode(msgStr));
    $("#slideMessageDiv").animate({
        height: 'toggle'
    }, 1500);
    setTimeout("J2bbCommon.hideSlideMsg ();",3000);
}
J2bbCommon.hideSlideMsg =function (){
    divInfo=document.getElementById("slideMessageDiv");
    if(divInfo==null)return;
    $("#slideMessageDiv").animate({
        height: 'toggle'
    }, 1500);
    //$("#slideMessageDiv").fadeOut(2000);
    setTimeout("J2bbCommon.removeSlideMsg();",2000);
}
J2bbCommon.removeSlideMsg =function (){
    divInfo=document.getElementById("slideMessageDiv");
    if(divInfo==null)return;
    divInfo.parentNode.removeChild(divInfo);
}


/**
     * 计算字符串的长度，汉字占两个字符
     * replace将符合此正则的字符串替换成指定字符 然后在计算长度
     * @param str
     */
J2bbCommon.strLen=function (str){
    return str.replace(/[^\x00-\xff]/g,"**").length;
}
/**
     * validator ,from tinymce
     */
var J2bbCommonValidator = {

    isEmail : function(s) {
        if(s=='')return false;
        //old patten:^[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&\'*+\\/0-9=?A-Z^_`a-z{|}~]+\.[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+$
        //return this.test(s, '^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$');
        var patrn=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
        if (!patrn.exec(s)){
            return false;
        }
        return true;
    },


    isMobile : function(s) {
        if(s=='')return false;
        var patrn=/^(13)[0-9]{9}/;
        if (!patrn.exec(s)){
            return false;
        }
        return true;
    },

    isDate : function(s) {
        if(s=='')return false;
        var patrn=/^(19)\d{2}\-\d{1-2}\-\d{1-2}/;
        if (!patrn.exec(s)){
            return false;
        }
        return true;
    },

    isQQ : function(s) {
        if(s=='')return false;
        var patrn=/[0-9]{3,12}/;
        if (!patrn.exec(s)){
            return false;
        }
        return true;
    },
    isWords : function(s) {
        if(s=='')return false;
        return this.test(s, '^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){2,19}$');
    },

    isWordsIncludingChinese : function(s) {
        if(s=='')return false;
        return this.test(s, '[\u4e00-\u9fa5_a-zA-Z0-9]{1,}');
    },


    isAbsUrl : function(s) {
        return this.test(s, '^(news|telnet|nttp|file|http|ftp|https)://[-A-Za-z0-9\\.]+\\/?.*$');
    },

    isSize : function(s) {
        return this.test(s, '^[0-9]+(px|%)?$');
    },

    isId : function(s) {
        return this.test(s, '^[A-Za-z_]([A-Za-z0-9_])*$');
    },

    isEmpty : function(s) {
        var nl, i;

        if (s.nodeName == 'SELECT' && s.selectedIndex < 1)
            return true;

        if (s.type == 'checkbox' && !s.checked)
            return true;

        if (s.type == 'radio') {
            for (i=0, nl = s.form.elements; i<nl.length; i++) {
                if (nl[i].type == "radio" && nl[i].name == s.name && nl[i].checked)
                    return false;
            }

            return true;
        }

        return new RegExp('^\\s*$').test(s.nodeType == 1 ? s.value : s);
    },

    isNumber : function(s, d) {
        return !isNaN(s.nodeType == 1 ? s.value : s) && (!d || !this.test(s, '^-?[0-9]*\\.[0-9]*$'));
    },

    test : function(s, p) {
        s = s.nodeType == 1 ? s.value : s;

        return s == '' || new RegExp(p).test(s);
    }
};


String.prototype.trim= function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.endWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
        return false;
    if(this.substring(this.length-str.length)==str)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
        return false;
    if(this.substr(0,str.length)==str)
        return true;
    else
        return false;
    return true;
}

// Copyright (C) krikkit - krikkit@gmx.net
// --> http://www.krikkit.net/
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
 
function copyToClip(meintext){
    if (window.clipboardData) {
   
        // the IE-manier
        window.clipboardData.setData("Text", meintext);
   
    // waarschijnlijk niet de beste manier om Moz/NS te detecteren;
    // het is mij echter onbekend vanaf welke versie dit precies werkt:
    }
    else if (window.netscape) {
        // dit is belangrijk maar staat nergens duidelijk vermeld:
        // you have to sign the code to enable this, or see notes below
        try {
            netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
        } catch(e) {
            alert("出于安全的考虑浏览器拒绝了您的操作\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'")
        }
        // maak een interface naar het clipboard
        var clip = Components.classes['@mozilla.org/widget/clipboard;1']
        .createInstance(Components.interfaces.nsIClipboard);
        if (!clip) return;
   
        // maak een transferable
        var trans = Components.classes['@mozilla.org/widget/transferable;1']
        .createInstance(Components.interfaces.nsITransferable);
        if (!trans) return;
   
        // specificeer wat voor soort data we op willen halen; text in dit geval
        trans.addDataFlavor('text/unicode');
   
        // om de data uit de transferable te halen hebben we 2 nieuwe objecten
        // nodig om het in op te slaan
        var str = new Object();
        var len = new Object();
   
        var str = Components.classes["@mozilla.org/supports-string;1"]
        .createInstance(Components.interfaces.nsISupportsString);
        var copytext=meintext;

        str.data=copytext;
        trans.setTransferData("text/unicode",str,copytext.length*2);

        var clipid=Components.interfaces.nsIClipboard;
        if (!clip) return false;
        clip.setData(trans,null,clipid.kGlobalClipboard);
    }
    return false;
}
