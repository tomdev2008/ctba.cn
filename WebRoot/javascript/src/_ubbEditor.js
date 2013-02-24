//Examine the browser
var userAgent = navigator.userAgent.toLowerCase();
var is_opera  = (userAgent.indexOf('opera') != -1);
var is_saf    = ((userAgent.indexOf('applewebkit') != -1) || (navigator.vendor == 'Apple Computer, Inc.'));
var is_webtv  = (userAgent.indexOf('webtv') != -1);
var is_ie     = ((userAgent.indexOf('msie') != -1) && (!is_opera) && (!is_saf) && (!is_webtv));
var is_ie4    = ((is_ie) && (userAgent.indexOf('msie 4.') != -1));
var is_moz    = ((navigator.product == 'Gecko') && (!is_saf));
var is_kon    = (userAgent.indexOf('konqueror') != -1);
var is_ns     = ((userAgent.indexOf('compatible') == -1) && (userAgent.indexOf('mozilla') != -1) && (!is_opera) && (!is_webtv) && (!is_saf));
var is_ns4    = ((is_ns) && (parseInt(navigator.appVersion) == 4));
var is_mac    = (userAgent.indexOf('mac') != -1);

//For firefox, tell Firefox not to display the content you input in last session
/*if (is_moz) {
	var tmp_c=document.getElementById('topicContent');
	if (tmp_c) tmp_c.value='';
}

//Show/Hide a DIV
function showhidediv(id){
  try{
    var panel=document.getElementById(id);
    if(panel){
      if(panel.style.display=='none'){
        panel.style.display='block';
      }else{
        panel.style.display='none';
      }
    }
  }catch(e){}
}
*/
function addhtml (id, htmlcode, uniqueid) {
    var panel=document.getElementById(id);
    var hiddenpannel=document.getElementById(uniqueid);
    if(panel){
        hiddenpannel.value='';
        hiddenpannel.value=panel.innerHTML;
        panel.innerHTML=hiddenpannel.value+htmlcode;
        hiddenpannel.value+=htmlcode;
    }
}


//Ctrl+Enter key submitting (Textarea)
function ctrlenterkey (eventobject){
    if(eventobject.ctrlKey && eventobject.keyCode==13) {
        document.getElementById("btnSubmit").click();
    }
}


//Avatar Selection
function changeavatar (slname, area) {
    var current=document.getElementById(slname);
    var realvalue=current.options[current.selectedIndex].value;
    var areashow=document.getElementById(area);
    if (areashow) {
        if (realvalue!='' && realvalue!=null) {
            areashow.innerHTML="<img src='images/avatars/"+realvalue+"' alt=''/>";
        }
        else {
            areashow.innerHTML=jslang[13];
        }
    }
}

//Insert Emots
function insertFace (emotcode) {
    var current=document.getElementById('topicContent');
    var emot="[face]"+emotcode+"[/face]";
    if (current) {
        if (current.value!='' && current.value!=null) {
            current.value+=emot;
        }
        else {
            current.value=emot;
        }
    }
}

//Insert new Emots
function newEM (emotcode) {
    var current=document.getElementById('topicContent');
    var emot="[panst"+emotcode+"]";
    if (current) {
        if (current.value!='' && current.value!=null) {
            current.value+=emot;
        }
        else {
            current.value=emot;
        }
    }
}


//Media Link
function playmedia(strID,strType,strURL,intWidth,intHeight) {
    var objDiv=document.getElementById(strID);
    if (!objDiv) return false;
    if (objDiv.style.display!='none') {
        objDiv.innerHTML='';
        objDiv.style.display='none';
    } else {
        objDiv.innerHTML=makemedia(strType,strURL,intWidth,intHeight);
        objDiv.style.display='block';
    }
}

