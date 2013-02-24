//package cn.ctba.people.service;
//
//import java.util.List;
//
//import org.net9.common.util.StringUtils;
//import org.net9.core.service.BaseService;
//import org.net9.domain.dao.ctba.PeopleDAO;
//import org.net9.domain.model.ctba.People;
//
//import com.google.inject.Inject;
//import com.google.inject.servlet.SessionScoped;
//
///**
// * 人物服务
// * 
// * @author gladstone
// */
//@SessionScoped
//public class PeopleService extends BaseService {
//
//	private static final long serialVersionUID = -4714862389120015909L;
//	@Inject
//	private PeopleDAO peopleDAO;
//
//	/**
//	 * 删除装备
//	 * 
//	 * @param model
//	 */
//	public void deletePeople(People model) {
//		peopleDAO.remove(model);
//	}
//
//	/**
//	 * 得到装备列表
//	 * 
//	 * @param createUsername
//	 * @param type
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<People> findPeoples(String createUsername, Integer type,
//			Integer start, Integer limit) {
//		String jpql = "select model from People model where model.id>0";
//		if (type != null) {
//			jpql += " and model.type='" + type + "'";
//		}
//		if (StringUtils.isNotEmpty(createUsername)) {
//			jpql += " and model.createUsername='" + createUsername + "'";
//		}
//		jpql += " order by model.id desc";
//		return peopleDAO.findByStatement(jpql, start, limit);
//	}
//
//	/**
//	 * 查找相关人物
//	 * 
//	 * @param pid
//	 * @param type
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<People> findRefPeoples(Integer pid, String type, Integer start,
//			Integer limit) {
//		String jpql = "select model from People model where model.id>0";
//		jpql += " and model.type='" + type + "'";
//		jpql += " and model.id<>" + pid;
//		jpql += " order by model.id desc";
//		return peopleDAO.findByStatement(jpql, start, limit);
//	}
//
//	/**
//	 * 根据点击量排序
//	 * 
//	 * @param type
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<People> findPeoplesByHits(String createUsername, Integer type,
//			Integer start, Integer limit) {
//		String jpql = "select model from People model where model.id>0";
//		if (StringUtils.isNotEmpty(createUsername)) {
//			jpql += " and model.createUsername='" + createUsername + "'";
//		}
//		if (type != null) {
//			jpql += " and model.type='" + type + "'";
//		}
//		jpql += " order by model.hits desc, model.hits desc";
//		return peopleDAO.findByStatement(jpql, start, limit);
//	}
//
//	/**
//	 * 得到数量
//	 * 
//	 * @param name
//	 * @param type
//	 * @return
//	 */
//	public Integer getPeopleCnt(String name, String createUsername, Integer type) {
//		String jpql = "select count(model) from People model where model.id>0";
//		if (type != null) {
//			jpql += " and model.type=" + type;
//		}
//		if (StringUtils.isNotEmpty(name)) {
//			jpql += " and model.name like '%" + name + "%'";
//		}
//		if (StringUtils.isNotEmpty(createUsername)) {
//			jpql += " and model.createUsername='" + createUsername + "'";
//		}
//		return peopleDAO.getCntByStatement(jpql);
//	}
//
//	/**
//	 * 得到下一个
//	 * 
//	 * @param wid
//	 *            必须
//	 * @param username
//	 * @param type
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public People getNextPeople(Integer wid, String username, Integer type) {
//		String jpql = "select model from People model where model.id>0";
//		if (type != null) {
//			jpql += " and model.type=" + type;
//		}
//		if (StringUtils.isNotEmpty(username)) {
//			jpql += " and model.username='" + username + "'";
//		}
//		jpql += " and model.id <" + wid;
//		jpql += " order by model.id desc";
//		List<People> list = peopleDAO.findByStatement(jpql, 0, 1);
//		if (list.size() > 0) {
//			return list.get(0);
//		}
//		list = this.findPeoples(null, type, 0, 1);
//		return list.size() > 0 ? list.get(0) : null;
//	}
//
//	@SuppressWarnings("unchecked")
//	public People getPeople(Integer pid) {
//		String jpql = "select model from People model where model.id =" + pid;
//		List<People> list = peopleDAO.findByStatement(jpql, 0, 1);
//		return list.size() > 0 ? list.get(0) : null;
//	}
//
//	/**
//	 * 得到上一个
//	 * 
//	 * @param wid
//	 *            必须
//	 * @param username
//	 * @param type
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public People getPrevPeople(Integer wid, String username, Integer type) {
//		String jpql = "select model from People model where model.id>0";
//		if (type != null) {
//			jpql += " and model.type=" + type;
//		}
//		if (StringUtils.isNotEmpty(username)) {
//			jpql += " and model.username='" + username + "'";
//		}
//		jpql += " and model.id >" + wid;
//		jpql += " order by model.id asc";
//		List<People> list = peopleDAO.findByStatement(jpql, 0, 1);
//		if (list.size() > 0) {
//			return list.get(0);
//		}
//		list = this.findPeoples(null, type, 0, 1);
//		return list.size() > 0 ? list.get(0) : null;
//	}
//
//	/**
//	 * 保存装备
//	 * 
//	 * @param model
//	 */
//	public void savePeople(People model) {
//		if (model.getId() != null) {
//			peopleDAO.update(model);
//		} else {
//			peopleDAO.save(model);
//		}
//	}
//}
