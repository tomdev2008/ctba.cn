package org.net9.group.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CustomizeUtils;

/**
 * Group Activity types
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("unchecked")
public abstract class ActivityTypeHelper {

	static Log log = LogFactory.getLog(ActivityTypeHelper.class);

	public static Map<Object, String> m = new HashMap<Object, String>();

	public static List<Map<String, Object>> typeList = new ArrayList<Map<String, Object>>();;

	static {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(CustomizeUtils.class.getClassLoader()
					.getResourceAsStream("activitytypes.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set s = properties.entrySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Entry et = (Entry) it.next();
			String key = (String) et.getKey();
			Object value = properties.get(key);
			Map<String, Object> typeMap = new HashMap<String, Object>();
			typeMap.put("activityType", key);
			typeMap.put("activityTypeCode", value);
			typeList.add(typeMap);
			m.put(value, key);
		}
	}
}