//Media Build
function makemedia (strType,strURL,intWidth,intHeight) {
    var strHtml;
    switch(strType) {
        case 'wmp':
            strHtml="<object width='"+intWidth+"' height='"+intHeight+"' classid='CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6'><param name='url' value='"+strURL+"'/><embed width='"+intWidth+"' height='"+intHeight+"' type='application/x-mplayer2' src='"+strURL+"'></embed></object>";
            break;
        case 'swf':
            strHtml="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='"+intWidth+"' height='"+intHeight+"'><param name='movie' value='"+strURL+"'/><param name='quality' value='high' /><embed src='"+strURL+"' quality='high' type='application/x-shockwave-flash' width='"+intWidth+"' height='"+intHeight+"'></embed></object>";
            break;
        case 'real':
            strHtml="<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width='"+intWidth+"' height='"+intHeight+"'><param name='src' value='"+absbaseurl+"inc/realplay.php?link="+strURL+"' /><param name='controls' value='Imagewindow' /><param name='console' value='clip1' /><param name='autostart' value='true' /><embed src='"+absbaseurl+"inc/realplay.php?link="+strURL+"' type='audio/x-pn-realaudio-plugin' autostart='true' console='clip1' controls='Imagewindow' width='"+intWidth+"' height='"+intHeight+"'></embed></object><br/><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width='"+intWidth+"' height='44'><param name='src' value='"+absbaseurl+"inc/realplay.php?link="+strURL+"' /><param name='controls' value='ControlPanel' /><param name='console' value='clip1' /><param name='autostart' value='true' /><embed src='"+absbaseurl+"inc/realplay.php?link="+strURL+"' type='audio/x-pn-realaudio-plugin' autostart='true' console='clip1' controls='ControlPanel' width='"+intWidth+"' height='44'></embed></object>";
            break;
    }
    return strHtml;
}

//Font size control
function doZoom(size) {
    document.getElementById('zoomtext').style.fontSize=size+'px';
}



function findobj(n, d) {
    var p, i, x;
    if(!d) d = document;
    if((p = n.indexOf("?"))>0 && parent.frames.length) {
        d = parent.frames[n.substring(p + 1)].document;
        n = n.substring(0, p);
    }
    if(x != d[n] && d.all) x = d.all[n];
    for(i = 0; !x && i < d.forms.length; i++) x = d.forms[i][n];
    for(i = 0; !x && d.layers && i < d.layers.length; i++) x = findobj(n, d.layers[i].document);
    if(!x && document.getElementById) x = document.getElementById(n);
    return x;
}

var jslang = new Array ();
jslang[0]='回复：';
jslang[1]='提交';
jslang[2]='清空';
jslang[3]='取消';
jslang[4]='确定删除这篇日志吗？包括回复和引用在内的一切都将被删除且不可恢复！';
jslang[5]='这步操作将消除一些数据且不可恢复！是否继续？';
jslang[6]='确定删除对这条评论的回复吗？这步操作不可恢复！';
jslang[7]='确定删除对这条留言的回复吗？这步操作不可恢复！';
jslang[8]='确定删除这条评论吗？这步操作不可恢复！';
jslang[9]='确定删除这条留言吗？这步操作不可恢复！';
jslang[10]='请填写验证码！';
jslang[11]='请填写昵称或内容！';
jslang[12]='正在提交，请稍候';
jslang[13]='无头像';
jslang[14]='未加星标';
jslang[15]='已加星标';
jslang[16]='确定删除这个项目吗？这步操作不可恢复！';
jslang[17]='确定删除这个天气吗？删除后，标注了此天气的日志的天气位置将显示为“未指定”。此操作不会同时删除该天气的图片。';
jslang[18]='位置：';
jslang[19]='左对齐';
jslang[20]='普通';
jslang[21]='右对齐';
jslang[22]='插入';
jslang[23]='Email 地址';
jslang[24]='文字将被变粗';
jslang[25]='文字将变斜体';
jslang[26]='文字';
jslang[27]='文字将加下划线';
jslang[28]='被引用的文字';
jslang[29]='为选中文字添加超级链接';
jslang[30]='链接文本显示';
jslang[31]='如果不想使用, 可以为空, 将只显示超级链接地址';
jslang[32]='超级链接';
jslang[33]='图片的 URL';
jslang[34]='没有可以挽回的数据！';
jslang[35]='大小 ';
jslang[36]='请输入自定义的字体名';
jslang[37]='要设置字体的文字';
jslang[38]='请输入自定义颜色的代码';
jslang[39]='颜色';
jslang[40]='Email 地址';
jslang[41]='文件下载地址';
jslang[42]='为选中文字添加注释';
jslang[43]='请输入文字';
jslang[44]='请输入注释';
jslang[45]='文字将加删除线';
jslang[46]='文字将作为下标';
jslang[47]='文字将作为上标';
jslang[48]='对齐样式';
jslang[49]="输入 'center' 表示居中, 'left' 表示左对齐, 'right' 表示右对齐.";
jslang[50]='错误!';
jslang[51]="类型只能输入 'center' 、 'left' 或者 'right'.";
jslang[52]='要对齐的文本';
jslang[53]='为选中文字添加超级链接';
jslang[54]="链接文本显示.\n如果不想使用, 可以为空, 将只显示超级链接地址. ";
jslang[55]='超级链接';
jslang[56]="输入 'm' 表示居中, 'l' 表示左对齐, 'r' 表示右对齐";
jslang[57]="类型只能输入 'm' 、 'l' 或者 'r'.";
jslang[58]="限定图片的尺寸（格式：宽,高，例：400,300）\n不限定则留空\n未知的高宽可用*代替，比如 400,* 或 *,200";
jslang[59]='该多媒体文件的地址';
jslang[60]='该多媒体文件的宽度';
jslang[61]='该多媒体文件的高度';
jslang[62]='输入代码';
jslang[63]=' 秒后自动保存';
jslang[64]='正在保存...';
jslang[65]='已自动保存。';
jslang[66]='视频地址';
jslang[67]='视频宽度';
jslang[68]='视频高度';

