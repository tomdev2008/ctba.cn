package org.net9.gallery.web.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.ImageOptionType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.gallery.ImageComment;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.gallery.service.ImageService;
import org.net9.group.web.GroupHelper;

/**
 * image action, now we support group image gallery
 * 
 * @author gladstone
 * 
 * @since 2008-8-29
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class ImageAction extends BusinessDispatchAction {

	private final GroupHelper groupHelper = new GroupHelper();

	/**
	 * 相关相册
	 * 
	 * @param request
	 * @param username
	 *            作者
	 */
	private void _refGalleries(HttpServletRequest request, String username) {
		List<ImageGallery> galleries = imageService.findGalleries(null,
				username, 0, 10);
		// List<Map<Object, Object>> models = new ArrayList<Map<Object,
		// Object>>();
		// for (ImageGallery g : galleries) {
		// Map<Object, Object> m = new HashMap<Object, Object>();
		// m.put("gallery", g);
		// m.put("cnt", this.imageService.getImagesCntByGallery(g.getId()));
		// m.put("isAuthor", g.getUsername().equals(username));
		// models.add(m);
		// }
		request.setAttribute("refGalleries", galleries);
	}

	/**
	 * 删除个人相册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvalidAuthorSecurityException
	 */
	public ActionForward deleteGallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws InvalidAuthorSecurityException {

		String id = request.getParameter("id");
		ImageGallery imageGallery = imageService
				.getImageGallery(new Integer(id));
		if (imageGallery != null) {
			// validate user
			UserHelper.authUserSimply(request, imageGallery);
			// if (imageGallery.getUsername().equals(username)) {
			imageService.delImageGallery(imageGallery);
			// }
		}
		return new ActionForward("img.shtml?method=galleryList", true);
	}

	/**
	 * delete image
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroupImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ImageModel imageModel = imageService.getImage(new Integer(id));

		Integer gid = imageModel.getGroupId();

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, imageModel,
				this.groupService.getGroup(gid));

		imageService.delImage(imageModel);
		return new ActionForward(UrlConstants.GROUP_GALLERY + gid, true);
	}

	/**
	 * 删除图片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ImageModel imageModel = imageService.getImage(new Integer(id));

		// validate user
		String username = UserHelper.getuserFromCookie(request);
		UserHelper.authUserForCurrentPojoSimply(username, imageModel);

		Integer galleryId = imageModel.getGalleryId();

		imageService.delImage(imageModel);
		String type = request.getParameter("type");
		if ("face".equalsIgnoreCase(type)) {
			return new ActionForward("img.shtml?method=faceGallery", true);
		}
		return new ActionForward(UrlConstants.GALLERY + galleryId, true);
	}

	/**
	 * 删除评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvalidAuthorSecurityException
	 */
	public ActionForward deleteImgComment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvalidAuthorSecurityException {
		String id = request.getParameter("id");
		ImageComment model = imageService.getImageComment(new Integer(id));

		// validate user
		String username = UserHelper.getuserFromCookie(request);
		UserHelper.authUserForCurrentPojoSimply(username, model);

		Integer imageId = model.getImageModel().getId();
		// if (model.getUsername().equals(username)) {
		imageService.delImageComment(model);
		// }

		if (ImageService.isGroupImage(model.getImageModel())) {
			return new ActionForward(
					UrlConstants.GROUP_GALLERY_PHOTO + imageId, true);
		} else {
			return new ActionForward(UrlConstants.GALLERY_PHOTO + imageId, true);
		}

	}

	/**
	 * 头像相册页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward faceGallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		List<ImageModel> images = imageService.findFaceImagesByUser(username,
				0, BusinessConstants.MAX_INT);
		request.setAttribute("models", images);
		return mapping.findForward("gallery.face");
	}

	/**
	 * 个人相册表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward galleryForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		request.setAttribute("user", user);
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			ImageGallery imageGallery = imageService
					.getImageGallery(new Integer(id));
			request.setAttribute("model", imageGallery);
		}
		return mapping.findForward("gallery.form");
	}

	/**
	 * 相册首页(包括游客视图)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward galleryIndexPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 最热相册
		List<ImageGallery> hotGalleries = imageService.findHotGalleries(null,
				null, 0, 6);
		request.setAttribute("hotGalleries", ListWrapper.getInstance()
				.formatGalleryList(hotGalleries));

		// 最新相册
		List<ImageGallery> galleries = imageService.findGalleries(null, null,
				0, 12);
		request.setAttribute("galleries", ListWrapper.getInstance()
				.formatGalleryList(galleries));

		// 如果是登录用户，显示自己的最近相册和最新的评论
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isNotEmpty(username)) {

			// 最新相册
			List<ImageGallery> myGalleries = imageService.findGalleries(null,
					username, 0, 6);
			request.setAttribute("myGalleries", ListWrapper.getInstance()
					.formatGalleryList(myGalleries)); // 最新相册

			// //朋友的新相册
			// List<ImageGallery> friendsGalleries =
			// imageService.findGalleries(null,
			// username , 0, 6);
			// request.setAttribute("friendsGalleries",
			// ListWrapper.getInstance().formatGalleryList(friendsGalleries));

			// 我的最新评论
			List<ImageComment> myCommentList = imageService
					.findImageCommentsByGalleryOwner(username, true, 0, 20);
			request.setAttribute("myCommentList", myCommentList);
		}
		// 最热相片
		List<ImageModel> hotImages = imageService.findHotGalleryImages(null, 0,
				12);
		request.setAttribute("hotImages", hotImages);
		// 最新评论
		List<ImageComment> globalCommentList = imageService
				.findGalleryImageComments(true, 0, 10);
		request.setAttribute("globalCommentList", globalCommentList);

		// 概况
		// 共上传几张相片，几个相册
		Integer galleryCnt = this.imageService.getGalleryCnt(null, null);
		Integer imageCnt = this.imageService.getGalleryImageCnt();
		request.setAttribute("totalGalleryCnt", galleryCnt);
		request.setAttribute("totalImageCnt", imageCnt);
		return mapping.findForward("gallery.home");
	}

	/**
	 * 个人相册列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward galleryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		User user = this.getFocusUser(request);
		request.setAttribute("user", user);
		boolean all = StringUtils.isNotEmpty(request.getParameter("type"));
		if (user == null) {
			all = true;
		}
		request.setAttribute("__gallery_all", all ? "true" : null);
		Integer start = HttpUtils.getStartParameter(request);
		int limit = 16;
		List<ImageGallery> galleries = imageService.findGalleries(null,
				all ? null : user.getUserName(), start, limit);
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ImageGallery g : galleries) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("gallery", g);
			m.put("cnt", this.imageService.getImagesCntByGallery(g.getId()));
			if (user != null) {
				m.put("isAuthor", g.getUsername().equals(user.getUserName()));
			}

			models.add(m);
		}

		Integer count = imageService.getGalleryCnt(null, all ? null : user
				.getUserName());
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		List<ImageComment> globalCommentList = null;
		if (all) {
			globalCommentList = imageService.findGalleryImageComments(true, 0,
					10);
		} else {
			globalCommentList = imageService.findImageCommentsByGalleryOwner(
					user.getUserName(), true, 0, 10);
		}

		request.setAttribute("globalCommentList", globalCommentList);
		return mapping.findForward("gallery.list");
	}

	/**
	 * 个人相册展现页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward galleryView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ImageGallery imageGallery = imageService
				.getImageGallery(new Integer(id));

		// check user view option >>>>>
		String username = UserHelper.getuserFromCookie(request);
		User loginUser = this.userService.getUser(null, username);
		if (loginUser == null) {
			log.debug("Not logined, check user setting");
			if (imageGallery.getViewOption() > ImageOptionType.PUBLIC
					.getValue()) {
				this.sendError(request, response, "image.privacy.forbidden");
				return null;
			}
		}
		boolean isSelf = loginUser != null
				&& loginUser.getUserName().equals(imageGallery.getUsername());

		boolean isFriend = isSelf
				|| (loginUser != null && userService.isFriend(imageGallery
						.getUsername(), loginUser.getUserName()));

		if (((!isSelf)
				&& (imageGallery.getViewOption() == ImageOptionType.PRIVATE
						.getValue()) || ((!isFriend) && (imageGallery
				.getViewOption() >= ImageOptionType.PROTECTED.getValue())))) {
			if (!this.userService.isEditor(username)) {// #903 (增加管理员查看图片的权限)
				this.sendError(request, response, "image.privacy.forbidden");
				return null;
			}

		}
		// <<<<<check user view option
		imageGallery.setHits(imageGallery.getHits() + CommonUtils.getHitPlus());
		this.imageService.saveImageGallery(imageGallery);

		request.setAttribute("galleryModel", imageGallery);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<ImageModel> images = imageService.findImagesByGallery(imageGallery
				.getId(), start, limit);
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ImageModel img : images) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("img", img);
			m.put("isAuthor", img.getUsername().equals(username));
			models.add(m);
		}

		Integer count = imageService
				.getImagesCntByGallery(imageGallery.getId());
		request.setAttribute("count", count);
		request.setAttribute("models", models);

		this._refGalleries(request, imageGallery.getUsername());
		List<ImageComment> globalCommentList = imageService
				.findImageCommentsByGalleryOwner(imageGallery.getUsername(),
						true, 0, 10);
		request.setAttribute("globalCommentList", globalCommentList);
		return mapping.findForward("gallery.view");
	}

	/**
	 * list group images
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward groupImageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = 16;
		String groupId = request.getParameter("gid");
		List<ImageModel> imgs = imageService.findImages("g", new Integer(
				groupId), start, limit);
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ImageModel img : imgs) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("img", img);
			m.put("isAuthor", img.getUsername().equals(username));
			models.add(m);
		}

		GroupModel group = groupService.getGroup(new Integer(groupId));

		// GroupUser gu = groupService.getGroupUser(group.getId(), username);
		request.setAttribute("group", group);
		groupHelper.info(request, group);
		Integer count = imageService.getImagesCnt(new Integer(groupId), null);
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		// boolean waiting = gu != null
		// && gu.getRole().equals(GroupService.GROUP_USER_ROLE_WAIT);
		// request.setAttribute("waiting", waiting);
		// this.groupHelper.prepareCommends(request);
		return mapping.findForward("img.list");
	}

	/**
	 * 查看小组相册的照片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward groupImageView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ImageModel model = this.imageService.getImage(new Integer(id));
		request.setAttribute("prevModel", this.imageService
				.getPrevImageInGroup(model.getId(), model.getGroupId()));
		request.setAttribute("nextModel", this.imageService
				.getNextImageInGroup(model.getId(), model.getGroupId()));
		GroupModel groupModel = this.groupService.getGroup(model.getGroupId());
		request.setAttribute("groupModel", groupModel);

		// hit++
		Integer hit = model.getHits();
		if (hit == null) {
			hit = 0;
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setHits(hit + hitPlus);
		this.imageService.saveImage(model);
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<ImageComment> commentList = imageService.findImageComments(model
				.getId(), false, start, limit);
		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (ImageComment comment : commentList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("comment", comment);
			m.put("isAuthor", comment.getUsername().equals(username));
			m
					.put("user", this.userService.getUser(null, comment
							.getUsername()));
			commentMapList.add(m);
		}
		Integer count = imageService.getImageCommentCnt(model.getId());
		request.setAttribute("imageModel", model);
		request.setAttribute("count", count);
		request.setAttribute("commentMapList", commentMapList);
		request.setAttribute("group", groupModel);
		groupHelper.info(request, groupModel);
		return mapping.findForward("img.view.group");
	}

	/**
	 * 查看单张照片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward imageView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ImageModel model = this.imageService.getImage(new Integer(id));
		request.setAttribute("prevModel", this.imageService.getPrevImage(model
				.getId(), model.getGalleryId()));
		request.setAttribute("nextModel", this.imageService.getNextImage(model
				.getId(), model.getGalleryId()));
		ImageGallery imageGallery = this.imageService.getImageGallery(model
				.getGalleryId());
		request.setAttribute("galleryModel", imageGallery);

		// check user view option >>>>>
		// TODO: copy-past,change this
		String username = UserHelper.getuserFromCookie(request);
		User loginUser = this.userService.getUser(null, username);
		if (loginUser == null) {
			log.debug("Not logined, check user setting");
			if (imageGallery.getViewOption().intValue() > ImageOptionType.PUBLIC
					.getValue()) {
				this.sendError(request, response, "image.privacy.forbidden");
				return null;
			}
		}
		boolean isSelf = loginUser != null
				&& loginUser.getUserName().equals(imageGallery.getUsername());

		boolean isFriend = isSelf
				|| (loginUser != null && userService.isFriend(imageGallery
						.getUsername(), loginUser.getUserName()));

		if (((!isSelf)
				&& (imageGallery.getViewOption().intValue() == ImageOptionType.PRIVATE
						.getValue()) || ((!isFriend) && (imageGallery
				.getViewOption().intValue() >= ImageOptionType.PROTECTED
				.getValue())))) {
			if (!this.userService.isEditor(username)) {// #903 (增加管理员查看图片的权限)
				this.sendError(request, response, "image.privacy.forbidden");
				return null;
			}
		}
		// <<<<<check user view option

		if (isSelf) {
			// #671相片的分类转移功能
			// 取得当前用户的相册列表
			List<ImageGallery> gList = this.imageService.findGalleries(null,
					username, 0, BusinessConstants.MAX_INT);
			request.setAttribute("galleryList", gList);
		}

		// hit++
		Integer hit = model.getHits();
		if (hit == null) {
			hit = 0;
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setHits(hit + hitPlus);
		this.imageService.saveImage(model);
		imageGallery.setHits(imageGallery.getHits() + hitPlus);
		this.imageService.saveImageGallery(imageGallery);

		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<ImageComment> commentList = imageService.findImageComments(model
				.getId(), false, start, limit);
		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (ImageComment comment : commentList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("comment", comment);
			m.put("isAuthor", comment.getUsername().equals(username));
			m
					.put("user", this.userService.getUser(null, comment
							.getUsername()));
			commentMapList.add(m);
		}
		Integer count = imageService.getImageCommentCnt(model.getId());
		request.setAttribute("imageModel", model);
		request.setAttribute("galleryModel", imageGallery);
		request.setAttribute("count", count);
		request.setAttribute("commentMapList", commentMapList);

		this._refGalleries(request, imageGallery.getUsername());

		return mapping.findForward("img.view");
	}

	/**
	 * 加载相册图片列表的容器
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public ActionForward miniImageForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return mapping.findForward("image.frame.mini");
	}

	/**
	 * 个人相册展现页面,ajax加载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ActionForward miniImageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		List<ImageGallery> galleries = imageService.findGalleries(null,
				username, 0, BusinessConstants.MAX_INT);
		request.setAttribute("galleries", galleries);
		Integer start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		ImageGallery imageGallery = null;
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			imageGallery = imageService.getImageGallery(new Integer(id));

		} else {
			if (galleries.size() > 0) {
				imageGallery = galleries.get(0);
			}
		}
		request.setAttribute("galleryModel", imageGallery);
		if (imageGallery != null) {
			request.setAttribute("count", imageService
					.getImagesCntByGallery(imageGallery.getId()));
			request.setAttribute("models", imageService.findImagesByGallery(
					imageGallery.getId(), start, limit));
		}

		return mapping.findForward("gallery.view.mini");
	}

	/**
	 * 保存个人相册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveGallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String username = UserHelper.getuserFromCookie(request);
		ImageGallery model = null;
		if (StringUtils.isNotEmpty(id)) {
			model = imageService.getImageGallery(new Integer(id));
			model.setUpdateTime(StringUtils.getTimeStrByNow());
		} else {
			model = new ImageGallery();
			model.setHits(0);
			model.setUsername(username);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			model.setCreateTime(StringUtils.getTimeStrByNow());
		}
		PropertyUtil.populateBean(model, request);
		imageService.saveImageGallery(model);
		if (StringUtils.isNotEmpty(id)) {
			userService.trigeEvent(this.userService.getUser(username), model
					.getId()
					+ "", UserEventType.GALLERY_EDIT);
			return new ActionForward(UrlConstants.GALLERY + id, true);
		} else {
			model = this.imageService.getImageGallery(null);
			userService.trigeEvent(this.userService.getUser(username), model
					.getId()
					+ "", UserEventType.GALLERY_NEW);
		}
		return new ActionForward("img.shtml?method=galleryList", true);
	}

	/**
	 * 保存图片信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvalidAuthorSecurityException
	 */
	public ActionForward saveImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws InvalidAuthorSecurityException {
		String imageId = request.getParameter("imageId");
		String galleryId = request.getParameter("galleryId");
		String name = request.getParameter("imageName");
		ImageModel imageModel = this.imageService.getImage(Integer
				.valueOf(imageId));

		if (ImageService.isGroupImage(imageModel)) {

			// validate user
			GroupModel groupModel = this.groupService.getGroup(imageModel
					.getGroupId());
			this.groupHelper.authUserForCurrentPojo(request, imageModel,
					groupModel);
		} else {
			// validate user
			UserHelper.authUserWithEditorOption(request, imageModel,
					userService);
		}
		// if (imageModel.getUsername().equals(username)) {
		imageModel.setUpdateTime(StringUtils.getTimeStrByNow());
		imageModel.setName(name);
		if (StringUtils.isNotEmpty(galleryId)) {
			imageModel.setGalleryId(Integer.valueOf(galleryId));
		}
		imageService.saveImage(imageModel);
		imageService.flushImageCache();
		// }
		if (ImageService.isGroupImage(imageModel)) {
			return new ActionForward(UrlConstants.GROUP_GALLERY_PHOTO
					+ imageModel.getId(), true);
		} else {
			return new ActionForward(UrlConstants.GALLERY_PHOTO
					+ imageModel.getId(), true);
		}

	}

	/**
	 * 保存图片的评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveImgComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String imageId = request.getParameter("imageId");
		String username = UserHelper.getuserFromCookie(request);
		ImageModel imageModel = this.imageService.getImage(Integer
				.valueOf(imageId));
		ImageComment model = new ImageComment();
		model.setImageModel(imageModel);
		model.setUsername(username);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setIp(HttpUtils.getIpAddr(request));
		PropertyUtil.populateBean(model, request);
		imageService.saveImageComment(model);
		userService.trigeEvent(this.userService.getUser(username), imageId,
				UserEventType.GALLERY_IMAGE_COMMENT);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapImageModel(imageModel) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}
		if (ImageService.isGroupImage(imageModel)) {
			return new ActionForward(UrlConstants.GROUP_GALLERY_PHOTO
					+ imageModel.getId(), true);
		} else {
			return new ActionForward(UrlConstants.GALLERY_PHOTO
					+ imageModel.getId(), true);
		}

	}

	/**
	 * 设置相册封面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setGalleryFace(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String galleryId = request.getParameter("galleryId");
		ImageModel imageModel = imageService.getImage(new Integer(id));
		ImageGallery gallery = imageService.getImageGallery(new Integer(
				galleryId));
		gallery.setFace(imageModel.getPath());
		imageService.saveImageGallery(gallery);
		imageService.flushGalleryCache();
		return new ActionForward(UrlConstants.GALLERY + galleryId, true);
	}

	/**
	 * 设置用户头像
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setUserFace(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ImageModel imageModel = imageService.getImage(new Integer(id));
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		user.setUserFace(imageModel.getPath());
		userService.saveUser(user, true);
		return new ActionForward("userSetting.action?method=face", true);
	}
}
