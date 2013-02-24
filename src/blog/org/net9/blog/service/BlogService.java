package org.net9.blog.service;

import java.util.List;

import org.net9.common.util.I18nMsgUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;

/**
 * blog service
 * 
 * @author gladstone
 * 
 */
public class BlogService extends BaseService {
	private static final long serialVersionUID = 1L;

	/**
	 * delete a blog
	 * 
	 * @param model
	 */
	public void delBlog(Blog model) {
		blogDAO.remove(model);
	}

	/**
	 * delete a category
	 * 
	 * @param model
	 */
	public void delCategory(BlogCategory model) {
		categoryDAO.remove(model);
	}

	/**
	 * list categories
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogCategory> findCategories(Integer blogId, int start,
			int limit) {
		String jpql = "select model from BlogCategory model where model.id>0 ";
		if (blogId != null) {
			jpql += "and model.blogBlog.id=" + blogId;
		}
		jpql += "  order by model.idx desc ";
		return categoryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 日志数量最多的博客
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Blog> findHotBlogs(Integer start, Integer limit) {
		return blogDAO.findByStatement(
				"select model from Blog model order by model.entryCnt desc",
				start, limit);
	}

	/**
	 * 点击量最多的博客
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Blog> findHotBlogsByHits(Integer start, Integer limit) {
		return blogDAO.findByStatement(
				"select model from Blog model order by model.hits desc", start,
				limit);
	}

	/**
	 * get blog by id
	 * 
	 * @param id
	 * @return
	 */
	public Blog getBlog(Integer id) {
		return (Blog) blogDAO.findById(id);
	}

	/**
	 * get blog by username
	 * 
	 * @param author
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Blog getBlogByUser(String author) {
		List l = blogDAO.findByAuthor(author);
		return l.size() > 0 ? (Blog) l.get(0) : null;
	}

	/**
	 * get count of all blogs
	 * 
	 * @return
	 */
	public Integer getBlogsCnt() {
		String jpql = "select count(model) from Blog model ";
		return blogDAO.getCntByStatement(jpql);
	}

	/**
	 * get count of all categories
	 * 
	 * @return
	 */
	public Integer getCategoriesCnt(Integer blogId) {
		String jpql = "select count(model) from BlogCategory model where model.id>0 ";
		if (blogId != null) {
			jpql += "and model.blogBlog.id=" + blogId;
		}
		return categoryDAO.getCntByStatement(jpql);
	}

	/**
	 * get a cat
	 * 
	 * @param id
	 *            null->返回最新的
	 * @return
	 */
	public BlogCategory getCategory(Integer id) {
		if (id == null) {
			List<BlogCategory> cList = findCategories(null, 0, 1);
			return cList.size() > 0 ? cList.get(0) : null;
		}
		return categoryDAO.findById(id);
	}

	@SuppressWarnings("unchecked")
	public BlogCategory getCategoryByName(Integer blogId, String name) {
		String jpql = "select model from BlogCategory model where model.id>0 ";
		jpql += "and model.blogBlog.id=" + blogId;
		jpql += "and model.name='" + name + "'";
		List<BlogCategory> cList = categoryDAO.findByStatement(jpql, 0, 1);
		return cList.size() > 0 ? cList.get(0) : null;
	}

	public Integer getMaxCategoryIdx(Blog blog) {
		String jpql = "select  max(model.idx)  from BlogCategory model where model.blogBlog.id="
				+ blog.getId();
		return linkDAO.getCntByStatement(jpql);
	}

	@SuppressWarnings("unchecked")
	public BlogCategory getMiniCategory(Integer blogId) {
		String catName = I18nMsgUtils.getInstance().getMessage("category.mini");
		String jpql = "select model from BlogCategory model where model.id>0 and model.blogBlog.id="
				+ blogId
				+ " and model.name='"
				+ catName
				+ "'  order by model.id desc ";
		List l = categoryDAO.findByStatement(jpql);
		BlogCategory c = l.size() > 0 ? (BlogCategory) l.get(0) : null;
		return c;
	}

	/**
	 * 得到排名比自己高的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogCategory getNextCategoryIdx(BlogCategory c) {
		String jpql = "select  model  from BlogCategory model where model.blogBlog.id="
				+ c.getBlogBlog().getId()
				+ " and model.idx>"
				+ c.getIdx()
				+ " order by model.idx asc";
		List l = categoryDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (BlogCategory) l.get(0) : null;
	}

	/**
	 * 得到排名比自己低的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogCategory getPrevCategoryIdx(BlogCategory c) {
		String jpql = "select  model  from BlogCategory model where model.blogBlog.id="
				+ c.getBlogBlog().getId()
				+ " and model.idx<"
				+ c.getIdx()
				+ " order by model.idx desc";
		List l = categoryDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (BlogCategory) l.get(0) : null;
	}

	/**
	 * get rss category
	 * 
	 * @param blogId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogCategory getRssCategory(Integer blogId) {
		String catName = I18nMsgUtils.getInstance().getMessage("category.rss");
		String jpql = "select model from BlogCategory model where model.id>0 and model.blogBlog.id="
				+ blogId
				+ " and model.name='"
				+ catName
				+ "'  order by model.id desc ";
		List l = categoryDAO.findByStatement(jpql);
		BlogCategory c = l.size() > 0 ? (BlogCategory) l.get(0) : null;
		return c;
	}

	/**
	 * 离线编辑分类
	 * 
	 * @param blogId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogCategory getLiveCategory(Integer blogId) {
		String catName = I18nMsgUtils.getInstance().getMessage("category.live");
		String jpql = "select model from BlogCategory model where model.id>0 and model.blogBlog.id="
				+ blogId
				+ " and model.name='"
				+ catName
				+ "'  order by model.id desc ";
		List l = categoryDAO.findByStatement(jpql);
		BlogCategory c = l.size() > 0 ? (BlogCategory) l.get(0) : null;
		return c;
	}

	/**
	 * the list of blogs
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Blog> findBlogs(int start, int limit) {
		String jpql = "select model from Blog model order by model.id desc ";
		return blogDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * new a blog instance
	 * 
	 * @param model
	 */
	public void newBlog(Blog model) {
		blogDAO.save(model);
	}

	/**
	 * new a category
	 * 
	 * @param model
	 */
	public void newCategory(BlogCategory model) {
		categoryDAO.save(model);
	}

	/**
	 * update a blog
	 * 
	 * @param model
	 */
	public void updateBlog(Blog model) {
		blogDAO.update(model);
	}

	public void updateBlogHit(Integer id, Integer hit) {
		blogDAO.exquteStatement("update Blog set hits=" + hit + " where id="
				+ id);
	}

	/**
	 * update a category
	 * 
	 * @param model
	 */
	public void updateCategory(BlogCategory model) {
		categoryDAO.update(model);
		// #854 (博客分类修改之后不显示更新的问题)
		categoryDAO.flushCache();
	}

}
