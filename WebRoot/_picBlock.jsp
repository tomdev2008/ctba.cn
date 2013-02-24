<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*" />
<jsp:directive.page import="java.util.Map.Entry"/>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
    Integer picIndex = new Random().nextInt(3) + 1;
    Integer wordIndex = new Random().nextInt(16);
    Map<Object, Object> wordsMap = new HashMap<Object, Object>();
    wordsMap.put("hoi", "荷兰语");
    wordsMap.put("salut", "罗马尼亚语");
    wordsMap.put("hola", "西班牙语");
    wordsMap.put("Guten Tag", "德语");
    wordsMap.put("sawubona", "祖鲁语");
    wordsMap.put("sa-wat-dee", "泰国语");
    wordsMap.put("Kumusta", "塔加路语");
    wordsMap.put("Jó napot", "匈牙利语");
    wordsMap.put("namaste", "海地语");
    wordsMap.put("shalom", "希伯来语");
    wordsMap.put("Saluton", "世界语");
    wordsMap.put("Goddag", "丹麦语");
    wordsMap.put("Ahalan", "阿拉伯语");
    wordsMap.put("Bok", "克罗地亚语");
    wordsMap.put("عليكم السلام", "阿拉伯语");
    wordsMap.put("こにちは", "日语");
    Entry e = (Entry) wordsMap.entrySet().toArray()[wordIndex];
    String word = (String) e.getKey();
    String wordType = (String) e.getValue();
    //Map<Integer, String> linkMap = new HashMap<Integer, String>();
    //linkMap.put(1, "http://www.ctba.cn/channel/dimemag");
    //linkMap.put(2, "http://www.ddku.com/index.php/campaign/charm/landing"); 
    //linkMap.put(3, "http://www.ctba.cn/channel/badminton"); 
%>
<div class="left_block">
    <div class="topbar_wrap">
        <div id="pic-slider">
            <ul>
                <li>
                    <a href="http://www.udong.com.cn/">
                        <img src="images/ctba/ct_banner_1.gif" alt="优动网，正品乒羽专卖" />
                    </a>
                </li>
                <li>
                    <a href="http://www.udong.com.cn/channel.action?method=badminton">
                        <img src="images/ctba/ct_banner_2.gif" alt="优动网，正品乒羽专卖" />
                    </a>
                </li>
                <li>
                    <a href="http://www.ctba.cn/channel/dimemag">
                        <img src="images/ctba/1.gif" alt="" />
                    </a>
                </li>
                <%-- 
                <li><a href="http://www.ddku.com/index.php/campaign/charm/landing">
                    <img src="images/ctba/2.gif" alt="" /></a>
                </li>
                --%>
            </ul>
        </div>
        <%-- 
        <a href="<%=linkMap.get(picIndex)%>" target="_blank">
            <img src="images/ctba/<%=picIndex%>.gif" class="noZoom" />
        </a>
        --%>
        <div class="topbar_info redlink">
            <c:if test="${empty __sys_username}">
                <img src="images/icons/lightbulb.jpg" align="absmiddle" />&nbsp;如果你已经是<bean:message key="sys.name" />的一员，那么请&nbsp;<a href="javascript:J2bbCommon.showLoginForm();">登录</a>&nbsp;或者现在就&nbsp;<a href="${context }/userExt.do?method=regForm">加入我们</a>，你将成为<bean:message key="sys.name" />的第&nbsp;<span class="color_blue">${userCnt+1 }</span>&nbsp;位会员
            </c:if>
            <c:if test="${not empty __sys_username}">
                <img src="images/icons/emotion_happy.png" align="absmiddle" />&nbsp;<span class="color_orange">&nbsp;<%=word%>, &nbsp;${__sys_username }!&nbsp;</span>现在你懂得用<%=wordType%>打招呼了:)&nbsp;欢迎回到<bean:message key="sys.name" />！
            </c:if>
        </div>
    </div>
</div>
