package org.net9.core.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.FileUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.image.ImageCutter;
import org.net9.common.util.image.JMagickImageCutter;
import org.net9.common.util.image.SimpleImageCutter;
import org.net9.core.constant.SystemConfigVars;

/**
 * 图片通用类，生成图片和缩略图等等
 * 
 * 不好意思，这个急，写的比较烂
 * 
 * image = image.getScaledInstance(w, h, Image.AreaAveragingScaleFilter); 图像缩放
 * 对于一个已经存在的Image对象，得到它的一个缩放的Image对象可以使用Image的getScaledInstance方法： Image
 * scaledImage=sourceImage. getScaledInstance(100,100,
 * Image.SCALE_DEFAULT);得到一个100X100的图像 Image doubledImage=sourceImage.
 * getScaledInstance(sourceImage.getWidth(this)*2,sourceImage.getHeight(this)*2,
 * Image.SCALE_DEFAULT);得到一个放大两倍的图像,这个程序一般在一个swing的组件中使用，而类Jcomponent实现了图像观察者接口ImageObserver，所有可以使用this。
 * 其它情况请参考API。
 * 
 * @author gladstone
 * 
 */
public class ImageUtils {
	static Log log = LogFactory.getLog(ImageUtils.class);

	public final static int IMAGE_HEIGHT_DEFAULT = 100;

	public final static int IMAGE_WIDTH_DEFAULT = 120;

	public final static int IMAGE_HEIGHT_MINI = 25;

	public final static int IMAGE_WIDTH_MINI = 30;

	public final static int IMAGE_HEIGHT_LARGE = 200;

	public final static int IMAGE_WIDTH_LARGE = 240;

	public static void checkDir(String filePath) {
		FileUtils.checkDir(filePath, false);
	}

	/**
	 * 根据时间得到文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String createImageName(String prefix, String fileName) {
		String reval = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
		if (fileName.indexOf(".") >= 0) {
			reval = sf.format(new Date())
					+ fileName.substring(fileName.lastIndexOf("."));
		} else {
			reval = sf.format(new Date());
		}
		if (CommonUtils.isNotEmpty(prefix)) {
			reval = prefix + "_" + reval;
		}
		return reval;
	}

	/**
	 * 根据时间得到文件名
	 * 
	 * @param originFilename
	 *            原先的文件名
	 * @return
	 * @throws Exception
	 */
	public static String wrapImageNameByTime(String originFilename)
			throws Exception {
		String reval = "";
		SimpleDateFormat sfFile = new SimpleDateFormat("HH_mm_ss_SSS");
		String timestampStr = sfFile.format(new Date());
		if (CommonUtils.isEmpty(originFilename)) {
			throw new Exception("OriginFilename can not be null!");
		}
		if (originFilename.indexOf(".") >= 0) {
			reval = StringUtils.getShorterStrWithoutSuffix(StringUtils
					.base64Encode(originFilename.substring(0, originFilename
							.lastIndexOf("."))), 20)
					+ "_"
					+ timestampStr
					+ originFilename.substring(originFilename.lastIndexOf("."));
		} else {
			reval = "_" + timestampStr;
		}

		reval = getDirNameByTime() + "/" + reval;
		return reval;
	}

	/**
	 * 生成缩略图，只是规定宽度
	 * 
	 * @param srcPath
	 * @param tarPath
	 * @param miniWidth
	 * @throws IOException
	 */
	private static void cutImage(String srcPath, String tarPath,
			Double miniWidth) throws IOException {
		cutImage(srcPath, tarPath, miniWidth, miniWidth);
		// // 拷贝文件
		// copyfile(srcPath, tarPath);
		// // 生成缩小图
		// File stadimgfile2 = new File(tarPath);
		// // 图片缓存
		// BufferedImage img2 = ImageIO.read(stadimgfile2);
		// // 得到图片的宽和高
		// double width = img2.getWidth();
		// double height = img2.getHeight();
		// int miniwidth = miniWidth.intValue();// 缩略图宽度
		// int miniheight = (int) (height * miniwidth / width);// 缩略图高度
		// double ratew = miniwidth / width;
		// double rateh = miniheight / height;
		// // 获得适合的缩放比率，即以在规定缩略尺寸中完整显示图片内容的同时又保证最大的缩放比率
		// double rate = Math.min(ratew, rateh);
		// rate = (Math.rint((rate * 100) + 0.5)) / 100;
		// BufferedImage imgmini = new java.awt.image.BufferedImage(miniwidth,
		// miniheight, BufferedImage.SCALE_SMOOTH);
		// Graphics2D gmini = imgmini.createGraphics();
		// gmini.setBackground(Color.WHITE);
		// gmini.clearRect(0, 0, miniwidth, miniheight);
		// AffineTransform trans = new AffineTransform();
		// trans.scale(rate, rate);
		// AffineTransformOp op = new AffineTransformOp(trans,
		// AffineTransformOp.TYPE_BILINEAR);
		// gmini.drawImage(img2, op, (int) (miniwidth - (width * rate)) / 2,
		// (int) (miniheight - (height * rate)) / 2);
		// ImageIO.write(imgmini, "jpg", stadimgfile2);
	}

