package org.net9.news.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.core.types.NewsStateType;
import org.net9.domain.model.news.NewsCategory;
import org.net9.domain.model.news.NewsComment;
import org.net9.domain.model.news.NewsEntry;
import org.net9.domain.model.news.NewsPost;

/**
 * news service
 * 
 * @author gladstone
 * @since 2008-9-18
 */
public class NewsService extends BaseService {
	private static final long serialVersionUID = 1L;

	public void deleteCategory(NewsCategory model) {
		newsCategoryDAO.remove(model);
	}

	public void deleteComment(NewsComment model) {
		newsCommentDAO.remove(model);
	}

	public void deleteNews(NewsEntry model) {
		newsEntryDAO.remove(model);
	}

	public void deletePost(NewsPost model) {
		newsPostDAO.remove(model);
	}

	@SuppressWarnings("unchecked")
	public List<NewsCategory> findCats(boolean containPlugin, Integer start,
			Integer limit) {
		String jpql = "select model from NewsCategory model";
		if (!containPlugin) {
			jpql += " where model.isPlugin=0";
		}
		return newsCategoryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到新闻的评论列表
	 * 
	 * @param nid
	 *            新闻ID
	 * @param orderByDesc
	 *            是否降序排列
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NewsComment> findComments(Integer nid, Boolean orderByDesc,
			Integer start, Integer limit) {
		String jpql = "select model from NewsComment model ";
		if (nid != null) {
			jpql += " where model.newsId=" + nid + " ";
		}
		if (orderByDesc) {
			jpql += " order by model.id desc";
		} else {
			jpql += " order by model.id asc";
		}

		return newsCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * newses that got the most hits
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NewsEntry> findHotNewses(boolean containPlugin, Integer start,
			Integer limit) {
		String jpql = "select model from NewsEntry model ";
		if (!containPlugin) {
			jpql += " where model.newsCategory.isPlugin=0 ";
		}
		jpql += " order by model.hits desc";
		return newsEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * new news entries
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NewsEntry> findNewses(boolean containPlugin, Integer state,
			Integer cid, Integer start, Integer limit) {
		String jpql = "select model from NewsEntry model where model.id>0 ";
		if (cid != null) {
			jpql += "and model.newsCategory.id=" + cid + " ";
		}
		if (!containPlugin) {
			jpql += " and model.newsCategory.isPlugin=0 ";
		}
		if (state != null) {
			jpql += "and model.state=" + state + " ";
		}
		jpql += " order by model.id desc";
		return newsEntryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 按照Digg排
	 * 
	 * @param containPlugin
	 * @param state
	 * @param cid
	 * @param date
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NewsEntry> findNewsesOrderByDigg(boolean containPlugin,
			Integer state, Integer cid, String date, Integer start,
			Integer limit) {
		String jpql = "select model from NewsEntry model where model.id>0 ";
		if (cid != null) {
			jpql += "and model.newsCategory.id=" + cid + " ";
		}
		if (state != null) {
			jpql += "and model.state=" + state + " ";
		}
		if (date != null) {
			jpql += "and model.updateTime>='" + date + "' ";
		}
		if (!containPlugin) {
			jpql += " and model.newsCategory.isPlugin=0 ";
		}
		jpql += " order by model.hitGood desc,model.id desc";
		return newsEntryDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<NewsPost> findPosts(Boolean notDone, Integer start,
			Integer limit) {
		String jpql = "select model from NewsPost model where model.id>0 ";
		if (notDone) {
			jpql += " and model.done=0 ";
		}
		jpql += " order by model.id desc";
		return newsPostDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 强制刷新缓存
	 */
	public void flushNewsEntryCache() {
		this.newsEntryDAO.flushCache();
	}

	public NewsCategory getCategory(Integer cid) {
		return newsCategoryDAO.findById(cid);
	}

	public Integer getCatsCnt(boolean containPlugin) {
		String jpql = "select count(model) from NewsCategory model";
		if (!containPlugin) {
			jpql += " where model.isPlugin=0 ";
		}
		return newsCategoryDAO.getCntByStatement(jpql);
	}

	public NewsComment getComment(Integer cid) {
		return newsCommentDAO.findById(cid);
	}

	public Integer getCommentsCnt(Integer nid) {
		String jpql = "select count(model) from NewsComment model ";
		if (nid != null) {
			jpql += " where model.newsId=" + nid + " ";
		}
		return newsCommentDAO.getCntByStatement(jpql);
	}

	public NewsEntry getNews(Integer nid) {
		return newsEntryDAO.findById(nid);
	}

	@SuppressWarnings("unchecked")
	public NewsEntry getNews(Integer cid, String title) {
		String jpql = "select model from NewsEntry model where model.id>0 ";
		if (cid != null) {
			jpql += "and  model.newsCategory.id=" + cid + " ";
		}
		if (StringUtils.isNotEmpty(title)) {
			jpql += "and model.title='" + title + "' ";
		}
		jpql += " order by model.id desc";
		List l = newsEntryDAO.findByStatement(jpql);
		return l == null || l.size() < 1 ? null : (NewsEntry) l.get(0);
	}

	@SuppressWarnings("unchecked")
	public NewsEntry getNewsByFakeUrl(String fakeUrl) {
		String jpql = "select model from NewsEntry model where model.fakeUrl='"
				+ fakeUrl + "' order by model.id desc";
		List<NewsEntry> l = newsEntryDAO.findByStatement(jpql);
		return l.size() < 1 ? null : l.get(0);
	}

	public Integer getNewsCnt(boolean containPlugin, Integer state, Integer cid) {
		String jpql = "select count(model) from NewsEntry model where model.id>0 ";
		if (cid != null) {
			jpql += "and model.newsCategory.id=" + cid + " ";
		}
		if (state != null) {
			jpql += "and model.state=" + state + " ";
		}
		if (!containPlugin) {
			jpql += " and model.newsCategory.isPlugin=0 ";
		}
		return newsEntryDAO.getCntByStatement(jpql);
	}

	public Integer getNewsCntByAuthor(String username) {
		String jpql = "select count(model) from NewsEntry model where model.id>0 ";
		jpql += "and model.state=" + NewsStateType.OK.getValue() + " ";
		jpql += "and model.author='" + username + "' ";
		return newsEntryDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到用戶投遞的新聞
	 * 
	 * @param pid
	 *            可以為空,為空返回最新的
	 * @return
	 */
	public NewsPost getPost(Integer pid) {
		if (pid == null) {
			List<NewsPost> pList = findPosts(false, 0, 1);
			return pList.size() > 0 ? pList.get(0) : null;
		} else {
			return newsPostDAO.findById(pid);
		}

	}

	public Integer getPostsCnt(Boolean notDone) {
		String jpql = "select count(model) from NewsPost model where model.id>0 ";
		if (notDone) {
			jpql += " and model.done=0 ";
		}
		return newsPostDAO.getCntByStatement(jpql);
	}

	public void saveCategory(NewsCategory model) {
		newsCategoryDAO.save(model);
	}

	public void saveComment(NewsComment model) {
		newsCommentDAO.save(model);
	}

	public void saveNews(NewsEntry model) {
		if (model.getId() == null) {
			newsEntryDAO.save(model);
		} else {
			newsEntryDAO.update(model);
		}

	}

	public void savePost(NewsPost model) {
		newsPostDAO.save(model);
	}
}