var clientVer = navigator.userAgent.toLowerCase(); // Get browser version
var is_firefox = ((clientVer.indexOf("gecko") != -1) && (clientVer.indexOf("firefox") != -1) && (clientVer.indexOf("opera") == -1)); //Firefox or other Gecko

function loadNewEM(pageOffset) {
    $("#emotionsDiv").html("<img src='images/ajax-loader.gif' align='absmiddle' />&nbsp;表情载入中...");
    $.get(
        "emotion.jsp",
        {
            p:pageOffset,
            loadType:"ajax"
        },
        function(data){
            $("#emotionsDiv").html(data);
        }
        );
}
function AddText(NewCode) {
    document.getElementById('topicContent').value+=NewCode
}

// From http://www.massless.org/mozedit/
function FxGetTxt(open, close)
{
    var selLength = document.getElementById('topicContent').textLength;
    var selStart = document.getElementById('topicContent').selectionStart;
    var selEnd = document.getElementById('topicContent').selectionEnd;
    if (selEnd == 1 || selEnd == 2)  selEnd = selLength;
    var s1 = (document.getElementById('topicContent').value).substring(0,selStart);
    var s2 = (document.getElementById('topicContent').value).substring(selStart, selEnd)
    var s3 = (document.getElementById('topicContent').value).substring(selEnd, selLength);
    document.getElementById('topicContent').value = s1 + open + s2 + close + s3;
    return;
}

function email() {
    txt=prompt(jslang[23],"name\@domain.com");
    if (txt!=null) {
        AddTxt="[email]"+txt+"[/email]";
        AddText(AddTxt);
    }
}

function bold() {
    if (document.selection && document.selection.type == "Text") {
        var range = document.selection.createRange();
        range.text = "[b]" + range.text + "[/b]";
    }
    else if (is_firefox && document.getElementById('topicContent').selectionEnd) {
        txt=FxGetTxt ("[b]", "[/b]");
        return;
    } else {
        txt=prompt(jslang[24],jslang[26]);
        if (txt!=null) {
            AddTxt="[b]"+txt;
            AddText(AddTxt);
            AddTxt="[/b]";
            AddText(AddTxt);
        }
    }
}

function italicize() {
    if (document.selection && document.selection.type == "Text") {
        var range = document.selection.createRange();
        range.text = "[i]" + range.text + "[/i]";
    } else if (is_firefox && document.getElementById('topicContent').selectionEnd) {
        txt=FxGetTxt ("[i]", "[/i]");
        return;
    } else {
        txt=prompt(jslang[25],jslang[26]);
        if (txt!=null) {
            AddTxt="[i]"+txt;
            AddText(AddTxt);
            AddTxt="[/i]";
            AddText(AddTxt);
        }
    }
}

function underline() {
    if (document.selection && document.selection.type == "Text") {
        var range = document.selection.createRange();
        range.text = "[u]" + range.text + "[/u]";
    } else if (is_firefox && document.getElementById('topicContent').selectionEnd) {
        txt=FxGetTxt ("[u]", "[/u]");
        return;
    } else {
        txt=prompt(jslang[27],jslang[26]);
        if (txt!=null) {
            AddTxt="[u]"+txt;
            AddText(AddTxt);
            AddTxt="[/u]";
            AddText(AddTxt);
        }
    }
}

function quoteme() {
    if (document.selection && document.selection.type == "Text") {
        var range = document.selection.createRange();
        range.text = "[quote]" + range.text + "[/quote]";
    } else if (is_firefox && document.getElementById('topicContent').selectionEnd) {
        txt=FxGetTxt ("[quote]", "[/quote]");
        return;
    } else {
        txt=prompt(jslang[28],jslang[26]);
        if(txt!=null) {
            AddTxt="[quote]"+txt;
            AddText(AddTxt);
            AddTxt="[/quote]";
            AddText(AddTxt);
        }
    }
}