	/**
	 * 生成缩略图
	 * 
	 * @param srcPath
	 *            源文件
	 * @param tarPath
	 *            目标文件
	 * @param miniWidth
	 *            宽度
	 * @param miniHeight
	 *            高度
	 * @throws IOException
	 */
	private static void cutImage(String srcPath, String tarPath,
			Double miniWidth, Double miniHeight) throws IOException {
		checkDir(tarPath);
		// System.out.println(srcPath);
		ImageCutter cutter = new JMagickImageCutter();
		try {
			cutter.getImage(srcPath, tarPath, miniWidth, miniHeight);
		} catch (Error e) {
			log.warn("no JMagick in java.library.path: " + e.getMessage());
			cutter = new SimpleImageCutter();
			try {
				cutter.getImage(srcPath, tarPath, miniWidth, miniHeight);
			} catch (Exception e1) {
				log.error(e1.getMessage());
				e1.printStackTrace();
			}
		} catch (Exception e) {
			log.warn("jmagick lib err: " + e.getMessage());
			cutter = new SimpleImageCutter();
			try {
				cutter.getImage(srcPath, tarPath, miniWidth, miniHeight);
			} catch (Exception e1) {
				log.error(e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 得到默认宽度图片
	 * 
	 * @param srcPath
	 * @param rateIt
	 *            是否按比例缩放
	 * @return
	 * @throws IOException
	 */
	public static String getDefaultImage(String srcPath, boolean rateIt)
			throws IOException {
		if (CommonUtils.isEmpty(srcPath))
			return "";
		String reval = srcPath;
		// if (reval.indexOf(".") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(".")) + "_default"
		// + reval.substring(reval.lastIndexOf("."));
		// }
		// if (srcPath.indexOf(File.separator) >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(File.separator))
		// + File.separator + "default"
		// + reval.substring(reval.lastIndexOf(File.separator));
		// log.debug(srcPath + " " + reval);
		// }

		if (srcPath.indexOf(SystemConfigVars.IMG_DIR_UPLOAD) >= 0) {
			reval = reval.substring(0, reval
					.indexOf(SystemConfigVars.IMG_DIR_UPLOAD))
					+ SystemConfigVars.IMG_DIR_DEFAULT
					+ reval.substring(reval
							.indexOf(SystemConfigVars.IMG_DIR_UPLOAD)
							+ SystemConfigVars.IMG_DIR_UPLOAD.length());
			log.debug(srcPath + "  " + reval);
		}

		if (rateIt)
			cutImage(srcPath, reval, new Double(IMAGE_WIDTH_DEFAULT),
					new Double(IMAGE_HEIGHT_DEFAULT));
		else
			cutImage(srcPath, reval, new Double(IMAGE_WIDTH_DEFAULT));
		return reval;
	}

	public static String getDefaultImageStr(String srcPath) {
		if (CommonUtils.isEmpty(srcPath))
			return "";
		String reval = srcPath;
		// if (reval.indexOf(".") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(".")) + "_default"
		// + reval.substring(reval.lastIndexOf("."));
		// }
		// if (srcPath.indexOf("\\") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf("\\")) + "\\default"
		// + reval.substring(reval.lastIndexOf("\\"));
		// log.debug(srcPath + " " + reval);
		// }
		reval = "default/" + reval;
		return reval;
	}

	public static String getDirNameByTime() {
		String reval = "";
		SimpleDateFormat sfDir = new SimpleDateFormat("yyyy-MM-dd");
		reval = sfDir.format(new Date());
		return reval;
	}

	/**
	 * 得到缩略图
	 * 
	 * @param srcPath
	 * @param rateIt
	 *            是否按比例缩放
	 * @return
	 * @throws IOException
	 */
	public static String getMiniImage(String srcPath, boolean rateIt)
			throws IOException {
		String reval = srcPath;
		// if (reval.indexOf(".") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(".")) + "_mini"
		// + reval.substring(reval.lastIndexOf("."));
		// }
		// if (reval.indexOf(".") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(".")) + "_mini"
		// + reval.substring(reval.lastIndexOf("."));
		// }

		// if (srcPath.indexOf(File.separator) >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(File.separator))
		// + File.separator + "mini"
		// + reval.substring(reval.lastIndexOf(File.separator));
		// log.debug(srcPath + " " + reval);
		// }
		if (srcPath.indexOf(SystemConfigVars.IMG_DIR_UPLOAD) >= 0) {
			reval = reval.substring(0, reval
					.indexOf(SystemConfigVars.IMG_DIR_UPLOAD))
					+ SystemConfigVars.IMG_DIR_MINI
					+ reval.substring(reval
							.indexOf(SystemConfigVars.IMG_DIR_UPLOAD)
							+ SystemConfigVars.IMG_DIR_UPLOAD.length());
			log.debug(srcPath + "  " + reval);
		}
		log.debug(srcPath + "  " + reval);
		if (rateIt)
			cutImage(srcPath, reval, new Double(IMAGE_WIDTH_MINI), new Double(
					IMAGE_HEIGHT_MINI));
		else
			cutImage(srcPath, reval, new Double(IMAGE_WIDTH_MINI));
		return reval;
	}

	/**
	 * 
	 * @param srcPath
	 * @return
	 * @throws IOException
	 */
	public static String getMiniImageStr(String srcPath) {
		if (CommonUtils.isEmpty(srcPath))
			return "";
		String reval = srcPath;
		// if (reval.indexOf(".") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(".")) + "_mini"
		// + reval.substring(reval.lastIndexOf("."));
		// }
		// if (srcPath.indexOf("\\") >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf("\\")) + "\\mini"
		// + reval.substring(reval.lastIndexOf("\\"));
		// log.debug(srcPath + " " + reval);
		// }
		reval = "mini/" + reval;
		return reval;
	}

	/**
	 * 根据宽度得到图片
	 * 
	 * @param srcPath
	 * @param width
	 * @return
	 * @throws IOException
	 */
	public static String getSizedImage(String srcPath, Integer width)
			throws IOException {
		if (CommonUtils.isEmpty(srcPath))
			return "";
		String reval = srcPath;

		// if (srcPath.indexOf(File.separator) >= 0) {
		// reval = reval.substring(0, reval.lastIndexOf(File.separator))
		// + File.separator + "sized"
		// + reval.substring(reval.lastIndexOf(File.separator));
		// log.debug(srcPath + " " + reval);
		// }
		if (srcPath.indexOf(SystemConfigVars.IMG_DIR_UPLOAD) >= 0) {
			reval = reval.substring(0, reval
					.indexOf(SystemConfigVars.IMG_DIR_UPLOAD))
					+ SystemConfigVars.IMG_DIR_SIZED
					+ reval.substring(reval
							.indexOf(SystemConfigVars.IMG_DIR_UPLOAD)
							+ SystemConfigVars.IMG_DIR_UPLOAD.length());
			log.debug(srcPath + "  " + reval);
		}

		if (reval.indexOf(".") >= 0) {
			reval = reval.substring(0, reval.lastIndexOf(".")) + "_" + width
					+ reval.substring(reval.lastIndexOf("."));
			log.debug(srcPath + "  " + reval);
		}
		// no rating
		cutImage(srcPath, reval, new Double(width), new Double(width));
		return reval;
	}

	public static String getSizedImageStr(String srcPath, Integer width) {
		if (CommonUtils.isEmpty(srcPath))
			return "images/nobody.png";
		String reval = srcPath;
		// log.debug(File.separator + " "
		// + ((srcPath.indexOf(File.separator) >= 0)) + ">>>>>>" + srcPath
		// + " " + reval);
		if (srcPath.indexOf(SystemConfigVars.IMG_DIR_UPLOAD) >= 0) {
			reval = reval.substring(0, reval
					.indexOf(SystemConfigVars.IMG_DIR_UPLOAD))
					+ SystemConfigVars.IMG_DIR_SIZED
					+ reval.substring(reval
							.indexOf(SystemConfigVars.IMG_DIR_UPLOAD)
							+ SystemConfigVars.IMG_DIR_UPLOAD.length());
			log.debug(srcPath + "  " + reval);
		}
		if (reval.indexOf(".") >= 0) {
			reval = reval.substring(0, reval.lastIndexOf(".")) + "_" + width
					+ reval.substring(reval.lastIndexOf("."));
		}
		return reval;
	}

	// public static void main(String[] args) throws IOException {
	// String pathS = "C:\\Program Files\\Apache Software Foundation\\Tomcat
	// 6.0\\webapps\\ctba\\UploadFile\\2007-12-11\\MTg2MQ==_18_46_19_575.jpg";
	// String pathT = "C:\\Program Files\\Apache Software Foundation\\Tomcat
	// 6.0\\webapps\\ctba\\UploadFile/default\\2007-12-11\\MTg2MQ==_18_46_19_575.jpg";
	// getImageWIthJimi(pathS, pathT, new Double(80), new Double(80));
	// }

}
