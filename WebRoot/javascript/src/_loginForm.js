/***
 * show model login form
 * @author gladstone
 * @date 2007-12-7
 * @note: this work with jQuery!
 */

var loginDivStr="";

function showLoginForm(){
	loginDivStr=$("#loginDiv").html();
	$("#loginDiv").html('');
	displayStaticMessage(loginDivStr,"modalDialog_login");
	$("#username").focus();
}
/**close the login window */
function closeLoginForm(){
	closeMessage();
	$("#loginDiv").html(loginDivStr);
}
/**use the static message */
function displayStaticMessage(messageContent,cssClass)
{
	messageObj.setHtmlContent(messageContent);
	messageObj.setSize(330,150);
	messageObj.setCssClassMessageBox(cssClass);
	messageObj.setSource(false);	// no html source since we want to use a static message here.
	messageObj.setShadowDivVisible(false);	// Disable shadow for these boxes	
	messageObj.display();
}
function closeMessage()
{
	messageObj.close();	
}
/**
 * 校验登录名：email now
 */
function checkUserName(){
	var userName=$("#username").val();
	//var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){2,19}$/;
	 var patrn=/[\u4e00-\u9fa5_a-zA-Z0-9]{2,}/;
	//var patrn=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if (!patrn.exec(userName)){
		 $("form > #loginError").html("您的用户名不符合要求");
		 $("form >  #loginError").css({color:"#ffffff",background: "red"});
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
function checkPass() {
	var userPassword=$("#password").val();
	var patrn=/^(\w){6,20}$/;
	if (!patrn.exec(userPassword)){
		 $("form > #loginError").html("请输入不少于六位的密码");
 		 $("form > #loginError").css({color:"#ffffff",background: "red"});
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
function checkLoginForm() {
	if(!checkUserName())
	return false;
	if(!checkPass())
	return false;
	cookieUtils.setCookie("j2bbCookieURL", location.href);
	$("#loginButton").val("登录中，请稍候...");
	$("#loginButton").attr("disabled","disabled");
	return true;
}
