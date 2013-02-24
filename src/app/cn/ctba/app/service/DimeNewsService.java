package cn.ctba.app.service;

import java.util.List;

import org.net9.domain.model.news.NewsCategory;
import org.net9.domain.model.news.NewsEntry;
import org.net9.news.service.NewsService;

/**
 * dime化新闻服务，注意方法里的containPlugin参数都无效
 * 
 * @author gladstone
 * @since 2008-9-18
 */
public class DimeNewsService extends NewsService {
	private static final long serialVersionUID = 1L;
	public static final String DIME_CODE = "dime";

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsCategory> findCats(boolean containPlugin, Integer start,
			Integer limit) {
		String jpql = "select model from NewsCategory model where model.isPlugin=1 and model.code='"
				+ DIME_CODE + "'";
		return newsCategoryDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsEntry> findHotNewses(boolean containPlugin, Integer start,
			Integer limit) {
		String jpql = "select model from NewsEntry model where model.newsCategory.isPlugin=1 and model.newsCategory.code='"
				+ DIME_CODE + "' order by model.hits desc";
		return newsEntryDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsEntry> findNewses(boolean containPlugin, Integer state,
			Integer cid, Integer start, Integer limit) {
		String jpql = "select model from NewsEntry model where  model.newsCategory.isPlugin=1 and model.newsCategory.code='"
				+ DIME_CODE + "' ";
		if (cid != null) {
			jpql += "and model.newsCategory.id=" + cid + " ";
		}
		if (state != null) {
			jpql += "and model.state=" + state + " ";
		}
		jpql += " order by model.id desc";
		return newsEntryDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsEntry> findNewsesOrderByDigg(boolean containPlugin,
			Integer state, Integer cid, String date, Integer start,
			Integer limit) {
		String jpql = "select model from NewsEntry model where model.newsCategory.isPlugin=1 and model.newsCategory.code='"
				+ DIME_CODE + "' ";
		if (cid != null) {
			jpql += "and model.newsCategory.id=" + cid + " ";
		}
		if (state != null) {
			jpql += "and model.state=" + state + " ";
		}
		if (date != null) {
			jpql += "and model.updateTime>='" + date + "' ";
		}
		jpql += " order by model.hitGood desc,model.id desc";
		return newsEntryDAO.findByStatement(jpql, start, limit);
	}

	@Override
	public Integer getCatsCnt(boolean containPlugin) {
		String jpql = "select count(model) from NewsCategory model where model.isPlugin=1 and model.code='"
				+ DIME_CODE + "'";
		return newsCategoryDAO.getCntByStatement(jpql);
	}

	@Override
	public Integer getNewsCnt(boolean containPlugin, Integer state, Integer cid) {
		String jpql = "select count(model) from NewsEntry model where model.newsCategory.isPlugin=1 and model.newsCategory.code='"
				+ DIME_CODE + "' ";
		if (cid != null) {
			jpql += "and model.newsCategory.id=" + cid + " ";
		}
		if (state != null) {
			jpql += "and model.state=" + state + " ";
		}
		return newsEntryDAO.getCntByStatement(jpql);
	}

}
