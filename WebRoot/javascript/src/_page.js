/*
 * core functions 4 page
 * @author gladstone
 * @date 2007-12-7
 * @note: this work with jQuery!
 */

$(function () {
    J2bbCommon.buildMenu();
    J2bbCommon.switchTabs();
});

//========>>>>暂时保留，将删除
var confirmWin="<div class='messageTitle'>确认</div>	<div id='confirmWinMsg' ></div><input type='button' id='confirmButton' value=' 确  定 ' class='input_btn'  style='margin-bottom:10px;'/>	<input type='button' value=' 取  消 ' class='input_btn'	onclick='closeMessage();' style='margin-bottom:10px;'/>";
var messageWin="<div class='messageTitle'>提示</div>	<div id='messageWinMsg' ></div><input type='button' value=' 确  定 ' class='input_btn' onclick='closeMessage();' style='margin-bottom:10px;'/>	";
messageObj = new DHTML_modalMessage();	
messageObj.setShadowOffset(0);
function confirmURL(str,url2Go){
	displayMessage(confirmWin,"modalDialog_confirm");
	$("#confirmWinMsg").html(str);
	$("#confirmButton").click(function(){closeMessage();location.href=url2Go;});
}
function confirmAction(str,actionFunc){
	displayMessage(confirmWin,"modalDialog_confirm");
	$("#confirmWinMsg").html(str);
	$("#confirmButton").click(function(){closeMessage();eval(actionFunc);});
}
function showMessage(str){
	displayMessage(messageWin,"modalDialog_message");
	$("#messageWinMsg").html(str);
}
function displayMessage(messageContent,cssClass)
{
	messageObj.setHtmlContent(messageContent);
	messageObj.setSize(330,150);
	messageObj.setCssClassMessageBox(cssClass);
	messageObj.setSource(false);	
	messageObj.setShadowDivVisible(false);	
	messageObj.display();
}
function displaySizedMessage(messageContent,cssClass,width,height)
{
	messageObj.setHtmlContent(messageContent);
	messageObj.setSize(width,height);
	messageObj.setCssClassMessageBox(cssClass);
	messageObj.setSource(false);	
	messageObj.setShadowDivVisible(false);	
	messageObj.display();
}
function displayUrlMessage(url,width,height)
{
	messageObj.setSource(url);
	messageObj.setCssClassMessageBox(false);
	messageObj.setSize(width,height);
	messageObj.setShadowDivVisible(true);	// Enable shadow for these boxes
	messageObj.display();
}
function closeMessage()
{
	messageObj.close();	
}
//<<<<=========暂时保留，将删除
