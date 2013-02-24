<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="javascript/jquery-1.2.6.min.js"></script>
<div id="emotionsDivMiniFrame"><img src='images/ajax-loader.gif' align='absmiddle' />&nbsp;表情载入中...</div>
<script type="text/javascript">
    function loadNewEM(pageOffset) {
        $("#emotionsDivMiniFrame").html("<img src='images/ajax-loader.gif' align='absmiddle' />&nbsp;表情载入中...");
        $.get(
        "emotion.jsp",
        { p:pageOffset,loadType:"ajax"},
        function(data){
            $("#emotionsDivMiniFrame").html(data);
        }
    );
    }
    loadNewEM(0);
</script>
