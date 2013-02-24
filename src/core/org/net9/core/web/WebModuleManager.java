package org.net9.core.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.ClassLoaderUtils;
import org.net9.common.util.ClassUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.web.servlet.BusinessCommonServlet;

/**
 * web模块管理器，负责在系统初始化的时候装载web模块
 * 
 * @author gladstone
 * @since Mar 19, 2009
 */
public class WebModuleManager {

	private static Log log = LogFactory.getLog(WebModuleManager.class);

	/** 保存转发列表 */
	@SuppressWarnings("unchecked")
	private static final Map actions = new HashMap();

	private String actionMappingFileName = DEFAULT_CONFIG_FILE;

	/**
	 * 初始化函数
	 * 
	 * @param actionMappingFileName
	 *            如果为空，使用 web_servlets.properties
	 */
	public WebModuleManager(String actionMappingFileName) {
		if (StringUtils.isNotEmpty(actionMappingFileName)) {
			this.actionMappingFileName = actionMappingFileName;
		}
		init();
	}

	/**
	 * 根据服务名得到实际的servlet
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BusinessCommonServlet getAction(String name) throws Exception {
		if (actions.containsKey(name)) {
			Class c = getClass().getClassLoader().loadClass(
					((String) actions.get(name)).trim());
			BusinessCommonServlet action = (BusinessCommonServlet) c
					.newInstance();
			return action;
		} else {
			return null;
		}
	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		try {
			// mapping
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					actionMappingFileName);
			Properties props = new Properties();
			props.load(in);

			// and store them in a HashMap
			Enumeration e = props.propertyNames();
			String actionName;
			String className;

			while (e.hasMoreElements()) {
				actionName = (String) e.nextElement();
				className = props.getProperty(actionName);
				actions.put(actionName, className);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/** 默认的wervlet配置文件 */
	public static final String DEFAULT_CONFIG_FILE = "web_servlets.properties";

	/**
	 * 扫描类路径，装入web模块
	 */
	public static void scanWebModules() {
		log.info("Scanning web modules...");
		scanWebModulesByDefinedPackages();
		scanWebModuleByDefinedJarFiles();
		log.info("Totally, " + actions.size()
				+ " module classes loaded by system");
	}

