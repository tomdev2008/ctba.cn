package org.net9.group.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBWithoutImgTagHandler;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.hit.HitStrategy;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.group.GroupUser;
import org.net9.domain.model.view.ViewGroupReference;
import org.net9.group.web.GroupHelper;
import org.net9.search.lucene.search.ref.LuceneGroupTopicReferenceSearcher;

import com.google.inject.Inject;

/**
 * Group Topic Action
 * 
 * @author gladstone
 * 
 */
@WebModule("gt")
@SuppressWarnings("serial")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class GroupTopicServlet extends BusinessCommonServlet {

	private final static Log log = LogFactory.getLog(GroupTopicServlet.class);
	@Inject
	private LuceneGroupTopicReferenceSearcher referenceSearcher;
	@Inject
	private HitStrategy hHitStrategy;
	// TODO:check this
	private final GroupHelper groupHelper = new GroupHelper();

	public void dealTop(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String tid = request.getParameter("tid");
		GroupTopic model = groupTopicService.getTopic(new Integer(tid));

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, model, model
				.getGroupModel());

		Integer gid = model.getGroupModel().getId();
		String username = UserHelper.getuserFromCookie(request);
		GroupUser gu = groupService.getGroupUser(gid, username);

		if (gu != null
				&& gu.getRole().equals(
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER)) {
			log.info("top: " + tid);
			if (model.getTopState().intValue() == 0) {
				model.setTopState(1);
			} else {
				model.setTopState(0);
			}
			this.groupTopicService.saveTopic(model);
			this.groupTopicService.flushTopicCache();
		} else {
			log.info("not manager doing top: " + tid);
		}
		response.sendRedirect(UrlConstants.GROUP + gid);
	}

	/**
	 * 删除小组话题
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String tid = request.getParameter("tid");
		String returnStr = request.getParameter("return");
		GroupTopic model = groupTopicService.getTopic(new Integer(tid));

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, model, model
				.getGroupModel());

		Integer gid = model.getGroupModel().getId();
		Integer pid = model.getParent();
		groupTopicService.delTopic(model);
		if (pid != null && pid > 0) {
			GroupTopic parentModel = groupTopicService.getTopic(pid);
			parentModel.setUpdateTime(DateUtils.getNow());
			parentModel.setReplyCnt(parentModel.getReplyCnt() - 1);
			groupTopicService.saveTopic(parentModel);
			response.sendRedirect(UrlConstants.GROUP_TOPIC + pid);
		} else {
			if ("personal".equals(returnStr)) {
				response.sendRedirect("gt.action?method=personal");
			} else {
				response.sendRedirect(UrlConstants.GROUP + gid);
			}

		}
	}

	/**
	 * 删除小组话题附件
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	public void deleteAttachemnt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String tid = request.getParameter("tid");
		String attachmentIndex = request.getParameter("index");
		GroupTopic model = groupTopicService.getTopic(new Integer(tid));
		if (model != null) {

			// validate user
			this.groupHelper.authUserForCurrentPojo(request, model, model
					.getGroupModel());

			String[] attachments = model.getAttachment().split(
					GroupHelper.FILE_SPLLITER);
			List<String> newAttachmentList = new ArrayList<String>();
			log.info("delete attachemnt for topic " + tid + ":"
					+ attachmentIndex);
			for (int i = 0; i < attachments.length; i++) {
				if (i != Integer.valueOf(attachmentIndex)) {
					newAttachmentList.add(attachments[i]);
				}
			}
			String newAttachmentStr = "";
			for (String att : newAttachmentList) {
				newAttachmentStr += att + GroupHelper.FILE_SPLLITER;
			}
			if (newAttachmentStr.endsWith(GroupHelper.FILE_SPLLITER)) {
				newAttachmentStr = newAttachmentStr.substring(0,
						newAttachmentStr.length() - 1);
			}
			model.setAttachment(newAttachmentStr);
			groupTopicService.saveTopic(model);
		}
		response.sendRedirect("gt.action?method=form&tid=" + tid);
	}

	/**
	 * 话题表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/group/topicForm.jsp")
	public boolean form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tid = request.getParameter("tid");
		String gid = request.getParameter("gid");
		Integer attachmentCnt = 0;
		if (StringUtils.isNotEmpty(tid)) {
			GroupTopic model = groupTopicService.getTopic(new Integer(tid));
			request.setAttribute("topicModel", model);
			request.setAttribute("groupModel", model.getGroupModel());
			if (StringUtils.isNotEmpty(model.getAttachment())) {
				request.setAttribute("attachments", model.getAttachment()
						.split(GroupHelper.FILE_SPLLITER));
				attachmentCnt = model.getAttachment().split(
						GroupHelper.FILE_SPLLITER).length;
			}

			groupHelper.info(request, model.getGroupModel());
		} else if (StringUtils.isNotEmpty(gid)) {
			GroupModel group = groupService.getGroup(new Integer(gid));
			request.setAttribute("groupModel", group);
			groupHelper.info(request, group);
		}
		request.setAttribute("attachmentsCnt", attachmentCnt);
		String username = UserHelper.getuserFromCookie(request);
		List<GroupModel> gList = this.groupService
				.findGroupsByUsernameAndRoles(username, new Integer[] {
						GroupUserRoleType.GROUP_USER_ROLE_NORMAL,
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER }, 0,
						BusinessConstants.MAX_INT);
		if (gList.size() < 1) {
			String msg = I18nMsgUtils.getInstance().getMessage(
					"group.error.noGroupYet");
			request.setAttribute(BusinessConstants.ERROR_KEY, msg);
			request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
					request, response);
			return true;
		}

		request.setAttribute("groupList", gList);
		return false;
	}

	/**
	 * 小组话题列表(小组首页)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@ReturnUrl(rederect = false, url = "/group/topicList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String username = UserHelper.getuserFromCookie(request);
		String idStr = request.getParameter("gid");
		String url = request.getParameter("url");
		GroupModel model = null;
		if (StringUtils.isNotEmpty(idStr)) {
			model = groupService.getGroup(new Integer(idStr));
		} else {
			model = groupService.getGroupsByMagicUrl(url);
		}

		// #735 (对于空贴或者其他导致nullpointer例外的情况的处理)
		if (model == null) {
			throw new ModelNotFoundException();
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<GroupTopic> allTopics = new ArrayList<GroupTopic>();
		List<GroupTopic> topTopics = groupTopicService.findTopics(
				model.getId(), true, false, null, 0, BusinessConstants.MAX_INT);

		List<GroupTopic> topics = groupTopicService.findTopics(model.getId(),
				false, false, null, start, limit);
		Integer cnt = groupTopicService.getGroupTopicsCnt(model.getId(), false,
				false);
		allTopics.addAll(topTopics);
		allTopics.addAll(topics);

		List<Map<Object, Object>> topicsMap = this.getWrappedTopicList(
				allTopics, username);
		request.setAttribute("start", start);
		request.setAttribute("count", cnt);
		request.setAttribute("topicsMap", topicsMap);
		// request.setAttribute("topics", topics);

		groupHelper.info(request, model);

		// 最新小组活动(6条)
		List<ActivityModel> activityList = activityService.findActivities(model
				.getId(), null, null, null, 0, 6);
		request.setAttribute("newActivityList", activityList);

		// 增加最新照片列表; (8条)
		List<ImageModel> imgs = imageService.findImages("g", new Integer(model
				.getId()), 0, 8);
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ImageModel img : imgs) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("img", img);
			m.put("isAuthor", img.getUsername().equals(username));
			models.add(m);
		}
		request.setAttribute("newImageList", models);

		// #668 (增加相关小组功能)
		// 相关小组(9条)
		List<ViewGroupReference> refGroups = this.groupService
				.findReferenceGroups(model.getId(), 0, 9);
		List<Map<Object, Object>> refGroupMaps = new ArrayList<Map<Object, Object>>();
		for (ViewGroupReference ref : refGroups) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("group", this.groupService.getGroup(ref.getRefid()));
			refGroupMaps.add(m);
		}
		request.setAttribute("refGroupMaps", refGroupMaps);
	}

	/**
	 * #818 (个人小组话题的列表)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	@ReturnUrl(rederect = false, url = "/group/personalTopicList.jsp")
	public void personal(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			CommonSystemException {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<GroupTopic> topics = groupTopicService.findTopicsByUser(null,
				username, start, limit);
		Integer cnt = groupTopicService.getTopicsCntByUser(null, username);

		List<Map<Object, Object>> topicsMap = this.getWrappedTopicList(topics,
				username);
		request.setAttribute("start", start);
		request.setAttribute("count", cnt);
		request.setAttribute("topicsMap", topicsMap);
		request.setAttribute("topics", topics);

	}

	private List<Map<Object, Object>> getWrappedTopicList(
			List<GroupTopic> topics, String username) {
		List<Map<Object, Object>> topicsMap = new ArrayList<Map<Object, Object>>();
		for (GroupTopic t : topics) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("topic", t);
			if (t.getParent() <= 0) {
				m.put("reCnt", groupTopicService.getGroupTopicsCntByParent(t
						.getId()));
			}
			m.put("isAuthor", t.getAuthor().equals(username));
			m.put("author", userService.getUser(null, t.getAuthor()));
			topicsMap.add(m);
		}
		return topicsMap;
	}

	/**
	 * 保存小组话题
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {

		Map<String, Object> requestMap = this.groupHelper.uploadAttachments(
				request, response);

		String idStr = (String) requestMap.get("tid");
		String pareintIdStr = (String) requestMap.get("pid");
		String groupId = (String) requestMap.get("gid");
		String content = (String) requestMap.get("topicContent");
		String title = (String) requestMap.get("title");
		String attachmentStr = (String) requestMap.get(GroupHelper.FILE_MARK);

		Integer parent = 0;
		GroupModel group = groupService.getGroup(new Integer(groupId));
		String username = UserHelper.getuserFromCookie(request);
		GroupTopic model = null;
		if (StringUtils.isEmpty(idStr)) {
			log.debug("new topic ");
			model = new GroupTopic();
			model.setCreateTime(DateUtils.getNow());
			model.setUpdateTime(DateUtils.getNow());
			model.setAuthor(username);
			model.setContent(content);
			model.setGroupModel(group);
			model.setIp(HttpUtils.getIpAddr(request));
			model.setParent(parent);
			model.setTitle(title);
			model.setGoodHits(0);
			model.setBadHits(0);
			model.setHits(0);
			model.setReplyCnt(0);
			model.setTopState(0);
		} else {
			log.debug("update topic ");
			model = groupTopicService.getTopic(new Integer(idStr));

			// validate user
			this.groupHelper.authUserForCurrentPojo(request, model, model
					.getGroupModel());

			model.setUpdateTime(DateUtils.getNow());
			model.setContent(content);
			model.setGroupModel(group);
			model.setIp(HttpUtils.getIpAddr(request));
			model.setParent(parent);
			model.setTitle(title);
		}

		if (StringUtils.isNotEmpty(pareintIdStr)) {
			model.setParent(Integer.parseInt(pareintIdStr));
		}
		// model.setLogLevel(GroupTopicService.TOPIC_LEVEL_DEFAULT);
		if (StringUtils.isNotEmpty(model.getAttachment())) {
			attachmentStr = model.getAttachment() + GroupHelper.FILE_SPLLITER
					+ attachmentStr;
		}
		model.setAttachment(attachmentStr);
		groupTopicService.saveTopic(model);

		// 是回复，返回主贴
		if (StringUtils.isNotEmpty(pareintIdStr)) {
			GroupTopic parentModel = groupTopicService.getTopic(new Integer(
					pareintIdStr));
			if (parentModel != null) {
				parentModel.setUpdateTime(DateUtils.getNow());
				parentModel.setReplyCnt((parentModel.getReplyCnt() == null ? 0
						: parentModel.getReplyCnt()) + 1);
				groupTopicService.saveTopic(parentModel);
			}
			userService.trigeEvent(this.userService.getUser(username),
					pareintIdStr, UserEventType.GROUP_TOPIC_REPLY);

			// 如果有被回复的用户，发送系统通知
			String repliedUsername = (String) requestMap
					.get(WebConstants.PARAMETER_REPLY_TO);// HttpUtils.getRepliedUsername(request);
			if (StringUtils.isNotEmpty(repliedUsername)) {
				String msg = I18nMsgUtils
						.getInstance()
						.createMessage(
								"notice.replied",
								new Object[] {
										CommonUtils.buildUserPagelink(username),
										SimplePojoWrapper
												.wrapGroupTopic(parentModel) });
				String refererURL = HttpUtils.getReferer(request);
				userService.trigeNotice(repliedUsername, username, msg,
						refererURL, NoticeType.REPLY);
			}

			response.sendRedirect(UrlConstants.GROUP_TOPIC + pareintIdStr);
		} else {
			if (StringUtils.isEmpty(idStr)) {
				// 新发帖子，得到当前的最新帖子
				model = groupTopicService.getNewestGroupTopic(group.getId(),
						false);
				userService.trigeEvent(this.userService.getUser(username),
						model.getId() + "", UserEventType.GROUP_TOPIC_NEW);
			} else {
				userService.trigeEvent(this.userService.getUser(username),
						model.getId() + "", UserEventType.GROUP_TOPIC_EDIT);
			}
			if (model.getId() != null) {
				response.sendRedirect(UrlConstants.GROUP_TOPIC + model.getId());
			} else {
				response.sendRedirect(UrlConstants.GROUP
						+ model.getGroupModel().getId());
			}
		}

	}

	/**
	 * 查看单个小组话题,到这里的唯一入口是小组的话题列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@ReturnUrl(rederect = false, url = "/group/topicView.jsp")
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String tid = request.getParameter("tid");
		GroupTopic gt = groupTopicService.getTopic(new Integer(tid));

		// #735 (对于空贴或者其他导致nullpointer例外的情况的处理)
		if (gt == null) {
			log.error("#735 ERROR");
			throw new ModelNotFoundException();
		}

		GroupModel model = gt.getGroupModel();

		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		
		hHitStrategy.hitGroupTopic(gt);
		
		List<GroupTopic> reTopics = groupTopicService.findTopicsByParent(gt
				.getId(), start, limit);
		Integer cnt = groupTopicService.getGroupTopicsCntByParent(gt.getId());

		request.setAttribute("count", cnt);
		request.setAttribute("topicModel", gt);
		User author = userService.getUser(null, gt.getAuthor());
		request.setAttribute("author", author);

		String username = UserHelper.getuserFromCookie(request);
		List<Map<Object, Object>> topicsMap = new ArrayList<Map<Object, Object>>();
		for (GroupTopic t : reTopics) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("topic", t);
			m.put("isAuthor", t.getAuthor().equals(username));
			m.put("author", userService.getUser(null, t.getAuthor()));
			if (StringUtils.isNotEmpty(t.getAttachment())) {
				m.put("attachments", t.getAttachment().split(
						GroupHelper.FILE_SPLLITER));
			}

			topicsMap.add(m);
		}
		// #137 去除簽名檔的img標籤
		// #327 签名档中的UBB代码转换有问题
		request.setAttribute("userQMD", UBBDecoder.decode(CommonUtils
				.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(author
						.getUserQMD())), new UBBWithoutImgTagHandler(),
				UBBDecoder.MODE_IGNORE));

		request.setAttribute("topicsMap", topicsMap);
		request.setAttribute("isAuthor", gt.getAuthor().equals(username));
		groupHelper.info(request, model);
		if (StringUtils.isNotEmpty(gt.getAttachment())) {
			request.setAttribute("attachments", gt.getAttachment().split(
					GroupHelper.FILE_SPLLITER));
		}
		try {
			List<Map<String, String>> refTopics = referenceSearcher
					.searchByKey(gt.getTitle(), UrlConstants.GROUP_TOPIC
							+ gt.getId());
			request.setAttribute("refTopics", refTopics);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
