package org.net9.core.config.menu;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.net9.common.util.DomXMLUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.service.DBAdminService;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.core.SysAdminGroup;

import com.google.inject.Guice;

/**
 * system menu builder
 * 
 * @author gladstone
 * 
 */
public class AdminMenuHandler {

	/** Logger */
	private final static Log log = LogFactory.getLog(AdminMenuHandler.class);

	/** DB admin service */
	static DBAdminService dbAdminService = Guice.createInjector(
			new ServiceModule()).getInstance(DBAdminService.class);

	/** config file name */
	static String configFileName = "menu_admin.xml";

	static final Integer TYPE_TOP = 1;

	static final Integer TYPE_SUB = 2;

	static {

	}

	/**
	 * build sub menu from an element
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static private MenuItem buildMenuItem(Element element) {
		MenuItem item = new MenuItem();
		String name = element.attributeValue("name");
		String link = element.attributeValue("link");
		String confirm = element.attributeValue("confirm");
		String role = element.attributeValue("role");
		item.setConfirm(confirm);
		item.setType(TYPE_SUB);
		item.setName(name);
		item.setRole(role);
		item.setLink(link);
		item.setChildren(new ArrayList<MenuItem>());
		List l = element.elements();
		for (int i = 0; i < l.size(); i++) {
			Element e = (Element) l.get(i);
			MenuItem subItem = buildMenuItem(e);
			item.getChildren().add(subItem);
		}
		return item;
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public static MenuItem buildRootMenuItem(String username) {
		log.debug("Got admin: " + username);
		MenuItem topItem = getTopItemsFromFile();
		if (dbAdminService.isSuperAdmin(username)) {
			return topItem;
		}
		List<SysAdminGroup> groups = dbAdminService.findAdminGroups(username);
		topItem = validateItem(groups, topItem);
		return topItem;
	}

	/**
	 * get all item from the config file
	 * 
	 * @return
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	public static MenuItem getTopItemsFromFile() {
		URL fileUrl = AdminMenuHandler.class.getClassLoader().getResource(
				configFileName);
		String filePath = fileUrl.getPath();
		filePath = URLDecoder.decode(filePath);
		Document doc = DomXMLUtils.load(filePath);
		log.debug("Got root element:" + doc.getRootElement().getName());
		Element menuElement = doc.getRootElement();
		MenuItem menu = new MenuItem();
		menu.setChildren(new ArrayList<MenuItem>());
		List l = menuElement.elements();
		for (int i = 0; i < l.size(); i++) {
			Element element = (Element) l.get(i);
			MenuItem item = buildMenuItem(element);
			item.setType(TYPE_TOP);
			menu.getChildren().add(item);
		}
		return menu;
	}

	/**
	 * validate menu item by admin groups
	 * 
	 * @param groups
	 * @param item
	 * @return
	 */
	public static MenuItem validateItem(List<SysAdminGroup> groups,
			MenuItem item) {
		boolean haveOption = false;
		if (StringUtils.isNotEmpty(item.getRole())) {
			for (SysAdminGroup group : groups) {
				if (item.getRole().equalsIgnoreCase(group.getOptionStr())) {
					// log.debug("OK:"+item.getRole()+">"+group.getOptionStr());
					haveOption = true;
				}
			}
		} else {
			haveOption = true;
		}
		if (!haveOption) {
			return null;
		}
		MenuItem reval = new MenuItem();
		reval.setChildren(new ArrayList<MenuItem>());
		if (item.getChildren() != null && item.getChildren().size() > 0) {
			for (MenuItem subItem : item.getChildren()) {
				MenuItem validatedItem = validateItem(groups, subItem);
				if (null != validatedItem) {
					log.debug("Alow:" + subItem.getName());
					reval.getChildren().add(validatedItem);
					validatedItem.setConfirm(subItem.getConfirm());
					validatedItem.setLink(subItem.getLink());
					validatedItem.setName(subItem.getName());
					validatedItem.setRole(subItem.getRole());
					validatedItem.setType(subItem.getType());
					// item.getChildren().remove(subItem);
				} else {
					// item.getChildren().remove(subItem);
					log.debug("Forbidden:" + subItem.getName());
				}
			}
		}
		return reval;
	}
}
