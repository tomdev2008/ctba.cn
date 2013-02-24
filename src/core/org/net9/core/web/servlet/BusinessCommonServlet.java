package org.net9.core.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.blog.service.CommentService;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.FileUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.group.service.GroupExtService;

import com.google.inject.Inject;
import com.oreilly.servlet.MultipartRequest;

/**
 * 基本业务servlet，所有action都继承这个类
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
public abstract class BusinessCommonServlet extends BusinessDispatchAction {

	/**
	 * 默认的处理方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			CommonSystemException {
	}

	// @Inject
	// protected VoteService voteService;
	//
	// @Inject
	// protected DefaultFileAdminService manageService;
	//
	// @Inject
	// protected UserService userService;
	//
	// @Inject
	// protected BoardService boardService;
	//
	// @Inject
	// protected TopicService topicService;
	//
	// @Inject
	// protected ReplyService replyService;
	//
	// @Inject
	// protected UserExtService extService;
	//
	// @Inject
	// protected CommonService commonService;
	//
	// @Inject
	// protected GroupService groupService;
	//
	// @Inject
	// protected GroupTopicService groupTopicService;
	//
	// @Inject
	// protected BlogService blogService;
	//
	// @Inject
	// protected EntryService entryService;
	//
	@Inject
	protected CommentService blogCommentService;

	@Inject
	protected GroupExtService groupExtService;

	//
	// @Inject
	// protected LinkService linkService;
	//
	// @Inject
	// protected AddressService vestService;
	//
	// @Inject
	// protected ImageService imageService;
	//
	// @Inject
	// protected GroupExtService groupExtService;
	//
	// @Inject
	// protected ActivityService activityService;
	//
	// @Inject
	// protected NewsService newsService;
	//
	// @Inject
	// protected ShareService shareService;

	public static final String ENCODE_STR = "UTF-8";

	public static final String FILE_SIZE = "fileSize";

	public static final String FILE_NAME = "fileName";

	public static final String FILE_PATH = "filePath";

	private MultipartRequest multiRequest;

	private HttpServletRequest httpRequest;

	/**
	 * 得到附件的展现字符串
	 * 
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	protected String getFileAttachStr(String fileName, String filePath,
			HttpServletRequest request) {
		String topicAttach = "";
		if (CommonUtils.isNotEmpty(fileName)
				&& CommonUtils.isNotEmpty(filePath)) {
			String username = UserHelper.getuserFromCookie(request);
			if (StringUtils.isNotEmpty(username)
					|| SystemConfigVars.ATTACH_VIEW_ALLOW_GUEST) {
				// 允许游客查看附件
				String staticRoot = SystemConfigUtils
						.getProperty("url.static.root");
				if (StringUtils.isEmpty(staticRoot)) {
					staticRoot = SystemConfigVars.UPLOAD_DIR_PATH;
				}
				topicAttach = "<img src='images/paperclip.gif' width=15 height=15 ><br>"
						+ CommonUtils.showAttach(fileName, staticRoot + "/"
								+ filePath);
			} else {
				// 禁止游客查看附件
				topicAttach = "<div class='quote'>"
						+ I18nMsgUtils.getInstance().getMessage(
								"error.noOption.attachment") + "</div>";
			}
		}
		return topicAttach;
	}

	/**
	 * 转向到系统的错误页面
	 * 
	 * @param request
	 * @param response
	 * @param errorKey
	 *            错误代码
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void sendError(HttpServletRequest request,
			HttpServletResponse response, String errorKey)
			throws ServletException, IOException {
		HttpUtils.sendError(request, response, errorKey);
	}

	/**
	 * 上传文件
	 * 
	 * @param f
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getAttach(File f, HttpServletRequest request)
			throws Exception {
		String baseDir = request.getSession().getServletContext().getRealPath(
				SystemConfigVars.UPLOAD_DIR_PATH);
		String fileName = ImageUtils.wrapImageNameByTime(f.getName());
		try {
			ImageUtils.checkDir(baseDir + File.separator + fileName);

			FileInputStream in = new FileInputStream(f);
			FileOutputStream out = new FileOutputStream(baseDir
					+ File.separator + fileName);
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			in.close();
			out.write(buffer);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return fileName;
	}

	/**
	 * 上传文件，老代码
	 * 
	 * @param request
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected Map getMultiFile(HttpServletRequest request, String name)
			throws Exception {
		Map reval = new HashMap();
		request.setCharacterEncoding(ENCODE_STR);
		String baseDir = request.getSession().getServletContext().getRealPath(
				SystemConfigVars.UPLOAD_DIR_PATH);
		FileUtils.checkDir(baseDir, true);
		int maxPostSize = BusinessConstants.MAX_FILE_SIZE;
		this.httpRequest = request;
		multiRequest = new MultipartRequest(request, baseDir, maxPostSize,
				ENCODE_STR);
		File f = multiRequest.getFile(name);
		String fileName = multiRequest.getOriginalFileName(name);

		String filePath = "";
		Integer fileSize = 0;
		if (f != null) {
			filePath = this.getAttach(f, request);
			fileSize = (int) f.length();
			f.delete();
		}
		reval.put(FILE_NAME, fileName);
		reval.put(FILE_PATH, filePath);
		reval.put(FILE_SIZE, fileSize);
		return reval;

	}

	/**
	 * 上传文件之后得到其他参数
	 * 
	 * @param name
	 * @return
	 */
	protected String getParameter(String name) {
		String reval = multiRequest.getParameter(name);
		if (CommonUtils.isEmpty(reval)) {
			reval = httpRequest.getParameter(name);
		}
		return reval;
	}
}
