package org.net9.domain.dao;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;

import org.dom4j.Element;
import org.net9.common.util.DomXMLUtils;
import org.net9.common.util.StringUtils;

public class JpaDataBaseConfig {

	static final String CONFIG_FILE = "META-INF/persistence.xml";

	static final String OPENJPA_URL = "openjpa.ConnectionURL";

	static final String OPENJPA_USERNAME = "openjpa.ConnectionUserName";

	static final String OPENJPA_DRIVER = "openjpa.ConnectionDriverName";

	static final String OPENJPA_PASSWORD = "openjpa.ConnectionPassword";

	static final String TOPLINK_URL = "toplink.jdbc.url";

	static final String TOPLINK_USERNAME = "toplink.jdbc.user";

	static final String TOPLINK_DRIVER = "toplink.jdbc.driver";

	static final String TOPLINK_PASSWORD = "toplink.jdbc.password";

	public static String jdbcUrl;

	public static String ormType;

	public static String jdbcDriver;

	public static String jdbcUsername;

	public static String jdbcPassword;

	static {
		parseConfigFile(EntityManagerHelper.PERSISTANCE_UNIT_TEST);
	}

	static private String getProperty(Element element) {
		String reval = element.getTextTrim();
		if (StringUtils.isEmpty(reval)) {
			reval = element.attributeValue("value");
		}
		return reval;
	}

	@SuppressWarnings( { "deprecation", "unchecked" })
	public static void parseConfigFile(String unitName) {
		ormType = "openjpa";

		URL fileUrl = JpaDataBaseConfig.class.getClassLoader().getResource(
				CONFIG_FILE);
		String filePath = fileUrl.getPath();
		filePath = URLDecoder.decode(filePath);
		org.dom4j.Document doc = DomXMLUtils.load(filePath);
		Element propertiesElement = null;
		// = doc.getRootElement().element(
		// "persistence-unit").element("properties");

		Element providerElement = null;
		// = doc.getRootElement().element(
		// "persistence-unit").element("provider");

		List<Element> eList = doc.getRootElement().elements("persistence-unit");
		for (Element e : eList) {
			// System.out.println(e.attributeValue("name"));
			if (e.attributeValue("name").equals(
					unitName)) {
				propertiesElement = e.element("properties");
				providerElement = e.element("provider");
			}
		}
		if (providerElement == null || propertiesElement == null) {
			return;
		}
		if (providerElement.getText().indexOf("toplink") > 0) {
			ormType = "toplink";
		}

		List l = propertiesElement.elements();
		for (int i = 0; i < l.size(); i++) {
			Element element = (Element) l.get(i);
			String attrVal = element.attributeValue("name");
			if (OPENJPA_URL.equals(attrVal) || TOPLINK_URL.equals(attrVal)) {
				jdbcUrl = getProperty(element);
			} else if (OPENJPA_USERNAME.equals(attrVal)
					|| TOPLINK_USERNAME.equals(attrVal)) {
				jdbcUsername = getProperty(element);
			} else if (OPENJPA_DRIVER.equals(attrVal)
					|| TOPLINK_DRIVER.equals(attrVal)) {
				jdbcDriver = getProperty(element);
			} else if (OPENJPA_PASSWORD.equals(attrVal)
					|| TOPLINK_PASSWORD.equals(attrVal)) {
				jdbcPassword = getProperty(element);
			}
		}
	}

	/**
	 * 
	 * Sample:
	 * 
	 * toplink.jdbc.driver=com.mysql.jdbc.Driver<br>
	 * toplink.jdbc.url=jdbc:mysql://localhost/j2bb_test_ctba<br>
	 * toplink.jdbc.user=root <br>
	 * toplink.jdbc.password=root<br>
	 * 
	 * @param p
	 */
	public static void setPeoperties(Properties p) {
		if (ormType.equals("toplink")) {
			jdbcUrl = p.getProperty(TOPLINK_URL);
			jdbcUsername = p.getProperty(TOPLINK_USERNAME);
			jdbcDriver = p.getProperty(TOPLINK_DRIVER);
			jdbcPassword = p.getProperty(TOPLINK_PASSWORD);
		}
	}

//	public static void main(String[] args) {
//		TestDataBaseConfig.parseConfigFile();
//		System.out.println(TestDataBaseConfig.jdbcDriver);
//		System.out.println(TestDataBaseConfig.jdbcUrl);
//		System.out.println(TestDataBaseConfig.jdbcUsername);
//		System.out.println(TestDataBaseConfig.jdbcPassword);
//	}
}
