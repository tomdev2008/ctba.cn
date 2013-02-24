//Not in use
function firstPage(){
    var currOffset= 0;
    loadNotes(currOffset);
}
function lastPage(currOffset,pageSize,totalCnt){
    currOffset= pageSize*(parseInt(""+totalCnt/pageSize));
    loadNotes(currOffset);
}
function nextPage(currOffset,pageSize,totalCnt){
    currOffset= currOffset+pageSize;
    if(currOffset>=totalCnt)currOffset= currOffset-pageSize;
    loadNotes(currOffset);
}
function prevPage(currOffset,pageSize){
    currOffset= currOffset-pageSize;
    if(currOffset<=0)currOffset=0;
    loadNotes(currOffset);
}
$( function(){
    loadNotes(0);
});
			 
function submitNote () {
    var name = $('#authorName').val();
    var verImg = "test";
    var content = $('#topicContent').val();
    if(content == '' || content.length > 500) {
        J2bbCommon.formError('topicContent', '内容不能为空，不能多于500字', 'red');
        return;
    }
    $.ajax({
        url : 'com.action',
        type : 'post',
        data : 'method=save&eid=${entryModel.id}&loadType=ajax&body=' + content + '&name=' + name + '&verImg=' + verImg,
        success : function (msg) {
            $('#noteContent').val('');
            $('#authorName').val('');
            loadNotes(0);
        }
    });
    J2bbCommon.removeformError('authorName');
    J2bbCommon.removeformError('noteContent');
}
function loadNotes (offSet) {
    $('#notes').html('<img src="images/ajax-loader.gif" />');
    $.ajax({
        url : 'com.action?method=list',
        type : 'get',
        data : 'themeDir=${blogModel.theme}&loadType=ajax&eid=${entryModel.id}&pager.offset=' + offSet,
        success : function (msg) {
            $('#notes').html(msg);
        }
    });
}
function reNote (username, content) {
    $('#topicContent').val('');
    $('#topicContent').val('[quote]@' + username + ' ' + content + '[/quote]');
    $('#topicContent').focus();
}