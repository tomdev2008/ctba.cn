package org.net9.gallery.web.struts.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.net9.common.util.DateUtils;
import org.net9.common.util.FileUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.gallery.web.struts.form.MultiImageFileForm;

/**
 * 小组文件上传
 * 
 * @author gladstone
 * @since Feb 7, 2009
 */
public class GroupImageUploadAction extends BusinessDispatchAction {

	private static Log log = LogFactory.getLog(GroupImageUploadAction.class);

	/**
	 * 表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String gid = request.getParameter("gid");
		if (StringUtils.isNotEmpty(id)) {
			ImageModel imageModel = imageService.getImage(new Integer(id));
			request.setAttribute("model", imageModel);
		}
		String username = UserHelper.getuserFromCookie(request);
		GroupModel groupModel = groupService.getGroup(new Integer(gid));
		request.setAttribute("group", groupModel);
		List<GroupModel> gList = this.groupService
				.findGroupsByUsernameAndRoles(username, new Integer[] {
						GroupUserRoleType.GROUP_USER_ROLE_NORMAL,
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER }, 0,
						BusinessConstants.MAX_INT);
		request.setAttribute("groupList", gList);
		return mapping.findForward("img.form");
	}

	/**
	 * 
	 * 处理上传
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String username = UserHelper.getuserFromCookie(request);
		MultiImageFileForm f = (MultiImageFileForm) form;
		List<FormFile> list = new ArrayList<FormFile>();
		Integer gid = f.getGid();
		list.add(f.getUploadFile1());
		list.add(f.getUploadFile2());
		list.add(f.getUploadFile3());
		list.add(f.getUploadFile4());
		list.add(f.getUploadFile5());
		list.add(f.getUploadFile6());
		int cnt = 0;
		for (FormFile file : list) {
			if (file != null && file.getFileSize() > 0
					&& StringUtils.isNotEmpty(file.getFileName())) {
				log.debug("get file:" + file.getFileName());
				try {
					String filename = file.getFileName();
					filename = URLEncoder.encode(filename, "UTF-8");
					String dirName = FileUtils.createDirName();
					String maskName = ImageUtils.createImageName("", filename);
					InputStream in = file.getInputStream();
					String fullPath = request.getSession().getServletContext()
							.getRealPath(SystemConfigVars.UPLOAD_DIR_PATH)
							+ File.separator
							+ dirName
							+ File.separator
							+ maskName;
					ImageUtils.checkDir(fullPath);
					OutputStream out = new FileOutputStream(fullPath);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
					out.close();
					in.close();
					file.destroy();

					ImageUtils.getDefaultImage(fullPath, false);
					ImageUtils.getMiniImage(fullPath, false);
					ImageUtils.getSizedImage(fullPath, 32);
					ImageUtils.getSizedImage(fullPath, 80);
					ImageUtils.getSizedImage(fullPath, 64);
					ImageUtils.getSizedImage(fullPath, 120);
					ImageUtils.getSizedImage(fullPath, 568);
					ImageUtils.getSizedImage(fullPath, 48);

					ImageModel model = new ImageModel();
					model.setCreateTime(DateUtils.getNow());
					model.setUpdateTime(DateUtils.getNow());
					model.setHits(0);
					model.setUsername(username);
					// 取截断字符
					model.setName(StringUtils.getShorterStr(filename, 15));
					model.setPath(dirName + "/" + maskName);
					model.setType("g");
					model.setGroupId(gid);
					model.setGalleryId(null);
					model.setDiscription(filename);
					imageService.saveImage(model);
					cnt++;
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		if (cnt > 0) {
			userService.trigeEvent(this.userService.getUser(username), gid
					+ "#" + cnt, UserEventType.GROUP_IMG_NEW);
		}
		return new ActionForward(UrlConstants.GROUP_GALLERY + gid, true);
	}

}
