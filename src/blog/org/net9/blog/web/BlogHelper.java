package org.net9.blog.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.net9.blog.service.BlogService;
import org.net9.blog.service.CommentService;
import org.net9.blog.service.EntryService;
import org.net9.blog.service.LinkService;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.hit.HitStrategy;
import org.net9.core.service.CommonService;
import org.net9.core.service.UserService;
import org.net9.core.types.CommendType;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogComment;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.blog.BlogLink;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.view.BlogEntryMonthly;
import org.net9.gallery.service.ImageService;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class BlogHelper implements java.io.Serializable {

	private static final long serialVersionUID = -3229905523610181583L;

	@Inject
	private BlogService blogService;

	@Inject
	private EntryService entryService;

	@Inject
	private UserService userService;

	@Inject
	private LinkService linkService;

	@Inject
	private CommentService commentService;

	@Inject
	private CommonService commonService;

	@Inject
	private ImageService imageService;
	
	@Inject
	private HitStrategy hitStrategy;

	public void blogInfo(HttpServletRequest request, Blog blog)
			throws ServletException, IOException {

		// int hitPlus = new Random().nextInt(5) + 1;
		// blog.setHits(blog.getHits() == null ? 0 : blog.getHits() + hitPlus);
		// blogService.updateBlog(blog);
		hitStrategy.hitBlog(blog);

		// get new entry list
		List<BlogEntry> entries = entryService.findEntries(blog.getId(), null,
				EntryService.EntryType.NORMAL.getType(), 0, 20);
		request.setAttribute("newEntries", entries);
		// get link list
		List<BlogLink> links = linkService.listLinks(blog.getId(), 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("links", links);
		// get comment list
		List<BlogComment> comments = commentService.findComments(blog.getId(),
				null, 0, 10);
		request.setAttribute("newComments", comments);
		// get categoriy list
		List<BlogCategory> cats = blogService.findCategories(blog.getId(), 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);
		request.setAttribute("blogAuthor", userService.getUser(null, blog
				.getAuthor()));
		request.setAttribute("blogModel", blog);

		// #899 日志存档样式更新，需要在列表中获得全部月份
		// List<BlogEntryMonthly> shortSummaryList = entryService
		// .findMonthlyEntries(null, blog.getId(), 0, 8);
		List<BlogEntryMonthly> shortSummaryList = entryService
				.findMonthlyEntries(null, blog.getId(), 0,
						BusinessConstants.MAX_INT);

		request.setAttribute("shortSummaryList", shortSummaryList);

		if (blog.getShowGallery().intValue() == 1) {
			List<ImageModel> imageList = this.imageService.findGalleryImages(
					blog.getAuthor(), 0, 10);
			request.setAttribute("userImageList", imageList);
			request.setAttribute("authorModel", userService.getUser(null, blog
					.getAuthor()));

		}

	}

	public void prepareCommends(HttpServletRequest request) {
		List<MainCommend> commendList = commonService.findMainCommendsByType(
				CommendType.BLOG.getValue(), 0, 10);
		request.setAttribute("commendList", commendList);
	}

}
