<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="../../common/error_page.jsp"%>
<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../common/include.jsp"%>
		<title>品牌检索&nbsp;-&nbsp;装备秀&nbsp;-&nbsp;<bean:message
			key="nav.indexPage" /></title>
		<style type="text/css">
			.simple_list li {
				padding: 4px 0;
				border-bottom: 1px dashed #eee
			}

			.simple_list li.last {
				border: none
			}

			ul.sort_detail {
				background:#FFFFFF none repeat scroll 0 0;
				clear:both;
				display:inline;
				float:left;
				overflow:hidden;
				padding:10px 15px 3px 10px;
				width:581px;
			}

			ul.sort_detail li {
				display:inline;
				float:left;
				margin:0 10px 7px;
				white-space:nowrap;
			}
		</style>
	</head>
	<body>
		<jsp:include flush="true" page="./head.jsp" />
		<div id="wrapper">
			<div id="area_left">

				<div id="webmap" class="clearfix">
					<div class="fleft">
						<img src="images/icons/map.png" align="absmiddle" />&nbsp;<a href="${context }/"><bean:message key="nav.indexPage" /></a>&nbsp;&gt;&nbsp;<a href="${context }/equipment/">装备秀</a>&nbsp;>&nbsp;品牌检索
					</div>
				</div>

				<div id="sidebar" class="fleft clearfix">
					<jsp:include flush="true" page="_userBar.jsp" />
					<jsp:include flush="true" page="../../_adBlockMini.jsp" />
				</div>
				<div id="mid_column" class="fright">
					<jsp:include flush="true" page="_types.jsp" />
					<div class="mid_block">
						<div class="title">
							热门品牌
						</div>
						<div class="brand_icons clearfix">
							<a class="nike" href="equipment/search/nike" title="nike">nike</a>
							<a class="adidas" href="equipment/search/adidas" />
							<a class="adidasoriginal" href="equipment/search/adidasoriginal" title="adidas original">adidas original</a>
							<a class="adidasstyle" href="equipment/search/adidasstyle" title="adidas style">adidas style</a>
							<a class="converse" href="equipment/search/converse" title="converse">converse</a>
							<a class="newbalance" href="equipment/search/newbalance" title="new balance">new balance</a>
							<a class="kappa" href="equipment/search/kappa" title="kappa">kappa</a>
							<a class="lecoq" href="equipment/search/lecoq" title="lecoq">lecoq</a>
							<a class="puma" href="equipment/search/puma" title="puma">puma</a>
							<a class="lining" href="equipment/search/lining" title="lining">lining</a>
							<a class="anta" href="equipment/search/anta" title="anta">anta</a>
							<a class="mizuno" href="equipment/search/mizuno" title="mizuno">mizuno</a>
							<a class="umbro" href="equipment/search/umbro" title="umbro">umbro</a>
							<a class="thenorthface" href="equipment/search/thenorthface" title="the northface">the northface</a>
							<a class="knoway" href="equipment/search/knoway" title="knoway">knoway</a>
							<a class="avia" href="equipment/search/avia" title="avia">avia</a>
							<a class="crocs" href="equipment/search/crocs" title="crocs">crocs</a>
						</div>
					</div>

					<div class="mid_block">
						<div class="title">
							网羽品牌
						</div>
						<ul class="sort_detail">
							<li><a href="equipment/search/YONEX">尤尼克斯 YONEX</a></li>
							<li><a href="equipment/search/VICTOR">胜利 VICTOR </a></li>
							<li><a href="equipment/search/OLIVER">奥立弗 OLIVER </a></li>
							<li><a href="equipment/search/KASON">凯胜 KASON </a></li>
							<li><a href="equipment/search/RSL">亚狮龙 RSL </a></li>
							<li><a href="equipment/search/Kawasaki">川崎 Kawasaki </a></li>
							<li><a href="equipment/search/SOTX">索牌 SOTX</a></li>
							<li><a href="equipment/search/Wilson">威尔胜 Wilson </a></li>
							<li><a href="equipment/search/Tactic">泰迪 Tactic </a></li>
							<li><a href="equipment/search/Gosen">高神 Gosen </a></li>
							<li><a href="equipment/search/Alpha">阿尔法 Alpha </a></li>
							<li><a href="equipment/search/Kennex">肯尼士 Kennex </a></li>
							<li><a href="equipment/search/FLEET">富力特 FLEET </a></li>
							<li><a href="equipment/search/JOPPA">劲牌 JOPPA </a></li>
							<li><a href="equipment/search/Wish">伟士 Wish </a></li>
							<li><a href="equipment/search/Babolat">百宝力 Babolat </a></li>
							<li><a href="equipment/search/Apacs">雅拍 Apacs</a></li>
							<li><a href="equipment/search/Prince">王子 Prince </a></li>
							<li><a href="equipment/search/Toalson">杜力臣 Toalson </a></li>
							<li><a href="equipment/search/Joerex">祖迪斯 Joerex </a></li>
							<li><a href="equipment/search/MMOA">摩亚 MMOA </a></li>
							<li><a href="equipment/search/FLEX">佛雷斯 FLEX </a></li>
							<li><a href="equipment/search/Qiangli">强力 Qiangli</a></li>
							<li><a href="equipment/search/Bonny">波力 Bonny </a></li>
							<li><a href="equipment/search/Kimoni">金万利 Kimoni </a></li>
							<li><a href="equipment/search/DoubleFish">双鱼 DoubleFish </a></li>
							<li><a href="equipment/search/poona">普纳 poona </a></li>
							<li><a href="equipment/search/HANGYU">航宇 HANGYU </a></li>
							<li><a href="equipment/search/Aeroplane">航空 Aeroplane </a></li>
							<li><a href="equipment/search/Decathlon">迪卡侬 Decathlon  </a></li>
							<li><a href="equipment/search/DHS">红双喜 DHS  </a></li>
							<li><a href="equipment/search/ASICS">爱世克斯 ASICS  </a></li>
							<li><a href="equipment/search/Forten">华腾 Forten  </a></li>
							<li><a href="equipment/search/Swallaw">燕子 Swallaw </a></li>
						</ul>
					</div>

					<div class="mid_block">
						<div class="title">
							其他品牌
						</div><ul class="sort_detail">
							<li><a href="equipment/search/Tifafa">Tifafa </a></li>
							<li><a href="equipment/search/Raywow">Raywow </a></li>
							<li><a href="equipment/search/other"> 其它</a></li>
						</ul>
					</div>
				</div>


			</div>
			<div id="area_right">
				<div class="state" id="epcomment">
					<div class="titles" style="color: #f70">
						旺铺推荐
					</div>
					<div class="ew">
						<a class="rss" href="http://www.ctba.cn/group/shop-rank">MORE</a>
					</div>
					<div>
						<img src="images/nike-air-stab-id-21-mercer.jpg"
						  alt="nike air stab" />
						<span> Nike Air Stab iD @ 21 Mercer </span>
					</div>
				</div>
				<div class="state">
					<ul class="infobar">
						<li class="icons_feed">
							<a href="rss.shtml" target="_blank"><bean:message
								key="rss.feed" /> </a>
						</li>
					</ul>
					<hr size="1" color="#eeeeee" />
					<jsp:include page="../../common/_right_mini_block.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../../bottom.jsp" flush="true" />
	</body>
</html>