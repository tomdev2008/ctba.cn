package org.net9.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.net9.common.util.StringUtils;

import com.google.inject.servlet.SessionScoped;

/**
 * user service,with the file ,not the db
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class DefaultFileAdminService extends BaseService {
	private static final long serialVersionUID = 4751395812539357612L;

	private static String propertiesFile = "admins.properties";

	private static Map<Object, Object> adminMap;

	private static DefaultFileAdminService instance = null;

	/**
	 * return a singleton
	 * 
	 * @return
	 */
	public static DefaultFileAdminService getInstance() {
		if (instance == null) {
			instance = new DefaultFileAdminService();
		}
		return instance;
	}

	/**
	 * constructor, initialize the map
	 * 
	 */
	@SuppressWarnings("unchecked")
	public DefaultFileAdminService() {
		adminMap = new HashMap<Object, Object>();
		Properties props = new Properties();
		InputStream in = DefaultFileAdminService.class.getClassLoader()
				.getResourceAsStream(propertiesFile);
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration e = props.propertyNames();
		String username;
		String password;
		while (e.hasMoreElements()) {
			username = (String) e.nextElement();
			password = props.getProperty(username);
			adminMap.put(username, password);
		}
	}

	public boolean authAdmin(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}
		return password.equals(adminMap.get(username));
	}

	public String getAdminRole(String username) {
		return "admin";
	}

	@SuppressWarnings("unchecked")
	public List<String> listAdmins(Integer start, Integer limit) {
		List<String> list = new ArrayList<String>();
		if (adminMap == null) {
			return list;
		}
		Set set = adminMap.entrySet();
		Iterator it = set.iterator();
		for (String username = null; it.hasNext(); username = (String) ((Entry) it
				.next()).getKey()) {
			list.add(username);
		}
		return list;
	}

}