function hyperlink() {
    if (document.selection && document.selection.type == "Text") {
        var range = document.selection.createRange();
        txt=prompt(jslang[29],"http://");
        range.text = "[url=" + txt + "]" + range.text + "[/url]";
    } else if (is_firefox && document.getElementById('topicContent').selectionEnd) {
        txt=prompt(jslang[29],"http://");
        txt=FxGetTxt ("[url=" + txt + "]", "[/url]");
        return;
    } else {
        txt2=prompt(jslang[30]+"\n"+jslang[31],"");
        if (txt2!=null) {
            txt=prompt(jslang[32],"http://");
            if (txt!=null) {
                if (txt2=="") {
                    AddTxt="[url]"+txt;
                    AddText(AddTxt);
                    AddTxt="[/url]";
                    AddText(AddTxt);
                } else {
                    AddTxt="[url="+txt+"]"+txt2;
                    AddText(AddTxt);
                    AddTxt="[/url]";
                    AddText(AddTxt);
                }
            }
        }
    }
}
//============
// 添加视频
//============
function flash() {
    if (document.selection && document.selection.type == "Text") {
        var range = document.selection.createRange();
        var width=prompt(jslang[67],"480");
        var height=prompt(jslang[68],"440");
        range.text = "[object=" + width +":"+height+ "]" + range.text + "[/object]";
    } else if (is_firefox && document.getElementById('topicContent').selectionEnd) {
        var width=prompt(jslang[67],"480");
        var height=prompt(jslang[68],"440");
        txt=FxGetTxt ("[object=" + width +":"+height+ "]", "[/object]");
        return;
    } else {
        txt=prompt(jslang[66],"http://");
        if (txt!="") {
            var width=prompt(jslang[67],"480");
            var height=prompt(jslang[68],"440");
            AddTxt="[object="+width+":"+height+"]"+txt;
            AddText(AddTxt);
            AddTxt="[/object]";
            AddText(AddTxt);
        }
    }
}

function image() {
    txt=prompt(jslang[33],"http://");
    if(txt!=null) {
        AddTxt="[img]"+txt+"[/img]";
        AddText(AddTxt);
    }
}

// Check for Browser & Platform for PC & IE specific bits
// More details from: http://www.mozilla.org/docs/web-developer/sniffer/browser_type.html
var clientPC = navigator.userAgent.toLowerCase(); // Get client info
var is_ie = ((clientPC.indexOf("msie") != -1) && (clientPC.indexOf("opera") == -1));
var is_nav = ((clientPC.indexOf('mozilla')!=-1) && (clientPC.indexOf('spoofer')==-1)
    && (clientPC.indexOf('compatible') == -1) && (clientPC.indexOf('opera')==-1)
    && (clientPC.indexOf('webtv')==-1) && (clientPC.indexOf('hotjava')==-1));
var is_moz = 0;
var is_win = ((clientPC.indexOf("win")!=-1) || (clientPC.indexOf("16bit") != -1));
var is_mac = (clientPC.indexOf("mac")!=-1);


function bbfontstyle(bbopen, bbclose) {
    var txtarea = document.getElementById('topicContent');
    if ((clientVer >= 4) && is_ie && is_win) {
        theSelection = document.selection.createRange().text;
        if (!theSelection) {
            txtarea.value += bbopen + bbclose;
            txtarea.focus();
            return;
        }
        document.selection.createRange().text = bbopen + theSelection + bbclose;
        txtarea.focus();
        return;
    }
    else if (txtarea.selectionEnd && (txtarea.selectionEnd - txtarea.selectionStart > 0))
    {
        mozWrap(txtarea, bbopen, bbclose);
        return;
    }
    else
    {
        txtarea.value += bbopen + bbclose;
        txtarea.focus();
    }
    storeCaret(txtarea);
}
// Insert at Claret position. Code from
// http://www.faqts.com/knowledge_base/view.phtml/aid/1052/fid/130
function storeCaret(textEl) {
    if (textEl.createTextRange) textEl.caretPos = document.selection.createRange().duplicate();
}
// From http://www.massless.org/mozedit/
function mozWrap(txtarea, open, close)
{
    var selLength = txtarea.textLength;
    var selStart = txtarea.selectionStart;
    var selEnd = txtarea.selectionEnd;
    if (selEnd == 1 || selEnd == 2)
        selEnd = selLength;

    var s1 = (txtarea.value).substring(0,selStart);
    var s2 = (txtarea.value).substring(selStart, selEnd)
    var s3 = (txtarea.value).substring(selEnd, selLength);
    txtarea.value = s1 + open + s2 + close + s3;
    return;
}