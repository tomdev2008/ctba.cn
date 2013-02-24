package cn.ctba.people;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeopleConstant {

	public static List<PeopleType> peopleTypeList;

	public static Map<Integer, Object> peopleTypeMap;

	public static String[] equipmentTypeArray = new String[] { "篮球明星", "足球明星",
			"其他体育明星", "教练", "宅男", "歌星", "影星", "美女", "摄影师", "导演", "作家", "神",
			"民族英雄", "知名专家", "写手/编辑", "大老板", "其他" };
	static {
		peopleTypeList = new ArrayList<PeopleType>();
		peopleTypeMap = new HashMap<Integer, Object>();
		int i = 0;
		for (String type : equipmentTypeArray) {
			i++;
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("index", i);
			m.put("name", type);
			peopleTypeMap.put(i, type);
			PeopleType model = new PeopleType();
			model.setIndex(i);
			model.setName(type);
			peopleTypeList.add(model);
		}
	}
}
