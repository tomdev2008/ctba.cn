package org.net9.blog.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.service.BaseService;
import org.net9.domain.model.blog.BlogLink;

/**
 * link service
 * 
 * @author gladstone
 * 
 */
public class LinkService extends BaseService {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(LinkService.class);

	/**
	 * delete an link
	 * 
	 * @param model
	 */
	public void delLink(BlogLink model) {
		linkDAO.remove(model);
	}

	/**
	 * get a link
	 * 
	 * @param id
	 * @return
	 */
	public BlogLink getLink(Integer id) {
		return (BlogLink) linkDAO.findById(id);
	}

	/**
	 * get count
	 * 
	 * @param blogId
	 * @param categoryId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Integer getLinksCnt(Integer blogId) {
		String jpql = "select count(model) from BlogLink model where model.id>0 ";
		if (blogId != null && blogId > 0) {
			jpql += " and model.blogId=" + blogId;
		}
		return linkDAO.getCntByStatement(jpql);
	}

	public Integer getMaxLinksIdx(Integer blogId) {
		String jpql = "select  max(model.idx) from BlogLink model where model.blogId="
				+ blogId;
		return linkDAO.getCntByStatement(jpql);
	}

	public Integer getMinLinksIdx(Integer blogId) {
		String jpql = "select  min(model.idx) from BlogLink model where model.blogId="
				+ blogId;
		return linkDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到排名比自己高的链接
	 * 
	 * @param link
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogLink getNextLinksIdx(BlogLink link) {
		String jpql = "select  model  from BlogLink model where model.blogId="
				+ link.getBlogId() + " and model.idx>" + link.getIdx()
				+ " order by model.idx asc";
		List l = linkDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (BlogLink) l.get(0) : null;
	}

	/**
	 * 得到排名比自己低的链接
	 * 
	 * @param link
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BlogLink getPrevLinksIdx(BlogLink link) {
		String jpql = "select  model  from BlogLink model where model.blogId="
				+ link.getBlogId() + " and model.idx<" + link.getIdx()
				+ " order by model.idx desc";
		List l = linkDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (BlogLink) l.get(0) : null;
	}

	/**
	 * entry list
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BlogLink> listLinks(Integer blogId, Integer start, Integer limit) {
		String jpql = "select model from BlogLink model where model.id>0 ";
		if (blogId != null && blogId > 0) {
			jpql += " and model.blogId=" + blogId;
		}
		jpql += " order by model.idx desc";
		log.debug(jpql);
		return linkDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * new an link
	 * 
	 * @param model
	 */
	public void newLink(BlogLink model) {
		linkDAO.save(model);
	}

	/**
	 * update an link
	 * 
	 * @param model
	 */
	public void updateLink(BlogLink model) {
		linkDAO.update(model);
	}
}
