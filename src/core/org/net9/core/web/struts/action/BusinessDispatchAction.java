package org.net9.core.web.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.bbs.service.BoardService;
import org.net9.bbs.service.ReplyService;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.AddressService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.CommentService;
import org.net9.blog.service.EntryService;
import org.net9.blog.service.LinkService;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebPageVarConstant;
import org.net9.core.service.CommonService;
import org.net9.core.service.DefaultFileAdminService;
import org.net9.core.service.EmailService;
import org.net9.core.service.SystemService;
import org.net9.core.service.UserExtService;
import org.net9.core.service.UserService;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.ShopModel;
import org.net9.gallery.service.ImageService;
import org.net9.group.service.ActivityService;
import org.net9.group.service.GroupExtService;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;
import org.net9.news.service.NewsService;
import org.net9.subject.service.SubjectService;
import org.net9.vote.service.VoteService;

import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.service.EquipmentCommentService;
import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.service.EquipmentUserService;
import cn.ctba.equipment.service.ShopService;
import cn.ctba.share.service.ShareService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;

/**
 * 基本Action
 * 
 * @author gladstone
 * 
 */
public abstract class BusinessDispatchAction extends GenericDispatchAction {

	@Inject
	protected VoteService voteService;

	@Inject
	protected ReplyService replyService;

	@Inject
	protected CommonService commonService;

	@Inject
	protected DefaultFileAdminService manageService;

	@Inject
	protected UserService userService;

	@Inject
	protected BoardService boardService;

	@Inject
	protected UserExtService userExtService;

	@Inject
	protected TopicService topicService;

	@Inject
	protected GroupService groupService;

	@Inject
	protected GroupTopicService groupTopicService;

	@Inject
	protected BlogService blogService;

	@Inject
	protected EntryService entryService;

	@Inject
	protected CommentService commentService;

	@Inject
	protected LinkService linkService;

	@Inject
	protected AddressService vestService;

	@Inject
	protected ImageService imageService;

	@Inject
	protected GroupExtService noticeService;

	@Inject
	protected SubjectService subjectService;

	@Inject
	protected EmailService emailService;

	@Inject
	protected NewsService newsService;

	@Inject
	protected ActivityService activityService;

	@Inject
	protected ShareService shareService;

	@Inject
	protected SystemService systemService;

	@Inject
	protected EquipmentService equipmentService;
	@Inject
	protected EquipmentCommentService equipmentCommentService;
	@Inject
	protected EquipmentUserService equipmentUserService;

	@Inject
	protected ShopService shopService;

	public BusinessDispatchAction() {
		// 加入Guice配置
		Injector injector = Guice.createInjector(new ServletModule());
		log.debug("Inject members with guice");
		injector.injectMembers(this);
	}

	/**
	 * 从当前会话得到被关注的用户（根据参数）
	 * 
	 * @param request
	 * @return
	 */
	protected User getFocusUser(HttpServletRequest request) {
		String uid = request.getParameter(UrlConstants.WRAP_UID);
		User focusUser = null;
		if (StringUtils.isNotEmpty(uid)) {
			focusUser = userService.getUser(Integer.valueOf(uid), null);
		}
		User loginedUser = userService.getUser(null, UserHelper
				.getuserFromCookie(request));
		if (focusUser == null) {
			focusUser = loginedUser;
			request.setAttribute(WebPageVarConstant.IS_SELF, true);
		} else {
			if (loginedUser == null) {
				request.setAttribute(WebPageVarConstant.IS_SELF, false);
			} else {
				request.setAttribute(WebPageVarConstant.IS_SELF, focusUser
						.getUserName().equals(loginedUser.getUserName()));
			}
		}

		if (focusUser != null) {
			request.setAttribute(WebPageVarConstant.USERNAME_FOCUS, focusUser
					.getUserName());
		}
		return focusUser;
	}

	/**
	 * #852 (CTBA社区财富(CTB))
	 * 
	 * BASE_CTB =
	 * 论坛文章数*1+所有小组数*3+小组话题数*1+小组活动数*2+日志数*1+创建的相册数*2+上传相片数*1+投递新闻数*2+发起投票数*2+参与投票数*1
	 * 
	 * +上传装备数*3+已经开店?10:1+装备点评数*1
	 * 
	 * 
	 * @param username
	 */
	protected Integer calculateBaseScore(String username) {
		Integer topicCnt = this.topicService.getTopicsCntByUser(username);
		Integer groupCnt = this.groupService.getGroupsCntByUsername(username,
				GroupUserRoleType.GROUP_USER_ROLE_MANAGER);
		Integer groupTopicCnt = this.groupTopicService.getTopicsCntByUser(null,
				username);
		Integer activityCnt = this.activityService.getActivitiesCnt(null, null,
				username, null);
		Integer blogCnt = 0;
		Blog blog = this.blogService.getBlogByUser(username);
		if (blog != null) {
			blogCnt = this.entryService.getEntriesCnt(blog.getId(), null,
					EntryService.EntryType.NORMAL.getType());
		}
		Integer imageCnt = this.imageService.getImagesCnt(null, username);
		Integer galleryCnt = this.imageService
				.getGalleryCntByUsername(username);
		Integer newsCnt = this.newsService.getNewsCntByAuthor(username);
		Integer voteCnt = this.voteService.getVotesCnt(null, username, null);
		Integer voteAnswerCnt = this.voteService
				.getVotesAnswerCntByUsername(username);
		Integer equipmentCnt = this.equipmentService.getEquipmentCnt(
				EquipmentStateType.OK, null, username, null, null);
		Integer shopScoreCnt = 0;
		Integer equipmentCommentCnt = this.equipmentUserService
				.getEquipmentUsersCntByUsername(username, null);
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		if (shopModel != null) {
			shopScoreCnt = 10;
		}
		return topicCnt + groupCnt * 3 + groupTopicCnt + activityCnt * 2
				+ blogCnt + galleryCnt * 2 + imageCnt + newsCnt * 2 + voteCnt
				* 2 + voteAnswerCnt + equipmentCnt * 3 + shopScoreCnt
				+ equipmentCommentCnt;
	}

	/**
	 * 转向到错误页面
	 * 
	 * @param request
	 * @param response
	 * @param errorCode
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void sendError(HttpServletRequest request,
			HttpServletResponse response, String errorCode)
			throws ServletException, IOException {
		String msg = I18nMsgUtils.getInstance().getMessage(errorCode);
		request.setAttribute(BusinessConstants.ERROR_KEY, msg);
		request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(request,
				response);
	}
}