	@SuppressWarnings("unchecked")
	private static void scanWebModulesByDefinedPackages() {

		if (StringUtils.isEmpty(SystemConfigVars.BASE_PACKAGE_PREFIXS)) {
			log.warn("Base packages not defined!");
			return;
		}
		String[] packages = SystemConfigVars.BASE_PACKAGE_PREFIXS.split(",");
		for (String basePackagePrefix : packages) {
			basePackagePrefix = basePackagePrefix.trim();
			URL url = WebModuleManager.class.getClassLoader().getResource(
					ClassUtils
							.convertClassNameToResourcePath(basePackagePrefix));

			if (url == null) {
				log
						.warn("No base web module package found:"
								+ ClassUtils
										.convertClassNameToResourcePath(basePackagePrefix));
				continue;
			}
			try {
				if (ClassUtils.isInJarFile(url)) {
					log.info("Scanning base package [" + basePackagePrefix
							+ "] in jar file : " + url.toString());
					initWebModulePathInJarFile(url, basePackagePrefix);
				} else {
					log.info("Scanning base package [" + basePackagePrefix
							+ "] in file system : " + url.toString());
					initWebModulePathInFileSystem(url.getFile(),
							basePackagePrefix);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			List<Class> classList = ClassLoaderUtils
					.getClassesLoadedByClassLoader(WebModuleManager.class
							.getClassLoader());
			for (int i = 0; i < classList.size(); i++) {
				try {
					Class clazz = classList.get(i);
					checkWebModule(clazz, basePackagePrefix);
				} catch (Exception e) {
					log.error("Error checking class: " + e.getMessage());
				}
			}
		}

	}

	/**
	 * 检查单独的class，看看是否web模块
	 * 
	 * @param clazz
	 * @param basePackagePrefix
	 */
	@SuppressWarnings("unchecked")
	private static void checkWebModule(Class clazz, String basePackagePrefix) {
		if (clazz != null && StringUtils.isNotEmpty(clazz.getCanonicalName())) {
			if (StringUtils.isNotEmpty(basePackagePrefix)
					&& !clazz.getCanonicalName().startsWith(basePackagePrefix)) {
				return;
			}
			log.debug("Check class: " + clazz.getCanonicalName());
			if (clazz.isAnnotationPresent(WebModule.class)) {
				WebModule w = (WebModule) clazz.getAnnotation(WebModule.class);
				log.debug("Load web module : " + clazz.getCanonicalName()
						+ " (" + w.value() + ")");
				actions.put(w.value(), clazz.getCanonicalName());
			}
		}
	}

	/**
	 * 扫描事实上存在的文件夹，而不是jar文件
	 * 
	 * @param dir
	 *            当前文件夹
	 * @param basePackagePrefix
	 *            基本包名
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private static void initWebModulePathInFileSystem(String dir,
			String basePackagePrefix) throws Exception {
		File f = new File(dir);
		if (f.isDirectory()) {
			for (File ff : f.listFiles()) {
				initWebModulePathInFileSystem(ff.getCanonicalPath(),
						basePackagePrefix);
			}
		} else {
			if (f.getCanonicalPath().endsWith(ClassUtils.CLASS_FILE_SUFFIX)) {
				WebModuleManager.class.getClassLoader().loadClass(
						ClassUtils.getClassCanonicalNameFromResourcePath(
								basePackagePrefix, f.getCanonicalPath()));
			}
		}

	}

	/**
	 * 扫描jar文件里面的文件夹
	 * 
	 * @param url
	 * @param basePackagePrefix
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void initWebModulePathInJarFile(URL url,
			String basePackagePrefix) throws Exception {
		log.info("Jar filename: " + ClassUtils.getJarFileName(url));
		JarURLConnection con = (JarURLConnection) url.openConnection();
		JarFile jarFile = con.getJarFile();
		// JarFile jarFile = new JarFile(ClassUtils.getJarFileName(url));
		Enumeration<JarEntry> entryEnum = jarFile.entries();
		while (entryEnum.hasMoreElements()) {
			JarEntry entry = entryEnum.nextElement();
			if (!entry.isDirectory()
					&& entry.getName().endsWith(ClassUtils.CLASS_FILE_SUFFIX)) {
				ClassUtils.getDefaultClassLoader().loadClass(
						ClassUtils.getClassCanonicalNameFromResourcePath(
								basePackagePrefix, entry.getName()));
			}
		}
	}

	/**
	 * 扫描jar包
	 */
	@SuppressWarnings("unchecked")
	private static void scanWebModuleByDefinedJarFiles() {
		if (StringUtils.isEmpty(SystemConfigVars.SCANNABLE_JARS)) {
			log.info("No jar file to scan defined");
			return;
		}
		try {
			String[] jarFilenames = SystemConfigVars.SCANNABLE_JARS.split(",");
			for (String jarFilename : jarFilenames) {
				jarFilename = SystemConfigVars.REAL_CONTEXT + "/WEB-INF/lib/"
						+ jarFilename;
				log.info("Scanning jar: " + jarFilename);
				// JarURLConnection con = (JarURLConnection)
				// url.openConnection();
				// JarFile jarFile = con.getJarFile();
				JarFile jarFile;
				jarFile = new JarFile(jarFilename);
				Enumeration<JarEntry> entryEnum = jarFile.entries();
				while (entryEnum.hasMoreElements()) {
					JarEntry entry = entryEnum.nextElement();
					if (!entry.isDirectory()
							&& entry.getName().endsWith(
									ClassUtils.CLASS_FILE_SUFFIX)) {
						String className = entry.getName().replace("\\", "/");
						className = ClassUtils
								.convertResourcePathToClassName(className);

						if (className.endsWith(ClassUtils.CLASS_FILE_SUFFIX)) {
							className = className.substring(0, className
									.length()
									- ClassUtils.CLASS_FILE_SUFFIX.length());
						}
						try {
							// System.out.println(className);
							WebModuleManager.class.getClassLoader().loadClass(
									className);
						} catch (java.lang.NoClassDefFoundError e) {
							log.error(e.getMessage());
						}

					}
				}
			}
		} catch (IOException e) {
			log.warn(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		List<Class> classList = ClassLoaderUtils
				.getClassesLoadedByClassLoader(WebModuleManager.class
						.getClassLoader());
		for (int i = 0; i < classList.size(); i++) {
			try {
				Class clazz = classList.get(i);
				checkWebModule(clazz, null);
			} catch (Exception e) {
				log.error("Error checking class: " + e.getMessage());
			}
		}
	}

}