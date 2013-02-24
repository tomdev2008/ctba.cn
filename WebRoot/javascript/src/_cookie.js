/*
 * cookie utils
 * @author gladstone
 * @date 2007-12-10
 */
 
var cookieUtils = {
    /*
     * 获得Cookie解码后的值
     * @param:offset
     */
    getCookieVal:function(offset) {
        var endstr = document.cookie.indexOf(";", offset);
        if (endstr == -1) {
            endstr = document.cookie.length;
        }
        return unescape(document.cookie.substring(offset, endstr));
    },
    /*
     * 设定Cookie值
     * @param:name
     * @param:value
     */
    setCookie:function(name, value) {
        var expdate = new Date();
        var argv = cookieUtils.setCookie.arguments;
        var argc = cookieUtils.setCookie.arguments.length;
        var expires = (argc > 2) ? argv[2] : null;
        var path ="/";// (argc > 3) ? argv[3] : null;
        var domain = "ctba.cn";//(argc > 4) ? argv[4] : null;
        var secure = (argc > 5) ? argv[5] : false;
        if (expires != null) {
            expdate.setTime(expdate.getTime() + (expires * 1000));
        }
        document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expdate.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
    },
    /*
     * 删除Cookie
     * @param:name
     */
    delCookie:function (name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = GetCookie(name);
        document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
    },
    /*
     * 获得Cookie的原始值
     * @param:name
     */
    getCookie:function (name) {
        var arg = name + "=";
        var alen = arg.length;
        var clen = document.cookie.length;
        var i = 0;
        while (i < clen) {
            var j = i + alen;
            if (document.cookie.substring(i, j) == arg) {
                return cookieUtils.getCookieVal(j);
            }
            i = document.cookie.indexOf(" ", i) + 1;
            if (i == 0) {
                break;
            }
        }
        return null;
    }
}