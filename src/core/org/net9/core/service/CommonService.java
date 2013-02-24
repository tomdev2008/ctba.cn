package org.net9.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.domain.dao.core.MainCommendDAO;
import org.net9.domain.dao.core.SysFeedbackDAO;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.core.MainPlace;
import org.net9.domain.model.core.Online;
import org.net9.domain.model.core.SysConfig;
import org.net9.domain.model.core.SysFeedback;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * common service
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class CommonService extends BaseService {

	private static final long serialVersionUID = 2722396949750969780L;
	static Log log = LogFactory.getLog(CommonService.class);
	@Inject
	private MainCommendDAO mainCommendDAO;
	@Inject
	private SysFeedbackDAO sysFeedbackDAO;

	public void deleteMainCommend(MainCommend model) {
		mainCommendDAO.remove(model);
	}

	public void deleteFeedback(SysFeedback model) {
		sysFeedbackDAO.remove(model);
	}

	/**
	 * delete online
	 * 
	 * @param id
	 */
	public void deleteOnline(Online model) {
		bbsOnlineDAO.remove(model);
	}

	@SuppressWarnings("unchecked")
	public List<MainCommend> findMainCommends(Integer start, Integer limit) {
		String jpql = "select model from MainCommend model order by model.idx desc , model.id desc ";
		return mainCommendDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<MainCommend> findMainCommendsByType(Integer type,
			Integer start, Integer limit) {
		String jpql = "select model from MainCommend model where model.type="
				+ type + " order by model.idx desc , model.id desc ";
		return mainCommendDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * list online
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Online> findOnlines(Integer start, Integer limit) {
		return bbsOnlineDAO.findByStatement(
				"select model from Online model order by model.id desc", start,
				limit);
	}

	/**
	 * list MainPlace
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MainPlace> findPlaces(String name, Integer start, Integer limit) {
		String jpql = "select model from MainPlace model ";
		if (StringUtils.isNotEmpty(name)) {
			jpql += " where model.name='" + name + "'";
		}
		return mainPlaceDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<SysFeedback> findFeedbacks(Integer start, Integer limit) {
		String jpql = "select model from SysFeedback model ";
		return sysFeedbackDAO.findByStatement(jpql, start, limit);
	}

	public void flushMainCommendCache() {
		mainCommendDAO.flushCache();
	}

	/**
	 * get config instance
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SysConfig getConfig() {
		List<SysConfig> list = sysConfigDAO.findByStatement(
				"select model from SysConfig model order by model.id desc", 0,
				1);
		return list.size() > 0 ? list.get(0) : new SysConfig();
	}

	/**
	 * dirty words
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getForbiddenWordsAsList() {
		List wordsList = new ArrayList();
		String words = this.getConfig().getForbiddenWords();
		if (CommonUtils.isNotEmpty(words)) {
			String[] wordsArray = words.split("\n");
			for (int i = 0; i < wordsArray.length; i++) {
				String[] temp = wordsArray[i].split(";");
				for (int j = 0; j < temp.length; j++) {
					if (CommonUtils.isNotEmpty(temp[j]))
						wordsList.add(temp[j]);
				}
			}
		}
		return wordsList;
	}

	public MainCommend getMainCommend(Integer id) {
		return (MainCommend) mainCommendDAO.getByPrimaryKey(MainCommend.class,
				id);
	}

	public int getMainCommendCnt() {
		String jpql = "select count(model) from MainCommend model";
		return mainCommendDAO.getCntByStatement(jpql);
	}

	public int getMainCommendCntByType(Integer type) {
		String jpql = "select count(model) from MainCommend model where model.type="
				+ type;
		return mainCommendDAO.getCntByStatement(jpql);
	}

	public Integer getMaxMainCommendIdx(Integer type) {
		String jpql = "select  max(model.idx)  from MainCommend model where model.type="
				+ type;
		return mainCommendDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到排名比自己高的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MainCommend getNextMainCommendIdx(MainCommend c) {
		String jpql = "select model from MainCommend model where model.type="
				+ c.getType() + " and model.idx>" + c.getIdx()
				+ " order by model.idx asc";
		List l = mainCommendDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (MainCommend) l.get(0) : null;
	}

	/**
	 * get online by username or ip
	 * 
	 * @param username
	 * @param ip
	 * @return
	 */
	public Online getOnlineByUsernameOrIp(String username, String ip) {
		String jpql = "select model from Online model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "' ";
		}
		if (StringUtils.isNotEmpty(ip)) {
			jpql += " and model.ip='" + ip + "' ";
		}
		jpql += " order by model.id desc";
		Online model = (Online) bbsOnlineDAO.findSingleByStatement(jpql);
		return model;
	}

	/**
	 * get online count
	 * 
	 * @return
	 */
	public int getOnlineCnt() {
		String jpql = "select count(model) from Online model";
		return bbsOnlineDAO.getCntByStatement(jpql);
	}

	@SuppressWarnings("unchecked")
	public MainPlace getPlace(Integer id) {
		if (id != null) {
			return (MainPlace) mainPlaceDAO
					.getByPrimaryKey(MainPlace.class, id);
		}
		List<MainPlace> list = mainPlaceDAO.findByStatement(
				"select model from MainPlace model order by model.id desc", 0,
				1);
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public SysFeedback getFeedback(Integer id) {
		if (id != null) {
			return (SysFeedback) this.sysFeedbackDAO.getByPrimaryKey(
					SysFeedback.class, id);
		}
		List<SysFeedback> list = sysFeedbackDAO.findByStatement(
				"select model from SysFeedback model order by model.id desc",
				0, 1);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * get MainPlace count
	 * 
	 * @return
	 */
	public int getPlaceCnt() {
		String jpql = "select count(model) from MainPlace model ";
		return mainPlaceDAO.getCntByStatement(jpql);
	}

	public int getFeedbackCnt() {
		String jpql = "select count(model) from SysFeedback model ";
		return sysFeedbackDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到排名比自己低的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MainCommend getPrevMainCommendIdx(MainCommend c) {
		String jpql = "select model from MainCommend model where model.type="
				+ c.getType() + " and model.idx<" + c.getIdx()
				+ " order by model.idx desc";
		List l = mainCommendDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (MainCommend) l.get(0) : null;
	}

	public void saveConfig(SysConfig model) {
		if (model.getId() != null && model.getId() > 0) {
			sysConfigDAO.update(model);
		} else {
			sysConfigDAO.save(model);
		}

	}

	public void saveMainCommend(MainCommend model) {
		if (model.getId() == null)
			mainCommendDAO.save(model);
		else
			mainCommendDAO.update(model);
	}

	/**
	 * insert or update
	 * 
	 * @param model
	 */
	public void saveOnline(Online model) {
		if (model.getId() == null)
			bbsOnlineDAO.save(model);
		else
			bbsOnlineDAO.update(model);
	}

	/**
	 * insert or update
	 * 
	 * @param model
	 */
	public void savePlace(MainPlace model) {
		if (model.getId() == null)
			mainPlaceDAO.save(model);
		else
			mainPlaceDAO.update(model);
	}

	public void saveFeedback(SysFeedback model) {
		if (model.getId() == null)
			sysFeedbackDAO.save(model);
		else
			sysFeedbackDAO.update(model);
	}
}
