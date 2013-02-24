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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.net9.common.util.DateUtils;
import org.net9.common.util.FileUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.gallery.web.struts.form.MultiImageFileForm;

/**
 * 多图片上传
 * 
 * @author gladstone
 * @since Nov 16, 2008
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class MultiImageUploadAction extends BusinessDispatchAction {

	/**
	 * 上传表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String galleryId = request.getParameter("galleryId");
		if (StringUtils.isNotEmpty(galleryId)) {
			ImageGallery gallery = this.imageService.getImageGallery(Integer
					.valueOf(galleryId));
			request.setAttribute("gallery", gallery);
		}
		String username = UserHelper.getuserFromCookie(request);
		List<ImageGallery> galleryList = this.imageService.findGalleries(null,
				username, 0, BusinessConstants.MAX_INT);
		request.setAttribute("galleryList", galleryList);
		return mapping.findForward("form");
	}

	public ActionForward miniForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String galleryId = request.getParameter("galleryId");
		if (StringUtils.isNotEmpty(galleryId)) {
			ImageGallery gallery = this.imageService.getImageGallery(Integer
					.valueOf(galleryId));
			request.setAttribute("gallery", gallery);
		}
		String username = UserHelper.getuserFromCookie(request);
		List<ImageGallery> galleryList = this.imageService.findGalleries(null,
				username, 0, BusinessConstants.MAX_INT);
		request.setAttribute("galleryList", galleryList);
		return mapping.findForward("form.mini");
	}

	/**
	 * 处理图片上传
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
		ImageGallery gallery = this.imageService.getImageGallery(gid);
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
					// ImageUtils.getMiniImage(fullPath, false);
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
					model.setName(filename);
					model.setPath(dirName + "/" + maskName);
					model.setType("u");
					model.setGroupId(null);
					model.setGalleryId(gallery.getId());
					model.setDiscription(filename);
					imageService.saveImage(model);
					cnt++;
					if (StringUtils.isEmpty(gallery.getFace())) {
						gallery.setFace(model.getPath());
						imageService.saveImageGallery(gallery);
					}
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		if (cnt > 0) {
			userService.trigeEvent(this.userService.getUser(username), gid
					+ "#" + cnt, UserEventType.GALLERY_IMAGE_NEW);
		}

		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage("file.uploaded");
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		if ("mini".equalsIgnoreCase(request.getParameter("type"))) {
			String naviType = request.getParameter("naviType");
			return new ActionForward("img.shtml?method=miniImageList&id="
					+ gallery.getId() + "&type=" + naviType, true);
		} else {
			return new ActionForward(UrlConstants.GALLERY + gallery.getId(),
					true);
		}

	}

}
