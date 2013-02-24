<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*" />
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%

		Integer picIndex = new Random().nextInt(8) + 1;
		Map<Integer, String> linkMap = new HashMap<Integer, String>();
		linkMap.put(1, "http://www.raywow.com/ec/goods.php?id=134&ad_u=31&ad_id=6");
		linkMap.put(2, "http://www.raywow.com/ec/goods.php?id=155&ad_u=31&ad_id=7");
		linkMap.put(3, "http://www.raywow.com/ec/goods.php?id=195&ad_u=31&ad_id=8");
		linkMap.put(4, "http://www.raywow.com/ec/goods.php?id=126&ad_u=31&ad_id=9");
		linkMap.put(5, "http://www.raywow.com/ec/goods.php?id=225&ad_u=31&ad_id=10");
		linkMap.put(6, "http://www.raywow.com/ec/goods.php?id=221&ad_u=31&ad_id=11");
		linkMap.put(7, "http://www.raywow.com/ec/goods.php?id=69&ad_u=31&ad_id=12");
		linkMap.put(8, "http://www.raywow.com/ec/goods.php?id=164&ad_u=31&ad_id=13");
%>
<div class="mb8">
	<a href="<%=linkMap.get(picIndex)%>" target="_blank">
		<img src="ad/raywow/<%=picIndex%>s.gif" class="noZoom" />
	</a>
</div>
