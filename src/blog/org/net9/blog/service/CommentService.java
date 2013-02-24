package org.net9.blog.service;

import java.util.List;

import org.net9.core.service.BaseService;
import org.net9.domain.model.blog.BlogComment;

/**
 * comment service
 * 
 * @author gladstone
 * 
 */
public class CommentService extends BaseService {
	private static final long serialVersionUID = 1L;

	/**
	 * new a comment
	 * 
	 * @param model
	 */
	public void newComment(BlogComment model) {
		commentDAO.save(model);
	}

	/**
	 * delete a comment
	 * 
	 * @param model
	 */
	public void delComment(BlogComment model) {
		commentDAO.remove(model);
	}

	/**
	 * update a comment
	 * 
	 * @param model
	 */
	public void updateComment(BlogComment model) {
		commentDAO.update(model);
	}

	/**
	 * get a comment
	 * 
	 * @param id
	 * @return
	 */
	public BlogComment getComment(Integer id) {
		return commentDAO.findById(new Long(id));
	}

	/**
	 * comment list
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogComment> findComments(Integer entryId, Integer start,
			Integer limit) {
		String jpql = "select model from BlogComment model where model.id>0";
		if (entryId != null)
			jpql += " and model.blogBlogentry.id= " + entryId;
		jpql += " order by model.id desc ";
		return commentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * comment list by blog id and entry id
	 * 
	 * @param blogId
	 * @param entryId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogComment> findComments(Integer blogId, Integer entryId,
			Integer start, Integer limit) {
		String jpql = "select model from BlogComment model where model.id>0";
		if (entryId != null)
			jpql += " and model.blogBlogentry.id= " + entryId;
		if (blogId != null)
			jpql += " and model.blogBlogentry.blogBlog.id= " + blogId;
		jpql += " order by model.id desc ";
		return commentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * commment count
	 * 
	 * @param entryId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Integer getCommentsCnt(Integer blogId, Integer entryId) {
		String jpql = "select count(model) from BlogComment model where model.id>0";
		if (entryId != null)
			jpql += " and model.blogBlogentry.id= " + entryId;
		if (blogId != null)
			jpql += " and model.blogBlogentry.blogBlog.id= " + blogId;
		// jpql += " order by model.id desc ";
		return commentDAO.getCntByStatement(jpql);
	}
}
