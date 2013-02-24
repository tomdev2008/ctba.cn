package cn.ctba.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.net9.common.util.StringUtils;

/**
 * 目前主要做
 * <li>优先级1： 球鞋,网羽,
 * <li>优先级2： 服饰 和数码
 * 
 * @author gladstone
 * @since May 20, 2009
 */
public class EquipmentConstant {

	public static List<EquipmentType> equipmentTypeList;

	public static Map<Integer, Object> equipmentTypeMap;
	public static Map<Integer, String> equipmentStateTypeMap;
	public static Map<Integer, Object> brandMap;
	public static List<EquipmentBrandType> brandList = new ArrayList<EquipmentBrandType>();
	// TODO: change this!
	// public static String[] equipmentTypeArray = new String[] { "篮球鞋", "羽毛球鞋",
	// "足球鞋", "训练鞋", "休闲鞋", "网球鞋", "球服", "包类", "运动护具", "T恤", "短裤", "潮流服饰",
	// "数码产品", "家居用品", "艺术收藏", "篮球", "羽毛球拍", "羽毛球/羽毛球用品", "乒乓球用品", "网球用品",
	// "户外服装", "瑜伽用品", "其他" };
	public static String[] equipmentTypeArray = new String[] { "篮球鞋", "羽毛球鞋",
			"足球鞋", "训练鞋", "休闲鞋", "网球鞋", "球服", "包类", "运动护具", "T恤", "短裤", "潮流服饰",
			"数码产品", "家居用品", "艺术收藏", "篮球", "羽毛球拍", "羽毛球/羽毛球用品", "乒乓球用品", "网球用品",
			"户外服装", "其他" };
	static {
		prepareTypeList();
		prepareStateTypeList();
		prepareBrandList();
	}

	private static void prepareBrand(int i, String name, String alias) {
		EquipmentBrandType model = new EquipmentBrandType();
		model.setIndex(i);
		model.setName(name);
		if (StringUtils.isNotEmpty(alias)) {
			model.setAlias(alias);
		} else {
			model.setAlias(name);
		}
		brandList.add(model);
		brandMap.put(i, model);
	}

	private static void prepareBrandList() {
		brandList = new ArrayList<EquipmentBrandType>();
		brandMap = new HashMap<Integer, Object>();
		prepareBrand(1, "nike", "耐克");
		prepareBrand(2, "adidas", "阿迪达斯");
		prepareBrand(3, "adidasoriginal", "三叶草");
		prepareBrand(4, "adidasstyle", "adidasstyle");
		prepareBrand(5, "converse", "匡威");
		prepareBrand(11, "lining", "李宁");
		prepareBrand(6, "newbalance", "newbalance");
		prepareBrand(7, "kappa", "kappa");
		prepareBrand(8, "lecoq", "lecoq");
		prepareBrand(9, "puma", "puma");
		prepareBrand(12, "anta", "安踏");
		prepareBrand(13, "mizuno", "mizuno");
		prepareBrand(14, "umbro", "umbro");
		prepareBrand(15, "thenorthface", "the northface");
		prepareBrand(16, "knoway", "knoway");
		prepareBrand(17, "avia", "avia");
		prepareBrand(18, "crocs", "crocs");
		prepareBrand(19, "Reebok", "锐步");

		prepareBrand(101, "YONEX", "尤尼克斯");
		prepareBrand(102, "VICTOR", "胜利");
		prepareBrand(103, "OLIVER", "奥立弗");
		prepareBrand(104, "KASON", "凯胜");
		prepareBrand(105, "RSL", "亚狮龙");
		prepareBrand(106, "Kawasaki", "川崎");
		prepareBrand(107, "SOTX", "索牌");
		prepareBrand(108, "Wilson", "威尔胜");
		prepareBrand(109, "Kimoni", "金万利");
		prepareBrand(110, "Qiangli", "强力");
		prepareBrand(111, "Bonny", "波力");
		prepareBrand(112, "MMOA", "摩亚");
		prepareBrand(113, "FLEX", "佛雷斯");
		prepareBrand(114, "Joerex", "祖迪斯");
		prepareBrand(115, "Toalson", "杜力臣");
		prepareBrand(116, "Prince", "王子");
		prepareBrand(117, "Apacs", "雅拍");
		prepareBrand(118, "Babolat", "百宝力");
		prepareBrand(119, "Wish", "伟士");
		prepareBrand(120, "JOPPA", "劲牌");
		prepareBrand(121, "FLEET", "富力特");
		prepareBrand(122, "Kennex", "肯尼士");
		prepareBrand(123, "Alpha", "阿尔法");
		prepareBrand(124, "Gosen", "高神");
		prepareBrand(125, "Tactic", "泰迪");
		prepareBrand(126, "Swallaw", "燕子");
		prepareBrand(127, "Forten", "华腾");
		prepareBrand(128, "DHS", "红双喜");
		prepareBrand(129, "ASICS", "爱世克斯");
		prepareBrand(130, "Decathlon", "迪卡侬");
		prepareBrand(131, "Aeroplane", "航空");
		prepareBrand(132, "poona", "普纳");
		prepareBrand(133, "HANGYU", "航宇");
		prepareBrand(134, "DoubleFish", "双鱼");

		prepareBrand(135, "feiyue", "飞跃");
		prepareBrand(136, "Jasper", "捷士勃");
		prepareBrand(137, "Butterfly", "蝴蝶");
		prepareBrand(138, "KingCamp", "康尔健野");
		prepareBrand(139, "SCALER", "思凯乐");
		prepareBrand(140, "Toff", "袋鼠");
		prepareBrand(141, "Yasaka", "亚萨卡");
		prepareBrand(142, "Teloon", "天龙");
		prepareBrand(143, "Dunlop", "邓禄普");
		prepareBrand(144, "STAR", "世达");
		prepareBrand(145, "doublestar", "双星");
		prepareBrand(146, "jordan", "乔丹");
		prepareBrand(147, "spalding", "斯伯丁");
		prepareBrand(-1, "", "其他");

	}

	private static void prepareStateTypeList() {
		equipmentStateTypeMap = new HashMap<Integer, String>();
		equipmentStateTypeMap
				.put(EquipmentStateType.COMMENDED.getValue(), "推荐");
		equipmentStateTypeMap.put(EquipmentStateType.OK.getValue(), "通过");
		equipmentStateTypeMap.put(EquipmentStateType.WAITING.getValue(), "等待中");

	}

	private static void prepareType(int i, String type) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("index", i);
		m.put("name", type);
		equipmentTypeMap.put(i, type);
		EquipmentType model = new EquipmentType();
		model.setIndex(i);
		model.setName(type);
		equipmentTypeList.add(model);

	}

	private static void prepareTypeList() {
		equipmentTypeList = new ArrayList<EquipmentType>();
		equipmentTypeMap = new HashMap<Integer, Object>();
		int i = 0;
		for (String type : equipmentTypeArray) {
			i++;
			prepareType(i, type);
		}
	}
}
