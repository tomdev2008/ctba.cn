package org.net9.blog.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.dao.view.ViewBlogEntryMonthlyDAO;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.view.BlogEntryMonthly;

import com.google.inject.Inject;

/**
 * 日志服务
 * 
 * @author gladstone
 * @since 2007/09/05
 */
public class EntryService extends BaseService {

	private static final long serialVersionUID = 3764525461930882239L;

	/**
	 * #684 博客增加草稿箱
	 * 
	 * @author gladstone
	 * @since Feb 21, 2009
	 */
	public enum EntryType {

		NORMAL(1000), DRAFT(1001);
		private final int type;

		EntryType(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	@Inject
	private ViewBlogEntryMonthlyDAO viewBlogEntryMonthlyDAO;

	/**
	 * 
	 * delete an entry
	 * 
	 * @param model
	 */
	public void delEntry(BlogEntry model) {
		blogEntryDAO.remove(model);
	}

	/**
	 * 
	 * @param blogId
	 * @param categoryId
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogEntry> findEntries(Integer blogId, Integer categoryId,
			Integer type, Integer start, Integer limit) {
		String jpql = "select model from BlogEntry model where model.id>0 ";
		if (categoryId != null && categoryId > 0) {
			jpql += " and model.categoryId=" + categoryId;
		}
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (blogId != null && blogId > 0) {
			jpql += " and model.blogBlog.id =" + blogId;
		}
		jpql += " order by model.publishDate desc";
		return blogEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据月份取得日志（不包括草稿）
	 * 
	 * @param blogId
	 * @param month
	 * @param start
	 * @param limit
	 * @return
	 */
	// TODO: change this for high performance
	@SuppressWarnings("unchecked")
	public List<BlogEntry> findEntriesByMonth(Integer blogId, String month,
			Integer start, Integer limit) {
		String jpql = "select model from BlogEntry model where model.id>0 ";
		jpql += " and model.date like '" + month + "%'";
		jpql += " and model.type=" + EntryService.EntryType.NORMAL.getType();
		if (blogId != null && blogId > 0) {
			jpql += " and model.blogBlog.id =" + blogId;
		}
		jpql += " order by model.publishDate desc";
		return blogEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 热门日志
	 * 
	 * @param key
	 *            一般不用
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogEntry> findHotEntries(String key, Integer type,
			Integer start, Integer limit) {
		String jpql = "select model from BlogEntry model where model.id>0 ";
		if (StringUtils.isNotEmpty(key)) {
			jpql += " and model.title like '%" + key + "' ";
		}
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		jpql += " order by model.hits desc, model.publishDate desc";
		return blogEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 每月摘要
	 * 
	 * @param beforeMonth
	 * @param blogId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogEntryMonthly> findMonthlyEntries(String beforeMonth,
			Integer blogId, Integer start, Integer limit) {
		String jpql = "select model from BlogEntryMonthly model where  model.bid="
				+ blogId;
		if (StringUtils.isNotEmpty(beforeMonth)) {
			jpql += " and model.month > '" + beforeMonth + "' ";
		}
		jpql += " order by model.month desc";
		return viewBlogEntryMonthlyDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<BlogEntry> findMostCommentedEntries(Integer type,
			Integer start, Integer limit) {
		String jpql = "select model from BlogEntry model where model.id>0 ";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		jpql += " order by model.commentCnt desc, model.publishDate desc";
		return blogEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 
	 * @param key
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogEntry> findNewEntries(String key, Integer type,
			Integer start, Integer limit) {
		String jpql = "select model from BlogEntry model where model.id>0 ";
		if (StringUtils.isNotEmpty(key)) {
			jpql += " and model.title like '%" + key + "' ";
		}
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		jpql += " order by model.publishDate desc";
		return blogEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 
	 * @param blogId
	 * @param categoryId
	 * @param type
	 * @return
	 */
	public Integer getEntriesCnt(Integer blogId, Integer categoryId,
			Integer type) {
		String jpql = "select count(model) from BlogEntry model where model.id>0 ";
		if (categoryId != null && categoryId > 0) {
			jpql += " and model.categoryId=" + categoryId;
		}
		if (blogId != null && blogId > 0) {
			jpql += " and model.blogBlog.id =" + blogId;
		}
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		return blogEntryDAO.getCntByStatement(jpql);
	}

	public Integer getEntriesCntByMonth(Integer blogId, String month) {
		String jpql = "select count(model) from BlogEntry model where model.id>0 ";
		if (blogId != null && blogId > 0) {
			jpql += " and model.blogBlog.id =" + blogId;
		}
		jpql += " and model.date like '" + month + "%'";
		return blogEntryDAO.getCntByStatement(jpql);
	}

	/**
	 * 
	 * @param title
	 * @param body
	 * @param type
	 * @return
	 */
	public Integer getEntriesCntWithTitle(String title, String body,
			Integer type) {
		String jpql = "select count(model) from BlogEntry model where model.id>0 ";
		if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(body)) {
			jpql += " and (model.body like '%" + body + "%'"
					+ " or model.title like '%" + title + "%')";
		} else if (StringUtils.isNotEmpty(title)) {
			jpql += " and model.title like '%" + title + "%'";
		} else if (StringUtils.isNotEmpty(body)) {
			jpql += " and model.body like '%" + body + "%'";
		}
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		// jpql += " order by model.id desc";
		return blogEntryDAO.getCntByStatement(jpql);
	}

	/**
	 * 
	 * get a entry
	 * 
	 * @param id
	 * @return
	 */
	public BlogEntry getEntry(Integer id) {
		return (BlogEntry) blogEntryDAO.findById(id);
	}

	/**
	 * 
	 * get entry by permalink
	 * 
	 * @param permalink
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogEntry getEntryByPermalink(String permalink) {
		String jpql = "select model from BlogEntry model where model.permalink='"
				+ permalink + "'";
		List l = blogEntryDAO.findByStatement(jpql, 0, 1);
		if (l.size() <= 0) {
			return null;
		}
		return (BlogEntry) l.get(0);
	}

	/**
	 * find by title
	 * 
	 * @param title
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogEntry getEntryByTitle(String title) {
		String jpql = "select model from BlogEntry model where model.title='"
				+ title + "'";
		List l = blogEntryDAO.findByStatement(jpql, 0, 1);
		if (l.size() <= 0) {
			return null;
		}
		return (BlogEntry) l.get(0);
	}

	/**
	 * 
	 * @param bid
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogEntry getNewestEntry(Integer bid, Integer type) {
		List l = findEntries(bid, null, type, 0, 1);
		if (l.size() <= 0) {
			return null;
		}
		return (BlogEntry) l.get(0);
	}

	/**
	 * 只得到不是草稿的 get next entry
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogEntry getNextEntry(BlogEntry entry) {
		String jpql = "select model from BlogEntry model where model.id>"
				+ entry.getId() + " and model.blogBlog.id="
				+ entry.getBlogBlog().getId() + " and model.type="
				+ EntryType.NORMAL.getType() + " order by model.id asc ";
		List l = blogEntryDAO.findByStatement(jpql, 0, 2);
		if (l.size() <= 0) {
			return null;
		}
		return (BlogEntry) l.get(0);
	}

	/**
	 * 只得到不是草稿的 get prev entry
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogEntry getPrevEntry(BlogEntry entry) {
		String jpql = "select model from BlogEntry model where model.id<"
				+ entry.getId() + " and model.blogBlog.id="
				+ entry.getBlogBlog().getId() + " and model.type="
				+ EntryType.NORMAL.getType() + " order by model.id desc ";
		List l = blogEntryDAO.findByStatement(jpql, 0, 2);
		if (l.size() <= 0) {
			return null;
		}
		return (BlogEntry) l.get(0);
	}

	/**
	 * 
	 * new an entry
	 * 
	 * @param model
	 */
	public void newEntry(BlogEntry model) {
		blogEntryDAO.save(model);
	}

	/**
	 * 
	 * update an entry
	 * 
	 * @param model
	 */
	public void updateEntry(BlogEntry model) {
		blogEntryDAO.update(model);
	}

	public void flushEntryCache() {
		blogEntryDAO.flushCache();
	}

	public void updateEntryHit(Integer id, Integer hit) {
		blogEntryDAO.exquteStatement("update BlogEntry set hits=" + hit
				+ " where id=" + id);
	}

}
