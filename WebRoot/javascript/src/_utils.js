/***
 * common utils
 * @author gladstone
 * @date 2007-12-7
 */
var commonUtils = {
    /***
	 * preview a img 2 upload
	 * @param:fileCompId file component
	 * @param:prevComId img component 
	 */
    previewImg : function (fileCompId, prevComId) {
        var fileValue = document.getElementById(fileCompId).value;
        var fileext = fileValue.substring(fileValue.lastIndexOf("."), fileValue.length);
        fileext = fileext.toLowerCase();
        if ((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp')) {
            //alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 !");
            document.getElementById(fileCompId).focus();
        } else {
            var img = document.createElement("img");
            img.setAttribute("src", "file://localhost/"+fileValue);
            img.setAttribute("width", "200");
            img.setAttribute("id", "img_preview");
            document.getElementById(prevComId).innerHTML="";
            document.getElementById(prevComId).appendChild(img);
        //document.getElementById(prevComId).innerHTML="<img src='"+fileValue+"' width=120 style='border:2px double #ccc'>"
        }
    },
    /***
	 * instead of alert
	 * @param:str message string
	 */
    message : function(str) {
        J2bbCommon.showMessage(str);
    },
    /***
	 * validate a file 2 upload
	 * @param:fileCompId  file component
	 * @param:maxFileSize max size of the file
	 */
    validateImg : function (fileCompId, maxFileSize) {
        var fileValue = document.getElementById(fileCompId).value;
        var fileext = fileValue.substring(fileValue.lastIndexOf("."),fileValue.length);
        fileext = fileext.toLowerCase();
        if ((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp')) {
            document.getElementById(fileCompId).focus();
        } else {
            var img=document.createElement("img");
            img.setAttribute("src", "file://localhost/"+fileValue);
            img.setAttribute("width", "200");
        //all.img.fileSize/1024;
        }
    }
}