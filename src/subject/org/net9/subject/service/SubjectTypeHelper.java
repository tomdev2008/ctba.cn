package org.net9.subject.service;

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

/**
 * subject types
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("unchecked")
public class SubjectTypeHelper {
	static Log log = LogFactory.getLog(SubjectTypeHelper.class);

	public static List typeList = new ArrayList();;

	static {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(SubjectTypeHelper.class.getClassLoader()
					.getResourceAsStream("subjecttypes.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set s = properties.entrySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Entry et = (Entry) it.next();
			String key = (String) et.getKey();
			Object value = properties.get(key);
			Map typeMap = new HashMap();
			typeMap.put("subjectType", key);
			typeMap.put("subjectTypeCode", value);
			typeList.add(typeMap);
		}
	}
}
