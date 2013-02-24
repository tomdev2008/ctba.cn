package org.net9.core.service;

import org.net9.domain.dao.bbs.BoardsDAO;
import org.net9.domain.dao.bbs.ForbiddensDAO;
import org.net9.domain.dao.bbs.ReplyDAO;
import org.net9.domain.dao.bbs.TopicsDAO;
import org.net9.domain.dao.bbs.UserboardDAO;
import org.net9.domain.dao.blog.BlogAddressDAO;
import org.net9.domain.dao.blog.BlogBlogDAO;
import org.net9.domain.dao.blog.BlogBlogentryDAO;
import org.net9.domain.dao.blog.BlogCategoryDAO;
import org.net9.domain.dao.blog.BlogCommentDAO;
import org.net9.domain.dao.blog.BlogLinkDAO;
import org.net9.domain.dao.core.FriendsDAO;
import org.net9.domain.dao.core.MainPlaceDAO;
import org.net9.domain.dao.core.MainUserDAO;
import org.net9.domain.dao.core.MessagesDAO;
import org.net9.domain.dao.core.NoticeDAO;
import org.net9.domain.dao.core.OnlineDAO;
import org.net9.domain.dao.core.ShareCommentDAO;
import org.net9.domain.dao.core.SysAdminDAO;
import org.net9.domain.dao.core.SysBlacklistDAO;
import org.net9.domain.dao.core.SysConfigDAO;
import org.net9.domain.dao.core.SysEmailDAO;
import org.net9.domain.dao.core.UserNoteDAO;
import org.net9.domain.dao.core.UserlogDAO;
import org.net9.domain.dao.core.UsersDAO;
import org.net9.domain.dao.ctba.VoteAnswerDAO;
import org.net9.domain.dao.ctba.VoteCommentDAO;
import org.net9.domain.dao.ctba.VoteDAO;
import org.net9.domain.dao.gallery.ImageCommentDAO;
import org.net9.domain.dao.gallery.ImageModelDAO;
import org.net9.domain.dao.group.ActivityCommentDAO;
import org.net9.domain.dao.group.ActivityModelDAO;
import org.net9.domain.dao.group.ActivityUserDAO;
import org.net9.domain.dao.group.GroupModelDAO;
import org.net9.domain.dao.group.GroupTopicDAO;
import org.net9.domain.dao.group.GroupUserDAO;
import org.net9.domain.dao.news.NewsCategoryDAO;
import org.net9.domain.dao.news.NewsCommentDAO;
import org.net9.domain.dao.news.NewsEntryDAO;
import org.net9.domain.dao.news.NewsPostDAO;
import org.net9.domain.dao.subject.SubjectDAO;
import org.net9.domain.dao.subject.SubjectTemplateDAO;
import org.net9.domain.dao.subject.SubjectTopicDAO;
import org.net9.domain.dao.view.ViewHotUserDAO;
import org.net9.domain.dao.view.ViewRandomGroupDAO;

import com.google.inject.Inject;

/**
 * 基本服务，采用Guice注入<br>
 * 在Web启动是由GuiceFilter创建Injector<br>
 * 在batch程序和UT中如果调用服务可能需要手动创建Injector来注入
 * 
 * @author gladstone
 * @since 2008/06/28
 */
public abstract class BaseService implements IService, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected UserNoteDAO userNoteDAO;

	@Inject
	protected ImageCommentDAO imageCommentDAO;

	@Inject
	protected ViewHotUserDAO viewHotUserDAO;

	@Inject
	protected ActivityCommentDAO activityCommentDAO;

	@Inject
	protected UsersDAO userDAO;

	@Inject
	protected SysConfigDAO sysConfigDAO;

	@Inject
	protected BoardsDAO boardDAO;

	@Inject
	protected FriendsDAO friendDAO;

	@Inject
	protected TopicsDAO topicDAO;

	@Inject
	protected MessagesDAO messageDAO;

	@Inject
	protected SysAdminDAO adminDAO;

	@Inject
	protected UserboardDAO userboardDAO;

	@Inject
	protected ForbiddensDAO forbiddensDAO;

	@Inject
	protected OnlineDAO bbsOnlineDAO;

	@Inject
	protected NewsCategoryDAO newsCategoryDAO;

	@Inject
	protected NewsCommentDAO newsCommentDAO;

	@Inject
	protected NewsEntryDAO newsEntryDAO;

	@Inject
	protected NewsPostDAO newsPostDAO;

	@Inject
	protected UserlogDAO userlogDAO;

	@Inject
	protected MainUserDAO mainUserDAO;

	@Inject
	protected SysEmailDAO sysEmailDAO;

	@Inject
	protected MainPlaceDAO mainPlaceDAO;

	@Inject
	protected ReplyDAO replyDAO;

	@Inject
	protected VoteDAO voteDAO;

	@Inject
	protected VoteCommentDAO voteCommentDAO;

	@Inject
	protected VoteAnswerDAO voteAnswerDAO;

	@Inject
	protected SubjectDAO subjectDAO;

	@Inject
	protected SubjectTopicDAO subjectTopicDAO;

	@Inject
	protected SubjectTemplateDAO subjectTemplateDAO;

	@Inject
	protected BlogBlogDAO blogDAO;

	@Inject
	protected BlogBlogentryDAO blogEntryDAO;

	@Inject
	protected BlogAddressDAO blogAddressDAO;

	@Inject
	protected BlogCategoryDAO categoryDAO;

	@Inject
	protected BlogCommentDAO commentDAO;

	@Inject
	protected BlogLinkDAO linkDAO;

	@Inject
	protected GroupModelDAO groupModelDAO;

	@Inject
	protected GroupTopicDAO groupTopicDAO;

	@Inject
	protected GroupUserDAO groupUserDAO;

	@Inject
	protected ImageModelDAO imageModelDAO;

	@Inject
	protected ActivityModelDAO activityModelDAO;

	@Inject
	protected ActivityUserDAO activityUserDAO;

	@Inject
	protected ViewRandomGroupDAO viewRandomGroupDAO;

	@Inject
	protected ShareCommentDAO shareCommentDAO;

	@Inject
	protected NoticeDAO noticeDAO;

	@Inject
	protected SysBlacklistDAO sysBlacklistDAO;

}
