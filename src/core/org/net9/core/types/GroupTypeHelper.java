package org.net9.core.types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;

/**
 * group types helper
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("unchecked")
public class GroupTypeHelper {

	static Log log = LogFactory.getLog(GroupTypeHelper.class);

	/** Map<代码(序号),POJO> */
	public static Map<Object, GroupType> typeMap = new HashMap<Object, GroupType>();

	/** List<Map>,POJO列表 */
	public static List<GroupType> typeList = new ArrayList<GroupType>();;

	static {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(GroupTypeHelper.class.getClassLoader()
					.getResourceAsStream("grouptypes.xml"));
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		for (Entry et : properties.entrySet()) {
			String key = (String) et.getKey();
			String value = (String) properties.get(key);
			GroupType model = new GroupType();
			int index = typeList.size();
			if (key.indexOf(":") > 0) {
				index = Integer.valueOf(key.split(":")[0]);
				key = key.split(":")[1];
			}
			model.setIndex(index);
			model.setName(key);
			if (StringUtils.isNotEmpty(value)) {
				model.setTags(value.split(";"));
			}
			typeMap.put(index, model);
			typeList.add(model);
			index++;
		}
		Collections.sort(typeList);
	}
}
