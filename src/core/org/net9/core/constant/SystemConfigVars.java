package org.net9.core.constant;

import org.net9.common.util.SystemConfigUtils;

/**
 * 写在文件里面的系统配置
 * 
 * @author gladstone
 * @since Mar 19, 2009
 */
public class SystemConfigVars {

	/** 应用的真实文件路径 */
	public final static String REAL_CONTEXT = SystemConfigUtils
			.getProperty("common.context.root");

	/** 文件上传路径 */
	public final static String UPLOAD_DIR_PATH = SystemConfigUtils
			.getProperty("common.upload.dir");

	/** cookie或者session */
	public final static String USER_AUTH_TYPE = SystemConfigUtils
			.getProperty("user.auth.type");

	/** 默认扫描基本包名列表 */
	public static final String BASE_PACKAGE_PREFIXS = SystemConfigUtils
			.getProperty("webmodule.base.package");

	/** 默认扫描JAR包 */
	public static final String SCANNABLE_JARS = SystemConfigUtils
			.getProperty("webmodule.scannable");

	public static final boolean ATTACH_VIEW_ALLOW_GUEST = SystemConfigUtils
			.getBoolean("common.attach.view.guest");
	
	public static final boolean DB_CACHE_USE_MEMCACHE = SystemConfigUtils
			.getBoolean("db.cache.usememcache");

	/** 图片的上传路径 */
	public final static String IMG_DIR_UPLOAD = UPLOAD_DIR_PATH;

	public final static String IMG_DIR_DEFAULT = IMG_DIR_UPLOAD + "/"
			+ "default";

	public final static String IMG_DIR_SIZED = IMG_DIR_UPLOAD + "/" + "sized";

	public final static String IMG_DIR_MINI = IMG_DIR_UPLOAD + "/" + "mini";

}
