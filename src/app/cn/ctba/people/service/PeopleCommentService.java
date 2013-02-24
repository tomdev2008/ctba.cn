//package cn.ctba.people.service;
//
//import java.util.List;
//
//import org.net9.common.util.StringUtils;
//import org.net9.core.service.BaseService;
//import org.net9.domain.dao.ctba.PeopleCommentDAO;
//import org.net9.domain.model.ctba.PeopleComment;
//
//import com.google.inject.Inject;
//import com.google.inject.servlet.SessionScoped;
//
///**
// * 人物评论服务
// * 
// * @author gladstone
// * @since Mar 8, 2009
// */
//@SessionScoped
//public class PeopleCommentService extends BaseService {
//
//	private static final long serialVersionUID = 1L;
//	@Inject
//	private PeopleCommentDAO peopleCommentDAO;
//
//	/**
//	 * 删除评论
//	 * 
//	 * @param model
//	 */
//	public void deletePeopleComment(PeopleComment model) {
//		peopleCommentDAO.remove(model);
//	}
//
//	/**
//	 * 得到评论列表
//	 * 
//	 * @param username
//	 * @param wareId
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<PeopleComment> findPeopleComment(String username,
//			Integer peopleId, Integer start, Integer limit) {
//		String jpql = "select model from PeopleComment model where model.id>0";
//		if (peopleId != null) {
//			jpql += " and model.people.id=" + peopleId;
//		}
//		if (StringUtils.isNotEmpty(username)) {
//			jpql += " and model.username = '" + username + "'";
//		}
//		jpql += " order by model.id desc";
//		return peopleCommentDAO.findByStatement(jpql, start, limit);
//	}
//
//	@SuppressWarnings("unchecked")
//	public PeopleComment getPeopleComment(Integer id) {
//		String jpql = "select model from PeopleComment model where model.id>0";
//		jpql += " and model.id=" + id;
//		jpql += " order by model.id desc";
//		List<PeopleComment> list = peopleCommentDAO.findByStatement(jpql, 0, 1);
//		return list.size() > 0 ? list.get(0) : null;
//	}
//
//	/**
//	 * 根据接收者用户得到评论列表
//	 * 
//	 * @param recieverUsername
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<PeopleComment> findPeopleCommentsByReciever(
//			String recieverUsername, Integer start, Integer limit) {
//		String jpql = "select model from PeopleComment model where model.id>0";
//		jpql += " and model.people.username='" + recieverUsername + "'";
//		jpql += " order by model.id desc";
//		return peopleCommentDAO.findByStatement(jpql, start, limit);
//	}
//
//	/**
//	 * 得到评论数量
//	 * 
//	 * @param uid
//	 * @param wareId
//	 * @return
//	 */
//	public Integer getPeopleCommentCnt(String username, Integer wareId) {
//		String jpql = "select count(model) from PeopleComment model where model.id>0";
//		if (wareId != null) {
//			jpql += " and model.people.id=" + wareId;
//		}
//		if (StringUtils.isNotEmpty(username)) {
//			jpql += " and model.username ='" + username + "'";
//		}
//		return peopleCommentDAO.getCntByStatement(jpql);
//	}
//
//	/**
//	 * 保存评论
//	 * 
//	 * @param model
//	 */
//	public void savePeopleComment(PeopleComment model) {
//		if (model.getId() != null) {
//			peopleCommentDAO.update(model);
//		} else {
//			peopleCommentDAO.save(model);
//		}
//	}
//
//}
